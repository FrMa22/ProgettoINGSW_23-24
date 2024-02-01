package com.example.progettoingsw.gestori_gui;

public class ElementoListaCategorie {
    private int immagine;
    private String testo;
    public ElementoListaCategorie(int immagine, String testo) {
        this.immagine = immagine;
        this.testo = testo;
    }

    public int getImmagine() {
        return immagine;
    }

    public String getTesto() {
        return testo;
    }
}
