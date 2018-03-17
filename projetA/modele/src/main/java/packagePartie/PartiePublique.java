package packagePartie;

import packageJoueur.Joueur;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jolan
 */
public class PartiePublique extends AbstractPartie {

    private List<Joueur> observateurs;

    public PartiePublique(Joueur j1) {
        super();
        setJ1(j1);
        observateurs = new ArrayList<>();
    }

    @Override
    public boolean isPrivate() {
        return false;
    }

    public List<Joueur> getObservateurs() {
        return this.observateurs;
    }

    public void setObservateurs(List<Joueur> observateurs) {
        this.observateurs = observateurs;
    }

    public void addObservateur(Joueur j) {
        observateurs.add(j);
    }

    public void removeObservateur(Joueur j) {
        observateurs.remove(j);
    }

}
