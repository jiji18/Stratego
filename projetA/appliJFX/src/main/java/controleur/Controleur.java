package controleur;

import Exceptions.*;
import controleur.erreurs.AucuneInvitation;
import controleur.erreurs.AucunePartie;
import controleur.erreurs.PartieComplete;
import controleur.erreurs.PartieInexistante;
import ecouteurs.exceptions.ConnexionErrorEvent;
import ecouteurs.exceptions.DeconnexionErrorEvent;
import ecouteurs.exceptions.InscriptionErrorEvent;
import ecouteurs.exceptions.JoueurOccupeEvent;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;
import rmiService.MonService;
import vues.FenetrePrincipale;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Objects;

public class Controleur {

    private MonService facade;
    private FenetrePrincipale fenetrePrincipale;

    private String identifiantUtilisateur;
    private Joueur utilisateur;

    private static final String HOST = "127.0.0.1";

    public Controleur() {

        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(HOST, 9345);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String[] names = new String[0];
        try {
            names = registry != null ? registry.list() : new String[0];
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (String name1 : names) {
            System.out.println("~~~~" + name1 + "~~~~");
        }
        try {
            this.facade = (MonService) (registry != null ? registry.lookup(MonService.serviceName) : null);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        this.fenetrePrincipale = FenetrePrincipale.creerInstance(this);
        this.fenetrePrincipale.setConnexionVue();
    }

    //<editor-fold desc="Connexion, Inscription, Deconnexion">
    public void connexion(String identifiant, String motDePasse) {
        //Verif champs obligatoire
        if ((identifiant.length() <= 0) || (motDePasse.length() <= 0)) {
            this.fenetrePrincipale.errorDetected(new ConnexionErrorEvent(this, "Les champs sont obligatoires"));
        } else {
            try {
                this.utilisateur = this.facade.connexion(identifiant, motDePasse);
                this.identifiantUtilisateur = this.utilisateur.getIdentifiant();
                goToLobbyVue();
            } catch (ErreurConnexionException e) {
                this.fenetrePrincipale.errorDetected(new ConnexionErrorEvent(this, "Connexion Impossible"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        try {
            this.facade.deconnexion(this.identifiantUtilisateur);
            identifiantUtilisateur = null;
            utilisateur = null;
            this.fenetrePrincipale.setConnexionVue();
        } catch (ErreurDeconnexionException e) {
            this.fenetrePrincipale.errorDetected(new DeconnexionErrorEvent(this, "Le joueur n'est pas connecté"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.fenetrePrincipale.setConnexionVue();
    }

    public void inscription(String identifiant, String motDePasse) {
        try {
            utilisateur = this.facade.inscription(identifiant, motDePasse);
            this.identifiantUtilisateur = utilisateur.getIdentifiant();
        } catch (PseudoDejaUtiliseException e) {
            this.fenetrePrincipale.errorDetected(new InscriptionErrorEvent(this, "Login déjà pris !!"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        goToLobbyVue();
    }
    //</editor-fold>

    //<editor-fold desc="Redirections">
    public void goToInscription() {
        this.fenetrePrincipale.setInscriptionVue();
    }

    public void goToAttenteVue() {
        this.fenetrePrincipale.setAttenteVue();
    }

    public void goToPlacementVue() {
        this.fenetrePrincipale.setPlacementVue();
    }

    public void goToLobbyVue() {
        //fenetrePrincipale.lancerTimerLobby();
        this.fenetrePrincipale.setLobbyVue();
    }

    public void goToJouerVue() {
        this.fenetrePrincipale.setJouerVue();
    }

    private void gotoObserverVue() {
        this.fenetrePrincipale.setObserverVue();
    }

    public void goToNotJouerVue() {
        utilisateur.setStatut(EnumStatut.WAITINGPLAY);
        getPartie().getJ2().setStatut(EnumStatut.PLAYING);
        this.fenetrePrincipale.setNotJouerVue();

    }
    //</editor-fold>

    //<editor-fold desc="Lobby">
    public List<Partie> getLesPartiesPublics() throws AucunePartie {
        List<Partie> result = null;
        try {
            result = facade.getLesPartiesPublics();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AucunePartieException e) {
            throw new AucunePartie("Aucune partie n'a été trouvée");
        }
        return result;
    }

    public List<Joueur> getLesJoueursConnectes() {
        List<Joueur> result = null;
        try {
            result = facade.getJoueursConnectes();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Partie> getLesInvitations() throws AucuneInvitation {
        List<Partie> result = null;
        try {
            result = facade.getInvitations(this.utilisateur.getIdentifiant());
        } catch (RemoteException | PartieInexistanteException e) {
            e.printStackTrace();
        } catch (AucunePartieException e) {
            throw new AucuneInvitation("Aucune invitation n'a été trouvée");
        }
        return result;
    }

    public Partie defier(Joueur j2) {
        Partie p = null;
        try {
            p = facade.defier(utilisateur.getIdentifiant(), j2.getIdentifiant());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (JoueurOccupeException e) {
            this.fenetrePrincipale.errorDetected(new JoueurOccupeEvent(this, "Le joueur n'est actuellement pas disponible pour un défis"));
        }
        return p;
    }

    public void quitterObservateur() {
        try {
            facade.quitterObservation(identifiantUtilisateur, facade.getPartie(identifiantUtilisateur).getJ1().getIdentifiant());
            goToLobbyVue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<Joueur> getClassement() {
        List<Joueur> result = null;
        try {
            result = facade.getJoueurs();
            result.sort((joueur, t1) -> {
                //ratio joueur1
                int nbV1 = joueur.getNbVictoires(), nbD1 = joueur.getNbDefaites();
                float ratio1 =
                        nbV1 == 0 ? 0f : (float) nbV1 / (float) (nbV1 + nbD1);

                //ratio joueur2
                int nbV2 = t1.getNbVictoires(), nbD2 = t1.getNbDefaites();
                float ratio2 =
                        nbV2 == 0 ? 0f : (float) nbV2 / (float) (nbV2 + nbD2);

                int res = 0;
                if (ratio1 == ratio2) {
                    if (joueur.getNbVictoires() == t1.getNbVictoires()) {
                        res = (t1.getNbDefaites() > joueur.getNbDefaites() ? 1 : -1);
                    } else {
                        res = (joueur.getNbVictoires() > t1.getNbVictoires() ? -1 : 1);
                    }
                } else {
                    if (ratio1 > ratio2) res = -1;
                    else res = 1;
                }
                return res;
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void creerPartiePublique() {
        try {
            //Si il y a deja une partie (surement privée) elle est deleted
            Partie partie = facade.getPartie(utilisateur.getIdentifiant());
            if (partie != null)
                facade.removePartie(utilisateur.getIdentifiant());
            facade.creerPartie(utilisateur.getIdentifiant());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void supprimerPartie() {
        try {
            Partie partie = facade.getPartie(utilisateur.getIdentifiant());
            if (partie != null)
                facade.removePartie(utilisateur.getIdentifiant());
            fenetrePrincipale.lancerTimerLobby();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void refuserInvitation(Partie partie) {
        try {
            facade.rejeterInvitation(utilisateur.getIdentifiant(), partie.getJ1().getIdentifiant());
            fenetrePrincipale.relancerTimerLobby();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void rejoindrePartie(Partie p) throws PartieComplete, PartieInexistante, AucuneInvitation {
        try {
            if (p.isPrivate())
                facade.rejoindrePartiePrivee(utilisateur.getIdentifiant(), p.getJ1().getIdentifiant());
            else
                facade.rejoindrePartie(utilisateur.getIdentifiant(), p.getJ1().getIdentifiant());

            fenetrePrincipale.arreterTimerLobby();
            goToAttenteVue();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (PartieCompleteException e) {
            throw new PartieComplete("La partie est déjà complète");
        } catch (PartieInexistanteException e) {
            throw new PartieInexistante("La partie n'existe plus");
        } catch (AucunePartieException e) {
            throw new AucuneInvitation("Aucune invitation n'a été trouvée");
        }
    }

    public void rejoindrePartieObservateur(Partie p) {
        try {
            this.facade.rejoindrePartieObservateur(utilisateur.getIdentifiant(), p.getJ1().getIdentifiant());
            fenetrePrincipale.arreterTimerLobby();
            gotoObserverVue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public int getMaxTimeLobby() {
        return 120;
    }

    public boolean partiePrete() {
        boolean res = false;
        try {
            Partie p = facade.getPartie(utilisateur.getIdentifiant());
            if (p != null) {
                res = (p.getJ1() != null && p.getJ2() != null);
                if (res) {
                    p.getJ1().setStatut(EnumStatut.PLAYING);
                    p.getJ2().setStatut(EnumStatut.WAITINGPLAY);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void changerPassword(String motDePasse) {
        try {
            facade.modifierMotDePasse(utilisateur.getIdentifiant(), motDePasse);
            fenetrePrincipale.relancerTimerLobby();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    public Partie getPartie() {
        Partie res = null;
        try {
            res = facade.getPartie(utilisateur.getIdentifiant());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Joueur getUtilisateur() {
        Joueur joueur = null;
        try {
            joueur = facade.getJoueur(this.identifiantUtilisateur);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return joueur;
    }

    public String getUtilisateurLogin() {
        return (identifiantUtilisateur == null ? "" : identifiantUtilisateur);
    }

    public int getUtilisateurNbGames() {
        return (utilisateur == null ? 0 : utilisateur.getNbDefaites() + utilisateur.getNbVictoires());
    }

    public int getUtilisateurNbGamesWin() {
        return (utilisateur == null ? 0 : utilisateur.getNbVictoires());
    }

    public int getUtilisateurNbGamesLost() {
        return (utilisateur == null ? 0 : utilisateur.getNbDefaites());
    }

    public Case[][] getDamier() {
        Case[][] res = null;
        try {
            res = facade.getDamier(utilisateur.getIdentifiant());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getTagOnAGame() {
        String res = "j1";
        try {
            Partie p = facade.getPartie(utilisateur.getIdentifiant());
            if (p != null) {
                res = p.getJ1().equals(utilisateur) ? "j1" : "j2";
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Pion> getPionsAPlacer() {
        List<Pion> res = null;
        try {
            res = facade.getListePionsDepart(utilisateur.getIdentifiant());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void envoyer(int[][] grille) {
        try {
            facade.placerPions(grille, utilisateur.getIdentifiant());
            Partie p = facade.getPartie(identifiantUtilisateur);
            if (p.getJ1().getIdentifiant().equals(identifiantUtilisateur)) {
                goToAttenteVue();
            } else {
                goToNotJouerVue();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getTempsMAX() {
        int tMax = 0;
        try {
            tMax = facade.getTempsMax();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return tMax;
    }

    public void abandonner() throws PartieInexistante {
        try {
            facade.abandonnerPartie(utilisateur.getIdentifiant());
            goToLobbyVue();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (PartieInexistanteException e) {
            throw new PartieInexistante("La partie n'existe plus");
        }
    }

    public boolean[][] deplacementPossible(int i, int j) {
        boolean[][] res = new boolean[this.getPartie().getPlateau().getROW_SIZE()][this.getPartie().getPlateau().getCOLUMN_SIZE()];
        try {
            res = facade.deplacementPossible(identifiantUtilisateur, i, j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean deplacerPion(int x, int y, int newX, int newY) {
        boolean res = false;
        try {
            res = facade.deplacerPion(identifiantUtilisateur, x, y, newX, newY);
            if (res) {
                goToLobbyVue();
            } else {
                goToNotJouerVue();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void passerSonTour() {
        try {
            this.facade.passerSonTour(identifiantUtilisateur);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<Pion> getPionsRestants() {
        List<Pion> res =null;
        Partie p = getPartie();
        if(p != null){
            Joueur adversaire = Objects.equals(getTagOnAGame(), "j1") ? p.getJ2() : p.getJ1();
            try {
                res =facade.getLesPionsRestants(adversaire.getIdentifiant());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
