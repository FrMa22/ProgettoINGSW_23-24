package com.example.progettoingsw.model;

import android.graphics.Bitmap;

public class AstaInversaItem {
    private int id;
    private String nome;
    private String descrizione;
    private Bitmap immagine;
    private String prezzoMax;
    private String dataDiScadenza;
    private String condizione;
    private String prezzoAttuale;
    private String emailAcquirente;

    public AstaInversaItem(int id, String nome, String descrizione, Bitmap immagine, String prezzoMax, String dataDiScadenza, String condizione, String prezzoAttuale, String emailAcquirente) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.prezzoMax = prezzoMax;
        this.dataDiScadenza = dataDiScadenza;
        this.condizione = condizione;
        this.prezzoAttuale = prezzoAttuale;
        this.emailAcquirente = emailAcquirente;
    }

    public String getPrezzoAttuale(){
        return prezzoAttuale;
    }
    public void setPrezzoAttuale(String prezzo){
        this.prezzoAttuale = prezzo;
    }
    public String getEmailAcquirente(){
        return emailAcquirente;
    }
    public void setEmailAcquirente(String emailAcquirente){
        this.emailAcquirente = emailAcquirente;
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

    public String getPrezzoMax() {
        return prezzoMax;
    }

    public void setPrezzoMax(String prezzoMax) {
        this.prezzoMax = prezzoMax;
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
}

