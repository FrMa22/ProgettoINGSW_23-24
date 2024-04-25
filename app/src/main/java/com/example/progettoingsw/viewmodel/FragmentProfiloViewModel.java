package com.example.progettoingsw.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.FragmentProfiloRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class FragmentProfiloViewModel extends ViewModel {

    public MutableLiveData<String> messaggioSocialUtenteNonTrovato = new MutableLiveData<>("");

    public MutableLiveData<Boolean> acquirenteModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> venditoreModelPresente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> socialAcquirentePresenti = new MutableLiveData<>(false);
    public MutableLiveData<String> messaggioInfoToast = new MutableLiveData<>("");
    public MutableLiveData<Boolean> modificaCampoProfilo = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> cambiaPassword = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> socialVenditorePresenti = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> entraInPopUpModificaSocial = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> apriPopUpAggiungiSocial = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> apriLeMieAste = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> apriPartecipazioneAste = new MutableLiveData<>(false);
    public MutableLiveData<String> messaggioErroreNomeNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreLinkNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreCognomeNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErrorePaeseNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreSitoNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreBioNuovo = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErrorePasswordVecchia = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErrorePasswordNuova = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreSocial = new MutableLiveData<>("");

    public MutableLiveData<Boolean> socialAssenti = new MutableLiveData<>(false);
    public MutableLiveData<String> nomeSocialSelezionato = new MutableLiveData<>("");
    public MutableLiveData<String> nomeLinkSelezionato = new MutableLiveData<>("");
    public MutableLiveData<ArrayList<SocialAcquirenteModel>> socialAcquirenteRecuperati = new MutableLiveData<>(null);

    public MutableLiveData<ArrayList<SocialVenditoreModel>> socialVenditoreRecuperati = new MutableLiveData<>(null);

    public MutableLiveData<AcquirenteModel> acquirenteRecuperato =new MutableLiveData<>(null);

    public MutableLiveData<VenditoreModel> venditoreRecuperato =new MutableLiveData<>(null);
    public MutableLiveData<Boolean> isPasswordCambiata = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isSocialCambiato = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isSocialAggiunto = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isUtenteCambiato = new MutableLiveData<>(false);


    public MutableLiveData<Boolean> esci =new MutableLiveData<>(false);

    private FragmentProfiloRepository fragmentProfiloRepository;
    private Repository repository;

    private static final String TOKEN_KEY = "token";

    public FragmentProfiloViewModel(){
        repository = Repository.getInstance();
        fragmentProfiloRepository = new FragmentProfiloRepository();
    }
    private void rimuoviTokenLocale(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
    public void removeTokenAcquirente(String email){
        fragmentProfiloRepository.removeTokenFromAcquirente(email, new FragmentProfiloRepository.RemoveTokenFromAcquirenteListener() {
            @Override
            public void onRemoveTokenFromAcquirenteListener(Integer valore) {
                if(valore>0){
                    Log.d("removeTokenAcquirente", "rimosso con successo");
                }else{
                    Log.d("removeTokenAcquirente", "errore nella rimozione");
                }
            }
        });
    }
    public void removeTokenVenditore(String email){
        fragmentProfiloRepository.removeTokenFromVenditore(email, new FragmentProfiloRepository.RemoveTokenFromVenditoreListener() {
            @Override
            public void onRemoveTokenFromVenditoreListener(Integer valore) {
                if(valore>0){
                    Log.d("removeTokenVenditore", "rimosso con successo");
                }else{
                    Log.d("removeTokenVenditore", "errore nella rimozione");
                }
            }
        });
    }

    public void logout(Context context){
        rimuoviTokenLocale(context);
        if(containsAcquirente()){
            removeTokenAcquirente(repository.getAcquirenteModel().getIndirizzo_email());
        }else{
            removeTokenVenditore(repository.getVenditoreModel().getIndirizzo_email());
        }
        repository.deleteRepository();
        setEsci(true);
    }

    public Boolean getEsci() {
        return esci.getValue();
    }

    public void setEsci(Boolean esci) {
        this.esci.setValue(esci);
    }

    public void trovaSocialAcquirenteViewModel(){
        if(containsAcquirente()) {
            String email = getAcquirenteEmail();
            System.out.println("entrato in fragmentProfilo di viewmodel con email:" + email);
            System.out.println("in fragmentProfiloAcquirente di viewmodel prima del try con email:" + email);
//            if (repository.getSocialAcquirenteModelList() == null || repository.getSocialAcquirenteModelList().isEmpty()) {
//                System.out.println("lista social acquirente null");
//                setSocialAssenti(true);
//                return;
//            }
            try {
                trovaSocialAcquirente(email);
                if (repository.getSocialAcquirenteModelList().isEmpty()) {
                    setMessaggioUtenteNonTrovato("acquirente non ha social");
                } //altrimenti trova i social e non fa nulla perchè verranno semplicemente messi
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public boolean aggiunteSocialValide(String newnome,String newlink){
        System.out.println("entrato in modifica social valida");
        if(newnome == null || newnome.isEmpty() ){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere vuoto");
            return false;
        } else  if(newlink == null || newlink.isEmpty() ){
            setMessaggioErroreLinkNuovo("Il link nuovo non può essere vuoto");
            return false;
        }
        else if(newnome.length()>50){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere più lungo di 50 caratteri");
            return false;
        } else if(newlink.length()>50) {
            setMessaggioErroreLinkNuovo("Il link nuovo non può essere più lungo di 50 caratteri");
            return false;
        }
        else{
            return true;
        }
    }

    public void aggiungiSocialViewModel(String nome,String link){
        if(aggiunteSocialValide(nome,link)) {

            if (containsAcquirente()) {
                aggiungiSocialAcquirenteViewModel(nome, link);
            } else if (containsVenditore()) {
                aggiungiSocialVenditoreViewModel(nome, link);
            }

        }
    }









    public boolean modificheSocialValide(String newnome,String newlink){
        System.out.println("entrato in modifica social valida");
          if(newnome == null || newnome.isEmpty() ){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere vuoto");
            return false;
        } else  if(newlink == null || newlink.isEmpty() ){
            setMessaggioErroreLinkNuovo("Il link nuovo non può essere vuoto");
            return false;
        }
        else if(newnome.length()>50){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere più lungo di 50 caratteri");
            return false;
        } else if(newlink.length()>50) {
            setMessaggioErroreLinkNuovo("Il link nuovo non può essere più lungo di 50 caratteri");
            return false;
        }
        else{
            return true;
        }
    }

    public void aggiornaSocialViewModel(String oldnome,String oldlink,String nome,String link){

        if(modificheSocialValide(nome,link)) {


            if (containsAcquirente()) {
                aggiornaSocialAcquirenteViewModel(oldnome, oldlink, nome, link);
            } else if (containsVenditore()) {
                aggiornaSocialVenditoreViewModel(oldnome, oldlink, nome, link);
            }

        }
    }

    public void eliminaSocialViewModel(String nome,String link){
        if(containsAcquirente()){
            eliminaSocialAcquirenteViewModel(nome,link);
        }
        else if(containsVenditore()){
            eliminaSocialVenditoreViewModel(nome,link);
        }

    }

    public boolean modificheProfiloValide(String nome,String cognome,String bio,String sitoweb,String paese){
        System.out.println("entrato in modifica profilo valida");
        if(nome == null || nome.isEmpty() ){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere vuoto");
            return false;
        } else  if(cognome == null || cognome.isEmpty() ){
            setMessaggioErroreCognomeNuovo("Il cognome nuovo non può essere vuoto");
            return false;
        }
        else if(nome.length()>50){
            setMessaggioErroreNomeNuovo("Il nome nuovo non può essere più lungo di 50 caratteri");
            return false;
        } else if(cognome.length()>50) {
            setMessaggioErroreCognomeNuovo("Il cognome nuovo non può essere più lungo di 50 caratteri");
            return false;
        } else if(bio.length()>100){
            setMessaggioErroreBioNuovo("La descrizione nuova non può essere più lunga di 100 caratteri");
            return false;
        } else if(sitoweb.length()>50) {
            setMessaggioErroreSitoNuovo("Il sito web nuovo non può essere più lungo di 50 caratteri");
            return false;
        } else if(paese.length()>25){
            setMessaggioErrorePaeseNuovo("Il paese nuovo non può essere più lungo di 25 caratteri");
            return false;
        }
        else{
            return true;
        }
    }

    public void aggiornaViewModel(String nome,String cognome,String bio,String sitoweb,String paese){
        if(modificheProfiloValide(nome,cognome,bio,sitoweb,paese)) {

            if (containsAcquirente()) {
                aggiornaAcquirenteViewModel(nome, cognome, bio, sitoweb, paese);
            } else if (containsVenditore()) {
                aggiornaVenditoreViewModel(nome, cognome, bio, sitoweb, paese);
            }
        }
    }


    public void setMessaggioErroreNomeNuovo(String messaggio){
        messaggioErroreNomeNuovo.setValue(messaggio);
    }

    public String getMessaggioErroreNomeNuovo(){
        return messaggioErroreNomeNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErroreNomeNuovo(){
        return !getMessaggioErroreNomeNuovo().equals("");
    }


    public void setMessaggioErroreCognomeNuovo(String messaggio){
        messaggioErroreCognomeNuovo.setValue(messaggio);
    }

    public String getMessaggioErroreCognomeNuovo(){
        return messaggioErroreCognomeNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErroreCognomeNuovo(){
        return !getMessaggioErroreCognomeNuovo().equals("");
    }

    public void setMessaggioErroreBioNuovo(String messaggio){
        messaggioErroreBioNuovo.setValue(messaggio);
    }

    public String getMessaggioErroreBioNuovo(){
        return messaggioErroreBioNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErroreBioNuovo(){
        return !getMessaggioErroreBioNuovo().equals("");
    }

    public void setMessaggioErrorePaeseNuovo(String messaggio){
        messaggioErrorePaeseNuovo.setValue(messaggio);
    }

    public String getMessaggioErrorePaeseNuovo(){
        return messaggioErrorePaeseNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErrorePaeseNuovo(){
        return !getMessaggioErrorePaeseNuovo().equals("");
    }


    public void setMessaggioErroreSitoNuovo(String messaggio){
        messaggioErroreSitoNuovo.setValue(messaggio);
    }

    public String getMessaggioErroreSitoNuovo(){
        return messaggioErroreSitoNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErroreSitoNuovo(){
        return !getMessaggioErroreSitoNuovo().equals("");
    }



    public void setMessaggioErroreLinkNuovo(String messaggio){
        messaggioErroreLinkNuovo.setValue(messaggio);
    }

    public String getMessaggioErroreLinkNuovo(){
        return messaggioErroreLinkNuovo.getValue();
    }
    public Boolean isNuovoMessaggioErroreLinkNuovo(){
        return !getMessaggioErroreLinkNuovo().equals("");
    }


    public void setMessaggioErrorePasswordVecchia(String messaggio){
        messaggioErrorePasswordVecchia.setValue(messaggio);
    }

    public String getMessaggioErrorePasswordVecchia(){
        return messaggioErrorePasswordVecchia.getValue();
    }
    public Boolean isNuovoMessaggioErrorePasswordVecchia(){
        return !getMessaggioErrorePasswordVecchia().equals("");
    }

    public void setMessaggioErrorePasswordNuova(String messaggio){
        messaggioErrorePasswordNuova.setValue(messaggio);
    }

    public String getMessaggioErrorePasswordNuova(){
        return messaggioErrorePasswordNuova.getValue();
    }
    public Boolean isNuovoMessaggioErrorePasswordNuova(){
        return !getMessaggioErrorePasswordNuova().equals("");
    }

    public boolean passwordValide(String oldpassword,String newpassword){
        System.out.println("entrato in login valido");
        if(oldpassword == null || oldpassword.isEmpty() ){
            setMessaggioErrorePasswordVecchia("La password vecchia non può essere vuota");
            return false;
        } else  if(newpassword == null || newpassword.isEmpty() ){
            setMessaggioErrorePasswordNuova("La password nuova non può essere vuota");
            return false;
        }
        else if(oldpassword.length()>100){
            setMessaggioErrorePasswordVecchia("La password vecchia non può essere più lunga di 100 caratteri");
            return false;
        } else if(newpassword.length()>100) {
            setMessaggioErrorePasswordNuova("La password nuova non può essere più lunga di 100 caratteri");
            return false;
        }
        else{
            return true;
        }
    }

    public void aggiornaPasswordViewModel(String password,String passwordNuova ){

        if(passwordValide(password,passwordNuova)) {

            if (containsAcquirente()) {


                if (password.equals(getAcquirentePassword())) {
                    aggiornaPasswordAcquirenteViewModel(passwordNuova);
                }else{setMessaggioErrorePasswordVecchia("La password vecchia non coincide con quella dell'utente");}
            } else if (containsVenditore()) {

                if (password.equals(getVenditorePassword())) {
                    aggiornaPasswordVenditoreViewModel(passwordNuova);
                }else{setMessaggioErrorePasswordVecchia("La password vecchia non coincide con quella dell'utente");}
            }
        }
    }

    public void aggiungiSocialAcquirenteViewModel(String nome,String link){
        String email=getAcquirenteEmail();
        System.out.println("entrato in fragmentProfilo aggiungi social di viewmodel con email:"+email + "nome:"+ nome + "link:"+ link);
        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiungiSocialAcquirente(nome,link,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Boolean getSocialAssenti() {
        return socialAssenti.getValue();
    }
    public void setSocialAssenti(Boolean socialAssenti) {
        this.socialAssenti.setValue(socialAssenti);
    }

    public void eliminaSocialAcquirenteViewModel(String nome,String link){
        String email=getAcquirenteEmail();
        System.out.println("entrato in fragmentProfilo elimina social di viewmodel con email:"+email + "nome:"+ nome + "link:"+ link);
        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            eliminaSocialAcquirente(nome,link,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void aggiornaSocialAcquirenteViewModel(String oldNome,String oldLink,String newNome,String newLink){
        System.out.println("Parametri del metodo aggiornaSocialAcquirenteViewModel:");
        System.out.println("oldNome: " + oldNome);
        System.out.println("oldLink: " + oldLink);
        System.out.println("newNome: " + newNome);
        System.out.println("newLink: " + newLink);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaSocialAcquirente(oldNome,oldLink,newNome,newLink);
        } catch (Exception e){
            e.printStackTrace();
        }

    }



    public void aggiornaAcquirenteViewModel(String nome,String cognome,String bio,String link,String areageografica){
        String email=getAcquirenteEmail();
        System.out.println("Parametri del metodo aggiornaAcquirenteViewModel:");
        System.out.println("nome: " + nome);
        System.out.println("cognome: " + cognome);
        System.out.println("bio: " + bio);
        System.out.println("link: " + link);
        System.out.println("areageografica: " + areageografica);
        System.out.println("email: " + email);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaAcquirente(nome,cognome,bio,link,areageografica,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void aggiornaPasswordAcquirenteViewModel(String password){
        String email=getAcquirenteEmail();
        System.out.println("Parametri del metodo aggiornaPasswordAcquirenteViewModel:");
        System.out.println("password: " + password);
        System.out.println("email: " + email);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaPasswordAcquirente(password,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }








    private void trovaSocialAcquirente(String email) {
        System.out.println("entrato in trova Social acquirente di view model");
        fragmentProfiloRepository.trovaSocialAcquirenteBackend(email, new FragmentProfiloRepository.OnFragmentProfiloAcquirenteListener() {
            @Override
            public void onFragmentProfilo(List<SocialAcquirenteModel> socialAcquirenteModelList) {
                repository.setSocialAcquirenteModelList(socialAcquirenteModelList);

                //se c'è la lista dei social allora setta il valore per l'observer della view
                if(socialAcquirenteModelList!=null  && !socialAcquirenteModelList.isEmpty()){
                    setSocialAcquirenteRecuperati((ArrayList<SocialAcquirenteModel>) socialAcquirenteModelList);
                }else{
                    setSocialAssenti(true);
                }
            }
        });
    }

    private void aggiungiSocialAcquirente(String nome,String link,String email) {
        System.out.println("entrato in aggiungi Social acquirente di view model");
        fragmentProfiloRepository.aggiungiSocialAcquirenteBackend(nome,link,email ,new FragmentProfiloRepository.OnAggiungiSocialAcquirenteListener() {
            @Override
            public void onAggiungiSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel) {
                if(socialAcquirenteModel!=null) {
                    repository.addSocialAcquirente(socialAcquirenteModel);
                    setIsSocialAggiunto(true);
                }else{
                    setMessaggioErroreSocial("Non è possibile inserire più volte lo stesso social.");
                    setIsSocialAggiunto(true);
                }
            }
        });
    }



    private void eliminaSocialAcquirente(String nome,String link,String email) {
        System.out.println("entrato in elimina Social acquirente di view model");
        fragmentProfiloRepository.eliminaSocialAcquirenteBackend(nome,link,email ,new FragmentProfiloRepository.OnEliminaSocialAcquirenteListener() {
            @Override
            public void onEliminaSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel) {
                repository.deleteSocialAcquirente(socialAcquirenteModel);
            }
        });
    }


    private void aggiornaSocialAcquirente(String oldNome,String oldLink,String newNome,String newLink) {
        System.out.println("entrato in aggiorna Social acquirente di view model");
        fragmentProfiloRepository.aggiornaSocialAcquirenteBackend(oldNome,oldLink,newNome,newLink ,new FragmentProfiloRepository.OnAggiornaSocialAcquirenteListener() {
            @Override
            public void onAggiornaSocialAcquirente(String nomeVecchio,String linkVecchio,String nomeNuovo,String linkNuovo) {
                repository.updateSocialAcquirente(nomeVecchio,linkVecchio,nomeNuovo,linkNuovo);
                setIsSocialCambiato(true);
            }
        });
    }


    private void aggiornaAcquirente(String nome,String cognome,String bio,String link,String areageografica,String email) {
        System.out.println("entrato in aggiorna  acquirente di view model");
        fragmentProfiloRepository.aggiornaAcquirenteBackend(nome,cognome,bio,link,areageografica,email ,new FragmentProfiloRepository.OnAggiornaAcquirenteListener() {
            @Override
            public void onAggiornaAcquirente(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo) {
                repository.updateAcquirente(nomeNuovo,cognomeNuovo,bioNuovo,linkNuovo,areageograficaNuovo);
                setIsUtenteCambiato(true);
            }
        });
    }


    private void aggiornaPasswordAcquirente(String password,String email) {
        System.out.println("entrato in aggiorna password  acquirente di view model");


            fragmentProfiloRepository.aggiornaPasswordAcquirenteBackend(password, email, new FragmentProfiloRepository.OnAggiornaPasswordAcquirenteListener() {
                @Override
                public void onAggiornaPasswordAcquirente(String passwordNuovo) {
                    repository.updatePasswordAcquirente(passwordNuovo);
                    setIsPasswordCambiata(true);
                }
            });

    }


    //versione venditore

    public void trovaSocialVenditoreViewModel(){
        if(containsVenditore()) {
            String email = getVenditoreEmail();
            System.out.println("entrato in fragmentProfilo di viewmodel con email:" + email);
            System.out.println("in fragmentProfiloVenditore di viewmodel prima del try con email:" + email + ", la repository social venditore ha: " + repository.getSocialVenditoreModelList());
//            if (repository.getSocialVenditoreModelList() == null || repository.getSocialVenditoreModelList().isEmpty()) {
//                System.out.println("lista social Venditore null");
//                setSocialAssenti(true);
//                return;
//            }
            try {
                trovaSocialVenditore(email);
                if (repository.getSocialVenditoreModelList().isEmpty()) {
                    setMessaggioUtenteNonTrovato("acquirente non ha social");
                } //altrimenti trova i social e non fa nulla perchè verranno semplicemente messi
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void aggiungiSocialVenditoreViewModel(String nome,String link){
        String email=getVenditoreEmail();
        System.out.println("entrato in fragmentProfilo aggiungi social di viewmodel con email:"+email + "nome:"+ nome + "link:"+ link);
        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiungiSocialVenditore(nome,link,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void eliminaSocialVenditoreViewModel(String nome,String link){
        String email=getVenditoreEmail();
        System.out.println("entrato in fragmentProfilo elimina social di viewmodel con email:"+email + "nome:"+ nome + "link:"+ link);
        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            eliminaSocialVenditore(nome,link,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void aggiornaSocialVenditoreViewModel(String oldNome,String oldLink,String newNome,String newLink){
        System.out.println("Parametri del metodo aggiornaSocialVenditoreViewModel:");
        System.out.println("oldNome: " + oldNome);
        System.out.println("oldLink: " + oldLink);
        System.out.println("newNome: " + newNome);
        System.out.println("newLink: " + newLink);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaSocialVenditore(oldNome,oldLink,newNome,newLink);
        } catch (Exception e){
            e.printStackTrace();
        }

    }



    public void aggiornaVenditoreViewModel(String nome,String cognome,String bio,String link,String areageografica){
        String email=getVenditoreEmail();
        System.out.println("Parametri del metodo aggiornaVenditoreViewModel:");
        System.out.println("nome: " + nome);
        System.out.println("cognome: " + cognome);
        System.out.println("bio: " + bio);
        System.out.println("link: " + link);
        System.out.println("areageografica: " + areageografica);
        System.out.println("email: " + email);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaVenditore(nome,cognome,bio,link,areageografica,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void aggiornaPasswordVenditoreViewModel(String password){
        String email=getVenditoreEmail();
        System.out.println("Parametri del metodo aggiornaPasswordVenditoreViewModel:");
        System.out.println("password: " + password);
        System.out.println("email: " + email);

        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiornaPasswordVenditore(password,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }






//chiamate al backend versione venditore

    private void trovaSocialVenditore(String email) {
        System.out.println("entrato in trova Social Venditore di view model");
        fragmentProfiloRepository.trovaSocialVenditoreBackend(email, new FragmentProfiloRepository.OnFragmentProfiloVenditoreListener() {
            @Override
            public void onFragmentProfiloVenditore(List<SocialVenditoreModel> socialVenditoreModelList) {
                repository.setSocialVenditoreModelList(socialVenditoreModelList);

                //se c'è la lista dei social allora setta il valore per l'observer della view
                if(socialVenditoreModelList!=null  && !socialVenditoreModelList.isEmpty()){
                    setSocialVenditoreRecuperati((ArrayList<SocialVenditoreModel>) socialVenditoreModelList);
                }else{
                    setSocialAssenti(true);
                }
            }
        });
    }

    private void aggiungiSocialVenditore(String nome,String link,String email) {
        System.out.println("entrato in aggiungi Social Venditore di view model");
        fragmentProfiloRepository.aggiungiSocialVenditoreBackend(nome,link,email ,new FragmentProfiloRepository.OnAggiungiSocialVenditoreListener() {
            @Override
            public void onAggiungiSocialVenditore(SocialVenditoreModel socialVenditoreModel) {
                if(socialVenditoreModel!=null){
                repository.addSocialVenditore(socialVenditoreModel);
                setIsSocialAggiunto(true);
            }else{
                setMessaggioErroreSocial("Non è possibile inserire più volte lo stesso social.");
                setIsSocialAggiunto(true);
            }
            }
        });
    }



    private void eliminaSocialVenditore(String nome,String link,String email) {
        System.out.println("entrato in elimina Social Venditore di view model");
        fragmentProfiloRepository.eliminaSocialVenditoreBackend(nome,link,email ,new FragmentProfiloRepository.OnEliminaSocialVenditoreListener() {
            @Override
            public void onEliminaSocialVenditore(SocialVenditoreModel socialVenditoreModel) {
                repository.deleteSocialVenditore(socialVenditoreModel);
            }
        });
    }


    private void aggiornaSocialVenditore(String oldNome,String oldLink,String newNome,String newLink) {
        System.out.println("entrato in aggiorna Social Venditore di view model");
        fragmentProfiloRepository.aggiornaSocialVenditoreBackend(oldNome,oldLink,newNome,newLink ,new FragmentProfiloRepository.OnAggiornaSocialVenditoreListener() {
            @Override
            public void onAggiornaSocialVenditore(String nomeVecchio,String linkVecchio,String nomeNuovo,String linkNuovo) {
                repository.updateSocialVenditore(nomeVecchio,linkVecchio,nomeNuovo,linkNuovo);
                setIsSocialCambiato(true);
            }
        });
    }


    private void aggiornaVenditore(String nome,String cognome,String bio,String link,String areageografica,String email) {
        System.out.println("entrato in aggiorna  Venditore di view model");
        fragmentProfiloRepository.aggiornaVenditoreBackend(nome,cognome,bio,link,areageografica,email ,new FragmentProfiloRepository.OnAggiornaVenditoreListener() {
            @Override
            public void onAggiornaVenditore(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo) {
                repository.updateVenditore(nomeNuovo,cognomeNuovo,bioNuovo,linkNuovo,areageograficaNuovo);
                setIsUtenteCambiato(true);
            }
        });
    }


    private void aggiornaPasswordVenditore(String password,String email) {
        System.out.println("entrato in aggiorna password  Venditore di view model");
            fragmentProfiloRepository.aggiornaPasswordVenditoreBackend(password, email, new FragmentProfiloRepository.OnAggiornaPasswordVenditoreListener() {
                @Override
                public void onAggiornaPasswordVenditore(String passwordNuovo) {
                    repository.updatePasswordVenditore(passwordNuovo);
                    setIsPasswordCambiata(true);
                }
            });


    }

    public void setIsPasswordCambiata(Boolean b){
        isPasswordCambiata.setValue(b);
    }
    public Boolean getIsPasswordCambiata(){
        return isPasswordCambiata.getValue();
    }
    public void resetErroriControlloPassword(){
        setMessaggioErrorePasswordVecchia("");
        setMessaggioErrorePasswordNuova("");
    }
    public void setIsSocialCambiato(Boolean b){
        isSocialCambiato.setValue(b);
    }
    public Boolean getIsSocialCambiato(){
        return isSocialCambiato.getValue();
    }

    public void setIsUtenteCambiato(Boolean b){
        isUtenteCambiato.setValue(b);
    }
    public Boolean getIsUtenteCambiato(){
        return isUtenteCambiato.getValue();
    }


    public void setIsSocialAggiunto(Boolean b){
        isSocialAggiunto.setValue(b);
    }
    public Boolean getIsSocialAggiunto(){
        return isSocialAggiunto.getValue();
    }


    public void setMessaggioUtenteNonTrovato(String messaggio){
        messaggioSocialUtenteNonTrovato.setValue(messaggio);
    }
    private String getMessaggioUtenteNonTrovato() {
        return messaggioSocialUtenteNonTrovato.getValue();
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


    private void setSocialAcquirentePresenti(boolean b) {
        socialAcquirentePresenti.setValue(true);
    }
    public Boolean getSocialAcquirentePresenti() {
        return socialAcquirentePresenti.getValue();
    }


    private void setSocialVenditorePresenti(boolean b) {
        socialVenditorePresenti.setValue(true);
    }
    public Boolean getSocialVenditorePresenti() {
        return socialVenditorePresenti.getValue();
    }


    public List<String> getNomiSocialAcquirente(){
        Log.d("getNomiSocialAcquirente", "numero nomi: "  + repository.getNomiSocialAcquirenteModelList().size());
        return repository.getNomiSocialAcquirenteModelList();
    }

    public List<String> getLinksSocialAcquirente(){
        Log.d("getLinksAcquirente", "numero link: "  + repository.getLinksSocialAcquirenteModelList().size());
        return repository.getLinksSocialAcquirenteModelList();
    }


    public void setEntraInPopUpModificaSocial(Boolean b){
        entraInPopUpModificaSocial.setValue(b);
    }
    public Boolean getEntraInPopUpModificaSocial(){
        return entraInPopUpModificaSocial.getValue();
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


    public void findSocialUtente(){
        if(containsAcquirente()){
            System.out.println("contains di acquirente");
            if(repository.findSocialAcquirente(getNomeSocialSelezionato(),getNomeLinkSelezionato()) ){
                setNomeSocial(getNomeSocialSelezionato());
                setLinkSocial(getNomeLinkSelezionato());
            }

        }else if(containsVenditore()) {
            System.out.println("contains di venditore");
            if(repository.findSocialVenditore(getNomeSocialSelezionato(),getNomeLinkSelezionato()) ){
                setNomeSocial(getNomeSocialSelezionato());
                setLinkSocial(getNomeLinkSelezionato());
            }
        }
    }



    public void gestisciModificaSocial(String nome,String link){
        // Esegui le azioni desiderate con l'oggetto Asta
            repository.setSocialAcquirenteSelezionato(nome,link);
            setEntraInPopUpModificaSocial(true);
//            int id = Math.toIntExact(((Asta_allingleseModel) asta).getId());
//            Log.d("Asta inglese", "id è " + id);
//            Intent intent = new Intent(getContext(), SchermataAstaInglese.class);//test del login
//            intent.putExtra("email", email);
//            intent.putExtra("tipoUtente", tipoUtente);
//            intent.putExtra("id", id);
//            startActivity(intent);

    }

    public String getAcquirenteEmail(){
        return repository.getAcquirenteModel().getIndirizzo_email();
    }

    public String getVenditoreEmail(){
        return repository.getVenditoreModel().getIndirizzo_email();
    }

    private String getAcquirentePassword(){
        return repository.getAcquirenteModel().getPassword();
    }

    private String getVenditorePassword(){
        return repository.getVenditoreModel().getPassword();
    }


    public AcquirenteModel getAcquirente(){
        return repository.getAcquirenteModel();
    }

    public VenditoreModel getVenditore(){
        return repository.getVenditoreModel();
    }

    public ArrayList<SocialAcquirenteModel> getListaSocialAcquirenteRecuperati(){
        return repository.getListaSocialAcquirenteRecuperati();
    }

    public void setSocialAcquirenteRecuperati(ArrayList<SocialAcquirenteModel> lista){
        socialAcquirenteRecuperati.setValue(lista);
    }

    public void setNomeSocial(String nome){
        nomeSocialSelezionato.setValue(nome);
    }

    public void setLinkSocial(String link){
        nomeLinkSelezionato.setValue(link);
    }




    public ArrayList<SocialVenditoreModel> getListaSocialVenditoreRecuperati(){
        return repository.getListaSocialVenditoreRecuperati();
    }

    public void setSocialVenditoreRecuperati(ArrayList<SocialVenditoreModel> lista){
        socialVenditoreRecuperati.setValue(lista);
    }

    public void setAcquirenteRecuperato(AcquirenteModel acquirenteModel){
        acquirenteRecuperato.setValue(acquirenteModel);
        System.out.println("in set acquirente recuperato con dati seguenti:"+ " nome:"+acquirenteModel.getNome() + " email:"+ acquirenteModel.getIndirizzo_email() + " cognome:"+ acquirenteModel.getCognome());
    }

    public void setVenditoreRecuperato(VenditoreModel venditoreModel){
        venditoreRecuperato.setValue(venditoreModel);
        System.out.println("in set venditore recuperato con dati seguenti:"+ " nome:"+venditoreModel.getNome() + " email:"+ venditoreModel.getIndirizzo_email() + " cognome:"+ venditoreModel.getCognome());
    }

    public void recuperaSocialAcquirente(){
        System.out.println("in recupera social acquirente del viewmodel");
        ArrayList<SocialAcquirenteModel> listaSocialAcquirenteRecuperati = getListaSocialAcquirenteRecuperati();
        setSocialAcquirenteRecuperati(listaSocialAcquirenteRecuperati);
    }


    public void recuperaSocialVenditore(){
        System.out.println("in recupera social venditore del viewmodel");
        ArrayList<SocialVenditoreModel> listaSocialVenditoreRecuperati = getListaSocialVenditoreRecuperati();
        setSocialVenditoreRecuperati(listaSocialVenditoreRecuperati);
    }


    public void setApriPopUpAggiungiSocial(Boolean b){
        apriPopUpAggiungiSocial.setValue(b);
    }
    public Boolean getApriPopUpAggiungiSocial(){
        return apriPopUpAggiungiSocial.getValue();
    }


    public void setApriLeMieAste(Boolean b){
        repository.setLeMieAsteUtenteAttuale(true);
        apriLeMieAste.setValue(b);
    }
    public Boolean getApriLeMieAste(){
        return apriLeMieAste.getValue();
    }

    public void resetErroriSocialAggiunti(){
        setMessaggioErroreNomeNuovo("");
        setMessaggioErroreLinkNuovo("");
    }
    public void resetErroriModificaCampoProfilo(){
        setMessaggioErroreNomeNuovo("");
        setMessaggioErroreCognomeNuovo("");
        setMessaggioErroreSitoNuovo("");
        setMessaggioErroreBioNuovo("");
        setMessaggioErrorePaeseNuovo("");
    }
    public void setApriPartecipazioneAste(Boolean b){
        apriPartecipazioneAste.setValue(b);
    }
    public Boolean getApriPartecipazioneAste(){
        return apriPartecipazioneAste.getValue();
    }

    public void setMessaggioErroreSocial(String errore){
        this.messaggioErroreSocial.setValue(errore);
    }
    public Boolean isMessaggioErroreSocial(){
        return !(messaggioErroreSocial.getValue().equals(""));
    }
    public void setMessaggioInfoToast(String messaggio){
        this.messaggioInfoToast.setValue(messaggio);
    }
    public Boolean isMessaggioInfoToast(){
        return !(messaggioInfoToast.getValue().equals(""));
    }

    public void mostraToastInfo(){
        setMessaggioInfoToast("Cliccare un social per modificarlo.");
    }
    public void setModificaCampoProfilo(Boolean b){
        this.modificaCampoProfilo.setValue(b);
    }
    public void setCambiaPassword(Boolean b){
        this.cambiaPassword.setValue(b);
    }
    public void mostraModificaCampoProfilo(){
        setModificaCampoProfilo(true);
    }
    public void mostraCambiaPassword(){
        setCambiaPassword(true);
    }



    public void setNomeSocialSelezionato(String nome){
        repository.setNomeSocialSelezionato(nome);
    }


    public  String getNomeSocialSelezionato(){
       return repository.getNomeSocialSelezionato();
    }


    public void setNomeLinkSelezionato(String link){
        repository.setNomeLinkSelezionato(link);
    }


    public  String getNomeLinkSelezionato(){
        return repository.getNomeLinkSelezionato();
    }

}
