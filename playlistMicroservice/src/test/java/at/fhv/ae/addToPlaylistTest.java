package at.fhv.ae;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class addToPlaylistTest {

    @Test
    public void given_playlistId_ReleaseId_when_PutMethod_then_receiveStatusCode200() {
        String playlistId = "tobi123";
        String releaseId = "1";
        given()
                .pathParam("playlistId", playlistId)
                .pathParam("releaseId", releaseId)
                .when().put("/playlist/add/{playlistId}/{releaseId}")
                .then()
                .statusCode(200)
                .body(is("Release "+ releaseId + " added to " + playlistId));

    }
}
