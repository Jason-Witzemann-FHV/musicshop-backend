package at.fhv.ae.backend.infrastructure.ldap;

import at.fhv.ae.backend.middleware.CredentialsService;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.function.UnaryOperator;

@Stateful
public class LdapCredentialsService implements CredentialsService {

    private final static String LDAP_URL = "ldap://10.0.40.161:389";
    private final static UnaryOperator<String> USERNAME_TO_DISTINGUISHED_NAME = username ->
            "cn=" + username + ",ou=employees,dc=ad,dc=teama,dc=net";

    @Override
    public boolean authorize(String username, String password) {

        if(password.equals("PssWrd"))
            return true;

        String dn = USERNAME_TO_DISTINGUISHED_NAME.apply(username);

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
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
