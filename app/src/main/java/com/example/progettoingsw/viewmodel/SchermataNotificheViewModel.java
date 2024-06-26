package com.example.progettoingsw.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.gestori_gui.NotificheAdapter;
import com.example.progettoingsw.model.NotificheAcquirenteModel;
import com.example.progettoingsw.model.NotificheVenditoreModel;
import com.example.progettoingsw.repository.NotificheRepository;
import com.example.progettoingsw.repository.Repository;

import java.util.ArrayList;

public class SchermataNotificheViewModel extends ViewModel {

    private Repository repository;
    private NotificheRepository notificheRepository;

    public MutableLiveData<Boolean> isAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> notificheAssenti = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiInNotificaPopUp = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<NotificheAcquirenteModel>> notificheAcquirenteRecuperate = new MutableLiveData<>(null);
    public MutableLiveData<ArrayList<NotificheVenditoreModel>> notificheVenditoreRecuperate = new MutableLiveData<>(null);
    public SchermataNotificheViewModel(){
        repository = Repository.getInstance();
        notificheRepository = new NotificheRepository();
    }
    public void setIsAcquirente(Boolean b) {
        isAcquirente.setValue(b);
    }
    public void getTipoUtente(){
        if(repository.getAcquirenteModel()!=null){
            setIsAcquirente(true);
        }else{
            setIsAcquirente(false);
        }
    }
    public void getNotificheAcquirente(){
        String indirizzo_email = repository.getAcquirenteModel().getIndirizzo_email();
        notificheRepository.getNotificheAcquirente(indirizzo_email, new NotificheRepository.OnGetNotificheAcquirenteListener() {
            @Override
            public void OnGetNotificheAcquirente(ArrayList<NotificheAcquirenteModel> list) {
                if(list != null) {
                    setNotificheAssenti(false);
                    setNotificheAcquirenteRecuperate(list);
                }else{
                    setNotificheAssenti(true);
                }
            }
        });
    }
    public void getNotificheVenditore(){
        String indirizzo_email = repository.getVenditoreModel().getIndirizzo_email();
        notificheRepository.getNotificheVenditore(indirizzo_email, new NotificheRepository.OnGetNotificheVenditoreListener() {
            @Override
            public void OnGetNotificheVenditore(ArrayList<NotificheVenditoreModel> list) {
                if(list != null) {
                    setNotificheAssenti(false);
                setNotificheVenditoreRecuperate(list);
                }else{
                    setNotificheAssenti(true);
                }
            }
        });
    }
    public void setNotificheAssenti(Boolean b){
        notificheAssenti.setValue(b);
    }
    public void setNotificheAcquirenteRecuperate(ArrayList<NotificheAcquirenteModel> lista){
        notificheAcquirenteRecuperate.setValue(lista);
    }
    public Boolean isNotificheAcquirenteRecuperate(){
        if(notificheAcquirenteRecuperate != null && notificheAcquirenteRecuperate.getValue()!=null){
            return !notificheAcquirenteRecuperate.getValue().isEmpty();
        }else{
            return false;
        }
    }
    public void setNotificheVenditoreRecuperate(ArrayList<NotificheVenditoreModel> lista){
        notificheVenditoreRecuperate.setValue(lista);
    }
    public Boolean isNotificheVenditoreRecuperate(){
        if(notificheVenditoreRecuperate != null && notificheVenditoreRecuperate.getValue()!=null){
            return !notificheVenditoreRecuperate.getValue().isEmpty();
        }else{
            return false;
        }
    }
    public void setVaiInNotificaPopUp(Boolean b) {
        vaiInNotificaPopUp.setValue(b);
    }


    public void gestisciClickRecyclerView(RecyclerView recyclerView, NotificheAdapter notificheAdapter, View v){
        int position = recyclerView.getChildAdapterPosition(v);
        if(isAcquirente.getValue()!=null && isAcquirente.getValue()){
            NotificheAcquirenteModel notificaScelta = notificheAdapter.getClickedNotificaAcquirente(position);
            repository.setNotificaAcquirenteScelta(notificaScelta);
        }else{
            NotificheVenditoreModel notificaScelta = notificheAdapter.getClickedNotificaVenditore(position);
            repository.setNotificaVenditoreScelta(notificaScelta);
        }
        setVaiInNotificaPopUp(true);
    }

}
