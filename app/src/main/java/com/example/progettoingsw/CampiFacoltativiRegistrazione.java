package com.example.progettoingsw;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CampiFacoltativiRegistrazione  extends AppCompatActivity {

    Controller controller;
    String opzioneSelezionata;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campi_facoltativi_registrazione);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        controller = new Controller();

        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        MaterialButton bottoneSocial = (MaterialButton) findViewById(R.id.bottoneSocialRegistrazione);
        MaterialButton bottoneSitoWeb = (MaterialButton) findViewById(R.id.bottoneSitoWebRegistrazione);
        MaterialButton bottoneProseguiRegistrazione= (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        ArrayList<String> ElencoSocialRegistrazione=new ArrayList<String>();


        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, PopUpLogin.class);
            }
        });




        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampiFacoltativiRegistrazione.this, PopUpRegistrazioneSocial.class);
                startActivity(intent);
            }
        });





        bottoneSitoWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre pop up per inserire il sito web
                Toast.makeText(getApplicationContext(),"Sito web!",Toast.LENGTH_SHORT).show();//stampa un messaggio a schermo quando si clicca
            }
        });



        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, InteressiRegistrazione.class);

            }
        });



    }





}
