package at.fhv.ae.backend.middleware.common;

import at.fhv.ae.shared.AuthorizationException;

import javax.ejb.Local;

@Local
public interface SessionFactory {

    Session logIn(String username, String password) throws AuthorizationException;
}
