package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;

import javax.ejb.Remote;
import java.util.List;
import java.util.UUID;

@Remote
public interface RemoteReleaseSearchService {

    List<ReleaseSearchResultDTO> query(String title, String artist, String genre);

    DetailedReleaseRemoteDTO getDetails(UUID releaseId);

    List<String> knownGenres();
}
