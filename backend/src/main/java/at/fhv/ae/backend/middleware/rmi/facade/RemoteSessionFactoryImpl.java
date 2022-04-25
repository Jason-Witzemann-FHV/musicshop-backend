package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.rmi.RemoteSession;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateless
public class RemoteSessionFactoryImpl implements RemoteSessionFactory {

    @EJB
    private SessionFactory sessionFactory;

    public RemoteSessionFactoryImpl(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    public RemoteSessionFactoryImpl() {

    }

    @Override
    public RemoteSession logIn(String username, String password) throws AuthorizationException {

        Session session = sessionFactory.logIn(username, password);

        RemoteSessionImpl remote = new RemoteSessionImpl();
        remote.setSession(session);
        return remote;
    }
}
