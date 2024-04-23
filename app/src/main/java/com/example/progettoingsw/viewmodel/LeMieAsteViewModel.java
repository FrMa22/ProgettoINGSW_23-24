package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.LeMieAsteRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class LeMieAsteViewModel extends ViewModel {



    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> visualizzaAsteAperte = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> visualizzaAsteChiuse = new MutableLiveData<>(false);

    public MutableLiveData<AcquirenteModel> acquirenteRecuperato =new MutableLiveData<>(null);

    public MutableLiveData<VenditoreModel> venditoreRecuperato =new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> asteAperteRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> asteChiuseRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<List<Asta_allingleseModel>> asteInglesiAperteRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<List<Asta_alribassoModel>> asteRibassoAperteRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<List<Asta_inversaModel>> asteInverseAperteRecuperate = new MutableLiveData<>(null);


    public MutableLiveData<List<Asta_allingleseModel>> asteInglesiChiuseRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<List<Asta_alribassoModel>> asteRibassoChiuseRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<List<Asta_inversaModel>> asteInverseChiuseRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);


    private LeMieAsteRepository leMieAsteRepository;
    private Repository repository;

    // Aggiungi tre booleani di controllo
    private boolean isAsteInglesiRecuperateAperte = false;
    private boolean isAsteRibassoRecuperateAperte = false;
    private boolean isAsteInverseRecuperateAperte = false;

    private boolean isAsteInglesiRecuperateChiuse = false;
    private boolean isAsteRibassoRecuperateChiuse = false;
    private boolean isAsteInverseRecuperateChiuse = false;

    public LeMieAsteViewModel(){
        repository = Repository.getInstance();
        leMieAsteRepository = new LeMieAsteRepository();
    }


//conviene fare che questo tipo di metodo sia fatto 3 volte per tipo di asta da trovare



    public void trovaAsteAperteViewModel(){
        try{
            if(repository.getLeMieAsteUtenteAttuale()) {
                Log.d("trovaAsteAperteViewModel","utente attuale");
                if (containsAcquirente()) {
                    String email = getAcquirenteEmail();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);
                    trovaAsteInverseAperte(email);

                } else if (containsVenditore()) {
                    String email = getVenditoreEmail();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);
                    trovaAsteInverseAperte(email);

                }
            }else{
                Log.d("trovaAsteAperteViewModel","utente da asta");
                if(repository.getAcquirenteEmailDaAsta()!=null){
                    String email = repository.getAcquirenteEmailDaAsta();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);
                    trovaAsteInverseAperte(email);
                }else if(repository.getVenditoreEmailDaAsta()!=null){
                    String email = repository.getVenditoreEmailDaAsta();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);
                    trovaAsteInverseAperte(email);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


//    public void trovaAsteAperteAcquirenteViewModel(){
//        try{
//            //trova le singole aste dal db poi deve fa la ""fusione dei risultati"
//            trovaAsteInglesiAperte();
//            trovaAsteRibassoAperte();
//            trovaAsteInverseAperte();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }


    public void trovaAsteChiuseViewModel(){
        try{
            if(repository.getLeMieAsteUtenteAttuale()) {
                Log.d("trovaAsteAperteViewModel", "utente attuale");
                if (containsAcquirente()) {
                    String email = getAcquirenteEmail();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                    trovaAsteInverseChiuse(email);

                } else if (containsVenditore()) {
                    String email = getVenditoreEmail();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                    trovaAsteInverseChiuse(email);
                }
            }else{
                Log.d("trovaAsteAperteViewModel","utente da asta");
                if(repository.getAcquirenteEmailDaAsta()!=null){
                    String email = repository.getAcquirenteEmailDaAsta();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                    trovaAsteInverseChiuse(email);
                }else if(repository.getVenditoreEmailDaAsta()!=null){
                    String email = repository.getVenditoreEmailDaAsta();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                    trovaAsteInverseChiuse(email);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

//    public void trovaAsteChiuseAcquirenteViewModel(){
//        try{
//            //trova le singole aste dal db poi deve fa la ""fusione dei risultati"
//            trovaAsteInglesiChiuse();
//            trovaAsteRibassoChiuse();
//            trovaAsteInverseChiuse();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }


    private void checkAndMergeAsteAperte() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInglesiRecuperateAperte && isAsteRibassoRecuperateAperte && isAsteInverseRecuperateAperte) {
            System.out.println("tutte le ricerche sono finite");
            mergeAsteAperteTrovate();
        }
    }

    private void checkAndMergeAsteChiuse() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInglesiRecuperateChiuse && isAsteRibassoRecuperateChiuse && isAsteInverseRecuperateChiuse) {
            System.out.println("tutte le ricerche sono finite");
            mergeAsteChiuseTrovate();
        }
    }


    private void trovaAsteInglesiAperte(String email) {

        System.out.println("entrato in trova aste inglesi aperte  di view model");
        leMieAsteRepository.trovaAsteInglesiAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteInglesiAperteListener() {
            @Override
            public void onTrovaAsteInglesiAperte(List<Asta_allingleseModel> asteInglesiAperteRecuperateList) {
               // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInglesiAperteRecuperateList(asteInglesiAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInglesiRecuperateAperte = true;
                checkAndMergeAsteAperte();

            }
        });
    }



    private void trovaAsteRibassoAperte(String email) {
        System.out.println("entrato in trova aste ribasso aperte di view model");
        leMieAsteRepository.trovaAsteRibassoAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteRibassoAperteListener() {
            @Override
            public void onTrovaAsteRibassoAperte(List<Asta_alribassoModel> asteRibassoAperteRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteRibassoAperteRecuperateList(asteRibassoAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteRibassoRecuperateAperte = true;
                checkAndMergeAsteAperte();
            }
        });
    }


    private void trovaAsteInverseAperte(String email) {
        System.out.println("entrato in trova aste inverse aperte  di view model");
        leMieAsteRepository.trovaAsteInverseAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteInverseAperteListener() {
            @Override
            public void onTrovaAsteInverseAperte(List<Asta_inversaModel> asteInverseAperteRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInverseAperteRecuperateList(asteInverseAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInverseRecuperateAperte = true;
                checkAndMergeAsteAperte();
            }
        });
    }

