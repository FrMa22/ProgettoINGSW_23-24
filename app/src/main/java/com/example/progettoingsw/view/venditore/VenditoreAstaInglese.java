package com.example.progettoingsw.view.venditore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.view.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.view.acquirente.MainActivity;
import com.example.progettoingsw.viewmodel.CreaAstaIngleseViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class VenditoreAstaInglese extends GestoreComuniImplementazioni {
    private CreaAstaIngleseViewModel creaAstaIngleseViewModel;
    AppCompatButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton bottone_info;
    EditText nome;
    private int idAsta;
    private ImageButton imageButtonRimuoviImmagineCreaAstaInglese;
    EditText descrizione;
    EditText baseAsta;
    EditText intervalloAsta;
    EditText rialzoAsta;
    Uri uriImmagine;
    private Bitmap bitmap;
    Controller controller;
    private byte [] img;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    private ArrayList<String> listaCategorieScelte;
    ActivityResultLauncher<Intent> resultLauncher;
    private String selectedDateString;
    private String selectedHourString;
    private MaterialButton bottoneCategorieAstaInglese;
    private ProgressBar progressBarVenditoreAstaInglese;
    private RelativeLayout relativeLayoutAstaInglese;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_inglese);



         controller = new Controller();
        progressBarVenditoreAstaInglese = findViewById(R.id.progressBarVenditoreAstaInglese);
        relativeLayoutAstaInglese = findViewById(R.id.relativeLayoutAstaInglese);
        AstaIngleseDAO astaIngleseDao = new AstaIngleseDAO(VenditoreAstaInglese.this);
        listaCategorieScelte = new ArrayList<>();
        baseAsta=findViewById(R.id.editTextBaseAstaAstaInglese);
        intervalloAsta=findViewById(R.id.editTextTimerDecrementoAstaInglese);
        rialzoAsta=findViewById(R.id.editTextSogliaRialzoAstaInglese);

        nome = findViewById(R.id.editTextNomeBeneCreaAstaInglese);
        descrizione=findViewById(R.id.editTextDescrizioneCreaAstaInglese);

        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaInglese);
        bottoneBack =  findViewById(R.id.bottoneBackAstaInglese);
        bottone_info = findViewById(R.id.button_info_asta_inglese_venditore);
        imageButtonRimuoviImmagineCreaAstaInglese = findViewById(R.id.imageButtonRimuoviImmagineCreaAstaInglese);

        immagineProdotto= findViewById(R.id.imageViewCreaAstaInglese);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaInglese);
        bottoneInserisciImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(Intent.ACTION_PICK).setType("image/*"));
            }
        });
        registraRisultati();

        bottoneCategorieAstaInglese = findViewById(R.id.bottoneCategorieAstaInglese);
        bottoneCategorieAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaIngleseViewModel.apriPopUp();
            }
        });
        imageButtonRimuoviImmagineCreaAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                immagineProdotto.setImageResource(android.R.color.transparent); // Rimuove l'immagine
                bitmap = null;
