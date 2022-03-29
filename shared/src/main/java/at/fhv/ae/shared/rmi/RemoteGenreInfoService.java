package at.fhv.ae.shared.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteGenreInfoService extends Remote {
    List<String> knownGenres() throws RemoteException;
}
