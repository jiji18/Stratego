package Services;


import Exceptions.ErreurConnexionException;
import Exceptions.ErreurDeconnexionException;
import Exceptions.PseudoDejaUtiliseException;
import packageJoueur.Joueur;

import java.util.List;

/**
 * @author Anthony
 */
public interface IGestionCompte {
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
     * Change le mot de passe du joueur courant
     *
     * @param j             joueur courant
     * @param newMotDePasse nouveau mot de passe pour modif
     */
    void modifierMotDePasse(Joueur j, String newMotDePasse);

    void sauvegarderJoueurs();

    /**
     * Charge les joueurs stockés en base
     *
     */
    void chargerJoueurs();
}
