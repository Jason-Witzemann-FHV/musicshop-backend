package at.fhv.ae.backend.infrastructure.rest;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.repository.PlaylistRepository;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Stateless
public class RestPlaylistRepository implements PlaylistRepository {

    @Override
    public void notifyBoughtRelease(Release release, User user) {
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8083/playlist/add/" + user.name() + "/" + release.releaseId().id().toString()).request("*/*").put(null);
        client.close();
    }
}
