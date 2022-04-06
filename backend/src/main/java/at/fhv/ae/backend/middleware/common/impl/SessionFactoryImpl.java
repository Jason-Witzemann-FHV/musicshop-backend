package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.backend.middleware.common.CredentialsService;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import at.fhv.ae.shared.AuthorizationException;

import java.util.Optional;

public class SessionFactoryImpl implements SessionFactory {
    private final CredentialsService credentialsService;
    private final UserRepository userRepository;


    public SessionFactoryImpl(CredentialsService credentialsService, UserRepository userRepository) {
        this.credentialsService = credentialsService;
        this.userRepository = userRepository;
    }

    @Override
    public Session logIn(String username, String password) throws AuthorizationException {

        if (!credentialsService.authorize(username, password)) {
            throw new AuthorizationException("Bad Credentials");
        }

        Optional<User> optUser = userRepository.userById(new UserId(username));

        if (optUser.isEmpty()) {
            throw new AuthorizationException("Not found in User/Role/Permission DB");
        }

        User user = optUser.get();

        return new SessionImpl(user);
    }

}
