package ecouteurs;

import ecouteurs.exceptions.ConnexionErrorEvent;

import java.util.EventListener;

/**
 * @author Anthony
 */
public interface ConnexionErrorListener extends EventListener {
    void errorDetected (ConnexionErrorEvent event);
}
