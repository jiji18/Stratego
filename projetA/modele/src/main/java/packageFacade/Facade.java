package packageFacade;

import Exceptions.*;
import Services.IGestionCompte;
import Services.IGestionJeu;
import Services.IGestionPartie;
import Services.impl.GestionCompte;
import Services.impl.GestionJeu;
import Services.impl.GestionPartie;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jihad
 */
public class Facade implements IFacade, Serializable {
    private Map<Joueur, Partie> lesParties;
    private List<Partie> lesPartiesFinies;

    private IGestionCompte gestionCompte;
    private IGestionPartie gestionPartie;
    private IGestionJeu gestionJeu;

    public Facade() {
        lesParties = new HashMap<Joueur, Partie>();
        lesPartiesFinies = new ArrayList<Partie>();
        //on instancie les différents services
        gestionJeu = new GestionJeu(lesParties, lesPartiesFinies);
        gestionPartie = new GestionPartie(lesParties, lesPartiesFinies);
        gestionCompte = new GestionCompte();
        //on charge les joueurs de la base
        gestionCompte.chargerJoueurs();
    }

    private static IFacade instance = null;

    public static IFacade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    @Override
    public void placerPions(int[][] tab, Partie p, Joueur j) {
        gestionJeu.placerPions(tab, p, j);
    }

    @Override
    public int getTempsMax() {
        return gestionJeu.getTEMP_MAX();
    }

    @Override
    public List<Pion> getLesPionsRestants(Partie p, Joueur j) {
        return gestionJeu.getLesPionsRestants(p, j);
    }

    @Override
    public Case[][] getDamier(Partie p, Joueur j) {
        return gestionJeu.getDamier(p, j);
    }

    @Override
    public boolean estCliquable(Partie p, Joueur j, int x, int y) {
        return gestionJeu.estCliquable(p, j, x, y);
    }

    @Override
    public boolean[][] deplacementPossible(Partie p, Joueur j, int x, int y) {
        return gestionJeu.deplacementPossible(p, j, x, y);
    }

    @Override
    public boolean deplacerPion(Partie p, Joueur j, int x, int y, int newX, int newY) {
        boolean test = gestionJeu.deplacerPion(p, j, x, y, newX, newY);
        if (test) gestionCompte.sauvegarderJoueurs();
        return test;
    }

    @Override
    public List<Joueur> getJoueursConnectes() {
        return gestionCompte.getJoueursConnectes();
    }

    @Override
    public List<Joueur> getJoueurs() {
        return gestionCompte.getJoueurs();
    }

    @Override
    public Joueur getJoueurConnectes(String identifiant) {
        return gestionCompte.getJoueur(identifiant);
    }

    @Override
    public Joueur getJoueur(String identifiant) {
        return gestionCompte.getJoueur(identifiant);
    }

    @Override
    public Joueur inscription(String id, String pass) throws PseudoDejaUtiliseException {
        return gestionCompte.inscription(id, pass);
    }

    @Override
    public Joueur connexion(String id, String pass) throws ErreurConnexionException {
        return gestionCompte.connexion(id, pass);
    }

    @Override
    public Joueur deconnexion(String id) throws ErreurDeconnexionException {
        return gestionCompte.deconnexion(id);
    }

    @Override
    public Partie creerPartie(Joueur j1) {
        return gestionPartie.creerPartie(j1);
    }

    @Override
    public Partie defier(Joueur j1, Joueur j2) throws JoueurOccupeException {
        return gestionPartie.defier(j1, j2);
    }

    @Override
    public boolean estAttenduPourJouer(Joueur j) {
        return gestionPartie.estAttenduPourJouer(j);
    }

    @Override
    public List<Partie> getInvitations(Joueur j) throws PartieInexistanteException, AucunePartieException {
        return gestionPartie.getInvitations(j);
    }

    @Override
    public List<Partie> getLesPartiesPublics() throws AucunePartieException {
        return gestionPartie.getLesPartiesPublics();
    }

    @Override
    public List<Partie> getLesPartiesFinies() throws AucunePartieException {
        return gestionPartie.getLesPartiesFinies();
    }

    @Override
    public Partie getPartie(Joueur j) {
        return gestionPartie.getPartie(j);
    }

    @Override
    public boolean removePartie(Joueur j, Partie p) {
        return gestionPartie.removePartie(j, p);
    }

    @Override
    public List<Pion> getListePionsDepart(Joueur j) {
        return gestionJeu.getListePionsDepart(j);
    }

    @Override
    public void placerPions(Case[][] plate, Partie p, Joueur joueur) {
        gestionJeu.placerPions(plate, p, joueur);
    }

    @Override
    public void quitterObservation(Partie p, Joueur j) {
        gestionPartie.quitterObservation(p, j);
    }

    @Override
    public void rejeterInvitation(Joueur j, Partie p) {
        gestionPartie.rejeterInvitation(j, p);
    }

    @Override
    public Partie rejoindrePartie(Joueur j, Partie p) throws PartieCompleteException, PartieInexistanteException {
        return gestionPartie.rejoindrePartie(j, p);
    }

    @Override
    public Partie getLesPartiesFinies(Joueur j) throws AucunePartieException {
        return gestionPartie.getLesPartiesFinies(j);
    }

    @Override
    public boolean partieEnAttente(Partie p) {
        return gestionPartie.partieEnAttente(p);
    }

    @Override
    public Partie rejoindrePartieObservateur(Joueur j, Partie p) {
        return gestionPartie.rejoindrePartieObservateur(j, p);
    }

    @Override
    public Partie rejoindrePartiePrivee(Joueur j, Partie p) throws PartieInexistanteException, PartieCompleteException, AucunePartieException {
        return gestionPartie.rejoindrePartiePrivee(j, p);
    }

    @Override
    public void abandonnerPartie(Joueur j) throws PartieInexistanteException {
        gestionPartie.abandonnerPartie(j);
        gestionCompte.sauvegarderJoueurs();
    }

    @Override
    public void modifierMotDePasse(Joueur j, String newMotDePasse) {
        gestionCompte.modifierMotDePasse(j, newMotDePasse);
    }

    @Override
    public void passerSonTour(Partie p, Joueur j) {
        gestionJeu.passerSonTour(p, j);
    }
}
