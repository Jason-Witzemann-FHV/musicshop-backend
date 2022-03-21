package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Optional<Release> optional = releaseRepository.findById(releaseId);
        if (optional.isEmpty()) {
            return Collections.emptyList();
        }
        Release release = optional.get();
        List<Recording> recordings = workRepository.findRecordings(release.recordingIds());
        //TODO: Implement the fields, the coaches want
        return Collections.emptyList();
    }
}
