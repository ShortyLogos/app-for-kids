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

public class DefiCouleurActivity extends AppCompatActivity {

    ConstraintLayout zoneChevalet;
    LinearLayout centreChevalet;
    LinearLayout zoneChoisieCouleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_couleur);

        centreChevalet = findViewById(R.id.centreChevalet);
        zoneChoisieCouleur = findViewById(R.id.zoneChoisieCouleur);
    }

    private class Ecouteur implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    break;

                // ici vérifier avec la question si la couleur est la bonne

            }

            return true;
        }
    }

    private class CouleurView extends androidx.appcompat.widget.AppCompatImageView {
        private Couleur couleur;

        public CouleurView(@NonNull Context context, Couleur couleur) {
            super(context);

            this.couleur = couleur;

            switch (couleur.getNom()) {
                case "rouge":
                    this.setBackgroundResource(R.drawable.ic_droplet_rouge);
                    break;
                case "jaune":
                    this.setBackgroundResource(R.drawable.ic_droplet_jaune);
                    break;
                case "bleu":
                    this.setBackgroundResource(R.drawable.ic_droplet_bleu);
                    break;
                case "orange":
                    this.setBackgroundResource(R.drawable.ic_droplet_orange);
                    break;
                case "vert":
                    this.setBackgroundResource(R.drawable.ic_droplet_vert);
                    break;
                case "violet":
                    this.setBackgroundResource(R.drawable.ic_droplet_violet);
                    break;
            }
        }
    }
}