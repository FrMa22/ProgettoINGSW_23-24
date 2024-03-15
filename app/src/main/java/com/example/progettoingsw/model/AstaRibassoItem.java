package com.example.progettoingsw.model;

import android.graphics.Bitmap;

public class AstaRibassoItem {
    private int id;
    private String nome;
    private String descrizione;
    private Bitmap immagine;
    private String prezzoBase;
    private String condizione;
    private String intervalloDecrementale;
    private String decrementoAutomatico;
    private String prezzoAttuale;
    private String prezzoMin;
    private String emailVenditore;

    public AstaRibassoItem(int id, String nome, String descrizione, Bitmap immagine, String prezzoBase, String intervalloDecrementale, String decrementoAutomatico, String prezzoMin, String prezzoAttuale, String condizione, String emailVenditore) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.prezzoBase = prezzoBase;
        this.intervalloDecrementale = intervalloDecrementale;
        this.decrementoAutomatico = decrementoAutomatico;
        this.prezzoMin = prezzoMin;
        this.prezzoAttuale = prezzoAttuale;
        this.condizione = condizione;
        this.emailVenditore = emailVenditore;
    }
    public String getEmailVenditore() {
        return emailVenditore;
    }

    public void setEmailVenditore(String emailVenditore) {
        this.emailVenditore = emailVenditore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Bitmap getImmagine() {
        return immagine;
    }

    public void setImmagine(Bitmap immagine) {
        this.immagine = immagine;
    }

    public String getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(String prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    public String getIntervalloDecrementale() {
        return intervalloDecrementale;
    }

    public void setIntervalloDecrementale(String intervalloDecrementale) {
        this.intervalloDecrementale = intervalloDecrementale;
    }

    public String getDecrementoAutomatico() {
        return decrementoAutomatico;
    }

    public void setDecrementoAutomatico(String decrementoAutomatico) {
        this.decrementoAutomatico = decrementoAutomatico;
    }

    public String getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(String prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }

    public String getPrezzoMin() {
        return prezzoMin;
    }

    public void setPrezzoMin(String prezzoMin) {
        this.prezzoMin = prezzoMin;
    }
}
