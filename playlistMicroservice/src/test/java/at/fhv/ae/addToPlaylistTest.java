package at.fhv.ae;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class addToPlaylistTest {

    @Test
    public void given_playlistId_ReleaseId_when_PutMethod_then_receiveStatusCode200() {
        String playlistId = "tobi123";
        String releaseId = "0f0956ec-c1d4-4258-b410-d2212a6d38cf";
        given()
                .pathParam("playlistId", playlistId)
                .pathParam("releaseId", releaseId)
                .when().put("/playlist/add/{playlistId}/{releaseId}")
                .then()
                .statusCode(200)
                .body(is("Release "+ releaseId + " added to " + playlistId));

    }
}
