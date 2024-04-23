package com.example.progettoingsw.viewmodel;

import android.util.Log;
import android.view.MenuItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.R;
import com.example.progettoingsw.repository.Repository;

public class MainActivityViewModel extends ViewModel {

    private Repository repository;
    public MutableLiveData<MenuItem> sceltoHome = new MutableLiveData<>(null);
    public MutableLiveData<MenuItem> sceltoCategorie = new MutableLiveData<>(null);
    public MutableLiveData<MenuItem> sceltoCreaAstaAcquirente = new MutableLiveData<>(null);
    public MutableLiveData<MenuItem> sceltoCreaAstaVenditore = new MutableLiveData<>(null);
    public MutableLiveData<MenuItem> sceltoRicerca = new MutableLiveData<>(null);
    public MutableLiveData<MenuItem> sceltoProfilo = new MutableLiveData<>(null);
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
    public void gestisciFragment(MenuItem item){


        if (item.getItemId() == R.id.action_home) {
            Log.d("BottomNav", "Selected Home");
            resetAllExcept(sceltoHome);
            setSceltoHome(item);

        } else if (item.getItemId() == R.id.action_categories) {
            Log.d("BottomNav", "Selected Categories");
            resetAllExcept(sceltoCategorie);
            setSceltoCategorie(item);

        } else if (item.getItemId() == R.id.action_crea_asta) {
            Log.d("BottomNav", "Selected Crea Asta");
            if(containsAcquirente()){
                resetAllExcept(sceltoCreaAstaAcquirente);
                setSceltoCreaAstaAcquirente(item);
            }else if(containsVenditore()){
                resetAllExcept(sceltoCreaAstaVenditore);
                setSceltoCreaAstaVenditore(item);
            }

        } else if (item.getItemId() == R.id.action_search) {
            Log.d("BottomNav", "Selected Search");
            resetAllExcept(sceltoRicerca);
            setSceltoRicerca(item);

        } else if (item.getItemId() == R.id.action_profile) {
            Log.d("BottomNav", "Selected Profile");
            resetAllExcept(sceltoProfilo);
            setSceltoProfilo(item);

        }





    }
    private MenuItem getSceltoHome(){
        return sceltoHome.getValue();
    }
    private void setSceltoHome(MenuItem valore){
        this.sceltoHome.setValue(valore);
    }
    private MenuItem getSceltoCategorie(){
        return sceltoCategorie.getValue();
    }
    private void setSceltoCategorie(MenuItem valore){
        this.sceltoCategorie.setValue(valore);
    }
    private MenuItem getSceltoCreaAstaAcquirente(){
        return sceltoCreaAstaAcquirente.getValue();
    }
    private void setSceltoCreaAstaAcquirente(MenuItem valore){
        this.sceltoCreaAstaAcquirente.setValue(valore);
    }
    private MenuItem getSceltoCreaAstaVenditore(){
        return sceltoCreaAstaVenditore.getValue();
    }
    private void setSceltoCreaAstaVenditore(MenuItem valore){
        this.sceltoCreaAstaVenditore.setValue(valore);
    }
    private MenuItem getSceltoRicerca(){
        return sceltoRicerca.getValue();
    }
    private void setSceltoRicerca(MenuItem valore){
        this.sceltoRicerca.setValue(valore);
    }
    private MenuItem getSceltoProfilo(){
        return sceltoProfilo.getValue();
    }
    private void setSceltoProfilo(MenuItem valore){
        this.sceltoProfilo.setValue(valore);
    }
    public void resetAllExcept(MutableLiveData<MenuItem> selectedMutableLiveData) {
        if (selectedMutableLiveData != null) {
            if (selectedMutableLiveData != sceltoHome) {
                sceltoHome.setValue(null);
            }
            if (selectedMutableLiveData != sceltoCategorie) {
                sceltoCategorie.setValue(null);
            }
            if (selectedMutableLiveData != sceltoCreaAstaAcquirente) {
                sceltoCreaAstaAcquirente.setValue(null);
            }
            if (selectedMutableLiveData != sceltoCreaAstaVenditore) {
                sceltoCreaAstaVenditore.setValue(null);
            }
            if (selectedMutableLiveData != sceltoRicerca) {
                sceltoRicerca.setValue(null);
            }
            if (selectedMutableLiveData != sceltoProfilo) {
                sceltoProfilo.setValue(null);
            }
        }
    }

    public boolean isSceltoHome() {
        return sceltoHome.getValue() != null;
    }

    public boolean isSceltoCategorie() {
        return sceltoCategorie.getValue() != null;
    }

    public boolean isSceltoCreaAstaAcquirente() {
        return sceltoCreaAstaAcquirente.getValue() != null;
    }

    public boolean isSceltoCreaAstaVenditore() {
        return sceltoCreaAstaVenditore.getValue() != null;
    }

    public boolean isSceltoRicerca() {
        return sceltoRicerca.getValue() != null;
    }

    public boolean isSceltoProfilo() {
        return sceltoProfilo.getValue() != null;
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
