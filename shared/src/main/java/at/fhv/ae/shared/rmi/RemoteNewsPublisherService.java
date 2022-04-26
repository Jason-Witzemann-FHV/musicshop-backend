package at.fhv.ae.shared.rmi;


import javax.ejb.Remote;

@Remote
public interface RemoteNewsPublisherService {

    void addReceiver(RemoteNewsReceiver receiver);

    void setUserId(String id);
}
