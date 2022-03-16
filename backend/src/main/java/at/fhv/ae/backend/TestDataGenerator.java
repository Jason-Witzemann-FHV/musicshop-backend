package at.fhv.ae.backend;

import at.fhv.ae.backend.domain.model.work.*;

import java.util.*;

public class TestDataGenerator {

    private static List<Work> works = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Recording> recordings = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) {

        song("Never gonna give you up", "Rick Astley", Genre.POP);

        song("Last friday night", "Katy Perry", Genre.POP);
        song("I kissed a girl","Katy Perry", Genre.POP);
        song("Roar ","Katy Perry", Genre.POP);
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
        song("Radioactive", "Imagine Dragons", Genre.RAP);
        song("Follow You", "Imagine Dragons", Genre.RAP);

        song("Schlechtes Vorbild", "Sido", Genre.HIP_HOP);
        song("Mein Block", "Sido", Genre.HIP_HOP);
        song("Maske", "Sido", Genre.HIP_HOP);
        song("Without me", "Eminem", Genre.HIP_HOP);
        song("Love the way you lie", "Eminem", Genre.HIP_HOP);
        song("Without me", "Eminem", Genre.HIP_HOP);
        song("Love the way you lie", "Eminem, Rihanna", Genre.HIP_HOP);
        song("Lose yourself", "Eminem", Genre.HIP_HOP);
        song("Rap God", "Eminem", Genre.HIP_HOP);

        song("DJ got us Fallin in Love", "Usher", Genre.RNB);
        song("Yeah!", "Usher", Genre.RNB);
        song("My Boo", "Usher", Genre.RNB);
        song("U remind me", "Usher", Genre.RNB);

        song("Bangarang", "Skillrex", Genre.EDM);
        song("In Da Getto", "Skillrex", Genre.EDM);
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

        works.forEach(System.out::println);
        artists.forEach(System.out::println);
        recordings.forEach(System.out::println);
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
                        recording.year(),
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
                    var artist = artists.get(random.nextInt(artists.size()));
                    var acoustic = new Recording(
                            new RecordingId(UUID.randomUUID()),
                            recording.title() + " - " + artist.name() + " Mix",
                            recording.duration(),
                            recording.year() + random.nextInt(5),
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
                        recording.year(),
                        recording.work(),
                        List.of(devArtist),
                        recording.genres()
                );
                recordings.add(acoustic);
            }
        });
    }
}
