package com.example.progettoingsw.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.repository.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SchermataAstaRibassoViewModel extends ViewModel {
    private Repository repository;
    public MutableLiveData<Boolean> isAstaRecuperata = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
    private Asta_alribassoModel astaRecuperata;

    public SchermataAstaRibassoViewModel(){
        repository = Repository.getInstance();
    }

    public void getAstaData(){
        Asta_alribassoModel asta = repository.getAsta_alribassoSelezionata();
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
    public void setAstaRecuperata(Asta_alribassoModel asta){
        this.astaRecuperata = asta;
    }
    public Asta_alribassoModel getAstaRecuperata(){
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
        long orarioAttuale = System.currentTimeMillis();
        long intervalloMillisecondi = convertiIntervallo(intervallo);
        long prossimoDecremento = orarioAttuale + intervalloMillisecondi;
        String orarioProssimoDecremento = convertiOrario(prossimoDecremento);
        return orarioProssimoDecremento;
    }
    private long convertiIntervallo(String intervallo) {
        // Dividi l'intervallo in ore, minuti e secondi
        String[] partiIntervallo = intervallo.split(":");
        long ore = Long.parseLong(partiIntervallo[0]);
        long minuti = Long.parseLong(partiIntervallo[1]);
        long secondi = Long.parseLong(partiIntervallo[2]);
        // Calcola il tempo totale in millisecondi
        long tempoTotaleMillisecondi = (ore * 3600 + minuti * 60 + secondi) * 1000;
        return tempoTotaleMillisecondi;
    }
    private String convertiOrario(long millisecondi) {
        // Converti i millisecondi in un oggetto LocalDateTime utilizzando il fuso orario locale
        LocalDateTime oraLocale = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(millisecondi),
                ZoneId.systemDefault()
        );
        // Formatta l'orario locale in una stringa nel formato hh:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return oraLocale.format(formatter);
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
