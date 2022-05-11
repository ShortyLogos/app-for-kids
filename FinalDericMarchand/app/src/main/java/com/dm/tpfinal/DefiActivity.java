package com.dm.tpfinal;

import java.util.Vector;

public interface DefiActivity {

    // Interface afin de mettre en oeuvre des méthodes récurrentes
    // qui devront être présentes dans toutes les activités Défi de notre application

    public Vector<Question> genererQuestions();
    public void nouvelleQuestion();
    public void showDefiPresentation(String texte, String titre);
}
