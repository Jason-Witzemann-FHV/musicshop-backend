package at.fhv.ae.backend.domain.model.user;


public enum Permission {

    NONE, // Permission to be used, when no permission is required but the interfaces still require one.
    SEARCH_RELEASES,
    SELL_RELEASES, // Employee sells to walk in
    BUY_RELEASES, // Customer buys for themselves
    ORDER_RELEASES,
    PUBLISH_WEBFEED

}
