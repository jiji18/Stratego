package Exceptions;

import java.io.Serializable;

/**
 * Gère le cas où un joueur est "unavailable"
 *
 * @author Anthony
 *
 */
public class JoueurOccupeException extends Exception implements Serializable {
    public JoueurOccupeException() {
        super();
    }

    public JoueurOccupeException(String message) {
        super(message);
    }
}
