package at.fhv.ae.backend.middleware.rmi.facade;

import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.shared.rmi.RemoteSession;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteSessionImpl extends UnicastRemoteObject implements RemoteSession {
    private final Session session;

    public RemoteSessionImpl(Session session) throws RemoteException {
        super();
        this.session = session;
    }
}
