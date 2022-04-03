package at.fhv.ae.shared;

import java.io.Serializable;

public class AuthorizationException extends Exception implements Serializable {
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException() {

    }
}
