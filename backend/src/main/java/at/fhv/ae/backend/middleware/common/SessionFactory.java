package at.fhv.ae.backend.middleware.common;

import java.util.Optional;

public interface SessionFactory {

    Optional<Session> logIn(String username, String password);

}
