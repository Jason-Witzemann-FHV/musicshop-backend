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
        return releaseSearchService.detailedInformation(releaseId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "*")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }


    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(
            @QueryParam("title") String title,
            @QueryParam("artist") String artist,
            @QueryParam("genre") String genre) {

        var results = releaseSearchService.query(title, artist, genre);

        return Response.ok(results)
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }


}
