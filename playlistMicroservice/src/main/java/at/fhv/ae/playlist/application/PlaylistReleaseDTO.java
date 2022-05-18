package at.fhv.ae.playlist.application;

public class PlaylistReleaseDTO {
    String title;
    String artist;
    int duration;

    public PlaylistReleaseDTO(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }
}
