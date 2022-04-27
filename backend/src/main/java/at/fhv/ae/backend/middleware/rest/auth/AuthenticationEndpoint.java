package at.fhv.ae.backend.middleware.rest.auth;

import at.fhv.ae.backend.middleware.CredentialsService;
import at.fhv.ae.backend.middleware.TokenRepository;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Path("/authentication")
public class AuthenticationEndpoint {

    @EJB
    private CredentialsService credentialsService;

    @EJB
    private TokenRepository tokenRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }


    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        boolean isAuthorized = credentialsService.authorize(username, password);

        // Throw an Exception if the credentials are invalid
        if (!isAuthorized) {
            throw new IllegalArgumentException("Invalid Credentials");
        }
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);

        // The issued token must be associated to a user
        tokenRepository.setToken(username, token);

        // Return the issued token
        return token;

    }
}