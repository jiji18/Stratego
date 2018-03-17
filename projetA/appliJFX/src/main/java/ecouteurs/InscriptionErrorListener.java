package ecouteurs;

import ecouteurs.exceptions.InscriptionErrorEvent;

import java.util.EventListener;

/**
 * Created by jihad on 10/01/17.
 */
public interface InscriptionErrorListener extends EventListener {
    void errorDetected (InscriptionErrorEvent event);
}
