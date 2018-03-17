package Exceptions;

import java.io.Serializable;

/**
 * @author Jolan
 */
public class PartieInexistanteException extends Exception implements Serializable {
    public PartieInexistanteException(){

    }
    public PartieInexistanteException(String s) {
        super(s);
    }
}
