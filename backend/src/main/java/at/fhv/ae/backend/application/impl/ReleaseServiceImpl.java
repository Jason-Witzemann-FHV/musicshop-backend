package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.RecordingDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
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
    public DetailedReleaseDTO detailedInformation(UUID releaseId) throws IllegalArgumentException  {
        // Getting domain objects
        Release release = releaseRepository.findById(new ReleaseId(releaseId)).orElseThrow(IllegalArgumentException::new);
        List<Recording> recordings = workRepository.findRecordings(release.recordingIds());

        return DetailedReleaseDTO.fromDomain(
                release,
                recordings.stream()
                        .map(RecordingDTO::fromDomain)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }
}
