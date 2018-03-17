package packagePlateau;

import Exceptions.PionIdentiqueException;
import packageJoueur.Joueur;
import packagePlateau.packageCase.*;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jolan on 07/11/2016.
 */
public class Plateau implements Cloneable, Serializable {
    private final int ROW_SIZE = 10;
    private final int COLUMN_SIZE = 10;
    private Case[][] damier;
    private Case[][] damierRenverser;

    public int getROW_SIZE() {
        return ROW_SIZE;
    }

    public int getCOLUMN_SIZE() {
        return COLUMN_SIZE;
    }

    public Plateau() {
        damier = new Case[ROW_SIZE][COLUMN_SIZE];
        damierRenverser = new Case[ROW_SIZE][COLUMN_SIZE];

        //Placement des obstacles pour le damierRenverser renversé

        //Rivière 1

        damier[ROW_SIZE / 2][2] = new CaseObstacle();
        damier[ROW_SIZE / 2][3] = new CaseObstacle();
        damier[ROW_SIZE / 2][COLUMN_SIZE - 3] = new CaseObstacle();
        damier[ROW_SIZE / 2][COLUMN_SIZE - 4] = new CaseObstacle();

        //Rivière 2

        damier[(ROW_SIZE / 2) - 1][2] = new CaseObstacle();
        damier[(ROW_SIZE / 2) - 1][3] = new CaseObstacle();
        damier[(ROW_SIZE / 2) - 1][COLUMN_SIZE - 3] = new CaseObstacle();
        damier[(ROW_SIZE / 2) - 1][COLUMN_SIZE - 4] = new CaseObstacle();

        //Placement des obstacles pour le damierRenverser

        //Rivière 1

        damierRenverser[ROW_SIZE / 2][2] = new CaseObstacle();
        damierRenverser[ROW_SIZE / 2][3] = new CaseObstacle();
        damierRenverser[ROW_SIZE / 2][COLUMN_SIZE - 3] = new CaseObstacle();
        damierRenverser[ROW_SIZE / 2][COLUMN_SIZE - 4] = new CaseObstacle();

        //Rivière 2

        damierRenverser[(ROW_SIZE / 2) - 1][2] = new CaseObstacle();
        damierRenverser[(ROW_SIZE / 2) - 1][3] = new CaseObstacle();
        damierRenverser[(ROW_SIZE / 2) - 1][COLUMN_SIZE - 3] = new CaseObstacle();
        damierRenverser[(ROW_SIZE / 2) - 1][COLUMN_SIZE - 4] = new CaseObstacle();

        //Initialisation des autres cases en objet CaseVideInitiale où le joueur pourra placer ses pions au départ du jeu

        for (int x = (ROW_SIZE / 2) + 1; x < ROW_SIZE; x++) {
            for (int y = 0; y < COLUMN_SIZE; y++) {
                damier[x][y] = new CaseVideInitiale();
                damierRenverser[x][y] = new CaseVideInitiale();
            }
        }

        //Compléter avec les cases vides qui sera notre No mans land

        for (int x = (ROW_SIZE / 2) - 1; x <= ROW_SIZE / 2; x++) {
            for (int i = 0; i < 2; i++) {
                damier[x][i] = new CaseVide();
                damierRenverser[x][i] = new CaseVide();
            }

            for (int y = 4; y < COLUMN_SIZE - 4; y++) {
                damier[x][y] = new CaseVide();
                damierRenverser[x][y] = new CaseVide();
            }

            for (int j = COLUMN_SIZE - 2; j < COLUMN_SIZE; j++) {
                damier[x][j] = new CaseVide();
                damierRenverser[x][j] = new CaseVide();
            }
        }

        for (int x = 0; x < (ROW_SIZE / 2) - 1; x++) {
            for (int y = 0; y < COLUMN_SIZE; y++) {
                damier[x][y] = new CaseVide();
                damierRenverser[x][y] = new CaseVide();
            }
        }
    }

    /**
     * @return le damier
     */
    public Case[][] getDamier() {
        return damier;
    }

    /**
     * @return le damierRenverser
     */
    public Case[][] getDamierRenverser() {
        return damierRenverser;
    }

