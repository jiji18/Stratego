package Exceptions;

import java.io.Serializable;

/**
 * Gère le cas d'une partie déjà complete (qui contient deux joueurs)
 *
 * @author Anthony
 */
public class PartieCompleteException extends Exception implements Serializable {
    public PartieCompleteException() {
        super();
    }
    public PartieCompleteException(String message) {
        super(message);
    }
}
