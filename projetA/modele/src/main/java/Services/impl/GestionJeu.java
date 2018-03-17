package Services.impl;

import Services.IGestionJeu;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.AbstractPartie;
import packagePartie.Partie;
import packagePartie.PartiePublique;
import packagePlateau.Pion;
import packagePlateau.Plateau;
import packagePlateau.TypePion;
import packagePlateau.packageCase.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jihad & Anthony
 */
public class GestionJeu implements IGestionJeu, Serializable {

    private final int TEMP_MAX = 15;
    private Map<Joueur, Partie> lesParties;
    private List<Partie> lesPartiesFinies;

    public GestionJeu(Map<Joueur, Partie> lesParties, List<Partie> partiesFinies) {
        this.lesParties = lesParties;
        this.lesPartiesFinies = partiesFinies;
    }

    /**
     * Cette méthode renvoie une case correspondant à sa valeur.
     *
     * @param i la valeur de la case recherché.
     * @param j un joueur.
     * @return une case.
     */
    private Case caseByValeur(int i, Joueur j) {

        Case c = null;

        if (i == TypeCase.VIDE.getValeur()) c = new CaseVide();
        if (i == TypeCase.OBSTACLE.getValeur()) c = new CaseObstacle();
        else {
            if (i == TypePion.MARECHAL.getValeur()) c = new CasePion(new Pion(TypePion.MARECHAL, j));
            if (i == TypePion.GENERAL.getValeur()) c = new CasePion(new Pion(TypePion.GENERAL, j));
            if (i == TypePion.COLONEL.getValeur()) c = new CasePion(new Pion(TypePion.COLONEL, j));
            if (i == TypePion.COMMANDANT.getValeur()) c = new CasePion(new Pion(TypePion.COMMANDANT, j));
            if (i == TypePion.CAPITAINE.getValeur()) c = new CasePion(new Pion(TypePion.CAPITAINE, j));
            if (i == TypePion.LIEUTENANT.getValeur()) c = new CasePion(new Pion(TypePion.LIEUTENANT, j));
            if (i == TypePion.SERGENT.getValeur()) c = new CasePion(new Pion(TypePion.SERGENT, j));
            if (i == TypePion.DEMINEUR.getValeur()) c = new CasePion(new Pion(TypePion.DEMINEUR, j));
            if (i == TypePion.ECLAIREUR.getValeur()) c = new CasePion(new Pion(TypePion.ECLAIREUR, j));
            if (i == TypePion.ESPION.getValeur()) c = new CasePion(new Pion(TypePion.ESPION, j));
            if (i == TypePion.BOMBE.getValeur()) c = new CasePion(new Pion(TypePion.BOMBE, j));
            if (i == TypePion.DRAPEAU.getValeur()) c = new CasePion(new Pion(TypePion.DRAPEAU, j));
        }

        return c;
    }

    @Override
    public void placerPions(int[][] tab, Partie p, Joueur joueur) {
        Plateau plateau = p.getPlateau();
        Case[][] res = new Case[plateau.getROW_SIZE()][plateau.getCOLUMN_SIZE()];
        for (int i = 0; i < plateau.getROW_SIZE(); i++) {
            for (int j = 0; j < plateau.getCOLUMN_SIZE(); j++) {
                res[i][j] = caseByValeur(tab[i][j], joueur);
            }
        }
        plateau.creerDamier(res, joueur.equals(p.getJ2()));

        //On change les status de joueurs
        joueur.setStatut(EnumStatut.WAITINGPLAY);
        if (joueur.equals(p.getJ1()))
            p.getJ2().setStatut(EnumStatut.PLAYING);
        else
            p.getJ1().setStatut(EnumStatut.PLAYING);
    }

    @Override
    public void placerPions(Case[][] plate, Partie p, Joueur joueur) {
        Plateau plateau = p.getPlateau();
        for (int i = plateau.getROW_SIZE() - 4; i < plateau.getROW_SIZE(); i++) {
            for (int j = 0; j < plateau.getCOLUMN_SIZE(); j++) {
                if (joueur.equals(p.getJ1())) {
                    p.getPlateau().getDamier()[i][j] = plate[i][j];
                } else {
                    p.getPlateau().getDamierRenverser()[i][j] = plate[i][j];
                }
            }
        }
        if (joueur.equals(p.getJ1())) p.getPlateau().updateRenverserFromDamier();
        else p.getPlateau().updateDamierFromRenverser();
    }

    /**
     * Cett méthode permet de savoir si une partie est fini ou non.
     *
     * @param p
     * @return
     */
    private boolean isFinished(Partie p) {
        Plateau plate = p.getPlateau();
        List<Pion> pionTues1 = p.getLesPionsTues().get(p.getJ1());
        List<Pion> pionTues2 = p.getLesPionsTues().get(p.getJ2());

        //Cas d'une découverte d'un drapeau
        if (pionTues1.stream().anyMatch(x -> x.getType() == TypePion.DRAPEAU)) {
            p.setGagnant(p.getJ2());
            p.getJ2().gagne();
            p.getJ1().perdu();
            removePartiesAnterieurs(p.getJ1());
            lesPartiesFinies.add(p.clone());
            return true;
        }

        if (pionTues2.stream().anyMatch(x -> x.getType() == TypePion.DRAPEAU)) {
            p.setGagnant(p.getJ1());
            p.getJ1().gagne();
            p.getJ2().perdu();
            removePartiesAnterieurs(p.getJ1());
            lesPartiesFinies.add(p.clone());
            return true;
        }

        //Cas où il n'y a plus de soldats dans l'une des équipes
        if (pionTues1.size() == 33) {
            p.setGagnant(p.getJ2());
            p.getJ2().gagne();
            p.getJ1().perdu();
            removePartiesAnterieurs(p.getJ1());
            lesPartiesFinies.add(p.clone());
            return true;
        } else if (pionTues2.size() == 33) {
            p.setGagnant(p.getJ1());
            p.getJ1().gagne();
            p.getJ2().perdu();
            removePartiesAnterieurs(p.getJ1());
            lesPartiesFinies.add(p.clone());
            return true;
        }

        //sinon
        return false;
    }

