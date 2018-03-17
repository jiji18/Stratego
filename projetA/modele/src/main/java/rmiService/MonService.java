package rmiService;

import Exceptions.*;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Antho
 */
public interface MonService extends Remote {

    public final String serviceName = "StrategoDAO";

    abstract void placerPions(int[][] tab, String identifiant) throws RemoteException;

    abstract boolean[][] deplacementPossible(String identifiant, int x, int y) throws RemoteException;

    abstract boolean estCliquable(String identifiant, int x, int y) throws RemoteException;

    abstract Case[][] getDamier(String identifiant) throws RemoteException;

    abstract List<Pion> getLesPionsRestants(String identifiant) throws RemoteException;

    abstract int getTempsMax() throws RemoteException;

    abstract boolean deplacerPion(String identifiant, int x, int y, int newX, int newY) throws RemoteException;

    abstract List<Joueur> getJoueursConnectes() throws RemoteException;

    abstract List<Joueur> getJoueurs() throws RemoteException;

    abstract Joueur getJoueurConnectes(String identifiant) throws RemoteException;

    abstract Joueur getJoueur(String identifiant) throws RemoteException;

    abstract Joueur inscription(String id, String pass) throws RemoteException, PseudoDejaUtiliseException;

    abstract Joueur connexion(String id, String pass) throws RemoteException, ErreurConnexionException;

    abstract Joueur deconnexion(String id) throws RemoteException, ErreurDeconnexionException;

    abstract Partie creerPartie(String identifiant) throws RemoteException;

    abstract Partie defier(String id1, String id2) throws RemoteException, JoueurOccupeException;

    abstract boolean estAttenduPourJouer(String identifiant) throws RemoteException;

    abstract Partie rejoindrePartiePrivee(String identifiant, String idJ1) throws RemoteException, PartieInexistanteException, PartieCompleteException, AucunePartieException;

    abstract void abandonnerPartie(String identifiant) throws RemoteException, PartieInexistanteException;

    abstract List<Partie> getInvitations(String identifiant) throws RemoteException, PartieInexistanteException, AucunePartieException;

    abstract Partie rejoindrePartieObservateur(String identifiant, String idJ1) throws RemoteException;

    abstract boolean partieEnAttente(String idJ1) throws RemoteException;

    abstract Partie rejoindrePartie(String identifiant, String idJ1) throws RemoteException, PartieCompleteException, PartieInexistanteException;

    abstract List<Partie> getLesPartiesPublics() throws RemoteException, AucunePartieException;

    abstract List<Partie> getLesPartiesFinies() throws RemoteException, AucunePartieException;

    abstract Partie getPartie(String identifiant) throws RemoteException;

    abstract boolean removePartie(String id) throws RemoteException;

    abstract List<Pion> getListePionsDepart(String id) throws RemoteException;

    abstract void placerPions(Case[][] plate, String id) throws RemoteException;

    abstract void rejeterInvitation(String identifiant, String idJ1) throws RemoteException;

    abstract void quitterObservation(String identifiant, String idJ1) throws RemoteException;

    abstract void modifierMotDePasse(String identifiant, String newMotDePasse) throws RemoteException;

    abstract Partie getLesPartiesFinies(String identifiant) throws RemoteException, AucunePartieException;

    abstract void passerSonTour(String identifiant) throws RemoteException;
}
