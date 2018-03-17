package vues;

import controleur.Controleur;
import controleur.erreurs.AucuneInvitation;
import controleur.erreurs.AucunePartie;
import controleur.erreurs.PartieComplete;
import controleur.erreurs.PartieInexistante;
import ecouteurs.LobbyErrorListener;
import ecouteurs.ModelChangedEvent;
import ecouteurs.ModelListener;
import ecouteurs.exceptions.DeconnexionErrorEvent;
import ecouteurs.exceptions.InvitationErrorEvent;
import ecouteurs.exceptions.JoueurOccupeEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import packageJoueur.Joueur;
import packagePartie.Partie;
import packagePartie.PartiePrivee;

import java.io.IOException;
import java.net.URL;

/**
 * @author Anthony
 */
public class LobbyVue implements ModelListener, LobbyErrorListener {

    private Timeline timer;
    private int tempsInnactif;

    private Controleur monControleur;

    private final Color couleurEntete = Color.BLUE;

    @FXML
    VBox racine;

    //<editor-fold desc="FXML Home">
    @FXML
    Pane ecranHome;
    @FXML
    GridPane partiesPubliques;
    @FXML
    GridPane joueursConnectes;
    @FXML
    Button newPublicGame;
    //</editor-fold>
    //<editor-fold desc="FXML Ranking">
    @FXML
    Pane ecranRanking;
    @FXML
    VBox classement;
    //</editor-fold>
    //<editor-fold desc="FXML Notifications">
    @FXML
    Pane ecranNotifications;
    @FXML
    GridPane invitations;
    //</editor-fold>
    //<editor-fold desc="FXML MyAccount">
    @FXML
    VBox myAccount;
    @FXML
    Pane ecranMyAccount;
    @FXML
    TextField accountLogin;
    @FXML
    TextField accountNbGames;
    @FXML
    TextField accountNbWin;
    @FXML
    TextField accountNbLose;
    @FXML
    GridPane formNewPass;
    @FXML
    PasswordField newPassword;
    @FXML
    PasswordField confirmNewPassword;
    @FXML
    Button validerNewPass;
    private boolean timerIsActivated;
    private boolean kicked;

    //</editor-fold>

    private void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    Node getNode() {
        return racine;
    }

