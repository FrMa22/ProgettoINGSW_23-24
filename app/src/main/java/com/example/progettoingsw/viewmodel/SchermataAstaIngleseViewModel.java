package com.example.progettoingsw.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchermataAstaIngleseViewModel extends ViewModel {
    private Repository repository;
    public MutableLiveData<Boolean> isAstaRecuperata = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
    private Asta_allingleseModel astaRecuperata;

    public SchermataAstaIngleseViewModel(){
        repository = Repository.getInstance();
    }

    public void getAstaData(){
        Asta_allingleseModel asta = repository.getAsta_allingleseSelezionata();
        if(asta!=null){
            setAstaRecuperata(asta);
            setIsAstaRecuperata(true);
        }else{
            setErroreRecuperoAsta("Errore nel recupero asta!");
        }
    }
    public void setIsAstaRecuperata(Boolean b){
        isAstaRecuperata.setValue(b);
    }
    public Boolean getIsAstaRecuperata(){
        return isAstaRecuperata.getValue();
    }
    public void setAstaRecuperata(Asta_allingleseModel asta){
        this.astaRecuperata = asta;
    }
    public Asta_allingleseModel getAstaRecuperata(){
        return astaRecuperata;
    }
    public void setErroreRecuperoAsta(String messaggio){
        erroreRecuperoAsta.setValue(messaggio);
    }
    public String getErroreRecuperoAsta(){
        return erroreRecuperoAsta.getValue();
    }
    public Boolean isErroreRecuperoAsta(){
        return !erroreRecuperoAsta.getValue().equals("");
    }

    public String convertiIntervalloOfferte(String intervallo){
        // Ottieni la data e l'ora attuali con il giorno incluso
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Analizza l'intervallo in ore e minuti
        String[] parts = intervallo.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Calcola l'intervallo totale in minuti
        int intervalloMinuti = hours * 60 + minutes;

        // Aggiungi l'intervallo alla data e all'ora attuali
        LocalDateTime scadenza = now.plusMinutes(intervalloMinuti);

        // Formatta il risultato nel formato desiderato
        String scadenzaFormattata = scadenza.format(formatter);
        return scadenzaFormattata;
    }
    public Bitmap convertiImmagine(byte[] immagine){
        if(immagine != null){
        Bitmap bitmap = BitmapFactory.decodeByteArray(immagine, 0, immagine.length);
        return bitmap;
        }else{
            return null;
        }

    }




}
