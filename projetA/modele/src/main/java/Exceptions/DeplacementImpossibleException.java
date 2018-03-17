package Exceptions;

import java.io.Serializable;

/**
 * @author Antho
 */
public class DeplacementImpossibleException extends Exception implements Serializable {
    public DeplacementImpossibleException() {
        super();
    }
    public DeplacementImpossibleException(String message) {
        super(message);
    }
}
