package vues;

import controleur.Controleur;
import controleur.erreurs.PartieInexistante;
import ecouteurs.exceptions.PartieInexistanteErrorEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePlateau.packageCase.Case;
import packagePlateau.packageCase.TypeCase;

import java.io.IOException;
import java.net.URL;

/**
 * @author jolan
 */
public class ObserverVue {

    private final double HEIGHT_IMAGE = 40;
    private final double WIDTH_IMAGE = 40;
    private final double INSET_SIZE =1;

    @FXML
    private Button quitterButton;
    @FXML
    private VBox racine;

    Button[][] tousMesBoutons;
    GridPane monPlateau;

    private Timeline timer;
    private Controleur monControleur;
    private boolean fstTime;
    private boolean booleanTimer;

    Node getNode() {
        return racine;
    }

    static ObserverVue creerInstance(Controleur monControleur) {
        URL location = ObserverVue.class.getResource("/vues/observer.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObserverVue vue = fxmlLoader.getController();
        vue.initialise();
        vue.setMonControleur(monControleur);
        return vue;
    }

    private void initialise(){
        fstTime=true;
        quitterButton.setOnAction(e-> {
            arreteTimer();
            monControleur.quitterObservateur()
        ;});
        quitterButton.setDisable(false);
        lancerTimer();
        booleanTimer = false;
        tousMesBoutons = new Button[10][10];
        monPlateau = new GridPane();
        for(int i=0; i<tousMesBoutons.length;i++)
            for(int j=0; j<tousMesBoutons[i].length;j++){
                tousMesBoutons[i][j] = new Button();
            }
        racine.getChildren().add(monPlateau);
    }

    private void lancerTimer() {
        booleanTimer = true;
        timer = new Timeline();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            if(!booleanTimer) return;
            Partie p = monControleur.getPartie();
            if(p==null || p.getJ2()==null || p.getJ1()==null){
                arreteTimer();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Fin de la partie");
                alert.setContentText("La partie est terminée");
                alert.show();
                monControleur.goToLobbyVue();
            }else{
                chargerGrille();
            }
        }));
        timer.play();
    }

    public void chargerGrille(){
        if(fstTime){
            for(int i=0; i<tousMesBoutons.length;i++)
                for(int j=0; j<tousMesBoutons[i].length;j++){
                    monPlateau.add(tousMesBoutons[i][j],j,i);
                }
            fstTime=false;
        }
        if(booleanTimer==false)
        {
            booleanTimer=true;
            lancerTimer();
        }
        Joueur j = monControleur.getUtilisateur();
        //Ajout du bon bouton selon le statut du joueur connecté

        Case[][] grilleCase = monControleur.getDamier();

        for (int ligne = 0; ligne < grilleCase.length; ligne++) {
            for (int colonne = 0; colonne < grilleCase[ligne].length; colonne++) {
                Button btnCase = tousMesBoutons[ligne][colonne];
                Case laCase = grilleCase[ligne][colonne];
                TypeCase typeC = laCase.getType();
                Image img = null;
                switch (typeC) {
                    case PION:
                        String tagJoueur = laCase.getJoueur().equals(monControleur.getPartie().getJ1()) ? "j1" : "j2";
                        img = createImage("/img/" + tagJoueur + "_pion_"+ laCase.getValeur() +".bmp");
                        break;
                    case OBSTACLE:
                        img = createImage("/img/CaseLac-" + ligne + colonne + ".jpg");
                        break;
                    case VIDE:
                        img = createImage("/img/CaseHerbe.jpg");
                        break;
                    case VIDEINITIALE:
                        img = createImage("/img/CaseHerbe.jpg");
                        break;
                }
                btnCase.setDisable(false);
                ImageView iw = new ImageView(img);
                formatImageView(iw, btnCase);
            }
        }
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    private Image createImage(String s) {
        return new Image(PlacementVue.class.getResourceAsStream(s));
    }

    private void formatImageView(ImageView iw, Button btn) {
        btn.setPadding(new Insets(INSET_SIZE));
        iw.setFitHeight(HEIGHT_IMAGE);
        iw.setFitWidth(WIDTH_IMAGE);
        btn.setGraphic(iw);
    }

    private void abandonnerPartie() {
        arreteTimer();
        monControleur.goToLobbyVue();
    }

    private void arreteTimer() {
        booleanTimer =false;
    }
}
