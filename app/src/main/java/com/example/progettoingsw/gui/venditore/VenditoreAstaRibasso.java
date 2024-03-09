package com.example.progettoingsw.gui.venditore;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.DAO.AstaRibassoDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class VenditoreAstaRibasso extends GestoreComuniImplementazioni {
    AppCompatButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton button_info_asta_Ribasso;
    EditText nome;
    EditText descrizione;
    EditText baseAsta;
    EditText intervalloDecremento;
    EditText sogliaDecremento;
    EditText prezzominimoAsta;
    private byte [] img;
    ActivityResultLauncher<Intent> resultLauncher;
    Uri uriImmagine;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_ribasso);
        AstaRibassoDAO astaRibassoDao = new AstaRibassoDAO();

        button_info_asta_Ribasso = findViewById(R.id.button_info_asta_Ribasso);
        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);

        baseAsta=findViewById(R.id.editTextBaseAstaAstaRibasso);
        intervalloDecremento=findViewById(R.id.editTextTimerDecrementoAstaRibasso);
        sogliaDecremento=findViewById(R.id.editTextSogliaDecrementoAstaRibasso);
        prezzominimoAsta=findViewById(R.id.editTextPrezzoSegretoAstaRibasso);

        nome = findViewById(R.id.editTextNomeBeneCreaAstaRibasso);
        descrizione=findViewById(R.id.editTextDescrizioneCreaAstaRibasso);
        String email=getIntent().getStringExtra("email");
        img=null;

        immagineProdotto= findViewById(R.id.imageViewCreaAstaRibasso);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaRibasso);
        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine



        registraRisultati();



        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Controller.redirectActivity(VenditoreAstaRibasso.this, AcquirenteFragmentHome.class);
                String nomeProdotto= nome.getText().toString();
                String descrizioneProdotto= descrizione.getText().toString();
                String base = baseAsta.getText().toString();
                String intervallo = intervalloDecremento.getText().toString();
                String soglia=sogliaDecremento.getText().toString();
                String min=prezzominimoAsta.getText().toString();

                // Chiamata al metodo per creare l'asta nel database
                astaRibassoDao.openConnection();
                astaRibassoDao.creaAstaRibasso(base,intervallo,soglia,min,nomeProdotto,descrizioneProdotto,email,img);
                astaRibassoDao.closeConnection();



            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        button_info_asta_Ribasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });
    }

    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


}

