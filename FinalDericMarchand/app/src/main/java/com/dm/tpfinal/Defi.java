package com.dm.tpfinal;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Defi implements Serializable {
    private String nom;
    private boolean reussi;
    private String description;
    private String activitePhysique;
    private Vector<Question> questions;
    private Question questionCourante;
    private Vector<String> reponseCourante;

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

    public Question questionAleatoire() {
        Random r = new Random();
        int index = r.nextInt(questions.size());
        return questions.get(index);
    }

    public void retirerQuestion(Question question) {
        questions.remove(question);
    }

    public int nbrQuestionsRestantes() {
        return questions.size();
    }

    public Vector<String> getReponseCourante() {
        return reponseCourante;
    }

    public Question getQuestionCourante() {
        return questionCourante;
    }

    public void setQuestionCourante(Question questionCourante) {
        this.questionCourante = questionCourante;
    }

    public void setReponseCourante(Vector<String> reponseCourante) {
        this.reponseCourante = reponseCourante;
    }

    public void ajoutReponseCourante(String reponse) {
        this.reponseCourante.add(reponse);
    }

    public void effacerReponseCourante() {
        this.reponseCourante.clear();
    }

    public String getActivitePhysique() {
        return activitePhysique;
    }

    public void setActivitePhysique(String activitePhysique) {
        this.activitePhysique = activitePhysique;
    }
}
