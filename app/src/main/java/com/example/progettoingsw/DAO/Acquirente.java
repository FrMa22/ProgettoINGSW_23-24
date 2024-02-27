package com.example.progettoingsw.DAO;
public class Acquirente {
    private String nome;
    private String cognome;
    private String email;
    private String sitoWeb;
    private String paese;
    private String bio;

    public Acquirente(String nome, String cognome, String email, String sitoWeb, String paese, String bio) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.sitoWeb = sitoWeb;
        this.paese = paese;
        this.bio = bio;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }

    public String getPaese() {
        return paese;
    }
    public String getBio(){
         return bio;
    }
}
