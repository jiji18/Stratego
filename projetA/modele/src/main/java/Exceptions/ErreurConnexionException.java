package Exceptions;

import java.io.Serializable;

/**
 * Gère le cas d'une erreur de connexion
 *
 * @author Anthony
 */
public class ErreurConnexionException extends Exception implements Serializable {
    public ErreurConnexionException() {
        super();
    }
    public ErreurConnexionException(String message) {
        super(message);
    }
}
