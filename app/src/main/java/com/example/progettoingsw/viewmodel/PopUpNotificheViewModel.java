package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.NotificheAcquirenteModel;
import com.example.progettoingsw.model.NotificheVenditoreModel;
import com.example.progettoingsw.repository.NotificheRepository;
import com.example.progettoingsw.repository.Repository;

public class PopUpNotificheViewModel extends ViewModel {

    private Repository repository;
    private NotificheRepository notificheRepository;
    public MutableLiveData<NotificheAcquirenteModel> impostaDatiNotificaAcquirente = new MutableLiveData<>(null);
    public MutableLiveData<NotificheVenditoreModel> impostaDatiNotificaVenditore = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> notificaEliminata = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreEliminazione = new MutableLiveData<>(null);

    public PopUpNotificheViewModel(){
        repository = Repository.getInstance();
        notificheRepository = new NotificheRepository();
    }
    public NotificheAcquirenteModel getImpostaDatiNotificaAcquirente() {
        return impostaDatiNotificaAcquirente.getValue();
    }
    public void setImpostaDatiNotificaAcquirente(NotificheAcquirenteModel notifica) {
        impostaDatiNotificaAcquirente.setValue(notifica);
    }
    public NotificheVenditoreModel getImpostaDatiNotificaVenditore() {
        return impostaDatiNotificaVenditore.getValue();
    }
    public void setImpostaDatiNotificaVenditore(NotificheVenditoreModel notifica) {
        impostaDatiNotificaVenditore.setValue(notifica);
    }
    public void getNotificaData(){
        if(repository.getAcquirenteModel()!=null){
            NotificheAcquirenteModel notificaScelta = repository.getNotificaAcquirenteScelta();
            setImpostaDatiNotificaAcquirente(notificaScelta);
        }else{
            NotificheVenditoreModel notificaScelta = repository.getNotificaVenditoreScelta();
            setImpostaDatiNotificaVenditore(notificaScelta);
        }
    }

    public Boolean getNotificaEliminata() {
        return notificaEliminata.getValue();
    }
    public void setNotificaEliminata(Boolean b) {
        this.notificaEliminata.setValue(b);
    }

    public String getErroreEliminazione() {
        return erroreEliminazione.getValue();
    }
    public void setErroreEliminazione(String messaggio) {
        this.erroreEliminazione.setValue(messaggio);
    }
    public Boolean isErroreEliminazione(){
        if(erroreEliminazione!=null && erroreEliminazione.getValue()!=null) {
            return !erroreEliminazione.getValue().equals("");
        }
        return false;
    }

    public void eliminaNotifica(){
        if(repository.getAcquirenteModel()!=null){
            Long idAsta = repository.getNotificaAcquirenteScelta().getId();
            notificheRepository.deleteNotificheAcquirente(idAsta, new NotificheRepository.OnDeleteNotificheAcquirenteListener() {
                @Override
                public void OnDeleteNotificheAcquirente(Integer result) {
                    if(result==1){
                        setNotificaEliminata(true);
                    }else{
                        setErroreEliminazione("Errore nell'eliminazione");
                    }
                }
            });
        }else{
            Long idAsta = repository.getNotificaVenditoreScelta().getId();
            notificheRepository.deleteNotificheVenditore(idAsta, new NotificheRepository.OnDeleteNotificheVenditoreListener() {
                @Override
                public void OnDeleteNotificheVenditore(Integer result) {
                    if(result==1){
                        setNotificaEliminata(true);
                    }else{
                        setErroreEliminazione("Errore nell'eliminazione");
                    }
                }
            });
        }
    }
}
