package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.ReleaseSearchService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/search")
public class ReleaseSearchRestController {

    @EJB
    private ReleaseSearchService releaseSearchService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detailedInformation(@PathParam("id") UUID releaseId) {

        var release = releaseSearchService.detailedInformation(releaseId);

        return Response.ok().entity(release).build();
    }



}
