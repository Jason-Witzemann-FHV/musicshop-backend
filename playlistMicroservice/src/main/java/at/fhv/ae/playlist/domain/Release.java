package at.fhv.ae.playlist.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class Release extends PanacheEntityBase {

    @Id
    private String releaseId;

    private String title;

    private String artist;

    private int duration;

    public Release() {
    }

    public Release(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }
}
