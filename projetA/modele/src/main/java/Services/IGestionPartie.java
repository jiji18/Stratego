package Services;

import Exceptions.AucunePartieException;
import Exceptions.JoueurOccupeException;
import Exceptions.PartieCompleteException;
import Exceptions.PartieInexistanteException;
import packageJoueur.Joueur;
import packagePartie.Partie;

import java.util.List;

/**
 * @author Anthony
 */
public interface IGestionPartie {

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
     * @param j
     * @return la partie du joueur où il est attendu.
     * @throws PartieInexistanteException si le joueur qui a créé la partie a abandonné.
     * @throws AucunePartieException s'il n'y a aucune partie privée
     *
     */
    Partie rejoindrePartiePrivee(Joueur j, Partie p) throws PartieInexistanteException, PartieCompleteException, AucunePartieException;

    /**
     * @param j un joueur
     * @return la liste des parties privees dans lesquels le joueur est invite
     * @throws PartieInexistanteException si aucune invitation a été envoyé
     * @throws AucunePartieException s'il n'y a aucune partie privée
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
     *  Cette méthode permet de quitter une partie
     * @param j le joueur.
     * @throws PartieInexistanteException
     */
    void abandonnerPartie(Joueur j) throws PartieInexistanteException;

    /**
     * Cette méthode permet de voir les parties publics.
     *
     * @return une liste contenant ces parties.
     * @throws AucunePartieException s'il n'y a aucune partie public.
     */
    List<Partie> getLesPartiesPublics() throws AucunePartieException;

    /**
     * Cette méthode permet de récupérer la partie finies du joueur passé en paramètre.
     * @param j
     * @return
     * @throws AucunePartieException
     */
    Partie getLesPartiesFinies(Joueur j) throws AucunePartieException;

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
     * Refuse un défi
     *
     * @param j un joueur qui va refuser l'invation
     * @param p une partie privee
     */
    void rejeterInvitation(Joueur j, Partie p);

    /**
     * Supprimer une partie
     *
     * @param j le joueur créateur d'une partie
     * @param p la partie en question
     * @return true si la partie a pu etre supprimée, faux sinon
     */
    boolean removePartie(Joueur j, Partie p);

    /**
     * Quitte le mode observateur pour un joueur dans une partie publique
     *
     * @param p une partie publique
     * @param j un joueur observateur de la partie
     */
    void quitterObservation(Partie p, Joueur j);

    /**
     * Cette méthode permet de voir les parties privées.
     * @return une liste contenant ces parties.
     *
     */
    List<Partie> getLesPartiesPrivees() throws AucunePartieException;
}
