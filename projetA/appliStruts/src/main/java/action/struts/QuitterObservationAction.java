package action.struts;

import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 15/12/16.
 */
public class QuitterObservationAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        Partie p = facade.getPartie(j);
        if(p !=null){
            facade.quitterObservation(p, j);
            return SUCCESS;
        }else{
            this.addActionError(this.getText("notPlay.noPart"));
            return INPUT;
        }
    }
}
