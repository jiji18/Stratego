package action.ajax;

import packageJoueur.Joueur;
import packagePartie.Partie;
import action.SuperClass;

/**
 * Created by jihad on 16/12/16.
 */
public class DeplacementPossibleAction extends SuperClass {

    private int x;
    private int y;
    private boolean[][] canMove;

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

    public boolean[][] getCanMove() {
        return canMove;
    }

    public void setCanMove(boolean[][] canMove) {
        this.canMove = canMove;
    }

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur)sessionMap.get("joueur");
        Partie p = facade.getPartie(j);
        canMove = facade.deplacementPossible(p ,j , x, y);
        return SUCCESS;
    }
}
