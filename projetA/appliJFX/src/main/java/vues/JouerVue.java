package vues;

import controleur.Controleur;
import controleur.erreurs.PartieInexistante;
import ecouteurs.JouerErrorListener;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;
import packagePlateau.packageCase.TypeCase;

import java.io.IOException;
import java.net.URL;

/**
 * Created by jihad on 12/01/17.
 */
public class JouerVue implements JouerErrorListener {

    private final double HEIGHT_IMAGE = 40;
    private final double WIDTH_IMAGE = 40;
    private final double INSET_SIZE = 1;

    private boolean[][] canMove;
    private Case[][] grilleCase;

    @FXML
    private Pane ecranPionsRestants;

    @FXML
    private VBox racineJouer;

    @FXML
    private Pane tempsRestantPane;

    private  GridPane monPlateau;
    private  GridPane pionsRestants;

    private Text textTemps;
    private int tempsRestants;

    private Timeline timer;

    private Button[][] tousMesBoutons;
    private static int iActif = -1;
    private static int jActif = -1;

    private Controleur monControleur;
    private boolean booleanTimer;
    private boolean fstTime;

    Node getNode() {
        return racineJouer;
    }

    static JouerVue creerInstance(Controleur c){
        URL location = JouerVue.class.getResource("/vues/jeu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            fxmlLoader.setRoot(null);
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JouerVue jouerVue = fxmlLoader.getController();
        jouerVue.setMonControleur(c);
        jouerVue.initialize();

        return jouerVue;
    }

    public void abandonnerPartie() {
        try {
            monControleur.abandonner();
        } catch (PartieInexistante partieInexistante) {
            errorDetected(new PartieInexistanteErrorEvent(monControleur, "La partie n'existe plus"));
            monControleur.goToLobbyVue();
        }
    }

    private void initialize(){
        fstTime=true;
        textTemps = new Text();
        tempsRestantPane.getChildren().add(textTemps);
        creerGrid();
        creerButton();
        racineJouer.getChildren().add(monPlateau);
        pionsRestants = new GridPane();
        VBox pionsRestantsVBOX = new VBox();
        pionsRestantsVBOX.getChildren().add(new Text("Pions adverses restants"));
        pionsRestantsVBOX.getChildren().add(pionsRestants);
        ecranPionsRestants.getChildren().add(pionsRestantsVBOX);
        creerTimer();
    }

     private void creerButton(){
         tousMesBoutons = new Button[10][10];
         for(int i= 0; i<tousMesBoutons.length;i++)
             for(int j= 0; j<tousMesBoutons[i].length;j++){
                 tousMesBoutons[i][j] = new Button();
             }
     }

    private void creerGrid() {
        monPlateau = new GridPane();
    }

    private void creerTimer() {
        booleanTimer = false;
        timer = new Timeline();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            if(!booleanTimer) return;
            if(monControleur.getPartie() == null){
                arreteTimer();
                monControleur.goToLobbyVue();
                return;
            }
            if(tempsRestants <= 0){
                arreteTimer();
                tempsRestants = monControleur.getTempsMAX();
                monControleur.passerSonTour();
                monControleur.goToNotJouerVue();
            }else{
                tempsRestants--;
            }
            changerAffichageTimer();
        }));
    }

    void chargerGrille(){
        if(fstTime){
            for(int i= 0; i<tousMesBoutons.length;i++)
                for(int j= 0; j<tousMesBoutons[i].length;j++){
                    monPlateau.add(tousMesBoutons[i][j],j,i);
                    fstTime=false;
            }
            fstTime=false;
        }
        grilleCase = monControleur.getDamier();
        String tagJoueur = monControleur.getTagOnAGame();
        String tagAdv = tagJoueur.equals("j1") ? "j2" : "j1";
        Button btnCase;

        for (int ligne = 0; ligne < tousMesBoutons.length; ligne++) {
            for (int colonne = 0; colonne < tousMesBoutons[ligne].length; colonne++) {
                btnCase= tousMesBoutons[ligne][colonne];
                Case laCase = grilleCase[ligne][colonne];
                TypeCase typeC = laCase.getType();
                Image img = null;
                ImageView iw;
                switch (typeC) {
                    case PION:
                        if(laCase.getJoueur().getIdentifiant().equals(monControleur.getUtilisateurLogin())){
                            img = createImage("/img/" + tagJoueur + "_pion_"+ laCase.getValeur() +".bmp");
                            btnCase.setDisable(false);
                        }else{
                            img = createImage("/img/" + tagAdv + "_pion_cachee.bmp");
                            btnCase.setDisable(true);
                        }
                        break;
                    case OBSTACLE:
                        img = createImage("/img/CaseLac-" + ligne + colonne + ".jpg");
                        btnCase.setDisable(true);
                        break;
                    case VIDE:
                        img = createImage("/img/CaseHerbe.jpg");
                        btnCase.setDisable(true);
                        break;
                }
                iw = new ImageView(img);
                formatImageView(iw, btnCase);
                final int fi = ligne;
                final int fj = colonne;
                btnCase.setOnAction(e ->
                     clique(fi,fj)
                );

            }
        }

        pionsRestants.getChildren().clear();
        int nLigne = 0;
        int nColonne = 0;
        int nbByLigne = 4;

        for(Pion p : monControleur.getPionsRestants()){
            Button btnRestant = new Button();
            Image imgRestant = createImage("/img/"+tagAdv+"_pion_"+p.getValeur()+".bmp");
            formatImageView(new ImageView(imgRestant),btnRestant);
            pionsRestants.add(btnRestant, nColonne,nLigne);
            nColonne++;
            if(nColonne == nbByLigne){
                nColonne =0;
                nLigne++;
            }

        }

        lancerTimerJouer();
    }

    private void lancerTimerJouer(){
        booleanTimer =true;
        tempsRestants = monControleur.getTempsMAX();
        timer.play();
    }

    private void arreteTimer(){
        timer.stop();
        booleanTimer = false;
    }
    private void changerAffichageTimer() {
        textTemps.setText("Temps restant: "+tempsRestants+" secondes");
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

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    private void clique(int ligne, int colonne){
        if (iActif == -1 && jActif==-1){
            iActif = ligne;
            jActif = colonne;
            Button btn = tousMesBoutons[ligne][colonne];
            btn.setStyle("-fx-border-color: red");
            boolean[][] canBeClicked = monControleur.deplacementPossible(ligne,colonne);
            for(int i=0;i<canBeClicked.length;i++){
                for(int j=0;j<canBeClicked[i].length;j++){
                    tousMesBoutons[i][j].setDisable(!canBeClicked[i][j]);
                }
            }
            tousMesBoutons[ligne][colonne].setDisable(false);
        }else{
            Button btn = tousMesBoutons[iActif][jActif];
            btn.setStyle("-fx-border-style: none");
            for(int i=0;i<grilleCase.length;i++){
                for(int j=0;j<grilleCase[i].length;j++){
                    if(grilleCase[i][j].getJoueur()!=null && grilleCase[i][j].getJoueur().getIdentifiant().equals(monControleur.getUtilisateurLogin())){
                        tousMesBoutons[i][j].setDisable(false);
                    }else{
                        tousMesBoutons[i][j].setDisable(true);
                    }
                }
            }

            if (iActif!= ligne || jActif!=colonne){


                if(grilleCase[ligne][colonne].isCasePion()){
                    String tagAdv = monControleur.getTagOnAGame().equals("j1") ? "j2" : "j1";
                    Button btnToModif=  tousMesBoutons[ligne][colonne];
                    btnToModif.setGraphic(new ImageView(createImage("/img/" + tagAdv + "_pion_"+ grilleCase[ligne][colonne].getValeur() +".bmp")));
                    /*formatImageView(
                        new ImageView(createImage("/img/" + tagAdv + "_pion_"+ grilleCase[ligne][colonne].getValeur() +".bmp")),
                            btnToModif
                    );*/
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                }
                boolean testWin = monControleur.deplacerPion(iActif,jActif,ligne,colonne);
                if(testWin){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Fin de la partie");
                    alert.setContentText("Vous avez gagné");
                    alert.show();
                }
            }
            iActif=-1; jActif=-1;
        }
    }

    @Override
    public void errorDetected(PartieInexistanteErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

}
