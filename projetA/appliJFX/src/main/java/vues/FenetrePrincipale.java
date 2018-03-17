package vues;

import controleur.Controleur;
import ecouteurs.*;
import ecouteurs.exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * @author Anthony
 */
public class FenetrePrincipale implements PlacementErrorListener, InscriptionErrorListener,ConnexionErrorListener, LobbyErrorListener{

    private Controleur monControleur;
    private Stage monStage;
    private ConnexionVue connexionVue;
    private LobbyVue lobbyVue;
    private InscriptionVue inscriptionVue;
    private PlacementVue placementVue;
    private JouerVue jouerVue;
    private NotJouerVue notJouerVue;
    private ObserverVue observerVue;

    @FXML
    private BorderPane racine;

    private void setMonControleur(Controleur c) {
        this.monControleur = c;
        this.connexionVue = ConnexionVue.creerInstance(c);
        this.inscriptionVue = InscriptionVue.creerInstance(c);
        this.jouerVue = JouerVue.creerInstance(c);
        this.notJouerVue = NotJouerVue.creerInstance(c);
        this.observerVue=ObserverVue.creerInstance(c);
    }

    private void setMonStage(Stage stage) {
        this.monStage = stage;
    }

    public void setConnexionVue() {
        this.racine.setCenter(this.connexionVue.getNode());
        this.monStage.show();
    }

    public void setLobbyVue() {
        this.lobbyVue = LobbyVue.creerInstance(monControleur);
        lobbyVue.lancerTimerLobby();
        this.racine.setCenter(this.lobbyVue.getNode());
        this.monStage.show();
    }

    public void setInscriptionVue() {
        this.racine.setCenter(this.inscriptionVue.getNode());
        this.monStage.show();
    }

    public void setAttenteVue() {
        AttenteVue attenteVue = AttenteVue.creerInstance(monControleur);
        this.racine.setCenter(attenteVue.getNode());
        this.monStage.show();
    }

    public void setPlacementVue() {
        this.placementVue = PlacementVue.creerInstance(monControleur);
        this.placementVue.chargementPlacement();
        this.racine.setCenter(this.placementVue.getNode());
        this.monStage.show();
    }

    public void setJouerVue(){
        jouerVue.chargerGrille();
        this.racine.setCenter(this.jouerVue.getNode());
        this.monStage.show();
    }

    public void setNotJouerVue(){
        notJouerVue.chargerGrille();
        this.racine.setCenter(this.notJouerVue.getNode());
        this.monStage.show();
    }

    public void setObserverVue() {
        observerVue.chargerGrille();
        this.racine.setCenter(this.observerVue.getNode());
        this.monStage.show();
    }

    public static FenetrePrincipale creerInstance(Controleur c) {
        URL location = ConnexionVue.class.getResource("/vues/fenetrePrincipale.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FenetrePrincipale vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        Stage primaryStage = new Stage();

        Dimension myScreen = Toolkit.getDefaultToolkit().getScreenSize();//dimension ecran
        assert root != null;
        primaryStage.setScene(new Scene(root, myScreen.getWidth(), myScreen.getHeight()));
        vue.setMonStage(primaryStage);

        return vue;
    }

    public void lancerTimerLobby() {
        lobbyVue.lancerTimerLobby();
    }

    public void relancerTimerLobby() {
        lobbyVue.relancerTimerLobby();
    }
    public void arreterTimerLobby() {     lobbyVue.arreterTimer();   }
    @Override
    public void errorDetected(ConnexionErrorEvent event) {
        this.connexionVue.errorDetected(event);
    }

    @Override
    public void errorDetected(DeconnexionErrorEvent event) {
        this.lobbyVue.errorDetected(event);
    }

    @Override
    public void errorDetected(JoueurOccupeEvent event) {
        this.lobbyVue.errorDetected(event);
    }

    @Override
    public void errorDetected(InvitationErrorEvent event) {
        this.lobbyVue.errorDetected(event);
    }

    @Override
    public void errorDetected(InscriptionErrorEvent event) {
        this.inscriptionVue.errorDetected(event);
    }

    @Override
    public void errorDetected(PartieInexistanteErrorEvent event) {
        this.placementVue.errorDetected(event);
    }



}
