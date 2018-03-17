package vues;

import controleur.Controleur;
import ecouteurs.InscriptionErrorListener;
import ecouteurs.exceptions.InscriptionErrorEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

/**
 * @author Anthony
 */
public class InscriptionVue implements InscriptionErrorListener{

    private Controleur monControleur;

    @FXML
    TextField pseudo;
    @FXML
    PasswordField mdp;
    @FXML
    PasswordField confirmation;
    @FXML
    VBox inscription;

    Node getNode() {
        return inscription;
    }

    static InscriptionVue creerInstance(Controleur monControleur) {
        URL location = InscriptionVue.class.getResource("/vues/inscription.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InscriptionVue vue = fxmlLoader.getController();
        vue.initialise();
        vue.setMonControleur(monControleur);

        return vue;
    }

    private void initialise() {
        this.pseudo.setOnKeyPressed(keyEvent->{
            if(keyEvent.getCode().equals(KeyCode.ENTER))
                this.mdp.requestFocus();
        });

        this.mdp.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER))
                this.confirmation.requestFocus();
        });

        this.confirmation.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER))
                validerInscription();
        });

    }

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }


    public void validerInscription(){

        String motDePasse = this.mdp.getText();
        String confirmationMotDePasse = this.confirmation.getText();
        String nom = this.pseudo.getText();

        if (motDePasse.length() == 0 || confirmationMotDePasse.length() == 0 || nom.length() == 0) {
            this.errorDetected(new InscriptionErrorEvent(this, "Tous les champs sont obligatoires"));
        } else {

            if (this.mdp.getText().equals(this.confirmation.getText())) {
                this.monControleur.inscription(nom, motDePasse);
            } else {
                this.errorDetected(new InscriptionErrorEvent(this, "Les mots de passe diffèrent !"));
            }

        }
    }

    @Override
    public void errorDetected(InscriptionErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }
}
