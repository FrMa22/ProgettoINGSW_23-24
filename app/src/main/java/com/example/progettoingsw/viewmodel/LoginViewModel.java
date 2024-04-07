package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.LoginRepository;
import com.example.progettoingsw.repository.Repository;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> messaggioErrorePassword = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreEmail = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioUtenteNonTrovato = new MutableLiveData<>("");
    public MutableLiveData<String> proseguiLogin = new MutableLiveData<>("");
    private LoginRepository loginRepository;
    private Repository repository;
    private AcquirenteModel acquirenteModel;
    public LoginViewModel(){
        repository = Repository.getInstance();
        loginRepository = new LoginRepository();
    }

    public void loginAcquirente(String email, String password){
        System.out.println("entrato in login di viewmodel");
        if(loginValido(email,password)){
            System.out.println("in login di viewmodel prima del try");
            try{
                trovaAcquirente(email,password);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void loginVenditore(String email, String password){
        System.out.println("entrato in login di viewmodel");
        if(loginValido(email,password)){
            System.out.println("in login di viewmodel prima del try");
            try{
                trovaVenditore(email,password);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setProseguiLogin(String tipo) {
        if(getProseguiLogin().equals("")){
            proseguiLogin.setValue(tipo);
        }else{
            proseguiLogin.setValue(tipo);
        }
    }
    public String getProseguiLogin(){
        return proseguiLogin.getValue();
    }
    public Boolean isProseguiLogin(String tipo){
        return getProseguiLogin().equals(tipo);
    }

    private void trovaAcquirente(String email, String password) {
        System.out.println("entrato in trova acquirente di view model");
        loginRepository.loginAcquirenteBackend(email, password, new LoginRepository.OnLoginAcquirenteListener() {
            @Override
            public void onLogin(AcquirenteModel acquirenteModel) {
                repository.setAcquirenteModel(acquirenteModel);
                Log.d("trovaAcquirente on Login " , "valore di acquirente model : " + acquirenteModel);
                if(repository.getAcquirenteModel()==null){
                    setMessaggioUtenteNonTrovato("acquirente non trovato");
                }else{
                    setProseguiLogin("acquirente");
                }
            }
        });
    }
    private void trovaVenditore(String email, String password) {
        System.out.println("entrato in trova venditore di view model");
        loginRepository.loginVenditoreBackend(email, password, new LoginRepository.OnLoginVenditoreListener() {
            @Override
            public void onLogin(VenditoreModel venditoreModel) {
                repository.setVenditoreModel(venditoreModel);
                if(repository.getVenditoreModel()!=null){
                    if(repository.getAcquirenteModel()!=null){
                        setProseguiLogin("entrambi");
                    }else{
                        setProseguiLogin("venditore");
                    }
                }else{
                    if(repository.getAcquirenteModel()==null){
                        setMessaggioUtenteNonTrovato("nessuna tipologia di utente trovato");
                    }else{
                        setMessaggioUtenteNonTrovato("venditore non trovato");
                    }
                }
                Log.d("trovaVenditore on Login " , "valore di venditore model : " + venditoreModel);
//                if(isProseguiLogin("acquirente")){
//                    Log.d("trovaVenditore" , "isProseguiLogin è acquirente");
//                    if(repository.getVenditoreModel() == null){
//                        Log.d("trovaVenditore" , "isProseguiLogin è acquirente e venditore null");
//                        return;
//                    }else{
//                        Log.d("trovaVenditore" , "isProseguiLogin è acquirente e venditore non null");
//                        setProseguiLogin("venditore");
//                    }
//                }else {
//                    Log.d("trovaVenditore" , "isProseguiLogin non è acquirente");
//                    if (repository.getVenditoreModel() == null) {
//                        setMessaggioUtenteNonTrovato("utente non trovato");
//                    } else {
//                        setProseguiLogin("venditore");
//                    }
//                }
            }
        });
    }

    public Boolean loginValido(String mail, String password){
        System.out.println("entrato in login valido");
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
        }else{
            return true;
        }
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
    public void setMessaggioUtenteNonTrovato(String messaggio){
        messaggioUtenteNonTrovato.setValue(messaggio);
    }
    public Boolean isMessaggioUtenteNonTrovato(){
        return !getMessaggioUtenteNonTrovato().equals("");
    }

    private String getMessaggioUtenteNonTrovato() {
        return messaggioUtenteNonTrovato.getValue();
    }


}
