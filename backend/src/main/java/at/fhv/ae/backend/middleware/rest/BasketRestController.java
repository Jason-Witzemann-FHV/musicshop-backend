package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.application.SellService;
import at.fhv.ae.backend.application.dto.BasketItemDisplayDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import at.fhv.ae.backend.application.exceptions.OutOfStockException;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.middleware.rest.auth.AuthenticatedUser;
import at.fhv.ae.backend.middleware.rest.auth.Secured;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
    @Operation(summary="Display basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Item in basket"),
            @APIResponse(responseCode="204", description="No items in basket"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
    @APIResponseSchema(value = BasketItemDisplayDTO[].class, responseCode = "200")
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
    @Operation(summary="Add item once to basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Item once added"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
    public Response addItemOnceToBasket(@PathParam("id") UUID releaseId) {
        basketService.addItemToBasket(user.userId().toString(), releaseId, 1);
        // We should really add custom exceptions
        return Response.ok("Release " + releaseId + " added once").build();
    }

    @PUT
    @Path("/add/{id}/{amount}")
    @Secured(Permission.BUY_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary="Add item to basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Item added"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
    public Response addItemToBasket(@PathParam("id") UUID releaseId,
                                    @PathParam("amount") int amount) {
        basketService.addItemToBasket(user.userId().toString(), releaseId, amount);
        return Response.ok("Release " + releaseId + " added " + amount +" times").build();
    }

    @DELETE
    @Path("/remove/{id}")
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary="Remove item from basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Item removed"),
            @APIResponse(responseCode="304", description="No item removed"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
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
    @Operation(summary="Clear basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Basket cleared"),
            @APIResponse(responseCode="304", description="No item removed, because basket was empty"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
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
    @Operation(summary="Sell basket")
    @APIResponses({
            @APIResponse(responseCode="200", description="Item sold"),
            @APIResponse(responseCode="304", description="No item sold"),
            @APIResponse(responseCode="401", description="Unauthorized")
    })
    public Response sellBasket(@QueryParam("customer")ObjectId customerId){
        try {
            sellService.sellItemsInBasket(user.userId().toString(),customerId);
            return Response.ok().status(Response.Status.ACCEPTED).build();
        } catch (OutOfStockException e) {
            return Response.notModified().build();
        }
    }
}
