package com.dm.tpfinal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Question {
    private String titre;
    private String formulation;
    private String[] reponse;
    private Vector<String> choixMultiples;
    private int imageAssociee;
    // On utilise un tableau de Strings plutôt qu'un seul String pour la réponse
    // au cas où plusieurs réponses seraient acceptables ou si elles sont
    // multiples (ex : couleurs à combiner pour produire couleur x)

    public Question(String titre, String formulation, String[] reponse) {
        this.titre = titre;
        this.formulation = formulation;
        this.reponse = reponse;
        this.choixMultiples = null;
        this.imageAssociee = 0;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String[] getReponse() {
        return reponse;
    }

    public void setReponse(String[] reponse) {
        this.reponse = reponse;
    }

    public void melangeReponse() {
        Collections.shuffle(choixMultiples);
    }

    public Vector<String> getChoixMultiples() {
        return choixMultiples;
    }

    public void setChoixMultiples(Vector<String> choixMultiples) {
        this.choixMultiples = choixMultiples;
    }

    public static Vector<String> genererChoixMultiple(String[] choixReponse) {
        Vector<String> v = new Vector<String>();
        for(String choix: choixReponse) {
            v.add(choix);
        }

        return v;
    }

    public int getImageAssociee() { return imageAssociee; }

    public void setImageAssociee(int adresseImage) { this.imageAssociee = adresseImage; }
}
