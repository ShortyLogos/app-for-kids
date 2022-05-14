package com.dm.tpfinal;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

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
    private transient Context context;

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

    public void serialiserListeDefis(Context context) throws IOException {
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
        return genererInfosDefis();
    }

    public boolean verifierDefiExiste(Defi defi) {
        boolean resultat = false;

        for (Defi d : listeDefis) {
            if (d.getNom().equals(defi.getNom())) {
                resultat = true;
                break;
            }
        }

        return resultat;
    }

    private Vector<Hashtable<String, Object>> genererInfosDefis() {
        Vector<Hashtable<String, Object>> defis = new Vector();

        Hashtable<String, Object> h = new Hashtable();
        h.put("image", R.drawable.color_palette);
        h.put("nom", "Couleurs Mêlées");
        h.put("reussi", setIconeReussite("Couleurs Mêlées"));
        defis.add(h);

        h = new Hashtable();
        h.put("image", R.drawable.boy);
        h.put("nom", "Devine Mon Nom");
        h.put("reussi", setIconeReussite("Devine Mon Nom"));
        defis.add(h);

        return defis;
    }

    private int setIconeReussite(String nomDefi) {
        int resultat = R.drawable.cross;;
        for (Defi defi : listeDefis) {
            if (defi.getNom().equals(nomDefi) && defi.isReussi()) {
                resultat = R.drawable.check;
            }
        }
        return resultat;
    }
}
