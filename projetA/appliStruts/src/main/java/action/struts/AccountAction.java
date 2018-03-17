package action.struts;

import packageJoueur.Joueur;
import action.SuperClass;

/**
 * @author Anthony
 */
public class AccountAction extends SuperClass {

    private String motDePasse;
    private String motDePasse2;

    public String execute() {
        if (!motDePasse.equals(motDePasse2)) {
            this.addFieldError("motDePasse2", getText("register.error.mdp2.notsame"));
            return INPUT;
        }
        facade.modifierMotDePasse((Joueur) sessionMap.get("joueur"), motDePasse);
        return SUCCESS;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMotDePasse2() {
        return motDePasse2;
    }

    public void setMotDePasse2(String motDePasse2) {
        this.motDePasse2 = motDePasse2;
    }
    //</editor-fold>
}

