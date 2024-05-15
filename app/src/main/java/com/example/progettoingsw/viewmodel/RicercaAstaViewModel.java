package com.example.progettoingsw.viewmodel;

import android.widget.Switch;

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

public class RicercaAstaViewModel extends ViewModel {
    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Asta_inversaRepository astaInversaRepository;

    public MutableLiveData<Boolean> isAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<String>> categorie = new MutableLiveData<>(null);



    public MutableLiveData<Boolean> noCategorie = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Asta_allingleseModel>> listaAstaIngleseRicerca = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaIngleseRicercaConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_alribassoModel>> listaAstaRibassoRicerca = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaRibassoRicercaConvertite = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Asta_inversaModel>> listaAstaInversaRicerca = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> listaAstaInversaRicercaConvertite = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> apriFiltro = new MutableLiveData<>(false);
    public MutableLiveData<String> ordinamento = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> chiudiPopUp = new MutableLiveData<>(false);

    private ArrayList<String> categorieScelte = new ArrayList<>();
    private String ordinamentoScelto;

    private ArrayList<String> categorieAncoraDaSalvare = new ArrayList<>();
    private String ordinamentoAncoraDaSalvare;
    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);
    public RicercaAstaViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaAlribassoRepository = new Asta_alribassoRepository();
        astaInversaRepository = new Asta_inversaRepository();
    }
    public Boolean getNoCategorie() {
        return noCategorie.getValue();
    }
    public void setNoCategorie(Boolean noCategorie) {
        this.noCategorie.setValue(noCategorie);
    }
    public ArrayList<Asta_allingleseModel> getListaAstaIngleseRicerca() {
        return listaAstaIngleseRicerca.getValue();
    }
    public void setListaAstaIngleseRicerca(ArrayList<Asta_allingleseModel> lista) {
        if (lista != null && !lista.isEmpty()) {
            // Utilizza la lista delle aste solo se non è null o vuota
            this.listaAstaIngleseRicerca.setValue(new ArrayList<>(lista)); // Inizializza la lista utilizzando il costruttore con la collezione
        } else {
            this.listaAstaIngleseRicerca.setValue(new ArrayList<>()); // Altrimenti inizializza la lista come nuova lista vuota
        }
    }
    public void setListaAstaIngleseRicercaConvertite(ArrayList<Object> lista) {
        listaAstaIngleseRicercaConvertite.setValue(lista);
    }
    public ArrayList<Asta_alribassoModel> getListaAstaRibassoRicerca() {
        return listaAstaRibassoRicerca.getValue();
    }
    public void setListaAstaRibassoRicerca(ArrayList<Asta_alribassoModel> lista) {
        listaAstaRibassoRicerca.setValue(lista);
    }
    public void setListaAstaRibassoRicercaConvertite(ArrayList<Object> lista) {
        listaAstaRibassoRicercaConvertite.setValue(lista);
    }
    public ArrayList<Asta_inversaModel> getListaAstaInversaRicerca() {
        return listaAstaInversaRicerca.getValue();
    }
    public void setListaAstaInversaRicerca(ArrayList<Asta_inversaModel> lista) {
        listaAstaInversaRicerca.setValue(lista);
    }
    public void setListaAstaInversaRicercaConvertite(ArrayList<Object> lista) {
        listaAstaInversaRicercaConvertite.setValue(lista);
    }
    public Boolean getIsAcquirente() {
        return isAcquirente.getValue();
    }
    public void setIsAcquirente(Boolean isAcquirente) {
        this.isAcquirente.setValue(isAcquirente);
    }
    public ArrayList<String> getCategorie() {
        return categorie.getValue();
    }
    public void setCategorie(ArrayList<String> categorie) {
        this.categorie.setValue(categorie);
    }
    public Boolean isCategorie(){
        if(categorie.getValue()!=null) {
            return !categorie.getValue().isEmpty();
        }
        return false;
    }
    public String getOrdinamento() {
        return ordinamento.getValue();
    }
    public void setOrdinamento(String ordinamento) {
        this.ordinamento.setValue(ordinamento);
    }
    public Boolean isOrdinamento(){
        if(ordinamento.getValue()!=null){
            return !ordinamento.getValue().equals("");
        }
        return false;
    }
    public ArrayList<String> getCategorieScelte() {
        return categorieScelte;
    }
    public void setCategorieScelte(ArrayList<String> categorieScelte) {
        this.categorieScelte = categorieScelte;
    }
    public String getOrdinamentoScelto() {
        return ordinamentoScelto;
    }
    public void setOrdinamentoScelto(String ordinamentoScelto) {
        this.ordinamentoScelto = ordinamentoScelto;
    }
    public Boolean isOrdinamentoAsc(){
        return ordinamentoAncoraDaSalvare.equals("ASC");
    }
    public ArrayList<String> getCategorieAncoraDaSalvare() {
        return categorieAncoraDaSalvare;
    }
    public void setCategorieAncoraDaSalvare(ArrayList<String> lista) {
        this.categorieAncoraDaSalvare = new ArrayList<>(lista);
    }
    public String getOrdinamentoAncoraDaSalvare() {
        return ordinamentoAncoraDaSalvare;
    }
    public void setOrdinamentoAncoraDaSalvare(String ordinamentoAncoraDaSalvare) {
        this.ordinamentoAncoraDaSalvare = new String(ordinamentoAncoraDaSalvare);
    }
    public void setCategoriePopUp(){
        if(categorieScelte!=null && !categorieScelte.isEmpty()){
            setCategorieAncoraDaSalvare(categorieScelte);
            setCategorie(categorieScelte);
        }else{
            categorieAncoraDaSalvare = new ArrayList<>();
            setNoCategorie(true);
        }

    }
    public void setOrdinamentoPopUp(){
        if(ordinamentoScelto!=null && !ordinamentoScelto.isEmpty()){
            setOrdinamentoAncoraDaSalvare(ordinamentoScelto);
            setOrdinamento(ordinamentoScelto);
        }
    }
    public void gestisciSwitchOrdinamento(){
        if(ordinamentoAncoraDaSalvare!=null) {
            if (ordinamentoAncoraDaSalvare.equals("ASC")) {
                ordinamentoAncoraDaSalvare = "DESC";
            } else if (ordinamentoAncoraDaSalvare.equals("DESC")) {
                ordinamentoAncoraDaSalvare = "ASC";
            }
            setOrdinamento(ordinamentoAncoraDaSalvare);
        }else{
            ordinamentoAncoraDaSalvare = "DESC";
            setOrdinamento(ordinamentoAncoraDaSalvare);
        }
    }
    public void gestisciSwitchCategorie(Switch RicercaCliccata){
        String nomeRicerca = RicercaCliccata.getText().toString();
        if(RicercaCliccata.isChecked()) {
            categorieAncoraDaSalvare.add(nomeRicerca);
        }else{
            categorieAncoraDaSalvare.remove(nomeRicerca);
        }
    }

    public void setChiudiPopUp(Boolean b) {
        this.chiudiPopUp.setValue(b);
    }

    public void salva(){
        setCategorieScelte(categorieAncoraDaSalvare);
        setOrdinamentoScelto(ordinamentoAncoraDaSalvare);
        setChiudiPopUp(true);
    }
    public void chiudi(){
        setChiudiPopUp(true);
    }
    public void resetPerPopUp(){
        setChiudiPopUp(false);
        setNoCategorie(false);
        setCategorie(null);
        setOrdinamento(null);
        categorieAncoraDaSalvare = null;
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
    public void convertiAsteInglese(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaIngleseRicerca());
        setListaAstaIngleseRicercaConvertite(lista);
    }
    public void convertiAsteRibasso(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaRibassoRicerca());
        setListaAstaRibassoRicercaConvertite(lista);
    }
    public void convertiAsteInversa(){
        ArrayList<Object> lista = new ArrayList<>(getListaAstaInversaRicerca());
        setListaAstaInversaRicercaConvertite(lista);
    }
    public void getAsteRicerca(String nomeRicercato){
        if(getIsAcquirente()){
            cercaAsteInglesi(nomeRicercato);
        }else{
            cercaAsteInversa(nomeRicercato);
        }
    }
    public void cercaAsteInglesi(String nomeRicercato){
        if(ordinamentoScelto==null){
            ordinamentoScelto = "ASC";
        }
        astaAllingleseRepository.getAstePerRicerca(nomeRicercato, categorieScelte, ordinamentoScelto, new Asta_allingleseRepository.OnGetAstePerRicercaListener() {
            @Override
            public void OnGetAstePerRicerca(ArrayList<Asta_allingleseModel> list) {
                setListaAstaIngleseRicerca(list);
                cercaAsteRibasso(nomeRicercato);
            }
        });
    }
    public void cercaAsteRibasso(String nomeRicercato){
        if(ordinamentoScelto==null){
            ordinamentoScelto = "ASC";
        }
        astaAlribassoRepository.getAstePerRicerca(nomeRicercato, categorieScelte, ordinamentoScelto, new Asta_alribassoRepository.OnGetAstePerRicercaListener() {
            @Override
            public void OnGetAstePerRicerca(ArrayList<Asta_alribassoModel> list) {
                setListaAstaRibassoRicerca(list);
            }
        });
    }
    public void cercaAsteInversa(String nomeRicercato){
        if(ordinamentoScelto==null){
            ordinamentoScelto = "ASC";
        }
        astaInversaRepository.getAstePerRicerca(nomeRicercato, categorieScelte, ordinamentoScelto, new Asta_inversaRepository.OnGetAstePerRicercaListener() {
            @Override
            public void OnGetAstePerRicerca(ArrayList<Asta_inversaModel> list) {
                setListaAstaInversaRicerca(list);
            }
        });
    }
    public void checkTipoUtente(){
        if(repository.getAcquirenteModel()!=null){
            setIsAcquirente(true);
        }else{
            setIsAcquirente(false);
        }
    }
    public void setApriFiltro(Boolean apriFiltro) {
        this.apriFiltro.setValue(apriFiltro);
    }
    public void apriFiltro(){
        setApriFiltro(true);
    }
}
