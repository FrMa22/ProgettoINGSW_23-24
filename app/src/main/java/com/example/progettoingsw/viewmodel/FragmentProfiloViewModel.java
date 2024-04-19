package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
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

    public MutableLiveData<Boolean> socialVenditorePresenti = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> entraInPopUpModificaSocial = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> apriPopUpAggiungiSocial = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> apriLeMieAste = new MutableLiveData<>(false);

    public MutableLiveData<ArrayList<SocialAcquirenteModel>> socialAcquirenteRecuperati = new MutableLiveData<>(null);

    public MutableLiveData<ArrayList<SocialVenditoreModel>> socialVenditoreRecuperati = new MutableLiveData<>(null);

    public MutableLiveData<AcquirenteModel> acquirenteRecuperato =new MutableLiveData<>(null);

    public MutableLiveData<VenditoreModel> venditoreRecuperato =new MutableLiveData<>(null);


    public MutableLiveData<Boolean> esci =new MutableLiveData<>(false);

    private FragmentProfiloRepository fragmentProfiloRepository;
    private Repository repository;

    public FragmentProfiloViewModel(){
        repository = Repository.getInstance();
        fragmentProfiloRepository = new FragmentProfiloRepository();
    }
    public void logout(){
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
        String email=getAcquirenteEmail();
        System.out.println("entrato in fragmentProfilo di viewmodel con email:"+email);
            System.out.println("in fragmentProfiloAcquirente di viewmodel prima del try con email:"+email);
            if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
            try{
                trovaSocialAcquirente(email);
                if(repository.getSocialAcquirenteModelList().isEmpty()){
                    setMessaggioUtenteNonTrovato("acquirente non ha social");
                } //altrimenti trova i social e non fa nulla perchè verranno semplicemente messi
            } catch (Exception e){
                e.printStackTrace();
            }

    }


    public void aggiungiSocialViewModel(String nome,String link){
        if(containsAcquirente()){
            aggiungiSocialAcquirenteViewModel(nome,link);
        }else if(containsVenditore()){
            aggiungiSocialVenditoreViewModel(nome,link);
        }

    }

    public void aggiornaSocialViewModel(String oldnome,String oldlink,String nome,String link){
        if(containsAcquirente()){
            aggiornaSocialAcquirenteViewModel(oldnome,oldlink,nome,link);
        }else if(containsVenditore()){
            aggiornaSocialVenditoreViewModel(oldnome,oldlink,nome,link);
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

    public void aggiornaViewModel(String nome,String cognome,String bio,String sitoweb,String paese){
        if(containsAcquirente()){
            aggiornaAcquirenteViewModel(nome,cognome,bio,sitoweb,paese);
        }
        else if(containsVenditore()){
           aggiornaVenditoreViewModel(nome,cognome,bio,sitoweb,paese);
        }


    }


    public void aggiornaPasswordViewModel(String password,String passwordNuova ){
        if(containsAcquirente()){
            if(password.equals(getAcquirentePassword())) {
                aggiornaPasswordAcquirenteViewModel(passwordNuova);
            }
        }
        else if(containsVenditore()){

            if(password.equals(getVenditorePassword())) {
                aggiornaPasswordVenditoreViewModel(passwordNuova);
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
                    setSocialAcquirentePresenti(true);
                }
            }
        });
    }

    private void aggiungiSocialAcquirente(String nome,String link,String email) {
        System.out.println("entrato in aggiungi Social acquirente di view model");
        fragmentProfiloRepository.aggiungiSocialAcquirenteBackend(nome,link,email ,new FragmentProfiloRepository.OnAggiungiSocialAcquirenteListener() {
            @Override
            public void onAggiungiSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel) {
                repository.addSocialAcquirente(socialAcquirenteModel);
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
            }
        });
    }


    private void aggiornaAcquirente(String nome,String cognome,String bio,String link,String areageografica,String email) {
        System.out.println("entrato in aggiorna  acquirente di view model");
        fragmentProfiloRepository.aggiornaAcquirenteBackend(nome,cognome,bio,link,areageografica,email ,new FragmentProfiloRepository.OnAggiornaAcquirenteListener() {
            @Override
            public void onAggiornaAcquirente(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo) {
                repository.updateAcquirente(nomeNuovo,cognomeNuovo,bioNuovo,linkNuovo,areageograficaNuovo);
            }
        });
    }


    private void aggiornaPasswordAcquirente(String password,String email) {
        System.out.println("entrato in aggiorna password  acquirente di view model");


            fragmentProfiloRepository.aggiornaPasswordAcquirenteBackend(password, email, new FragmentProfiloRepository.OnAggiornaPasswordAcquirenteListener() {
                @Override
                public void onAggiornaPasswordAcquirente(String passwordNuovo) {
                    repository.updatePasswordAcquirente(passwordNuovo);
                }
            });

    }


    //versione venditore

    public void trovaSocialVenditoreViewModel(){
        String email=getVenditoreEmail();
        System.out.println("entrato in fragmentProfilo di viewmodel con email:"+email);
        System.out.println("in fragmentProfiloVenditore di viewmodel prima del try con email:"+email);
        if(repository.getSocialVenditoreModelList()==null){System.out.println("lista social Venditore null");return ;}
        try{
            trovaSocialVenditore(email);
            if(repository.getSocialVenditoreModelList().isEmpty()){
                setMessaggioUtenteNonTrovato("acquirente non ha social");
            } //altrimenti trova i social e non fa nulla perchè verranno semplicemente messi
        } catch (Exception e){
            e.printStackTrace();
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
                    setSocialVenditorePresenti(true);
                }
            }
        });
    }

    private void aggiungiSocialVenditore(String nome,String link,String email) {
        System.out.println("entrato in aggiungi Social Venditore di view model");
        fragmentProfiloRepository.aggiungiSocialVenditoreBackend(nome,link,email ,new FragmentProfiloRepository.OnAggiungiSocialVenditoreListener() {
            @Override
            public void onAggiungiSocialVenditore(SocialVenditoreModel socialVenditoreModel) {
                repository.addSocialVenditore(socialVenditoreModel);
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
            }
        });
    }


    private void aggiornaVenditore(String nome,String cognome,String bio,String link,String areageografica,String email) {
        System.out.println("entrato in aggiorna  Venditore di view model");
        fragmentProfiloRepository.aggiornaVenditoreBackend(nome,cognome,bio,link,areageografica,email ,new FragmentProfiloRepository.OnAggiornaVenditoreListener() {
            @Override
            public void onAggiornaVenditore(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo) {
                repository.updateVenditore(nomeNuovo,cognomeNuovo,bioNuovo,linkNuovo,areageograficaNuovo);
            }
        });
    }


    private void aggiornaPasswordVenditore(String password,String email) {
        System.out.println("entrato in aggiorna password  Venditore di view model");
            fragmentProfiloRepository.aggiornaPasswordVenditoreBackend(password, email, new FragmentProfiloRepository.OnAggiornaPasswordVenditoreListener() {
                @Override
                public void onAggiornaPasswordVenditore(String passwordNuovo) {
                    repository.updatePasswordVenditore(passwordNuovo);
                }
            });


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
        apriLeMieAste.setValue(b);
    }
    public Boolean getApriLeMieAste(){
        return apriLeMieAste.getValue();
    }

}
