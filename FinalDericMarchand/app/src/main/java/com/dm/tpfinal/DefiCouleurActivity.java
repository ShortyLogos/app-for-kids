package com.dm.tpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class DefiCouleurActivity extends AppCompatActivity {

    ConstraintLayout zoneChevalet;
    TextView zoneQuestionCouleur;
    LinearLayout centreChevalet;
    LinearLayout zoneChoixCouleur;
    LinearLayout zoneChoisieCouleur;
    Defi defiCouleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_couleur);

        zoneChevalet = findViewById(R.id.zoneChevalet);
        centreChevalet = findViewById(R.id.centreChevalet);
        zoneQuestionCouleur = findViewById(R.id.zoneQuestionCouleur);
        zoneChoixCouleur = findViewById(R.id.zoneChoixCouleur);
        zoneChoisieCouleur = findViewById(R.id.zoneChoisieCouleur);

        defiCouleur = new Defi("Couleur Mêlées",
                "Déplace les couleurs vers le chevalet afin de répondre aux questions!",
                false,
                "Faites 5 pas en arrière",
                genererQuestions());

        defiCouleur.setQuestionCourante(defiCouleur.questionAleatoire());
        zoneQuestionCouleur.setText(defiCouleur.getQuestionCourante().getFormulation());

        // On remplit la zone de couleurs possibles de CouleurView correspondant aux couleurs primaires
        remplirCouleurs();

        Ecouteur ec = new Ecouteur();

        // Inscription des sources (le chevalet et les couleurs) à l'écouteur
        centreChevalet.setOnDragListener(ec);
        for (int couleur = 0; couleur < zoneChoixCouleur.getChildCount(); couleur++) {
            zoneChoixCouleur.getChildAt(couleur).setOnTouchListener(ec);
        }
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        @Override
        public boolean onDrag(View source, DragEvent dragEvent) {
            CouleurView couleur = (CouleurView)dragEvent.getLocalState(); // la couleur

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    zoneChevalet.setBackgroundResource(R.drawable.chevalet_hover);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    zoneChevalet.setBackgroundResource(R.drawable.chevalet);
                    break;

                case DragEvent.ACTION_DROP:
                    if (Arrays.asList(defiCouleur.getQuestionCourante().getReponse()).contains(couleur.getCouleur().getNom())) {
                        zoneChoixCouleur.removeView(couleur);
                        zoneChoisieCouleur.addView(couleur);
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    zoneChevalet.setBackgroundResource(R.drawable.chevalet);
                    couleur.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

            return true;
        }

        @Override
        public boolean onTouch(View couleurView, MotionEvent event) {
            View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(couleurView);
            couleurView.startDragAndDrop(null, dragshadow, couleurView, View.DRAG_FLAG_OPAQUE);

            couleurView.setVisibility(View.INVISIBLE);

            return true;
        }
    }

    // Redéfinition de la classe ImageView avec ajout d'un objet Couleur comme propriété
    private class CouleurView extends androidx.appcompat.widget.AppCompatImageView {
        private Couleur couleur;

        public CouleurView(@NonNull Context context, Couleur couleur) {
            super(context);
            this.couleur = couleur;

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            this.setLayoutParams(param);

            switch (couleur.getNom()) {
                case "Rouge":
                    this.setImageResource(R.drawable.ic_droplet_rouge);
                    break;
                case "Jaune":
                    this.setImageResource(R.drawable.ic_droplet_jaune);
                    break;
                case "Bleu":
                    this.setImageResource(R.drawable.ic_droplet_bleu);
                    break;
                case "Orange":
                    this.setImageResource(R.drawable.ic_droplet_orange);
                    break;
                case "Vert":
                    this.setImageResource(R.drawable.ic_droplet_vert);
                    break;
                case "Violet":
                    this.setImageResource(R.drawable.ic_droplet_violet);
                    break;
            }
        }

        public Couleur getCouleur() {
            return couleur;
        }
    }

    private void remplirCouleurs() {
        for (int i = 0; i < zoneChoixCouleur.getChildCount(); i++) {
            zoneChoixCouleur.removeViewAt(i);
        }

        for (Couleur c : Couleur.couleursPrimaires()) {
            zoneChoixCouleur.addView(new CouleurView(DefiCouleurActivity.this, c));
        }
    }

    private Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();
        questions.add(new Question("Vert", "Quelle combinaison de couleurs permet d'obtenir du vert ?", new String[]{"Bleu", "Jaune"}));
        questions.add(new Question("Orange", "Quelle combinaison de couleurs permet d'obtenir du vert ?", new String[]{"Rouge", "Jaune"}));
        questions.add(new Question("Violet", "Quelle combinaison de couleurs permet d'obtenir du vert ?", new String[]{"Bleu", "Rouge"}));
        questions.add(new Question("France", "À part le blanc, quelle couleur retrouve-t-on sur le drapeau de la France ?", new String[]{"Bleu", "Rouge"}));
        questions.add(new Question("Canada", "À part le blanc, quelle couleur retrouve-t-on sur le drapeau du Canada ?", new String[]{"Rouge"}));
        questions.add(new Question("Québec", "À part le blanc, quelle couleur retrouve-t-on sur le drapeau du Québec ?", new String[]{"Bleu"}));
        questions.add(new Question("Ketchup", "De quelle couleur est le ketchup ?", new String[]{"Rouge"}));
        questions.add(new Question("Moutarde", "De quelle couleur est la moutarde ?", new String[]{"Jaune"}));
        questions.add(new Question("Bleu", "De quelle couleur est le ciel ?", new String[]{"Bleu"}));

        return questions;
    }
}