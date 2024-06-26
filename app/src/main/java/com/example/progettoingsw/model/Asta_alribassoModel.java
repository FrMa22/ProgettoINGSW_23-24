package com.example.progettoingsw.model;

public class Asta_alribassoModel {
    private Long id;
    private String nome;
    private String descrizione;
    private byte[] path_immagine;
    private float prezzoBase;
    private String intervalloDecrementale;
    private String intervalloBase;
    private float decrementoAutomaticoCifra;
    private float prezzoMin;
    private float prezzoAttuale;
    private String condizione;
    private String id_venditore;

    public Asta_alribassoModel(){

    }
    public Asta_alribassoModel(Long id, String nome, String descrizione, byte[] path_immagine, float prezzoBase, String intervalloDecrementale, String intervalloBase, float decrementoAutomaticoCifra, float prezzoMin, float prezzoAttuale, String condizione, String id_venditore) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.path_immagine = path_immagine;
        this.prezzoBase = prezzoBase;
        this.intervalloDecrementale = intervalloDecrementale;
        this.intervalloBase = intervalloBase;
        this.decrementoAutomaticoCifra = decrementoAutomaticoCifra;
        this.prezzoMin = prezzoMin;
        this.prezzoAttuale = prezzoAttuale;
        this.condizione = condizione;
        this.id_venditore = id_venditore;
    }
    public Asta_alribassoModel(String nome, String descrizione, byte[] path_immagine, float prezzoBase, String intervalloDecrementale, String intervalloBase, float decrementoAutomaticoCifra, float prezzoMin, float prezzoAttuale, String condizione, String id_venditore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.path_immagine = path_immagine;
        this.prezzoBase = prezzoBase;
        this.intervalloDecrementale = intervalloDecrementale;
        this.intervalloBase = intervalloBase;
        this.decrementoAutomaticoCifra = decrementoAutomaticoCifra;
        this.prezzoMin = prezzoMin;
        this.prezzoAttuale = prezzoAttuale;
        this.condizione = condizione;
        this.id_venditore = id_venditore;
    }

    // Metodi getter e setter per id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodi getter e setter per nome, descrizione, path_immagine, prezzoBase, intervalloDecrementale, intervalloBase, decrementoAutomaticoCifra, prezzoMin, prezzoAttuale, condizione, id_venditore (omessi per brevità)
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

    // Getter e setter per prezzoBase
    public float getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(float prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    // Getter e setter per intervalloDecrementale
    public String getIntervalloDecrementale() {
        return intervalloDecrementale;
    }

    public void setIntervalloDecrementale(String intervalloDecrementale) {
        this.intervalloDecrementale = intervalloDecrementale;
    }

    // Getter e setter per intervalloBase
    public String getIntervalloBase() {
        return intervalloBase;
    }

    public void setIntervalloBase(String intervalloBase) {
        this.intervalloBase = intervalloBase;
    }

    // Getter e setter per decrementoAutomaticoCifra
    public float getDecrementoAutomaticoCifra() {
        return decrementoAutomaticoCifra;
    }

    public void setDecrementoAutomaticoCifra(float decrementoAutomaticoCifra) {
        this.decrementoAutomaticoCifra = decrementoAutomaticoCifra;
    }

    // Getter e setter per prezzoMin
    public float getPrezzoMin() {
        return prezzoMin;
    }

    public void setPrezzoMin(float prezzoMin) {
        this.prezzoMin = prezzoMin;
    }

    // Getter e setter per prezzoAttuale
    public float getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(float prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }

    // Getter e setter per condizione
    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    // Getter e setter per id_venditore
    public String getId_venditore() {
        return id_venditore;
    }

    public void setId_venditore(String id_venditore) {
        this.id_venditore = id_venditore;
    }
}
