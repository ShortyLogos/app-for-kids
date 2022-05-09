 package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class DefiPersosActivity extends AppCompatActivity implements DefiActivity {

    TextView textePersoQuestion;
    TextView nbVie;
    ImageView imgEncadree;
    LinearLayout zoneBtnReponse;
    Defi defiPersos;
    int vie = 3;
    boolean questionCompletee;
    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_persos);

        textePersoQuestion = findViewById(R.id.textePersoQuestion);
        nbVie = findViewById(R.id.nbVie);
        imgEncadree = findViewById(R.id.imgEncadree);
        zoneBtnReponse = findViewById(R.id.zoneBtnReponse);

        defiPersos = new Defi(getResources().getString(R.string.defi_persos_titre),
                getResources().getString(R.string.defi_persos_description),
                false,
                getResources().getString(R.string.activite_physique_course),
                genererQuestions());

        nouvelleQuestion();

        for (int i = 0; i < zoneBtnReponse.getChildCount(); i++) {
            Button b = (Button)zoneBtnReponse.getChildAt(i);
            b.setOnClickListener(v -> {
                proposeReponse(b.getText().toString());
            });
        }
    }

    private void proposeReponse(String reponse) {
        if (reponse.equals(defiPersos.getQuestionCourante().getReponse()[0])) {
            defiPersos.getQuestions().remove(defiPersos.getQuestionCourante());
            if (defiPersos.getQuestions().size() > 0) {
                questionCompletee = true;
                nouvelleQuestion();
            }
            else {
                Utils.showActiviteDialog(DefiPersosActivity.this, defiPersos.getActivitePhysique(), R.drawable.course);
//                                Intent i = new Intent(DefiCouleurActivity.this, DefiPersosActivity.class);
//                                finish();
//                                startActivity(i);
            }
        }
        else {
            vie--;
            nbVie.setText(String.valueOf(vie));
        }
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    public void nouvelleQuestion() {
        defiPersos.setQuestionCourante(defiPersos.questionAleatoire());
        textePersoQuestion.setText(defiPersos.getQuestionCourante().getFormulation());
        imgEncadree.setImageResource(defiPersos.getQuestionCourante().getImageAssociee());

        defiPersos.getQuestionCourante().melangeReponse();

        for (int i = 0; i < zoneBtnReponse.getChildCount(); i++) {
            Button b = (Button)zoneBtnReponse.getChildAt(i);
            b.setText(defiPersos.getQuestionCourante().getChoixMultiples().get(i));
        }
    }

    public Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();

        Question q1 = new Question("Harry Potter", getResources().getString(R.string.defi_persos_harry_potter), new String[]{"Harry Potter"});
        q1.setChoixMultiples(Question.genererChoixMultiple(new String[] {"Frafry Putter", "Harry Potter", "Arnold Schwarput", "Thomas Halloween"}));
        q1.setImageAssociee(R.drawable.harry_potter);
        questions.add(q1);

        Question q2 = new Question("Les Simpsons", getResources().getString(R.string.defi_persos_simpsons), new String[]{"Les Simpsons"});
        q2.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Les McDuff", "Les Grapsons", "Les Simpsons", "Les Sérieux"}));
        q2.setImageAssociee(R.drawable.simpsons);
        questions.add(q2);

        Question q3 = new Question("Spider-Man", getResources().getString(R.string.defi_persos_spider_man), new String[]{"Spider-Man"});
        q3.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Venom", "Super-Man", "Cat-Woman", "Spider-Man"}));
        q3.setImageAssociee(R.drawable.spider_man);
        questions.add(q3);

        Question q4 = new Question("Pinocchio", getResources().getString(R.string.defi_persos_pinocchio), new String[]{"Pinocchio"});
        q4.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Espresso", "Pinocchio", "Cappucino", "Frodon"}));
        q4.setImageAssociee(R.drawable.pinocchio);
        questions.add(q4);

        Question q5 = new Question("Pikachu", getResources().getString(R.string.defi_persos_pikachu), new String[]{"Pikachu"});
        q5.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Bulbasaure", "Garfield", "Pikachu", "Ampoule"}));
        q5.setImageAssociee(R.drawable.pikachu);
        questions.add(q5);

        Question q6 = new Question("Elsa", getResources().getString(R.string.defi_persos_elsa), new String[]{"Elsa"});
        q6.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Arielle", "Elsa", "Doris", "Vanille"}));
        q6.setImageAssociee(R.drawable.elsa);
        questions.add(q6);

        Question q7 = new Question("Bugs Bunny", getResources().getString(R.string.defi_persos_bugs_bunny), new String[]{"Bugs Bunny"});
        q7.setChoixMultiples(Question.genererChoixMultiple(new String[]{"Ratatouille", "Jo Rabbit", "Bob l'Éponge", "Bugs Bunny"}));
        q7.setImageAssociee(R.drawable.bugs_bunny);
        questions.add(q7);

        return questions;
    }
}