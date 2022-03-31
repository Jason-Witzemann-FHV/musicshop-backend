package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.middleware.common.AuthorizationService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.function.Function;

public class LdapAuthorizationService implements AuthorizationService {

    private final Function<String, String> usernameToDistinguishedName;

    public LdapAuthorizationService(Function<String, String> usernameToDistinguishedName) {
        this.usernameToDistinguishedName = usernameToDistinguishedName;
    }

    @Override
    public boolean authorize(String username, String password) {

        Properties env = new Properties();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, usernameToDistinguishedName.apply(username));
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            Context ctx = new InitialContext(env); // authenticated bind
            ctx.close();
            return true;
        }
        catch(NamingException ex) {
            return false;
        }
    }
}
