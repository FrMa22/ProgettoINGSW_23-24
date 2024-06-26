package com.example.progettoingsw.viewmodel;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.Repository;

import java.sql.Timestamp;

public class PopUpNuovaOffertaViewModel extends ViewModel {
    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private Asta_inversaRepository astaInversaRepository;
    private SchermataAstaIngleseViewModel schermataAstaIngleseViewModel;
    private SchermataAstaInversaViewModel schermataAstaInversaViewModel;
    public MutableLiveData<Boolean> tipoAstaChecked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isPartecipazioneAvvenuta = new MutableLiveData<>(false);
    public MutableLiveData<String> messaggioPartecipazioneAsta = new MutableLiveData<>("");
    public MutableLiveData<Boolean> offertaValida = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> messaggioErroreOfferta = new MutableLiveData<>(false);
    private String messaggioErrore;
    private String tipoAsta;

    public PopUpNuovaOffertaViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        astaInversaRepository = new Asta_inversaRepository();
        schermataAstaIngleseViewModel = new SchermataAstaIngleseViewModel();
        schermataAstaInversaViewModel = new SchermataAstaInversaViewModel();
    }

    public void checkTipoAsta(){
        if(repository.getAcquirenteModel()!=null){
            setTipoAsta("inglese");
        }else{
            setTipoAsta("inversa");
        }
        setTipoAstaChecked(true);
    }
    public void setTipoAsta(String tipo){
        this.tipoAsta = tipo;
    }
    public void setTipoAstaChecked(Boolean b){
        tipoAstaChecked.setValue(b);
    }
    public Boolean isTipoAstaChecked(){
        return tipoAstaChecked.getValue();
    }
    public Boolean isAstaInglese(){
        return tipoAsta.equals("inglese");
    }
    public Boolean isAstaInversa(){
        return tipoAsta.equals("inversa");
    }
    public String getMessaggioErrore() {
        return messaggioErrore;
    }
    public void setMessaggioErrore(String messaggio) {
        this.messaggioErrore = messaggio;
    }
    public void setMessaggioErroreOfferta(Boolean b) {
        messaggioErroreOfferta.setValue(b);
    }
    public Boolean isMessaggioErroreOfferta(){
        return messaggioErroreOfferta.getValue();
    }
    public void setOffertaValida(Boolean b){
        offertaValida.setValue(b);
    }
    public Boolean isOffertaValida(){
        return offertaValida.getValue();
    }

    public void checkOfferta(String offerta){
        if(offerta==null || offerta.isEmpty()){
            setMessaggioErrore("Si prega di inserire un offerta!");
            setMessaggioErroreOfferta(true);
        }else if (!offerta.matches("^\\d*\\.?\\d+$")) {
            setMessaggioErrore("Si prega di inserire solo numeri per la nuova offerta.");
            setMessaggioErroreOfferta(true);
        } else if (offerta.length()>20) {
            setMessaggioErrore("offerta fuori limite, inseriti più di 20 numeri");
            setMessaggioErroreOfferta(true);
        }else{
            //caso in cui l'offerta è nal valido formato
            if(isAstaInglese()){
                float minimaOffeta = (getRialzoMin()) + (getPrezzoVecchio());
                float offertaAttuale = Float.parseFloat(offerta);
                if(offertaAttuale< minimaOffeta){
                    setMessaggioErrore("L'offerta deve almeno eguagliare il valore dell'offerta attuale + il valore del rialzo minimo!");
                    setMessaggioErroreOfferta(true);
                }else{
                    setOffertaValida(true);
                }

            }else{
                float offertaAttuale = Float.parseFloat(offerta);
                float offertaVecchia = repository.getAsta_inversaSelezionata().getPrezzoAttuale();
                if(offertaAttuale >= (offertaVecchia-0.10)){
                    setMessaggioErrore("l'offerta deve essere inferiore rispetto all'offerta attuale di almeno 0.10€. ");
                    setMessaggioErroreOfferta(true);
                }else{
                    setOffertaValida(true);
                }
            }
        }
    }


    public float getRialzoMin(){
        return repository.getAsta_allingleseSelezionata().getRialzoMin();
    }
    public float getPrezzoVecchio(){
        if(isAstaInglese()){
            return repository.getAsta_allingleseSelezionata().getPrezzoAttuale();
        }else{
            return repository.getAsta_inversaSelezionata().getPrezzoAttuale();
        }

    }
    public void eseguiPartecipazioneAstaInglese(String offerta){
        astaAllingleseRepository.partecipaAsta_allinglese(recuperaIdAstaInglese(), recuperaEmailAcquirente(), offerta, getTempoOfferta(),getStatoAsta(), new Asta_allingleseRepository.OnPartecipazioneAstaIngleseListener() {
            @Override
            public void OnPartecipazioneAstaInglese(Integer risposta) {
                setIsPartecipazioneAvvenuta(true);
                if(risposta!=null && risposta==1){
                    setMessaggioPartecipazioneAsta("Offerta effettuata con successo!");
                }else{
                    setMessaggioPartecipazioneAsta("Errore nell'effettuare l'offerta, riprovare.");
                }

            }
        });
    }
    public void eseguiPartecipazioneAstaInversa(String offerta){
        astaInversaRepository.partecipaAsta_inversa(recuperaIdAstaInversa(), recuperaEmailVenditore(), offerta, getTempoOfferta(),getStatoAsta(), new Asta_inversaRepository.OnPartecipazioneAstaInversaListener() {
            @Override
            public void OnPartecipazioneAstaInversa(Integer risposta) {
                setIsPartecipazioneAvvenuta(true);
                if(risposta!=null && risposta==1){
                    setMessaggioPartecipazioneAsta("Offerta effettuata con successo!");
                }else{
                    setMessaggioPartecipazioneAsta("Errore nell'effettuare l'offerta, riprovare.");
                }

            }
        });
    }
    public void proseguiPartecipazione(String offerta){
        //arrivati a questo metodo abbiamo gia controllato se l'offerta è valida
        if(isAstaInglese()){
            eseguiPartecipazioneAstaInglese(offerta);
        }else{
            eseguiPartecipazioneAstaInversa(offerta);
        }
    }
    public void setMessaggioPartecipazioneAsta(String risposta){
        this.messaggioPartecipazioneAsta.setValue(risposta);
    }
    public String getMessaggioPartecipazioneAsta(){
        return messaggioPartecipazioneAsta.getValue();
    }
    public void setIsPartecipazioneAvvenuta(Boolean b){
        isPartecipazioneAvvenuta.setValue(b);
    }
    public Boolean getIsPartecipazioneAvvenuta(){
        return isPartecipazioneAvvenuta.getValue();
    }
    public Long recuperaIdAstaInglese(){
        return repository.getAsta_allingleseSelezionata().getId();
    }
    public Long recuperaIdAstaInversa()
    {
        return repository.getAsta_inversaSelezionata().getId();
    }
    public String recuperaEmailAcquirente(){
        return repository.getAcquirenteModel().getIndirizzo_email();
    }
    public String recuperaEmailVenditore(){
        return repository.getVenditoreModel().getIndirizzo_email();
    }
    public String getTempoOfferta(){
        Timestamp tempo = new java.sql.Timestamp(System.currentTimeMillis());
        return String.valueOf(tempo);
    }
    public String getStatoAsta(){
        return "attiva";
    }
    public String getMinimaOfferta(){
        float minimaOfferta = getPrezzoVecchio() + getRialzoMin();
        return String.valueOf(minimaOfferta);
    }
    public void resetErroriNuovaOfferta(LifecycleOwner lifecycleOwner){
        setMessaggioErrore("");
        setMessaggioErroreOfferta(false);
        setOffertaValida(false);
        setIsPartecipazioneAvvenuta(false);
        setTipoAsta("");
        setMessaggioPartecipazioneAsta("");
        if(tipoAstaChecked.hasActiveObservers()){
            tipoAstaChecked.removeObservers(lifecycleOwner);
        }
        if(messaggioPartecipazioneAsta.hasActiveObservers()) {
            messaggioPartecipazioneAsta.removeObservers(lifecycleOwner);
        }
        if(offertaValida.hasActiveObservers()) {
            offertaValida.removeObservers(lifecycleOwner);
        }
        if(messaggioErroreOfferta.hasActiveObservers()) {
            messaggioErroreOfferta.removeObservers(lifecycleOwner);
        }
    }

}
