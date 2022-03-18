package at.fhv.ae.backend.domain.model.work;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recording {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    private Long recordingIdInternal;

    @Embedded
    private RecordingId recordingId;

    private String title;

    private int duration; // in seconds

    private int year;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Work work;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Artist> artists;

    @ElementCollection
    private List<Genre> genres;

    public Recording(RecordingId recordingId, String title, int duration, int year, Work work, List<Artist> artists, List<Genre> genres) {
        this.recordingId = recordingId;
        this.title = title;
        this.duration = duration;
        this.year = year;
        this.work = work;
        this.artists = artists;
        this.genres = genres;
    }
}
