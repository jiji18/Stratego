package action.ajax;

import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 03/12/16.
 */
public class ObservePartAction extends SuperClass {

    private String idJoueur1;

    @Override
    public String execute() throws Exception {
        Partie p = facade.getPartie(facade.getJoueur(idJoueur1));
        if(p !=null){
            facade.rejoindrePartieObservateur((Joueur)sessionMap.get("joueur"), p);
            return SUCCESS;
        }
        return ERROR;
    }

    public String getIdJoueur1() {
        return idJoueur1;
    }

    public void setIdJoueur1(String idJoueur1) {
        this.idJoueur1 = idJoueur1;
    }
}
