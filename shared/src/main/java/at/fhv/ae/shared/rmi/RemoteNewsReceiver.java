package at.fhv.ae.shared.rmi;

import at.fhv.ae.shared.dto.news.NewsRemoteDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.io.Serializable;
import java.rmi.RemoteException;

@Remote
public interface RemoteNewsReceiver extends Serializable {

    void receive(NewsRemoteDTO news);
}
