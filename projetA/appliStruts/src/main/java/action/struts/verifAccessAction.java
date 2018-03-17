package action.struts;

import packageJoueur.Joueur;
import action.SuperClass;

/**
 * @author Jihad
 */
public class verifAccessAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        if (sessionMap.get("joueur") != null) {
            Joueur j = (Joueur) sessionMap.get("joueur");
            return j.getStatut().toString();
        } else {
            return "INCONNU";
        }
    }

}
