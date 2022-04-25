package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.AuthorizationException;

import javax.ejb.Remote;

@Remote
public interface RemoteSessionFactory {

    RemoteSession logIn(String username, String password) throws AuthorizationException;
}
