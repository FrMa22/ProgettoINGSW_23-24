package com.example.progettoingsw.model;

public class NotificheVenditoreModel {
    private Long id;
    private String titolo;
    private String commento;
    private String idVenditore;

    // Costruttori
    public NotificheVenditoreModel() {
    }

    public NotificheVenditoreModel(Long id, String titolo, String commento, String idVenditore) {
        this.id = id;
        this.titolo = titolo;
        this.commento = commento;
        this.idVenditore = idVenditore;
    }

    // Getter e setter per id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e setter per titolo
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    // Getter e setter per commento
    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    // Getter e setter per idVenditore
    public String getIdVenditore() {
        return idVenditore;
    }

    public void setIdVenditore(String idVenditore) {
        this.idVenditore = idVenditore;
    }
}
