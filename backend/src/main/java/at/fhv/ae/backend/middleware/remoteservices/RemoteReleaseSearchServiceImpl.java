package at.fhv.ae.backend.middleware.remoteservices;

import at.fhv.ae.backend.application.ReleaseSearchService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.domain.model.work.Genre;
import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.RecordingRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.services.RemoteReleaseSearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
@NoArgsConstructor
@AllArgsConstructor
public class RemoteReleaseSearchServiceImpl implements RemoteReleaseSearchService {

    @EJB
    private ReleaseSearchService releaseSearchService;

    @Override
    public List<ReleaseSearchResultDTO> query(String title, String artist, String genre) {
        return releaseSearchService.query(title, artist, genre)
                .stream()
                .map(rel -> new ReleaseSearchResultDTO(rel.id(), rel.title(), rel.medium(), rel.stock(), rel.price()))
                .collect(Collectors.toList());
    }

    @Override
    public DetailedReleaseRemoteDTO getDetails(UUID releaseId) {
        DetailedReleaseDTO result = releaseSearchService.detailedInformation(releaseId).orElseThrow(IllegalArgumentException::new);
        return new DetailedReleaseRemoteDTO(
                result.title(),
                result.price(),
                result.stock(),
                result.medium(),
                result.recordings().stream().map(r -> new RecordingRemoteDTO(
                        r.title(),
                        r.artists(),
                        r.genres(),
                        r.year(),
                        r.duration()
                )).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public List<String> knownGenres() {
        return releaseSearchService.knownGenres()
                .stream()
                .map(Genre::friendlyName)
                .collect(Collectors.toList());
    }
}
