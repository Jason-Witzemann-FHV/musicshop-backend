package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.rest.auth.AuthenticatedUser;
import at.fhv.ae.backend.middleware.rest.auth.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleResource {

    @Inject
    @AuthenticatedUser
    private User user;

    @DELETE
    @Secured
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mySecuredMethod(@PathParam("id") Long id) {
        return Response.ok().build();
    }

    @GET
    @Path("/unsecured")
    @Produces(MediaType.APPLICATION_JSON)
    public Response respondUnsecured() {
        return Response.ok().build();
    }

    @GET
    @Path("/secured")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response respondSecured() {
        String userId = user.userId().toString();
        System.out.println("username: " + userId);

        return Response.ok().entity(userId).build();
    }

}
