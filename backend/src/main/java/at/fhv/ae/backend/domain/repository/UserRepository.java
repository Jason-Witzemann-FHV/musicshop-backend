package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Optional;

@Local
public interface UserRepository {

    Optional<User> userById(UserId userId);

}
