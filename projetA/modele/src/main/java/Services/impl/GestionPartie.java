package Services.impl;


import Exceptions.AucunePartieException;
import Exceptions.JoueurOccupeException;
import Exceptions.PartieCompleteException;
import Exceptions.PartieInexistanteException;
import packageJoueur.EnumStatut;
import packageJoueur.Joueur;
import packagePartie.*;
import Services.IGestionPartie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Anthony
 */
public class GestionPartie implements IGestionPartie, Serializable {

    private Map<Joueur, Partie> lesParties;
    private List<Partie> lesPartiesFinies;

    public GestionPartie(Map<Joueur, Partie> lesParties, List<Partie> lesPartiesFinies) {
        this.lesParties = lesParties;
        this.lesPartiesFinies = lesPartiesFinies;
    }

    @Override
    public Partie creerPartie(Joueur j1) {
        //On créer la partie et on l'ajoute
        Partie p = new PartiePublique(j1);
        //On change le statut du joueur pour prévenir qu'il est en attente
        j1.setStatut(EnumStatut.WAITINGPLAYER);
        lesParties.put(j1, p);
        return p;
    }

    @Override
    public Partie defier(Joueur j1, Joueur j2) throws JoueurOccupeException {
        // On vérifie si le joueur est connecté
        if (j2.getStatut() != EnumStatut.CONNECTED) throw new JoueurOccupeException();
        Partie p;
        if (!lesParties.containsKey(j1)) {
            //On créer la partie et on l'ajoute
            p = new PartiePrivee(j1);
            //On change le statut du joueur pour prévenir qu'il est en attente
            j1.setStatut(EnumStatut.WAITINGPLAYER);
            lesParties.put(j1, p);
        } else {
            p = getPartie(j1);
        }
        ((PartiePrivee) p).addInvite(j2);
        return p;
    }

    @Override
    public boolean estAttenduPourJouer(Joueur j) {
        for(Map.Entry<Joueur, Partie> e: lesParties.entrySet()){
            if (e.getValue().isPrivate()) {
                if(((PartiePrivee)e.getValue()).containsInvite(j)) return (j.getStatut() == EnumStatut.CONNECTED);
            }
        }
        return false;
    }

    @Override
    public Partie rejoindrePartiePrivee(Joueur j, Partie p) throws PartieInexistanteException, PartieCompleteException, AucunePartieException {
        //On vérifie si la partie existe toujours
        assert (p.isPrivate());
        if (!getLesPartiesPrivees().contains(p)) throw new PartieInexistanteException();
        else {
            if (p.estComplete()) throw new PartieCompleteException();
            p.setJ2(j);
            ((PartiePrivee) p).clearInvites();
            j.setStatut(EnumStatut.WAITINGPLAY);
            p.getJ1().setStatut(EnumStatut.PLAYING);
            lesParties.put(j, p);
        }

        return lesParties.get(j);
    }

    @Override
    public List<Partie> getInvitations(Joueur j) throws PartieInexistanteException, AucunePartieException {
        List<Partie> invits = new ArrayList<Partie>();
        for (Partie p : getLesPartiesPrivees()) {
            if (((PartiePrivee) p).containsInvite(j))
                invits.add(p);
        }
        return invits;
    }

    @Override
    public Partie rejoindrePartieObservateur(Joueur j, Partie p) {
        assert (!p.isPrivate());
        ((PartiePublique) p).addObservateur(j);
        lesParties.put(j, p);
        j.setStatut(EnumStatut.OBSERVING);
        return p;
    }

    @Override
    public void quitterObservation(Partie p, Joueur j) {
        assert (!p.isPrivate());
        lesParties.remove(j);
        ((PartiePublique) p).removeObservateur(j);
        j.setStatut(EnumStatut.CONNECTED);
    }

    @Override
    public boolean partieEnAttente(Partie p) {
        return p.getJ1().getStatut() == EnumStatut.WAITINGPLAYER;
    }

    /**
     * Cette méthode vérifie si une partie existe.
     *
     * @param p une partie dont on veut tester l'existance.
     * @return true si la partie existe, false sinon.
     */
    private boolean existeToujours(Partie p) {
        for (Map.Entry<Joueur, Partie> entry : lesParties.entrySet()) {
            if (entry.getValue().equals(p)) return true;
        }
        return false;
    }

