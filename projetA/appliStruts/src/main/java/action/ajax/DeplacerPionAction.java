package action.ajax;

import packageFacade.Facade;
import packageFacade.IFacade;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.packageCase.TypeCase;
import action.SuperClass;

import java.util.Map;


/**
 * Created by jihad on 17/12/16.
 */
public class DeplacerPionAction extends SuperClass {
    private int x;
    private int y;
    private int newX;
    private int newY;
    private String srcVu;
    private String msg;
    private Map<String, Object> applicationMap;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public String getSrcVu() {
        return srcVu;
    }

    public void setSrcVu(String srcVu) {
        this.srcVu = srcVu;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
            if(facade.getDamier(p, j)[newX][newY].getType() == TypeCase.PION){
                srcVu = "./img/"+(p.getJ1().equals(facade.getDamier(p, j)[newX][newY].getJoueur())?"j1":"j2")+"_pion_"+facade.getDamier(p, j)[newX][newY].getValeur()+".bmp";
                applicationMap.put("xDefier", x);
                applicationMap.put("yDefier", y);
                applicationMap.put("srcVu", "./img/"+(p.getJ1().equals(facade.getDamier(p, j)[x][y].getJoueur())?"j1":"j2")+"_pion_"+facade.getDamier(p, j)[x][y].getValeur()+".bmp");
            }
            if(facade.deplacerPion(p, j, x, y, newX, newY)){
                msg = this.getText("notPlay.finPartie")+" "+p.getGagnant().getIdentifiant();
            }
        }else{
            msg = this.getText("loadPlay.error.abandon");
        }
        return SUCCESS;
    }
}
