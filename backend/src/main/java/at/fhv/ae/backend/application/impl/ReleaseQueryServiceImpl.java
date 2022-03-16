package at.fhv.ae.backend.application.impl;

import at.fhv.ae.backend.application.ReleaseQueryService;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.domain.repository.ReleaseRepository;
import at.fhv.ae.backend.infrastructure.HibernateReleaseRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ReleaseQueryServiceImpl implements ReleaseQueryService {

    private final ReleaseRepository releaseRepository;

    @Override
    public List<ReleaseDTO> query(String title, String artist) {
        return this.releaseRepository.query(title, artist)
                .stream()
                .map(ReleaseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
