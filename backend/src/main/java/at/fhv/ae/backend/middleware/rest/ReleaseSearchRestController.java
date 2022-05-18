package at.fhv.ae.backend.middleware.rest;

import at.fhv.ae.backend.application.ReleaseSearchService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import at.fhv.ae.backend.application.dto.DetailedReleaseDTO;
import at.fhv.ae.backend.application.dto.ReleaseDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/search")
public class ReleaseSearchRestController {

    @EJB
    private ReleaseSearchService releaseSearchService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary="Get detailed information about release")
    @APIResponses({
            @APIResponse(responseCode="200", description="Detailed information"),
            @APIResponse(responseCode="404", description="Detailed information not found"),
    })
    @APIResponseSchema(value = DetailedReleaseDTO.class, responseCode = "200")
    public Response detailedInformation(@PathParam("id") UUID releaseId) {
        return releaseSearchService.detailedInformation(releaseId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }


    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary="Search releases")
    @APIResponses({
            @APIResponse(responseCode="200", description="Releases that match the parameters")
    })
    @APIResponseSchema(value = ReleaseDTO[].class, responseCode = "200")
    public Response query(
            @QueryParam("title") String title,
            @QueryParam("artist") String artist,
            @QueryParam("genre") String genre) {

        var results = releaseSearchService.query(title, artist, genre);

        return Response.ok(results)
                .status(200)
                .build();
    }
}