    @Override
    public Partie rejoindrePartie(Joueur j, Partie p) throws PartieCompleteException, PartieInexistanteException {
        if (!this.existeToujours(p)) throw new PartieInexistanteException();
        else if (p.estComplete()) throw new PartieCompleteException();
        else {
            p.setJ2(j);
            j.setStatut(EnumStatut.WAITINGPLAY);
            p.getJ1().setStatut(EnumStatut.PLAYING);
            lesParties.put(j, p);
        }
        return p;
    }

    @Override
    public void abandonnerPartie(Joueur j) throws PartieInexistanteException {
        Joueur j1, j2;
        if(lesParties.containsKey(j)){
            Partie p = lesParties.get(j);
            p.getJ1().setStatut(EnumStatut.CONNECTED);
            p.getJ2().setStatut(EnumStatut.CONNECTED);
            j.perdu();
            if(j.equals(p.getJ1())){
                p.getJ2().gagne();
                p.setGagnant(p.getJ2());
            }
            else{
                p.getJ1().gagne();
                p.setGagnant(p.getJ1());
            }
            removePartiesAnterieurs(j);
            lesPartiesFinies.add(p.clone());
            j1 = p.getJ1();
            j2 = p.getJ2();
            List<Joueur> observateurs = new ArrayList<>();
            if(!p.isPrivate()){
                observateurs = ((PartiePublique)p).getObservateurs();
            }
            lesParties.remove(j1);
            lesParties.remove(j2);
            for(Joueur joueur : observateurs){
                joueur.setStatut(EnumStatut.CONNECTED);
                lesParties.remove(joueur);
            }
        }else throw new PartieInexistanteException();
    }

    @Override
    public List<Partie> getLesPartiesPublics() throws AucunePartieException {
        List<Partie> parties = new ArrayList<Partie>();
        for (Joueur j : lesParties.keySet()) {
            Partie p = lesParties.get(j);
            if (!p.isPrivate() && !parties.contains(p)) {
                parties.add(p);
            }
        }
        if (parties.isEmpty()) throw new AucunePartieException();
        return parties;
    }

    private void removePartiesAnterieurs(Joueur j){
        for(int i = 0; i<lesPartiesFinies.size(); i++){
            if(j.equals(lesPartiesFinies.get(i).getJ1()) || j.equals(lesPartiesFinies.get(i).getJ2())){
                lesPartiesFinies.remove(i);
                i--;
            }
            else if (!lesPartiesFinies.get(i).isPrivate()){
                if(((PartiePublique)lesPartiesFinies.get(i)).getObservateurs().contains(j)){
                    lesPartiesFinies.remove(i);
                    i--;
                }
            }
        }
    }

    public Partie getLesPartiesFinies(Joueur j) throws AucunePartieException{
        for(Partie p : lesPartiesFinies){
            if(j.equals(p.getJ1()) || j.equals(p.getJ2())) return p;
            else if (!p.isPrivate()){
                if(((PartiePublique)p).getObservateurs().contains(j)) return p;
            }
        }
        throw new AucunePartieException();
    }

    @Override
    public List<Partie> getLesPartiesFinies() throws AucunePartieException {
        return lesPartiesFinies;
    }

    @Override
    public Partie getPartie(Joueur j) {
        return lesParties.get(j);
    }

    @Override
    public void rejeterInvitation(Joueur j, Partie p) {
        assert (p.isPrivate());
        ((PartiePrivee) p).removeInvite(j);
    }

    @Override
    public boolean removePartie(Joueur j, Partie p) {
        // si ce n'est pas le j1 qui supprime une partie, alors on ne supprime pas
        if (!p.getJ1().equals(j)) return false;
        p.getJ1().setStatut(EnumStatut.CONNECTED);
        // On retire les parties des observateurs
        if(!p.isPrivate()){
            for(Joueur joueur : ((PartiePublique)p).getObservateurs()){
                joueur.setStatut(EnumStatut.CONNECTED);
                lesParties.remove(joueur);
            }
        }
        //Enfin on retire la partie du joueur 1 et 2 s'il existe
        if(p.getJ2() != null) lesParties.remove(p.getJ2());
        lesParties.remove(j);
        return true;
    }

    @Override
    public List<Partie> getLesPartiesPrivees()throws AucunePartieException {
        List<Partie> parties = new ArrayList<Partie>();
        for (Joueur j : lesParties.keySet()) {
            Partie p = lesParties.get(j);
            if (p.isPrivate() && !parties.contains(p)) {
                parties.add(p);
            }
        }
        if(parties.isEmpty()) throw new AucunePartieException();
        return parties;
    }
}
