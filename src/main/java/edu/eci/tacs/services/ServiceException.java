package edu.eci.tacs.services;

public class ServiceException extends Exception {

    public static final String NULL_USERNAME ="El nombre del usuario no puede ser nulo";

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
