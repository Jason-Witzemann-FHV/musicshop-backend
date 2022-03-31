package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.backend.middleware.common.CredentialsService;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import java.util.Optional;

public class SessionFactoryImpl implements SessionFactory {
    private final CredentialsService credentialsService;
    private final UserRepository userRepository;


    public SessionFactoryImpl(CredentialsService credentialsService, UserRepository userRepository) {
        this.credentialsService = credentialsService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Session> logIn(String username, String password) {
        if (!credentialsService.authorize(username, password)) {
            return Optional.empty();
        }

        Optional<User> optUser = userRepository.userById(new UserId(username));

        if (optUser.isEmpty()){
            System.out.println("Found User in AuthService, but not in Repository!");
            return Optional.empty();
        }

        User user = optUser.get();

        return Optional.of(new SessionImpl(user));
    }

}
