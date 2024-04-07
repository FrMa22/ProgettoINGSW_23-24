package com.example.progettoingsw.repository;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.util.ArrayList;

public class Repository {
    private AcquirenteModel acquirenteModel;
    private VenditoreModel venditoreModel;
    public static final String backendUrl = "http:/13.37.225.18:8080/";
    public static Repository questaRepository = null;
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza;

    private ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove;

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
    public void setListaAsteAllIngleseInScadenza(ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza) {
        this.listaAsteAllIngleseInScadenza = listaAsteAllIngleseInScadenza;
    }

    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseInScadenza() {
        return listaAsteAllIngleseInScadenza;
    }

    public void setListaAsteAlRibassoNuove(ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove){
        this.listaAsteAlRibassoNuove = listaAsteAlRibassoNuove;
    }
    public ArrayList<Asta_alribassoModel> getListaAsteAlRibassoNuove(){
        return listaAsteAlRibassoNuove;
    }

}
