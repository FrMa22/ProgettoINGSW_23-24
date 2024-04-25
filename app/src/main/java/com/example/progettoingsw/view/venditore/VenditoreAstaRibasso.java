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

import com.example.progettoingsw.view.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.view.acquirente.MainActivity;
import com.example.progettoingsw.viewmodel.CreaAstaRibassoViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class VenditoreAstaRibasso extends GestoreComuniImplementazioni {
    private CreaAstaRibassoViewModel creaAstaRibassoViewModel;
    private Bitmap bitmap;
    AppCompatButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton button_info_asta_Ribasso;
    private int idAsta;
    EditText nome;
    EditText descrizione;
    private ImageButton imageButtonRimuoviImmagineCreaAstaRibasso;
    EditText baseAsta;
    EditText intervalloDecremento;
    EditText sogliaDecremento;
    EditText prezzominimoAsta;
    private byte [] img;
    ActivityResultLauncher<Intent> resultLauncher;
    private ArrayList<String> listaCategorieScelte;
    Uri uriImmagine;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    private MaterialButton bottoneCategorieAstaRibasso;
    private String email;
    private ProgressBar progressBarVenditoreAstaRibasso;
    private RelativeLayout relativeLayoutAstaRibasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_ribasso);



        progressBarVenditoreAstaRibasso = findViewById(R.id.progressBarVenditoreAstaRibasso);
        relativeLayoutAstaRibasso = findViewById(R.id.relativeLayoutAstaRibasso);
        listaCategorieScelte = new ArrayList<>();
        button_info_asta_Ribasso = findViewById(R.id.button_info_asta_Ribasso);
        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);
        imageButtonRimuoviImmagineCreaAstaRibasso = findViewById(R.id.imageButtonRimuoviImmagineCreaAstaRibasso);

        baseAsta=findViewById(R.id.editTextBaseAstaAstaRibasso);
        intervalloDecremento=findViewById(R.id.editTextTimerDecrementoAstaRibasso);
        sogliaDecremento=findViewById(R.id.editTextSogliaDecrementoAstaRibasso);
        prezzominimoAsta=findViewById(R.id.editTextPrezzoSegretoAstaRibasso);

        nome = findViewById(R.id.editTextNomeBeneCreaAstaRibasso);
        descrizione=findViewById(R.id.editTextDescrizioneCreaAstaRibasso);

        immagineProdotto= findViewById(R.id.imageViewCreaAstaRibasso);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaRibasso);
        bottoneInserisciImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(Intent.ACTION_PICK).setType("image/*"));
            }
        });
        registraRisultati();

        bottoneCategorieAstaRibasso = findViewById(R.id.bottoneCategorieAstaRibasso);
        bottoneCategorieAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaRibassoViewModel.apriPopUp();
            }
        });

        imageButtonRimuoviImmagineCreaAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                immagineProdotto.setImageResource(android.R.color.transparent); // Rimuove l'immagine
                bitmap = null;
