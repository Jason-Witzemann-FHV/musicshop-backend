package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.domain.model.user.Permission;
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
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unsecuredMethodWithParameter(@PathParam("id") Long id) {
        return Response.ok().build();
    }

    @GET
    @Path("/unsecured")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unsecuredMethod() {
        return Response.ok().build();
    }

    @GET
    @Path("/secured/buy")
    @Secured(Permission.BUY_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response securedMethodBuy() {
        String userId = user.userId().toString();
        return Response.ok().entity(userId).build();
    }

    @GET
    @Path("/secured/search")
    @Secured(Permission.SEARCH_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response securedMethodSearch() {
        String userId = user.userId().toString();
        return Response.ok().entity(userId).build();
    }

}
