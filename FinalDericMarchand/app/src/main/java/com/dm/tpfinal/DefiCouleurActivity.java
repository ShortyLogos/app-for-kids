package com.dm.tpfinal;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class DefiCouleurActivity extends DefiActivity implements DefiActivityInterface {

    ConstraintLayout zoneChevalet;
    TextView zoneQuestionCouleur;
    LinearLayout centreChevalet;
    LinearLayout zoneChoixCouleur;
    LinearLayout zoneChoisieCouleur;
    Defi defiCouleur;
    ListeDefis listeDefis;
    boolean questionCompletee;
    Ecouteur ec;

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

        listeDefis = (ListeDefis)getIntent().getSerializableExtra("listeDefis");
        if (!listeDefis.verifierDefiExiste(defiCouleur)) {
            listeDefis.getListeDefis().add(defiCouleur);
        }

        nouvelleQuestion();

        ec = new Ecouteur();

        // On remplit la zone de couleurs possibles de CouleurView correspondant aux couleurs primaires
        remplirCouleurs();

        // Inscription du chevalet à l'écouteur
        // Celle des objets CouleurView se fait dans la méthode remplirCouleurs()
        centreChevalet.setOnDragListener(ec);

        showDefiPresentation(this, defiCouleur.getDescription(), defiCouleur.getNom(), R.drawable.brush);
    }

    private void verifReponse() {
        if (zoneChoisieCouleur.getChildCount() == defiCouleur.getQuestionCourante().getReponse().length) {
            defiCouleur.getQuestions().remove(defiCouleur.getQuestionCourante());
            if (defiCouleur.getQuestions().size() > 0) {
                questionCompletee = true;
            }
            else {
                defiCouleur.setReussi(true);
                showActiviteDialog(DefiCouleurActivity.this, defiCouleur.getActivitePhysique(), R.drawable.reculons, listeDefis, ListeDefisActivity.class);
            }
        }

        if (questionCompletee) {
            questionCompletee = false;

            toastPersonnalise(getResources().getString(R.string.bonne_reponse), false);

            nouvelleQuestion();
            remplirCouleurs();
        }
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
                        centreChevalet.addView(couleur);

                        fadeOut(couleur, 500);

                        // Ici, on enchâsse des Handler afin de produire dans un premier temps des
                        // animations et ensuite seulement convoquer la logique du jeu
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        centreChevalet.removeView(couleur);
                                        zoneChoisieCouleur.addView(couleur);
                                        fadeIn(couleur, 500);

                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        verifReponse();
                                                    }
                                                }, 550);
                                    }
                                }, 550);
                    }
                    else {
                        // Effet de "shake" sur le chevalet en cas de mauvaise réponse
                        zoneChevalet.animate()
                                .xBy(-100)
                                .setInterpolator(setTimeInterpolator()) // Interpolateur spécifique à l'effet
                                .setDuration(550)
                                .start();
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

    // Méthode abstraite de l'interface DefiActivityInterface
    public void nouvelleQuestion() {
        defiCouleur.setQuestionCourante(defiCouleur.questionAleatoire());
        zoneQuestionCouleur.setAlpha(0);
        zoneQuestionCouleur.setText(defiCouleur.getQuestionCourante().getFormulation());

        fadeIn(zoneQuestionCouleur, 500);
    }

    // Méthode abstraite de l'interface DefiActivityInterface
    public Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();

        String rouge = getResources().getString(R.string.rouge);
        String bleu = getResources().getString(R.string.bleu);
        String jaune = getResources().getString(R.string.jaune);

        Question q = null;
        questions.add(new Question(getResources().getString(R.string.vert), getResources().getString(R.string.defi_couleurs_vert), new String[]{bleu, jaune}));
        questions.add(new Question(getResources().getString(R.string.orange), getResources().getString(R.string.defi_couleurs_orange), new String[]{rouge, jaune}));
        questions.add(new Question(getResources().getString(R.string.violet), getResources().getString(R.string.defi_couleurs_violet), new String[]{bleu, rouge}));
        questions.add(new Question(getResources().getString(R.string.france), getResources().getString(R.string.defi_couleurs_france), new String[]{bleu, rouge}));
        questions.add(new Question(getResources().getString(R.string.canada), getResources().getString(R.string.defi_couleurs_canada), new String[]{rouge}));
        questions.add(new Question(getResources().getString(R.string.quebec), getResources().getString(R.string.defi_couleurs_quebec), new String[]{bleu}));
        questions.add(new Question(getResources().getString(R.string.ketchup), getResources().getString(R.string.defi_couleurs_ketchup), new String[]{rouge}));
        questions.add(new Question(getResources().getString(R.string.moutarde), getResources().getString(R.string.defi_couleurs_moutarde), new String[]{jaune}));
        questions.add(new Question(getResources().getString(R.string.bleu), getResources().getString(R.string.defi_couleurs_ciel), new String[]{bleu}));

        return questions;
    }
}