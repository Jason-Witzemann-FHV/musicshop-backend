package at.fhv.ae.playlist.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist extends PanacheEntityBase {

    @Id
    String userId;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Song> songs;

    public Playlist() {
    }

    public Playlist(String userId) {
        this.userId = userId;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        if(song != null) {
            this.songs.add(song);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Song> allSongs(){
        return songs;
    }
}

