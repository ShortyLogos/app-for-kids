package com.dm.tpfinal;

public class Defi {
    private boolean reussi;
    private String description;
    private String activitePhysique;

    public Defi(boolean reussi, String description, String activitePhysique) {
        this.reussi = reussi;
        this.description = description;
        this.activitePhysique = activitePhysique;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivitePhysique() {
        return activitePhysique;
    }

    public void setActivitePhysique(String activitePhysique) {
        this.activitePhysique = activitePhysique;
    }
}
