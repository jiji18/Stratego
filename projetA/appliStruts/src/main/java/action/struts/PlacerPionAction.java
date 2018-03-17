package action.struts;


import packageJoueur.Joueur;
import action.SuperClass;

/**
 * Created by jihad on 12/12/16.
 */
public class PlacerPionAction extends SuperClass {
    private String[] valeurs;

    public String[] getValeurs() {
        return valeurs;
    }

    public void setValeurs(String[] valeurs) {
        this.valeurs = valeurs;
    }

    private int[][] damier(){
        int[][] damier = new int[10][10];
        int indice = 0;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                damier[i][j] = Integer.parseInt(valeurs[indice]);
                indice++;
            }
        }
        return damier;
    }

    @Override
    public String execute() throws Exception {
        Joueur j = (Joueur) sessionMap.get("joueur");
        facade.placerPions(this.damier(), facade.getPartie(j), j);
        if(j.equals(facade.getPartie(j).getJ2())) return "notPlay";
        else return "notPlacerPion";
    }
}
