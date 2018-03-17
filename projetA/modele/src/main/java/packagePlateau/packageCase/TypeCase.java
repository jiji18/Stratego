package packagePlateau.packageCase;

import java.io.Serializable;

/**
 * Created by jihad on 12/11/16.
 */
public enum TypeCase implements Serializable {
    PION(0), OBSTACLE(-3), VIDE(-2), VIDEINITIALE(-1);
    private int valeur;

    TypeCase(int valeur) {
        this.valeur=valeur;
    }

    public int getValeur() {
        return valeur;
    }
}
