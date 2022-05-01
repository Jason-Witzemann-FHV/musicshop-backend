package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.rest.auth.AuthenticatedUser;
import at.fhv.ae.backend.middleware.rest.auth.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/basket")
public class BasketRestController {

    @Inject
    @AuthenticatedUser
    private User user;

    @EJB
    private BasketService basketService;

    @GET
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response displayBasket() {
        var results = basketService.itemsInBasket(user.userId().toString());

        if(!results.isEmpty()) {
            return Response.ok(results).build();
        } else {
            return Response.noContent().build();
        }
    }

    // body not needed here
    @PUT
    @Path("/add/{id}")
    @Secured(Permission.BUY_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemOnceToBasket(@PathParam("id") UUID releaseId) {
        basketService.addItemToBasket(user.userId().toString(), releaseId, 1);
        // We should really add custom exceptions
        return Response.ok("Release " + releaseId + " added once").build();
    }

    @PUT
    @Path("/add/{id}/{amount}")
    @Secured(Permission.BUY_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToBasket(@PathParam("id") UUID releaseId,
                                    @PathParam("amount") int amount) {
        basketService.addItemToBasket(user.userId().toString(), releaseId, amount);
        return Response.ok("Release " + releaseId + " added " + amount +" times").build();
    }

    @DELETE
    @Path("/remove/{id}")
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItemFromBasket(@PathParam("id") UUID releaseId) {
        try {
            basketService.removeItemFromBasket(user.userId().toString(), releaseId);
            return Response.ok().status(Response.Status.ACCEPTED).build();
        } catch(Exception e){
            return Response.notModified().build();
        }
    }
}
