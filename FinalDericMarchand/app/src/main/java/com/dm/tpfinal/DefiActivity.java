package com.dm.tpfinal;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class DefiActivity extends AppCompatActivity {

    // Super-classe de laquelle héritent les deux Défis de l'application

    public void showDefiPresentation(Context context, String texte, String titre, int image) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.defi_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        TextView t = dialog.findViewById(R.id.txtDefiPresentation);
        ImageView img = dialog.findViewById(R.id.imgDefiPresentation);
        t.setText(texte);
        img.setImageResource(image);

        dialog.findViewById(R.id.txtBoutonSuivant).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showActiviteDialog(Context context, String texte, int image, ListeDefis listeDefis, Class destination) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.activite_physique_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        TextView t = dialog.findViewById(R.id.txtActivite);
        ImageView img = dialog.findViewById(R.id.imgActivite);
        t.setText(texte);
        img.setImageResource(image);

        dialog.findViewById(R.id.txtBoutonSuivant).setOnClickListener(v -> {
            Intent i = new Intent(context, destination);
            i.putExtra("listeDefis", listeDefis);
            startActivity(i);
            finish();
            dialog.dismiss();
        });

        dialog.show();
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
            startActivity(getIntent());
        });

        return dialog;
    }

    // Fonctions d'animations qui seront utilisées de manière récurrente dans les deux activités
    public void fadeIn(View v, long duree) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, View.ALPHA, 1);
        fadeIn.setDuration(duree);

        fadeIn.start();
    }

    public void fadeOut(View v, long duree) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, View.ALPHA, 0);
        fadeIn.setDuration(duree);

        fadeIn.start();
    }

    public TimeInterpolator setTimeInterpolator() {
        TimeInterpolator timeInterpolator = new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                double raw = Math.sin(3f * input * 2 * Math.PI);
                return (float)(raw * Math.exp(-input * 2f));
            }
        };

        return timeInterpolator;
    }

    public void toastPersonnalise(String msg, boolean top) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        View view = toast.getView();

        // On récupère l'arrière-plan du Toast pour en changer la couleur
        view.getBackground().setColorFilter(getColor(R.color.blue), PorterDuff.Mode.SRC_IN);

        // On récupère le TextView du toast pour le modifier à notre goût
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getColor(R.color.white));
        text.setTypeface(Typeface.DEFAULT_BOLD);

        if (top) {
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, -100);
            view.getBackground().setColorFilter(getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
        }

        toast.show();
    }
}
