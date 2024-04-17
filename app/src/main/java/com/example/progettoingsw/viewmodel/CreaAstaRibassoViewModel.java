package com.example.progettoingsw.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreaAstaRibassoViewModel extends ViewModel{
    private Repository repository;
    private Asta_alribassoRepository astaAlribassoRepository;
    private Uri uriImmagine;
    private ArrayList<String> categorieScelte = new ArrayList<>();
    private ArrayList<String> categorieScelteProvvisorie = new ArrayList<>();
    public MutableLiveData<Boolean> apriPopUpCategoria = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreNome = new MutableLiveData<>("");
    public MutableLiveData<String> erroreDescrizione = new MutableLiveData<>("");
    public MutableLiveData<String> erroreBaseAsta = new MutableLiveData<>("");
    public MutableLiveData<String> erroreIntervalloDecrementale = new MutableLiveData<>("");
    public MutableLiveData<String> erroreGenerico = new MutableLiveData<>("");
    public MutableLiveData<String> erroreSogliaDiDecremento = new MutableLiveData<>("");
    public MutableLiveData<String> errorePrezzoSegreto = new MutableLiveData<>("");
    public MutableLiveData<Intent> apriGalleria = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> astaCreata = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<String>> categorieInserite = new MutableLiveData<>(null);
    public MutableLiveData<AlertDialog> popUpInformazioni = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> apriPopUpInformazioni = new MutableLiveData<>(false);
    public MutableLiveData<Bitmap> immagineConvertita = new MutableLiveData<>(null);

    public CreaAstaRibassoViewModel(){
        repository=Repository.getInstance();
        astaAlribassoRepository = new Asta_alribassoRepository();
    }

    public Boolean getAstaCreata() {
        return astaCreata.getValue();
    }
    public void setAstaCreata(Boolean astaCreata) {
        this.astaCreata.setValue(astaCreata);
    }
    public AlertDialog getPopUpInformazioni() {
        return popUpInformazioni.getValue();
    }

    public void setPopUpInformazioni(AlertDialog popUpInformazioni) {
        this.popUpInformazioni.setValue(popUpInformazioni);
    }
    public Boolean isPopUpInformazioni(){
        return (popUpInformazioni.getValue()!=null);
    }
    public ArrayList<String> getCategorieInserite() {
        return categorieInserite.getValue();
    }
    public void setCategorieInserite(ArrayList<String> categorieInserite) {
        this.categorieInserite.setValue(categorieInserite);
    }
    public Boolean listaCategorieNotEmpty(){
        if(categorieInserite.getValue()!=null) {
            return !(categorieInserite.getValue().isEmpty());
        }else{
            return false;
        }
    }
    public void setErroreGenerico(String errore){
        erroreGenerico.setValue(errore);
    }
    public String getErroreGenerico(){
        return erroreGenerico.getValue();
    }
    public Boolean isErroreGenerico(){
        if(erroreGenerico.getValue()!=null){
            return !(erroreGenerico.getValue().equals(""));
        }else{
            return false;
        }
    }
    public Boolean getApriPopUpInformazioni() {
        return apriPopUpInformazioni.getValue();
    }
    public void setApriPopUpInformazioni(Boolean b) {
        this.apriPopUpInformazioni.setValue(b);
    }
    public Intent getApriGalleria() {
        return apriGalleria.getValue();
    }
    public void setApriGalleria(Intent intent) {
        this.apriGalleria.setValue(intent);
    }
    public Boolean isApriGalleria(){
        return (apriGalleria.getValue() != null);
    }
    public void setErroreNome(String errore){
        erroreNome.setValue(errore);
    }
    public String getErroreNome(){
        return erroreNome.getValue();
    }
    public Boolean isErroreNome(){
        if(erroreNome.getValue()!=null){
            return !erroreNome.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreDescrizione(String errore){
        erroreDescrizione.setValue(errore);
    }
    public String getErroreDescrizione(){
        return erroreDescrizione.getValue();
    }
    public Boolean isErroreDescrizione(){
        if(erroreDescrizione.getValue()!=null){
            return !erroreDescrizione.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreBaseAsta(String errore){
        erroreBaseAsta.setValue(errore);
    }
    public String getErroreBaseAsta(){
        return erroreBaseAsta.getValue();
    }
    public Boolean isErroreBaseAsta(){
        if(erroreBaseAsta.getValue()!=null){
            return !erroreBaseAsta.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreIntervalloDecrementale(String errore){
        erroreIntervalloDecrementale.setValue(errore);
    }
    public String getErroreIntervalloDecrementale(){
        return erroreIntervalloDecrementale.getValue();
    }
    public Boolean isErroreIntervalloDecrementale(){
        if(erroreIntervalloDecrementale.getValue()!=null){
            return !erroreIntervalloDecrementale.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreSogliaDiDecremento(String errore){
        erroreSogliaDiDecremento.setValue(errore);
    }
    public String getErroreSogliaDiDecremento(){
        return erroreSogliaDiDecremento.getValue();
    }
    public Boolean isErroreSogliaDiDecremento(){
        if(erroreSogliaDiDecremento.getValue()!=null){
            return !erroreSogliaDiDecremento.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErrorePrezzoSegreto(String errore){
        errorePrezzoSegreto.setValue(errore);
    }
    public String getErrorePrezzoSegreto(){
        return errorePrezzoSegreto.getValue();
    }
    public Boolean isErrorePrezzoSegreto(){
        if(errorePrezzoSegreto.getValue()!=null){
            return !errorePrezzoSegreto.getValue().equals("");
        }else{
            return false;
        }
    }
    public Boolean getApriPopUpCategoria() {
        return apriPopUpCategoria.getValue();
    }

    public void setApriPopUpCategoria(Boolean b) {
        this.apriPopUpCategoria.setValue(b);
    }
    public Bitmap getImmagineConvertita() {
        return immagineConvertita.getValue();
    }
    public void setImmagineConvertita(Bitmap immagineConvertita) {
        this.immagineConvertita.setValue(immagineConvertita);
    }
    public Boolean isImmagineConverita(){
        return immagineConvertita.getValue()!=null;
    }

    public void apriPopUp() {
        setApriPopUpCategoria(true);
    }

    private boolean isValidName(String nome) {
        if (nome==null || nome.isEmpty()) {
            setErroreNome("Si prega di inserire un nome.");
            return false;
        } else if (nome.length() > 100) {
            setErroreNome("Il nome non può essere più lungo di 100 caratteri.");
            return false;
        }
        return true;
    }

    private boolean isValidDescription(String descrizione) {
        if (descrizione.length() > 250) {
            setErroreDescrizione("La descrizione non può essere più lunga di 250 caratteri.");
            return false;
        }
        return true;
    }
    private boolean isValidBaseAsta(String prezzo) {
        if (prezzo == null || prezzo.isEmpty()) {
            setErroreBaseAsta("Si prega di inserire un prezzo.");
            return false;
        } else if (!prezzo.matches("^\\d+(\\.\\d+)?$")) {
            setErroreBaseAsta("Il prezzo deve essere un numero valido.");
            return false;
        } else if (Float.parseFloat(prezzo) <= 0) {
            setErroreBaseAsta("Il prezzo deve essere maggiore di 0.");
            return false;
        }
        return true;
    }
    private Boolean isValidIntervalloDecrementale(String intervallo){
        if (intervallo == null || intervallo.isEmpty()) {
            setErroreIntervalloDecrementale("Si prega di inserire un intervallo per le offerte.");
            return false;
        } else if (!intervallo.matches("^\\d{1,5}$")) {
            setErroreIntervalloDecrementale("L'intervallo deve contenere solo numeri e non può superare i 5 caratteri.");
            return false;
        }else if(Float.parseFloat(intervallo)<=0){
            setErroreIntervalloDecrementale("L'intervallo deve essere di almeno 1 minuto.");
            return false;
        }else{
            return true;
        }
    }
    private Boolean isValidSogliaDiDecremento(String rialzo, String prezzoBase){
        if(rialzo == null || rialzo.isEmpty()) {
            setErroreSogliaDiDecremento("Si prega di inserire un valore minimo di rialzo.");
            return false;
        } else if (!rialzo.matches("^\\d*\\.?\\d+$")) {
            setErroreSogliaDiDecremento("Si prega di inserire solo numeri per il valore minimo di rialzo.");
            return false;
        }else if(Float.parseFloat(rialzo)<=0){
            setErroreSogliaDiDecremento("Inserire un prezzo maggiore di 0");
            return false;
        }else if(Float.parseFloat(rialzo)>=Float.parseFloat(prezzoBase)){
            setErroreSogliaDiDecremento("La soglia deve essere inferiore al prezzo base.");
            return false;
        }else{
            return true;
        }
    }
    private Boolean isValidPrezzoSegreto(String prezzoSegreto, String prezzoBase){
        if(prezzoSegreto == null || prezzoSegreto.isEmpty()) {
            setErrorePrezzoSegreto("Si prega di inserire un valore per prezzo segreto minimo.");
            return false;
        } else if (!prezzoSegreto.matches("^\\d*\\.?\\d+$")) {
            setErrorePrezzoSegreto("Si prega di inserire solo numeri per il valore prezzo segreto minimo.");
            return false;
        }else if(Float.parseFloat(prezzoSegreto)<=0){
            setErrorePrezzoSegreto("Inserire un prezzo maggiore di 0");
            return false;
        }else if(Float.parseFloat(prezzoSegreto)>=Float.parseFloat(prezzoBase)){
            setErrorePrezzoSegreto("Il prezzo minimo deve essere minore della base asta.");
            return false;
        }else{
            return true;
        }
    }

    public void creaAsta(String nome, String descrizione, String prezzoBase, String intervalloDecrementale,String decrementoAutomaticoCifra,String prezzoMin, Bitmap immagine) {
        if (isValidName(nome) && isValidDescription(descrizione) && isValidBaseAsta(prezzoBase) && isValidIntervalloDecrementale(intervalloDecrementale) && isValidSogliaDiDecremento(decrementoAutomaticoCifra,prezzoBase) &&
                isValidPrezzoSegreto(prezzoMin,prezzoBase)) {
            byte[] image_byte;
            if(immagine!=null) {
                image_byte = compressAndConvertToByteArray(immagine);
            }else{
                image_byte=null;
            }
            float prezzoBase_float = Float.parseFloat(prezzoBase);
            float decrementoAutomaticoCifra_float = Float.parseFloat(decrementoAutomaticoCifra);
            float prezzoMin_float = Float.parseFloat(prezzoMin);
            byte[] img_byte = null;
            if(immagine!=null) {
                img_byte = bitmapToByteArray(immagine);
            }
            String id_venditore = repository.getVenditoreModel().getIndirizzoEmail();

            Asta_alribassoModel asta = new Asta_alribassoModel(nome,  descrizione, img_byte, prezzoBase_float,  intervalloDecrementale,  intervalloDecrementale,
                    decrementoAutomaticoCifra_float,  prezzoMin_float, prezzoBase_float, "aperta", id_venditore);

            if(categorieScelte!=null && !categorieScelte.isEmpty()) {
                Log.d("creo asta", "nome: " + nome + "categorie: " + categorieScelte.get(0));
            }
            creaAstaBackend(asta);

        }
    }
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Puoi cambiare il formato e la qualità qui
        return stream.toByteArray();
    }
    public void creaAstaBackend(Asta_alribassoModel asta){
        astaAlribassoRepository.saveAsta_ribasso(asta,categorieScelte , new Asta_alribassoRepository.OnInserimentoAstaRibassoListener() {
            @Override
            public void OnInserimentoAstaRibasso(Long numeroRecuperato) {
                if(numeroRecuperato==0){
                    setAstaCreata(true);
                }else{
                    setErroreGenerico("Errore nella creazione dell'asta, riprovare più tardi."+ numeroRecuperato);
                }
            }
        });
    }

    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
        return outputStream.toByteArray();
    }
    public void onActivityResult(Uri uriImmagine) {
        try {
            Log.d("ok","ok");
            //displayImage(uriImmagine);
        } catch (Exception e) {
            setErroreGenerico("Nessuna Immagine selezionata");
        }
    }
    public void prelevaImmagine(Activity activity){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            setApriGalleria(intent);
            //resultLauncher.launch(intent);
        } else {
            setErroreGenerico("Nessuna app disponibile per gestire la selezione delle immagini");
        }
    }
    public void setImmagine(ActivityResult result, Activity activity){
        uriImmagine = result.getData().getData();
        displayImage(uriImmagine,activity);
    }
    private int getImageOrientation(Uri uri, Activity activity) {
        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(inputStream);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getImageRotation(orientation);
    }
    private int getImageRotation(int orientation) {
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }
    private void displayImage(Uri uri, Activity activity) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Controllo dell'orientamento dell'immagine e rotazione se necessario
            int orientation = getImageOrientation(uri,activity);
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            // Ridimensiona l'immagine per adattarla alla dimensione desiderata
            int targetWidth = 500; // Imposta la larghezza desiderata
            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth())); // Calcola l'altezza in base al rapporto
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);


            // Imposta l'immagine ridimensionata nella ImageView
            setImmagineConvertita(resizedBitmap);

            //immagineProdotto.setImageBitmap(resizedBitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void apriPopUpInformazioni(){
        setApriPopUpInformazioni(true);
    }

    public void creaPopUpInformazioni(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Cos'è un asta al ribasso?"); // Puoi impostare un titolo per il popup
        builder.setMessage(" Un’asta al ribasso è caratterizzata da un prezzo iniziale elevato specificato dal venditore, da un timer per il decremento del prezzo da un importo, in €," +
                " per ciascun decremento, e da un prezzo minimo (segreto) a cui vendere il prodotto/servizio. Il prodotto/servizio sarà in vendita al prezzo iniziale stabilito dal venditore." +
                " Al raggiungimento del timer, il prezzo verrà decrementato dell’importo previsto. Il primo compratore a presentare un’offerta si aggiudica il prodotto/servizio. Se il prezzo viene " +
                "decrementato fino a raggiungere il prezzo minimo segreto, l’asta viene considerata fallita e il venditore visualizza una notifica. "); // Il testo da mostrare nel popup


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Azione da eseguire quando si preme il pulsante OK
                dialog.dismiss(); // Chiudi il popup
            }
        });

        AlertDialog dialog = builder.create();
        //dialog.show();
        setPopUpInformazioni(dialog);

    }

    public void addCategoriaProvvisoria(String nomeCategoria){
        Log.d("addcategoria", "entrato");
        categorieScelteProvvisorie.add(nomeCategoria);
    }
    public void removeCategoriaProvvisoria(String nomeCategoria){
        Log.d("removecategoria", "entrato");
        categorieScelteProvvisorie.remove(nomeCategoria);
    }
    public void saveCategorieScelte(){
        this.categorieScelte = categorieScelteProvvisorie;
    }
    public void setCategoriaProvvisoria(ArrayList<String> lista){
        this.categorieScelteProvvisorie = lista;
    }
    public void checkCategorieInserite(){
        if(categorieScelte != null && !categorieScelte.isEmpty()){
            setCategoriaProvvisoria(categorieScelte);
            setCategorieInserite(categorieScelte);
        }
    }
}
