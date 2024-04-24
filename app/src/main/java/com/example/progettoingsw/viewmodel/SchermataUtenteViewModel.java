package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.FragmentProfiloRepository;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.repository.SchermataUtenteRepository;

import java.util.ArrayList;
import java.util.List;

public class SchermataUtenteViewModel extends ViewModel {
    private Repository repository;
    private SchermataUtenteRepository schermataUtenteRepository;
    private FragmentProfiloRepository fragmentProfiloRepository;

    public MutableLiveData<Boolean> socialAssenti = new MutableLiveData<>(false);

    public MutableLiveData<AcquirenteModel> acquirenteRecuperato = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<SocialAcquirenteModel>> socialAcquirente = new MutableLiveData<>(null);

    public MutableLiveData<VenditoreModel> venditoreRecuperato = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> apriLeMieAste = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<SocialVenditoreModel>> socialVenditore = new MutableLiveData<>(null);

    public MutableLiveData<String> erroreRecupero = new MutableLiveData<>("");

    public SchermataUtenteViewModel(){
        repository = Repository.getInstance();
        schermataUtenteRepository = new SchermataUtenteRepository();
        fragmentProfiloRepository = new FragmentProfiloRepository();
    }
    public void setSocialAssenti(Boolean socialAssenti) {
        this.socialAssenti.setValue(socialAssenti);
    }
    public void getUtenteData(){
        if(repository.getAcquirenteEmailDaAsta()!=null){
            recuperaAcquirente();
        }else{
            recuperaVenditore();
        }
    }

    public void recuperaAcquirente(){
        String email = repository.getAcquirenteEmailDaAsta();
        schermataUtenteRepository.getAcquirenteByIndirizzo_email(email, new SchermataUtenteRepository.OnGetAcquirenteByIndirizzo_emailListener() {
            @Override
            public void onGetAcquirenteByIndirizzo_emailListener(AcquirenteModel acquirenteModel) {
                if(acquirenteModel!=null) {
                    setAcquirenteRecuperato(acquirenteModel);
                    trovaSocialAcquirente(email);
                }else{
                    setErroreRecupero("Errore nel recupero dell'acquirente");
                }
            }
        });
    }

    public void trovaSocialAcquirente(String email){
        fragmentProfiloRepository.trovaSocialAcquirenteBackend(email, new FragmentProfiloRepository.OnFragmentProfiloAcquirenteListener() {
            @Override
            public void onFragmentProfilo(List<SocialAcquirenteModel> socialAcquirenteModelList) {
                if(socialAcquirenteModelList!=null && !socialAcquirenteModelList.isEmpty()) {
                    setSocialAcquirente((ArrayList<SocialAcquirenteModel>) socialAcquirenteModelList);
                }else{
                    setSocialAssenti(true);
                }
            }
        });
    }
    public void recuperaVenditore(){
        String email = repository.getVenditoreEmailDaAsta();
        schermataUtenteRepository.getVenditoreByIndirizzo_email(email, new SchermataUtenteRepository.OnGetVenditoreByIndirizzo_emailListener() {
            @Override
            public void onGetVenditoreByIndirizzo_emailListener(VenditoreModel VenditoreModel) {
                if(VenditoreModel!=null) {
                    setVenditoreRecuperato(VenditoreModel);
                    trovaSocialVenditore(email);
                }else{
                    setErroreRecupero("Errore nel recupero dell'Venditore");
                }
            }
        });
    }

    public void trovaSocialVenditore(String email){
        fragmentProfiloRepository.trovaSocialVenditoreBackend(email, new FragmentProfiloRepository.OnFragmentProfiloVenditoreListener() {
            @Override
            public void onFragmentProfiloVenditore(List<SocialVenditoreModel> socialVenditoreModelList) {
                if(socialVenditoreModelList!=null && !socialVenditoreModelList.isEmpty()) {
                    setSocialVenditore((ArrayList<SocialVenditoreModel>) socialVenditoreModelList);
                }else{
                    setSocialAssenti(true);
                }
            }
        });
    }
    public AcquirenteModel getAcquirenteRecuperato() {
        return acquirenteRecuperato.getValue();
    }
    public void setAcquirenteRecuperato(AcquirenteModel acquirenteRecuperato) {
        this.acquirenteRecuperato.setValue(acquirenteRecuperato);
    }
    public Boolean isAcquirenteRecuperato(){
        return getAcquirenteRecuperato() != null;
    }
    public String getErroreRecupero() {
        return erroreRecupero.getValue();
    }
    public void setErroreRecupero(String erroreRecupero) {
        this.erroreRecupero.setValue(erroreRecupero);
    }
    public ArrayList<SocialAcquirenteModel> getSocialAcquirente() {
        return socialAcquirente.getValue();
    }
    public void setSocialAcquirente(ArrayList<SocialAcquirenteModel> socialAcquirente) {
        this.socialAcquirente.setValue(socialAcquirente);
    }
    public Boolean isSocialAcquirente(){
        return (getSocialAcquirente()!=null && !getSocialAcquirente().isEmpty());
    }
    public VenditoreModel getVenditoreRecuperato() {
        return venditoreRecuperato.getValue();
    }
    public void setVenditoreRecuperato(VenditoreModel venditoreRecuperato) {
        this.venditoreRecuperato.setValue(venditoreRecuperato);
    }
    public Boolean isVenditoreRecuperato(){
        return getVenditoreRecuperato()!=null;
    }
    public ArrayList<SocialVenditoreModel> getSocialVenditore() {
        return socialVenditore.getValue();
    }
    public void setSocialVenditore(ArrayList<SocialVenditoreModel> socialVenditore) {
        this.socialVenditore.setValue(socialVenditore);
    }
    public Boolean isSocialVenditore(){
        return (getSocialVenditore()!=null && !getSocialVenditore().isEmpty());
    }
    public void setApriLeMieAste(Boolean b){
        repository.setLeMieAsteUtenteAttuale(false);
        apriLeMieAste.setValue(b);
    }
    public Boolean getApriLeMieAste(){
        return apriLeMieAste.getValue();
    }
}
