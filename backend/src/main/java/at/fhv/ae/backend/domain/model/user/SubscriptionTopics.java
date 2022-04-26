package at.fhv.ae.backend.domain.model.user;

public enum SubscriptionTopics {

    SYSTEM_TOPIC("SystemTopic"),
    POP_TOPIC("PopTopic"),
    ROCK_TOPIC("RockTopic");

    private final String friendlyName;

    SubscriptionTopics(String friendlyName) {this.friendlyName = friendlyName;}

    public String friendlyName() { return friendlyName;}
}
