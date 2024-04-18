package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;

public class SchermataPreferityViewModel extends ViewModel {
    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Asta_inversaRepository astaInversaRepository;
    public MutableLiveData<Boolean> isAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Asta_allingleseModel>> listaAstaInglesePreferite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaInglesePreferiteConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_alribassoModel>> listaAstaRibassoPreferite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaRibassoPreferiteConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_inversaModel>> listaAstaInversaPreferite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaInversaPreferiteConvertite = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);

    public SchermataPreferityViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaAlribassoRepository = new Asta_alribassoRepository();
        astaInversaRepository = new Asta_inversaRepository();
    }
    public void setIsAcquirente(Boolean b) {
        isAcquirente.setValue(b);
    }

    public ArrayList<Asta_allingleseModel> getListaAstaInglesePreferite() {
        return listaAstaInglesePreferite.getValue();
    }
    public void setListaAstaInglesePreferite(ArrayList<Asta_allingleseModel> lista) {
        listaAstaInglesePreferite.setValue(lista);
    }
    public void setListaAstaInglesePreferiteConvertite(ArrayList<Object> lista) {
        listaAstaInglesePreferiteConvertite.setValue(lista);
    }
    public ArrayList<Asta_alribassoModel> getListaAstaRibassoPreferite() {
        return listaAstaRibassoPreferite.getValue();
    }

    public void setListaAstaRibassoPreferite(ArrayList<Asta_alribassoModel> lista) {
        listaAstaRibassoPreferite.setValue(lista);
    }
    public void setListaAstaRibassoPreferiteConvertite(ArrayList<Object> lista) {
        listaAstaRibassoPreferiteConvertite.setValue(lista);
    }
    public ArrayList<Asta_inversaModel> getListaAstaInversaPreferite() {
        return listaAstaInversaPreferite.getValue();
    }
    public void setListaAstaInversaPreferite(ArrayList<Asta_inversaModel> lista) {
        listaAstaInversaPreferite.setValue(lista);
    }
    public void setListaAstaInversaPreferiteConvertite(ArrayList<Object> lista) {
        listaAstaInversaPreferiteConvertite.setValue(lista);
    }

    public void getTipoUtente(){
        if(repository.getAcquirenteModel()!=null){
            setIsAcquirente(true);
        }else{
            setIsAcquirente(false);
        }
    }
    public void getAsteInglesePreferite(){
        String indirizzo_email = repository.getAcquirenteModel().getIndirizzoEmail();
        astaAllingleseRepository.getAsteInglesePreferite(indirizzo_email, new Asta_allingleseRepository.OnGetAstePreferiteListener() {
            @Override
            public void OnGetAstePreferite(ArrayList<Asta_allingleseModel> list) {
                setListaAstaInglesePreferite(list);
            }
        });
    }
    public void convertiAsteInglese(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaInglesePreferite());
        setListaAstaInglesePreferiteConvertite(lista);
    }
    public void getAsteRibassoPreferite(){
        String indirizzo_email = repository.getAcquirenteModel().getIndirizzoEmail();
        astaAlribassoRepository.getAsteRibassoPreferite(indirizzo_email, new Asta_alribassoRepository.OnGetAsteRibassoPreferiteListener() {
            @Override
            public void OnGetAsteRibassoPreferite(ArrayList<Asta_alribassoModel> list) {
                setListaAstaRibassoPreferite(list);
            }
        });
    }
    public void convertiAsteRibasso(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaRibassoPreferite());
        setListaAstaRibassoPreferiteConvertite(lista);
    }
    public void getAsteInversaPreferite(){
        String indirizzo_email = repository.getVenditoreModel().getIndirizzoEmail();
        astaInversaRepository.getAsteInversaPreferite(indirizzo_email, new Asta_inversaRepository.OnGetAsteInversaPreferiteListener() {
            @Override
            public void OnGetAsteInversaPreferite(ArrayList<Asta_inversaModel> list) {
                setListaAstaInversaPreferite(list);
            }
        });
    }
    public void convertiAsteInversa(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaInversaPreferite());
        setListaAstaInversaPreferiteConvertite(lista);
    }
    public void setEntraInSchermataAstaInglese(Boolean b){
        entraInSchermataAstaInglese.setValue(b);
    }
    public Boolean getEntraInSchermataAstaInglese(){
        return entraInSchermataAstaInglese.getValue();
    }
    public void setEntraInSchermataAstaRibasso(Boolean b){
        entraInSchermataAstaRibasso.setValue(b);
    }
    public Boolean getEntraInSchermataAstaRibasso(){
        return entraInSchermataAstaRibasso.getValue();
    }
    public void setEntraInSchermataAstaInversa(Boolean b){
        entraInSchermataAstaInversa.setValue(b);
    }
    public Boolean getEntraInSchermataAstaInversa(){
        return entraInSchermataAstaInversa.getValue();
    }
    public void gestisciClickRecyclerView( Object asta){
        // Esegui le azioni desiderate con l'oggetto Asta
        if(asta instanceof Asta_allingleseModel){
            repository.setAsta_allingleseSelezionata((Asta_allingleseModel) asta);
            setEntraInSchermataAstaInglese(true);

        }else if(asta instanceof Asta_alribassoModel){
            repository.setAsta_alribassoSelezionata((Asta_alribassoModel) asta);
            setEntraInSchermataAstaRibasso(true);

        }else if(asta instanceof Asta_inversaModel){
            repository.setAsta_inversaSelezionata((Asta_inversaModel) asta);
            setEntraInSchermataAstaInversa(true);

        }
    }


}
