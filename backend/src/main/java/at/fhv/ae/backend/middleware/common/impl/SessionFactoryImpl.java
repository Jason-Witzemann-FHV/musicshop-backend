package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.backend.middleware.common.CredentialsService;
import at.fhv.ae.backend.middleware.common.Session;
import at.fhv.ae.backend.middleware.common.SessionFactory;
import at.fhv.ae.shared.AuthorizationException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Stateful
public class SessionFactoryImpl implements SessionFactory {
    @EJB
    private CredentialsService credentialsService;

    @EJB
    private UserRepository userRepository;

    private final Set<Session> activeSessions = new HashSet<>();


//    public SessionFactoryImpl(CredentialsService credentialsService, UserRepository userRepository) {
//        this.credentialsService = credentialsService;
//        this.userRepository = userRepository;
//        this.activeSessions
//    }

    @Override
    public Session logIn(String username, String password) throws AuthorizationException {

        if (!credentialsService.authorize(username, password)) {
            throw new AuthorizationException("Bad Credentials");
        }

        Optional<User> optUser = userRepository.userById(new UserId(username));

        User user = optUser.orElseThrow(() -> new AuthorizationException("Not found in User/Role/Permission DB"));

        var session = new SessionImpl();
        session.setUserId(user);
        return session;
    }

}
