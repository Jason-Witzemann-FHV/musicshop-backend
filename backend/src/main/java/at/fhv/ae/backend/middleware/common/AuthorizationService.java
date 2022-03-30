package at.fhv.ae.backend.middleware.common;

public interface AuthorizationService {

    boolean authorize(String username, String password);
}
