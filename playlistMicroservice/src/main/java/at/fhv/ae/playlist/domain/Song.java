package at.fhv.ae.playlist.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Song extends PanacheEntityBase implements Serializable {

    @Id
    UUID songId;

    String title;

    String artist;

    int duration;

    public Song() {

    }

    public Song(UUID songId, String title, String artist, int duration) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public UUID getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }
}
