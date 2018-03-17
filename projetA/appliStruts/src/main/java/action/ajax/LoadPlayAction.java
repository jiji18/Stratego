package action.ajax;

import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 12/12/16.
 */
public class LoadPlayAction extends SuperClass {

    private boolean canPlay;
    private boolean canPlace;

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean isCanPlace() {
        return canPlace;
    }

    public void setCanPlace(boolean canPlace) {
        this.canPlace = canPlace;
    }

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        Partie p = facade.getPartie(j);
        canPlace = (j.getStatut() == EnumStatut.PLAYING && j.equals(p.getJ2()));
        canPlay = (j.getStatut() == EnumStatut.PLAYING && j.equals(p.getJ1()));
        return SUCCESS;
    }
}
