package at.fhv.ae.backend.domain.model.work;


import java.util.Arrays;

public enum Genre {
    POP("Pop"),
    ELECTRONIC("Elektronik"),
    SCHLAGER("Schlager"),
    EDM("EDM"),
    METAL("Metal"),
    ROCK("Rock"),
    COUNTRY("Country"),
    RAP("Rap"),
    HIP_HOP("Hip Hop"),
    RNB("RNB"),
    ACOUSTIC("Acoustic");

    private String friendlyName;

    Genre(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String friendlyName() {
        return friendlyName;
    }

    public static Genre byFriendlyName(String friendlyName) {
        return Arrays.stream(values()).filter(g -> g.friendlyName.equalsIgnoreCase(friendlyName)).findFirst().orElse(null);
    }
}
