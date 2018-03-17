package packagePartie;

import packageJoueur.Joueur;
import packagePlateau.Pion;
import packagePlateau.Plateau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jolan
 */
public abstract class AbstractPartie implements Partie, Serializable {
    private Joueur j1;
    private Joueur j2;
    private Map<Joueur, List<Pion>> lesPionTues;
    private Joueur gagnant;

    private Plateau plateau;

    public AbstractPartie() {
        plateau = new Plateau();
        lesPionTues = new HashMap<>();
    }

    @Override
    public Map<Joueur, List<Pion>> getLesPionsTues() {
        return lesPionTues;
    }

    @Override
    public boolean estComplete() {
        return !(j1 == null || j2 == null);
    }

    @Override
    public Plateau getPlateau() {
        return plateau;
    }

    @Override
    public Joueur getJ1() {
        return j1;
    }


    public void setJ1(Joueur j1) {
        this.j1 = j1;
        this.lesPionTues.put(j1, new ArrayList<Pion>());
    }

    @Override
    public Joueur getJ2() {
        return j2;
    }

    @Override
    public void setJ2(Joueur j2) {
        this.j2 = j2;
        this.lesPionTues.put(j2, new ArrayList<Pion>());
    }

    @Override
    public Joueur getGagnant() {
        return gagnant;
    }

    @Override
    public void setGagnant(Joueur winner) {
        this.gagnant = winner;
    }

    public Partie clone() {
        Partie p = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            p = (Partie) super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }

        // on renvoie le clone
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Partie partie = (Partie) o;
        if (partie.isPrivate() != this.isPrivate()) return false;
        return partie.getJ1().getIdentifiant().equals(this.getJ1().getIdentifiant());
    }
}
