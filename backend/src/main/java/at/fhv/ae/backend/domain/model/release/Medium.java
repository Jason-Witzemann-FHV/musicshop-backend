package at.fhv.ae.backend.domain.model.release;

public enum Medium {
    CD("CD"),
    VINYL("Vinyl"),
    MC("Music Cassette");

    private final String friendlyName;

    Medium(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String friendlyName() {
        return friendlyName;
    }
}
