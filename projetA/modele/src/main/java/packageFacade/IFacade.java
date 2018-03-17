package packageFacade;

import Exceptions.*;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;

import java.util.List;

/**
 * @author Jolan
 */
public interface IFacade {

    /**
     * Cette méthode permet de placer les pions d'un joueur à l'initialisation.
     * Le tableau en paramètre sera bien évidemment compléter là il y a les cases initiales et seulement là.
     * Gérer aussi côté JSP.
     *
     * @param tab le tableau contenant les valeurs des pions où ils seront placés.
     * @param p   la partie courante.
     * @param j   le joueur courant stocké en session.
     */
    void placerPions(int[][] tab, Partie p, Joueur j);

    /**
     * Une méthode qui permet de creer un plateau de valeur booleene
     * pour savoir quels sont les déplacement possible d'un pion donné par
     * ses coordonnées en entrée.
     *
     * @param p la partie courante.
     * @param j
     * @param x
     * @param y
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
    int getTempsMax();

    /**
     * @param p    Partie dans laquelle le mouvement sera effectué
     * @param j    Joueur qui effectue le deplacement
     * @param x    coord ligne de depart
     * @param y    coord colonne de depart
     * @param newX coord ligne d'arrive
     * @param newY coord colonne d'arrive
     * @return true si la partie est gagne, false sinon
     */
    boolean deplacerPion(Partie p, Joueur j, int x, int y, int newX, int newY);

    /**
     * Recupere les joueurs actuellement connectés au modele
     *
     * @return une liste de joueur
     */
    List<Joueur> getJoueursConnectes();

    /**
     * Recupere la liste des joueurs inscrits au modele
     *
     * @return une liste de joueurs
     */
    List<Joueur> getJoueurs();

    /**
     * @param identifiant
     * @return
     */
    Joueur getJoueurConnectes(String identifiant);

    /**
     * @param identifiant
     * @return
     */
    Joueur getJoueur(String identifiant);

    /**
     * Inscrit un joueur dans le modele
     *
     * @param id   pseudo de l'utilisateur
     * @param pass mot de passe de l'utilisateur
     * @return un joueur correspondant
     * @throws PseudoDejaUtiliseException si qqn possede deja ce psuedo
     */
    Joueur inscription(String id, String pass) throws PseudoDejaUtiliseException;

    /**
     * Connecte un joueur dans le modele
     *
     * @param id   pseudo de l'utilisateur
     * @param pass mot de passe de l'utilisateur
     * @return le joueur connecté
     * @throws ErreurConnexionException si le joueur est deja connecté, ou si le joueur n'existe pas
     */
    Joueur connexion(String id, String pass) throws ErreurConnexionException;

    /**
     * Deconnecte un joueur du modele
     *
     * @param id pseudo de l'utilisateur
     * @return le joueur deconnecté
     * @throws ErreurDeconnexionException si la personne est deja deconnectée
     */
    Joueur deconnexion(String id) throws ErreurDeconnexionException;

    /**
     * Cette méthode Créer une nouvelle partie  public.
     * Ici le joueur est bien évidemment déjà connecté, ce que l'on gérera dans notre JSP.
     *
     * @param j1 le joueur qui instancie la nouvelle partie
     * @return une partie composée de j1
     */
    Partie creerPartie(Joueur j1);

    /**
     * Cette méthode permet d'organiser un défis entre 2 joueurs dans une partie privé.
     * Le joueur qui souhaite en défier un autre est bien sûre connecté, ce que l'on gérera
     * dans notre JSP.
     *
     * @param j1 un joueur défieur
     * @param j2 un joueur défié
     * @throws JoueurOccupeException si le joueur défié n'est pas disponible, dans l'etat connecté (en attente)
     */
    Partie defier(Joueur j1, Joueur j2) throws JoueurOccupeException;

    /**
     * Cette méthode permet de savoir si un joueur est attendu pour jouer ou non.
     * Ici le joueur est bien évidemment déjà connecté, ce que l'on gérera dans notre JSP.
     *
     * @param j
     * @return true si le joueur est attendu pour jouer false sinon.
     */
    boolean estAttenduPourJouer(Joueur j);

