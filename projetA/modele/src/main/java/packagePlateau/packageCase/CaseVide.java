package packagePlateau.packageCase;


import packageJoueur.Joueur;

import java.io.Serializable;

/**
 * Created by jihad on 12/11/16.
 */
public class CaseVide implements Case, Serializable {

    @Override
    public TypeCase getType() {
        return TypeCase.VIDE;
    }

    @Override
    public int getValeur() {
        return TypeCase.VIDE.getValeur();
    }

    @Override
    public boolean estAtteignable() {
        return true;
    }

    @Override
    public boolean isCaseObstacle() {
        return false;
    }

    @Override
    public boolean isCaseVide() {
        return true;
    }

    @Override
    public boolean isCasePion() {
        return false;
    }

    @Override
    public boolean isCaseVideInitial() {
        return false;
    }

    @Override
    public Joueur getJoueur() {
        return null;
    }
}