    private void removePartiesAnterieurs(Joueur j) {
        for (int i = 0; i < lesPartiesFinies.size(); i++) {
            if (j.equals(lesPartiesFinies.get(i).getJ1()) || j.equals(lesPartiesFinies.get(i).getJ2())) {
                lesPartiesFinies.remove(i);
                i--;
            } else if (!lesPartiesFinies.get(i).isPrivate()) {
                if (((PartiePublique) lesPartiesFinies.get(i)).getObservateurs().contains(j)) {
                    lesPartiesFinies.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public boolean[][] deplacementPossible(Partie p, Joueur j, int x, int y) {

        //Test pour savoir s'il on doit renverser le plateau ou non
        boolean renverser = j.equals(((AbstractPartie) p).getJ2());

        int tailleLigne = p.getPlateau().getROW_SIZE();
        int tailleColonne = p.getPlateau().getCOLUMN_SIZE();

        boolean tablePossible[][] = new boolean[tailleLigne][tailleColonne];

        //Initialisation du plateau à False
        for (int i = 0; i < tailleLigne; i++) {
            for (int h = 0; h < tailleColonne; h++) {
                tablePossible[i][h] = false;
            }
        }

        //Met la case à true si le retour de la fonction canMove est vrai donc si on peut se deplacer
        //Cas des cases colonnes adjacentes
        for (int col = 0; col < tailleColonne; col++) {
            tablePossible[x][col] = p.getPlateau().canMove(x, y, x, col, renverser);
        }
        //Cas des cases lignes adjacentes
        for (int row = 0; row < tailleLigne; row++) {
            tablePossible[row][y] = p.getPlateau().canMove(x, y, row, y, renverser);
        }

        return tablePossible;
    }

    @Override
    public boolean deplacerPion(Partie p, Joueur j, int x, int y, int newX, int newY) {
        Joueur j1 = p.getJ1();
        Joueur j2 = p.getJ2();

        //Savoir si l'on crée le damier renversé ou non.
        boolean renverser = j.equals(j2);

        p.getPlateau().deplacerPion(x, y, newX, newY, renverser, p.getLesPionsTues());

        if (!isFinished(p)) {
            //On change les status de joueurs
            j.setStatut(EnumStatut.WAITINGPLAY);
            if (j.equals(j1)) j2.setStatut(EnumStatut.PLAYING);
            else j1.setStatut(EnumStatut.PLAYING);
            return false;
        } else {
            j1.setStatut(EnumStatut.CONNECTED);
            j2.setStatut(EnumStatut.CONNECTED);
            Partie partie = p.clone();
            lesParties.remove(j);
            if (j.equals(partie.getJ1())) {
                lesParties.remove(partie.getJ2());
            } else {
                lesParties.remove(partie.getJ1());
            }
            if (!p.isPrivate()) {
                for (Joueur joueur : ((PartiePublique) partie).getObservateurs()) {
                    joueur.setStatut(EnumStatut.CONNECTED);
                    lesParties.remove(joueur);
                }
            }
            return true;
        }
    }

    @Override
    public int getTEMP_MAX() {
        return this.TEMP_MAX;
    }

    @Override
    public List<Pion> getLesPionsRestants(Partie p, Joueur j) {
        List<Pion> res = this.getListePionsDepart(j);
        Map<Integer, Integer> lesPionsTues = new HashMap<>();
        for (Pion pion : p.getLesPionsTues().get(j)) {
            if (lesPionsTues.containsKey(pion.getValeur())) {
                lesPionsTues.put(pion.getValeur(), lesPionsTues.get(pion.getValeur()) + 1);
            } else {
                lesPionsTues.put(pion.getValeur(), 1);
            }
        }
        for (int i = 0; i < res.size(); i++) {
            if (lesPionsTues.get(res.get(i).getValeur()) != null) {
                if (lesPionsTues.get(res.get(i).getValeur()) == 0) {
                    lesPionsTues.remove(res.get(i).getValeur());
                } else {
                    lesPionsTues.put(res.get(i).getValeur(), lesPionsTues.get(res.get(i).getValeur()) - 1);
                    res.remove(i);
                    i--;
                }
            }
        }
        return res;
    }

    @Override
    public List<Pion> getListePionsDepart(Joueur j) {
        List<Pion> result = new ArrayList<Pion>();
        //On récupère les pions de départ
        for (TypePion type : TypePion.values()) {
            for (int i = 0; i < type.getOccurrence(); i++)
                result.add(new Pion(type, j));
        }
        return result;
    }

    @Override
    public Case[][] getDamier(Partie p, Joueur j) {
        if (j.equals(p.getJ1()))
            return p.getPlateau().getDamier();
        else
            return p.getPlateau().getDamierRenverser();
    }

    @Override
    public boolean estCliquable(Partie p, Joueur j, int x, int y) {
        return ((CasePion) p.getPlateau().getDamier()[x][y]).getPion().getJoueur().equals(j);
    }

    @Override
    public void passerSonTour(Partie p, Joueur j) {
        Joueur j1 = p.getJ1();
        Joueur j2 = p.getJ2();
        j.setStatut(EnumStatut.WAITINGPLAY);
        if (j.equals(j1)) j2.setStatut(EnumStatut.PLAYING);
        else j1.setStatut(EnumStatut.PLAYING);
    }

}
