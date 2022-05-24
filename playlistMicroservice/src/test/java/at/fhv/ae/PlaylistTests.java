package at.fhv.ae;

import at.fhv.ae.playlist.domain.Playlist;
import at.fhv.ae.playlist.domain.Song;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PlaylistTests {

    @Test
    void given_userId_releaseId_when_putMethod_then_receiveStatusCode200() {
        String userId = "testuser";
        String releaseId = "12345678-ffff-1234-abcd-000000000001";
        given()
                .pathParam("userId", userId)
                .pathParam("releaseId", releaseId)
                .when()
                .put("/playlist/add/{userId}/{releaseId}")
                .then()
                .statusCode(200);
    }

    @Test
    void given_userId_wrongReleaseId_when_putMethod_then_receiveStatusCode304() {
        String userId = "testuser";
        String releaseId = "12345678-ffff-1234-abcd-999999999999";
        given()
                .pathParam("userId", userId)
                .pathParam("releaseId", releaseId)
                .when()
                .put("/playlist/add/{userId}/{releaseId}")
                .then()
                .statusCode(400);
    }


    @Test
    void given_userId_when_releases_in_playlist_and_getMethod_then_receiveStatusCode200() {

        QuarkusTransaction.begin();
        Playlist.deleteAll();
        var song =  Song.streamAll()
                .filter(s -> ((Song) s).getSongId().equals(UUID.fromString("12345678-ffff-1234-abcd-000000000001")))
                .map(Song.class::cast)
                .findAny()
                .get();

        var playlist = new Playlist("testuser");
        playlist.addSong(song);
        playlist.persist();
        QuarkusTransaction.commit();

        given()
                .when()
                .get("/playlist")
                .then()
                .statusCode(200);
    }

    @Test
    void given_userId_when_no_releases_in_playlist_and_getMethod_then_receiveStatusCode204() {
        QuarkusTransaction.begin();
        Playlist.deleteById("testuser");
        QuarkusTransaction.commit();
        given()
                .when()
                .get("/playlist")
                .then()
                .statusCode(204);

    }
}
