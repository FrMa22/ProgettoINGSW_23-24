package com.example.progettoingsw.model;

import android.graphics.Bitmap;

public class AstaIngleseItem {
    private int id;
    private String nome;
    private String descrizione;
    private Bitmap immagine;
    private String baseAsta;
    private String dataDiScadenza;
    private String condizione;
    private String intervalloTempoOfferte;
    private String rialzoMin;
    private String prezzoAttuale;

    public AstaIngleseItem(int id, String nome, String descrizione, Bitmap immagine, String baseAsta, String intervalloTempoOfferte, String rialzoMin, String prezzoAttuale, String dataDiScadenza, String condizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.baseAsta = baseAsta;
        this.intervalloTempoOfferte = intervalloTempoOfferte;
        this.rialzoMin = rialzoMin;
        this.prezzoAttuale = prezzoAttuale;
        this.dataDiScadenza = dataDiScadenza;
        this.condizione = condizione;
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

    public String getBaseAsta() {
        return baseAsta;
    }

    public void setBaseAsta(String baseAsta) {
        this.baseAsta = baseAsta;
    }

    public String getDataDiScadenza() {
        return dataDiScadenza;
    }

    public void setDataDiScadenza(String dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    public String getIntervalloTempoOfferte() {
        return intervalloTempoOfferte;
    }

    public void setIntervalloTempoOfferte(String intervalloTempoOfferte) {
        this.intervalloTempoOfferte = intervalloTempoOfferte;
    }

    public String getRialzoMin() {
        return rialzoMin;
    }

    public void setRialzoMin(String rialzoMin) {
        this.rialzoMin = rialzoMin;
    }

    public String getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(String prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }
}
