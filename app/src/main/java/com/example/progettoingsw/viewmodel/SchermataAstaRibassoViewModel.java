package com.example.progettoingsw.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.LoginRepository;
import com.example.progettoingsw.repository.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SchermataAstaRibassoViewModel extends ViewModel {
    private Repository repository;
    private Asta_alribassoRepository astaAlribassoRepository;
    public MutableLiveData<Boolean> isAstaRecuperata = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
    public MutableLiveData<Boolean> tipoUtenteChecked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isAcquistoAvvenuto = new MutableLiveData<>(false);
    private String messaggioAcquistaAstaRibasso;
    private String tipoUtente;
    private Asta_alribassoModel astaRecuperata;

    public SchermataAstaRibassoViewModel(){
        repository = Repository.getInstance();
        astaAlribassoRepository = new Asta_alribassoRepository();
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
    public String getTipoUtente() {
        return tipoUtente;
    }
    public void setTipoUtente(String tipoUtente) {
        this.tipoUtente = tipoUtente;
    }
    public void setTipoUtenteChecked(Boolean b){
        tipoUtenteChecked.setValue(b);
    }
    public Boolean isTipoUtenteChecked(){
        return tipoUtenteChecked.getValue();
    }
    public Boolean isAcquirente(){
        return getTipoUtente().equals("acquirente");
    }
    public void checkTipoUtente(){
        if(repository.getAcquirenteModel()!=null){
            setTipoUtente("acquirente");
        }else{
            setTipoUtente("venditore");
        }
        setTipoUtenteChecked(true);
    }
    public void eseguiAcquistoAsta(){
        astaAlribassoRepository.acquistaAsta_alribasso(recuperaIdAstaRibasso(), recuperaEmailAcquirente(), recuperaPrezzoAttualeAstaRibasso(), new Asta_alribassoRepository.OnAcquistaAstaRibassoListener() {
            @Override
            public void OnAcquistaAstaRibasso(Integer risposta) {
                Log.d("acquisto fatto" , "valore : " + risposta);
                if(risposta==1){
                    setMessaggioAcquistaAstaRibasso("ok");
                }else{
                    setMessaggioAcquistaAstaRibasso("errore");
                }

                setIsAcquistoAvvenuto(true);
            }
        });
    }
    public void setMessaggioAcquistaAstaRibasso(String risposta){
        this.messaggioAcquistaAstaRibasso = risposta;
    }
    public String getMessaggioAcquistaAstaRibasso(){
        return messaggioAcquistaAstaRibasso;
    }
    public void setIsAcquistoAvvenuto(Boolean b){
        isAcquistoAvvenuto.setValue(b);
    }
    public Boolean getIsAcquistoAvvenuto(){
        return isAcquistoAvvenuto.getValue();
    }
    public Long recuperaIdAstaRibasso(){
        return repository.getAsta_alribassoSelezionata().getId();
    }
    public String recuperaEmailAcquirente(){
        return repository.getAcquirenteModel().getIndirizzoEmail();
    }
    public String recuperaPrezzoAttualeAstaRibasso(){
        return String.valueOf(repository.getAsta_alribassoSelezionata().getPrezzoAttuale());
    }


}
