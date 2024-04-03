package com.example.progettoingsw.gui.venditore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.gui.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.controllers_package.InsertAsta;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VenditoreAstaInglese extends GestoreComuniImplementazioni {
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
        email = getIntent().getStringExtra("email");
        img=null;

        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaInglese);
        bottoneBack =  findViewById(R.id.bottoneBackAstaInglese);
        bottone_info = findViewById(R.id.button_info_asta_inglese_venditore);
        imageButtonRimuoviImmagineCreaAstaInglese = findViewById(R.id.imageButtonRimuoviImmagineCreaAstaInglese);

        immagineProdotto= findViewById(R.id.imageViewCreaAstaInglese);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaInglese);
        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine

        bottoneCategorieAstaInglese = findViewById(R.id.bottoneCategorieAstaInglese);
        bottoneCategorieAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(VenditoreAstaInglese.this, VenditoreAstaInglese.this,listaCategorieScelte);
                popUpAggiungiCategorieAsta.show();
            }
        });
        imageButtonRimuoviImmagineCreaAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                immagineProdotto.setImageResource(android.R.color.transparent); // Rimuove l'immagine
                img = null; // Reimposta il byte array a null
                uriImmagine = null;
            }
        });

        bottone_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPopup();
            }
        });

        registraRisultati();

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VenditoreAstaInglese.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", "venditore");
                startActivity(intent);
//        AppCompatActivity activity = (AppCompatActivity) VenditoreAstaInglese.this;
//        Fragment fragment = new AcquirenteFragmentHome(email, "venditore");
//        ((AcquirenteMainActivity) activity).navigateToFragmentAndSelectIcon(fragment);
            }
        });

        bottoneConferma.setOnClickListener(v -> {
            String nomeProdotto = nome.getText().toString().trim();
            String descrizioneProdotto = descrizione.getText().toString().trim();
            String base = baseAsta.getText().toString().trim();
            String intervallo = intervalloAsta.getText().toString().trim();
            String rialzo = rialzoAsta.getText().toString().trim();

            if (nomeProdotto.isEmpty()) {
                nome.setError("Si prega di inserire un nome.");
            } else if (nomeProdotto.length() > 100) {
                nome.setError("Il nome non può essere più lungo di 100 caratteri.");
            } else if (descrizioneProdotto.length() > 250) {
                descrizione.setError("La descrizione non può essere più lunga di 250 caratteri.");
            } else if (base.isEmpty()) {
                baseAsta.setError("Si prega di inserire un prezzo base.");
            } else if (!base.matches("^\\d*\\.?\\d+$")) {
                baseAsta.setError("Si prega di inserire solo numeri per il prezzo base.");
            } else if (intervallo.isEmpty()) {
                intervalloAsta.setError("Si prega di inserire un intervallo per le offerte.");
            } else if (!intervallo.matches("^\\d{1,5}$")) {
                intervalloAsta.setError("L'intervallo deve contenere solo numeri e non può superare i 5 caratteri.");
            } else if (rialzo.isEmpty()) {
                rialzoAsta.setError("Si prega di inserire un valore minimo di rialzo.");
            } else if (!rialzo.matches("^\\d*\\.?\\d+$")) {
                rialzoAsta.setError("Si prega di inserire solo numeri per il valore minimo di rialzo.");
            } else {
                // Chiamata al metodo per creare l'asta nel database
                progressBarVenditoreAstaInglese.setVisibility(View.VISIBLE);
                setAllClickable(relativeLayoutAstaInglese, false);
                astaIngleseDao.openConnection();
                astaIngleseDao.creaAstaInglese(base, intervallo, rialzo, nomeProdotto, descrizioneProdotto, email, img);
            }
        });


    }



    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        dialog.show();
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
                            uriImmagine = result.getData().getData();
                            displayImage(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


    // Metodo per comprimere un'immagine Bitmap e convertirla in un array di byte
    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
        return outputStream.toByteArray();
    }

    private void displayImage(Uri uri) {
        try {
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Controllo dell'orientamento dell'immagine e rotazione se necessario
            int orientation = getImageOrientation(uri);
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            // Ridimensiona l'immagine per adattarla alla dimensione desiderata
            int targetWidth = 500; // Imposta la larghezza desiderata
            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth())); // Calcola l'altezza in base al rapporto
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

            // Comprimi l'immagine
            byte[] compressedImageBytes = compressAndConvertToByteArray(resizedBitmap);

            // Imposta l'immagine ridimensionata nella ImageView
            immagineProdotto.setImageBitmap(resizedBitmap);

            // Salva i byte compressi per l'invio
            img = compressedImageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per ottenere l'orientamento dell'immagine dalla Uri
    private int getImageOrientation(Uri uri) {
        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
        try {
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(inputStream);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getImageRotation(orientation);
    }

    // Metodo per ottenere la rotazione in gradi in base all'orientamento
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
    public void handlePopUp(ArrayList<String> switchTexts){
        this.listaCategorieScelte = switchTexts;
        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
        for (String categoria : switchTexts) {
            Log.d("PopUpHandler", "Categoria: " + categoria);
        }
    }
    public void handleID(int id){
        this.idAsta = id;
        AstaIngleseDAO astaIngleseDAO = new AstaIngleseDAO(VenditoreAstaInglese.this);
        if(!listaCategorieScelte.isEmpty()){
            astaIngleseDAO.openConnection();
            Log.d("id recuperato è Venditore inglese : " , " id: " + idAsta);
            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
            astaIngleseDAO.inserisciCategorieAstaInglese(asta);
            astaIngleseDAO.closeConnection();

        }else{
            astaIngleseDAO.closeConnection();
        }
        Intent intent = new Intent(VenditoreAstaInglese.this, AcquirenteMainActivity.class);//test del login
        intent.putExtra("email", email);
        intent.putExtra("tipoUtente", "venditore");
        startActivity(intent);
        progressBarVenditoreAstaInglese.setVisibility(View.INVISIBLE);
        setAllClickable(relativeLayoutAstaInglese,true);
        Toast.makeText(this, "Asta creata con successo!", Toast.LENGTH_SHORT).show();
    }


}
