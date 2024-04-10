package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.repository.FragmentProfiloRepository;
import com.example.progettoingsw.repository.LoginRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.List;

public class FragmentProfiloViewModel extends ViewModel {

    public MutableLiveData<String> messaggioSocialUtenteNonTrovato = new MutableLiveData<>("");
    private FragmentProfiloRepository fragmentProfiloRepository;
    private Repository repository;

    public FragmentProfiloViewModel(){
        repository = Repository.getInstance();
        fragmentProfiloRepository = new FragmentProfiloRepository();
    }



    public void fragmentProfiloAcquirente(String email){
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

    public void aggiungiSocialAcquirenteViewModel(String nome,String link,String email){
        System.out.println("entrato in fragmentProfilo aggiungi social di viewmodel con email:"+email + "nome:"+ nome + "link:"+ link);
        //if(repository.getSocialAcquirenteModelList()==null){System.out.println("lista social acquirente null");return ;}
        try{
            aggiungiSocialAcquirente(nome,link,email);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void eliminaSocialAcquirenteViewModel(String nome,String link,String email){
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



    public void aggiornaAcquirenteViewModel(String nome,String cognome,String bio,String link,String areageografica,String email){
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

    public void aggiornaPasswordAcquirenteViewModel(String password,String email){
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
        fragmentProfiloRepository.aggiornaPasswordAcquirenteBackend(password,email ,new FragmentProfiloRepository.OnAggiornaPasswordAcquirenteListener() {
            @Override
            public void onAggiornaPasswordAcquirente(String passwordNuovo) {
                repository.updatePasswordAcquirente(passwordNuovo);
            }
        });
    }


    public void setMessaggioUtenteNonTrovato(String messaggio){
        messaggioSocialUtenteNonTrovato.setValue(messaggio);
    }
    private String getMessaggioUtenteNonTrovato() {
        return messaggioSocialUtenteNonTrovato.getValue();
    }



}
