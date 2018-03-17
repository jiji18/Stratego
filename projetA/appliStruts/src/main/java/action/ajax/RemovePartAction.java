package action.ajax;

import packageJoueur.Joueur;
import action.SuperClass;

/**
 * @author Jihad
 */
public class RemovePartAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        facade.removePartie(j, facade.getPartie(j));
        return SUCCESS;
    }
}
