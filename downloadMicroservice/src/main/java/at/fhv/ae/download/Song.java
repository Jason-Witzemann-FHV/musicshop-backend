package at.fhv.ae.download;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

public class Song {

    private static final File SONG_DIR = new File("..\\..\\..\\..\\songs");

    private Song(String contentType, String downloadName, byte[] bytes) {
        this.contentType = contentType;
        this.downloadName = downloadName;
        this.bytes = bytes;
    }

    private final String contentType;
    private final String downloadName;
    private final byte[] bytes;

    public String contentType() {
        return contentType;
    }

    public String downloadName() {
        return downloadName;
    }

    public byte[] bytes() {
        return bytes;
    }

    public static Optional<Song> find(UUID id) {

        var matches = SONG_DIR.listFiles((dir, name) -> {
            var f = name.split("\\.");
            return f.length == 2 && f[0].equals(id.toString());
        });

        if(matches == null || matches.length == 0) {
            System.out.println(id + " not found in files");
            return Optional.empty();
        }

        var file = matches[0];

        try {
            var metadata = new Metadata();

            new AutoDetectParser().parse(new FileInputStream(file), new DefaultHandler(), metadata);

            var contentType = Files.probeContentType(file.toPath());

            var f = file.getName().split("\\.");
            var extension = f[f.length - 1];
            var name = metadata.get("dc:creator") + " - " + metadata.get("dc:title") + "." + extension;

            var bytes = new FileInputStream(file).readAllBytes();

            return Optional.of(new Song(contentType, name, bytes));

        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
