package com.dm.tpfinal;

public class Defi {
    private String nom;
    private boolean reussi;
    private String description;
    private String activitePhysique;

    public Defi(String nom, String description, boolean reussi, String activitePhysique) {
        this.nom = nom;
        this.reussi = reussi;
        this.description = description;
        this.activitePhysique = activitePhysique;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public String getActivitePhysique() {
        return activitePhysique;
    }

    public void setActivitePhysique(String activitePhysique) {
        this.activitePhysique = activitePhysique;
    }
}
