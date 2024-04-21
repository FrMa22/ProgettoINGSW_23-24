package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.LoginRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> messaggioErrorePassword = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreEmail = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioUtenteNonTrovato = new MutableLiveData<>("");
    public MutableLiveData<String> proseguiLogin = new MutableLiveData<>("");
    public MutableLiveData<String> tokenSalvato = new MutableLiveData<>("");

    private String tokenViewModel;

    private LoginRepository loginRepository;
    private Repository repository;
    private static final String TOKEN_KEY = "token";
    public LoginViewModel(){
        repository = Repository.getInstance();
        loginRepository = new LoginRepository();
    }

    public String getTokenViewModel() {
        return tokenViewModel;
    }
    public void setTokenViewModel(String tokenViewModel) {
        Log.d("setTokenViewModel","token salvato localmente: " + tokenViewModel);
        this.tokenViewModel = tokenViewModel;
    }
    public String getTokenSalvato(){
        return tokenSalvato.getValue();
    }
    public void setTokenSalvato(String token){
        this.tokenSalvato.setValue(token);
    }
    public Boolean isTokenSalvato(){
        return !tokenSalvato.getValue().equals("");
    }
//    public void checkSavedToken(Context context){
//        Log.d("checkSavedToken","controllo il token");
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString(TOKEN_KEY, null);
//        if(token != null){
//            Log.d("checkSavedToken","token recuperato : " + token);
//            setTokenSalvato(token);
//        }else{
//            Log.d("checkSavedToken","token non trovato : ");
//        }
//
//        setTokenSalvato("");
//    }
    public void loginAcquirenteConToken(String token) {
        setTokenViewModel(token);
        if (token != null && !token.isEmpty()) {
            loginRepository.loginAcquirenteConTokenBackend(token, new LoginRepository.OnLoginAcquirenteConTokenListener() {
                @Override
                public void onLoginConToken(AcquirenteModel acquirenteModel) {
                    if(acquirenteModel!=null){
                        //accesso come utente
                        Log.d("onLoginConToken","acquirente trovato");
                        repository.setAcquirenteModel(acquirenteModel);
                        trovaCategorieAcquirenteConToken(acquirenteModel.getIndirizzo_email());
                    }else{
                        //accesso come venditore ma va trovato
                        Log.d("onLoginConToken","acquirente non trovato, cerco venditore");
                        loginVenditoreConToken();
                    }
                }
            });
        }
    }
    public void trovaCategorieAcquirenteConToken(String indirizzo_email){
        if(indirizzo_email != null && !indirizzo_email.isEmpty()){
            Log.d("trovaCategorieAcquirenteConToken", "cerco con email " + indirizzo_email);
            loginRepository.recuperaCategorieAcquirenteBackend(indirizzo_email, new LoginRepository.OnRecuperaCategorieAcquirenteListener() {
                @Override
                public void onRecuperaCategorieAcquirente(ArrayList<String> listaCategorie) {
                    if(listaCategorie!=null && !listaCategorie.isEmpty()){
                        Log.d("onRecuperaCategorieAcquirente","categorie acquirente trovate");
                        repository.setListaCategorieAcquirente(listaCategorie);
                    }
                    Log.d("onRecuperaCategorieAcquirente","prosegui login");
                    setProseguiLogin("acquirente");
                }
            });
        }
    }
    public void loginVenditoreConToken(){
        loginRepository.loginVenditoreConTokenBackend(getTokenViewModel(), new LoginRepository.OnLoginVenditoreConTokenListener() {
            @Override
            public void onLoginConToken(VenditoreModel venditoreModel) {
                if(venditoreModel != null){
                    repository.setVenditoreModel(venditoreModel);
                    trovaCategorieVenditoreConToken(venditoreModel.getIndirizzo_email());
                }else{
                    setMessaggioUtenteNonTrovato("nessun utente trovato");
                }
            }
        });
    }
    public void trovaCategorieVenditoreConToken(String indirizzo_email){
        if(indirizzo_email != null && !indirizzo_email.isEmpty()){
            Log.d("trovaCategorieVenditoreConToken", "cerco con email " + indirizzo_email);
            loginRepository.recuperaCategorieVenditoreBackend(indirizzo_email, new LoginRepository.OnRecuperaCategorieVenditoreListener() {
                @Override
                public void onRecuperaCategorieVenditore(ArrayList<String> listaCategorie) {
                    if(listaCategorie!=null && !listaCategorie.isEmpty()){
                        Log.d("onRecuperaCategorieVenditore","categorie Venditore trovate");
                        repository.setListaCategorieVenditore(listaCategorie);
                    }
                    Log.d("onRecuperaCategorieVenditore","prosegui login");
                    setProseguiLogin("venditore");
                }
            });
        }
    }

    public void loginAcquirente(String email, String password,String token){
        System.out.println("entrato in login di viewmodel");
        if(loginValido(email,password)){
            System.out.println("in login di viewmodel prima del try");
            try{
                setTokenViewModel(token);
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

    public String generaFCMToken() {
        Task<String> task = FirebaseMessaging.getInstance().getToken();
        while (!task.isComplete()) {
            // Wait for the task to complete
        }
        if (task.isSuccessful()) {
            String token = task.getResult();
            return token;
        } else {
            // Handle error
            return null;
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
    public void sceltoTipoAccount(String tipo){
        if(tipo.equals("acquirente")){
            repository.setVenditoreModel(null);
            mandaTokenAcquirenteBackend(repository.getAcquirenteModel().getIndirizzo_email(),getTokenViewModel());
            setProseguiLogin("acquirente");
        }else{
            repository.setAcquirenteModel(null);
            mandaTokenVenditoreBackend(repository.getVenditoreModel().getIndirizzo_email(),getTokenViewModel());
            setProseguiLogin("venditore");
        }
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
                    trovaVenditore(email,password);
                }else{
                    trovaCategorieAcquirente(email,password);
                }
            }
        });
    }
    public void mandaTokenAcquirenteBackend(String email, String token){
        loginRepository.setTokenAcquirente(email, token, new LoginRepository.OnSetTokenAcquirenteListener() {
            @Override
            public void onSetTokenAcquirente(Integer valore) {
                if(valore>0){
                    Log.d("TOKEN", "Token inviato con successo");
                }else{
                    Log.d("TOKEN", "Problema con l'invio del token");
                }
            }
        });
    }
    public void mandaTokenVenditoreBackend(String email, String token){
        loginRepository.setTokenVenditore(email, token, new LoginRepository.OnSetTokenVenditoreListener() {
            @Override
            public void onSetTokenVenditore(Integer valore) {
                if(valore>0){
                    Log.d("TOKEN", "Token inviato con successo");
                }else{
                    Log.d("TOKEN", "Problema con l'invio del token");
                }
            }
        });
    }
    private void trovaCategorieAcquirente(String email,String password) {
        loginRepository.recuperaCategorieAcquirenteBackend(email, new LoginRepository.OnRecuperaCategorieAcquirenteListener() {
            @Override
            public void onRecuperaCategorieAcquirente(ArrayList<String> list) {
                repository.setListaCategorieAcquirente(list);
                trovaVenditore(email,password);
                Log.d("categorie acquirente", "" + list);
                //setProseguiLogin("acquirente");
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
                    trovaCategorieVenditore(email);
                }else{
                    if(repository.getAcquirenteModel()==null){
                        setMessaggioUtenteNonTrovato("nessuna tipologia di utente trovato");
                    }else{
                        setMessaggioUtenteNonTrovato("venditore non trovato");
                        mandaTokenAcquirenteBackend(repository.getAcquirenteModel().getIndirizzo_email(),getTokenViewModel());
                        setProseguiLogin("acquirente");
                    }
                }
                Log.d("trovaVenditore on Login " , "valore di venditore model : " + venditoreModel);
            }
        });
    }
    private void trovaCategorieVenditore(String email) {
        loginRepository.recuperaCategorieVenditoreBackend(email, new LoginRepository.OnRecuperaCategorieVenditoreListener() {
            @Override
            public void onRecuperaCategorieVenditore(ArrayList<String> list) {
                repository.setListaCategorieVenditore(list);
                Log.d("categorie venditore", "" + list);
                if(repository.getAcquirenteModel()!=null){
                    setProseguiLogin("entrambi");
                }else{
                    mandaTokenVenditoreBackend(repository.getVenditoreModel().getIndirizzo_email(),getTokenViewModel());
                    setProseguiLogin("venditore");
                }
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
    public Boolean isNessunUtenteTrovatoConToken(){
        return (getTokenViewModel()!=null && getMessaggioUtenteNonTrovato().equals("nessun utente trovato"));
    }

    private String getMessaggioUtenteNonTrovato() {
        return messaggioUtenteNonTrovato.getValue();
    }


}
