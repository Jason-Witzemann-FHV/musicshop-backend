package at.fhv.ae.playlist.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlaylistReleaseDTO implements Serializable {
    String title;
    String artist;
    int duration;

    public PlaylistReleaseDTO(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }
}
