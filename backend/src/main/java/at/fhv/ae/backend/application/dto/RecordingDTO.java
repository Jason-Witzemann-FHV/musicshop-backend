package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.work.Artist;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import lombok.Value;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Value
public class RecordingDTO {

    String title;
    ArrayList<String> artists;
    ArrayList<String> genres;
    int year;
    int duration;

    public static RecordingDTO fromDomain(Recording recording) {
        return new RecordingDTO(
                recording.title(),
                recording.artists().stream().map(Artist::name).collect(Collectors.toCollection(ArrayList::new)),
                recording.genres().stream().map(Genre::friendlyName).collect(Collectors.toCollection(ArrayList::new)),
                recording.year(),
                recording.duration()
        );
    }
}
