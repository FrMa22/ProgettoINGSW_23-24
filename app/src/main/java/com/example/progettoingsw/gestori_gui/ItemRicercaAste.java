package com.example.progettoingsw.gestori_gui;

import java.util.ArrayList;

public class ItemRicercaAste {
    private String parolaRicercata;
    private ArrayList<String> categorieScelteList;
    private String ordinamentoPrezzo;

    public ItemRicercaAste(String parolaRicercata, ArrayList<String> categorieScelteList, String ordinamentoPrezzo) {
        this.parolaRicercata = parolaRicercata;
        this.categorieScelteList = categorieScelteList;
        this.ordinamentoPrezzo = ordinamentoPrezzo;
    }

    // Metodi getter per ottenere i valori

    public String getParolaRicercata() {
        return parolaRicercata;
    }

    public ArrayList<String> getCategorieScelteList() {
        return categorieScelteList;
    }

    public String getOrdinamentoPrezzo() {
        return ordinamentoPrezzo;
    }
}
