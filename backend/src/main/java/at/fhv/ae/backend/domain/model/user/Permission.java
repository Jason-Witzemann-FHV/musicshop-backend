package at.fhv.ae.backend.domain.model.user;


public enum Permission {

    SEARCH_RELEASES,
    SELL_RELEASES, // Employee sells to walk in
    BUY_RELEASES, // Customer buys for themselves
    ORDER_RELEASES,
    PUBLISH_WEBFEED

}