//                imageBytes = null; // Reimposta il byte array a null
//                uriImmagine = null;
            }
        });




        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomeProdotto = nome.getText().toString().trim();
                String descrizioneProdotto = descrizione.getText().toString().trim();
                String base = baseAsta.getText().toString().trim();
                String intervallo = intervalloDecremento.getText().toString().trim();
                String soglia = sogliaDecremento.getText().toString().trim();
                String min = prezzominimoAsta.getText().toString().trim();
                creaAstaRibassoViewModel.creaAsta(nomeProdotto,descrizioneProdotto,base,intervallo,soglia,min,bitmap);
            }
        });



        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                creaAstaRibassoViewModel.premutoBack();

            }
        });
        button_info_asta_Ribasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaRibassoViewModel.apriPopUpInformazioni();
            }
        });

        creaAstaRibassoViewModel = new ViewModelProvider(this).get(CreaAstaRibassoViewModel.class);
        osservaErroreNome();
        osservaErroreDescrizione();
        osservaErroreBaseAsta();
        osservaErroreIntervalloDecrementale();
        osservaErrorePrezzoSegreto();
        osservaErroreSogliaDiDecremento();
        osservaErroreGenerico();
        osservaApriGalleria();
        osservaCreaPopUpInformazioni();
        osservaApriPopUpInformazioni();
        osservaApriPopUpCategorie();
        osservaAstaCreata();
        osservaImmagineConvertita();
        osservaTornaIndietro();

    }
    public void osservaTornaIndietro(){
        creaAstaRibassoViewModel.tornaIndietro.observe(this , (valore)->{
            if(valore){
                Intent intent = new Intent(VenditoreAstaRibasso.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInformazioni(){
        creaAstaRibassoViewModel.apriPopUpInformazioni.observe(this, (valore) ->{
            if(valore){
                creaAstaRibassoViewModel.creaPopUpInformazioni(this);
            }
        });
    }
    public void osservaCreaPopUpInformazioni(){
        creaAstaRibassoViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(creaAstaRibassoViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
    public void osservaApriPopUpCategorie(){
        creaAstaRibassoViewModel.apriPopUpCategoria.observe(this, (valore) ->{
            if(valore){
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(this, VenditoreAstaRibasso.this,creaAstaRibassoViewModel);
                popUpAggiungiCategorieAsta.show();
            }
        });
    }
    public void osservaAstaCreata(){
        creaAstaRibassoViewModel.astaCreata.observe(this, (result) -> {
            if(result){
                bottoneBack.performClick();
            }
        });
    }
    public void osservaErroreNome(){
        creaAstaRibassoViewModel.erroreNome.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreNome()){
                nome.setError(errore);
            }
        });
    }
    public void osservaErroreDescrizione(){
        creaAstaRibassoViewModel.erroreDescrizione.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreDescrizione()){
                descrizione.setError(errore);
            }
        });
    }
    public void osservaErroreBaseAsta(){
        creaAstaRibassoViewModel.erroreBaseAsta.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreBaseAsta()){
                baseAsta.setError(errore);
            }
        });
    }
    public void osservaErroreIntervalloDecrementale(){
        creaAstaRibassoViewModel.erroreIntervalloDecrementale.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreIntervalloDecrementale()){
                intervalloDecremento.setError(errore);
            }
        });
    }
    public void osservaErroreSogliaDiDecremento(){
        creaAstaRibassoViewModel.erroreSogliaDiDecremento.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreSogliaDiDecremento()){
                sogliaDecremento.setError(errore);
            }
        });
    }
    public void osservaErrorePrezzoSegreto(){
        creaAstaRibassoViewModel.errorePrezzoSegreto.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErrorePrezzoSegreto()){
                prezzominimoAsta.setError(errore);
            }
        });
    }
    public void osservaErroreGenerico(){
        creaAstaRibassoViewModel.erroreGenerico.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreGenerico()){
                Toast.makeText(this, errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void osservaApriGalleria(){
        creaAstaRibassoViewModel.apriGalleria.observe(this, (intent) -> {
            if(creaAstaRibassoViewModel.isApriGalleria()){
                resultLauncher.launch(intent);
                registraRisultati();
            }
        });
    }
    public void osservaImmagineConvertita(){
        creaAstaRibassoViewModel.immagineConvertita.observe(this, (immagine) -> {
            if(creaAstaRibassoViewModel.isImmagineConverita()){
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
                            creaAstaRibassoViewModel.setImmagine(result, VenditoreAstaRibasso.this);
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
//        Log.d("id recuperato è VenditoreRibasso : " , " id: " + idAsta);
//        AstaRibassoDAO astaRibassoDAO = new AstaRibassoDAO(VenditoreAstaRibasso.this);
//        if(!listaCategorieScelte.isEmpty()){
//            astaRibassoDAO.openConnection();
//            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
//            astaRibassoDAO.inserisciCategorieAstaRibasso(asta);
//            astaRibassoDAO.closeConnection();
//        }else{
//            astaRibassoDAO.closeConnection();
//        }
//        Intent intent = new Intent(VenditoreAstaRibasso.this, MainActivity.class);//test del login
//        intent.putExtra("email", email);
//        intent.putExtra("tipoUtente", "venditore");
//        startActivity(intent);
//        progressBarVenditoreAstaRibasso.setVisibility(View.VISIBLE);
//        setAllClickable(relativeLayoutAstaRibasso,false);
//        Toast.makeText(this, "Asta creata con successo!", Toast.LENGTH_SHORT).show();
//    }


}

