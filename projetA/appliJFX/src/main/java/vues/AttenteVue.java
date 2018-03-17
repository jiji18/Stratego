package vues;

import controleur.Controleur;
import controleur.erreurs.PartieInexistante;
import ecouteurs.AttenteErrorListener;
import ecouteurs.exceptions.PartieInexistanteErrorEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;

import java.io.IOException;
import java.net.URL;

/**
 * @author Anthony
 */
public class AttenteVue implements AttenteErrorListener {
    private Controleur monControleur;

    private Timeline timer;

    @FXML
    VBox racine;

    Node getNode() {
        return racine;
    }

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    static AttenteVue creerInstance(Controleur monControleur) {
        URL location = AttenteVue.class.getResource("/vues/attente.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttenteVue vue = fxmlLoader.getController();
        vue.setMonControleur(monControleur);
        vue.initialize();
        return vue;
    }

    private void initialize() {
        WebView webview = new WebView();
        webview.getEngine().load(
                "https://www.youtube.com/embed/nq0xn5fOTz4"
        );
        //webview.setPrefSize(640, 390);
        racine.getChildren().add(webview);

        WebView webview2 = new WebView();
        webview2.getEngine().load("https://fr.wikipedia.org/wiki/Stratego");
        racine.getChildren().add(webview2);

        lancerTimerAttente();
    }

    private void lancerTimerAttente() {
        timer = new Timeline();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            Partie p = monControleur.getPartie();
            if (p==null || p.getJ1() == null || p.getJ2()==null){
                monControleur.goToLobbyVue();
            }
            Joueur j = (p.getJ1().equals(monControleur.getUtilisateur())?p.getJ1():p.getJ2());
            if(j.getStatut() == EnumStatut.CONNECTED){
                timer.stop();
                monControleur.goToLobbyVue();
            }else if(j.getStatut() == EnumStatut.PLAYING && j.equals(p.getJ2())){
                timer.stop();
                monControleur.goToPlacementVue();
            }else if(j.getStatut() == EnumStatut.PLAYING && j.equals(p.getJ1())){
                timer.stop();
                monControleur.goToJouerVue();
            }
        }));
        timer.play();
    }

    public void abandonner() {
        try {
            monControleur.abandonner();
        } catch (PartieInexistante partieInexistante) {
            errorDetected(new PartieInexistanteErrorEvent(monControleur, "La partie n'existe plus"));
            monControleur.goToLobbyVue();
        }
    }


    @Override
    public void errorDetected(PartieInexistanteErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }
}
