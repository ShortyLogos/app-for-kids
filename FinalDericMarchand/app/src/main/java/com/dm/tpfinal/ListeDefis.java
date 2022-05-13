package com.dm.tpfinal;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void serialiserListeDefis() throws IOException {
        ObjectOutputStream oos = null;

        FileOutputStream fos = context.openFileOutput("listedefis.ser", Context.MODE_PRIVATE);

        oos = new ObjectOutputStream(fos);
        oos.writeObject(listeDefis);
        oos.close();
    }

    public void recupererFichierSerialisation() throws Exception {
        ObjectInputStream ois = null;
        Vector<Defi> resultat = null;

        FileInputStream fis = context.openFileInput("listedefis.ser");
        ois = new ObjectInputStream(fis);
        resultat = (Vector<Defi>)ois.readObject();
        listeDefis = resultat;
        ois.close();
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
