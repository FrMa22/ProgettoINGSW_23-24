package com.example.progettoingsw.model;
public class Prodotto {
    private int id;
    private String nome;
    private String descrizione;
    private String pathImmagine;

    public Prodotto() {
    }

    public Prodotto(int id, String nome, String descrizione, String pathImmagine) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.pathImmagine = pathImmagine;
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

    public String getPathImmagine() {
        return pathImmagine;
    }

    public void setPathImmagine(String pathImmagine) {
        this.pathImmagine = pathImmagine;
    }
}
