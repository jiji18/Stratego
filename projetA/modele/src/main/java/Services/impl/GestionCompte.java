package Services.impl;

import Exceptions.ErreurConnexionException;
import Exceptions.ErreurDeconnexionException;
import Exceptions.PseudoDejaUtiliseException;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import Services.IGestionCompte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de gestion de compte pour les joueurs
 *
 * @author Anthony
 */
public class GestionCompte implements IGestionCompte, Serializable {
    private final String FILENAME = "joueurs.ser";

    private List<Joueur> joueurs;

    public GestionCompte() {
        this.joueurs = new ArrayList<>();
    }

    public List<Joueur> getJoueursConnectes() {
        List<Joueur> connectes = new ArrayList<>();
        for (Joueur j : joueurs) {
            if (!j.getStatut().equals(EnumStatut.DISCONNECTED)) {
                connectes.add(j);
            }
        }
        return connectes;
    }

    @Override
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    @Override
    public Joueur getJoueurConnectes(String identifiant) {
        for (Joueur j : this.getJoueursConnectes()) {
            if (j.getIdentifiant().equals(identifiant)) return j;
        }
        return null;
    }

    @Override
    public Joueur getJoueur(String identifiant) {
        for (Joueur j : joueurs) {
            if (j.getIdentifiant().equals(identifiant)) return j;
        }
        return null;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    /**
     * Lors de la première connexion du joueur on l'inscrit dans notre base et le sauvegardons dans le fichier
     * si aucun autre joueur porte le même pseudo
     *
     * @param id
     * @param pass
     * @throws PseudoDejaUtiliseException
     */
    @Override
    public Joueur inscription(String id, String pass) throws PseudoDejaUtiliseException {
        for (Joueur j : joueurs) {
            if (j.getIdentifiant().equals(id)) {
                throw new PseudoDejaUtiliseException("Pseudo déjà utilisé");
            }
        }
        //on créé le joueur
        Joueur j = new Joueur(id, pass);
        this.joueurs.add(j);
        sauvegarderJoueurs();
        j.setStatut(EnumStatut.CONNECTED);
        this.getJoueursConnectes().add(j);
        return j;
    }

    /**
     * Si le joueur est inscrit alors on le connecte en lui changeant
     * son statut courant en "connected" et on l'ajoute à la liste des
     * joueur connectes
     *
     * @param id   l'identifiant de connexion
     * @param pass le mot de passe
     * @return le joueur qui vient de se connecté
     * @throws ErreurConnexionException si la connexion n'a pas eu lieu
     */
    @Override
    public Joueur connexion(String id, String pass) throws ErreurConnexionException {
        for (Joueur joueur : joueurs) {
            if (joueur.getIdentifiant().equals(id) && joueur.getPassword().equals(pass)) {
                if (!joueur.getStatut().equals(EnumStatut.DISCONNECTED)) {
                    throw new ErreurConnexionException("joueur deja connectés");
                }
                joueur.setStatut(EnumStatut.CONNECTED);
                return joueur;
            }
        }
        throw new ErreurConnexionException("Erreur de connexion");

    }

    /**
     * La methode deconnexion retire de la liste des joueurs connectes le joueur courant
     * et lui change son statut courant en "disconnected"
     *
     * @param id
     */
    @Override
    public Joueur deconnexion(String id) throws ErreurDeconnexionException {
        for (Joueur joueur : getJoueursConnectes()) {
            if (joueur.getIdentifiant().equals(id)) {
                joueur.setStatut(EnumStatut.DISCONNECTED);
                return joueur;
            }
        }
        throw new ErreurDeconnexionException("Aucun joueur avec ce pseudo n'est connecté");
    }

    @Override
    public void modifierMotDePasse(Joueur j, String newMotDePasse) {
        getJoueur(j.getIdentifiant()).setPassword(newMotDePasse);
    }

    @Override
    public void sauvegarderJoueurs(){
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fichier = new FileOutputStream(FILENAME);
            oos = new ObjectOutputStream(fichier);
            for(Joueur joueur : joueurs){
                //On sauvegarde le statut du joueur
                EnumStatut statut = joueur.getStatut();
                //on fixe un nouveau statut à DISCONNECTED pour l'enregistrer en base
                joueur.setStatut(EnumStatut.DISCONNECTED);
                //on ecrit
                oos.writeObject(joueur);
                //on rétabli le statut
                joueur.setStatut(statut);
            }
            oos.flush();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void chargerJoueurs(){
        ObjectInputStream ois = null;
        //test existence du fichier
        File f = new File(FILENAME);
        if(!f.exists()) return;
        //sinon on le charge
        try {
            final FileInputStream fichier = new FileInputStream(FILENAME);
            ois = new ObjectInputStream(fichier);
            Joueur joueur;
            //tant que des objts sont dispos, on charge
            while (true) {
                joueur = (Joueur) ois.readObject();
                joueurs.add(joueur);
            }
        }catch (EOFException e){
            //do anything, it's beacause the file is finished
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
