package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DetailedReleaseDTO {

    String title;
    double price;
    int stock;
    String medium;
    List<RecordingDTO> recordings;

    public static DetailedReleaseDTO fromDomain(Release release, List<RecordingDTO> recordings) {
        return new DetailedReleaseDTO(
                release.title(),
                release.price(),
                release.stock(),
                release.medium().friendlyName(),
                recordings
        );
    }
}
