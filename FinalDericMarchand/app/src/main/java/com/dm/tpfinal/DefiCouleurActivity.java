package com.dm.tpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class DefiCouleurActivity extends AppCompatActivity implements DefiActivity {

    ConstraintLayout zoneChevalet;
    TextView zoneQuestionCouleur;
    LinearLayout centreChevalet;
    LinearLayout zoneChoixCouleur;
    LinearLayout zoneChoisieCouleur;
    Defi defiCouleur;
    Ecouteur ec;
    boolean questionCompletee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_couleur);

        zoneChevalet = findViewById(R.id.zoneChevalet);
        centreChevalet = findViewById(R.id.centreChevalet);
        zoneQuestionCouleur = findViewById(R.id.zoneQuestionCouleur);
        zoneChoixCouleur = findViewById(R.id.zoneChoixCouleur);
        zoneChoisieCouleur = findViewById(R.id.zoneChoisieCouleur);

        defiCouleur = new Defi(getResources().getString(R.string.defi_couleurs_nom_defi),
                getResources().getString(R.string.defi_couleurs_description),
                false,
                getResources().getString(R.string.activite_physique_reculons),
                genererQuestions());

        nouvelleQuestion();

        ec = new Ecouteur();

        // On remplit la zone de couleurs possibles de CouleurView correspondant aux couleurs primaires
        remplirCouleurs();

        // Inscription du chevalet à l'écouteur
        // Celle des objets CouleurView se fait dans la méthode remplirCouleurs()
        centreChevalet.setOnDragListener(ec);
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
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
                        couleur.setOnTouchListener(null);
                        zoneChoixCouleur.removeView(couleur);
                        zoneChoisieCouleur.addView(couleur);

                        if (zoneChoisieCouleur.getChildCount() == defiCouleur.getQuestionCourante().getReponse().length) {
                            defiCouleur.getQuestions().remove(defiCouleur.getQuestionCourante());
                            if (defiCouleur.getQuestions().size() > 0) {
                                questionCompletee = true;
                            }
                            else {
                                Utils.showActiviteDialog(DefiCouleurActivity.this, defiCouleur.getActivitePhysique(), R.drawable.reculons);
//                                Intent i = new Intent(DefiCouleurActivity.this, DefiPersosActivity.class);
//                                finish();
//                                startActivity(i);
                            }
                        }
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if (questionCompletee) {
                        questionCompletee = false;
                        nouvelleQuestion();
                        remplirCouleurs();
                    }
                    zoneChevalet.setBackgroundResource(R.drawable.chevalet);
                    couleur.setVisibility(View.VISIBLE);

                    break;

                default:
                    break;
            }

            return true;
        }

        @SuppressLint("ClickableViewAccessibility")
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
        private final Couleur couleur;

        @SuppressLint("ClickableViewAccessibility")
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

    @SuppressLint("ClickableViewAccessibility")
    private void remplirCouleurs() {
        zoneChoixCouleur.removeAllViews();
        zoneChoisieCouleur.removeAllViews();
        for (Couleur c : Couleur.couleursPrimaires()) {
            CouleurView couleurView = new CouleurView(DefiCouleurActivity.this, c);
            couleurView.setOnTouchListener(ec);
            zoneChoixCouleur.addView(couleurView);
        }
    }

    public void nouvelleQuestion() {
        defiCouleur.setQuestionCourante(defiCouleur.questionAleatoire());
        zoneQuestionCouleur.setText(defiCouleur.getQuestionCourante().getFormulation());
    }

    public Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();
        Question q = null;
        questions.add(new Question("Vert", getResources().getString(R.string.defi_couleurs_vert), new String[]{"Bleu", "Jaune"}));
        questions.add(new Question("Orange", getResources().getString(R.string.defi_couleurs_orange), new String[]{"Rouge", "Jaune"}));
        questions.add(new Question("Violet", getResources().getString(R.string.defi_couleurs_violet), new String[]{"Bleu", "Rouge"}));
        questions.add(new Question("France", getResources().getString(R.string.defi_couleurs_france), new String[]{"Bleu", "Rouge"}));
        questions.add(new Question("Canada", getResources().getString(R.string.defi_couleurs_canada), new String[]{"Rouge"}));
        questions.add(new Question("Québec", getResources().getString(R.string.defi_couleurs_quebec), new String[]{"Bleu"}));
        questions.add(new Question("Ketchup", getResources().getString(R.string.defi_couleurs_ketchup), new String[]{"Rouge"}));
        questions.add(new Question("Moutarde", getResources().getString(R.string.defi_couleurs_moutarde), new String[]{"Jaune"}));
        questions.add(new Question("Bleu", getResources().getString(R.string.defi_couleurs_ciel), new String[]{"Bleu"}));

        return questions;
    }
}