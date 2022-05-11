package com.dm.tpfinal;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Vector;

// Classe d'utilitaires utilisés au sein du projet
public class Utils {

    public static int conversionDpPx(Context context, float dp) {
        float px = dp * context.getResources().getDisplayMetrics().density;
        return (int)px;
    }

//    public void jouerAnimation(Vector<ObjectAnimator> suiteAnimation) {
//        suiteAnimation.get(noAnimation).start();
//        noAnimation++;
//        if (noAnimation == ensembleAnimation.size()) { noAnimation = 0; };
//    }
}
