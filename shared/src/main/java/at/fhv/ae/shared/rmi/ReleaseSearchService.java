package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ReleaseSearchService extends Remote {

    List<ReleaseSearchResultDTO> query(String title, String artist, String genre) throws RemoteException;

    DetailedReleaseRemoteDTO getDetails(UUID releaseId) throws RemoteException;
}
