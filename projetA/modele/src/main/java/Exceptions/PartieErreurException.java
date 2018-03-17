package Exceptions;

import java.io.Serializable;

/**
 * Gère le cas d'une erreur de partie (non creer)
 *
 * @author Anthony
 */
public class PartieErreurException extends Exception implements Serializable {
    public PartieErreurException() {
        super();
    }
    public PartieErreurException(String message) {
        super(message);
    }
}
