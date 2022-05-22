package at.fhv.ae.backend.middleware.rest.auth;

import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.middleware.CredentialsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@OpenAPIDefinition(info = @Info(
        title = "OpenAPIDefinition for Soundkraut",
        version = "1",
        description = "OpenAPI for our REST services"))

@Path("/authentication")
public class AuthenticationEndpoint {

    public static final Key KEY = Keys.hmacShaKeyFor("asdfaefasdascxaefaergdfbsasdfasdfcvydaeeafasdafewfasdfcvydaefsdadfdfscvdsfsdfasdfadsfafefwf".getBytes(StandardCharsets.UTF_8));

    @EJB
    private CredentialsService credentialsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary="Authenticate user")
    @APIResponses({
            @APIResponse(responseCode="200", description="User authenticated"),
            @APIResponse(responseCode="403", description="Wrong credentials")
    })
    @APIResponseSchema(value = String.class, responseCode = "200")
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

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("my_little_server")
                .setExpiration(Date.from(LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.UTC))) // utc offest = +2 hours => token is 5 hours valid
                .signWith(KEY)
                .compact();
    }
}