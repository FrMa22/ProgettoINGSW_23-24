package com.example.progettoingsw.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.R;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.acquirente.AcquirenteMainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreaAstaInversaViewModel extends ViewModel {
    private Repository repository;
    private Asta_inversaRepository astaInversaRepository;
    private Uri uriImmagine;
    private ArrayList<String> categorieScelte = new ArrayList<>();
    public MutableLiveData<Boolean> apriPopUpCategoria = new MutableLiveData<>(false);
    public MutableLiveData<String> erroreNome = new MutableLiveData<>("");
    public MutableLiveData<String> erroreDescrizione = new MutableLiveData<>("");
    public MutableLiveData<String> errorePrezzo = new MutableLiveData<>("");
    public MutableLiveData<String> erroreDataOra = new MutableLiveData<>("");
    public MutableLiveData<Intent> apriGalleria = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> openCalendario = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> openOrario = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> astaCreata = new MutableLiveData<>(false);
    public MutableLiveData<ArrayList<String>> categorieInserite = new MutableLiveData<>(null);
    public MutableLiveData<AlertDialog> popUpInformazioni = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> apriPopUpInformazioni = new MutableLiveData<>(false);
    public MutableLiveData<Bitmap> immagineConvertita = new MutableLiveData<>(null);


    public CreaAstaInversaViewModel() {
        repository = Repository.getInstance();
        astaInversaRepository = new Asta_inversaRepository();
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
    public Boolean getApriPopUpInformazioni() {
        return apriPopUpInformazioni.getValue();
    }
    public void setApriPopUpInformazioni(Boolean b) {
        this.apriPopUpInformazioni.setValue(b);
    }
    public Boolean getOpenCalendario() {
        return openCalendario.getValue();
    }
    public void setOpenCalendario(Boolean b) {
        this.openCalendario.setValue(b);
    }
    public Boolean getOpenOrario() {
        return openOrario.getValue();
    }
    public void setOpenOrario(Boolean b) {
        this.openOrario.setValue(b);
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
    public void setErrorePrezzo(String errore){
        errorePrezzo.setValue(errore);
    }
    public String getErrorePrezzo(){
        return errorePrezzo.getValue();
    }
    public Boolean isErrorePrezzo(){
        if(errorePrezzo.getValue()!=null){
            return !errorePrezzo.getValue().equals("");
        }else{
            return false;
        }
    }
    public void setErroreDataOra(String errore){
        erroreDataOra.setValue(errore);
    }
    public String getErroreDataOra(){
        return erroreDataOra.getValue();
    }
    public Boolean isErroreDataOra(){
        if(erroreDataOra.getValue()!=null){
            return !erroreDataOra.getValue().equals("");
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
        if(descrizione.length() > 250) {
            setErroreDescrizione("La descrizione non può essere più lunga di 250 caratteri.");
            return false;
        }
        return true;
    }

    private boolean isValidPrice(String prezzo) {
        if (prezzo == null || prezzo.isEmpty()) {
            setErrorePrezzo("Si prega di inserire un prezzo.");
            return false;
        } else if (!prezzo.matches("^\\d+(\\.\\d+)?$")) {
            setErrorePrezzo("Il prezzo deve essere un numero valido.");
            return false;
        } else if (Float.parseFloat(prezzo) <= 0) {
            setErrorePrezzo("Il prezzo deve essere maggiore di 0.");
            return false;
        }
        return true;
    }

    private boolean isValidDateTime(String data, String ora) {
        if (data==null || data.isEmpty() || ora==null || ora.isEmpty()) {
            setErroreDataOra("Si prega di selezionare una data e un'ora valida.");
            //Toast.makeText(getContext(), "Si prega di selezionare una data e un'ora valida.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Converte la data e l'ora in oggetti Calendar per la comparazione
        Calendar selectedDateTime = Calendar.getInstance();
        selectedDateTime.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
        selectedDateTime.set(Calendar.MONTH, Integer.parseInt(data.substring(5, 7)) - 1);
        selectedDateTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.substring(8, 10)));
        selectedDateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ora.substring(0, 2)));
        selectedDateTime.set(Calendar.MINUTE, Integer.parseInt(ora.substring(3)));

        Calendar currentDateTime = Calendar.getInstance();

        // Controlla se la data è precedente a oggi o se la data e l'ora sono precedenti all'istante attuale
        if (selectedDateTime.before(currentDateTime)) {
            setErroreDataOra("La data e l'ora devono essere in futuro.");
            //Toast.makeText(getContext(), "La data e l'ora devono essere in futuro.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    // Metodo per comprimere un'immagine Bitmap e convertirla in un array di byte
    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
        return outputStream.toByteArray();
    }
    private String combineDateTime(String date, String time) {
        // Formato della data
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // Formato dell'ora
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            // Parsa la stringa della data
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));

            // Parsa la stringa dell'ora
            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(timeFormat.parse(time));

            // Imposta l'ora della data
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));

            // Formatta il risultato nel formato desiderato
            SimpleDateFormat combinedFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            return combinedFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Gestione dell'errore: restituisce null se c'è un errore nella conversione
        }
    }
    public void creaAsta(String nome, String descrizione, String prezzo, String data, String ora, Bitmap immagine) {

        if (isValidName(nome) && isValidDescription(descrizione) && isValidPrice(prezzo) && isValidDateTime(data, ora)) {
            String data_e_ora = combineDateTime(data,ora);
            if(data_e_ora!=null){
                byte[] image_byte;
                if(immagine!=null) {
                    image_byte = compressAndConvertToByteArray(immagine);
                }else{
                    image_byte=null;
                }
                float prezzo_float = Float.parseFloat(prezzo);
                String id_acquirente = repository.getAcquirenteModel().getIndirizzoEmail();

                Asta_inversaModel asta = new Asta_inversaModel(nome,descrizione,image_byte, prezzo_float, prezzo_float,data_e_ora,"aperta",id_acquirente);
                if(categorieScelte!=null && !categorieScelte.isEmpty()) {
                    Log.d("creo asta", "nome: " + nome + "categorie: " + categorieScelte.get(0));
                }
                creaAstaBackend(asta);
            }

        }
    }
    public void creaAstaBackend(Asta_inversaModel asta){
        astaInversaRepository.saveAsta_inversa(asta,categorieScelte , new Asta_inversaRepository.OnInserimentoAstaInversaListener() {
            @Override
            public void OnInserimentoAstaInversa(Long numeroRecuperato) {
                if(numeroRecuperato==1){
                    setAstaCreata(true);
                }else{
                    setErroreDataOra("Errore nella creazione dell'asta, riprovare più tardi.");
                }
            }
        });
    }
    public void onActivityResult(Uri uriImmagine) {
        try {
            Log.d("ok","ok");
            //displayImage(uriImmagine);
        } catch (Exception e) {
            setErroreDataOra("Nessuna Immagine selezionata");
        }
    }
    public void prelevaImmagine(Activity activity){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            setApriGalleria(intent);
            //resultLauncher.launch(intent);
        } else {
            setErroreDataOra("Nessuna app disponibile per gestire la selezione delle immagini");
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

    public void openCalendario(){
        setOpenCalendario(true);
    }
    public void openOrario(){
        setOpenOrario(true);
    }
    public void apriPopUpInformazioni(){
        setApriPopUpInformazioni(true);
    }

    public void creaPopUpInformazioni(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Cos'è un asta inversa?"); // Puoi impostare un titolo per il popup
        builder.setMessage("Nell'asta inversa, il compratore specifica il prodotto/servizio richiesto, eventualmente inserendo un’immagine dello stesso," +
                " un prezzo iniziale che è disposto a pagare, e una data di scadenza. I venditori in grado di fornire quel particolare prodotto/servizio " +
                "possono quindi partecipare all’asta competendo abbassando il prezzo. In particolare, fino al momento della scadenza dell’asta, i venditori " +
                "possono presentare offerte al ribasso. Al momento della scadenza dell’asta, il venditore con l’offerta più bassa si aggiudica la fornitura del prodotto/servizio.");


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

    public void addCategoria(String nomeCategoria){
        Log.d("addcategoria", "entrato");
        categorieScelte.add(nomeCategoria);
    }
    public void removeCategoria(String nomeCategoria){
        Log.d("removecategoria", "entrato");
        categorieScelte.remove(nomeCategoria);
    }
    public void checkCategorieInserite(){
        if(categorieScelte != null && !categorieScelte.isEmpty()){
            setCategorieInserite(categorieScelte);
        }
    }

}
