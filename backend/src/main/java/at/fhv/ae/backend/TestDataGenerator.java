package at.fhv.ae.backend;

import at.fhv.ae.backend.domain.model.work.Genre;

import java.util.List;

public class TestDataGenerator {

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
        song("Burn it down", "Linkin Park", Genre.ROCK);

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
        song("Astronaut", "Sido", Genre.RAP, Genre.HIP_HOP);
        song("Willst du", "Alligatoah", Genre.RAP);
        song("Trostpreis", "Alligatoah", Genre.RAP);

        song("Schlechtes Vorbild", "Sido", Genre.HIP_HOP);
        song("Mein Block", "Sido", Genre.HIP_HOP);
        song("Maske", "Sido", Genre.HIP_HOP);
        song("Without me", "Eminem", Genre.HIP_HOP);
        song("Love the way you lie", "Eminem", Genre.HIP_HOP);
        song("Without me", "Eminem", Genre.HIP_HOP);
        song("Love the way you lie", "Eminem", Genre.HIP_HOP);
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

        List<String> works = List.of(


                // Country
                "Tanz mit mir",
                "Santiano",
                "Wie zuhause",
                "Wellerman",
                "Mädchen von Heithabu"
        );
    }

    private static void song(String work, String artist, Genre... genres) {

    }
}
