package Exceptions;

import java.io.Serializable;

/**
 * Created by jihad on 12/11/16.
 */
public class PionIdentiqueException extends Exception implements Serializable {
    public PionIdentiqueException(){ super();}
    public PionIdentiqueException(String message){ super(message);}
}
