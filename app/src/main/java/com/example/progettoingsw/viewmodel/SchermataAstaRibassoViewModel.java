package com.example.progettoingsw.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SchermataAstaRibassoViewModel extends ViewModel {
    private Repository repository;
    private Asta_alribassoRepository astaAlribassoRepository;
    public MutableLiveData<Asta_alribassoModel> astaRecuperata = new MutableLiveData<>(null);
    public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
    public MutableLiveData<Boolean> tipoUtenteChecked = new MutableLiveData<>(false);
    public MutableLiveData<String> messaggioAcquistaAstaRibasso = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isAstaInPreferiti = new MutableLiveData<>(false);
    public MutableLiveData<Bitmap> immagineAstaConvertita = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> isAstaChiusa = new MutableLiveData<>(false);
    public MutableLiveData<String> intervalloOfferteConvertito = new MutableLiveData("");
    private Boolean isAcquistoAvvenuto;
    private String tipoUtente;


    public SchermataAstaRibassoViewModel(){
        repository = Repository.getInstance();
        astaAlribassoRepository = new Asta_alribassoRepository();
    }

    public void getAstaData(){
        Long idAsta = repository.getAsta_alribassoSelezionata().getId();
        astaAlribassoRepository.trovaAsta_alribasso(idAsta, new Asta_alribassoRepository.OnTrovaAstaRibassoListener() {
            @Override
            public void OnTrovaAstaRibasso(Asta_alribassoModel astaRecuperata) {
                Log.d("asta ricercata" , "qui");
                if(astaRecuperata!=null){
                    if(astaRecuperata.getPath_immagine()==null){
                        Log.d("immagine null", "null");
                        //astaRecuperata.setImmagine(R.drawable.image_default_low_quality);
                    }
                    repository.setAsta_alribassoSelezionata(astaRecuperata);
                    setAstaRecuperata(astaRecuperata);
                }else{
                    setErroreRecuperoAsta("errore nel recupero asta");
                }
            }
        });
//        Asta_alribassoModel asta = repository.getAsta_alribassoSelezionata();
//        if(asta!=null){
//            setAstaRecuperata(asta);
//            setIsAstaRecuperata(true);
//        }else{
//            setErroreRecuperoAsta("Errore nel recupero asta!");
//        }
    }
    public void setAstaRecuperata(Asta_alribassoModel b){
        astaRecuperata.setValue(b);
    }
    public Asta_alribassoModel getAstaRecuperata(){
        return astaRecuperata.getValue();
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

    public void setIntervalloOfferteConvertito(String intervallo){
        intervalloOfferteConvertito.setValue(intervallo);
    }
    public void convertiIntervalloOfferte(Asta_alribassoModel asta){
        String intervallo = asta.getIntervalloDecrementale();
        long orarioAttuale = System.currentTimeMillis();
        long intervalloMillisecondi = convertiIntervallo(intervallo);
        long prossimoDecremento = orarioAttuale + intervalloMillisecondi;
        String orarioProssimoDecremento = convertiOrario(prossimoDecremento);
        Log.d("convertiIntervalloOfferte", orarioProssimoDecremento);

        setIntervalloOfferteConvertito(orarioProssimoDecremento);
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
    public void setImmagineAstaConvertita(Bitmap img){
        immagineAstaConvertita.setValue(img);
    }
    public void convertiImmagine(byte[] immagine){
        if(immagine != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(immagine, 0, immagine.length);
            setImmagineAstaConvertita(bitmap);
        }else{
            setImmagineAstaConvertita(null);
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
    public Boolean isMessaggioAcquistaAstaRibasso(){
        return !messaggioAcquistaAstaRibasso.getValue().equals("");
    }
    public void eseguiAcquistoAsta(){
        astaAlribassoRepository.acquistaAsta_alribasso(recuperaIdAstaRibasso(), recuperaEmailAcquirente(), recuperaPrezzoAttualeAstaRibasso(), new Asta_alribassoRepository.OnAcquistaAstaRibassoListener() {
            @Override
            public void OnAcquistaAstaRibasso(Integer risposta) {
                Log.d("acquisto fatto" , "valore : " + risposta);
                setIsAcquistoAvvenuto(true);
                if(risposta==1){
                    setMessaggioAcquistaAstaRibasso("ok");
                }else{
                    setMessaggioAcquistaAstaRibasso("errore");
                }
            }
        });
    }
    public void setMessaggioAcquistaAstaRibasso(String risposta){
        messaggioAcquistaAstaRibasso.setValue(risposta);
    }
    public void setIsAcquistoAvvenuto(Boolean b){
        Log.d("setIsAcquistoAvvenuto","entrato nel set");
        isAcquistoAvvenuto = b;
    }
    public Boolean getIsAcquistoAvvenuto(){
        Log.d("getIsAcquistoAvvenuto","entrato nel get");
        return isAcquistoAvvenuto;
    }
    public Long recuperaIdAstaRibasso(){
        return repository.getAsta_alribassoSelezionata().getId();
    }
    public String recuperaEmailAcquirente(){
        return repository.getAcquirenteModel().getIndirizzo_email();
    }
    public String recuperaPrezzoAttualeAstaRibasso(){
        return String.valueOf(repository.getAsta_alribassoSelezionata().getPrezzoAttuale());
    }
    public void setIsAstaChiusa(Boolean b){
        isAstaChiusa.setValue(b);
    }
    public void isAstaChiusa(){
        setIsAstaChiusa(!repository.getAsta_alribassoSelezionata().getCondizione().equals("aperta"));
    }
    public void setIsAstaInPreferiti(Boolean b){
        isAstaInPreferiti.setValue(b);
    }
    public Boolean getIsAstaInPreferiti(){
        return isAstaInPreferiti.getValue();
    }
    public void verificaAstaInPreferiti(){
        String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
        Long idAsta = repository.getAsta_alribassoSelezionata().getId();
        astaAlribassoRepository.verificaAstaRibassoInPreferiti(indirizzoEmail,idAsta, new Asta_alribassoRepository.OnVerificaAstaRibassoInPreferitiListener() {
            @Override
            public void OnVerificaAstaRibassoInPreferiti(Integer numeroRecuperato) {
                Log.d("asta ricercata" , "qui");
                if(numeroRecuperato!=null && numeroRecuperato!=0){
                    Log.d("asta ricercata" , "è nei preferiti");
                    setIsAstaInPreferiti(true);
                }else{
                    Log.d("asta ricercata" , "non è nei preferiti");
                    setErroreRecuperoAsta("errore nella verifica asta in preferiti");
                    setIsAstaInPreferiti(false);
                }
            }
        });
    }
    public void inserimentoAstaInPreferiti(){
        String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
        Long idAsta = repository.getAsta_alribassoSelezionata().getId();
        astaAlribassoRepository.inserimentoAstaInPreferiti(idAsta, indirizzoEmail, new Asta_alribassoRepository.OnInserimentoAstaRibassoInPreferitiListener() {
            @Override
            public void OnInserimentoAstaRibassoInPreferiti(Integer numeroRecuperato) {
                Log.d("asta inserita in preferiti" , "qui");
                if(numeroRecuperato!=null && numeroRecuperato==1){
                    //setAstaInPreferiti(true);
                    setIsAstaInPreferiti(true);
                }else{
                    setErroreRecuperoAsta("errore nella verifica asta in preferiti");
                    //setVerificaAstaInPreferitiChecked(true);
                }
            }
        });
    }
    public void eliminazioneAstaInPreferiti(){
        String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
        Long idAsta = repository.getAsta_alribassoSelezionata().getId();
        astaAlribassoRepository.eliminazioneAstaInPreferiti(idAsta, indirizzoEmail, new Asta_alribassoRepository.OnEliminazioneAstaRibassoInPreferitiListener() {
            @Override
            public void OnEliminazioneAstaRibassoInPreferiti(Integer numeroRecuperato) {
                Log.d("asta eliminata in preferiti" , "qui");
                if(numeroRecuperato!=null && numeroRecuperato==1){
                    //setAstaInPreferiti(false);
                    setIsAstaInPreferiti(false);
                }else{
                    setErroreRecuperoAsta("errore nella verifica asta in preferiti");
                    //setVerificaAstaInPreferitiChecked(true);
                }
            }
        });
    }


}
