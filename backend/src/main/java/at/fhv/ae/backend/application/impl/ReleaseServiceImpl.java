package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Artist;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;

    private final WorkRepository workRepository;

    @Override
    public List<ReleaseDTO> query(String title, String artist,String genre) {
        return this.releaseRepository.query(title, artist,genre)
                .stream()
                .map(ReleaseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<DetailedReleaseDTO> detailedInformation(ReleaseId releaseId)  {
        // Getting domain objects
        Optional<Release> optional = releaseRepository.findById(releaseId);
        if (optional.isEmpty()) {
            return Collections.emptyList();
        }
        Release release = optional.get();
        List<Recording> recordings = workRepository.findRecordings(release.recordingIds());
        Set<Artist> artists = new HashSet<>();
        for (Recording r: recordings) {
            artists.addAll(r.artists());
        }
        Set<Genre> genres = new HashSet<>();
        for (Recording r: recordings) {
            genres.addAll(r.genres());
        }
        artists.forEach(Artist::name);
        genres.forEach(Genre::friendlyName);

        //TODO: Implement the fields, the coaches want
        return Collections.emptyList();
    }
}
