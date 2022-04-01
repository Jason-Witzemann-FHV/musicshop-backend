package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.rmi.RemoteSession;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSessionFactoryImpl extends UnicastRemoteObject implements RemoteSessionFactory {
    private final SessionFactory sessionFactory;

    public RemoteSessionFactoryImpl(SessionFactory sessionFactory) throws RemoteException {
        super();
        this.sessionFactory = sessionFactory;
    }

    @Override
    public RemoteSession logIn(String username, String password) throws RemoteException, AuthorizationException {

        Session session = sessionFactory.logIn(username, password);

        return new RemoteSessionImpl(session);
    }
}
