package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Vector;

public class DefiPersosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_persos);


    }

    private Vector<Question> genererQuestions() {
        Vector<Question> questions = new Vector<>();

        Question q1 = new Question("Harry Potter", getResources().getString(R.string.defi_persos_harry_potter), new String[]{"Harry Potter"});
        q1.setChoixMultiples(new String[]{"Frafry Putter", "Harry Potter", "Arnold Schwarput", "Thomas Halloween"});
        questions.add(q1);

        Question q2 = new Question("Les Simpsons", getResources().getString(R.string.defi_persos_simpsons), new String[]{"Les Simpsons"});
        q.setChoixMultiples(new String[]{"Les McDuff", "Les Grapsons", "Les Simpsons", "Les Sérieux"});
        questions.add(q2);

        Question q3 = new Question("Spider-Man", getResources().getString(R.string.defi_persos_spider_man), new String[]{"Spider-Man"});
        q3.setChoixMultiples(new String[]{"Venom", "Super-Man", "Cat-Woman", "Spider-Man"});
        questions.add(q3);

        Question q4 = new Question("Pinocchio", getResources().getString(R.string.defi_persos_pinocchio), new String[]{"Pinocchio"});
        q4.setChoixMultiples(new String[]{"Espresso", "Pinocchio", "Cappucino", "Frodon"});
        questions.add(q4);

        Question q5 = new Question("Pikachu", getResources().getString(R.string.defi_persos_pikachu), new String[]{"Pikachu"});
        q5.setChoixMultiples(new String[]{"Bulbasaure", "Garfield", "Pikachu", "Ampoule"});
        questions.add(q5);

        Question q6 = new Question("Elsa", getResources().getString(R.string.defi_persos_elsa), new String[]{"Elsa"});
        q6.setChoixMultiples(new String[]{"Arielle", "Elsa", "Doris", "Vanille"});
        questions.add(q6);

        Question q7 = new Question("Bugs Bunny", getResources().getString(R.string.defi_persos_elsa), new String[]{"Bugs Bunny"});
        q7.setChoixMultiples(new String[]{"Ratatouille", "Jo Rabbit", "Bob l'Éponge", "Bugs Bunny"});
        questions.add(q7);

        return questions;
    }
}