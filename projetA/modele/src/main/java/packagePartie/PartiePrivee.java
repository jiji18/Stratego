package packagePartie;

import javafx.scene.control.Label;
import packageJoueur.Joueur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jolan
 */
public class PartiePrivee extends AbstractPartie {
    private ArrayList<Joueur> invites;

    public PartiePrivee(Joueur j1) {
        super();
        setJ1(j1);
        invites = new ArrayList<>();
    }

    @Override
    public boolean isPrivate() {
        return true;
    }

    public List<Joueur> getInvites() {
        return invites;
    }

    public void addInvite(Joueur j) {
        invites.add(j);
    }

    public boolean removeInvite(Joueur j) {
        return invites.remove(j);
    }

    public void clearInvites() {
        invites.clear();
    }

    public boolean containsInvite(Joueur j) {
        return invites.contains(j);
    }

    public boolean invitesIsEmpty() {
        return invites.isEmpty();
    }

}
