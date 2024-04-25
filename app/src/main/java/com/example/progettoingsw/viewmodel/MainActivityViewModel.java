package com.example.progettoingsw.viewmodel;

import android.util.Log;
import android.view.MenuItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.R;
import com.example.progettoingsw.repository.Repository;

public class MainActivityViewModel extends ViewModel {

    private Repository repository;
    public MutableLiveData<Boolean> sceltoHome = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> sceltoCategorie = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> sceltoCreaAstaAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> sceltoCreaAstaVenditore = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> sceltoRicerca = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> sceltoProfilo = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> apriSchermataAstaInglese = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> apriSchermataAstaRibasso = new MutableLiveData<>(false);



    public MainActivityViewModel(){
        repository = Repository.getInstance();
    }

    private Boolean containsAcquirente(){
        return (repository.getAcquirenteModel()!=null);
    }
    private Boolean containsVenditore(){
        return (repository.getVenditoreModel()!=null);
    }
    public void gestisciFragment(int itemPosition){


        if (itemPosition==1) {
            Log.d("BottomNav", "Selected Home");
            resetAllExcept(1);
            setSceltoHome(true);

        } else if (itemPosition==2) {
            Log.d("BottomNav", "Selected Categories");
            resetAllExcept(2);
            setSceltoCategorie(true);

        } else if (itemPosition==3) {
            Log.d("BottomNav", "Selected Crea Asta");
            if(containsAcquirente()){
                resetAllExcept(3);
                setSceltoCreaAstaAcquirente(true);
            }else if(containsVenditore()){
                resetAllExcept(4);
                setSceltoCreaAstaVenditore(true);
            }

        } else if (itemPosition==4) {
            Log.d("BottomNav", "Selected Search");
            resetAllExcept(5);
            setSceltoRicerca(true);

        } else if (itemPosition==5) {
            Log.d("BottomNav", "Selected Profile");
            resetAllExcept(6);
            setSceltoProfilo(true);

        }





    }
    private Boolean getSceltoHome(){
        return sceltoHome.getValue();
    }
    private void setSceltoHome(Boolean valore){
        this.sceltoHome.setValue(valore);
    }
    private Boolean getSceltoCategorie(){
        return sceltoCategorie.getValue();
    }
    private void setSceltoCategorie(Boolean valore){
        this.sceltoCategorie.setValue(valore);
    }
    private Boolean getSceltoCreaAstaAcquirente(){
        return sceltoCreaAstaAcquirente.getValue();
    }
    private void setSceltoCreaAstaAcquirente(Boolean valore){
        this.sceltoCreaAstaAcquirente.setValue(valore);
    }
    private Boolean getSceltoCreaAstaVenditore(){
        return sceltoCreaAstaVenditore.getValue();
    }
    private void setSceltoCreaAstaVenditore(Boolean valore){
        this.sceltoCreaAstaVenditore.setValue(valore);
    }
    private Boolean getSceltoRicerca(){
        return sceltoRicerca.getValue();
    }
    private void setSceltoRicerca(Boolean valore){
        this.sceltoRicerca.setValue(valore);
    }
    private Boolean getSceltoProfilo(){
        return sceltoProfilo.getValue();
    }
    private void setSceltoProfilo(Boolean valore){
        this.sceltoProfilo.setValue(valore);
    }
    public void resetAllExcept(int position){
        if(position>0){
            if (position!=1) {
                sceltoHome.setValue(null);
            }
            if (position!=2) {
                sceltoCategorie.setValue(null);
            }
            if (position!=3) {
                sceltoCreaAstaAcquirente.setValue(null);
            }
            if (position!=4) {
                sceltoCreaAstaVenditore.setValue(null);
            }
            if (position!=5) {
                sceltoRicerca.setValue(null);
            }
            if (position!=6) {
                sceltoProfilo.setValue(null);
            }
        }
    }

    public boolean isSceltoHome() {
        return (sceltoHome.getValue()!=null && sceltoHome.getValue());
    }

    public boolean isSceltoCategorie() {
        return (sceltoCategorie.getValue()!=null && sceltoCategorie.getValue());
    }

    public boolean isSceltoCreaAstaAcquirente() {
        return (sceltoCreaAstaAcquirente.getValue()!=null && sceltoCreaAstaAcquirente.getValue());
    }

    public boolean isSceltoCreaAstaVenditore() {
        return (sceltoCreaAstaVenditore.getValue()!=null && sceltoCreaAstaVenditore.getValue()) ;
    }

    public boolean isSceltoRicerca() {
        return (sceltoRicerca.getValue()!=null && sceltoRicerca.getValue());
    }

    public boolean isSceltoProfilo() {
        return (sceltoProfilo.getValue()!=null && sceltoProfilo.getValue());
    }

    public void setApriSchermataAstaInglese(Boolean b){
        this.apriSchermataAstaRibasso.setValue(false);
        this.apriSchermataAstaInglese.setValue(b);
    }
    public void setApriSchermataAstaRibasso(Boolean b){
        this.apriSchermataAstaInglese.setValue(false);
        this.apriSchermataAstaRibasso.setValue(b);
    }
}
