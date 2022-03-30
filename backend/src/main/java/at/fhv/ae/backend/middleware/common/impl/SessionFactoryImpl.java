package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.backend.middleware.common.AuthorizationService;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import java.util.Optional;

public class SessionFactoryImpl implements SessionFactory {
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;


    public SessionFactoryImpl(AuthorizationService authorizationService, UserRepository userRepository) {
        this.authorizationService = authorizationService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Session> logIn(String username, String password) {
        if (!authorizationService.authorize(username, password)) {
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
