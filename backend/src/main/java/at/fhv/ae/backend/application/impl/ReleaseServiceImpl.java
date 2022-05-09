package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.RecordingDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.model.release.ReleaseId;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.backend.domain.model.work.Recording;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.domain.repository.WorkRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class ReleaseServiceImpl implements ReleaseSearchService {

    @EJB
    private ReleaseRepository releaseRepository;

    @EJB
    private WorkRepository workRepository;

    @Transactional
    @Override
    public List<ReleaseDTO> query(String title, String artist,String genre) {
        return this.releaseRepository.query(title, artist, Genre.byFriendlyName(genre))
                .stream()
                .map(ReleaseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<DetailedReleaseDTO> detailedInformation(UUID releaseId) {
        return releaseRepository.findById(new ReleaseId(releaseId))
                .map(release -> {
                    List<Recording> recordings = workRepository.findRecordings(release.recordingIds());

                    ArrayList<RecordingDTO> recordingDTOs = recordings
                            .stream()
                            .map(RecordingDTO::fromDomain)
                            .collect(Collectors.toCollection(ArrayList::new));

                    return DetailedReleaseDTO.fromDomain(release, recordingDTOs);
                });
    }

    @Override
    public List<Genre> knownGenres() {
        return List.of(Genre.values());
    }
}
