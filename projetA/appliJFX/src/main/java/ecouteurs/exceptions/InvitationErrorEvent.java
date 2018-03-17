package ecouteurs.exceptions;

import java.util.EventObject;

/**
 * @author Anthony
 */
public class InvitationErrorEvent extends EventObject {
    private String message;

    public InvitationErrorEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
