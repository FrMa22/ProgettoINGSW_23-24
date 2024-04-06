package com.example.progettoingsw.DTO;

public class AcquirenteDTO {
    private String indirizzoEmail;
    private String nome;
    private String cognome;
    private String password;
    private String bio;
    private String link;
    private String areaGeografica;

    public AcquirenteDTO() {
    }

    public AcquirenteDTO(String indirizzoEmail, String nome, String cognome, String password, String bio, String link, String areaGeografica) {
        this.indirizzoEmail = indirizzoEmail;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.bio = bio;
        this.link = link;
        this.areaGeografica = areaGeografica;
    }

    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public void setIndirizzoEmail(String indirizzoEmail) {
        this.indirizzoEmail = indirizzoEmail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAreaGeografica() {
        return areaGeografica;
    }

    public void setAreaGeografica(String areaGeografica) {
        this.areaGeografica = areaGeografica;
    }
}
