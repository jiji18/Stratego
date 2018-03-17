package packageJoueur;

import java.io.Serializable;

/**
 * @author Anthony
 */
public class Joueur implements Comparable<Joueur>, Cloneable, Serializable {

    private String identifiant;
    private String password;
    private EnumStatut statut;
    private int nbVictoires;
    private int nbDefaites;

    public Joueur(String identifiant, String password) {
        this.identifiant = identifiant;
        this.password = password;
        this.statut = EnumStatut.DISCONNECTED; //[JPH-21/12/16] Fix du statut à DISCONNECTED par défaut
        this.nbVictoires = 0;
        this.nbDefaites = 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        if(o.getClass() != this.getClass()) return false;
        return ((Joueur) o).getIdentifiant().equals(this.getIdentifiant());
    }

    @Override
    public int hashCode(){
        return identifiant.hashCode();
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumStatut getStatut() {
        return this.statut;
    }

    public void setStatut(EnumStatut statut) {
        this.statut = statut;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public int getNbDefaites() {
        return nbDefaites;
    }

    public void setNbDefaites(int nbDefaites) {
        this.nbDefaites = nbDefaites;
    }

    public void gagne() {
        nbVictoires++;
    }

    public void perdu() {
        nbDefaites++;
    }

    @Override
    public int compareTo(Joueur joueur) {
        return ((joueur.getNbVictoires() - joueur.getNbDefaites()) - (nbVictoires - nbDefaites));
    }

    public Joueur clone() {
        Joueur j = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            j = (Joueur) super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }

        // on renvoie le clone
        return j;
    }
}
