package action.ajax;

import Exceptions.AucunePartieException;
import action.SuperClass;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePartie.PartiePrivee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jihad
 */
public class LoadDataAction extends SuperClass {
    private List<Joueur> joueursConnectes;
    private List<Partie> partiesPubliques;
    private List<Partie> invitations;
    private List<Joueur> dejaInvite;
    private List<Joueur> classement;
    private String reponse;
    private boolean dejaCreerPartiePublique;
    private boolean dejaCreerPartie;

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");

        //On regarde ici si le joueur a dejà créé une partie publique
        dejaCreerPartie = (facade.getPartie(j) != null);
        if (dejaCreerPartie) dejaCreerPartiePublique = !facade.getPartie(j).isPrivate();

        //Récupération des joueurs connectés en retirant celui en session
        joueursConnectes = facade.getJoueursConnectes();
        joueursConnectes.remove(j);

        //Récupération des parties publiques en retirant ses propres parties
        try {
            partiesPubliques = facade.getLesPartiesPublics();
            for (int i = 0; i < partiesPubliques.size(); i++) {
                if (j.equals(partiesPubliques.get(i).getJ1())) partiesPubliques.remove(i);
            }
        } catch (AucunePartieException e) {

        }

        //Récupérations des invitations
        if (facade.estAttenduPourJouer(j)) {
            invitations = facade.getInvitations(j);
        }

        //Récupération d'autres infos
        if (facade.getPartie(j) != null) {
            if (j.getStatut() == EnumStatut.PLAYING) {
                reponse = this.getText("pre-game.continue");
                sessionMap.put("pionsATuer", facade.getLesPionsRestants(facade.getPartie(j), facade.getPartie(j).getJ2()));
            } else {
                Partie p = facade.getPartie(j);
                if (p.isPrivate()) {
                    dejaInvite = ((PartiePrivee) p).getInvites();
                    if (!dejaInvite.isEmpty()) {
                        for (Joueur inviter : dejaInvite) {
                            if (inviter.getStatut() == EnumStatut.CONNECTED) {
                                reponse = this.getText("pre-game.wait");
                                break;
                            }
                        }
                    }
                    if (reponse == null) reponse = this.getText("pre-game.emptyInvit");
                } else {
                    reponse = this.getText("pre-game.wait");
                }
            }
        }

        //Récupération du classement
        classement = facade.getJoueurs();
        Collections.sort(classement);

        return SUCCESS;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public List<Joueur> getJoueursConnectes() {
        return joueursConnectes;
    }

    public void setJoueursConnectes(List<Joueur> joueursConnectes) {
        this.joueursConnectes = joueursConnectes;
    }

    public List<Partie> getPartiesPubliques() {
        return partiesPubliques;
    }

    public void setPartiesPubliques(List<Partie> partiesPubliques) {
        this.partiesPubliques = partiesPubliques;
    }

    public List<Partie> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Partie> invitations) {
        this.invitations = invitations;
    }

    public List<Joueur> getDejaInvite() {
        return dejaInvite;
    }

    public void setDejaInvite(List<Joueur> dejaInvite) {
        this.dejaInvite = dejaInvite;
    }

    public List<Joueur> getClassement() {
        return classement;
    }

    public void setClassement(List<Joueur> classement) {
        this.classement = classement;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public boolean isDejaCreerPartiePublique() {
        return dejaCreerPartiePublique;
    }

    public void setDejaCreerPartiePublique(boolean dejaCreerPartiePublique) {
        this.dejaCreerPartiePublique = dejaCreerPartiePublique;
    }

    public boolean isDejaCreerPartie() {
        return dejaCreerPartie;
    }

    public void setDejaCreerPartie(boolean dejaCreerPartie) {
        this.dejaCreerPartie = dejaCreerPartie;
    }
}
