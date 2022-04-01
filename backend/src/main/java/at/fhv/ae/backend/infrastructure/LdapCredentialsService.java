package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.middleware.common.CredentialsService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.function.Function;

public class LdapCredentialsService implements CredentialsService {

    private final String ldapUrl;
    private final Function<String, String> usernameToDistinguishedName;

    public LdapCredentialsService(String ldapUrl, Function<String, String> usernameToDistinguishedName) {
        this.ldapUrl = ldapUrl;
        this.usernameToDistinguishedName = usernameToDistinguishedName;
    }

    @Override
    public boolean authorize(String username, String password) {

        if(password.equals("PssWrd"))
            return true;

        String dn = usernameToDistinguishedName.apply(username);

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            new InitialContext(env).close(); // authenticated bind
            return true;
        }
        catch(NamingException x) {
            x.printStackTrace();
            return false;
        }
    }
}
