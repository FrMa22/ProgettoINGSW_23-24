package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.LeMieAsteRepository;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.repository.SchermataPartecipazioneAsteRepository;

import java.util.ArrayList;
import java.util.List;

public class SchermataPartecipazioneAsteViewModel  extends ViewModel {


    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> visualizzaAste = new MutableLiveData<>(false);

    public MutableLiveData<AcquirenteModel> acquirenteRecuperato =new MutableLiveData<>(null);
    public MutableLiveData<VenditoreModel> venditoreRecuperato =new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<Object>> asteRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<List<Asta_allingleseModel>> asteInglesiRecuperate = new MutableLiveData<>(null);

    public MutableLiveData<List<Asta_inversaModel>> asteInverseRecuperate = new MutableLiveData<>(null);


    public MutableLiveData<Boolean> entraInSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaRibasso = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> entraInSchermataAstaInversa = new MutableLiveData<>(false);


    private SchermataPartecipazioneAsteRepository schermataPartecipazioneAsteRepository;
    private Repository repository;

    // Aggiungi tre booleani di controllo
    private boolean isAsteInglesiRecuperate = false;
    private boolean isAsteInverseRecuperate = false;

    public SchermataPartecipazioneAsteViewModel(){
        repository = Repository.getInstance();
        schermataPartecipazioneAsteRepository=new SchermataPartecipazioneAsteRepository();
    }


    public void trovaAsteViewModel(){
        try{
                Log.d("trovaAsteViewModel","utente attuale");
                if (containsAcquirente()) {
                    String email = getAcquirenteEmail();
                    trovaAsteInglesi(email);
//                    trovaAsteInverse(email);

                } else if (containsVenditore()) {
                    String email = getVenditoreEmail();
//                    trovaAsteInglesi(email);
                    trovaAsteInverse(email);
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }









    private void checkAndMergeAsteAcquirente() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInglesiRecuperate) {
            System.out.println("tutte le ricerche sono finite");
            mergeAsteTrovate();
        }
    }

    private void checkAndMergeAsteVenditore() {
        // Controlla se tutte e tre le ricerche sono completate
        if (isAsteInverseRecuperate) {
            System.out.println("tutte le ricerche sono finite");
            mergeAsteTrovate();
        }
    }





    private void trovaAsteInglesi(String email) {

        System.out.println("entrato in trova aste inglesi   di view model");
        schermataPartecipazioneAsteRepository.trovaAsteInglesiBackend(email, new SchermataPartecipazioneAsteRepository.OnTrovaAsteInglesiListener() {
            @Override
            public void onTrovaAsteInglesi(List<Asta_allingleseModel> asteInglesiRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInglesiRecuperateList(asteInglesiRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInglesiRecuperate = true;
                checkAndMergeAsteAcquirente();

            }
        });
    }






    private void trovaAsteInverse(String email) {
        System.out.println("entrato in trova aste inverse   di view model");
        schermataPartecipazioneAsteRepository.trovaAsteInverseBackend(email, new SchermataPartecipazioneAsteRepository.OnTrovaAsteInverseListener() {
            @Override
            public void onTrovaAsteInverse(List<Asta_inversaModel> asteInverseRecuperateList) {
                // repository.setAsteAperteRecuperateList(asteAperteRecuperateList);//non so se serve visto che sono acchiappate dal viewModel
                setAsteInverseRecuperateList(asteInverseRecuperateList);//cosi poi la gui "acchiappa" le aste trovate
                // Imposta il flag su true quando la ricerca è completata
                isAsteInverseRecuperate = true;
                checkAndMergeAsteVenditore();
            }
        });
    }

//




    //merge di tutte e 3 le liste di aste
    public  void mergeAsteTrovate(){
        System.out.println("in merge aste  trovate");
        List<Asta_allingleseModel> asteInglesi=getAsteInglesiRecuperateList();
        List<Asta_inversaModel> asteInverse=getAsteInverseRecuperateList();
        ArrayList<Object> aste=new ArrayList<>();
        //get delle liste e le mette in variabili
        if(asteInglesi!=null && !asteInglesi.isEmpty()){aste.addAll(asteInglesi);System.out.println("aggiunte aste inglesi");}
        if(asteInverse!=null && !asteInverse.isEmpty()){aste.addAll(asteInverse);System.out.println("aggiunte aste inverse");}
        //setta poi la variabile osservata
        System.out.println("Finito il merge");
        setAsteRecuperateList(aste);
        isAsteInglesiRecuperate =false;
        isAsteInverseRecuperate =false;
    }






    public void resetAsteRecuperate() {
        asteRecuperate.setValue(null);
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


    public void setVisualizzaAste(Boolean b){
        visualizzaAste.setValue(b);
    }
    public Boolean getVisualizzaAste(){
        return visualizzaAste.getValue();
    }


    public void setAsteRecuperateList(ArrayList<Object> lista){
        asteRecuperate.setValue(lista);
    }


    public void setAsteInglesiRecuperateList(List<Asta_allingleseModel> lista){
        asteInglesiRecuperate.setValue(lista);
    }


    public void setAsteInverseRecuperateList(List<Asta_inversaModel> lista){
        asteInverseRecuperate.setValue(lista);
    }


    //
    public List<Asta_allingleseModel> getAsteInglesiRecuperateList(){
        return asteInglesiRecuperate.getValue();
    }



    public List<Asta_inversaModel> getAsteInverseRecuperateList(){
        return asteInverseRecuperate.getValue();
    }
//



    public void setAstaIngleseSelezionata(Asta_allingleseModel asta) {
        repository.setAsta_allingleseSelezionata(asta);
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

        } else if(asta instanceof Asta_inversaModel){
            repository.setAsta_inversaSelezionata((Asta_inversaModel) asta);
            setEntraInSchermataAstaInversa(true);

        }
    }


}
