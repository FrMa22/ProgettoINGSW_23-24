package com.example.progettoingsw.model;

public class NotificheAcquirenteModel {
    private Long id;
    private String titolo;
    private String commento;
    private String idAcquirente;

    // Costruttori
    public NotificheAcquirenteModel() {
    }

    public NotificheAcquirenteModel(Long id, String titolo, String commento, String idAcquirente) {
        this.id = id;
        this.titolo = titolo;
        this.commento = commento;
        this.idAcquirente = idAcquirente;
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

    // Getter e setter per idAcquirente
    public String getIdAcquirente() {
        return idAcquirente;
    }

    public void setIdAcquirente(String idAcquirente) {
        this.idAcquirente = idAcquirente;
    }
}

