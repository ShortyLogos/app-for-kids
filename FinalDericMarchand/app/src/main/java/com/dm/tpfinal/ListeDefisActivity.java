package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Hashtable;
import java.util.Vector;

public class ListeDefisActivity extends AppCompatActivity {

    ListView listeItemsDefis;
    Vector<Hashtable<String, Object>> infoDefis;
    ListeDefis listeDefis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeDefis = ListeDefis.getInstance(this);
        infoDefis = listeDefis.getInfoDefis();

        listeItemsDefis = findViewById(R.id.listeItemsDefis);
        int[] conteneurs = {R.id.iconeDefi, R.id.nomDefi, R.id.reussi};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                infoDefis,
                R.layout.itemdefi,
                new String[]{"image", "nom", "reussi"},
                conteneurs);

        listeItemsDefis.setAdapter(simpleAdapter);
    }
}