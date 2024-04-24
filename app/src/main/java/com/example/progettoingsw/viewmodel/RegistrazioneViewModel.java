package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.RegistrazioneRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;

public class RegistrazioneViewModel extends ViewModel {
    public MutableLiveData<String> messaggioErroreEmail = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErrorePassword = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreConfermaPassword = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreNome = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreCognome = new MutableLiveData<>("");

    public MutableLiveData<String> messaggioErroreLink = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreNomeSocial = new MutableLiveData<>("");
    public MutableLiveData<String> proseguiRegistrazione = new MutableLiveData<>("");
    public MutableLiveData<String> proseguiInserimento = new MutableLiveData<>("");
    public MutableLiveData<String> proseguiInserimentoSocial = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreBio = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErrorePaese = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreSitoWeb = new MutableLiveData<>("");
    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<AcquirenteModel> acquirenteModel = new MutableLiveData<>();
    public MutableLiveData<VenditoreModel> venditoreModel = new MutableLiveData<>();
    public MutableLiveData<Boolean> apriPopUpSocial = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<SocialAcquirenteModel>> listaSocialAcquirente = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<SocialVenditoreModel>> listaSocialVenditore = new MutableLiveData<>(null);

    public ArrayList<SocialAcquirenteModel> socialAcquirente = new ArrayList<SocialAcquirenteModel>();



    public MutableLiveData<Boolean> socialVuoti = new MutableLiveData<>(false);
    public ArrayList<SocialVenditoreModel> socialVenditore = new ArrayList<SocialVenditoreModel>();
    public MutableLiveData<AcquirenteModel> valoriPresentiAcquirente = new MutableLiveData<>(null);
    public MutableLiveData<VenditoreModel> valoriPresentiVenditore = new MutableLiveData<>(null);
    public MutableLiveData<AcquirenteModel> valoriPresentiFacoltativiAcquirente = new MutableLiveData<>(null);
    public MutableLiveData<VenditoreModel> valoriPresentiFacoltativiVenditore = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> isSocialCambiato = new MutableLiveData<>(false);
    private RegistrazioneRepository registrazioneRepository;
    private Repository repository;

    public RegistrazioneViewModel() {
        repository = Repository.getInstance();
        registrazioneRepository = new RegistrazioneRepository();
    }

    private void setProseguiRegistrazione(String check) {
        proseguiRegistrazione.setValue(check);
    }

    public String getProseguiRegistrazione() {
        return proseguiRegistrazione.getValue();
    }

    public Boolean isProseguiRegistrazione(String check) {
        return getProseguiRegistrazione().equals(check);
    }

    private void setProseguiInserimento(String check) {
        proseguiInserimento.setValue(check);
    }

    public String getProseguiInserimento() {
        return proseguiInserimento.getValue();
    }

    public Boolean isProseguiInserimento(String check) {
        return getProseguiInserimento().equals(check);
    }

    private void setProseguiInserimentoSocial(String check) {
        proseguiInserimentoSocial.setValue(check);
    }

    public String getProseguiInserimentoSocial() {
        return proseguiInserimentoSocial.getValue();
    }

    public Boolean isProseguiInserimentoSocial(String check) {
        return getProseguiInserimentoSocial().equals(check);
    }


    public void checkTipoUtente() {
        if (containsAcquirente()) {
            setAcquirenteModelPresente();
        } else if (containsVenditore()) {
            setVenditoreModelPresente();
        }

    }


