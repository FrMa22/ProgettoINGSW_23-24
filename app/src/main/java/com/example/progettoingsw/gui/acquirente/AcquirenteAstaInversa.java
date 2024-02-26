package com.example.progettoingsw.gui.acquirente;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.venditore.VenditoreFragmentCreaLaTuaAstaVenditore;
import com.google.android.material.button.MaterialButton;

public class AcquirenteAstaInversa extends GestoreComuniImplementazioni {

    Controller controller;
    MaterialButton bottoneData;
    MaterialButton bottoneOra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquirente_asta_inversa);

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaInversa);
        ImageButton bottoneBack =  findViewById(R.id.bottoneBackAstaInversa);

        bottoneData = (MaterialButton) findViewById(R.id.bottoneDataAstaInversa);
        bottoneOra = (MaterialButton) findViewById(R.id.bottoneOraAstaInversa);


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
                Controller.redirectActivity(AcquirenteAstaInversa.this, AcquirenteFragmentHome.class);
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




