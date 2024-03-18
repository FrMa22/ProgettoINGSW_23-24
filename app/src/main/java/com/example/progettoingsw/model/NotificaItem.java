package com.example.progettoingsw.model;

public class NotificaItem {

private int id;
private String titolo;
private String commento;

private String emailA;
private String emailV;
private String tipoUtente;


    public NotificaItem(int id,String titolo,String commento,String emailAcquirente,String emailVenditore,String tipo){
        this.id=id;
        this.titolo=titolo;
        this.commento=commento;
        this.emailA=emailAcquirente;
        this.emailV=emailVenditore;
        this.tipoUtente=tipo;
    }


    // Getter e Setter per id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter per titolo
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    // Getter e Setter per commento
    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    // Getter e Setter per emailA
    public String getEmailA() {
        return emailA;
    }

    public void setEmailA(String emailAcquirente) {
        this.emailA = emailAcquirente;
    }

    // Getter e Setter per emailV
    public String getEmailV() {
        return emailV;
    }

    public void setEmailV(String emailVenditore) {
        this.emailV = emailVenditore;
    }


    // Getter e Setter per tipoUtente
    public String getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(String tipo) {
        this.tipoUtente = tipo;
    }

}
