package com.example.progettoingsw.controllers_package;

import java.util.ArrayList;

public class InsertAsta {
    private int idAsta;
    private ArrayList<String> categorie;

    public InsertAsta(int idAsta, ArrayList<String> categorie) {
        this.idAsta = idAsta;
        this.categorie = categorie;
    }

    public int getIdAsta() {
        return idAsta;
    }

    public ArrayList<String> getCategorie() {
        return categorie;
    }
}
