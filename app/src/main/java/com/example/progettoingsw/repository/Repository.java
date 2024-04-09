package com.example.progettoingsw.repository;

import android.util.Log;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.util.ArrayList;

public class Repository {
    private AcquirenteModel acquirenteModel;
    private ArrayList<String> listaCategorieAcquirente;
    private VenditoreModel venditoreModel;
    private ArrayList<String> listaCategorieVenditore;
    public static final String backendUrl = "http:/13.38.47.64:8080/";
    public static Repository questaRepository = null;
    //liste per le aste all'inglese nel caso di accesso come acquirente (aste in home)
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza;
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseCategoriaNome;
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseNuove;
    //liste per le aste al ribasso nel caso di accesso come acquirente (aste in home)
    private ArrayList<Asta_alribassoModel> listaAsteAlRibassoCategoriaNome;
    private ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove;
    //liste per le aste inverse nel caso di accesso come venditore (aste in home)
    private ArrayList<Asta_inversaModel> listaAsteInversaInScadenza;
    private ArrayList<Asta_inversaModel> listaAsteInversaNuove;
    private ArrayList<Asta_inversaModel> listaAsteInversaCategoriaNome;


    private Repository(){

    }
    public static Repository getInstance() {
        if (questaRepository == null) {
            questaRepository = new Repository();
        }
        return questaRepository;
    }

    public void setAcquirenteModel(AcquirenteModel acquirenteModel){
        this.acquirenteModel = acquirenteModel;
    }

    public AcquirenteModel getAcquirenteModel(){
        return acquirenteModel;
    }
    public void setVenditoreModel(VenditoreModel venditoreModel){
        this.venditoreModel = venditoreModel;
    }

    public VenditoreModel getVenditoreModel(){
        return venditoreModel;
    }


    //metodi getter e setter per le liste di aste inglesi
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseInScadenza() {
        return listaAsteAllIngleseInScadenza;
    }
    public void setListaAsteAllIngleseInScadenza(ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza) {
        this.listaAsteAllIngleseInScadenza = listaAsteAllIngleseInScadenza;
    }
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseNuove() {
        return listaAsteAllIngleseNuove;
    }
    public void setListaAsteAllIngleseNuove(ArrayList<Asta_allingleseModel> listaAsteAllIngleseNuove) {
        this.listaAsteAllIngleseNuove = listaAsteAllIngleseNuove;
    }
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseCategoriaNome() {
        return listaAsteAllIngleseCategoriaNome;
    }
    public void setListaAsteAllIngleseCategoriaNome(ArrayList<Asta_allingleseModel> listaAsteAllIngleseCategoriaNome) {
        this.listaAsteAllIngleseCategoriaNome = listaAsteAllIngleseCategoriaNome;
    }

    //metodi getter e setter per le liste di aste al ribasso
    public void setListaAsteAlRibassoNuove(ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove){
        this.listaAsteAlRibassoNuove = listaAsteAlRibassoNuove;
    }
    public ArrayList<Asta_alribassoModel> getListaAsteAlRibassoNuove(){
        return listaAsteAlRibassoNuove;
    }
    public ArrayList<Asta_alribassoModel> getListaAsteAlRibassoCategoriaNome() {
        return listaAsteAlRibassoCategoriaNome;
    }
    public void setListaAsteAlRibassoCategoriaNome(ArrayList<Asta_alribassoModel> listaAsteAlRibassoCategoriaNome) {
        this.listaAsteAlRibassoCategoriaNome = listaAsteAlRibassoCategoriaNome;
    }


    //metodi getter e setter per le liste di aste inverse
    public ArrayList<Asta_inversaModel> getListaAsteInversaInScadenza() {
        return listaAsteInversaInScadenza;
    }
    public void setListaAsteInversaInScadenza(ArrayList<Asta_inversaModel> listaAsteInversaInScadenza) {
        this.listaAsteInversaInScadenza = listaAsteInversaInScadenza;
    }
    public ArrayList<Asta_inversaModel> getListaAsteInversaNuove() {
        return listaAsteInversaNuove;
    }
    public void setListaAsteInversaNuove(ArrayList<Asta_inversaModel> listaAsteInversaNuove) {
        this.listaAsteInversaNuove = listaAsteInversaNuove;
    }
    public ArrayList<Asta_inversaModel> getListaAsteInversaCategoriaNome() {
        return listaAsteInversaCategoriaNome;
    }
    public void setListaAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> listaAsteInversaCategoriaNome) {
        this.listaAsteInversaCategoriaNome = listaAsteInversaCategoriaNome;
    }





    public ArrayList<String> getListaCategorieAcquirente() {
        return listaCategorieAcquirente;
    }

    public void setListaCategorieAcquirente(ArrayList<String> listaCategorieAcquirente) {
        Log.d("In repository", "setListaCategoriaAcquirente con lista " + listaCategorieAcquirente);
        this.listaCategorieAcquirente = listaCategorieAcquirente;
    }
    public ArrayList<String> getListaCategorieVenditore() {
        return listaCategorieVenditore;
    }

    public void setListaCategorieVenditore(ArrayList<String> listaCategorieVenditore) {
        Log.d("In repository", "setListaCategoriaVenditore con lista " + listaCategorieAcquirente);
        this.listaCategorieVenditore = listaCategorieVenditore;
    }

}
