package packagePlateau.packageCase;

import packageJoueur.Joueur;

/**
 * Created by Jolan on 07/11/2016.
 */
public interface Case {

    /**
     * @return le type de la case.
     */
    TypeCase getType();

    /**
     * Cette méthode récupère la valeur du pion positionné à cette case. Par convention, si c'est un obstacle, on renverra
     * -1.
     *
     * @return la valeur en entier.
     */
    int getValeur();

    /**
     * Cette méthode permet de savoir si l'on peut atteindre cette case ou non.
     *
     * @return
     */
    boolean estAtteignable();

    boolean isCaseObstacle();

    boolean isCaseVide();

    boolean isCasePion();

    boolean isCaseVideInitial();

    Joueur getJoueur();

}
