package com.dm.tpfinal;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public interface DefiActivityInterface {

    // Interface afin de mettre en oeuvre des méthodes récurrentes
    // qui devront être présentes dans toutes les activités Défi de notre application

    public Vector<Question> genererQuestions();
    public void nouvelleQuestion();
}
