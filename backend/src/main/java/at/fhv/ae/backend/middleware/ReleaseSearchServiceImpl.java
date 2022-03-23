package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.RecordingRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReleaseSearchServiceImpl extends UnicastRemoteObject implements ReleaseSearchService {

    private  transient ReleaseService releaseService;

    public ReleaseSearchServiceImpl(ReleaseService releaseService) throws RemoteException {
        super();
        this.releaseService = releaseService;
    }

    @Override
    public List<ReleaseSearchResultDTO> query(String title, String artist, String genre) {
        return releaseService.query(title, artist, genre)
                .stream()
                .map(rel -> new ReleaseSearchResultDTO(rel.id(), rel.title(), rel.medium(), rel.stock(), rel.price()))
                .collect(Collectors.toList());
    }

    @Override
    public DetailedReleaseRemoteDTO getDetails(UUID releaseId) throws RemoteException {
        DetailedReleaseDTO result = releaseService.detailedInformation(releaseId);
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
}
