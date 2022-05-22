package at.fhv.ae.playlist;

import at.fhv.ae.playlist.domain.Song;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
@Startup
public class DataGenerator {

    private boolean doRunDataGenerator = false;

    @PostConstruct
    public void runDataGenerator() {

        if (!doRunDataGenerator) {
            return;
        }

        QuarkusTransaction.begin();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000001"), "I beg to differ", "Billy Talent", 201).persistAndFlush();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000002"), "Red Flag", "Billy Talent", 123).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000003"), "Maniac", "Eric Speed", 421).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000004"), "Give That Wolf A Banana", "Subwoolfer", 163).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000005"), "Stitch Me Up", "Set If Off", 112).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000006"), "Why Worry", "Set It Off", 112).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000007"), "Ghost in the Machine", "Trivecta", 112).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000008"), "Anthem", "zebrahead", 112).persist();
        new Song(UUID.fromString("12345678-ffff-1234-abcd-000000000009"), "Halo", "LUM!X", 112).persist();
        QuarkusTransaction.commit();
    }


}
