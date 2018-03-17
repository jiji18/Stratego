package packageJoueur;

import java.io.Serializable;

/**
 * Type énuméré du statut d'un joueur
 *
 * @author Anthony
 */
public enum EnumStatut implements Serializable {
    CONNECTED, DISCONNECTED, WAITINGPLAYER, WAITINGPLAY, PLAYING, OBSERVING;
    EnumStatut() {
    }
}
