package com.dm.tpfinal;

import android.content.Context;

import java.io.Serializable;
import java.util.Vector;

public class ListeDefis implements Serializable {
    private Vector<Defi> listeDefis;
    private static ListeDefis instance;
    private Context context;

    public static ListeDefis getInstance(Context context) {
        if (instance == null) {
            instance = new ListeDefis(context.getApplicationContext());
        }
        return instance;
    }

    private ListeDefis(Context context) {
        this.context = context;
        this.listeDefis = new Vector<Defi>();
    }
}
