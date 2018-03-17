package Services;

import Exceptions.DeplacementImpossibleException;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;

import java.util.List;

/**
 * @author Jihad
 */
public interface IGestionJeu {

    /**
     * Cette méthode permet de placer les pions d'un joueur à l'initialisation.
     * Le tableau en paramètre sera bien évidemment compléter là il y a les cases initiales et seulement là.
     * Gérer aussi côté JSP.
     *
     * @param tab    le tableau contenant les valeurs des pions où ils seront placés.
     * @param p      la partie courante.
     * @param joueur le joueur courant stocké en session.
     */
    void placerPions(int[][] tab, Partie p, Joueur joueur);

    /**
     * Cette méthode permet de déplacer un pion dans le damier de la partie où il joue actuellement
     *
     * @param p    la partie courante.
     * @param j    le joueur courant qui est stocké en session.
     * @param x    la ligne actuelle du pion.
     * @param y    la colonne acteulle du pion.
     * @param newX la ligne dans laquelle il va être déplacé.
     * @param newY la colonne dans laquelle il va être déplacé.
     * @return true si la partie est terminé, false sinon.
     * @throws DeplacementImpossibleException
     */
    boolean deplacerPion(Partie p, Joueur j, int x, int y, int newX, int newY);

    /**
     * Cette méthode permet de placer les pions d'un joueur à l'initialisation.
     * Le tableau en paramètre sera bien évidemment compléter là il y a les cases initiales et seulement là.
     *
     * @param plate  le tableau des Cases que le joueur souhaite exporter
     * @param p      la partie courante
     * @param joueur le joueur courant qui palce ses pions
     */
    void placerPions(Case[][] plate, Partie p, Joueur joueur);

    /**
     * Une méthode qui permet de creer un plateau de valeur booleene
     * pour savoir quels sont les déplacement possible d'un pion donné par
     * ses coordonnées en entrée.
     *
     * @param p la partie courante.
     * @param j le joueur courant
     * @param x la ligne de la case courante du pion
     * @param y la colonne de la case courante du pion
     * @return Le tableau de booleen indiquant les valeurs de deplacement possible
     * pour le joueur j.
     */
    boolean[][] deplacementPossible(Partie p, Joueur j, int x, int y);

    /**
     * Cette méthode permet de savoir si le joueur peut accéder ou non à la case.
     *
     * @param p la partie courante.
     * @param j le joueur courant.
     * @param x la ligne de le case.
     * @param y la colonne de la case.
     * @return true s'il le peut, false sinon.
     */
    boolean estCliquable(Partie p, Joueur j, int x, int y);

    /**
     * Cette méthode nous retourne les pions a placer au départ d'une partie
     *
     * @param j le joueur pour qui on veut creer un set de jeu
     * @return une liste de pion
     */
    List<Pion> getListePionsDepart(Joueur j);

    /**
     * Cette méthode permet d'afficher le damier de la partie du joueur courant.
     *
     * @param p la partie courante.
     * @param j le joueur courant stocké en session.
     * @return le damier.
     */
    Case[][] getDamier(Partie p, Joueur j);

    /**
     * Cette méthode nous retourne les pions restants d'un joueur
     *
     * @param p la partie courante.
     * @param j le joueur courant.
     * @return
     */
    List<Pion> getLesPionsRestants(Partie p, Joueur j);


    /**
     * Indique le temps maximum en seconde qu'un joueur a pour jouer.
     *
     * @return le temps max pour jouer.
     */
    int getTEMP_MAX();

    /**
     * Modifie les status quand le joueur passe son tour
     * en ne jouant pas avant la fin de son temps imparti
     */
    void passerSonTour(Partie p, Joueur j);

}
