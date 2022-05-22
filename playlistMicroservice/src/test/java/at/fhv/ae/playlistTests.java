package at.fhv.ae;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class playlistTests {

   /* @Test
    public void given_userId_releaseId_when_putMethod_then_receiveStatusCode200() {
        String userId = "tobi123";
        String releaseId = "0f0956ec-c1d4-4258-b410-d2212a6d38cf";
        given()
                .pathParam("userId", userId)
                .pathParam("releaseId", releaseId)
                .when().put("/playlist/add/{userId}/{releaseId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void given_userId_wrongReleaseId_when_putMethod_then_receiveStatusCode304() {
        String userId = "tobi123";
        String releaseId = "0f0956ec-c1d4-4258-b410-d2212a5d38cf";
        given()
                .pathParam("userId", userId)
                .pathParam("releaseId", releaseId)
                .when().put("/playlist/add/{userId}/{releaseId}")
                .then()
                .statusCode(304);
    }


    @Test
    public void given_userId_when_releases_in_playlist_and_getMethod_then_receiveStatusCode200() {
        String userId = "tobi123";
        given()
                .pathParam("userId", userId)
                .when().get("/playlist/{userId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void given_userId_when_no_releases_in_playlist_and_getMethod_then_receiveStatusCode204() {
        String userId = "userWithoutReleases";
        given()
                .pathParam("userId", userId)
                .when().get("/playlist/{userId}")
                .then()
                .statusCode(204);

    } */
}
