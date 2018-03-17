package rmiService;

import Exceptions.*;
import packageFacade.Facade;
import packageFacade.IFacade;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Antho
 */
public class MonServiceImpl implements MonService {

    private IFacade maDAO = Facade.getInstance();

    @Override
    public void placerPions(int[][] tab, String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        maDAO.placerPions(tab, p, j);
    }

    @Override
    public boolean[][] deplacementPossible(String identifiant, int x, int y) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        return maDAO.deplacementPossible(p, j, x, y);
    }

    @Override
    public boolean estCliquable(String identifiant, int x, int y) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        return maDAO.estCliquable(p, j, x, y);
    }

    @Override
    public Case[][] getDamier(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        return maDAO.getDamier(p, j);
    }

    @Override
    public List<Pion> getLesPionsRestants(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        return maDAO.getLesPionsRestants(p, j);
    }

    @Override
    public int getTempsMax() throws RemoteException {
        return this.maDAO.getTempsMax();
    }

    @Override
    public boolean deplacerPion(String identifiant, int x, int y, int newX, int newY) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        return maDAO.deplacerPion(p, j, x, y, newX, newY);
    }

    @Override
    public List<Joueur> getJoueursConnectes() throws RemoteException {
        return this.maDAO.getJoueursConnectes();
    }

    @Override
    public List<Joueur> getJoueurs() throws RemoteException {
        return this.maDAO.getJoueurs();
    }

    @Override
    public Joueur getJoueurConnectes(String identifiant) throws RemoteException {
        return this.maDAO.getJoueurConnectes(identifiant);
    }

    @Override
    public Joueur getJoueur(String identifiant) throws RemoteException {
        return this.maDAO.getJoueur(identifiant);
    }

    @Override
    public Joueur inscription(String id, String pass) throws RemoteException, PseudoDejaUtiliseException {
        return this.maDAO.inscription(id, pass);
    }

    @Override
    public Joueur connexion(String id, String pass) throws RemoteException, ErreurConnexionException {
        return this.maDAO.connexion(id, pass);
    }

    @Override
    public Joueur deconnexion(String id) throws RemoteException, ErreurDeconnexionException {
        return this.maDAO.deconnexion(id);
    }

    @Override
    public Partie creerPartie(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        return maDAO.creerPartie(j);
    }

    @Override
    public Partie defier(String id1, String id2) throws RemoteException, JoueurOccupeException {
        Joueur j1 = maDAO.getJoueur(id1);
        Joueur j2 = maDAO.getJoueur(id2);
        return maDAO.defier(j1, j2);
    }

    @Override
    public boolean estAttenduPourJouer(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        return maDAO.estAttenduPourJouer(j);
    }

    @Override
    public Partie rejoindrePartiePrivee(String identifiant, String idJ1) throws RemoteException, PartieInexistanteException, PartieCompleteException, AucunePartieException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        return maDAO.rejoindrePartiePrivee(j, p);
    }

    @Override
    public void abandonnerPartie(String identifiant) throws RemoteException, PartieInexistanteException {
        Joueur j = maDAO.getJoueur(identifiant);
        maDAO.abandonnerPartie(j);
    }

    @Override
    public List<Partie> getInvitations(String identifiant) throws RemoteException, PartieInexistanteException, AucunePartieException {
        Joueur j = maDAO.getJoueur(identifiant);
        return maDAO.getInvitations(j);
    }

    @Override
    public Partie rejoindrePartieObservateur(String identifiant, String idJ1) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        return maDAO.rejoindrePartieObservateur(j, p);
    }

    @Override
    public boolean partieEnAttente(String idJ1) throws RemoteException {
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        return maDAO.partieEnAttente(p);
    }

    @Override
    public Partie rejoindrePartie(String identifiant, String idJ1) throws RemoteException, PartieCompleteException, PartieInexistanteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        return maDAO.rejoindrePartie(j, p);
    }

    @Override
    public List<Partie> getLesPartiesPublics() throws RemoteException, AucunePartieException {
        return this.maDAO.getLesPartiesPublics();
    }

    @Override
    public List<Partie> getLesPartiesFinies() throws RemoteException, AucunePartieException {
        return maDAO.getLesPartiesFinies();
    }

    @Override
    public Partie getPartie(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        return maDAO.getPartie(j);
    }

    @Override
    public boolean removePartie(String id) throws RemoteException {
        Joueur j = maDAO.getJoueur(id);
        Partie p = maDAO.getPartie(j);
        return maDAO.removePartie(j, p);
    }

    @Override
    public List<Pion> getListePionsDepart(String id) throws RemoteException {
        Joueur j = maDAO.getJoueur(id);
        return maDAO.getListePionsDepart(j);
    }

    @Override
    public void placerPions(Case[][] plate, String id) throws RemoteException {
        Joueur j = maDAO.getJoueur(id);
        Partie p = maDAO.getPartie(j);
        maDAO.placerPions(plate, p, j);
    }

    @Override
    public void rejeterInvitation(String identifiant, String idJ1) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        maDAO.rejeterInvitation(j, p);
    }

    @Override
    public void quitterObservation(String identifiant, String idJ1) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(maDAO.getJoueur(idJ1));
        maDAO.quitterObservation(p, j);
    }

    @Override
    public void modifierMotDePasse(String identifiant, String newMotDePasse) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        maDAO.modifierMotDePasse(j, newMotDePasse);
    }

    @Override
    public Partie getLesPartiesFinies(String identifiant) throws RemoteException, AucunePartieException {
        Joueur j = maDAO.getJoueur(identifiant);
        return maDAO.getLesPartiesFinies(j);
    }

    @Override
    public void passerSonTour(String identifiant) throws RemoteException {
        Joueur j = maDAO.getJoueur(identifiant);
        Partie p = maDAO.getPartie(j);
        maDAO.passerSonTour(p, j);
    }
}
