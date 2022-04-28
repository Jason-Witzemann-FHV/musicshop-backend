package at.fhv.ae.backend.middleware.rest.auth;

import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.repository.UserRepository;
import at.fhv.ae.backend.middleware.TokenRepository;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Inject
    @AuthenticatedUser
    private Event<String> userAuthenticatedEvent;

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    private TokenRepository tokenRepository;

    @EJB
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        // Get the Permission to authorize against
        Method method = resourceInfo.getResourceMethod();
        Permission permission = method.getAnnotation(Secured.class).value();

        try {
            // Validate the token, throws error if not authenticated
            validateToken(token);

            // Fire UserAuthenticatedEvent to enable Injection of User
            String username = tokenRepository.userIdOfToken(token).orElseThrow(() -> new IllegalStateException("At that point, token must have been already validated."));

            // authorize the user
            authorize(username, permission);

            userAuthenticatedEvent.fire(username);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        tokenRepository.userIdOfToken(token).orElseThrow();
    }

    private void authorize(String username, Permission permission) throws Exception {
        User user = userRepository.userById(new UserId(username)).orElseThrow();

        if(!user.hasPermission(permission)) {
            throw new IllegalStateException("authorization rejected");
        }
    }
}