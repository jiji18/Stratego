package Exceptions;

import java.io.Serializable;

/**
 * Created by jihad on 11/11/16.
 */
public class AucunePartieException extends Exception implements Serializable {
    public AucunePartieException(){
        super();
    }
    public AucunePartieException(String m){
        super(m);
    }
}
