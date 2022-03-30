package at.fhv.ae.backend.domain.repository;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;

import java.util.Optional;


public interface UserRepository {

    Optional<User> userById(UserId userId);

}
