package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReleaseSearchService extends Remote {

    List<ReleaseSearchResultDTO> query(String title, String artist, String genre) throws RemoteException;

    // ReleaseDetailsDTO getDetails(String releaseId);
}
