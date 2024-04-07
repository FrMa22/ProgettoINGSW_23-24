package com.example.progettoingsw.model;

public class Asta_allingleseModel {
    private Long id;
    private String nome;
    private String descrizione;
    private byte[] path_immagine;
    private float baseAsta;
    private String intervalloTempoOfferte;
    private String intervalloOfferteBase;
    private float rialzoMin;
    private float prezzoAttuale;
    private String condizione;
    private String id_venditore;

    public Asta_allingleseModel(){

    }
    public Asta_allingleseModel(Long id, String nome, String descrizione, byte[] path_immagine, float baseAsta, String intervalloTempoOfferte, String intervalloOfferteBase, float rialzoMin, float prezzoAttuale, String condizione, String id_venditore) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.path_immagine = path_immagine;
        this.baseAsta = baseAsta;
        this.intervalloTempoOfferte = intervalloTempoOfferte;
        this.intervalloOfferteBase = intervalloOfferteBase;
        this.rialzoMin = rialzoMin;
        this.prezzoAttuale = prezzoAttuale;
        this.condizione = condizione;
        this.id_venditore = id_venditore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getPath_immagine() {
        return path_immagine;
    }

    public void setPath_immagine(byte[] path_immagine) {
        this.path_immagine = path_immagine;
    }

    public float getBaseAsta() {
        return baseAsta;
    }

    public void setBaseAsta(float baseAsta) {
        this.baseAsta = baseAsta;
    }

    public String getIntervalloTempoOfferte() {
        return intervalloTempoOfferte;
    }

    public void setIntervalloTempoOfferte(String intervalloTempoOfferte) {
        this.intervalloTempoOfferte = intervalloTempoOfferte;
    }

    public String getIntervalloOfferteBase() {
        return intervalloOfferteBase;
    }

    public void setIntervalloOfferteBase(String intervalloOfferteBase) {
        this.intervalloOfferteBase = intervalloOfferteBase;
    }

    public float getRialzoMin() {
        return rialzoMin;
    }

    public void setRialzoMin(float rialzoMin) {
        this.rialzoMin = rialzoMin;
    }

    public float getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(float prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    public String getId_venditore() {
        return id_venditore;
    }

    public void setId_venditore(String id_venditore) {
        this.id_venditore = id_venditore;
    }

}
