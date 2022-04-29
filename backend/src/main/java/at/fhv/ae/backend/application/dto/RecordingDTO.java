package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.work.Artist;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
                recording.recordingYear(),
                recording.duration()
        );
    }
}
