package action.struts;

import Exceptions.ErreurConnexionException;
import action.SuperClass;

/**
 * @author Anthony
 */
public class LoginAction extends SuperClass {

    private String identifiant;
    private String motDePasse;

    @Override
    public String execute() {
        try {
            sessionMap.put("joueur", facade.connexion(this.identifiant, this.motDePasse));
            return SUCCESS;
        } catch (ErreurConnexionException e) {
            this.addActionError(getText("login.error"));
            return ERROR;
        }
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

}
