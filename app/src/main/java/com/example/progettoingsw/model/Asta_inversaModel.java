package com.example.progettoingsw.model;

import java.sql.Timestamp;

public class Asta_inversaModel {
    private Long id;
    private String nome;
    private String descrizione;
    private byte[] path_immagine;
    private float prezzoMax;
    private float prezzoAttuale;
    private String dataDiScadenza;
    private String condizione;
    private String id_acquirente;

    public Asta_inversaModel(){

    }
    public Asta_inversaModel(Long id, String nome, String descrizione, byte[] path_immagine, float prezzoMax, float prezzoAttuale, String dataDiScadenza, String condizione, String id_acquirente) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.path_immagine = path_immagine;
        this.prezzoMax = prezzoMax;
        this.prezzoAttuale = prezzoAttuale;
        this.dataDiScadenza = dataDiScadenza;
        this.condizione = condizione;
        this.id_acquirente = id_acquirente;
    }

    // Metodi getter e setter per id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodi getter e setter per nome, descrizione, path_immagine, prezzoMax, prezzoAttuale, dataDiScadenza, condizione, id_acquirente (omessi per brevità)
    // Getter e setter per nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e setter per descrizione
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // Getter e setter per path_immagine
    public byte[] getPath_immagine() {
        return path_immagine;
    }

    public void setPath_immagine(byte[] path_immagine) {
        this.path_immagine = path_immagine;
    }

    // Getter e setter per prezzoMax
    public float getPrezzoMax() {
        return prezzoMax;
    }

    public void setPrezzoMax(float prezzoMax) {
        this.prezzoMax = prezzoMax;
    }

    // Getter e setter per prezzoAttuale
    public float getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(float prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }

    // Getter e setter per dataDiScadenza
    public String getDataDiScadenza() {
        return dataDiScadenza;
    }

    public void setDataDiScadenza(String dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    // Getter e setter per condizione
    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    // Getter e setter per id_acquirente
    public String getId_acquirente() {
        return id_acquirente;
    }

    public void setId_acquirente(String id_acquirente) {
        this.id_acquirente = id_acquirente;
    }
}

