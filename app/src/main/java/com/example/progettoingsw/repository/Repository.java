package com.example.progettoingsw.repository;

import com.example.progettoingsw.model.AcquirenteModel;

public class Repository {
    private AcquirenteModel acquirenteModel;
    public static final String backendUrl = "http://15.236.37.19:8080/";
    public static Repository questaRepository = null;

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

}
