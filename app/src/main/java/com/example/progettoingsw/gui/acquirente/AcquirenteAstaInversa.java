package com.example.progettoingsw.gui.acquirente;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.progettoingsw.DAO.ImmaginiDAO;
import com.example.progettoingsw.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.venditore.VenditoreFragmentCreaLaTuaAstaVenditore;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AcquirenteAstaInversa extends Fragment {
    AppCompatButton bottoneConferma;
    MaterialButton bottoneData;
    MaterialButton bottoneOra;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    ImageButton bottone_info;
    ActivityResultLauncher<Intent> resultLauncher;
    EditText prodottoAstaInversa;
    EditText prezzoAstaInversa;

    private String selectedDateString;
    private String selectedHourString;
    private byte [] img;//è presente nei metodi per fare select+aggiunta foto nella schermata quindi non serve a livello pratico qui
    Bitmap bitmap;
    Uri uriImmagine;
    byte[] imageBytes;
    String email;
    EditText editTextDescrizioneProdottoAstaAstaInversa;

    public AcquirenteAstaInversa(String email){
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acquirente_asta_inversa, container, false);
    }

    @Override
    public void onViewCreated(View view2, Bundle savedInstanceState) {
        super.onViewCreated(view2, savedInstanceState);

        AstaInversaDAO astaInversaDao = new AstaInversaDAO();
        //ImmaginiDAO immaginiDAO=new ImmaginiDAO(this);

        bottoneConferma = view2.findViewById(R.id.bottoneConfermaAstaInversa);
        immagineProdotto= view2.findViewById(R.id.imageViewCreaAstaAcquirente);
        bottoneInserisciImmagine = view2.findViewById(R.id.imageButtonInserisciImmagineCreaAstaAcquirente);
        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine

        bottoneData =  view2.findViewById(R.id.bottoneDataAstaInversa);
        bottoneOra =  view2.findViewById(R.id.bottoneOraAstaInversa);

        prodottoAstaInversa=view2.findViewById(R.id.editTextNomeProdottoAstaAstaInversa);
        editTextDescrizioneProdottoAstaAstaInversa = view2.findViewById(R.id.editTextDescrizioneProdottoAstaAstaInversa);
        prezzoAstaInversa=view2.findViewById(R.id.editTextPrezzoAstaInversa);

        imageBytes=null;

        bottone_info = view2.findViewById(R.id.bottone_info);
        bottone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        registraRisultati();

        //bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        // Imposta l'immagine nel tuo ImageView
        //immagineProdotto.setImageBitmap(bitmap);

        bottoneData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apriCalendario();
            }
        });

        bottoneOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apriOrologio();
            }
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Controller.redirectActivity(AcquirenteAstaInversa.this, AcquirenteFragmentHome.class);


                String nome = prodottoAstaInversa.getText().toString();
                String prezzo = prezzoAstaInversa.getText().toString();
                String descrizione = editTextDescrizioneProdottoAstaAstaInversa.getText().toString();
                String data=selectedDateString;
                String ora=selectedHourString;

                // Chiamata al metodo per creare l'asta nel database
                astaInversaDao.openConnection();
                astaInversaDao.creaAstaInversa(nome,prezzo,data,ora,descrizione,email,imageBytes);
                astaInversaDao.closeConnection();

            }
        });

    }


    private void apriCalendario(){
        DatePickerDialog calendario = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                bottoneData.setText(String.valueOf(year) + "." + String.valueOf(month+1) + "." + String.valueOf(day));
                // Formatta la data selezionata come una stringa unica contenente anno, mese e giorno
                selectedDateString = String.format("%04d-%02d-%02d", year, month + 1, day);
                bottoneData.setText(selectedDateString);
            }
        }, 2023, 0, 1);
        calendario.show();
    }

    private void apriOrologio(){
        TimePickerDialog orologio = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minute) {
                bottoneOra.setText(String.valueOf(hours)+":"+String.valueOf(minute));
                selectedHourString=String.format("%02d:%02d", hours, minute);
                bottoneOra.setText(selectedHourString);
            }
        }, 00, 00, true);
        orologio.show();
    }

    public void updateFoto(byte[] foto){//metodo da usare in una select della foto
        img=foto;
        bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        // Imposta l'immagine nel tuo ImageView
        immagineProdotto.setImageBitmap(bitmap);
    }
    private void prelevaImmagine(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            resultLauncher.launch(intent);
        } else {
            Toast.makeText(requireContext(), "Nessuna app disponibile per gestire la selezione delle immagini", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(requireContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
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
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
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
            imageBytes = compressedImageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        dialog.show();
    }

}




