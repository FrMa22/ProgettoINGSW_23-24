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
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreaAstaIngleseViewModel extends ViewModel {
    private Repository repository;
    private Asta_allingleseRepository astaAllingleseRepository;
    private Uri uriImmagine;
    private ArrayList<String> categorieScelte = new ArrayList<>();
    private ArrayList<String> categorieScelteProvvisorie = new ArrayList<>();
    public MutableLiveData<Boolean> apriPopUpCategoria = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreNome = new MutableLiveData<>("");
    public MutableLiveData<String> erroreDescrizione = new MutableLiveData<>("");
    public MutableLiveData<String> erroreBaseAsta = new MutableLiveData<>("");
    public MutableLiveData<String> erroreSogliaRialzoMinimo = new MutableLiveData<>("");
    public MutableLiveData<String> erroreGenerico = new MutableLiveData<>("");
    public MutableLiveData<String> erroreIntervallo = new MutableLiveData<>("");
    public MutableLiveData<Intent> apriGalleria = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> astaCreata = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<String>> categorieInserite = new MutableLiveData<>(null);
    public MutableLiveData<AlertDialog> popUpInformazioni = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> apriPopUpInformazioni = new MutableLiveData<>(false);
    public MutableLiveData<Bitmap> immagineConvertita = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);


    public CreaAstaIngleseViewModel(){
        repository = Repository.getInstance();
        astaAllingleseRepository = new Asta_allingleseRepository();
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
        if(categorieInserite!=null && !categorieInserite.isEmpty()) {
            this.categorieInserite.setValue(new ArrayList<>(categorieInserite));
        }else{
            this.categorieInserite.setValue(new ArrayList<>());
        }
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
    public void setErroreIntervallo(String errore){
        erroreIntervallo.setValue(errore);
    }
    public String getErroreIntervallo(){
        return erroreIntervallo.getValue();
    }
    public Boolean isErroreIntervallo(){
        if(erroreIntervallo.getValue()!=null){
            return !erroreIntervallo.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreSogliaRialzoMinimo(String errore){
        erroreSogliaRialzoMinimo.setValue(errore);
    }
    public String getErroreSogliaRialzoMinimo(){
        return erroreSogliaRialzoMinimo.getValue();
    }
    public Boolean isErroreSogliaRialzoMinimo(){
        if(erroreSogliaRialzoMinimo.getValue()!=null){
            return !erroreSogliaRialzoMinimo.getValue().equals("");
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

    private Boolean isValidIntervallo(String intervallo){
        if (intervallo == null || intervallo.isEmpty()) {
            setErroreIntervallo("Si prega di inserire un intervallo per le offerte.");
            return false;
        } else if (!intervallo.matches("^\\d{1,5}$")) {
            setErroreIntervallo("L'intervallo deve contenere solo numeri e non può superare i 5 caratteri.");
            return false;
        }else if(Float.parseFloat(intervallo)<=0){
            setErroreIntervallo("L'intervallo deve essere di almeno 1 minuto.");
            return false;
        }else{
            return true;
        }
    }
    private Boolean isValidSogliaRialzoMinimo(String rialzo){
        if(rialzo == null || rialzo.isEmpty()) {
            setErroreSogliaRialzoMinimo("Si prega di inserire un valore minimo di rialzo.");
            return false;
        } else if (!rialzo.matches("^\\d*\\.?\\d+$")) {
            setErroreSogliaRialzoMinimo("Si prega di inserire solo numeri per il valore minimo di rialzo.");
            return false;
        }else if(Float.parseFloat(rialzo)<=0){
            setErroreSogliaRialzoMinimo("Inserire un prezzo maggiore di 0");
            return false;
        }else{
            return true;
        }
    }

    public void creaAsta(String nome, String descrizione, String baseAsta, String intervallo, String rialzo, Bitmap immagine) {
        if (isValidName(nome) && isValidDescription(descrizione) && isValidBaseAsta(baseAsta) && isValidIntervallo(intervallo) && isValidSogliaRialzoMinimo(rialzo)) {
            byte[] image_byte;
            if(immagine!=null) {
                image_byte = compressAndConvertToByteArray(immagine);
            }else{
                image_byte=null;
            }
            float baseAsta_float = Float.parseFloat(baseAsta);
            float rialzo_float = Float.parseFloat(rialzo);
            String id_venditore = repository.getVenditoreModel().getIndirizzo_email();

            Asta_allingleseModel asta = new Asta_allingleseModel(nome,descrizione,image_byte, baseAsta_float,intervallo,intervallo, rialzo_float,baseAsta_float,"aperta",id_venditore);
            if(categorieScelte!=null && !categorieScelte.isEmpty()) {
                Log.d("creo asta", "nome: " + nome + "categorie: " + categorieScelte.get(0));
            }

            creaAstaBackend(asta);

        }
    }
    public void creaAstaBackend(Asta_allingleseModel asta){
        astaAllingleseRepository.saveAsta_inglese(asta,categorieScelte , new Asta_allingleseRepository.OnInserimentoAstaIngleseListener() {
            @Override
            public void OnInserimentoAstaInglese(Long numeroRecuperato) {
                if(numeroRecuperato==0){
                    setAstaCreata(true);
                }else{
                    setErroreGenerico("Errore nella creazione dell'asta, riprovare più tardi." + numeroRecuperato);
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
        builder.setTitle("Cos'è un asta all'inglese?"); // Puoi impostare un titolo per il popup
        builder.setMessage(" Un’asta all’inglese è caratterizzata da una base d’asta iniziale pubblica, specificata dal venditore al momento della " +
                "creazione dell’asta, da un intervallo di tempo fisso per presentare nuove offerte (di default 1 " +
                "ora), e da una soglia di rialzo (di default 10€).\n Gli acquirenti possono presentare un’offerta per " +
                "il prezzo corrente.\n Quando viene presentata un’offerta, il timer per la presentazione di nuove " +
                "offerte viene resettato. Quando il timer raggiunge lo zero senza che siano presentate nuove " +
                "offerte, l’ultima offerta si aggiudica il bene/servizio in vendita, e il venditore e gli acquirenti che " +
                "hanno partecipato all’asta visualizzano una notifica. "); // Il testo da mostrare nel popup


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
        Log.d("addcategoria", "entrato" + nomeCategoria);
        Log.d("addcategoria", "entrato e scelte: " + categorieScelte);
        Log.d("removecategoria", "prima di aver aggiunto " + nomeCategoria + " si ha " + categorieScelteProvvisorie );
        categorieScelteProvvisorie.add(nomeCategoria);
        Log.d("removecategoria", "dopo aver aggiunto " + nomeCategoria + " si ha " + categorieScelteProvvisorie );
    }
    public void removeCategoriaProvvisoria(String nomeCategoria){
        Log.d("removecategoria", "entrato" + nomeCategoria);
        Log.d("removecategoria", "entrato e scelte: " + categorieScelte);
        Log.d("removecategoria", "prima di aver rimosso " + nomeCategoria + " si ha " + categorieScelteProvvisorie );
        categorieScelteProvvisorie.remove(nomeCategoria);
        Log.d("removecategoria", "dopo aver rimosso " + nomeCategoria + " si ha " + categorieScelteProvvisorie );
    }
    public void saveCategorieScelte(){
        Log.d("save","in categorie scelte metto categorie provvisorie" + categorieScelteProvvisorie);
        this.categorieScelte = new ArrayList<>(categorieScelteProvvisorie);
        setCategorieInserite(categorieScelte);
        this.categorieScelteProvvisorie.clear();
    }
    public void setCategoriaProvvisoria(ArrayList<String> lista){
        this.categorieScelteProvvisorie = new ArrayList<>(lista);
    }
    public void checkCategorieInserite(){
        if(categorieScelte != null && !categorieScelte.isEmpty()){
            Log.d("checkCategorieInserite","categorie scelte non null e non vuoto");
            setCategoriaProvvisoria(categorieScelte);
            setCategorieInserite(categorieScelte);
        }else{
            Log.d("checkCategorieInserite","categorie scelte null o vuoto");
            categorieScelteProvvisorie = new ArrayList<>();
        }
    }
    public void setTornaIndietro(Boolean b){
        this.tornaIndietro.setValue(b);
    }
    public void premutoBack(){
        setTornaIndietro(true);
    }
}
