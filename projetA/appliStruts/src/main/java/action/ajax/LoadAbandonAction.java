package action.ajax;

import packageJoueur.Joueur;
import action.SuperClass;

/**
 * Created by jihad on 13/12/16.
 */
public class LoadAbandonAction extends SuperClass {

    private boolean abandon;

    public boolean isAbandon() {
        return abandon;
    }

    public void setAbandon(boolean abandon) {
        this.abandon = abandon;
    }

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        abandon = (facade.getPartie(j) == null);
        return SUCCESS;
    }
}
