package at.fhv.ae.backend.middleware.common;

public interface CredentialsService {

    boolean authorize(String username, String password);
}