    static LobbyVue creerInstance(Controleur monControleur) {
        URL location = LobbyVue.class.getResource("/vues/lobby.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LobbyVue vue = fxmlLoader.getController();
        vue.setMonControleur(monControleur);
        vue.initialize();
        return vue;
    }

    void relancerTimerLobby() {
        tempsInnactif = monControleur.getMaxTimeLobby();
    }

    private void initialize() {
        lancerTimerLobby();
        Button deconnecterButton = new Button("Deconnexion");
        deconnecterButton.setOnAction(e -> monControleur.deconnexion());
        racine.getChildren().add(deconnecterButton);
        chargerPartiesPubliques();
        chargerJoueursConnectes();
        chargerMyAccount();
        chargerRanking();
        chargerNotifications();

    }

    void lancerTimerLobby() {
        timerIsActivated = true;
        tempsInnactif = monControleur.getMaxTimeLobby();
        timer = new Timeline();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            if(!timerIsActivated)return;
                chargerPartiesPubliques();
                chargerJoueursConnectes();
                chargerRanking();
                chargerNotifications();
                if (monControleur.partiePrete()) {
                    arreterTimer();
                    switch (monControleur.getTagOnAGame()) {
                        case "j1":
                            monControleur.goToPlacementVue();
                            break;
                        case "j2":
                            monControleur.goToAttenteVue();
                            break;
                    }
                }
                if(kicked){
                    if(tempsInnactif <=0){
                        arreterTimer();
                        monControleur.deconnexion();
                    }else {
                        tempsInnactif--;
                    }
                }

        }));
        timer.play();
    }

    public void arreterTimer() {
        relancerTimerLobby();
        timerIsActivated = false;
        timer.stop();
    }

    @Override
    public void modelChanged(ModelChangedEvent event) {
        chargerPartiesPubliques();
        chargerJoueursConnectes();
        chargerMyAccount();
        chargerRanking();
        chargerNotifications();
    }

    private void chargerPartiesPubliques() {

        ecranHome.getChildren().remove(partiesPubliques);
        partiesPubliques = new GridPane();
        partiesPubliques.setLayoutX(14.0);
        partiesPubliques.setLayoutY(3.0);
        partiesPubliques.setPrefHeight(435.0);
        partiesPubliques.setPrefWidth(422.0);
        ecranHome.getChildren().add(partiesPubliques);

        Text enTetePP = new Text("Parties publiques");
        enTetePP.setFill(couleurEntete);
        partiesPubliques.add(enTetePP, 0, 0);

        try {
            int row = 1;
            partiesPubliques.add(new Text("Créateur"), 0, row);
            partiesPubliques.add(new Text(""), 1, row);
            partiesPubliques.add(new Text(""), 2, row);
            row++;

            for (Partie p : monControleur.getLesPartiesPublics()) {
                boolean test = true;
                if (p.getJ1() != null) {
                    if (p.getJ1().getIdentifiant().equals(monControleur.getUtilisateurLogin())) {
                        test = false;
                    }
                }
                if (p.getJ2() != null) {
                    if (p.getJ2().getIdentifiant().equals(monControleur.getUtilisateurLogin())) {
                        test = false;
                    }
                }
                if (test) {
                    partiesPubliques.add(new Text(p.getJ1().getIdentifiant()), 0, row);

                    Button joinButton = new Button("Rejoindre");
                    joinButton.setOnAction(e -> rejoindreViaControleur(p));
                    if (p.getJ2() != null) joinButton.setDisable(true);
                    partiesPubliques.add(joinButton, 1, row);

                    Button obsButton = new Button("Observer");
                    obsButton.setOnAction(e -> monControleur.rejoindrePartieObservateur(p));
                    partiesPubliques.add(obsButton, 2, row);
                    row++;
                }
            }
        } catch (AucunePartie aucunePartie) {
            partiesPubliques.add(new Text("Aucune partie publique n'a été trouvée"), 0, 2);
        }
        if (monControleur.getPartie() == null) {
            newPublicGame.setOnAction(this::creerPartiePublique);
            newPublicGame.setText("Créer une partie publique");
        } else {
            newPublicGame.setOnAction(e -> {
                monControleur.supprimerPartie();
                newPublicGame.setOnAction(this::creerPartiePublique);
                newPublicGame.setText("Créer une partie publique");
            });
            newPublicGame.setText("Annuler partie");
        }
    }

    private void rejoindreViaControleur(Partie p) {
        try {
            monControleur.rejoindrePartie(p);
        } catch (PartieComplete partieComplete) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Partie introuvable");
            alert.setHeaderText("Partie injoignable");
            alert.setContentText("La partie est déjà complète");
            alert.showAndWait();
        } catch (PartieInexistante partieInexistante) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Partie introuvable");
            alert.setHeaderText("Partie injoignable");
            alert.setContentText("La partie n'existe plus");
            alert.showAndWait();
        } catch (AucuneInvitation aucuneInvitation) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Partie introuvable");
            alert.setHeaderText("Partie injoignable");
            alert.setContentText("Aucune invitation");
            alert.showAndWait();
        }
    }

    private void chargerJoueursConnectes() {

        ecranHome.getChildren().remove(joueursConnectes);
        joueursConnectes = new GridPane();
        joueursConnectes.setLayoutX(461.0);
        joueursConnectes.setLayoutY(2.0);
        joueursConnectes.setPrefHeight(434.0);
        joueursConnectes.setPrefWidth(432.0);
        ecranHome.getChildren().add(joueursConnectes);

        Text enTeteJC = new Text("Joueurs connectés");
        enTeteJC.setFill(couleurEntete);
        joueursConnectes.add(enTeteJC, 0, 0);

        int row = 1;
        joueursConnectes.add(new Text("Pseudo"), 0, row);
        joueursConnectes.add(new Text("Statut"), 1, row);
        joueursConnectes.add(new Text(""), 2, row);
        row++;

        //On recupere la potentielle partie privée crée en cours
        Partie partieEnCours = monControleur.getPartie();
        PartiePrivee partiePriveEnCours = null;
        if (partieEnCours != null && partieEnCours.isPrivate())
            partiePriveEnCours = (PartiePrivee) monControleur.getPartie();

        //on parcours les joueurs connectés
        for (Joueur j : monControleur.getLesJoueursConnectes()) {
            if (!j.getIdentifiant().equals(monControleur.getUtilisateurLogin())) {
                joueursConnectes.add(new Text(j.getIdentifiant()), 0, row);
                String statutString;
                Color couleurStatut = Color.YELLOW;
                boolean disable = false;
                switch (j.getStatut()) {
                    case CONNECTED:
                        couleurStatut = Color.GREEN;
                        statutString = "Connecté  ";
                        break;
                    default:
                        statutString = "Occupé  ";
                        disable = true;
                }
                Text txtStatut = new Text(statutString);
                txtStatut.setFill(couleurStatut);
                joueursConnectes.add(txtStatut, 1, row);
                if (!disable && ((partieEnCours != null) || (partiePriveEnCours != null && partiePriveEnCours.containsInvite(j)))) {
                    disable = true;
                }

                Button defierButton = new Button("Défier");
                defierButton.setDisable(disable);
                defierButton.setOnAction(e -> {
                    monControleur.defier(j);

                });
                joueursConnectes.add(defierButton, 2, row);
                row++;
            }
        }
    }

    private void chargerNotifications() {

        ecranNotifications.getChildren().remove(invitations);
        invitations = new GridPane();
        ecranNotifications.getChildren().add(invitations);

        Text enTeteNotifs = new Text("Invitations");
        enTeteNotifs.setFill(couleurEntete);
        this.invitations.add(enTeteNotifs, 0, 0);

        int row = 1;
        invitations.add(new Text("Créateur"), 0, row);
        invitations.add(new Text(""), 1, row);
        invitations.add(new Text(""), 2, row);
        row++;


        try {

            for (Partie p : monControleur.getLesInvitations()) {
                Joueur j1 = p.getJ1();
                invitations.add(new Text(j1.getIdentifiant() + "  "), 0, row);

                Button joinButton = new Button("Rejoindre");
                joinButton.setOnAction(e ->
                    rejoindreViaControleur(p)
                );
                invitations.add(joinButton, 1, row);

                Button declineButton = new Button("X");
                declineButton.setOnAction(e -> monControleur.refuserInvitation(p));
                invitations.add(declineButton, 2, row);

            }
        } catch (AucuneInvitation aucuneInvitation) {
            invitations.add(new Text("Aucune invitation n'a été trouvée"), 0, row);
        }
    }

    private void chargerRanking() {

        ecranRanking.getChildren().remove(classement);
        classement = new VBox();
        ecranRanking.getChildren().add(classement);

        creerLabel(classement, "Classement");
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setHgap(5d);
        grid.setVgap(2d);
        classement.getChildren().add(grid);

        int row = 0, classement = 1;

        //<editor-fold desc="en-tete tab">
        Color couleurTr = Color.BLUEVIOLET;
        Text Pos = new Text("Pos");
        Pos.setFill(couleurTr);
        grid.add(Pos, 0, row);

        Text JoueurTr = new Text("Joueur");
        JoueurTr.setFill(couleurTr);
        grid.add(JoueurTr, 1, row);

        Text VictoireTr = new Text("Victoire");
        VictoireTr.setFill(couleurTr);
        grid.add(VictoireTr, 2, row);

        Text DefaiteTr = new Text("Defaite");
        DefaiteTr.setFill(couleurTr);
        grid.add(DefaiteTr, 3, row);

        Text RatioTr = new Text("Ratio");
        RatioTr.setFill(couleurTr);
        grid.add(RatioTr, 4, row);
        row++;
        //</editor-fold>

        for (Joueur joueur : monControleur.getClassement()) {

            grid.add(new Text(classement + "e"), 0, row);
            grid.add(new Text(joueur.getIdentifiant()), 1, row);
            grid.add(new Text(joueur.getNbVictoires() + ""), 2, row);
            grid.add(new Text(joueur.getNbDefaites() + ""), 3, row);

            int nbV = joueur.getNbVictoires(),
                    nbD = joueur.getNbDefaites();

            String stRatio =
                    nbV == 0 ? "0.0" : "" + ((float) nbV / (float) (nbV + nbD));

            int lengthToKeep = stRatio.length() >= 5 ? 5 : stRatio.length();
            grid.add(new Text(stRatio.substring(0, lengthToKeep)), 4, row);
            joueur.getIdentifiant();
            row++;
            classement++;
        }
    }

    private void creerLabel(Pane node, String text) {
        Label entete = new Label(text);
        entete.setTextFill(couleurEntete);
        node.getChildren().add(entete);
    }

    private void chargerMyAccount() {
        accountLogin.setText(monControleur.getUtilisateurLogin());
        accountNbGames.setText("" + monControleur.getUtilisateurNbGames());
        accountNbWin.setText("" + monControleur.getUtilisateurNbGamesWin());
        accountNbLose.setText("" + monControleur.getUtilisateurNbGamesLost());
    }

    public void gotoChangerMotDePasse(ActionEvent actionEvent) {
        formNewPass.setDisable(false);
        validerNewPass.setDisable(false);
    }

    public void changerMotDePasse() {
        String motDePasse = this.newPassword.getText();
        String confirmationMotDePasse = this.confirmNewPassword.getText();
        if (motDePasse.length() == 0 || confirmationMotDePasse.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Problème de mise à jour");
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();
        } else {
            if (!motDePasse.equals(confirmationMotDePasse)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de mise à jour");
                alert.setContentText("Les mots de passes doivent être identiques");
                alert.showAndWait();
            } else {
                monControleur.changerPassword(motDePasse);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Changement de mot de passe");
                alert.setContentText("Le mot de passe a correctement été changé");
                alert.showAndWait();
                formNewPass.setDisable(true);
                validerNewPass.setDisable(true);
                this.newPassword.setText("");
                this.confirmNewPassword.setText("");
            }
        }
    }

    public void creerPartiePublique(ActionEvent actionEvent) {
        monControleur.creerPartiePublique();
        cannotBeKicked();
        newPublicGame.setText("Annuler partie publique");
        newPublicGame.setOnAction(e -> {
            monControleur.supprimerPartie();
            canBeKicked();
            newPublicGame.setOnAction(e2 -> creerPartiePublique(e2));
            newPublicGame.setText("Créer une partie publique");
        });
    }

    @Override
    public void errorDetected(DeconnexionErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

    @Override
    public void errorDetected(JoueurOccupeEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

    @Override
    public void errorDetected(InvitationErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(event.getMessage());
        alert.showAndWait();
    }

    private void canBeKicked(){
        kicked = true;
    }
    private void cannotBeKicked(){
        kicked = false;
    }

}
