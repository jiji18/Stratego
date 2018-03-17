package packagePlateau;

import Exceptions.PionIdentiqueException;
import packageJoueur.Joueur;

import java.io.Serializable;


/**
 * Created by Jolan on 07/11/2016.
 */
public class Pion implements Serializable {

    private TypePion type;
    private Joueur joueur;

    public Pion(TypePion type, Joueur j) {
        this.type = type;
        this.joueur = j;
    }

    public int getValeur() {
        return type.getValeur();
    }

    /**
     * Cette méthode permet de savoir si ce pion peut se déplacer ou non.
     *
     * @return true s'il le peut, false sinon.
     */
    public boolean isMobile() {
        return (type != TypePion.BOMBE && type != TypePion.DRAPEAU);
    }


    /**
     * Cette méthode permet de savoir si ce pion peut mangé ou non le pion en paramètre.
     *
     * @param p qui est un objet Pion.
     * @return true s'il le peut, false sinon.
     */
    public boolean canKill(Pion p) throws PionIdentiqueException {
        // Cas particulier

        if ((this.type == TypePion.ESPION && p.getType() == TypePion.MARECHAL) ||
                (this.type == TypePion.DEMINEUR && p.getType() == TypePion.BOMBE)) return true;

        //On vérifie que les 2 pions ne sont pas identiques
        if (this.getValeur() == p.getValeur()) throw new PionIdentiqueException();

            // Cas de base sinon

        else return this.getValeur() > p.getValeur();
    }

    /**
     * Cette méthode permet de savoir si ce pion peut se déplacer de plus d'une case.
     *
     * @return vrai s'il le peut, faux sinon.
     */
    public boolean moveMoreThanOneCase() {
        return (type == TypePion.ECLAIREUR);
    }

    /**
     * @return le type de ce pion de type TypePion.
     */
    public TypePion getType() {
        return this.type;
    }

    /**
     * @return l'objet Joueur possédant ce pion.
     */
    public Joueur getJoueur() {
        return joueur;
    }
}
