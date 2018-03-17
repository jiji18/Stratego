package ecouteurs;

import ecouteurs.exceptions.PartieInexistanteErrorEvent;

/**
 * Created by jihad on 14/01/17.
 */
public interface NotJouerErrorListener {
    void errorDetected(PartieInexistanteErrorEvent event);
}
