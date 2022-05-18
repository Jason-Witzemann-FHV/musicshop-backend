package at.fhv.ae.playlist.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity
public class Playlist extends PanacheEntityBase {

    @Id
    String userId;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Release> releases;

    public Playlist() {
    }

    public Playlist(String userId, List<Release> releases) {
        this.userId = userId;
        this.releases = releases;
    }

    public void addRelease(Release release) {
        this.releases.add(release);
        //b.getAuthors().add(this);
    }
}

