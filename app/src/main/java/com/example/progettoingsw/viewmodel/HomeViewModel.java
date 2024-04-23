package com.example.progettoingsw.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.signature.ObjectKey;
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.NotificheRepository;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.SchermataAstaInglese;
import com.example.progettoingsw.view.SchermataAstaInversa;
import com.example.progettoingsw.view.SchermataAstaRibasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeViewModel extends ViewModel {
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Asta_inversaRepository astaInversaRepository;
    private NotificheRepository notificheRepository;
    private Repository repository;
    public MutableLiveData<Boolean> aste_allingleseInScadenzaPresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_allingleseInScadenzaRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> aste_allingleseCategoriaNomePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_allingleseCategoriaNomeRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> aste_allingleseNuovePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_allingleseNuoveRecuperate = new MutableLiveData<>(null);


    public MutableLiveData<Boolean> aste_alribassoCategoriaNomePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_alribassoCategoriaNomeRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> aste_alribassoNuovePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_alribassoNuoveRecuperate = new MutableLiveData<>(null);


    public MutableLiveData<Boolean> aste_inversaNuovePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_inversaNuoveRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> aste_inversaCategoriaNomePresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_inversaCategoriaNomeRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> aste_inversaInScadenzaPresenti = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<Object>> aste_inversaInScadenzaRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);

    public MutableLiveData<Integer> numeroNotifiche = new MutableLiveData<>(0);

    private ArrayList<Asta_allingleseModel> listAsta_allingleseScadenzaRecente = new ArrayList<>();
    private ArrayList<Asta_allingleseModel> listAsta_allingleseNuove = new ArrayList<>();
    private ArrayList<Asta_inversaModel> listAsta_inversaScadenzaRecente = new ArrayList<>();
    private ArrayList<Asta_alribassoModel> listAsta_alribassoNuove = new ArrayList<>();
    public HomeViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaAlribassoRepository = new Asta_alribassoRepository();
        astaInversaRepository = new Asta_inversaRepository();
        notificheRepository = new NotificheRepository();
    }

    public void checkTipoUtente(){
        checkNumeroNotifiche();
        if(containsAcquirente()){
            setAcquirenteModelPresente();
        }else if(containsVenditore()) {
            setVenditoreModelPresente();
        }

    }
    public Integer getNumeroNotifiche() {
        return numeroNotifiche.getValue();
    }
    public void setNumeroNotifiche(Integer numeroNotifiche) {
        this.numeroNotifiche.setValue(numeroNotifiche);
    }
    public void checkNumeroNotifiche(){
        if(containsAcquirente()){
            notificheRepository.getNumeroNotificheAcquirente(repository.getAcquirenteModel().getIndirizzo_email(), new NotificheRepository.OnGetNumeroNotificheAcquirenteListener() {
                @Override
                public void OnGetNumeroNotificheAcquirente(Integer result) {
                    if(result>0){
                        setNumeroNotifiche(result);
                    }
                }
            });
        }else{
            notificheRepository.getNumeroNotificheVenditore(repository.getVenditoreModel().getIndirizzo_email(), new NotificheRepository.OnGetNumeroNotificheVenditoreListener() {
                @Override
                public void OnGetNumeroNotificheVenditore(Integer result) {
                    if(result>0){
                        setNumeroNotifiche(result);
                    }
                }
            });
        }
    }
    public void trovaEImpostaAste(){
        if(repository.getAcquirenteModel()!=null){
            Log.d("HomeViewModel ", "entrato come acquirente");
            try {
                trovaAste_allingleseScadenzaRecente();
                trovaAste_allingleseNuove();
                //trovaAste_alribassoNuove();
                ArrayList<String> listaCategorieAcquirente = getListaCategorieAcquirente();
                Log.d("trova e imposta aste", "lista categorie acquirente: " + listaCategorieAcquirente);
                if (listaCategorieAcquirente != null && !listaCategorieAcquirente.isEmpty()) {
                    for (String categoria : listaCategorieAcquirente) {
                        Log.d("trova e imposta aste", "cercando aste per categoria " + categoria);
                    }
                        trovaAste_allingleseCategoriaNome(listaCategorieAcquirente);
                        //trovaAste_alribassoCategoriaNome(categoria);

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }else if(repository.getVenditoreModel()!=null){
            Log.d("HomeViewModel ", "entrato come venditore");
            try {
                trovaAste_inversaScadenzaRecente();
                trovaAste_inverseNuove();
                ArrayList<String> listaCategorieVenditore = getListaCategorieVenditore();
                Log.d("trova e imposta aste", "lista categorie venditore: " + listaCategorieVenditore);
                if (listaCategorieVenditore != null && !listaCategorieVenditore.isEmpty()) {
                    for (String categoria : listaCategorieVenditore) {
                        Log.d("trova e imposta aste", "cercando aste per categoria " + categoria);
                    }
                    trovaAste_inversaCategoriaNome(listaCategorieVenditore);

                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //metodi per trovare le aste all'inglese
    private void trovaAste_allingleseScadenzaRecente() {
        astaAllingleseRepository.getAste_allingleseScadenzaRecenteBackend(new Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener() {
            @Override
            public void OnGetAsteScadenzaRecente(ArrayList<Asta_allingleseModel> list) {
                repository.setListaAsteAllIngleseInScadenza(list);
                if(list != null && !list.isEmpty()){
                    setAste_allingleseInScadenzaPresenti(true);
                }
            }
        });
    }
    private void trovaAste_allingleseNuove() {
        astaAllingleseRepository.getAste_allingleseNuoveBackend(new Asta_allingleseRepository.OnGetAsteNuoveListener() {
            @Override
            public void OnGetAsteNuove(ArrayList<Asta_allingleseModel> list) {
                repository.setListaAsteAllIngleseNuove(list);
                if(list != null && !list.isEmpty()){
                    setAste_allingleseNuovePresenti(true);
                }
                trovaAste_alribassoNuove();
            }
        });
    }
    private void trovaAste_allingleseCategoriaNome(ArrayList<String> nomiCategorie) {
        Log.d("Cerco aste inglesi per categorie", ""+nomiCategorie.get(0) + nomiCategorie.get(1));
        astaAllingleseRepository.getAste_allingleseCategoriaNomeBackend(nomiCategorie, new Asta_allingleseRepository.OnGetAsteCategoriaNomeListener() {
            @Override
            public void OnGetAsteCategoriaNome(ArrayList<Asta_allingleseModel> list) {
                Log.d("aste recuperate inglesi categoria" ,""+list);
                repository.setListaAsteAllIngleseCategoriaNome(list);
                if(list != null && !list.isEmpty()){
                    setAste_allingleseCategoriaNomePresenti(true);
                }
                trovaAste_alribassoCategoriaNome(nomiCategorie);
            }
        });
    }

    //metodi per trovare le aste al ribasso
    private void trovaAste_alribassoCategoriaNome(ArrayList<String> nomiCategorie) {
        astaAlribassoRepository.getAste_alribassoCategoriaNomeBackend(nomiCategorie, new Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener() {
            @Override
            public void OnGetAsteRibassoCategoriaNome(ArrayList<Asta_alribassoModel> list) {
                Log.d("aste recuperate ribasso categoria" ,""+list);
                repository.setListaAsteAlRibassoCategoriaNome(list);
                if(list != null && !list.isEmpty()){
                    setAste_alribassoCategoriaNomePresenti(true);
                }
            }
        });
    }
    private void trovaAste_alribassoNuove() {
        astaAlribassoRepository.getAste_alribassoNuoveBackend(new Asta_alribassoRepository.OnGetAsteRibassoNuoveListener() {
            @Override
            public void OnGetAsteRibassoNuove(ArrayList<Asta_alribassoModel> list) {
                repository.setListaAsteAlRibassoNuove(list);
                if(list != null && !list.isEmpty()){
                    setAste_alribassoNuovePresenti(true);
                }
            }
        });
    }

    //metodi per trovare le aste inverse
    private void trovaAste_inversaScadenzaRecente() {
        astaInversaRepository.getAste_inversaScadenzaRecenteBackend(new Asta_inversaRepository.OnGetAsteScadenzaRecenteListener() {
            @Override
            public void OnGetAsteScadenzaRecente(ArrayList<Asta_inversaModel> list) {
                listAsta_inversaScadenzaRecente = list;
                repository.setListaAsteInversaInScadenza(list);
                if(listAsta_inversaScadenzaRecente != null && !listAsta_inversaScadenzaRecente.isEmpty()){
                    setAste_inversaInScadenzaPresenti(true);
                }
            }
        });
    }
    private void trovaAste_inverseNuove() {
        astaInversaRepository.getAste_inversaNuoveBackend(new Asta_inversaRepository.OnGetAsteInversaNuoveListener() {
            @Override
            public void OnGetAsteInversaNuove(ArrayList<Asta_inversaModel> list) {
                repository.setListaAsteInversaNuove(list);
                if(list != null && !list.isEmpty()){
                    setAste_inversaNuovePresenti(true);
                }
            }
        });
    }
    private void trovaAste_inversaCategoriaNome(ArrayList<String> nomiCategorie ) {
        astaInversaRepository.getAste_inversaCategoriaNomeBackend(nomiCategorie, new Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener() {
            @Override
            public void OnGetAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> list) {
                repository.setListaAsteInversaCategoriaNome(list);
                if(list != null && !list.isEmpty()){
                    setAste_inversaCategoriaNomePresenti(true);
                }
            }
        });
    }


    public boolean containsAcquirente() {
        return repository.getAcquirenteModel()!=null;
    }
    public ArrayList<String> getListaCategorieAcquirente(){
        Log.d("In home view model", "getListaCategoriaAcquirente con lista " + repository.getListaCategorieAcquirente());
        return repository.getListaCategorieAcquirente();
    }
    public Boolean containsVenditore(){
        return repository.getVenditoreModel()!=null;
    }

    public ArrayList<String> getListaCategorieVenditore() {


        return repository.getListaCategorieVenditore();
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


    //metodi per aste all'inglese
    private void setAste_allingleseInScadenzaPresenti(boolean b) {
        aste_allingleseInScadenzaPresenti.setValue(true);
    }
    public Boolean getAste_allingleseInScadenzaPresenti() {
        return aste_allingleseInScadenzaPresenti.getValue();
    }
    public void setAste_allingleseNuovePresenti(Boolean b){
        aste_allingleseNuovePresenti.setValue(b);
    }
    public Boolean getAste_allingleseNuovePresenti(){
        return aste_allingleseNuovePresenti.getValue();
    }
    public void setAste_allingleseCategoriaNomePresenti(Boolean b){
        aste_allingleseCategoriaNomePresenti.setValue(b);
    }
    public Boolean getAste_allingleseCategoriaNomePresenti(){
        return aste_allingleseCategoriaNomePresenti.getValue();
    }
    public ArrayList<Asta_allingleseModel> getListaAsta_allingleseScadenzaRecente(){
        Log.d("getListaAsta_allingleseScadenzaRecente", "asta: "  + repository.getListaAsteAllIngleseInScadenza().size());
        return repository.getListaAsteAllIngleseInScadenza();
    }
    public ArrayList<Asta_allingleseModel> getListaAsta_allingleseCategoriaNome(){
        Log.d("aste inglesi per categoria", "size: " + repository.getListaAsteAllIngleseCategoriaNome().size());
        return repository.getListaAsteAllIngleseCategoriaNome();
    }
    public ArrayList<Asta_allingleseModel> getListaAsta_allingleseNuove(){
        return repository.getListaAsteAllIngleseNuove();
    }
    public void setEntraInSchermataAstaInglese(Boolean b){
        entraInSchermataAstaInglese.setValue(b);
    }
    public Boolean getEntraInSchermataAstaInglese(){
        return entraInSchermataAstaInglese.getValue();
    }

    //metodi per le aste al ribasso
    public void setAste_alribassoNuovePresenti(Boolean b){
        aste_alribassoNuovePresenti.setValue(true);
    }
    public Boolean getAste_alribassoNuovePresenti(){
        return aste_alribassoNuovePresenti.getValue();
    }
    public ArrayList<Asta_alribassoModel> getListaAsta_alribassoNuove(){
        return repository.getListaAsteAlRibassoNuove();
    }
    public Boolean getAste_alribassoCategoriaNomePresenti(){
        return aste_alribassoCategoriaNomePresenti.getValue();
    }
    public void setAste_alribassoCategoriaNomePresenti(Boolean b){
        aste_alribassoCategoriaNomePresenti.setValue(b);
    }
    public ArrayList<Asta_alribassoModel> getListaAsta_alribassoCategoriaNome(){
        return repository.getListaAsteAlRibassoCategoriaNome();
    }
    public void setEntraInSchermataAstaRibasso(Boolean b){
        entraInSchermataAstaRibasso.setValue(b);
    }
    public Boolean getEntraInSchermataAstaRibasso(){
        return entraInSchermataAstaRibasso.getValue();
    }

    //metodi per le aste inverse
    private void setAste_inversaInScadenzaPresenti(boolean b) {
        aste_inversaInScadenzaPresenti.setValue(true);
    }
    public Boolean getAste_inversaInScadenzaPresenti() {
        return aste_inversaInScadenzaPresenti.getValue();
    }
    public ArrayList<Asta_inversaModel> getListaAsta_inversaScadenzaRecente(){
        return repository.getListaAsteInversaInScadenza();
    }
    public void setAste_inversaNuovePresenti(Boolean b){
        aste_inversaNuovePresenti.setValue(b);
    }
    public Boolean getAste_inversaNuovePresenti(){
        return aste_inversaNuovePresenti.getValue();
    }
    public ArrayList<Asta_inversaModel> getListaAsta_inversaNuove(){
        return repository.getListaAsteInversaNuove();
    }
    public void setAste_inversaCategoriaNomePresenti(Boolean b){
        aste_inversaCategoriaNomePresenti.setValue(b);
    }
    public Boolean getAste_inversaCategoriaNomePresenti(){
        return aste_inversaCategoriaNomePresenti.getValue();
    }
    public ArrayList<Asta_inversaModel> getListaAsta_inversaCategoriaNome(){
        return repository.getListaAsteInversaCategoriaNome();
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

//            int id = Math.toIntExact(((Asta_allingleseModel) asta).getId());
//            Log.d("Asta inglese", "id è " + id);
//            Intent intent = new Intent(getContext(), SchermataAstaInglese.class);//test del login
//            intent.putExtra("email", email);
//            intent.putExtra("tipoUtente", tipoUtente);
//            intent.putExtra("id", id);
//            startActivity(intent);
        }else if(asta instanceof Asta_alribassoModel){
            repository.setAsta_alribassoSelezionata((Asta_alribassoModel) asta);
            setEntraInSchermataAstaRibasso(true);

//            int id = Math.toIntExact(((Asta_alribassoModel) asta).getId());
//            Log.d("Asta inglese", "id è " + id);
//            Intent intent = new Intent(getContext(), SchermataAstaInglese.class);//test del login
//            intent.putExtra("email", email);
//            intent.putExtra("tipoUtente", tipoUtente);
//            intent.putExtra("id", id);
//            startActivity(intent);
        }else if(asta instanceof Asta_inversaModel){
            repository.setAsta_inversaSelezionata((Asta_inversaModel) asta);
            setEntraInSchermataAstaInversa(true);

//            int id = Math.toIntExact(((Asta_inversaModel) asta).getId());
//            Log.d("Asta inglese", "id è " + id);
//            Intent intent = new Intent(getContext(), SchermataAstaInglese.class);//test del login
//            intent.putExtra("email", email);
//            intent.putExtra("tipoUtente", tipoUtente);
//            intent.putExtra("id", id);
//            startActivity(intent);
        }
    }
    public void setAste_allingleseCategoriaNomeRecuperate(ArrayList<Object> lista){
        aste_allingleseCategoriaNomeRecuperate.setValue(lista);
    }
    public void recuperaAste_allingleseCategorieNome(){
        ArrayList<Asta_allingleseModel> listaAsteCategoriaNome = getListaAsta_allingleseCategoriaNome();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteCategoriaNome);
        setAste_allingleseCategoriaNomeRecuperate(listaOggetti);
    }
    public void setAste_allingleseInScadenzaRecuperate(ArrayList<Object> lista){
        aste_allingleseInScadenzaRecuperate.setValue(lista);
    }
    public void recuperaAste_allingleseInScadenza(){
        ArrayList<Asta_allingleseModel> listaAsteInScadenza = getListaAsta_allingleseScadenzaRecente();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteInScadenza);
        setAste_allingleseInScadenzaRecuperate(listaOggetti);
    }
    public void setAste_allingleseNuoveRecuperate(ArrayList<Object> lista){
        aste_allingleseNuoveRecuperate.setValue(lista);
    }
    public void recuperaAste_allingleseNuove(){
        ArrayList<Asta_allingleseModel> listaAsteNuove = getListaAsta_allingleseNuove();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteNuove);
        setAste_allingleseNuoveRecuperate(listaOggetti);
    }


    public void setAste_alribassoCategoriaNomeRecuperate(ArrayList<Object> lista){
        aste_alribassoCategoriaNomeRecuperate.setValue(lista);
    }
    public void recuperaAste_alribassoCategorieNome(){
        ArrayList<Asta_alribassoModel> listaAsteCategoriaNome = getListaAsta_alribassoCategoriaNome();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteCategoriaNome);
        setAste_alribassoCategoriaNomeRecuperate(listaOggetti);
    }
    public void setAste_alribassoNuoveRecuperate(ArrayList<Object> lista){
        aste_alribassoNuoveRecuperate.setValue(lista);
    }
    public void recuperaAste_alribassoNuove(){
        ArrayList<Asta_alribassoModel> listaAsteNuove = getListaAsta_alribassoNuove();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteNuove);
        setAste_alribassoNuoveRecuperate(listaOggetti);
    }


    public void setAste_inversaInScadenzaRecuperate(ArrayList<Object> lista){
        aste_inversaInScadenzaRecuperate.setValue(lista);
    }
    public void recuperaAste_inversaInScadenza(){
        ArrayList<Asta_inversaModel> listaAsteInScadenza = getListaAsta_inversaScadenzaRecente();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteInScadenza);
        setAste_inversaInScadenzaRecuperate(listaOggetti);
    }
    public void setAste_inversaNuoveRecuperate(ArrayList<Object> lista){
        aste_inversaNuoveRecuperate.setValue(lista);
    }
    public void recuperaAste_inversaNuove(){
        ArrayList<Asta_inversaModel> listaAsteNuove = getListaAsta_inversaNuove();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteNuove);
        setAste_inversaNuoveRecuperate(listaOggetti);
    }
    public void setAste_inversaCategoriaNomeRecuperate(ArrayList<Object> lista){
        aste_inversaCategoriaNomeRecuperate.setValue(lista);
    }
    public void recuperaAste_inversaCategorieNome(){
        ArrayList<Asta_inversaModel> listaAsteCategoriaNome = getListaAsta_inversaCategoriaNome();
        ArrayList<Object> listaOggetti = new ArrayList<>(listaAsteCategoriaNome);
        setAste_inversaCategoriaNomeRecuperate(listaOggetti);
    }
}
