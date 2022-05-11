package com.dm.tpfinal;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DefiActivity extends AppCompatActivity {

    public void showDefiPresentation(Context context, String texte, String titre, int image) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.presentation_defi_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        TextView t = dialog.findViewById(R.id.txtDefiPresentation);
        ImageView img = dialog.findViewById(R.id.imgDefiPresentation);
        t.setText(texte);
        img.setImageResource(image);

        dialog.findViewById(R.id.txtContinuer).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();

//        AlertDialog.Builder b = new AlertDialog.Builder(this);
//
//        b.setMessage(texte);
//        b.setTitle(titre);
//
//        AlertDialog dialog = b.create();
//        dialog.show();
    }

    public void showActiviteDialog(Context context, String texte, int image) {
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
