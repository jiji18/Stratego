package action.struts;

import Exceptions.PseudoDejaUtiliseException;
import action.SuperClass;

/**
 * @author Jolan
 */
public class RegisterAction extends SuperClass {

    private String identifiant;
    private String motDePasse;
    private String motDePasse2;

    public String execute() {
        if (!motDePasse.equals(motDePasse2)) {
            this.addFieldError("motDePasse2", getText("register.error.mdp2.notsame"));
            return INPUT;
        }

        try {
            sessionMap.put("joueur", facade.inscription(identifiant, motDePasse));
        } catch (PseudoDejaUtiliseException e) {
            addFieldError("identifiant", getText("register.error.id.exist"));
            return INPUT;
        }
        return SUCCESS;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
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
