package com.dm.tpfinal;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

// Classe d'utilitaires utilisés au sein du projet
public class Utils {

    public static int conversionDpPx(Context context, float dp) {
        float px = dp * context.getResources().getDisplayMetrics().density;
        return (int)px;
    }

    public static void showActiviteDialog(Context context, String texte, int image) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.activite_physique_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        TextView t = dialog.findViewById(R.id.txtActivite);
        ImageView img = dialog.findViewById(R.id.imgActivite);
        t.setText(texte);
        img.setImageResource(image);

        dialog.findViewById(R.id.txtContinuer).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
