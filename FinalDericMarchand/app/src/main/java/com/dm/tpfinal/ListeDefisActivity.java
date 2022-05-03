package com.dm.tpfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Hashtable;
import java.util.Vector;

public class ListeDefisActivity extends AppCompatActivity {

    ListView listeItemsDefis;
    Vector<Hashtable<String, Object>> infoDefis;
    ListeDefis listeDefis;
    Button codeQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeDefis = ListeDefis.getInstance(this);
        infoDefis = listeDefis.getInfoDefis();
        codeQR = findViewById(R.id.codeQR);

        listeItemsDefis = findViewById(R.id.listeItemsDefis);
        int[] conteneurs = {R.id.iconeDefi, R.id.nomDefi, R.id.reussi};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                infoDefis,
                R.layout.itemdefi,
                new String[]{"image", "nom", "reussi"},
                conteneurs);

        listeItemsDefis.setAdapter(simpleAdapter);

        codeQR.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setPrompt("Scan a barcode or QR Code");
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.initiateScan();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Annulé", Toast.LENGTH_SHORT).show();
            } else {
                if (intentResult.getContents().equals("Les couleurs")) {
                    startActivity(new Intent(this, DefiCouleurActivity.class));
                }
                else if (intentResult.getContents().equals("Les personnages célèbres")) {
                    startActivity(new Intent(this, DefiPersosActivity.class));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}