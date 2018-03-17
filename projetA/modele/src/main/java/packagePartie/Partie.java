package packagePartie;

import packageJoueur.Joueur;
import packagePlateau.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jolan
 */
public interface Partie extends Cloneable {
    Map<Joueur, List<Pion>> getLesPionsTues();
    boolean estComplete();
    boolean isPrivate();
    Plateau getPlateau();
    public Joueur getJ1();
    public Joueur getJ2();
    public void setJ2(Joueur j);
    Joueur getGagnant();
    void setGagnant(Joueur winner);
    Partie clone();
}
