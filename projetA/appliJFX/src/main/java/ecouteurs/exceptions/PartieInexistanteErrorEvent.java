package ecouteurs.exceptions;

import java.util.EventObject;

/**
 * @author jolan
 */
public class PartieInexistanteErrorEvent extends EventObject{
    private String message;

    public PartieInexistanteErrorEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
