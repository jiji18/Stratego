package packagePlateau;


import java.io.Serializable;

/**
 * Created by Jolan on 07/11/2016.
 */
public enum TypePion implements Serializable {
    MARECHAL(10, 1), GENERAL(9, 1), COLONEL(8, 2),
    COMMANDANT(7, 3), CAPITAINE(6, 4), LIEUTENANT(5, 4),
    SERGENT(4, 4), DEMINEUR(3, 5), ECLAIREUR(2, 8),
    ESPION(1, 1), DRAPEAU(0, 1), BOMBE(11, 6);

    private int valeur;
    private int occurrence;


    TypePion(int i, int i1) {
        this.valeur = i;
        this.occurrence = i1;
    }

    public int getValeur() {
        return valeur;
    }

    public int getOccurrence() {
        return occurrence;
    }


}
