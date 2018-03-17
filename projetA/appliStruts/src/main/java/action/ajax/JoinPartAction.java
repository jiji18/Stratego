package action.ajax;

import Exceptions.AucunePartieException;
import Exceptions.PartieCompleteException;
import Exceptions.PartieInexistanteException;
import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 02/12/16.
 */
public class JoinPartAction extends SuperClass {

    private String idJoueur1;

    @Override
    public String execute() throws Exception {
        Partie p = facade.getPartie(facade.getJoueur(idJoueur1));
        if (p != null) {
            try {
                if (p.isPrivate()) {
                    facade.rejoindrePartiePrivee((Joueur)sessionMap.get("joueur"), p);
                } else {
                    facade.rejoindrePartie((Joueur)sessionMap.get("joueur"), p);
                }
                sessionMap.put("pionsAPlacer", facade.getListePionsDepart((Joueur)sessionMap.get("joueur")));
                sessionMap.put("pionsATuer", facade.getLesPionsRestants(p, p.getJ1()));
                sessionMap.put("monDamier", facade.getDamier(p, (Joueur)sessionMap.get("joueur")));
                sessionMap.put("quelJoueur", "j2");
                return SUCCESS;
            } catch (PartieInexistanteException e) {
                return ERROR;
            } catch (PartieCompleteException f) {
                return ERROR;
            } catch (AucunePartieException g) {
                return ERROR;
            }
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
