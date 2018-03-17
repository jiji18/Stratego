package action.struts;

import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 17/12/16.
 */
public class PasserSonTourAction extends SuperClass {

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        Partie p = facade.getPartie(j);
        if(p!=null){
            p.getJ1().setStatut((j.equals(p.getJ1())? EnumStatut.WAITINGPLAY:EnumStatut.PLAYING));
            p.getJ2().setStatut((j.equals(p.getJ2())? EnumStatut.WAITINGPLAY:EnumStatut.PLAYING));
            return SUCCESS;
        }else {
            this.addActionError(this.getText("notPlay.finPartie")+" "+facade.getLesPartiesFinies(j).getGagnant().getIdentifiant());
            return INPUT;
        }
    }

}
