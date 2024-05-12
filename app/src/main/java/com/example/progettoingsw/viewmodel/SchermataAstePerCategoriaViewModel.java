package com.example.progettoingsw.viewmodel;

import android.util.Log;

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

public class SchermataAstePerCategoriaViewModel extends ViewModel {

    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Asta_inversaRepository astaInversaRepository;

    public MutableLiveData<Boolean> isAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Asta_allingleseModel>> listaAstaIngleseCategoria = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaIngleseCategoriaConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_alribassoModel>> listaAstaRibassoCategoria = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaRibassoCategoriaConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_inversaModel>> listaAstaInversaCategoria = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaInversaCategoriaConvertite = new MutableLiveData<>(null);


    public MutableLiveData<String> nomeCategoriaPerTextview = new MutableLiveData<>("");

    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);


    public SchermataAstePerCategoriaViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaAlribassoRepository = new Asta_alribassoRepository();
        astaInversaRepository = new Asta_inversaRepository();
    }


    public void getAstePerCategoria(){
        String categoriaSelezionata = repository.getCategoriaSelezionata();
        ArrayList<String> listaCategoriaSingola = new ArrayList<>();
        listaCategoriaSingola.add(categoriaSelezionata);
        if(repository.getAcquirenteModel()!=null){
            getAsteInglesiPerCategoria(listaCategoriaSingola);
        }else{
            getAsteInversaPerCategoria(listaCategoriaSingola);
        }
    }
    public Boolean getIsAcquirente() {
        return isAcquirente.getValue();
    }
    public void setIsAcquirente(Boolean isAcquirente) {
        this.isAcquirente.setValue(isAcquirente);
    }
    public ArrayList<Asta_allingleseModel> getListaAstaIngleseCategoria() {
        return listaAstaIngleseCategoria.getValue();
    }
    public void setListaAstaIngleseCategoria(ArrayList<Asta_allingleseModel> lista) {
        listaAstaIngleseCategoria.setValue(lista);
    }
    public void setListaAstaIngleseCategoriaConvertite(ArrayList<Object> lista) {
        listaAstaIngleseCategoriaConvertite.setValue(lista);
    }
    public ArrayList<Asta_alribassoModel> getListaAstaRibassoCategoria() {
        return listaAstaRibassoCategoria.getValue();
    }
    public void setListaAstaRibassoCategoria(ArrayList<Asta_alribassoModel> lista) {
        listaAstaRibassoCategoria.setValue(lista);
    }
    public void setListaAstaRibassoCategoriaConvertite(ArrayList<Object> lista) {
        listaAstaRibassoCategoriaConvertite.setValue(lista);
    }
    public ArrayList<Asta_inversaModel> getListaAstaInversaCategoria() {
        return listaAstaInversaCategoria.getValue();
    }
    public void setListaAstaInversaCategoria(ArrayList<Asta_inversaModel> lista) {
        listaAstaInversaCategoria.setValue(lista);
    }
    public void setListaAstaInversaCategoriaConvertite(ArrayList<Object> lista) {
        listaAstaInversaCategoriaConvertite.setValue(lista);
    }
    public void checkTipoUtente(){
        if(repository.getAcquirenteModel()!=null){
            setIsAcquirente(true);
        }else{
            setIsAcquirente(false);
        }
    }
    public void getAsteInglesiPerCategoria(ArrayList<String> listaCategoriaSingola){
        astaAllingleseRepository.getAste_allingleseCategoriaNomeBackend(listaCategoriaSingola, new Asta_allingleseRepository.OnGetAsteCategoriaNomeListener() {
            @Override
            public void OnGetAsteCategoriaNome(ArrayList<Asta_allingleseModel> list) {
                setListaAstaIngleseCategoria(list);
                getAsteRibassoPerCategoria(listaCategoriaSingola);
            }
        });
    }
    public void getAsteRibassoPerCategoria(ArrayList<String> listaCategoriaSingola){
        astaAlribassoRepository.getAste_alribassoCategoriaNomeBackend(listaCategoriaSingola, new Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener() {
            @Override
            public void OnGetAsteRibassoCategoriaNome(ArrayList<Asta_alribassoModel> list) {
                setListaAstaRibassoCategoria(list);
            }
        });
    }
    public void getAsteInversaPerCategoria(ArrayList<String> listaCategoriaSingola){
        astaInversaRepository.getAste_inversaCategoriaNomeBackend(listaCategoriaSingola, new Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener() {
            @Override
            public void OnGetAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> list) {
                setListaAstaInversaCategoria(list);
            }
        });
    }
    public void convertiAsteInglese(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaIngleseCategoria());
        setListaAstaIngleseCategoriaConvertite(lista);
    }
    public void convertiAsteRibasso(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaRibassoCategoria());
        setListaAstaRibassoCategoriaConvertite(lista);
    }
    public void convertiAsteInversa(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaInversaCategoria());
        setListaAstaInversaCategoriaConvertite(lista);
    }

    public String getNomeCategoriaPerTextview() {
        return nomeCategoriaPerTextview.getValue();
    }
    public void setNomeCategoriaPerTextview(String nomeCategoriaPerTextview) {
        this.nomeCategoriaPerTextview.setValue(nomeCategoriaPerTextview);
    }
    public Boolean isNomeCategoriaPerTextView(){
        return !nomeCategoriaPerTextview.getValue().equals("");
    }
    public void checkNomeCategoriaPerTextview(){
        String nome = repository.getCategoriaSelezionata();
        setNomeCategoriaPerTextview(nome);
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
