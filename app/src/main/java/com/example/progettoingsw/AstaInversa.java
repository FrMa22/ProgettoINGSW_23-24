package com.example.progettoingsw;

import static com.example.progettoingsw.MainActivity.redirectActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class AstaInversa extends AppCompatActivity {

    MaterialButton bottoneData;
    MaterialButton bottoneOra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_inversa);

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaInversa);
        MaterialButton bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackAstaInversa);

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
                redirectActivity(AstaInversa.this, HomeUtente.class);
            }
        });

       /* bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, ???.class);
            }
        }); */
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




