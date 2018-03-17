package packagePlateau.packageCase;

import packageJoueur.Joueur;

import java.io.Serializable;

/**
 * Created by jihad on 12/11/16.
 */
public class CaseVideInitiale implements Case , Serializable {
    @Override
    public TypeCase getType() {
        return TypeCase.VIDEINITIALE;
    }

    @Override
    public int getValeur() {
        return TypeCase.VIDEINITIALE.getValeur();
    }

    @Override
    public boolean estAtteignable() {
        return false;
    }

    @Override
    public boolean isCaseObstacle() {
        return false;
    }

    @Override
    public boolean isCaseVide() {
        return false;
    }

    @Override
    public boolean isCasePion() {
        return false;
    }

    @Override
    public boolean isCaseVideInitial() {
        return true;
    }

    @Override
    public Joueur getJoueur() {
        return null;
    }
}
