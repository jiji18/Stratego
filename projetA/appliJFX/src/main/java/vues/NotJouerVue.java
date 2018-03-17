package vues;

import controleur.Controleur;
import controleur.erreurs.PartieInexistante;
import ecouteurs.NotJouerErrorListener;
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
 * Created by jihad on 14/01/17.
 */
public class NotJouerVue implements NotJouerErrorListener {

    private final double HEIGHT_IMAGE = 40;
    private final double WIDTH_IMAGE = 40;
    private final double INSET_SIZE =1;

    private Controleur monControleur;

    private Button[][] tousMesBoutons;

    private Timeline timer;

    @FXML
    private VBox racineNotJouer;
    @FXML
    private Button monBouton;

    private GridPane monPlateau;

    private boolean booleanTimer;
    private boolean fstTime;

    Node getNode() {
        return racineNotJouer;
    }

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    static NotJouerVue creerInstance(Controleur c){
        URL location = NotJouerVue.class.getResource("/vues/notPlay.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NotJouerVue notjouerVue = fxmlLoader.getController();
        notjouerVue.setMonControleur(c);
        notjouerVue.initialize();

        return notjouerVue;
    }

    private void initialize(){
        fstTime=true;
        booleanTimer = false;
        tousMesBoutons = new Button[10][10];
        monPlateau = new GridPane();
        for(int i=0; i<tousMesBoutons.length;i++)
            for(int j=0; j<tousMesBoutons[i].length;j++){
                tousMesBoutons[i][j] = new Button();
            }
        racineNotJouer.getChildren().add(monPlateau);
    }

    private void lancerTimernotPlay() {
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
            Joueur j = (p.getJ1().equals(monControleur.getUtilisateur())?p.getJ1():p.getJ2());
            if(j.getStatut() == EnumStatut.CONNECTED){
                arreteTimer();
                monControleur.goToLobbyVue();
            }else if(j.getStatut() == EnumStatut.PLAYING) {
                arreteTimer();
                monControleur.goToJouerVue();
            }}
        }));
        timer.play();
    }

    private void arreteTimer(){
        timer.stop();
        booleanTimer = false;
    }

    void chargerGrille(){
        if(fstTime){
            for(int i=0; i<tousMesBoutons.length;i++)
                for(int j=0; j<tousMesBoutons[i].length;j++){
                    monPlateau.add(tousMesBoutons[i][j],j,i);
                }
            fstTime=false;
        }
        Joueur j = monControleur.getUtilisateur();
        //Ajout du bon bouton selon le statut du joueur connecté
        if(j.getStatut() == EnumStatut.WAITINGPLAY){
            monBouton.setText("Abandonner");
            monBouton.setOnAction(e -> this.abandonnerPartie());
        }else{
            monBouton.setText("Quitter");
            monBouton.setOnAction(e -> this.monControleur.quitterObservateur());
        }
        lancerTimernotPlay();
        Case[][] grilleCase = monControleur.getDamier();

        String tagJoueur = monControleur.getTagOnAGame();
        String tagAdv = tagJoueur.equals("j1") ? "j2" : "j1";

        for (int ligne = 0; ligne < grilleCase.length; ligne++) {
            for (int colonne = 0; colonne < grilleCase[ligne].length; colonne++) {
                Button btnCase = tousMesBoutons[ligne][colonne];
                Case laCase = grilleCase[ligne][colonne];
                TypeCase typeC = laCase.getType();
                Image img = null;
                switch (typeC) {
                    case PION:
                        if(laCase.getJoueur().getIdentifiant().equals(monControleur.getUtilisateurLogin())){
                            img = createImage("/img/" + tagJoueur + "_pion_"+ laCase.getValeur() +".bmp");
                        }else{
                            img = createImage("/img/" + tagAdv + "_pion_cachee.bmp");
                        }
                        break;
                    case OBSTACLE:
                        img = createImage("/img/CaseLac-" + ligne + colonne + ".jpg");
                        break;
                    case VIDE:
                        img = createImage("/img/CaseHerbe.jpg");
                        break;
                }
                btnCase.setDisable(true);
                ImageView iw = new ImageView(img);
                formatImageView(iw, btnCase);
            }
        }
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
        try {
            arreteTimer();
            monControleur.abandonner();
        } catch (PartieInexistante partieInexistante) {
            errorDetected(new PartieInexistanteErrorEvent(monControleur, partieInexistante.getMessage()));
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