    /**
     * Cette méthode va être utilisé pour ajouter un joueur invité à rejoindre une partie
     * privé. Cela sera vérifier dans notre JSP aussi.
     *
     * @param j
     * @return la partie du joueur où il est attendu.
     * @throws PartieInexistanteException si le joueur qui a créé la partie a abandonné.
     * @throws AucunePartieException      s'il nexiste aucune partie.
     */
    Partie rejoindrePartiePrivee(Joueur j, Partie p) throws PartieInexistanteException, PartieCompleteException, AucunePartieException;

    /**
     * Cette méthode permet de quitter une partie
     *
     * @param j le joueur.
     * @throws PartieInexistanteException
     */
    void abandonnerPartie(Joueur j) throws PartieInexistanteException;

    /**
     * @param j un joueur
     * @return la liste des parties privees dans lesquels le joueur est invite
     * @throws PartieInexistanteException si aucune invitation a été envoyé
     * @throws AucunePartieException      s'il nexiste aucune partie.
     */
    List<Partie> getInvitations(Joueur j) throws PartieInexistanteException, AucunePartieException;

    /**
     * Méthode pour rejoindre une partie en observation
     *
     * @param j joueur qui souhaite rejoindre
     * @param p partie a rejoindre
     * @return
     */
    Partie rejoindrePartieObservateur(Joueur j, Partie p);

    /**
     * Cette méthode permet de savoir si une partie p est en attente ou non.
     *
     * @param p la partie.
     * @return true si une partie est en attente false sinon.
     */
    boolean partieEnAttente(Partie p);

    /**
     * Cette méthode permet a un joueur de rejoindre une partie public.
     * On gèrera en JSP les parties privés comme non rejoignable.
     *
     * @param j le joueur qui souhaite rejoindre.
     * @param p la partie a rejoindre.
     * @return la partie que le joueur rejoint.
     * @throws PartieCompleteException    si la partie que le joueur cherche a rejoindre est déjà pleine.
     * @throws PartieInexistanteException si la partie n'existe plus.
     */
    Partie rejoindrePartie(Joueur j, Partie p) throws PartieCompleteException, PartieInexistanteException;

    /**
     * Cette méthode permet de récupérer la partie finies du joueur passé en paramètre.
     *
     * @param j
     * @return
     * @throws AucunePartieException
     */
    Partie getLesPartiesFinies(Joueur j) throws AucunePartieException;

    /**
     * Cette méthode permet de voir les parties publics.
     *
     * @return une liste contenant ces parties.
     * @throws AucunePartieException s'il n'y a aucune partie public.
     */
    List<Partie> getLesPartiesPublics() throws AucunePartieException;

    /**
     * Cette méthode permet de voir les parties finies.
     *
     * @return une liste contenant ces parties.
     * @throws AucunePartieException s'il n'y a aucune partie finie.
     */
    List<Partie> getLesPartiesFinies() throws AucunePartieException;

    /**
     * Recupere la partie d'un joueur
     *
     * @param j un joueur dont on veut la partie
     * @return une partie dans lequel le joueur est présent
     */
    Partie getPartie(Joueur j);

    /**
     * Supprimer une partie
     *
     * @param j le joueur créateur d'une partie
     * @param p la partie en question
     * @return true si la partie a pu etre supprimée, faux sinon
     */
    boolean removePartie(Joueur j, Partie p);

    /**
     * Cette méthode nous retourne les pions a placer au départ d'une partie
     *
     * @param j le joueur pour qui on veut creer un set de jeu
     * @return une liste de pion
     */
    List<Pion> getListePionsDepart(Joueur j);

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
     * Refuse une demande de jeu
     *
     * @param p partie privee dans laquelle un joueur est invite
     * @param j un joueur invite dans une partie
     */
    void rejeterInvitation(Joueur j, Partie p);

    /**
     * Quitte le mode observateur pour un joueur dans une partie publique
     *
     * @param p une partie publique
     * @param j un joueur observateur de la partie
     */
    void quitterObservation(Partie p, Joueur j);

    /**
     * Change le mot de passe du joueur courant
     *
     * @param j             joueur courant
     * @param newMotDePasse nouveau mot de passe pour modif
     */
    void modifierMotDePasse(Joueur j, String newMotDePasse);

    void passerSonTour(Partie p, Joueur j);
}
