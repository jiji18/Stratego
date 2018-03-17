package action.ajax;

import Exceptions.JoueurOccupeException;
import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * @author Jihad
 */
public class CreatePartAction extends SuperClass {
    private String typeOfPart;
    private String joueurInviter;
    private String error;

    @Override
    public String execute() throws Exception {
        Partie p;
        if (this.getText("lobby.createPart.prive").equals(this.typeOfPart)) {
            Joueur inviter = facade.getJoueurConnectes(this.joueurInviter);
            try {
                p = this.facade.defier((Joueur) this.sessionMap.get("joueur"), inviter);
            }catch (JoueurOccupeException e){
                error = this.getText("lobby.error.joueurInivter.occuper");
                return ERROR;
            }
        } else {
            p = this.facade.creerPartie((Joueur) this.sessionMap.get("joueur"));
        }
        sessionMap.put("monDamier", facade.getDamier(p, (Joueur) this.sessionMap.get("joueur")));
        sessionMap.put("pionsAPlacer", facade.getListePionsDepart((Joueur) this.sessionMap.get("joueur")));
        sessionMap.put("quelJoueur", "j1");
        return SUCCESS;
    }

    public String getTypeOfPart() {
        return typeOfPart;
    }

    public void setTypeOfPart(String typeOfPart) {
        this.typeOfPart = typeOfPart;
    }

    public String getJoueurInviter() {
        return joueurInviter;
    }

    public void setJoueurInviter(String joueurInviter) {
        this.joueurInviter = joueurInviter;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
