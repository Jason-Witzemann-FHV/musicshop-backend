package at.fhv.ae.backend.middleware;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface TokenRepository {

    void setToken(String userId, String token);

    Optional<String> userIdOfToken(String token);

}
