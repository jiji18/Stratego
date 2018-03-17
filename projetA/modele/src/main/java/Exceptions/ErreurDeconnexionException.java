package Exceptions;

import java.io.Serializable;

/**
 * @author Jolan
 */
public class ErreurDeconnexionException extends Exception implements Serializable{
    public ErreurDeconnexionException(String s) {
        super(s);
    }
    public ErreurDeconnexionException(){
        super();
    }
}
