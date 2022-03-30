package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.SessionFactory;
import at.fhv.ae.shared.rmi.RemoteSession;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class RemoteSessionFactoryImpl extends UnicastRemoteObject implements RemoteSessionFactory {
    private final SessionFactory sessionFactory;

    public RemoteSessionFactoryImpl(SessionFactory sessionFactory) throws RemoteException {
        super();
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<RemoteSession> logIn(String username, String password) throws RemoteException {
        var session = sessionFactory.logIn(username, password);

        return session.map(s -> {
            try {
                return new RemoteSessionImpl(s);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
