package action.struts;

import Exceptions.PartieInexistanteException;
import packageJoueur.Joueur;
import action.SuperClass;

/**
 * Created by jihad on 10/12/16.
 */
public class AbandonnerAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        try {
            facade.abandonnerPartie((Joueur) sessionMap.get("joueur"));
            return SUCCESS;
        }catch (PartieInexistanteException e){
            this.addActionError(this.getText("placePion.error.noPart"));
            return INPUT;
        }
    }
}
