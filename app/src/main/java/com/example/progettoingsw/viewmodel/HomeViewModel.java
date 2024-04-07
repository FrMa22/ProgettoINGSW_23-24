package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Repository repository;
    public MutableLiveData<Boolean> aste_allingleseInScadenzaPresenti = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> aste_alribassoNuovePresenti = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);
    private ArrayList<Asta_allingleseModel> listAsta_allingleseScadenzaRecente = new ArrayList<>();
    private ArrayList<Asta_alribassoModel> listAsta_alribassoNuove = new ArrayList<>();
    public HomeViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaAlribassoRepository = new Asta_alribassoRepository();
    }

    public void checkTipoUtente(){
        if(containsAcquirente()){
            setAcquirenteModelPresente();
        }else if(containsVenditore()) {
            setVenditoreModelPresente();
        }

    }
    public void trovaEImpostaAste(){
        if(repository.getAcquirenteModel()!=null){
            Log.d("HomeViewModel ", "entrato come acquirente");
            try {
                trovaAste_allingleseScadenzaRecente();
                trovaAste_alribassoNuove();
            } catch (Exception e){
                e.printStackTrace();
            }
        }else if(repository.getVenditoreModel()!=null){
            Log.d("HomeViewModel ", "entrato come venditore");
//            try {
//                trovaAste_inverseScadenzaRecente();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
        }
    }

    private void setAcquirenteModelPresente() {
        acquirenteModelPresente.setValue(true);
    }
    public Boolean getAcquirenteModelPresente(){
        return acquirenteModelPresente.getValue();
    }
    private void setVenditoreModelPresente() {
        venditoreModelPresente.setValue(true);
    }
    public Boolean getVenditoreModelPresente(){
        return venditoreModelPresente.getValue();
    }


    private void setAste_allingleseInScadenzaPresenti(boolean b) {
        aste_allingleseInScadenzaPresenti.setValue(true);
    }
    public Boolean getAste_allingleseInScadenzaPresenti() {
        return aste_allingleseInScadenzaPresenti.getValue();
    }

    private void trovaAste_allingleseScadenzaRecente() {
        astaAllingleseRepository.getAste_allingleseScadenzaRecenteBackend(new Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener() {
            @Override
            public void OnGetAsteScadenzaRecente(ArrayList<Asta_allingleseModel> list) {
                listAsta_allingleseScadenzaRecente = list;
                repository.setListaAsteAllIngleseInScadenza(list);
                if(listAsta_allingleseScadenzaRecente != null && !listAsta_allingleseScadenzaRecente.isEmpty()){
                    setAste_allingleseInScadenzaPresenti(true);
                }
            }
        });
    }
    private void trovaAste_alribassoNuove() {
        astaAlribassoRepository.getAste_alribassoNuoveBackend(new Asta_alribassoRepository.OnGetAsteRibassoNuoveListener() {
            @Override
            public void OnGetAsteRibassoNuove(ArrayList<Asta_alribassoModel> list) {
                listAsta_alribassoNuove = list;
                repository.setListaAsteAlRibassoNuove(list);
                if(listAsta_alribassoNuove != null && !listAsta_alribassoNuove.isEmpty()){
                    setAste_alribassoNuovePresenti(true);
                }
            }
        });
    }

    public List<Asta_allingleseModel> getListaAsta_allingleseScadenzaRecente(){
        Log.d("getListaAsta_allingleseScadenzaRecente", "asta: "  + repository.getListaAsteAllIngleseInScadenza().size());
        return repository.getListaAsteAllIngleseInScadenza();
    }
    public boolean containsAcquirente() {
        return repository.getAcquirenteModel()!=null;
    }
    public Boolean containsVenditore(){
        return repository.getVenditoreModel()!=null;
    }

    public void setAste_alribassoNuovePresenti(Boolean b){
        aste_alribassoNuovePresenti.setValue(true);
    }
    public Boolean getAste_alribassoNuovePresenti(){
        return aste_alribassoNuovePresenti.getValue();
    }
    public List<Asta_alribassoModel> getListaAsta_alribassoNuove(){
        return repository.getListaAsteAlRibassoNuove();
    }

}
