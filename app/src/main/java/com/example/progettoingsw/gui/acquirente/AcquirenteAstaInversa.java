package com.example.progettoingsw.gui.acquirente;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.progettoingsw.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.venditore.VenditoreFragmentCreaLaTuaAstaVenditore;
import com.google.android.material.button.MaterialButton;

public class AcquirenteAstaInversa extends GestoreComuniImplementazioni {

    Controller controller;
    MaterialButton bottoneData;
    MaterialButton bottoneOra;

    EditText prodottoAstaInversa;
    EditText prezzoAstaInversa;

    private String selectedDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquirente_asta_inversa);

        AstaInversaDAO astaInversaDao = new AstaInversaDAO();

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaInversa);
        ImageButton bottoneBack =  findViewById(R.id.bottoneBackAstaInversa);

        bottoneData = (MaterialButton) findViewById(R.id.bottoneDataAstaInversa);
        bottoneOra = (MaterialButton) findViewById(R.id.bottoneOraAstaInversa);

        prodottoAstaInversa=findViewById(R.id.editTextNomeProdottoAstaAstaInversa);
        prezzoAstaInversa=findViewById(R.id.editTextPrezzoAstaInversa);




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
                String data=selectedDateString;

                // Chiamata al metodo per creare l'asta nel database
                astaInversaDao.openConnection();
                astaInversaDao.creaAstaInversa(nome,prezzo,data);
                astaInversaDao.closeConnection();

            }
        });

//        bottoneBack.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//               Controller.redirectActivity(AcquirenteAstaInversa.this, VenditoreFragmentCreaLaTuaAstaVenditore.class);
//            }
//        });
    }


    private void apriCalendario(){
        DatePickerDialog calendario = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog orologio = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minute) {
                bottoneOra.setText(String.valueOf(hours)+":"+String.valueOf(minute));
            }
        }, 00, 00, true);
        orologio.show();
    }

}




