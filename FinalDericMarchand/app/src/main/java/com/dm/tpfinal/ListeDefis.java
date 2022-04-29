package com.dm.tpfinal;

import android.content.Context;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class ListeDefis implements Serializable {
    private Vector<Defi> listeDefis;
    private Vector<Hashtable<String, Object>> infoDefis;
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
        this.infoDefis = genererInfosDefis();
    }

    public Vector<Defi> getListeDefis() {
        return listeDefis;
    }

    public Vector<Hashtable<String, Object>> getInfoDefis() {
        return infoDefis;
    }

    private Vector<Hashtable<String, Object>> genererInfosDefis() {
        Vector<Hashtable<String, Object>> defis = new Vector();

        Hashtable<String, Object> h = new Hashtable();
        h.put("image", R.drawable.color_palette);
        h.put("nom", context.getString(R.string.couleur_name));
        h.put("reussi", R.drawable.cross);
        defis.add(h);

        h = new Hashtable();
        h.put("image", R.drawable.boy);
        h.put("nom", context.getString(R.string.persos_name));
        h.put("reussi", R.drawable.cross);
        defis.add(h);

        return defis;
    }
}
