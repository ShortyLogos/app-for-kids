package com.dm.tpfinal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class DefiPersosActivity extends DefiActivity implements DefiActivityInterface {

    TextView textePersoQuestion;
    TextView nbVie;
    ImageView imgEncadree;
    ImageView iconeVie;
    LinearLayout zoneBtnReponse;
    FrameLayout cadrePerso;
    Defi defiPersos;
    ListeDefis listeDefis;
    int vie = 3;
    boolean questionCompletee;
    Handler handlerAnimCoeur;
    AnimatorSet couleurNbVie;
    Dialog recommencerDefi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_persos);

        textePersoQuestion = findViewById(R.id.textePersoQuestion);
        nbVie = findViewById(R.id.nbVie);
        imgEncadree = findViewById(R.id.imgEncadree);
        zoneBtnReponse = findViewById(R.id.zoneBtnReponse);
        cadrePerso = findViewById(R.id.cadrePerso);
        iconeVie = findViewById(R.id.iconeVie);

        defiPersos = new Defi(getResources().getString(R.string.defi_persos_titre),
                getResources().getString(R.string.defi_persos_description),
                false,
                getResources().getString(R.string.activite_physique_course),
                genererQuestions());

        listeDefis = (ListeDefis)getIntent().getSerializableExtra("listeDefis");
        if (!listeDefis.verifierDefiExiste(defiPersos)) {
            listeDefis.getListeDefis().add(defiPersos);
        }

        for (int i = 0; i < zoneBtnReponse.getChildCount(); i++) {
            Button b = (Button)zoneBtnReponse.getChildAt(i);
            b.setOnClickListener(v -> {
                if (!questionCompletee) {
                    proposeReponse(b.getText().toString());
                }
            });
        }

        // Préparation des animations de l'Activity
        handlerAnimCoeur = new Handler();
        Thread threadAnimCoeur = new animationCoeur();
        handlerAnimCoeur.post(threadAnimCoeur);

        couleurNbVie = new AnimatorSet();
        couleurNbVie.playSequentially(genereNbVieAnimation());

        showDefiPresentation(this, defiPersos.getDescription(), defiPersos.getNom(), R.drawable.boy);

        nouvelleQuestion();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recommencerDefi != null) {
            recommencerDefi.dismiss();
        }
    }

    private ArrayList<Animator> genereNbVieAnimation() {
        // Animation qui génère un léger flash rouge sur le TextView du nombre de Vie
        ObjectAnimator vieCouleurRouge = ObjectAnimator.ofInt(nbVie, "textColor",
                ResourcesCompat.getColor(getResources(), R.color.yellow, null),
                ResourcesCompat.getColor(getResources(), R.color.red, null));
        vieCouleurRouge.setEvaluator(new ArgbEvaluator());
        vieCouleurRouge.setDuration(150);

        ObjectAnimator vieCouleurJaune = ObjectAnimator.ofInt(nbVie, "textColor",
                ResourcesCompat.getColor(getResources(), R.color.red, null),
                ResourcesCompat.getColor(getResources(), R.color.yellow, null));
        vieCouleurJaune.setEvaluator(new ArgbEvaluator());
        vieCouleurJaune.setDuration(1200);

        ArrayList<Animator> animations = new ArrayList<Animator>();
        animations.add(vieCouleurRouge);
        animations.add(vieCouleurJaune);

        // On retourne un ArrayList d'objets Animator que l'on passera à un AnimatorSet
        return animations;
    }

    private void proposeReponse(String reponse) {
        if (reponse.equals(defiPersos.getQuestionCourante().getReponse()[0])) {
            toastPersonnalise(getResources().getString(R.string.bonne_reponse), true);
            for (int i = 0; i < zoneBtnReponse.getChildCount(); i++) {
                Button b = (Button)zoneBtnReponse.getChildAt(i);
                fadeOut(b, 800);
            }

            defiPersos.getQuestions().remove(defiPersos.getQuestionCourante());
            if (defiPersos.getQuestions().size() > 0) {
                questionCompletee = true;

                // On instaure un délai entre chaque nouvelle question afin
                // d'installer un rythme convenable au jeu et permettre des animations plus développées
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                nouvelleQuestion();
                            }
                        }, 1200);
            }
            else {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                defiPersos.setReussi(true);
                                showActiviteDialog(DefiPersosActivity.this, defiPersos.getActivitePhysique(), R.drawable.course, listeDefis, ListeDefisActivity.class);
//                                Intent i = new Intent(DefiCouleurActivity.this, DefiPersosActivity.class);
//                                finish();
//                                startActivity(i);(Context context, String texte, int image, Defi defiActuel, Class destination, int codeRetour)
                            }
                        }, 2000);
            }
        }
        else {
            vie--;
            couleurNbVie.start();
            nbVie.setText(String.valueOf(vie));

            // Effet de "shake" sur le chevalet en cas de mauvaise réponse
            cadrePerso.animate()
                    .xBy(-100)
                    .setInterpolator(setTimeInterpolator()) // Interpolateur spécifique à l'effet
                    .setDuration(550)
                    .start();

            if (vie < 1) {
                recommencerDefi = showRecommencerDefi(DefiPersosActivity.this, getResources().getString(R.string.recommencer_texte), R.drawable.sad);
                recommencerDefi.show();
            }
        }
    }

    private class animationCoeur extends Thread {
        // Animation constante de l'image du coeur aux côtées du TextView du nombre de vies
        @Override
        public void run() {
            Animation anim = new ScaleAnimation(
                    0.85f, 1f, // Start and end values for the X axis scaling
                    0.85f, 1f, // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
            anim.setFillAfter(true); // Needed to keep the result of the animation
            anim.setDuration(700);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            iconeVie.startAnimation(anim);
        }
    }

    // Méthode abstraite de l'interface DefiActivityInterface
    public void nouvelleQuestion() {
        questionCompletee = false;
        defiPersos.setQuestionCourante(defiPersos.questionAleatoire());

        textePersoQuestion.setText(defiPersos.getQuestionCourante().getFormulation());
        textePersoQuestion.setAlpha(0);

        // On associe la bonne image à la bonne question
        imgEncadree.setImageResource(defiPersos.getQuestionCourante().getImageAssociee());

        imageAnimation();
        boutonsAnimation();

        // Animation fade-in de l'indice sous l'image, fonction héritée de la superclasse DefiActivity
        fadeIn(textePersoQuestion, 1500);
    }

    // Fonction qui génère l'animation de l'image (effet de grossissement + rebondissement)
     public void imageAnimation() {
         imgEncadree.setScaleX(0.01f);
         imgEncadree.setScaleY(0.01f);

         ObjectAnimator imageScaleUpX = ObjectAnimator.ofFloat(imgEncadree, View.SCALE_X, 1f);
         ObjectAnimator imageScaleUpY = ObjectAnimator.ofFloat(imgEncadree, View.SCALE_Y, 1f);

         AnimatorSet animationsImage = new AnimatorSet();
         animationsImage.playTogether(imageScaleUpX, imageScaleUpY);
         animationsImage.setDuration(1400);
         animationsImage.setInterpolator(new BounceInterpolator());
         animationsImage.start();

         defiPersos.getQuestionCourante().melangeReponse();
     }

    // Fonction qui génère l'animation des boutons de choix de réponse
    public void boutonsAnimation() {
        AnimatorSet animationsBoutons = new AnimatorSet();
        ArrayList<Animator> ensembleAnimationsBoutons = new ArrayList<Animator>();
        for (int i = 0; i < zoneBtnReponse.getChildCount(); i++) {
            Button b = (Button)zoneBtnReponse.getChildAt(i);
            b.setText(defiPersos.getQuestionCourante().getChoixMultiples().get(i));
            b.setAlpha(0);
            // Selon la position du bouton, on veut qu'il arrive de la gauche ou de la droite
            if (i % 2 == 0) {
                b.setTranslationX(1200);
            }
            else {
                b.setTranslationX(-1200);
            }

            ensembleAnimationsBoutons.add(ObjectAnimator.ofFloat(b, View.TRANSLATION_X, 0));
            ensembleAnimationsBoutons.add(ObjectAnimator.ofFloat(b, "alpha", 1));
        }
        animationsBoutons.playTogether(ensembleAnimationsBoutons);
        animationsBoutons.setDuration(1500);
        animationsBoutons.setInterpolator(new BounceInterpolator());

        animationsBoutons.start();
    }

    public Dialog showRecommencerDefi(Context context, String texte, int image) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.defi_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        TextView t = dialog.findViewById(R.id.txtDefiPresentation);
        ImageView img = dialog.findViewById(R.id.imgDefiPresentation);
        t.setText(texte);
        img.setImageResource(image);

        dialog.findViewById(R.id.txtBoutonSuivant).setOnClickListener(v -> {
            finish();
            startActivity(new Intent(DefiPersosActivity.this, ListeDefisActivity.class));
        });

        return dialog;
    }

    // Méthode abstraite de l'interface DefiActivityInterface
    public Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();

        Question q1 = new Question(getResources().getString(R.string.harry_potter), getResources().getString(R.string.defi_persos_harry_potter), new String[]{getResources().getString(R.string.harry_potter)});
        q1.setChoixMultiples(Question.genererChoixMultiple(new String[] {
                getResources().getString(R.string.frafry_putter),
                getResources().getString(R.string.harry_potter),
                getResources().getString(R.string.arnold_schwarput),
                getResources().getString(R.string.thomas_halloween)}));
        q1.setImageAssociee(R.drawable.harry_potter);
        questions.add(q1);

        Question q2 = new Question(getResources().getString(R.string.les_simpsons), getResources().getString(R.string.defi_persos_simpsons), new String[]{getResources().getString(R.string.les_simpsons)});
        q2.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.les_mcduff),
                getResources().getString(R.string.les_grapsons),
                getResources().getString(R.string.les_simpsons),
                getResources().getString(R.string.les_serieux)}));
        q2.setImageAssociee(R.drawable.simpsons);
        questions.add(q2);

        Question q3 = new Question(getResources().getString(R.string.spider_man), getResources().getString(R.string.defi_persos_spider_man), new String[]{getResources().getString(R.string.spider_man)});
        q3.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.venom),
                getResources().getString(R.string.super_man),
                getResources().getString(R.string.cat_woman),
                getResources().getString(R.string.spider_man)}));
        q3.setImageAssociee(R.drawable.spider_man);
        questions.add(q3);

        Question q4 = new Question(getResources().getString(R.string.pinocchio), getResources().getString(R.string.defi_persos_pinocchio), new String[]{getResources().getString(R.string.pinocchio)});
        q4.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.espresso),
                getResources().getString(R.string.pinocchio),
                getResources().getString(R.string.cappucino),
                getResources().getString(R.string.frodon)}));
        q4.setImageAssociee(R.drawable.pinocchio);
        questions.add(q4);

        Question q5 = new Question(getResources().getString(R.string.pikachu), getResources().getString(R.string.defi_persos_pikachu), new String[]{getResources().getString(R.string.pikachu)});
        q5.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.bulbasaure),
                getResources().getString(R.string.garfield),
                getResources().getString(R.string.pikachu),
                getResources().getString(R.string.ampoule)}));
        q5.setImageAssociee(R.drawable.pikachu);
        questions.add(q5);

        Question q6 = new Question(getResources().getString(R.string.elsa), getResources().getString(R.string.defi_persos_elsa), new String[]{getResources().getString(R.string.elsa)});
        q6.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.arielle),
                getResources().getString(R.string.elsa),
                getResources().getString(R.string.doris),
                getResources().getString(R.string.passe_partout)}));
        q6.setImageAssociee(R.drawable.elsa);
        questions.add(q6);

        Question q7 = new Question(getResources().getString(R.string.bugs_bunny), getResources().getString(R.string.defi_persos_bugs_bunny), new String[]{getResources().getString(R.string.bugs_bunny)});
        q7.setChoixMultiples(Question.genererChoixMultiple(new String[]{
                getResources().getString(R.string.ratatouille),
                getResources().getString(R.string.jo_rabbit),
                getResources().getString(R.string.bob_eponge),
                getResources().getString(R.string.bugs_bunny)}));
        q7.setImageAssociee(R.drawable.bugs_bunny);
        questions.add(q7);

        return questions;
    }
}