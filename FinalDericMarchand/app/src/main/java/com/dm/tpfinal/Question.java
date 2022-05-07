package com.dm.tpfinal;

import java.util.Vector;

public class Question {
    private String titre;
    private String formulation;
    private String[] reponse;
    private String[] choixMultiples;
    // On utilise un tableau de Strings plutôt qu'un seul String pour la réponse
    // au cas où plusieurs réponses seraient acceptables ou si elles sont
    // multiples (ex : couleurs à combiner pour produire couleur x)

    public Question(String titre, String formulation, String[] reponse) {
        this.titre = titre;
        this.formulation = formulation;
        this.reponse = reponse;
        this.choixMultiples = null;
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

    public String[] getChoixMultiples() {
        return choixMultiples;
    }

    public void setChoixMultiples(String[] choixMultiples) {
        this.choixMultiples = choixMultiples;
    }
}
