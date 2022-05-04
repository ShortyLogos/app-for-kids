package com.dm.tpfinal;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Defi implements Serializable {
    private String nom;
    private boolean reussi;
    private String description;
    private String activitePhysique;
    private Vector<Question> questions;

    public Defi(String nom, String description, boolean reussi, String activitePhysique, Vector<Question> questions) {
        this.nom = nom;
        this.reussi = reussi;
        this.description = description;
        this.activitePhysique = activitePhysique;
        this.questions = questions;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public Vector<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Vector<Question> questions) {
        this.questions = questions;
    }

    public String getActivitePhysique() {
        return activitePhysique;
    }

    public void setActivitePhysique(String activitePhysique) {
        this.activitePhysique = activitePhysique;
    }
}
