package ecouteurs.exceptions;

import java.util.EventObject;

/**
 * Created by jihad on 10/01/17.
 */
public class InscriptionErrorEvent extends EventObject {

    private String message;

    public InscriptionErrorEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