//

    private void trovaAsteInglesiChiuse(String email) {
        System.out.println("entrato in trova aste inglesi chiuse  di view model");
        leMieAsteRepository.trovaAsteInglesiChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteInglesiChiuseListener() {
            @Override
            public void onTrovaAsteInglesiChiuse(List<Asta_allingleseModel> asteInglesiChiuseRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInglesiChiuseRecuperateList(asteInglesiChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInglesiRecuperateChiuse = true;
                checkAndMergeAsteChiuse();
            }
        });
    }



    private void trovaAsteRibassoChiuse(String email) {
        System.out.println("entrato in trova aste ribasso chiuse  di view model");
        leMieAsteRepository.trovaAsteRibassoChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteRibassoChiuseListener() {
            @Override
            public void onTrovaAsteRibassoChiuse(List<Asta_alribassoModel> asteRibassoChiuseRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteRibassoChiuseRecuperateList(asteRibassoChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteRibassoRecuperateChiuse = true;
                checkAndMergeAsteChiuse();
            }
        });
    }


    private void trovaAsteInverseChiuse(String email) {
        System.out.println("entrato in trova aste inverse chiuse  di view model");
        leMieAsteRepository.trovaAsteInverseChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteInverseChiuseListener() {
            @Override
            public void onTrovaAsteInverseChiuse(List<Asta_inversaModel> asteInverseChiuseRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInverseChiuseRecuperateList(asteInverseChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInverseRecuperateChiuse = true;
                checkAndMergeAsteChiuse();
            }
        });
    }









    //merge di tutte e 3 le liste di aste
    public  void mergeAsteAperteTrovate(){
        System.out.println("in merge aste aperte trovate");
        List<Asta_allingleseModel> asteInglesi=getAsteInglesiAperteRecuperateList();
        List<Asta_alribassoModel> asteRibasso=getAsteRibassoAperteRecuperateList();
        List<Asta_inversaModel> asteInverse=getAsteInverseAperteRecuperateList();
        ArrayList<Object> asteAperte=new ArrayList<>();
        //get delle liste e le mette in variabili
        if(asteInglesi!=null && !asteInglesi.isEmpty()){asteAperte.addAll(asteInglesi);System.out.println("aggiunte aste inglesi");}
        if(asteRibasso!=null && !asteRibasso.isEmpty()) {asteAperte.addAll(asteRibasso);System.out.println("aggiunte aste ribasso");}
        if(asteInverse!=null && !asteInverse.isEmpty()){asteAperte.addAll(asteInverse);System.out.println("aggiunte aste inverse");}
        //setta poi la variabile osservata
        System.out.println("Finito il merge");
        setAsteAperteRecuperateList(asteAperte);
        isAsteInglesiRecuperateAperte=false;
        isAsteRibassoRecuperateAperte=false;
        isAsteInverseRecuperateAperte=false;
    }



    public  void mergeAsteChiuseTrovate(){
        System.out.println("in merge aste chiuse trovate");
        List<Asta_allingleseModel> asteInglesi=getAsteInglesiChiuseRecuperateList();
        List<Asta_alribassoModel> asteRibasso=getAsteRibassoChiuseRecuperateList();
        List<Asta_inversaModel> asteInverse=getAsteInverseChiuseRecuperateList();
        ArrayList<Object> asteChiuse=new ArrayList<>();
        //get delle liste e le mette in variabili
        if(asteInglesi!=null && !asteInglesi.isEmpty()){asteChiuse.addAll(asteInglesi);System.out.println("aggiunte aste inglesi");}
        if(asteRibasso!=null && !asteRibasso.isEmpty()) {asteChiuse.addAll(asteRibasso);System.out.println("aggiunte aste ribasso");}
        if(asteInverse!=null && !asteInverse.isEmpty()){asteChiuse.addAll(asteInverse);System.out.println("aggiunte aste inverse");}
        //setta poi la variabile osservata
        System.out.println("Finito il merge");
        setAsteChiuseRecuperateList(asteChiuse);
        isAsteInglesiRecuperateChiuse=false;
        isAsteRibassoRecuperateChiuse=false;
        isAsteInverseRecuperateChiuse=false;
    }


    public void resetAsteAperteRecuperate() {
        asteAperteRecuperate.setValue(null);
    }

    public void resetAsteChiuseRecuperate() {
        asteChiuseRecuperate.setValue(null);
    }





    private void setAcquirenteModelPresente() {
        acquirenteModelPresente.setValue(true);
    }
    public Boolean getAcquirenteModelPresente(){
        return acquirenteModelPresente.getValue();
    }


    public boolean containsAcquirente() {
        return repository.getAcquirenteModel()!=null;
    }

    public Boolean containsVenditore(){
        return repository.getVenditoreModel()!=null;
    }

    private void setVenditoreModelPresente() {
        venditoreModelPresente.setValue(true);
    }
    public Boolean getVenditoreModelPresente(){
        return venditoreModelPresente.getValue();
    }









    public void checkTipoUtente(){
        if(containsAcquirente()){
            System.out.println("contains di acquirente");
            setAcquirenteModelPresente();
            setAcquirenteRecuperato(getAcquirente());
        }else if(containsVenditore()) {
            System.out.println("contains di venditore");
            setVenditoreModelPresente();
            setVenditoreRecuperato(getVenditore());
        }



    }




    public String getAcquirenteEmail(){
        return repository.getAcquirenteModel().getIndirizzo_email();
    }

    public String getVenditoreEmail(){
        return repository.getVenditoreModel().getIndirizzo_email();
    }

    public AcquirenteModel getAcquirente(){
        return repository.getAcquirenteModel();
    }

    public VenditoreModel getVenditore(){
        return repository.getVenditoreModel();
    }

    public void setAcquirenteRecuperato(AcquirenteModel acquirenteModel){
        acquirenteRecuperato.setValue(acquirenteModel);
    }

    public void setVenditoreRecuperato(VenditoreModel venditoreModel){
        venditoreRecuperato.setValue(venditoreModel);
    }


    public void setVisualizzaAsteAperte(Boolean b){
        visualizzaAsteAperte.setValue(b);
    }
    public Boolean getVisualizzaAsteAperte(){
        return visualizzaAsteAperte.getValue();
    }

    public void setVisualizzaAsteChiuse(Boolean b){
        visualizzaAsteChiuse.setValue(b);
    }
    public Boolean getVisualizzaAsteChiuse(){
        return visualizzaAsteChiuse.getValue();
    }


    public void setAsteAperteRecuperateList(ArrayList<Object> lista){
        asteAperteRecuperate.setValue(lista);
    }

    public void setAsteChiuseRecuperateList(ArrayList<Object> lista){
        asteChiuseRecuperate.setValue(lista);
    }


    public void setAsteInglesiAperteRecuperateList(List<Asta_allingleseModel> lista){
        asteInglesiAperteRecuperate.setValue(lista);
    }

    public void setAsteRibassoAperteRecuperateList(List<Asta_alribassoModel> lista){
        asteRibassoAperteRecuperate.setValue(lista);
    }

    public void setAsteInverseAperteRecuperateList(List<Asta_inversaModel> lista){
        asteInverseAperteRecuperate.setValue(lista);
    }


    //
    public List<Asta_allingleseModel> getAsteInglesiAperteRecuperateList(){
        return asteInglesiAperteRecuperate.getValue();
    }

    public List<Asta_alribassoModel> getAsteRibassoAperteRecuperateList(){
        return asteRibassoAperteRecuperate.getValue();
    }

    public List<Asta_inversaModel> getAsteInverseAperteRecuperateList(){
       return asteInverseAperteRecuperate.getValue();
    }
