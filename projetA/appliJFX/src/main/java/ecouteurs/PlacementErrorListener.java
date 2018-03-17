package ecouteurs;

import ecouteurs.exceptions.PartieInexistanteErrorEvent;

/**
 * @author jolan
 */
public interface PlacementErrorListener {
    void errorDetected(PartieInexistanteErrorEvent event);
}
