package at.fhv.ae.backend.middleware;

import at.fhv.ae.backend.ServiceRegistry;
import at.fhv.ae.backend.application.ReleaseService;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class ReleaseSearchServiceImpl extends UnicastRemoteObject implements ReleaseSearchService {

    private final ReleaseService releaseService = ServiceRegistry.releaseService();

    public ReleaseSearchServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<ReleaseSearchResultDTO> query(String title, String artist, String genre) {
        return releaseService.query(title, artist, genre)
                .stream()
                .map(rel -> new ReleaseSearchResultDTO(rel.title(), rel.medium(), rel.stock(), rel.price()))
                .collect(Collectors.toList());
    }
}