//


    public void setAsteInglesiChiuseRecuperateList(List<Asta_allingleseModel> lista){
        asteInglesiChiuseRecuperate.setValue(lista);
    }

    public void setAsteRibassoChiuseRecuperateList(List<Asta_alribassoModel> lista){
        asteRibassoChiuseRecuperate.setValue(lista);
    }

    public void setAsteInverseChiuseRecuperateList(List<Asta_inversaModel> lista){
        asteInverseChiuseRecuperate.setValue(lista);
    }




//
public List<Asta_allingleseModel> getAsteInglesiChiuseRecuperateList(){
    return asteInglesiChiuseRecuperate.getValue();
}

    public List<Asta_alribassoModel> getAsteRibassoChiuseRecuperateList(){
        return asteRibassoChiuseRecuperate.getValue();
    }

    public List<Asta_inversaModel> getAsteInverseChiuseRecuperateList(){
        return asteInverseChiuseRecuperate.getValue();
    }


    public void setAstaIngleseSelezionata(Asta_allingleseModel asta) {
        repository.setAsta_allingleseSelezionata(asta);
    }

    public void setAstaRibassoSelezionata(Asta_alribassoModel asta) {
        repository.setAsta_alribassoSelezionata(asta);
    }

    public void setAstaInversaSelezionata(Asta_inversaModel asta) {
        repository.setAsta_inversaSelezionata(asta);
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
