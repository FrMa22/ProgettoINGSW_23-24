package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.repository.LoginRepository;
import com.example.progettoingsw.repository.Repository;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> messaggioErrorePassword = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioErroreEmail = new MutableLiveData<>("");
    public MutableLiveData<String> messaggioUtenteNonTrovato = new MutableLiveData<>("");
    public MutableLiveData<Boolean> proseguiLogin = new MutableLiveData<>(false);
    private LoginRepository loginRepository;
    private Repository repository;
    private AcquirenteModel acquirenteModel;
    public LoginViewModel(){
        repository = Repository.getInstance();
        loginRepository = new LoginRepository();
    }

    public void login(String email, String password){
        System.out.println("entrato in login di viewmodel");
        if(loginValido(email,password)){
            System.out.println("in login di viewmodel prima del try");
            try{
                    trovaUtente(email,password);
                    if(repository.getAcquirenteModel()==null){
                        setMessaggioUtenteNonTrovato("utente non trovato");
                    }else{
                        setProseguiLogin();
                    }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setProseguiLogin() {
        proseguiLogin.setValue(true);
    }
    public Boolean getProseguiLogin(){
        return proseguiLogin.getValue();
    }
    public Boolean isProseguiLogin(){
        return getProseguiLogin();
    }

    private void trovaUtente(String email, String password) {
        System.out.println("entrato in trova utente di view model");
        loginRepository.loginBackend(email, password, new LoginRepository.OnLoginListener() {
            @Override
            public void onLogin(AcquirenteModel acquirenteModel) {
                repository.setAcquirenteModel(acquirenteModel);
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


}
