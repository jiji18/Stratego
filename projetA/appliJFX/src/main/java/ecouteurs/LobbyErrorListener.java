package ecouteurs;

import ecouteurs.exceptions.DeconnexionErrorEvent;
import ecouteurs.exceptions.InvitationErrorEvent;
import ecouteurs.exceptions.JoueurOccupeEvent;

import java.util.EventListener;

/**
 * @author Jihad
 */
public interface LobbyErrorListener extends EventListener {
    void errorDetected(DeconnexionErrorEvent event);

    void errorDetected(JoueurOccupeEvent event);

    void errorDetected(InvitationErrorEvent event);
}
