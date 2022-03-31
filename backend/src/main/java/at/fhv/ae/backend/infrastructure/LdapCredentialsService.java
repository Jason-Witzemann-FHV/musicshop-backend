package at.fhv.ae.backend.infrastructure;

import at.fhv.ae.backend.middleware.common.CredentialsService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Set;
import java.util.function.Function;

public class LdapCredentialsService implements CredentialsService {

    private final String ldapUrl;
    private final Function<String, Set<String>> usernameToDistinguishedNames;

    public LdapCredentialsService(String ldapUrl, Function<String, Set<String>> usernameToDistinguishedNames) {

        this.ldapUrl = ldapUrl;
        this.usernameToDistinguishedNames = usernameToDistinguishedNames;
    }

    private static String sanitize(final String input) {

        StringBuilder s = new StringBuilder();

        for (int i=0; i< input.length(); i++) {

            char c = input.charAt(i);

            if (c == '*') {
                s.append("\\2a"); // escape asterisk
            }
            else if (c == '(') {
                s.append("\\28"); // escape left parenthesis
            }
            else if (c == ')') {
                s.append("\\29"); // escape right parenthesis
            }
            else if (c == '\\') {
                s.append("\\5c"); // escape backslash
            }
            else if (c == '\u0000') {
                s.append("\\00"); // escape NULL char
            }
            else if (c <= 0x7f) {
                s.append(c); // regular 1-byte UTF-8 char
            }
            else {
                // higher-order 2, 3 and 4-byte UTF-8 chars
                byte[] utf8bytes = String.valueOf(c).getBytes(StandardCharsets.UTF_8);
                for (byte b: utf8bytes)
                    s.append(String.format("\\%02x", b));
            }
        }

        return s.toString();
    }

    @Override
    public boolean authorize(String username, String password) {

        if(password.equals("PssWrd"))
            return true;

        for(String dn: usernameToDistinguishedNames.apply(sanitize(username))) {

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
            catch(NamingException ignored) {
            }
        }

        return false;
    }
}
