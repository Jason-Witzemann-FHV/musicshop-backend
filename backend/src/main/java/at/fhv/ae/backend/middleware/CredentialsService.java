package at.fhv.ae.backend.middleware;

import javax.ejb.Local;

@Local
public interface CredentialsService {

    boolean authorize(String username, String password);
}
