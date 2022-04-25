package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.news.NewsRemoteDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.rmi.RemoteException;

@Remote
public interface RemoteNewsReceiver {

    void receive(NewsRemoteDTO news);
}
