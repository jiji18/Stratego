package vues;

import controleur.Controleur;
import controleur.erreurs.PartieInexistante;
import ecouteurs.ModelChangedEvent;
import ecouteurs.ModelListener;
import ecouteurs.PlacementErrorListener;
import ecouteurs.exceptions.PartieInexistanteErrorEvent;
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
import packagePlateau.Pion;
import packagePlateau.packageCase.Case;
import packagePlateau.packageCase.TypeCase;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author jolan
 */
public class PlacementVue implements ModelListener, PlacementErrorListener {

    //<editor-fold desc="Constantes">
    private final double HEIGHT_IMAGE = 40;
    private final double WIDTH_IMAGE = 40;
    private final double INSET_SIZE =1;
    private final int PION_PAR_LIGNE_APLACER = 5;
    //</editor-fold>
    //<editor-fold desc="FXML">
    @FXML
    Pane ecranGrille;
    @FXML
    Pane ecranPionsAPlacer;
    @FXML
    private VBox racine;
    @FXML
    private Button btnEnvoyer;
    @FXML
    private Button btnRandom;

    //</editor-fold>

    private Controleur monControleur;
    private Button[][] buttonsGrille;
    private Button[][] buttonsAPlacer;

    private int[][] grille;
    private int[][] aPlacer;

    private int ligneChoisie;
    private int colonneChoisie;
    private Boolean onGrilleChoisie;

    private int nbElem;
    private boolean testChargement;

