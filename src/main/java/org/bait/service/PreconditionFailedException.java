package org.bait.service;

public class PreconditionFailedException extends Exception {

    public PreconditionFailedException(final String message) {
        super(message);
    }
}
