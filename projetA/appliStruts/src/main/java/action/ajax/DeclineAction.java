package action.ajax;

import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jolan on 12/1/16.
 */
public class DeclineAction extends SuperClass {

    private String idJoueur1;

    @Override
    public String execute() {
        Joueur j = (Joueur) sessionMap.get("joueur");
        Partie p = facade.getPartie(facade.getJoueur(idJoueur1));
        if(p == null) return ERROR;
        facade.rejeterInvitation(j, p);
        return SUCCESS;
    }

    public void setIdJoueur1(String adversaire) {
        this.idJoueur1 = adversaire;
    }
}
