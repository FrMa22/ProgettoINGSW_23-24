package com.example.progettoingsw.model;

public class SocialAcquirenteModel {
    private String nome;
    private String link;
    private String indirizzo_email;



    // Costruttore con parametri
    public SocialAcquirenteModel(String nome, String link,String indirizzo_email) {
        this.nome = nome;
        this.link = link;
        this.indirizzo_email=indirizzo_email;
    }

    // Metodi getter e setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIndirizzo_email() {
        return indirizzo_email;
    }

    public void setIndirizzo_email(String indirizzo_email) {
        this.indirizzo_email = indirizzo_email;
    }
}
