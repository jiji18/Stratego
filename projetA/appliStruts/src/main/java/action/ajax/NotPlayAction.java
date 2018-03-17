package action.ajax;

import Exceptions.AucunePartieException;
import packageFacade.Facade;
import packageFacade.IFacade;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.packageCase.Case;
import action.SuperClass;

import java.util.Map;

/**
 * Created by jihad on 15/12/16.
 */
public class NotPlayAction extends SuperClass {

    private Case[][] damierActif;
    private String msg;
    private String joueur1;
    private String joueur2;
    private boolean canPlay;
    private Map<String, Object> applicationMap;
    private int xDefier;
    private int yDefier;
    private String srcVu;

    public Case[][] getDamierActif() {
        return damierActif;
    }

    public void setDamierActif(Case[][] damierActif) {
        this.damierActif = damierActif;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(String joueur1) {
        this.joueur1 = joueur1;
    }

    public String getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(String joueur2) {
        this.joueur2 = joueur2;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public int getxDefier() {
        return xDefier;
    }

    public void setxDefier(int xDefier) {
        this.xDefier = xDefier;
    }

    public int getyDefier() {
        return yDefier;
    }

    public void setyDefier(int yDefier) {
        this.yDefier = yDefier;
    }

    public String getSrcVu() {
        return srcVu;
    }

    public void setSrcVu(String srcVu) {
        this.srcVu = srcVu;
    }

    public void setApplication(Map<String, Object> map) {
        applicationMap = map;
        this.facade = (IFacade) map.get("facade");
        if (this.facade == null) {
            this.facade = Facade.getInstance();
            map.put("facade", this.facade);
        }
    }

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        Partie p = facade.getPartie(j);
        if(p != null){
            damierActif = (p.getJ1().getStatut() == EnumStatut.PLAYING?p.getPlateau().getDamier():p.getPlateau().getDamierRenverser());
            joueur1 = p.getJ1().getIdentifiant();
            joueur2 = p.getJ2().getIdentifiant();

            if(applicationMap.get("srcVu") != null){
                srcVu = (String) applicationMap.get("srcVu");
                xDefier = (Integer) applicationMap.get("xDefier");
                yDefier = (Integer) applicationMap.get("yDefier");
                applicationMap.remove("srcVu");
                applicationMap.remove("xDefier");
                applicationMap.remove("yDefier");
            }

            canPlay = (j.getStatut() == EnumStatut.PLAYING);

            if(canPlay){
                sessionMap.remove("pionsATuer");
                sessionMap.put("pionsATuer", facade.getLesPionsRestants(p, (j.equals(p.getJ1())?p.getJ2():p.getJ1())));
            }
        }else{
            try {
                p = facade.getLesPartiesFinies(j);
                joueur1 = p.getJ1().getIdentifiant();
                joueur2 = p.getJ2().getIdentifiant();
                msg = this.getText("notPlay.finPartie")+" "+p.getGagnant().getIdentifiant();
            }catch (AucunePartieException e){

            }
        }
        return SUCCESS;
    }
}