//                imageBytes = null; // Reimposta il byte array a null
//                uriImmagine = null;
            }
        });

        bottone_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                creaAstaIngleseViewModel.apriPopUpInformazioni();
            }
        });


        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                creaAstaIngleseViewModel.premutoBack();

            }
        });

        bottoneConferma.setOnClickListener(v -> {
            String nomeProdotto = nome.getText().toString().trim();
            String descrizioneProdotto = descrizione.getText().toString().trim();
            String base = baseAsta.getText().toString().trim();
            String intervallo = intervalloAsta.getText().toString().trim();
            String rialzo = rialzoAsta.getText().toString().trim();
            creaAstaIngleseViewModel.creaAsta(nomeProdotto, descrizioneProdotto,base,intervallo,rialzo,bitmap);
        });
        creaAstaIngleseViewModel = new ViewModelProvider(this).get(CreaAstaIngleseViewModel.class);
        osservaErroreNome();
        osservaErroreDescrizione();
        osservaErroreBaseAsta();
        osservaErroreIntervallo();
        osservaErroreSogliaRialzoMinimo();
        osservaErroreGenerico();
        osservaApriPopUpCategorie();
        osservaApriGalleria();
        osservaAstaCreata();
        osservaApriPopUpInformazioni();
        osservaCreaPopUpInformazioni();
        osservaImmagineConvertita();
        osservaTornaIndietro();

    }

    public void osservaTornaIndietro(){
        creaAstaIngleseViewModel.tornaIndietro.observe(this , (valore)->{
            if(valore){
                Intent intent = new Intent(VenditoreAstaInglese.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInformazioni(){
        creaAstaIngleseViewModel.apriPopUpInformazioni.observe(this, (valore) ->{
            if(valore){
                creaAstaIngleseViewModel.creaPopUpInformazioni(this);
            }
        });
    }
    public void osservaCreaPopUpInformazioni(){
        creaAstaIngleseViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(creaAstaIngleseViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
    public void osservaApriPopUpCategorie(){
        creaAstaIngleseViewModel.apriPopUpCategoria.observe(this, (valore) ->{
            if(valore){
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(this, VenditoreAstaInglese.this,creaAstaIngleseViewModel);
                popUpAggiungiCategorieAsta.show();
            }
        });
    }
    public void osservaAstaCreata(){
        creaAstaIngleseViewModel.astaCreata.observe(this, (result) -> {
            if(result){
                bottoneBack.performClick();
            }
        });
    }
    public void osservaErroreNome(){
        creaAstaIngleseViewModel.erroreNome.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreNome()){
                nome.setError(errore);
            }
        });
    }
    public void osservaErroreDescrizione(){
        creaAstaIngleseViewModel.erroreDescrizione.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreDescrizione()){
                descrizione.setError(errore);
            }
        });
    }
    public void osservaErroreBaseAsta(){
        creaAstaIngleseViewModel.erroreBaseAsta.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreBaseAsta()){
                baseAsta.setError(errore);
            }
        });
    }
    public void osservaErroreIntervallo(){
        creaAstaIngleseViewModel.erroreIntervallo.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreIntervallo()){
                intervalloAsta.setError(errore);
            }
        });
    }
    public void osservaErroreSogliaRialzoMinimo(){
        creaAstaIngleseViewModel.erroreSogliaRialzoMinimo.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreSogliaRialzoMinimo()){
                rialzoAsta.setError(errore);
            }
        });
    }
    public void osservaErroreGenerico(){
        creaAstaIngleseViewModel.erroreGenerico.observe(this, (errore) ->{
            if(creaAstaIngleseViewModel.isErroreGenerico()){
                Toast.makeText(this, errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void osservaApriGalleria(){
        creaAstaIngleseViewModel.apriGalleria.observe(this, (intent) -> {
            if(creaAstaIngleseViewModel.isApriGalleria()){
                resultLauncher.launch(intent);
                registraRisultati();
            }
        });
    }
    public void osservaImmagineConvertita(){
        creaAstaIngleseViewModel.immagineConvertita.observe(this, (immagine) -> {
            if(creaAstaIngleseViewModel.isImmagineConverita()){
                bitmap = immagine;
                immagineProdotto.setImageBitmap(immagine);
            }
        });
    }



    private void prelevaImmagine(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            resultLauncher.launch(intent);
        } else {
            Toast.makeText(this, "Nessuna app disponibile per gestire la selezione delle immagini", Toast.LENGTH_SHORT).show();
        }
    }
    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            creaAstaIngleseViewModel.setImmagine(result,VenditoreAstaInglese.this);
//                            uriImmagine = result.getData().getData();
//                            displayImage(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


//    // Metodo per comprimere un'immagine Bitmap e convertirla in un array di byte
//    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
//        return outputStream.toByteArray();
//    }
//
//    private void displayImage(Uri uri) {
//        try {
//            InputStream inputStream = this.getContentResolver().openInputStream(uri);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//            // Controllo dell'orientamento dell'immagine e rotazione se necessario
//            int orientation = getImageOrientation(uri);
//            if (orientation != 0) {
//                Matrix matrix = new Matrix();
//                matrix.postRotate(orientation);
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            }
//
//            // Ridimensiona l'immagine per adattarla alla dimensione desiderata
//            int targetWidth = 500; // Imposta la larghezza desiderata
//            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth())); // Calcola l'altezza in base al rapporto
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
//
//            // Comprimi l'immagine
//            byte[] compressedImageBytes = compressAndConvertToByteArray(resizedBitmap);
//
//            // Imposta l'immagine ridimensionata nella ImageView
//            immagineProdotto.setImageBitmap(resizedBitmap);
//
//            // Salva i byte compressi per l'invio
//            img = compressedImageBytes;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Metodo per ottenere l'orientamento dell'immagine dalla Uri
//    private int getImageOrientation(Uri uri) {
//        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
//        try {
//            InputStream inputStream = this.getContentResolver().openInputStream(uri);
//            ExifInterface exifInterface = new ExifInterface(inputStream);
//            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return getImageRotation(orientation);
//    }
//
//    // Metodo per ottenere la rotazione in gradi in base all'orientamento
//    private int getImageRotation(int orientation) {
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                return 90;
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                return 180;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                return 270;
//            default:
//                return 0;
//        }
//    }
//    public void handlePopUp(ArrayList<String> switchTexts){
//        this.listaCategorieScelte = switchTexts;
//        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
//        for (String categoria : switchTexts) {
//            Log.d("PopUpHandler", "Categoria: " + categoria);
//        }
//    }
//    public void handleID(int id){
//        this.idAsta = id;
//        AstaIngleseDAO astaIngleseDAO = new AstaIngleseDAO(VenditoreAstaInglese.this);
//        if(!listaCategorieScelte.isEmpty()){
//            astaIngleseDAO.openConnection();
//            Log.d("id recuperato è Venditore inglese : " , " id: " + idAsta);
//            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
//            astaIngleseDAO.inserisciCategorieAstaInglese(asta);
//            astaIngleseDAO.closeConnection();
//
//        }else{
//            astaIngleseDAO.closeConnection();
//        }
//        Intent intent = new Intent(VenditoreAstaInglese.this, MainActivity.class);//test del login
//        intent.putExtra("email", email);
//        intent.putExtra("tipoUtente", "venditore");
//        startActivity(intent);
//        progressBarVenditoreAstaInglese.setVisibility(View.INVISIBLE);
//        setAllClickable(relativeLayoutAstaInglese,true);
//        Toast.makeText(this, "Asta creata con successo!", Toast.LENGTH_SHORT).show();
//    }


}
