package com.example.progettoingsw.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.Repository;

public class SchermataAstaInversaViewModel extends ViewModel {
    private Repository repository;
    public MutableLiveData<Asta_inversaModel> astaRecuperata = new MutableLiveData<>(null);
    public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
    public MutableLiveData<Boolean> tipoUtenteAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isPartecipazioneAvvenuta = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isAstaInPreferiti = new MutableLiveData<>(false);
    public MutableLiveData<Bitmap> immagineAstaConvertita = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> isAstaChiusa = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isUltimaOffertaTua = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiInAcquirente = new MutableLiveData<>(false);
    public MutableLiveData<Asta_inversaModel> astadaMostrare = new MutableLiveData<>(null);
    private String messaggioPartecipazioneAstaInglese;
    private Asta_inversaRepository astaInversaRepository;

    public SchermataAstaInversaViewModel(){
        repository = Repository.getInstance();
        astaInversaRepository = new Asta_inversaRepository();
    }

    public void getAstaData(){
        Log.d("getAstaData","entrato");
        Long idAsta = repository.getAsta_inversaSelezionata().getId();
        astaInversaRepository.trovaAsta_inversa(idAsta, new Asta_inversaRepository.OnTrovaAstaInversaListener() {
            @Override
            public void OnTrovaAstaInversa(Asta_inversaModel astaRecuperata) {
                Log.d("asta ricercata" , "qui");
                if(astaRecuperata!=null){
                    repository.setAsta_inversaSelezionata(astaRecuperata);
                    setAstaRecuperata(astaRecuperata);
                }else{
                    setErroreRecuperoAsta("errore nel recupero asta");
                }
            }
        });
    }
    public Boolean getIsUltimaOffertaTua() {
        return isUltimaOffertaTua.getValue();
    }

    public void setIsUltimaOffertaTua(Boolean isUltimaOffertaTua) {
        this.isUltimaOffertaTua.setValue(isUltimaOffertaTua);
    }
    public void checkUltimaOfferta(){
        Long id_asta = repository.getAsta_inversaSelezionata().getId();
        VenditoreModel venditoreModel=repository.getVenditoreModel();
        if(venditoreModel!=null) {
            String indirizzo_email = repository.getVenditoreModel().getIndirizzo_email();

            astaInversaRepository.getEmailVincente(indirizzo_email, id_asta, new Asta_inversaRepository.OnGetEmailVincenteListener() {
                @Override
                public void OnGetEmailVincente(Boolean numeroRecuperato) {
                    if (numeroRecuperato) {
                        setIsUltimaOffertaTua(true);
                    } else {
                        setIsUltimaOffertaTua(false);
                    }
                }
            });
        }
    }
    public void setAstaRecuperata(Asta_inversaModel asta){
        astaRecuperata.setValue(asta);
    }
    public Asta_inversaModel getAstaRecuperata(){
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



    public void setTipoUtenteAcquirente(Boolean b){
        tipoUtenteAcquirente.setValue(b);
    }

    public void checkTipoUtente(){
        setTipoUtenteAcquirente(repository.getAcquirenteModel() != null);
    }
    public void setIsPartecipazioneAvvenuta(Boolean b){
        isPartecipazioneAvvenuta.setValue(b);
    }
    public Boolean getIsPartecipazioneAvvenuta(){
        return isPartecipazioneAvvenuta.getValue();
    }
    public void setMessaggioPartecipazioneAstaInglese(String messaggio){
        this.messaggioPartecipazioneAstaInglese = messaggio;
    }
    public String getMessaggioPartecipazioneAstaInglese(){
        return messaggioPartecipazioneAstaInglese;
    }

    public void setIsAstaChiusa(Boolean b){
        isAstaChiusa.setValue(b);
    }
    public void isAstaChiusa(){
        setIsAstaChiusa(!repository.getAsta_inversaSelezionata().getCondizione().equals("aperta"));
    }
    public void setIsAstaInPreferiti(Boolean b){
        isAstaInPreferiti.setValue(b);
    }
    public Boolean getIsAstaInPreferiti(){
        return isAstaInPreferiti.getValue();
    }
    public void verificaAstaInPreferiti(){
        VenditoreModel venditoreModel=repository.getVenditoreModel();
        if(venditoreModel!=null) {
            String indirizzoEmail = repository.getVenditoreModel().getIndirizzo_email();
            Long idAsta = repository.getAsta_inversaSelezionata().getId();
            astaInversaRepository.verificaAstaInversaInPreferiti(indirizzoEmail, idAsta, new Asta_inversaRepository.OnVerificaAstaInversaInPreferitiListener() {
                @Override
                public void OnVerificaAstaInversaInPreferiti(Integer numeroRecuperato) {
                    Log.d("asta ricercata", "qui");
                    if (numeroRecuperato != null && numeroRecuperato != 0) {
                        Log.d("asta ricercata", "è nei preferiti");
                        setIsAstaInPreferiti(true);
                    } else {
                        Log.d("asta ricercata", "non è nei preferiti");
                        setErroreRecuperoAsta("errore nella verifica asta in preferiti");
                        setIsAstaInPreferiti(false);
                    }
                }
            });
        }
    }
    public void inserimentoAstaInPreferiti(){
        String indirizzoEmail = repository.getVenditoreModel().getIndirizzo_email();
        Long idAsta = repository.getAsta_inversaSelezionata().getId();
        astaInversaRepository.inserimentoAstaInPreferiti(idAsta, indirizzoEmail, new Asta_inversaRepository.OnInserimentoAstaInversaInPreferitiListener() {
            @Override
            public void OnInserimentoAstaInversaInPreferiti(Integer numeroRecuperato) {
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
        String indirizzoEmail = repository.getVenditoreModel().getIndirizzo_email();
        Long idAsta = repository.getAsta_inversaSelezionata().getId();
        astaInversaRepository.eliminazioneAstaInPreferiti(idAsta, indirizzoEmail, new Asta_inversaRepository.OnEliminazioneAstaInversaInPreferitiListener() {
            @Override
            public void OnEliminazioneAstaInversaInPreferiti(Integer numeroRecuperato) {
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
    public void setVaiInAcquirente(Boolean b){
        this.vaiInAcquirente.setValue(b);
    }
    public Boolean getVaiInvAcquirente(){
        return vaiInAcquirente.getValue();
    }
    public void vaiInAcquirente(String emailAcquirente){
        repository.setAcquirenteEmailDaAsta(emailAcquirente);
        repository.setLeMieAsteUtenteAttuale(false);
        setVaiInAcquirente(true);
    }
    public Asta_inversaModel getAstadaMostrare() {
        return astadaMostrare.getValue();
    }

    public void setAstadaMostrare(Asta_inversaModel astadaMostrare) {
        this.astadaMostrare.setValue(astadaMostrare);
    }
    public Boolean isAstaDaMostrare(){
        return (this.astadaMostrare.getValue()!=null);
    }
    public void recuperaAstaDaMostrare(){
        if(astaRecuperata.getValue()!=null) {
            Log.d("recuperaAstaDaMostrare", "valori: " + astaRecuperata.getValue().getNome());
        }
        setAstadaMostrare(repository.getAsta_inversaSelezionata());
    }
}
