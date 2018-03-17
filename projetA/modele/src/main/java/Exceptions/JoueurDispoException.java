package Exceptions;

import java.io.Serializable;

/**
 * @author Jolan
 */
public class JoueurDispoException extends Exception implements Serializable {
    public JoueurDispoException(){
        super();
    }
    public JoueurDispoException(String m){super(m);}
}
