package Exceptions;

import java.io.Serializable;

/**
 * @author Jolan
 */
public class AbandonImpossibleException extends Exception implements Serializable {
    public AbandonImpossibleException (){
        super();
    }
    public AbandonImpossibleException(String m){
        super(m);
    }
}
