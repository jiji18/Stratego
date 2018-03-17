package packagePlateau.packageCase;

import packageJoueur.Joueur;
import packagePlateau.Pion;

import java.io.Serializable;

/**
 * Created by Jolan on 07/11/2016.
 */
public class CasePion implements Case , Serializable {
    private Pion pion;

    public CasePion(Pion p) {
        this.pion = p;
    }

    @Override
    public TypeCase getType() {
        return TypeCase.PION;
    }

    @Override
    public int getValeur() {
        return pion.getValeur();
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
        return false;
    }

    @Override
    public boolean isCasePion() {
        return true;
    }

    @Override
    public boolean isCaseVideInitial() {
        return false;
    }

    @Override
    public Joueur getJoueur() {
        return pion.getJoueur();
    }

    public Pion getPion() {
        return pion;
    }

}
