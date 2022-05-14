package com.dm.tpfinal;

import java.io.Serializable;

public class Couleur {
    private String nom;

    public Couleur(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static Couleur[] couleursPrimaires() {
        return new Couleur[]{new Couleur("Rouge"), new Couleur("Jaune"), new Couleur("Bleu")};
    }
}
