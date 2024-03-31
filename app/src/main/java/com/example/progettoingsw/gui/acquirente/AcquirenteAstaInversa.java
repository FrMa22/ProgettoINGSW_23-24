package com.example.progettoingsw.gui.acquirente;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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


import com.example.progettoingsw.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;

import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.controllers_package.InsertAsta;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AcquirenteAstaInversa extends Fragment {
    private int idAsta;
    AppCompatButton bottoneConferma;
    AppCompatButton bottoneAnnullaAstaInversa;
    MaterialButton bottoneData;
    MaterialButton bottoneOra;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    ImageButton bottone_info;
    ActivityResultLauncher<Intent> resultLauncher;
    private ArrayList<String> listaCategorieScelte;
    private MaterialButton bottoneCategorieAstaInversa;
    EditText nomeAstaInversa;
    EditText prezzoAstaInversa;

    private String selectedDateString;
    private String selectedHourString;
    private byte [] img;//è presente nei metodi per fare select+aggiunta foto nella schermata quindi non serve a livello pratico qui
    Bitmap bitmap;
    Uri uriImmagine;
    byte[] imageBytes;
    String email;
    EditText DescrizioneProdottoAstaAstaInversa;

    public AcquirenteAstaInversa(String email){
        this.email = email.trim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acquirente_asta_inversa, container, false);
    }

    @Override
    public void onViewCreated(View view2, Bundle savedInstanceState) {
        super.onViewCreated(view2, savedInstanceState);

        AstaInversaDAO astaInversaDao = new AstaInversaDAO(AcquirenteAstaInversa.this);
        //ImmaginiDAO immaginiDAO=new ImmaginiDAO(this);
        listaCategorieScelte = new ArrayList<>();
        bottoneAnnullaAstaInversa = view2.findViewById(R.id.bottoneAnnullaAstaInversa);
        bottoneConferma = view2.findViewById(R.id.bottoneConfermaAstaInversa);
        immagineProdotto= view2.findViewById(R.id.imageViewCreaAstaAcquirente);
        bottoneInserisciImmagine = view2.findViewById(R.id.imageButtonInserisciImmagineCreaAstaAcquirente);
        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine

        bottoneCategorieAstaInversa =view2.findViewById(R.id.bottoneCategorieAstaInversa);
        bottoneCategorieAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(getContext(),AcquirenteAstaInversa.this,listaCategorieScelte);
                popUpAggiungiCategorieAsta.show();
            }
        });
        bottoneData =  view2.findViewById(R.id.bottoneDataAstaInversa);
        bottoneOra =  view2.findViewById(R.id.bottoneOraAstaInversa);

        nomeAstaInversa =view2.findViewById(R.id.editTextNomeProdottoAstaAstaInversa);
        DescrizioneProdottoAstaAstaInversa = view2.findViewById(R.id.editTextDescrizioneProdottoAstaAstaInversa);
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
                String nome = nomeAstaInversa.getText().toString();
                String prezzo = prezzoAstaInversa.getText().toString();
                String descrizione = DescrizioneProdottoAstaAstaInversa.getText().toString();
                String data = selectedDateString;
                String ora = selectedHourString;

                if (nome.isEmpty()) {
                    nomeAstaInversa.setError("Si prega di inserire un nome.");
                } else if (nome.length() > 100) {
                    nomeAstaInversa.setError("Il nome non può essere più lungo di 100 caratteri.");
                } else if (descrizione.length() > 250) {
                    DescrizioneProdottoAstaAstaInversa.setError("La descrizione non può essere più lunga di 250 caratteri.");
                } else if (prezzo.isEmpty()) {
                    prezzoAstaInversa.setError("Si prega di inserire un prezzo.");
                } else if (!prezzo.matches("^\\d+(\\.\\d+)?$")) {
                    prezzoAstaInversa.setError("Il prezzo deve essere un numero valido.");
                } else if (data.isEmpty()) {
                    Toast.makeText(getContext(), "Si prega di selezionare una data valida.", Toast.LENGTH_SHORT).show();
                } else {
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
                        Toast.makeText(getContext(), "La data e l'ora devono essere in futuro.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Chiamata al metodo per creare l'asta nel database
                        astaInversaDao.openConnection();
                        astaInversaDao.creaAstaInversa(nome, prezzo, data, ora, descrizione, email, imageBytes, listaCategorieScelte);
                        AppCompatActivity activity = (AppCompatActivity) requireContext();

                        Intent intent = new Intent(getContext(), AcquirenteMainActivity.class);//test del login
                        intent.putExtra("email", email);
                        intent.putExtra("tipoUtente", "acquirente");
                        startActivity(intent);
                    }
                }
            }
        });
        bottoneAnnullaAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", "acquirente");
                startActivity(intent);
            }
        });

    }


    private void apriCalendario() {
        // Ottieni l'anno, il mese e il giorno odierni
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Crea il DatePickerDialog con la data odierna come minima data selezionabile
        DatePickerDialog calendario = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Imposta la data selezionata nel bottone e nella variabile di classe
                bottoneData.setText(String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(day));
                selectedDateString = String.format("%04d-%02d-%02d", year, month + 1, day);
                bottoneData.setText(selectedDateString);
            }
        }, year, month, day);

        // Imposta la data minima selezionabile come data odierna
        calendario.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Mostra il DatePickerDialog
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
    public void handlePopUp(ArrayList<String> switchTexts){
        this.listaCategorieScelte = switchTexts;
        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
        for (String categoria : switchTexts) {
            Log.d("PopUpHandler", "Categoria: " + categoria);
        }
    }
    public void handleID(int id){
        this.idAsta = id;
        AstaInversaDAO astaInversaDAO = new AstaInversaDAO(AcquirenteAstaInversa.this);
        if(!listaCategorieScelte.isEmpty()){
            astaInversaDAO.openConnection();
            Log.d("id recuperato è ACquirente : " , " id: " + idAsta);
            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
            astaInversaDAO.inserisciCategorieAstaInversa(asta);
            astaInversaDAO.closeConnection();
        }else{
            astaInversaDAO.closeConnection();
        }

        Intent intent = new Intent(getContext(), AcquirenteMainActivity.class);//test del login
        intent.putExtra("email", email);
        intent.putExtra("tipoUtente", "acquirente");
        startActivity(intent);
    }
}