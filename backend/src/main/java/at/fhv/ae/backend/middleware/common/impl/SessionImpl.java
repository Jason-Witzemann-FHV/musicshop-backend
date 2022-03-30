package at.fhv.ae.backend.middleware.common.impl;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.common.Session;

public class SessionImpl implements Session {
    private final User user;

    public SessionImpl(User user) {
        this.user = user;
    }
}
