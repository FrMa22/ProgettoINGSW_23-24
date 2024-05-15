    package com.example.progettoingsw.viewmodel;
    
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    
    import androidx.lifecycle.MutableLiveData;
    import androidx.lifecycle.ViewModel;

    import com.example.progettoingsw.R;
    import com.example.progettoingsw.model.Asta_allingleseModel;
    import com.example.progettoingsw.repository.Asta_allingleseRepository;
    import com.example.progettoingsw.repository.Repository;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    
    public class SchermataAstaIngleseViewModel extends ViewModel {
        private Repository repository;
        public MutableLiveData<Asta_allingleseModel> astaRecuperata = new MutableLiveData<>(null);
        public MutableLiveData<String> erroreRecuperoAsta = new MutableLiveData<>("");
        public MutableLiveData<Boolean> tipoUtenteChecked = new MutableLiveData<>(false);
        public MutableLiveData<Boolean> isPartecipazioneAvvenuta = new MutableLiveData<>(false);
        public MutableLiveData<Boolean> astaInPreferiti = new MutableLiveData<>(false);
        public MutableLiveData<Boolean> isAstaInPreferiti = new MutableLiveData<>(false);
        public MutableLiveData<Bitmap> immagineAstaConvertita = new MutableLiveData<>(null);
        public MutableLiveData<Boolean> isAstaChiusa = new MutableLiveData<>(false);
        public MutableLiveData<Boolean> vaiInVenditore = new MutableLiveData<>(false);
        public MutableLiveData<Boolean> isUltimaOffertaTua = new MutableLiveData<>(false);
        public MutableLiveData<String> intervalloOfferteConvertito = new MutableLiveData("");
        public MutableLiveData<Asta_allingleseModel> astadaMostrare = new MutableLiveData<>(null);
        public MutableLiveData<AlertDialog> popUpInformazioni = new MutableLiveData<>(null);
        private String messaggioPartecipazioneAstaInglese;
        private String tipoUtente;
        private Asta_allingleseRepository astaAllingleseRepository;
    
        public SchermataAstaIngleseViewModel(){
            repository = Repository.getInstance();
            astaAllingleseRepository = new Asta_allingleseRepository();
        }
    
        public void getAstaData(){
            Long idAsta = repository.getAsta_allingleseSelezionata().getId();
            astaAllingleseRepository.trovaAsta_allinglese(idAsta, new Asta_allingleseRepository.OnTrovaAstaIngleseListener() {
                @Override
                public void OnTrovaAstaInglese(Asta_allingleseModel astaRecuperata) {
                    if(astaRecuperata!=null){
                        repository.setAsta_allingleseSelezionata(astaRecuperata);
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
            Long id_asta = repository.getAsta_allingleseSelezionata().getId();
            String indirizzo_email = repository.getAcquirenteModel().getIndirizzo_email();
            astaAllingleseRepository.getEmailVincente(indirizzo_email, id_asta, new Asta_allingleseRepository.OnGetEmailVincenteListener() {
                @Override
                public void OnGetEmailVincente(Boolean numeroRecuperato) {
                    if(numeroRecuperato){
                        setIsUltimaOffertaTua(true);
                    }else{
                        setIsUltimaOffertaTua(false);
                    }
                }
            });
        }
        public void setAstaRecuperata(Asta_allingleseModel asta){
            astaRecuperata.setValue(asta);
        }
        public Asta_allingleseModel getAstaRecuperata(){
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
        public void setIntervalloOfferteConvertito(String scadenza){
            intervalloOfferteConvertito.setValue(scadenza);
        }
    
        public void convertiIntervalloOfferte(){
            // Ottieni la data e l'ora attuali con il giorno incluso
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
            String intervallo = repository.getAsta_allingleseSelezionata().getIntervalloTempoOfferte();
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
    
            setIntervalloOfferteConvertito(scadenzaFormattata);
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
            setIsAstaChiusa(!repository.getAsta_allingleseSelezionata().getCondizione().equals("aperta"));
        }
        public void setAstaInPreferiti(Boolean b){
            astaInPreferiti.setValue(b);
        }
        public Boolean getAstaInPreferiti(){
            return astaInPreferiti.getValue();
        }
        public Boolean isAstaInPreferiti(){
            return astaInPreferiti.getValue();
        }
        public void setIsAstaInPreferiti(Boolean b){
            isAstaInPreferiti.setValue(b);
        }
        public Boolean getIsAstaInPreferiti(){
            return isAstaInPreferiti.getValue();
        }
        public void verificaAstaInPreferiti(){
            String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
            Long idAsta = repository.getAsta_allingleseSelezionata().getId();
            astaAllingleseRepository.verificaAstaIngleseInPreferiti(indirizzoEmail,idAsta, new Asta_allingleseRepository.OnVerificaAstaIngleseInPreferitiListener() {
                @Override
                public void OnVerificaAstaIngleseInPreferiti(Integer numeroRecuperato) {
                    if(numeroRecuperato!=null && numeroRecuperato!=0){
                        setIsAstaInPreferiti(true);
                    }else{
                        setIsAstaInPreferiti(false);
                    }
                }
            });
        }
        public void inserimentoAstaInPreferiti(){
            String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
            Long idAsta = repository.getAsta_allingleseSelezionata().getId();
            astaAllingleseRepository.inserimentoAstaInPreferiti(idAsta, indirizzoEmail, new Asta_allingleseRepository.OnInserimentoAstaIngleseInPreferitiListener() {
                @Override
                public void OnInserimentoAstaIngleseInPreferiti(Integer numeroRecuperato) {
                    if(numeroRecuperato!=null && numeroRecuperato==1){
                        setIsAstaInPreferiti(true);
                    }else{
                        setErroreRecuperoAsta("Errore nell'inserimento asta in preferiti");
                    }
                }
            });
        }
        public void eliminazioneAstaInPreferiti(){
            String indirizzoEmail = repository.getAcquirenteModel().getIndirizzo_email();
            Long idAsta = repository.getAsta_allingleseSelezionata().getId();
            astaAllingleseRepository.eliminazioneAstaInPreferiti(idAsta, indirizzoEmail, new Asta_allingleseRepository.OnEliminazioneAstaIngleseInPreferitiListener() {
                @Override
                public void OnEliminazioneAstaIngleseInPreferiti(Integer numeroRecuperato) {
                    if(numeroRecuperato!=null && numeroRecuperato==1){
                        setIsAstaInPreferiti(false);
                    }else{
                        setErroreRecuperoAsta("Errore nella rimozione asta dai preferiti");
                    }
                }
            });
        }
        public void setVaiInVenditore(Boolean b){
            this.vaiInVenditore.setValue(b);
        }
        public Boolean getVaiInvVenditore(){
            return vaiInVenditore.getValue();
        }
        public void vaiInVenditore(String emailVenditore){
            repository.setVenditoreEmailDaAsta(emailVenditore);
            repository.setLeMieAsteUtenteAttuale(false);
            setVaiInVenditore(true);
        }
        public Asta_allingleseModel getAstadaMostrare() {
            return astadaMostrare.getValue();
        }
    
        public void setAstadaMostrare(Asta_allingleseModel astadaMostrare) {
            this.astadaMostrare.setValue(astadaMostrare);
        }
        public Boolean isAstaDaMostrare(){
            return (this.astadaMostrare.getValue()!=null);
        }
        public void recuperaAstaDaMostrare(){
            setAstadaMostrare(astaRecuperata.getValue());
        }
        public void setPopUpInformazioni(AlertDialog popUpInformazioni) {
            this.popUpInformazioni.setValue(popUpInformazioni);
        }
        public Boolean isPopUpInformazioni(){
            return (popUpInformazioni.getValue()!=null);
        }
        public void creaPopUpInformazioni(Activity activity){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialogThemeInglese);
            builder.setTitle("Cos'è un asta all'inglese?"); // Puoi impostare un titolo per il popup
            builder.setMessage("Un’asta all’inglese è caratterizzata da una base d’asta iniziale, da un intervallo di tempo fisso per presentare nuove offerte " +
                    ", e da una soglia di rialzo .\nGli acquirenti possono presentare un’offerta per " +
                    "il prezzo corrente.\nQuando viene presentata un’offerta, il timer per la presentazione di nuove " +
                    "offerte viene resettato.\nQuando il timer raggiunge lo zero senza che siano presentate nuove " +
                    "offerte, l’ultima offerta si aggiudica il bene/servizio in vendita.\nNon lasciartelo scappare! "); // Il testo da mostrare nel popup


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Azione da eseguire quando si preme il pulsante OK
                    dialog.dismiss(); // Chiudi il popup
                }
            });

            AlertDialog dialog = builder.create();
            setPopUpInformazioni(dialog);
            setPopUpInformazioni(dialog);
        }

    }
