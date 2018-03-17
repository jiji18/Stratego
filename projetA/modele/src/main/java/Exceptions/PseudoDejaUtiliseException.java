package Exceptions;

import java.io.Serializable;

/**
 * Gère le cas à la création d'un nouveau Joueur si le pseudo est déjà pris
 *
 * @author Anthony
 */
public class PseudoDejaUtiliseException extends Exception implements Serializable {
    public PseudoDejaUtiliseException() {
        super();
    }
    public PseudoDejaUtiliseException(String message) {
        super(message);
    }
}