    private GridPane grilleGrid;
    @Override
    public void errorDetected(PartieInexistanteErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

    @Override
    public void modelChanged(ModelChangedEvent event) {
    }

    static PlacementVue creerInstance(Controleur monControleur) {
        URL location = PlacementVue.class.getResource("/vues/placement.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlacementVue vue = fxmlLoader.getController();
        vue.setMonControleur(monControleur);
        vue.initialize();
        return vue;
    }

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    Node getNode() {
        return racine;
    }

    private void initialize() {
        testChargement = true;
    }

    void chargementPlacement() {
        if(!testChargement) return;
        if(!monControleur.partiePrete() || !testChargement) return;
        testChargement = false;
        initVariablesChoisies();
        chargerGrille();
        chargerAPlacer();
        chargerAffichage();
    }

    private void chargerGrille(){
        Case[][] grilleCase = monControleur.getDamier();
        buttonsGrille = new Button[grilleCase.length][grilleCase[0].length];
        grille = new int[buttonsGrille.length][buttonsGrille[0].length];
        String tagJoueur = monControleur.getTagOnAGame();
        String tagAdv = tagJoueur.equals("j1") ? "j2" : "j1";

        for (int ligne = 0; ligne < grilleCase.length; ligne++) {
            for (int colonne = 0; colonne < grilleCase[ligne].length; colonne++) {
                Button btnCase = new Button();
                Case laCase = grilleCase[ligne][colonne];
                TypeCase typeC = laCase.getType();
                Image img = null;
                switch (typeC) {
                    case PION:
                        img = createImage("/img/" + tagAdv + "_pion_cachee.bmp");
                        btnCase.setDisable(true);
                        break;
                    case OBSTACLE:
                        img = createImage("/img/CaseLac-" + ligne + colonne + ".jpg");
                        btnCase.setDisable(true);
                        break;
                    case VIDE:
                        img = createImage("/img/CaseHerbe.jpg");
                        btnCase.setDisable(true);
                        break;
                    case VIDEINITIALE:
                        img = createImage("/img/CaseHerbe.jpg");
                        int fLigne = ligne, fColonne = colonne;
                        btnCase.setOnAction(actionEvent -> clique(fLigne,fColonne,true));
                        break;
                }
                ImageView iw = new ImageView(img);
                formatImageView(iw, btnCase);
                buttonsGrille[ligne][colonne] = btnCase;
                grille[ligne][colonne] = laCase.getValeur();
            }
        }
    }
    private void chargerAPlacer(){
        String tag = monControleur.getTagOnAGame();
        List<Pion> lesPions = monControleur.getPionsAPlacer();
        this.nbElem = lesPions.size();
        buttonsAPlacer = new Button[this.nbElem/this.PION_PAR_LIGNE_APLACER][this.PION_PAR_LIGNE_APLACER]; //equivalent a 8 lignes, et 5 colonnes
        aPlacer = new int[buttonsAPlacer.length][buttonsAPlacer[0].length];
        int ligne = 0, colonne = 0;
        for(Pion pion : lesPions){
            Button btn = new Button();
            int fLigne = ligne, fColonne = colonne;
            btn.setOnAction(e -> clique(fLigne, fColonne, false));
            Image img = createImage("/img/" + tag + "_pion_"+pion.getValeur()+".bmp");
            formatImageView(new ImageView(img),btn);
            buttonsAPlacer[ligne][colonne]=btn;
            aPlacer[ligne][colonne] = pion.getValeur();
            colonne++;
            if(colonne == this.PION_PAR_LIGNE_APLACER){
                colonne = 0;
                ligne++;
            }
        }
    }
    private void chargerAffichage(){
        grilleGrid = new GridPane();
        for(int ligne = 0; ligne < buttonsGrille.length; ligne++){
            for(int colonne=0; colonne <buttonsGrille[ligne].length; colonne++){
                grilleGrid.add(buttonsGrille[ligne][colonne], colonne,ligne);
            }
        }
        grilleGrid.setGridLinesVisible(true);
        if (ecranGrille.getChildren().size() > 0 && ecranGrille.getChildren().get(0) != null)
            ecranGrille.getChildren().remove(0);
        ecranGrille.getChildren().add(grilleGrid);

        GridPane aPlacerGrid = new GridPane();
        for(int ligne = 0; ligne < buttonsAPlacer.length; ligne++){
            for(int colonne=0; colonne<buttonsAPlacer[ligne].length; colonne++){
                aPlacerGrid.add(buttonsAPlacer[ligne][colonne], colonne, ligne);
            }
        }
        aPlacerGrid.setGridLinesVisible(true);
        if (ecranPionsAPlacer.getChildren().size() > 0 && ecranPionsAPlacer.getChildren().get(0) != null)
            ecranPionsAPlacer.getChildren().remove(0);
        ecranPionsAPlacer.getChildren().add(aPlacerGrid);
    }

    private void clique(int ligne, int colonne, boolean onGrille){
        if(this.ligneChoisie==-1 && this.colonneChoisie==-1 && this.onGrilleChoisie==null){
            this.ligneChoisie=ligne;
            this.colonneChoisie=colonne;
            this.onGrilleChoisie=onGrille;
            setRedBorders(ligne, colonne, onGrille);
        }else{
            btnRandom.setDisable(true);
            if(onGrilleChoisie){
                if(onGrille){
                    int pivot= grille[ligne][colonne];
                    grille[ligne][colonne] = grille[ligneChoisie][colonneChoisie];
                    grille[ligneChoisie][colonneChoisie] = pivot;

                    changeImage(ligneChoisie,colonneChoisie,true);
                    changeImage(ligne, colonne, true);

                }else{
                    int pivot= aPlacer[ligne][colonne];
                    aPlacer[ligne][colonne] = grille[ligneChoisie][colonneChoisie];
                    grille[ligneChoisie][colonneChoisie] = pivot;

                    changeImage(ligne,colonne,false);
                    changeImage(ligneChoisie,colonneChoisie,true);
                }
            }else{
                if(onGrille){
                    int pivot = grille[ligne][colonne];
                    grille[ligne][colonne] = aPlacer[ligneChoisie][colonneChoisie];
                    aPlacer[ligneChoisie][colonneChoisie] = pivot ;

                    changeImage(ligne,colonne,true);
                    changeImage(ligneChoisie,colonneChoisie,false);
                }else{
                    int pivot = aPlacer[ligne][colonne];
                    aPlacer[ligne][colonne] = aPlacer[ligneChoisie][colonneChoisie];
                    aPlacer[ligneChoisie][colonneChoisie] = pivot;

                    changeImage(ligne,colonne,false);
                    changeImage(ligneChoisie,colonneChoisie,false);
                }
            }
            removeBorders(ligneChoisie,colonneChoisie,onGrilleChoisie);
            initVariablesChoisies();
            chargerAffichage();
            this.btnEnvoyer.setDisable(!allValueArePut());
        }
    }

    private boolean allValueArePut() {
        for(int i=grille.length-4; i<grille.length;i++)
            for(int j=0; j<grille[i].length;j++)
                if (grille[i][j] < 0) return false;
        return true;
    }

    private void initVariablesChoisies() {
        this.ligneChoisie=-1;
        this.colonneChoisie=-1;
        this.onGrilleChoisie=null;
    }

    //<editor-fold desc="Buttons">
    public void envoyerValeur() {
        monControleur.envoyer(grille);
    }
    public void abandonnerPartie() {
        try {
            monControleur.abandonner();
        } catch (PartieInexistante partieInexistante) {
            errorDetected(new PartieInexistanteErrorEvent(monControleur, partieInexistante.getMessage()));
        }
    }

    public void resetGrilles() {
        int ligneAPlacer = 0;
        int colAPlacer = 0;
        for(int ligneGrille = grille.length-4; ligneGrille < grille.length; ligneGrille++)
            for(int colGrille = 0; colGrille < grille[ligneGrille].length; colGrille++){
                clique(ligneAPlacer,colAPlacer,false);
                clique(ligneGrille,colGrille,true);
                colAPlacer++;
                if(colAPlacer==aPlacer[0].length){
                    colAPlacer =0;
                    ligneAPlacer++;
                }
            }
    }

    public void placementALeatoire() {
        class Couple{
            private int x,y;
            private Couple(int x, int y){
                this.x = x;
                this.y = y;
            }
        }
        List<Couple> coords = new ArrayList<>();
        for(int ligne = 0; ligne<aPlacer.length; ligne++){
            for(int colonne = 0; colonne<aPlacer[ligne].length; colonne++) {
                coords.add(new Couple(ligne,colonne));
            }
        }
        Collections.shuffle(coords);
        int count = 0;
        for(int i=buttonsGrille.length-4; i<buttonsGrille.length; i++){
            for(int j=0; j<buttonsGrille[i].length;j++){
                clique(i,j,true);
                Couple coord = coords.get(count);
                clique(coord.x,coord.y,false);
                count++;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Border">
    private void removeBorders(int ligne, int colonne, Boolean onGrille) {
        Button btn = onGrille? buttonsGrille[ligne][colonne] : buttonsAPlacer[ligne][colonne];
        btn.setStyle("");
    }

    private void setRedBorders(int ligne, int colonne, boolean onGrille) {
        Button btn = onGrille? buttonsGrille[ligne][colonne] : buttonsAPlacer[ligne][colonne];
        btn.setStyle("-fx-border-color: #ff000b");
    }

    //</editor-fold>
    //<editor-fold desc="Images">
    private Image createImage(String s) {
        return new Image(PlacementVue.class.getResourceAsStream(s));
    }

    private void formatImageView(ImageView iw, Button btn) {
        btn.setPadding(new Insets(INSET_SIZE));
        iw.setFitHeight(HEIGHT_IMAGE);
        iw.setFitWidth(WIDTH_IMAGE);
        btn.setGraphic(iw);
    }

    private void changeImage(int ligne, int colonne, boolean isGrille) {
        String tag = monControleur.getTagOnAGame();

        Button[][] buttons;
        int[][] valeurs;
        if(isGrille){
            buttons = buttonsGrille;
            valeurs = grille;
        }else{
            buttons = buttonsAPlacer;
            valeurs = aPlacer;
        }

        Image img;

        Button btn = buttons[ligne][colonne];
        int val=valeurs[ligne][colonne];
        switch(val){
            case -1:
            case -2:
                if(isGrille) img = createImage("/img/CaseHerbe.jpg");
                else img = createImage("/img/"+tag+"_pion_cachee.bmp");
                break;
            default:
                img = createImage("/img/"+tag+"_pion_"+valeurs[ligne][colonne]+".bmp");
        }
        formatImageView(new ImageView(img),btn);
    }
    //</editor-fold>
}