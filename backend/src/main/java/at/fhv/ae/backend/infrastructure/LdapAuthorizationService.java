package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.middleware.common.AuthorizationService;

public class LdapAuthorizationService implements AuthorizationService {
    //Todo
    @Override
    public boolean authorize(String username, String password) {
        return true;
    }
}
