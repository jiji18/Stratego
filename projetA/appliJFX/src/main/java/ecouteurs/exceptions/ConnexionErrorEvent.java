package ecouteurs.exceptions;

import java.util.EventObject;

/**
 * @author Anthony
 */
public class ConnexionErrorEvent extends EventObject {
    private String message;
    public ConnexionErrorEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
