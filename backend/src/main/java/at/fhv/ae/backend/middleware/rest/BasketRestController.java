package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.rest.auth.AuthenticatedUser;
import at.fhv.ae.backend.middleware.rest.auth.Secured;
import org.bson.types.ObjectId;

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

    @EJB
    private SellService sellService;

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

    @DELETE
    @Path("/clear")
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearBasket() {
        try {
            basketService.clearBasket(user.userId().toString());
            return Response.ok().status(Response.Status.ACCEPTED).build();
        } catch(Exception e){
            return Response.notModified().build();
        }
    }

    @POST
    @Path("/sell")
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellBasket(@QueryParam("customer")ObjectId customerId){
        try {
            sellService.sellItemsInBasket(user.userId().toString(),customerId);
            return Response.ok().status(Response.Status.ACCEPTED).build();
        } catch (OutOfStockException e) {
            return Response.notModified().build();
        }
    }
}
