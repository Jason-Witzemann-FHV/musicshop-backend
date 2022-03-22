package at.fhv.ae.backend.application.dto;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.work.Artist;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import lombok.Value;

import java.util.List;

@Value
public class DetailedReleaseDTO {

    String title;
    String medium;
    int stock;
    double price;
    //TODO: Fill in data after coaching

    public static DetailedReleaseDTO fromDomain(Release release, List<Recording> recordings, List<Artist> artists, List<Genre> genres) {
        return new DetailedReleaseDTO(release.title(), release.medium().friendlyName(), release.stock(), release.price());
    }
}
