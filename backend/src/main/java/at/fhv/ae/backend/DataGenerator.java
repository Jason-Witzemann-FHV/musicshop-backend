package at.fhv.ae.backend;

import at.fhv.ae.backend.domain.model.release.*;
import at.fhv.ae.backend.domain.model.user.Permission;
import at.fhv.ae.backend.domain.model.user.Role;
import at.fhv.ae.backend.domain.model.user.User;
import at.fhv.ae.backend.domain.model.user.UserId;
import at.fhv.ae.backend.domain.model.work.*;

import javax.persistence.Persistence;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGenerator {

    private static List<Work> works = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Recording> recordings = new ArrayList<>();
    private static List<Release> releases = new ArrayList<>();
    private static List<Label> labels = new ArrayList<>();
    private static List<Supplier> suppliers = new ArrayList<>();
    private static List<User> users;
    private static Random random = new Random();

    public static void main(String[] args) {
        deleteOldData();
        generateWorkAggregateData();
        generateReleaseAggregateData();
        prepareUsers();
        persistGeneratedData();
    }

    private static void prepareUsers() {

        var adminRoles = Set.of(new Role("admin", Set.of(Permission.values())));

        var admins = Stream.of(
                "ago8927", "jwi6503", "nsu3146", "tku8427", "tfi7196", "mbr6504", "jhe6245", "tf-test")
                .map(id -> new User(new UserId(id), adminRoles));

        var customerRoles = Set.of(new Role("customer", Set.of(Permission.SEARCH_RELEASES)));

        var customers = Stream.of(new User(new UserId("max"), customerRoles));

        users = Stream.concat(admins, customers).collect(Collectors.toList());
    }

    private static void generateWorkAggregateData() {
        song("Never gonna give you up", "Rick Astley", Genre.POP);

        song("Last friday night", "Katy Perry", Genre.POP);
        song("I kissed a girl","Katy Perry", Genre.POP);
        song("Roar","Katy Perry", Genre.POP);
        song("The one that got away","Katy Perry", Genre.POP);

        song("Numb", "Linkin Park", Genre.ROCK);
        song("In the End", "Linkin Park", Genre.ROCK);
        song("Faint", "Linkin Park", Genre.ROCK, Genre.METAL);
        song("Good Goodbye", "Linkin Park", Genre.ROCK);
        song("Heavy", "Linkin Park", Genre.ROCK);
        song("Nobody can save me", "Linkin Park", Genre.ROCK);
        song("Papercut", "Linkin Park", Genre.ROCK);
        song("Nobody's listening", "Linkin Park", Genre.ROCK);
        song("Castle of Glass", "Linkin Park", Genre.EDM, Genre.POP);
        song("Burn it down", "Linkin Park", Genre.ROCK);
        song("Lost in the Echo", "Linkin Park", Genre.ROCK);

        song("Give it all", "Rise against", Genre.ROCK);
        song("The Violence", "Rise against", Genre.ROCK);
        song("Satellite", "Rise against", Genre.ROCK);
        song("Hero of War", "Rise against", Genre.RAP);

        song("Sunrise", "Our Last Night", Genre.METAL, Genre.ROCK);
        song("Same old War", "Our Last Night", Genre.METAL);
        song("1-800-273-8255", "Our Last Night", Genre.METAL);
        song("Bullet", "Hollywood Undead", Genre.ACOUSTIC);

        song("Army of the Night", "Powerwolf", Genre.METAL);
        song("Higher than Heaven", "Powerwolf", Genre.METAL);
        song("Dead until dark", "Powerwolf", Genre.METAL);
        song("Blessed & Possessed", "Powerwolf", Genre.METAL);
        song("Mirror, Mirror", "Blind Guardien", Genre.METAL);

        song("Atemlos durch die Nacht", "Helene Fischer", Genre.SCHLAGER, Genre.POP);
        song("Null auf 100", "Helene Fischer", Genre.SCHLAGER, Genre.POP);
        song("Herzbeben", "Helene Fischer", Genre.SCHLAGER, Genre.POP);
        song("Egal", "Michael Wendler", Genre.SCHLAGER);
        song("Die FHV ist toll", "Jason Witzemann", Genre.SCHLAGER);

        song("Bilder im Kopf", "Sido", Genre.RAP, Genre.HIP_HOP);
        song("Astronaut", "Sido, Andreas Bourani", Genre.RAP, Genre.HIP_HOP);
        song("Willst du", "Alligatoah", Genre.RAP);
        song("Trostpreis", "Alligatoah", Genre.RAP);
        song("Radioactive", "Imagine Dragons", Genre.RAP);
        song("Dull Knives", "Imagine Dragons", Genre.RAP);
        song("Nothing left to say", "Imagine Dragons", Genre.RAP);
        song("TipToe", "Imagine Dragons", Genre.RAP);
        song("Thunder", "Imagine Dragons", Genre.RAP);
        song("Follow You", "Imagine Dragons", Genre.RAP);

        song("Schlechtes Vorbild", "Sido", Genre.HIP_HOP);
        song("Augen Auf", "Sido", Genre.HIP_HOP);
        song("Mein Block", "Sido", Genre.HIP_HOP);
        song("Maske", "Sido", Genre.HIP_HOP);
        song("Not afraid", "Eminem", Genre.HIP_HOP);
        song("Without me", "Eminem", Genre.HIP_HOP);
        song("Love the way you lie", "Eminem, Rihanna", Genre.HIP_HOP);
        song("Lose yourself", "Eminem", Genre.HIP_HOP);
        song("Rap God", "Eminem", Genre.HIP_HOP);

        song("DJ got us Fallin in Love", "Usher", Genre.RNB);
        song("Yeah!", "Usher", Genre.RNB);
        song("My Boo", "Usher", Genre.RNB);
        song("U remind me", "Usher", Genre.RNB);

        song("Bangarang", "Skillex", Genre.EDM);
        song("In Da Getto", "Skillex", Genre.EDM);
        song("Levels", "Avicii", Genre.EDM, Genre.ELECTRONIC);

        song("The Nights", "Avicii", Genre.EDM, Genre.ELECTRONIC);
        song("Wake me up", "Avicii", Genre.EDM, Genre.ELECTRONIC);
        song("Hey Brother", "Avicii", Genre.EDM, Genre.ELECTRONIC);
        song("Addicted to You", "Avicii", Genre.EDM, Genre.ELECTRONIC);
        song("Waiting for love", "Avicii", Genre.EDM, Genre.ELECTRONIC);

        song("Disconnected", "Pegboard Nerds", Genre.ELECTRONIC);
        song("Try this", "Pegboard Nerds", Genre.ELECTRONIC);
        song("Emergency", "Pegboard Nerds", Genre.ELECTRONIC);
        song("Swap the thing", "Pegboard Nerds", Genre.ELECTRONIC);

        song("Tanz mit mir", "Santiano", Genre.COUNTRY);
        song("Santiano", "Santiano", Genre.COUNTRY);
        song("Wie zuhause", "Santiano", Genre.COUNTRY);
        song("Wellerman", "Santiano", Genre.COUNTRY);
        song("Mädchen von Heithabu", "Santiano, Alligatoah", Genre.COUNTRY);

        generateRandoms();
    }



    private static void generateReleaseAggregateData() {
        suppliers.add(new Supplier("Music International", "Römerstraße 4, 6900 Bregenz, Austria"));
        suppliers.add(new Supplier("Digital Concerns GmbH", "Höchsterstraße 24, 6972 Fußach, Austria"));
        suppliers.add(new Supplier("json Trades", "Kastenlangen 27, 6850 Dornbirn, Austria"));
        suppliers.add(new Supplier("A-Songs", "Herrmansfeld 17, 2311 Sinnau, Austria"));
        suppliers.add(new Supplier("Vinyl Enthusiast AG", "Dorfstraße 23, 4753 Haselau-Oberdorf, Austria"));
        suppliers.add(new Supplier("Wälder Providerchain", "Hinteregg 32, 6863 Egg, Austria"));
        suppliers.add(new Supplier("Vienna Music", "Mozartgrasse 2334, 1100 Wien, Austria"));

        labels.add(new Label("Sony Music Entertainment", "SMC"));
        labels.add(new Label("Universal Music Publishing Group", "UMG"));
        labels.add(new Label("Warner Music Group", "UMG"));
        labels.add(new Label("Island Records", "ISR"));
        labels.add(new Label("BMG Rights Management", "BMG"));
        labels.add(new Label("ABC-Paramount Records", "ABC"));
        labels.add(new Label("Virgin Records", "VGN"));
        labels.add(new Label("Red Hill Records", "RHR"));
        labels.add(new Label("Atlantic Records", "ATL"));
        labels.add(new Label("Def Jam Recordings", "DEF"));

        release("Best Song Ever", "Never gonna give you up");
        release("Best Of Katy Perry", "I kissed a girl", "Roar", "The one that got away");
        release("LP Classic Hits", "Numb", "In the End", "Faint", "Papercut", "Nobody's listening");
        release("LP Modern Hits", "Good Goodbye", "Heavy", "Nobody can save me", "Castle of Glass", "Burn it down", "Lost in the Echo");
        release("Against the Current", "Give it all", "The Violence", "Satellite", "Hero of War");
        release("Happy Times", "Sunrise", "Same old War", "1-800-273-8255");
        release("Single: Mirror, Mirror", "Mirror, Mirror");
        release("Blessed & Posessed", "Army of the Night", "Higher than Heaven", "Dead until dark", "Blessed & Possessed");
        release("Helene's Sommerhits", "Atemlos durch die Nacht", "Null auf 100", "Herzbeben");
        release("Aua meine Ohren", "Atemlos durch die Nacht", "Null auf 100", "Herzbeben", "Egal", "Die FHV ist toll");
        release("COMEBACK - Sido", "Bilder im Kopf", "Astronaut");
        release("Triebwerke", "Willst du", "Trostpreis");
        release("Dragons personal Favourites", "Radioactive", "Dull Knives", "Dull Knives", "Nothing left to say", "TipToe", "Thunder", "Follow You");
        release("Recovery", "Love the way you lie", "Not afraid");
        release("The Eminem Show", "Rap God", "Lose Yourself", "Without me");
        release("Ich & meine Maske", "Schlechtes Vorbild", "Augen Auf", "Mein Block", "Maske");
        release("Usher's Party Hits", "DJ got us Fallin in Love", "Yeah!", "My Boo", "U remind me");
        release("Best of Skills' Rave", "Bangarang", "In Da Getto");
        release("Farewell - Avicii", "Levels", "The Nights", "Wake me up", "Hey Brother", "Addicted to You", "Waiting for love");
        release("Retroworld", "Disconnected", "Try this", "Emergency", "Swap the thing");
        release("Santiano","Tanz mit mir", "Santiano", "Wie zuhause", "Wellerman", "Mädchen von Heithabu");


        // add Acoustic Release for every Artist
        recordings.stream()
                .filter(r -> r.title().endsWith(" - Acoustic")) // filter non acoustic titles
                .collect(Collectors.groupingBy(recording -> recording.artists().get(0), Collectors.mapping(Recording::title, Collectors.toList()))) // List<Recording> -> Map<Artist, String> (Songtitles per Artist)
                .forEach((artists, titles) -> release(artists.name() + "'s Acoustics", titles));

        // add Live recording Release for every Artist
        recordings.stream()
                .filter(r -> r.title().endsWith(" - Live")) // filter non acoustic titles
                .collect(Collectors.groupingBy(recording -> recording.artists().get(0), Collectors.mapping(Recording::title, Collectors.toList()))) // List<Recording> -> Map<Artist, String> (Songtitles per Artist)
                .forEach((artists, titles) -> release(artists.name() + " - Live", titles));

        // add remixes of every Artist
        recordings.stream()
                .filter(r -> r.title().endsWith(" Mix"))
                .collect(Collectors.groupingBy(recording -> recording.artists().get(0), Collectors.mapping(Recording::title, Collectors.toList())))
                .forEach((artist, titles) -> release(artist.name() + "'s Remixes", titles));

        // add dev covers
        var devCovers = recordings.stream().filter(r -> r.artists().get(0).name().equals("DevTeamA")).map(Recording::title).collect(Collectors.toList());
        release("Entwicklergeräusche", devCovers);

    }

    private static void release(String title, String... songs) {
        release(title, Arrays.stream(songs).collect(Collectors.toList()));
    }

    private static void release(String title, Collection<String> songs) {

        // get recordingids of choosen songs
        var recordingIds = songs.stream()
                .map(string -> recordings.stream().filter(r -> r.title().equals(string)).findFirst()) // find recording by given name
                .filter(Optional::isPresent)
                .map(r -> r.get().recordingId()) // recording -> id of recording
                .collect(Collectors.toList());

        // random label and supplier (supplier 1-2 possible)
        var label = randomElement(labels);
        var supplier = new HashSet<Supplier>(); // hashset for no duplicate entries
        for (int i = 0; i < random.nextInt() + 1; i++) { // 1 - 2 Supplier
            supplier.add(randomElement(suppliers));
        }

        // create releases for mediums
        for (Medium medium : Medium.values()) {
            if (random.nextInt() > 0.25) {
                var price = recordingIds.size() * (0.79 + random.nextDouble() * 1.2); // price per song between 0.79 - 1.99
                switch (medium) {
                    case CD:
                        price += 3;
                        break;
                    case MC:
                        price += 5;
                        break;
                    case VINYL:
                        price += 10;
                        break;
                }
                var release = new Release(
                        new ReleaseId(UUID.randomUUID()),
                        random.nextInt(150),
                        title,
                        medium,
                        price,
                        label,
                        new ArrayList<>(supplier),
                        recordingIds
                );
                releases.add(release);
            }
        }
    }

    private static void song(String workString, String artist, Genre... genres) {

        // add work
        var work = new Work(workString);
        works.add(work);

        // add artist
        var recordingArtists = new ArrayList<Artist>();
        Arrays.stream(artist.split(", ")).forEach(art -> artists.stream().filter(a -> a.name().equals(artist)).findFirst().ifPresentOrElse(
                recordingArtists::add,
                () -> { Artist a = new Artist(art) ; artists.add(a) ; recordingArtists.add(a); }
        ));

        // add recording
        var recording = new Recording(
                new RecordingId(UUID.randomUUID()),
                workString,
                random.nextInt(180) + 120,
                random.nextInt(25) + 1995,
                work,
                recordingArtists,
                Arrays.asList(genres)
        );
        recordings.add(recording);
    }

    private static void generateRandoms() {

        // acoustic
        works.forEach(work -> {
            if (random.nextDouble() > 0.8) {
                var recording = recordings.stream().filter(r -> r.title().equals(work.title())).findFirst().get();
                var acoustic = new Recording(
                        new RecordingId(UUID.randomUUID()),
                        recording.title() + " - Acoustic",
                        recording.duration(),
                        recording.recordingYear(),
                        recording.work(),
                        recording.artists(),
                        List.of(Genre.ACOUSTIC)
                );
                recordings.add(acoustic);
            }
        });

        // live recording
        works.forEach(work -> {
            if (random.nextDouble() > 0.5) {
                var recording = recordings.stream().filter(r -> r.title().equals(work.title())).findFirst().get();
                var acoustic = new Recording(
                        new RecordingId(UUID.randomUUID()),
                        recording.title() + " - Live",
                        recording.duration(),
                        recording.recordingYear(),
                        recording.work(),
                        recording.artists(),
                        recording.genres()
                );
                recordings.add(acoustic);
            }
        });

        // remix
        works.forEach(work -> {
            for (int i = 0; i < 10; i++) {
                if (random.nextDouble() > 0.90) {
                    var recording = recordings.stream().filter(r -> r.title().equals(work.title())).findFirst().get();
                    var artist = randomElement(artists);
                    var acoustic = new Recording(
                            new RecordingId(UUID.randomUUID()),
                            recording.title() + " - " + artist.name() + " Mix",
                            recording.duration(),
                            recording.recordingYear() + random.nextInt(5),
                            recording.work(),
                            List.of(artist),
                            recording.genres() // might wanna get a random genre
                    );
                    recordings.add(acoustic);
                }
            }
        });

        // dev cover
        var devArtist = new Artist("DevTeamA");
        artists.add(devArtist);
        works.forEach(work -> {
            if (random.nextDouble() > 0.95) {
                var recording = recordings.stream().filter(r -> r.title().equals(work.title())).findFirst().get();
                var acoustic = new Recording(
                        new RecordingId(UUID.randomUUID()),
                        recording.title() + " - Dev Cover",
                        recording.duration(),
                        recording.recordingYear(),
                        recording.work(),
                        List.of(devArtist),
                        recording.genres()
                );
                recordings.add(acoustic);
            }
        });
    }

    private static <T> T randomElement(List<T> source) {
        return source.get(random.nextInt(source.size()));
    }

    /**
     * Does not show errors in compiletime. if tables were renamed/removed/added, this method needs to be adjusted.
     */
    private static void deleteOldData() {
        var em = Persistence.createEntityManagerFactory("Test").createEntityManager();
        var transaction = em.getTransaction();
        transaction.begin();
        em.createNativeQuery("DROP TABLE IF EXISTS release_supplier").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS release_recordingids").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS recording_genres").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS recording_artist").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS artist").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS recording").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS release").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS supplier").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS work").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS label").executeUpdate();

        em.createNativeQuery("DROP TABLE IF EXISTS \"user\" CASCADE").executeUpdate();
        transaction.commit();
    }

    private static void persistGeneratedData() {
        var em = Persistence.createEntityManagerFactory("Test").createEntityManager();
        var transaction = em.getTransaction();
        transaction.begin();
        labels.forEach(em::persist);
        suppliers.forEach(em::persist);
        artists.forEach(em::persist);
        works.forEach(em::persist);
        recordings.forEach(em::persist);
        releases.forEach(em::persist);
        users.forEach(em::persist);
        transaction.commit();
    }
}
