package com.example.progettoingsw.viewmodel;


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





    public void trovaAsteAperteViewModel(){
        try{
            if(repository.getLeMieAsteUtenteAttuale()) {
                if (containsAcquirente()) {
                    String email = getAcquirenteEmail();
                    trovaAsteInverseAperte(email);

                } else if (containsVenditore()) {
                    String email = getVenditoreEmail();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);

                }
            }else{
                if(repository.getAcquirenteEmailDaAsta()!=null){
                    String email = repository.getAcquirenteEmailDaAsta();
                    trovaAsteInverseAperte(email);
                }else if(repository.getVenditoreEmailDaAsta()!=null){
                    String email = repository.getVenditoreEmailDaAsta();
                    trovaAsteInglesiAperte(email);
                    trovaAsteRibassoAperte(email);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }



    public void trovaAsteChiuseViewModel(){
        try{
            if(repository.getLeMieAsteUtenteAttuale()) {
                if (containsAcquirente()) {
                    String email = getAcquirenteEmail();
                    trovaAsteInverseChiuse(email);

                } else if (containsVenditore()) {
                    String email = getVenditoreEmail();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                }
            }else{
                if(repository.getAcquirenteEmailDaAsta()!=null){
                    String email = repository.getAcquirenteEmailDaAsta();
                    trovaAsteInverseChiuse(email);
                }else if(repository.getVenditoreEmailDaAsta()!=null){
                    String email = repository.getVenditoreEmailDaAsta();
                    trovaAsteInglesiChiuse(email);
                    trovaAsteRibassoChiuse(email);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }



    private void checkAndMergeAsteVenditoreAperte() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInglesiRecuperateAperte && isAsteRibassoRecuperateAperte) {
            mergeAsteAperteTrovate();
        }
    }


    private void checkAndMergeAsteAcquirenteAperte() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInverseRecuperateAperte) {
            mergeAsteAperteTrovate();
        }
    }



    private void checkAndMergeAsteVenditoreChiuse() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInglesiRecuperateChiuse && isAsteRibassoRecuperateChiuse) {
            mergeAsteChiuseTrovate();
        }
    }

    private void checkAndMergeAsteAcquirenteChiuse() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInverseRecuperateChiuse) {
            mergeAsteChiuseTrovate();
        }
    }



    private void trovaAsteInglesiAperte(String email) {

        leMieAsteRepository.trovaAsteInglesiAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteInglesiAperteListener() {
            @Override
            public void onTrovaAsteInglesiAperte(List<Asta_allingleseModel> asteInglesiAperteRecuperateList) {
                setAsteInglesiAperteRecuperateList(asteInglesiAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInglesiRecuperateAperte = true;
                checkAndMergeAsteVenditoreAperte();

            }
        });
    }



    private void trovaAsteRibassoAperte(String email) {
        leMieAsteRepository.trovaAsteRibassoAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteRibassoAperteListener() {
            @Override
            public void onTrovaAsteRibassoAperte(List<Asta_alribassoModel> asteRibassoAperteRecuperateList) {
                setAsteRibassoAperteRecuperateList(asteRibassoAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteRibassoRecuperateAperte = true;
                checkAndMergeAsteVenditoreAperte();
            }
        });
    }


    private void trovaAsteInverseAperte(String email) {
        leMieAsteRepository.trovaAsteInverseAperteBackend(email, new LeMieAsteRepository.OnTrovaAsteInverseAperteListener() {
            @Override
            public void onTrovaAsteInverseAperte(List<Asta_inversaModel> asteInverseAperteRecuperateList) {
                setAsteInverseAperteRecuperateList(asteInverseAperteRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInverseRecuperateAperte = true;
                checkAndMergeAsteAcquirenteAperte();
            }
        });
    }

//

    private void trovaAsteInglesiChiuse(String email) {
        leMieAsteRepository.trovaAsteInglesiChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteInglesiChiuseListener() {
            @Override
            public void onTrovaAsteInglesiChiuse(List<Asta_allingleseModel> asteInglesiChiuseRecuperateList) {
                setAsteInglesiChiuseRecuperateList(asteInglesiChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInglesiRecuperateChiuse = true;
                checkAndMergeAsteVenditoreChiuse();
            }
        });
    }



    private void trovaAsteRibassoChiuse(String email) {
        leMieAsteRepository.trovaAsteRibassoChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteRibassoChiuseListener() {
            @Override
            public void onTrovaAsteRibassoChiuse(List<Asta_alribassoModel> asteRibassoChiuseRecuperateList) {
                setAsteRibassoChiuseRecuperateList(asteRibassoChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteRibassoRecuperateChiuse = true;
                checkAndMergeAsteVenditoreChiuse();
            }
        });
    }


    private void trovaAsteInverseChiuse(String email) {
        leMieAsteRepository.trovaAsteInverseChiuseBackend(email, new LeMieAsteRepository.OnTrovaAsteInverseChiuseListener() {
            @Override
            public void onTrovaAsteInverseChiuse(List<Asta_inversaModel> asteInverseChiuseRecuperateList) {
                setAsteInverseChiuseRecuperateList(asteInverseChiuseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInverseRecuperateChiuse = true;
                checkAndMergeAsteAcquirenteChiuse();
            }
        });
    }









    //merge di tutte e 3 le liste di aste
    public  void mergeAsteAperteTrovate(){
        List<Asta_allingleseModel> asteInglesi=getAsteInglesiAperteRecuperateList();
        List<Asta_alribassoModel> asteRibasso=getAsteRibassoAperteRecuperateList();
        List<Asta_inversaModel> asteInverse=getAsteInverseAperteRecuperateList();
        ArrayList<Object> asteAperte=new ArrayList<>();
        //get delle liste e le mette in variabili
        if(asteInglesi!=null && !asteInglesi.isEmpty()){
            asteAperte.addAll(asteInglesi);
        }
        if(asteRibasso!=null && !asteRibasso.isEmpty()) {
            asteAperte.addAll(asteRibasso);
        }
        if(asteInverse!=null && !asteInverse.isEmpty()){
            asteAperte.addAll(asteInverse);
        }
        //setta poi la variabile osservata
        setAsteAperteRecuperateList(asteAperte);
        isAsteInglesiRecuperateAperte=false;
        isAsteRibassoRecuperateAperte=false;
        isAsteInverseRecuperateAperte=false;
    }



    public  void mergeAsteChiuseTrovate(){
        List<Asta_allingleseModel> asteInglesi=getAsteInglesiChiuseRecuperateList();
        List<Asta_alribassoModel> asteRibasso=getAsteRibassoChiuseRecuperateList();
        List<Asta_inversaModel> asteInverse=getAsteInverseChiuseRecuperateList();
        ArrayList<Object> asteChiuse=new ArrayList<>();
        //get delle liste e le mette in variabili
        if(asteInglesi!=null && !asteInglesi.isEmpty()){
            asteChiuse.addAll(asteInglesi);
        }
        if(asteRibasso!=null && !asteRibasso.isEmpty()) {
            asteChiuse.addAll(asteRibasso);
        }
        if(asteInverse!=null && !asteInverse.isEmpty()){
            asteChiuse.addAll(asteInverse);
        }
        //setta poi la variabile osservata
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
            setAcquirenteModelPresente();
            setAcquirenteRecuperato(getAcquirente());
        }else if(containsVenditore()) {
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
