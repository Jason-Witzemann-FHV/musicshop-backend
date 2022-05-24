package at.fhv.ae.download;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/download")
public class DownloadRestController {

    @Path("/{song}")
    @GET
    @Produces("audio/mpeg")
    public Response download(@PathParam("song") String song) {
        return Song.find(UUID.fromString(song))
                .map(s -> Response
                        .ok(s.bytes(), s.contentType())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + s.downloadName())
                )
                .orElse(Response.status(404))
                .build();
    }
}