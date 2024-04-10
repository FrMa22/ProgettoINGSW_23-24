package com.example.progettoingsw.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Repository;

import java.sql.Timestamp;

public class PopUpNuovaOffertaViewModel extends ViewModel {
    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private SchermataAstaIngleseViewModel schermataAstaIngleseViewModel;
    public MutableLiveData<Boolean> tipoAstaChecked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isPartecipazioneAvvenuta = new MutableLiveData<>(false);
    private String messaggioPartecipazioneAstaInglese;
    public MutableLiveData<Boolean> offertaValida = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> messaggioErroreOfferta = new MutableLiveData<>(false);

    private String messaggioErrore;
    private String tipoAsta;

    public PopUpNuovaOffertaViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
        schermataAstaIngleseViewModel = new SchermataAstaIngleseViewModel();
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
        Log.d("checkOfferta", "valore : " + offerta);
        if(offerta==null || offerta.isEmpty()){
            Log.d("checkOfferta", "caso in cui offerta è null o vuoto");
            setMessaggioErrore("Si prega di inserire un offerta!");
            setMessaggioErroreOfferta(true);
        }else if (!offerta.matches("^\\d*\\.?\\d+$")) {
            Log.d("checkOfferta", "caso in cui offerta è di un formato non valido");
            setMessaggioErrore("Si prega di inserire solo numeri per la nuova offerta.");
            setMessaggioErroreOfferta(true);
        } else if (offerta.length()>20) {
            Log.d("checkOfferta", "caso in cui offerta è piu lungo di 20");
            setMessaggioErrore("offerta fuori limite, inseriti più di 20 numeri");
            setMessaggioErroreOfferta(true);
        }else{
            //caso in cui l'offerta è nal valido formato
            if(isAstaInglese()){
                float minimaOffeta = (getRialzoMin()) + (getPrezzoVecchio());
                float offertaAttuale = Float.parseFloat(offerta);
                if(offertaAttuale<= minimaOffeta){
                    Log.d("checkOfferta", "caso in cui offerta è minore o uguale a minima offerta");
                    setMessaggioErrore("L'offerta deve superare il valore dell'offerta attuale + il valore del rialzo minimo!");
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
        return repository.getAsta_allingleseSelezionata().getPrezzoAttuale();
    }
    public void eseguiPartecipazioneAsta(String offerta){
        astaAllingleseRepository.partecipaAsta_allinglese(recuperaIdAstaInglese(), recuperaEmailAcquirente(), offerta, getTempoOfferta(),getStatoAsta(), new Asta_allingleseRepository.OnPartecipazioneAstaIngleseListener() {
            @Override
            public void OnPartecipazioneAstaInglese(Integer risposta) {
                Log.d("partecipazione fatta" , "valore : " + risposta);
                if(risposta!=null && risposta==1){
                    setMessaggioPartecipazioneAstaInglese("ok");
                }else{
                    setMessaggioPartecipazioneAstaInglese("errore nel recupero del valore di ritorno ");
                }
                setIsPartecipazioneAvvenuta(true);
            }
        });
    }
    public void proseguiPartecipazione(String offerta){
        //arrivati a questo metodo abbiamo gia controllato se l'offerta è valida
        if(isAstaInglese()){
            eseguiPartecipazioneAsta(offerta);
        }else{
            //partecipa asta inversa
        }
    }
    public void setMessaggioPartecipazioneAstaInglese(String risposta){
        this.messaggioPartecipazioneAstaInglese = risposta;
    }
    public String getMessaggioPartecipazioneAstaInglese(){
        return messaggioPartecipazioneAstaInglese;
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
    public String recuperaEmailAcquirente(){
        return repository.getAcquirenteModel().getIndirizzoEmail();
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


}
