package vues;

import controleur.Controleur;
import ecouteurs.ConnexionErrorListener;
import ecouteurs.exceptions.ConnexionErrorEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class ConnexionVue implements ConnexionErrorListener {

    private Controleur monControleur;

    @FXML
    private VBox racine;
    @FXML
    private TextField identifiant;
    @FXML
    private TextField motDePasse;

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    Node getNode() {
        return racine;
    }

    static ConnexionVue creerInstance(Controleur monControleur) {
        URL location = ConnexionVue.class.getResource("/vues/connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnexionVue vue = fxmlLoader.getController();
        vue.setMonControleur(monControleur);
        vue.initialise();
        return vue;
    }

    private void initialise() {
        this.identifiant.setOnKeyPressed(keyEvent->{
            if(keyEvent.getCode().equals(KeyCode.ENTER))
                motDePasse.requestFocus();
        });

        this.motDePasse.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER))
                monControleur.connexion(identifiant.getText(),motDePasse.getText());
        });
    }

    @Override
    public void errorDetected(ConnexionErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

    public void connexion() {
        this.monControleur.connexion(this.identifiant.getText(), this.motDePasse.getText());
    }

    public void runInscription(ActionEvent actionEvent) {
        this.monControleur.goToInscription();
    }
}
