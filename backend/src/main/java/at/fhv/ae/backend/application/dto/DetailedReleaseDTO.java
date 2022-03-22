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
    List<String> artists;
    List<String> recordings;
    List<String> genres;
    String medium;

    public static DetailedReleaseDTO fromDomain(Release release, List<Recording> recordings, Set<Artist> artists, Set<Genre> genres) {
        return new DetailedReleaseDTO(
                release.title(),
                release.price(),
                release.stock(),
                artists.stream().map(Artist::name).collect(Collectors.toList()),
                recordings.stream().map(Recording::title).collect(Collectors.toList()),
                genres.stream().map(Genre::friendlyName).collect(Collectors.toList()),
                release.medium().friendlyName()
        );
    }
}
