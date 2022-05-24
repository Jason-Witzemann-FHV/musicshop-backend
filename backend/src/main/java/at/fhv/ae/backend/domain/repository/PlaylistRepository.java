package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.release.Release;
import at.fhv.ae.backend.domain.model.user.User;

import javax.ejb.Local;

@Local
public interface PlaylistRepository {

    void notifyBoughtRelease(Release release, User user);

}
