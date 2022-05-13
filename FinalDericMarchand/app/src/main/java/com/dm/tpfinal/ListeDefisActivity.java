package com.dm.tpfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class ListeDefisActivity extends DefiActivity {

    ListView listeItemsDefis;
    Vector<Hashtable<String, Object>> infoDefis;
    ListeDefis listeDefis;
    Button codeQR;
    ImageView logoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeDefis = ListeDefis.getInstance(this);
        infoDefis = listeDefis.getInfoDefis();
        codeQR = findViewById(R.id.codeQR);
        logoApp = findViewById(R.id.logoApp);
        ListView listeItemsDefis = findViewById(R.id.listeItemsDefis);
        ImageView appareilPhoto = findViewById(R.id.appareilPhoto);
        TextView auteur = findViewById(R.id.auteur);

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

        codeQR.setAlpha(0);
        listeItemsDefis.setAlpha(0);
        logoApp.setAlpha((float) 0);
        appareilPhoto.setAlpha((float) 0);
        auteur.setAlpha(0);

        ArrayList<View> elementsFadeIn = new ArrayList<View>();
        elementsFadeIn.add(listeItemsDefis);
        elementsFadeIn.add(appareilPhoto);
        elementsFadeIn.add(codeQR);
        elementsFadeIn.add(auteur);

        // Animation du logo
        fadeIn(logoApp, 1000);
        AnimatorSet rotationLogo = new AnimatorSet();
        rotationLogo.playSequentially(
                ObjectAnimator.ofFloat(logoApp, View.ROTATION, 380),
                ObjectAnimator.ofFloat(logoApp, View.ROTATION, 320),
                ObjectAnimator.ofFloat(logoApp, View.ROTATION, 360)
        );
        rotationLogo.setDuration(1100);
        rotationLogo.start();

        // Fade-in du reste des éléments
        fadeInElements(elementsFadeIn, 3000, 500, 500);

        Intent i = new Intent(this, DefiPersosActivity.class);
        startActivity(i);
    }

    private void fadeInElements(ArrayList<View> ensembleElement, long delaiInitial, long delai, long dureeAnimation) {

        for (View v : ensembleElement) {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            fadeIn(v, dureeAnimation);
                        }
                    }, delaiInitial);

            delaiInitial += delai;
        }
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