    public void checkSocial(String nomeSocial, String link) {
        System.out.println("entrato in registrazione di viewmodel");
        if (socialValido(nomeSocial, link)) {
            boolean socialGiaPresente = false;
                if (containsAcquirente()) {
                    for (SocialAcquirenteModel social : socialAcquirente) {
                        if (social.getNome().equals(nomeSocial) && social.getLink().equals(link)) {
                            setMessaggioErroreLink("social già aggiunto");
                            socialGiaPresente = true;
                            break;
                        }
                    }
                    if(!socialGiaPresente) {
                        SocialAcquirenteModel socialAcquirenteModel = new SocialAcquirenteModel(nomeSocial, link, repository.getAcquirenteModel().getIndirizzo_email());
                        socialAcquirente.add(socialAcquirenteModel);
                        setProseguiInserimentoSocial("inserito");
                    }
                } else {
                    for(SocialVenditoreModel social : socialVenditore) {
                        if (social.getNome().equals(nomeSocial) && social.getLink().equals(link)) {
                            setMessaggioErroreLink("social già aggiunto");
                            socialGiaPresente = true;
                            break;
                        }
                    }
                    if(!socialGiaPresente){
                        SocialVenditoreModel socialVenditoreModel = new SocialVenditoreModel(nomeSocial, link, repository.getVenditoreModel().getIndirizzo_email());
                        socialVenditore.add((socialVenditoreModel));
                        setProseguiInserimentoSocial("inserito");
                    }
            }

        }

    }
public void controlloSocial(){
        if(containsAcquirente()){
            if(!socialAcquirente.isEmpty()){
             inserisciSocialAcquirente();
            }
        } else if (containsVenditore()) {
            if(!socialVenditore.isEmpty()){
          inserisciSocialVenditore();
            }
        }
}
    public void registrazioneAcquirente(String email, String password, String confermaPassword, String nome, String cognome) {
        System.out.println("entrato in registrazione di viewmodel");
        if (registrazioneValida(email, password, confermaPassword, nome, cognome)) {
            System.out.println("in registrazione di viewmodel prima del try");
            try {
                trovaAcquirenteDoppio(email, password, nome, cognome);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registrazioneAcquirenteCompleta(String bio, String paese, String sitoWeb, AcquirenteModel acquirente) {
        System.out.println("entrato in registrazione di viewmodel");
        if (registrazioneParzialeValidaAcquirente(bio, paese, sitoWeb, acquirente)) {
            System.out.println("in registrazione di viewmodel prima del try");
            try {
                inserisciAcquirente(acquirente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inserisciSocialAcquirente(){
        System.out.println("entrato in inserisci social acquirente di view model");
        registrazioneRepository.inserimentoSocialAcquirente(socialAcquirente,new RegistrazioneRepository.OnInserimentoSocialAcquirenteListener(){
            @Override
            public void socialInseritoAcquirente(){

            }
        });
    }

    public void inserisciSocialVenditore(){
        System.out.println("entrato in inserisci social venditore di view model");
        registrazioneRepository.inserimentoSocialVenditore(socialVenditore,new RegistrazioneRepository.OnInserimentoSocialVenditoreListener(){
            @Override
            public void socialInseritoVenditore(){

            }
        });
    }

    public void inserisciAcquirente(AcquirenteModel acquirente) {
        System.out.println("entrato in inserisci acquirente di view model");
        registrazioneRepository.inserimentoAcquirente(acquirente, new RegistrazioneRepository.OnInserisciAcquirenteListener() {
            @Override
            public void confermaAcquirente(Long check) {
                if (check == 0) {
                    repository.setAcquirenteModel(acquirente);
                    setProseguiInserimento("inserito");
                } else {
                    setProseguiInserimento("inserimento fallito");
                }
            }
        });
    }

    private void trovaAcquirenteDoppio(String email, String password, String nome, String cognome) {
        System.out.println("entrato in trova acquirente di view model");
        registrazioneRepository.registrazioneAcquirenteDoppioBackend(email, new RegistrazioneRepository.OnRegistrazioneAcquirenteDoppioListener() {
            @Override
            public void ricercaDoppia(AcquirenteModel acquirenteControllo) {
                if (acquirenteControllo == null) {
                    AcquirenteModel acquirenteModel = new AcquirenteModel(nome, cognome, email, password, null, null, null);
                    repository.setAcquirenteModel(acquirenteModel);
                    repository.setVenditoreModel(null);
                    setProseguiRegistrazione("nuovo acquirente");
                } else {
                    setMessaggioErroreEmail("indirizzo email già usato");
                }
            }
        });
    }

    public void registrazioneVenditoreCompleta(String bio, String paese, String sitoWeb, VenditoreModel venditore) {
        System.out.println("entrato in registrazione di viewmodel");
        if (registrazioneParzialeValidaVenditore(bio, paese, sitoWeb, venditore)) {
            System.out.println("in registrazione di viewmodel prima del try");
            try {
                inserisciVenditore(venditore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inserisciVenditore(VenditoreModel venditore) {
        System.out.println("entrato in trova venditore di view model");
        registrazioneRepository.inserimentoVenditore(venditore, new RegistrazioneRepository.OnInserisciVenditoreListener() {
            @Override
            public void confermaVenditore(Long check) {
                if (check == 0) {
                    repository.setVenditoreModel(venditore);
                    setProseguiInserimento("inserito");
                } else {
                    setProseguiInserimento("inserimento fallito");
                }
            }
        });
    }

    private void trovaVenditoreDoppio(String email, String password, String nome, String cognome) {
        System.out.println("entrato in trova venditore di view model");
        registrazioneRepository.registrazioneVenditoreDoppioBackend(email, new RegistrazioneRepository.OnRegistrazioneVenditoreDoppioListener() {
            @Override
            public void ricercaDoppia(VenditoreModel venditoreControllo) {
                if (venditoreControllo == null) {
                    VenditoreModel venditoreModel = new VenditoreModel(nome, cognome, email, password, null, null, null);
                    repository.setVenditoreModel(venditoreModel);
                    repository.setAcquirenteModel(null);
                    setProseguiRegistrazione("nuovo venditore");
                } else {
                    setMessaggioErroreEmail("indirizzo email già usato");
                }
            }
        });
    }


    public void registrazioneVenditore(String email, String password, String confermaPassword, String nome, String cognome) {
        System.out.println("entrato in registrazione di viewmodel");
        if (registrazioneValida(email, password, confermaPassword, nome, cognome)) {
            System.out.println("in registrazione di viewmodel prima del try");
            try {
                trovaVenditoreDoppio(email, password, nome, cognome);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void categorieAcquirente(String email, ArrayList<String> categorieScelte){
        registrazioneRepository.saveCategorieAcquirente(email,categorieScelte, new RegistrazioneRepository.OnInserimentoCategorieAcquirente(){
            @Override
            public void   categorieInseriteAcquirente(){

            }
        });
    }
    public void categorieVenditore(String email, ArrayList<String> categorieScelte){
        registrazioneRepository.saveCategorieVenditore(email,categorieScelte, new RegistrazioneRepository.OnInserimentoCategorieVenditore(){
            @Override
            public void   categorieInseriteVenditore(){

            }
        });
    }
    public Boolean socialValido(String nomeSocial, String link) {
        System.out.println("entrato in social valido");
        if (nomeSocial == null || nomeSocial.isEmpty()) {
            setMessaggioErroreNomeSocial("nomeSocial non può essere vuoto");
            return false;
        } else if (nomeSocial.length() > 50) {
            setMessaggioErroreNomeSocial("nomeSocial non può essere più lungo di 50 caratteri");
            return false;
        } else if (link == null || link.isEmpty()) {
            setMessaggioErroreLink(" il link non può essere vuoto");
            return false;
        } else if (link.length() > 50) {
            setMessaggioErroreLink("il link non può essere più lungo di 50 caratteri");
            return false;
        } else {
            return  true;
            }
    }



    public Boolean registrazioneValida(String mail, String password,String confermaPassword, String nome, String cognome){
        System.out.println("entrato in registrazione valida");
        if(mail == null || mail.isEmpty() ){
            setMessaggioErroreEmail("L'indirizzo email non può essere vuoto");
            return false;
        }else if(mail.length()>100){
            setMessaggioErroreEmail("L'indirizzo email non può essere più lungo di 100 caratteri");
            return false;
        }else if(password == null || password.isEmpty()){
            setMessaggioErrorePassword("La passsword non può essere vuota");
            return false;
        }else if(password.length()>100){
            setMessaggioErrorePassword("La passsword non può essere più lunga di 100 caratteri");
            return false;
        } else if (!confermaPassword.equals(password)) {
            setMessaggioErroreConfermaPassword("Le password sono diverse");
            return false;
        } else if (nome == null || nome.isEmpty()) {
            setMessaggioErroreNome("il nome non può essere vuoto");
            return false;
        } else if (nome.length() > 50) {
            setMessaggioErroreNome("il nome non può essere più lungo di 50 caratteri");
            return false;
        } else if (cognome == null || nome.isEmpty()) {
            setMessaggioErroreCognome("il cognome non può essere vuoto");
            return false;
        } else if (cognome.length() > 50) {
            setMessaggioErroreCognome("il cognome non può essere più lungo di 50 caratteri");
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean registrazioneParzialeValidaAcquirente(String bio,String paese,String sitoWeb,AcquirenteModel acquirente){
        if (bio.length() > 100) {
            setMessaggioErroreBio("La bio non può superare i 100 caratteri");
            return false;
        }
        else{
            acquirente.setBio(bio);
        }
        if (paese.length() > 25) {
            setMessaggioErrorePaese("Il paese non può superare i 25 caratteri");
            return false;
        }
        else{
            acquirente.setAreageografica(paese);
        }
        if (sitoWeb.length() > 50) {
            setMessaggioErroreSitoWeb("Il sito web non può superare i 50 caratteri");
            return false;
        }
        else{
            acquirente.setLink(sitoWeb);
        }
        return true;

    }

    public Boolean registrazioneParzialeValidaVenditore(String bio,String paese,String sitoWeb,VenditoreModel venditore){
        if (bio.length() > 100) {
            setMessaggioErroreBio("La bio non può superare i 100 caratteri");
            return false;
        }
        else{
            venditore.setBio(bio);
        }
        if (paese.length() > 25) {
            setMessaggioErrorePaese("Il paese non può superare i 25 caratteri");
            return false;
        }
        else{
            venditore.setAreageografica(paese);
        }
        if (sitoWeb.length() > 50) {
            setMessaggioErroreSitoWeb("Il sito web non può superare i 50 caratteri");
            return false;
        }
        else{
            venditore.setLink(sitoWeb);
        }
        return true;

    }
    public void setMessaggioErroreEmail(String messaggio){
        messaggioErroreEmail.setValue(messaggio);
    }
    public String getMessaggioErroreEmail(){
        return messaggioErroreEmail.getValue();
    }
    public Boolean isNuovoMessaggioErroreEmail(){
        return !getMessaggioErroreEmail().equals("");
    }
    public void setMessaggioErrorePassword(String messaggio){
        messaggioErrorePassword.setValue(messaggio);
    }
    public String getMessaggioErrorePassword(){
        return messaggioErrorePassword.getValue();
    }
    public Boolean isNuovoMessaggioErrorePassword(){
        return !getMessaggioErrorePassword().equals("");
    }

    public void setMessaggioErroreConfermaPassword(String messaggio){
        messaggioErroreConfermaPassword.setValue(messaggio);
    }
    public String getMessaggioErroreConfermaPassword(){
        return messaggioErroreConfermaPassword.getValue();
    }
    public Boolean isNuovoMessaggioErroreConfermaPassword(){
        return !getMessaggioErroreConfermaPassword().equals("");
    }

    public void setMessaggioErroreNome(String messaggio){
        messaggioErroreNome.setValue(messaggio);
    }
    public String getMessaggioErroreNome(){
        return messaggioErroreNome.getValue();
    }
    public Boolean isNuovoMessaggioErroreNome(){
        return !getMessaggioErroreNome().equals("");
    }

    public void setMessaggioErroreCognome(String messaggio){
        messaggioErroreCognome.setValue(messaggio);
    }
    public String getMessaggioErroreCognome(){
        return messaggioErroreCognome.getValue();
    }
    public Boolean isNuovoMessaggioErroreCognome(){
        return !getMessaggioErroreCognome().equals("");
    }

    public void setMessaggioErroreBio(String messaggio){
        messaggioErroreBio.setValue(messaggio);
    }
    public String getMessaggioErroreBio(){
        return messaggioErroreBio.getValue();
    }
    public Boolean isNuovoMessaggioErroreBio(){
        return !getMessaggioErroreBio().equals("");
    }

    public void setMessaggioErrorePaese(String messaggio){
        messaggioErrorePaese.setValue(messaggio);
    }
    public String getMessaggioErrorePease(){
        return messaggioErrorePaese.getValue();
    }
    public Boolean isNuovoMessaggioErrorePaese(){
        return !getMessaggioErrorePease().equals("");
    }

    public void setMessaggioErroreSitoWeb(String messaggio){
        messaggioErroreSitoWeb.setValue(messaggio);
    }
    public String getMessaggioErroreSitoWeb(){
        return messaggioErroreSitoWeb.getValue();
    }
    public Boolean isNuovoMessaggioErroreSitoWeb(){
        return !getMessaggioErroreSitoWeb().equals("");
    }
    public void setMessaggioErroreNomeSocial(String messaggio){
        messaggioErroreNomeSocial.setValue(messaggio);
    }
    public String getMessaggioErroreNomeSocial(){
        return messaggioErroreNomeSocial.getValue();
    }
    public Boolean isNuovoMessaggioErrorNomeSociale(){
        return !getMessaggioErroreNomeSocial().equals("");
    }
    public void setMessaggioErroreLink(String messaggio){
        messaggioErroreLink.setValue(messaggio);
    }
    public String getMessaggioErroreLink(){
        return messaggioErroreLink.getValue();
    }
    public Boolean isNuovoMessaggioErrorLink(){
        return !getMessaggioErroreLink().equals("");
    }
    public Boolean containsAcquirente() {
        return repository.getAcquirenteModel()!=null;
    }

    public Boolean containsVenditore(){
        return repository.getVenditoreModel()!=null;
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

    public LiveData<AcquirenteModel> getAcquirente() {
        return acquirenteModel;
    }
    public  void recuperoAcquirente(){
        acquirenteModel.setValue(repository.getAcquirenteModel());
    }

    public  void recuperoVenditore(){
        venditoreModel.setValue(repository.getVenditoreModel());
    }
    public LiveData<VenditoreModel> getVenditore() {
        return venditoreModel;
    }

    public void setApriPopUpsocial(Boolean b) {
        this.apriPopUpSocial.setValue(b);
    }

    public void apriPopUp() {
        setApriPopUpsocial(true);
    }

    public void resetErrori(){
        setMessaggioErroreNomeSocial("");
        setMessaggioErroreLink("");
    }
    public void setValoriPresentiAcquirente(AcquirenteModel utente){
        this.valoriPresentiAcquirente.setValue(utente);
    }
    public Boolean isValoriPresentiAcquirente(){
        return (valoriPresentiAcquirente.getValue()!=null);
    }
    public void setValoriPresentiVenditore(VenditoreModel utente){
        this.valoriPresentiVenditore.setValue(utente);
    }
    public Boolean isValoriPresentiVenditore(){
        return (valoriPresentiVenditore.getValue()!=null);
    }
    public void checkValoriPresenti(){
        Log.d("checkValoriPresenti","entrato");
        if(repository.getAcquirenteModel()!=null){
            setValoriPresentiAcquirente(repository.getAcquirenteModel());
        }else if(repository.getVenditoreModel()!=null){
            setValoriPresentiVenditore(repository.getVenditoreModel());
        }
    }
    public void setValoriPresentiFacoltativiAcquirente(AcquirenteModel utente){
        this.valoriPresentiFacoltativiAcquirente.setValue(utente);
    }
    public Boolean isValoriPresentiFacoltativiAcquirente(){
        return (valoriPresentiFacoltativiAcquirente.getValue()!=null);
    }
    public void setValoriPresentiFacoltativiVenditore(VenditoreModel utente){
        this.valoriPresentiFacoltativiVenditore.setValue(utente);
    }
    public Boolean isValoriPresentiFacoltativiVenditore(){
        return (valoriPresentiFacoltativiVenditore.getValue()!=null);
    }
    public void checkValoriPresentiFacoltativi(){
        Log.d("checkValoriPresenti","entrato");
        if(acquirenteModel.getValue()!=null){
            Log.d("checkValoriPresenti","entrato caso acquirente");
            setValoriPresentiFacoltativiAcquirente(acquirenteModel.getValue());
        }else if(venditoreModel.getValue()!=null){
            Log.d("checkValoriPresenti","entrato caso venditore");
            setValoriPresentiFacoltativiVenditore(venditoreModel.getValue());
        }
    }
    public void setIsSocialCambiato(Boolean b){
        this.isSocialCambiato.setValue(b);
    }
    public void aggiornaSocialViewModel(String nome_vecchio, String link_vecchio, String nome, String link) {
        boolean coppiaPresente = false;

        if(socialValido(nome,link)) {
            if (socialAcquirente != null && !socialAcquirente.isEmpty()) {
                for (SocialAcquirenteModel social : socialAcquirente) {
                    if (social.getNome().equals(nome) && social.getLink().equals(link)) {
                        coppiaPresente = true;
                        setMessaggioErroreLink("social già aggiunto");
                        break;
                    }
                }
            } else if (socialVenditore != null && !socialVenditore.isEmpty()) {
                for (SocialVenditoreModel social : socialVenditore) {
                    if (social.getNome().equals(nome) && social.getLink().equals(link)) {
                        coppiaPresente = true;
                        setMessaggioErroreLink("social già aggiunto");
                        break;
                    }
                }
            }

            if (!coppiaPresente) {
                if (socialAcquirente != null && !socialAcquirente.isEmpty()) {
                    for (SocialAcquirenteModel social : socialAcquirente) {
                        if (social.getNome().equals(nome_vecchio) && social.getLink().equals(link_vecchio)) {
                            social.setNome(nome);
                            social.setLink(link);
                            setIsSocialCambiato(true);
                            break;
                        }
                    }
                } else if (socialVenditore != null && !socialVenditore.isEmpty()) {
                    for (SocialVenditoreModel social : socialVenditore) {
                        if (social.getNome().equals(nome_vecchio) && social.getLink().equals(link_vecchio)) {
                            social.setNome(nome);
                            social.setLink(link);
                            setIsSocialCambiato(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void eliminaSocialViewModel(String nome_vecchio,String link_vecchio){
        if (socialAcquirente != null && !socialAcquirente.isEmpty()) {
            for (SocialAcquirenteModel social : socialAcquirente) {
                if (social.getNome().equals(nome_vecchio) && social.getLink().equals(link_vecchio)) {
                    socialAcquirente.remove(social);
                    setIsSocialCambiato(true);
                    break;
                }
            }
        } else if (socialVenditore != null && !socialVenditore.isEmpty()) {
            for (SocialVenditoreModel social : socialVenditore) {
                if (social.getNome().equals(nome_vecchio) && social.getLink().equals(link_vecchio)) {
                    socialVenditore.remove(social);
                    setIsSocialCambiato(true);
                    break;
                }
            }
        }
    }
    public void resetErroriModificaSocial(){
        setMessaggioErroreNomeSocial("");
        setMessaggioErroreLink("");
        setIsSocialCambiato(false);
    }
    // Metodi per listaSocialAcquirente
    public ArrayList<SocialAcquirenteModel> getListaSocialAcquirente() {
        return listaSocialAcquirente.getValue();
    }

    public void setListaSocialAcquirente(ArrayList<SocialAcquirenteModel> listaSocialAcquirente) {
        this.listaSocialAcquirente.setValue(listaSocialAcquirente);
    }

    public boolean isListaSocialAcquirente() {
        return listaSocialAcquirente.getValue()!=null;
    }

    public ArrayList<SocialVenditoreModel> getListaSocialVenditore() {
        return listaSocialVenditore.getValue();
    }

    public void setListaSocialVenditore(ArrayList<SocialVenditoreModel> listaSocialVenditore) {
        this.listaSocialVenditore.setValue(listaSocialVenditore);
    }

    public boolean isListaSocialVenditore() {
        return listaSocialVenditore.getValue()!=null;
    }
    public Boolean getSocialVuoti() {
        return socialVuoti.getValue();
    }

    public void setSocialVuoti(Boolean socialVuoti) {
        this.socialVuoti.setValue(socialVuoti);
    }
    public void inserisciSocialNellaLista(){
        Log.d("inserisciSocialNellaLista","entrato");
        if(socialAcquirente!=null && !socialAcquirente.isEmpty()){
            setListaSocialAcquirente(socialAcquirente);
        }else if(socialVenditore!=null && !socialVenditore.isEmpty()){
            setListaSocialVenditore(socialVenditore);
        }else {
            setSocialVuoti(true);
        }
    }
    public void stampaLista() {
        if (listaSocialAcquirente != null && listaSocialAcquirente.getValue() != null) {
            Log.d("stampaLista", "social acquirenti: " + listaSocialAcquirente.getValue().size());
        } else if (listaSocialVenditore != null && listaSocialVenditore.getValue() != null) {
            Log.d("stampaLista", "social acquirenti: " + listaSocialVenditore.getValue().size());
        }
    }
}