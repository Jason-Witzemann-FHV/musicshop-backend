package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.work.Artist;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class DetailedReleaseDTO {

    String title;
    double price;
    int stock;
    String medium;
    ArrayList<RecordingDTO> recordings;

    public static DetailedReleaseDTO fromDomain(Release release, ArrayList<RecordingDTO> recordings) {
        return new DetailedReleaseDTO(
                release.title(),
                release.price(),
                release.stock(),
                release.medium().friendlyName(),
                recordings
        );
    }
}
