package action.struts;

import packageJoueur.Joueur;
import action.SuperClass;

/**
 * @author Jihad
 */
public class LogoutAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        if(j!=null){
            if(this.facade.getPartie(j) != null) this.facade.removePartie(j, facade.getPartie(j));
            this.facade.deconnexion(j.getIdentifiant());
            sessionMap.clear();
        }
        return SUCCESS;
    }
}
