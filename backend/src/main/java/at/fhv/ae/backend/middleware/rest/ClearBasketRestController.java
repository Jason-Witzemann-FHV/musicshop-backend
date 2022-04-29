package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.BasketService;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.middleware.rest.auth.AuthenticatedUser;
import at.fhv.ae.backend.middleware.rest.auth.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClearBasketRestController {

    @Inject
    @AuthenticatedUser
    private User user;

    @EJB
    private BasketService basketService;


    @DELETE
    @Path("/clearBasket/{UserId}")
    @Secured(Permission.SELL_RELEASES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearBasket(@PathParam("UserId")UserId userId) {
        try {
            basketService.clearBasket(user.userId().toString());
            return Response.ok().status(Response.Status.ACCEPTED).build();
        } catch(Exception e){
            return Response.notModified().build();
        }
    }
}
