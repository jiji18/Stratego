package packagePlateau.packageCase;

import packageJoueur.Joueur;

import java.io.Serializable;

/**
 * Created by Jolan on 07/11/2016.
 */
public class CaseObstacle implements Case, Serializable {

    public CaseObstacle() {

    }

    @Override
    public TypeCase getType() {
        return TypeCase.OBSTACLE;
    }

    @Override
    public int getValeur() {
        return TypeCase.OBSTACLE.getValeur();
    }

    @Override
    public boolean estAtteignable() {
        return false;
    }

    @Override
    public boolean isCaseObstacle() {
        return true;
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
        return false;
    }

    @Override
    public Joueur getJoueur() {
        return null;
    }

}