    /**
     * Cette méthode nous permet de créer notre damier avec les pions de chaque adversaire placés.
     *
     * @param damier
     * @param renverser true si c'est le damier renversé, false sinon.
     */
    public void creerDamier(Case[][] damier, boolean renverser) {
        if (renverser) {
            for (int i = (ROW_SIZE / 2) + 1; i < ROW_SIZE; i++) {
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    this.damierRenverser[i][j] = damier[i][j];
                    //On met à jour notre damier renversé
                    Map.Entry<Integer, Integer> renverserCoord = this.caseRenverser(i, j);
                    this.damier[renverserCoord.getKey()][renverserCoord.getValue()] = damier[i][j];
                }
            }
        } else {
            for (int i = (ROW_SIZE / 2) + 1; i < ROW_SIZE; i++) {
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    this.damier[i][j] = damier[i][j];
                    //On met à jour notre damier
                    Map.Entry<Integer, Integer> renverserCoord = this.caseRenverser(i, j);
                    this.damierRenverser[renverserCoord.getKey()][renverserCoord.getValue()] = damier[i][j];
                }
            }
        }
    }

    /**
     * Cette méthode permet de savoir si l'on peut ou non effectuer un déplacement dans le damier.
     *
     * @param x         la ligne actuelle de la case.
     * @param y         la colonne actuelle de la case.
     * @param newX      la ligne où l'on souhaite déplacer la case.
     * @param newY      la colonne où l'on souhaite déplacer la case.
     * @param renverser true si c'est le damier renversé false sinon.
     * @return true si l'on peut déplacer cette case false sinon.
     */
    public boolean canMove(int x, int y, int newX, int newY, boolean renverser) {
        Case[][] damierActif;

        //On vérfie quel damier on a à faire
        if (renverser) {
            damierActif = this.damierRenverser;
        } else {
            damierActif = this.damier;
        }

        // Cas où l'on déplace un pion vers un obstacle
        if (damierActif[x][y].getType() == TypeCase.PION && damierActif[newX][newY].getType() == TypeCase.OBSTACLE)
            return false;

        // Cas où l'on déplace un pion vers un pion
        if (damierActif[x][y].getType() == TypeCase.PION && damierActif[newX][newY].getType() == TypeCase.PION) {

            //On vérifie si l'on ne déplace pas le pion d'un joueur sur un autre qui lui appartient
            if (!((CasePion) damierActif[x][y]).getPion().getJoueur().equals(((CasePion) damierActif[newX][newY]).getPion().getJoueur())) {
                //Cas où c'est un pion non déplacable
                if (!((CasePion) damierActif[x][y]).getPion().isMobile()) return false;
                    //Sinon
                else {
                    //Cas où c'est un éclaireur qui peut se déplacer de plusieurs case à l'horizontale ou verticale
                    if (((CasePion) damierActif[x][y]).getPion().moveMoreThanOneCase()) {
                        //S'il se déplace d'une seule case
                        if ((newX == x && (newY == (y - 1) || newY == (y + 1))) || (newY == y && (newX == (x + 1) || newX == (x - 1))))
                            return true;
                            //Sinon s'il se déplace à la verticale de plusieurs cases
                        else if (newX == x) {
                            //On vérifie les cases qu'il y a sur son chemin soient vides
                            for (int i = Math.min(y, newY) + 1; i < Math.max(y, newY); i++) {
                                if (damierActif[x][i].getType() != TypeCase.VIDE) return false;
                            }
                            return true;
                        }
                        //Sinon s'il se déplace à l'horizontale
                        else if (newY == y) {
                            //On vérifie les cases qu'il y a sur son chemin soient vides
                            for (int i = Math.min(x, newX) + 1; i < Math.max(x, newX); i++) {
                                if (damierActif[i][y].getType() != TypeCase.VIDE) return false;
                            }
                            return true;
                        }
                        //Sinon
                        else return false;
                        // Sinon on se déplace que d'une case au voisinage et bien sûre seulement en verticale et horizontale
                    } else
                        return ((newX == x && (newY == (y - 1) || newY == (y + 1))) || (newY == y && (newX == (x + 1) || newX == (x - 1))));
                }
                // Sinon
            } else return false;
        }

        // Cas où l'on déplace un pion vers une case vide
        if (damierActif[x][y].getType() == TypeCase.PION && damierActif[newX][newY].getType() == TypeCase.VIDE) {
            //Si on ne peut pas déplacer la case, on ne la déplace pas
            if (!((CasePion) damierActif[x][y]).getPion().isMobile()) return false;
            //Si c'est un éclaireur qui peut se déplacer de plusieurs case à l'horizontale ou verticale
            if (((CasePion) damierActif[x][y]).getPion().moveMoreThanOneCase()) {
                //S'il se déplace d'une seule case
                if ((newX == x && (newY == (y - 1) || newY == (y + 1))) || (newY == y && (newX == (x + 1) || newX == (x - 1))))
                    return true;
                    //Sinon s'il se déplace à la verticale de plusieurs cases
                else if (newX == x) {
                    //On vérifie les cases qu'il y a sur son chemin soient vides
                    for (int i = Math.min(y, newY) + 1; i < Math.max(y, newY); i++) {
                        if (damierActif[x][i].getType() != TypeCase.VIDE) return false;
                    }
                    return true;
                }
                //Sinon s'il se déplace à l'horizontale
                else if (newY == y) {
                    //On vérifie les cases qu'il y a sur son chemin soient vides
                    for (int i = Math.min(x, newX) + 1; i < Math.max(x, newX); i++) {
                        if (damierActif[i][y].getType() != TypeCase.VIDE) return false;
                    }
                    return true;
                }
                //Sinon
                else return false;
            }
            // Sinon on se déplace que d'une case au voisinage et bien sûre seulement en verticale et horizontale
            else
                return ((newX == x && (newY == (y - 1) || newY == (y + 1))) || (newY == y && (newX == (x + 1) || newX == (x - 1))));
        }

        // Si ce n'est aucun des cas, cela veut dire que l'on veut déplacer une case qui n'a pas de pion...
        return false;

    }

    /**
     * @param x         la ligne de la case du damier dans lequel se trouve le pion que l'on souhaite déplacer.
     * @param y         la colonne de la case du damierdans lequel se trouve le pion que l'on souhaite déplacer.
     * @param newX      la nouvelle ligne de la case du damier où l'on va déplacer notre pion.
     * @param newY      la nouvelle colonne de la case du damier où l'on va déplacer notre pion.
     * @param renverser un booléen nous permettant de savoir s'il on a à faire à notre damier renversé ou non.
     */
    public void deplacerPion(int x, int y, int newX, int newY, boolean renverser, Map<Joueur, List<Pion>> lesPionTues) {
        Case[][] damierActuel = (renverser?damierRenverser:damier);

        //on garde depart et arrivé
        Case caseCourante = damierActuel[x][y];
        Case caseArrivee = damierActuel[newX][newY];
        //la case depart sera vide quoi qu'il arrive
        damierActuel[x][y] = new CaseVide();

        if(damierActuel[newX][newY].getType().equals(TypeCase.VIDE)){
            // si case vide a l'arrivée, rien a faire, on met juste le pion
            damierActuel[newX][newY] = caseCourante;
        }else{
            //sinon on combat !
            Pion pionCourant = ((CasePion)caseCourante).getPion();
            Pion pionAdverse = ((CasePion)caseArrivee).getPion();
            try {
                if(pionCourant.canKill(pionAdverse)){
                    //si il peut kill, alors on remplace
                    damierActuel[newX][newY] = caseCourante;
                    //On ajoute dans la List des pions tués en question
                    lesPionTues.get(pionAdverse.getJoueur()).add(pionAdverse);
                }else{
                    //On ajoute dans la List des pions tués en question
                    lesPionTues.get(pionCourant.getJoueur()).add(pionCourant);
                }
                //sinon on fait rien ! Le pion attaquant disparait simplement
            } catch (PionIdentiqueException e) {
                //la case arrivee est vide si y'a égalite
                damierActuel[newX][newY] = new CaseVide();
                //On ajoute dans la List des pions tués en question
                lesPionTues.get(pionCourant.getJoueur()).add(pionCourant);
                //On ajoute dans la List des pions tués en question
                lesPionTues.get(pionAdverse.getJoueur()).add(pionAdverse);
            }
        }
        if(renverser) updateDamierFromRenverser();
        else updateRenverserFromDamier();
    }

    /**
     * Cette méthode calcule les coordonnées de la case dans le damierinverse.
     *
     * @param x la ligne de la case recherché.
     * @param y la colonne de la case recherché.
     * @return
     */
    private Map.Entry<Integer, Integer> caseRenverser(int x, int y) {
        int newX, newY;

        // Calcul des coordonnées
        newX = Math.abs((ROW_SIZE - 1) - x);
        newY = Math.abs((ROW_SIZE - 1) - y);

        return new AbstractMap.SimpleEntry<Integer, Integer>(newX, newY);
    }

    /**
     * Met a jour le tableau reverse a partir du damier
     */
    public void updateRenverserFromDamier() {
        for (int i = 0; i < getROW_SIZE(); i++) {
            for (int j = 0; j < getCOLUMN_SIZE(); j++) {
                damierRenverser[getROW_SIZE() - 1 - i][getROW_SIZE() - 1 - j] = damier[i][j];
            }
        }
    }

    /**
     * Met a jour le tableau damier a partir du reverse
     */
    public void updateDamierFromRenverser() {
        for (int i = 0; i < getROW_SIZE(); i++) {
            for (int j = 0; j < getCOLUMN_SIZE(); j++) {
                damier[getROW_SIZE() - 1 - i][getROW_SIZE() - 1 - j] = damierRenverser[i][j];
            }
        }
    }

    public Plateau clone() {
        Plateau p = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            p = (Plateau) super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }

        // on renvoie le clone
        return p;
    }
}
