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
    public DetailedReleaseDTO detailedInformation(ReleaseId releaseId) throws IllegalArgumentException  {
        // Getting domain objects
        Release release = releaseRepository.findById(releaseId).orElseThrow(IllegalArgumentException::new);
        List<Recording> recordings = workRepository.findRecordings(release.recordingIds());

        // Unique Artists and Genres
        Set<Artist> artists = recordings.stream().flatMap(recording -> recording.artists().stream()).collect(Collectors.toSet());
        Set<Genre> genres = recordings.stream().flatMap(recording -> recording.genres().stream()).collect(Collectors.toSet());

        return DetailedReleaseDTO.fromDomain(release, recordings, artists, genres);
    }
}
