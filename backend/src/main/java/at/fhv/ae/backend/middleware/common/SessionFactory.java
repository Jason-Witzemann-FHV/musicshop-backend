package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.shared.AuthorizationException;

public interface SessionFactory {

    Session logIn(String username, String password) throws AuthorizationException;
}
