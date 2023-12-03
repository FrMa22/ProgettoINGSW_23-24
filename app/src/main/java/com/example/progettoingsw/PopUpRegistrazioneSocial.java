package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpRegistrazioneSocial extends AppCompatActivity {

    private Controller controller;
    String opzioneSelezionata;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_registrazione_social);

        controller = new Controller();



        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        int offsetY = (int) (height * 0.15);
        getWindow().setLayout((int) (width * .763), (int) (height * .4475));
        getWindow().setGravity(Gravity.CENTER);
        getWindow().getAttributes().y = offsetY;



        //Riferimenti ai widget all'interno del pop up(quindi qui si opera come una normale activity)
        MaterialButton bottoneChiudiRegistrazioneSocial=(MaterialButton) findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        MaterialButton bottoneConfermaRegistrazioneSocial=(MaterialButton) findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        EditText editTextUsernameRegistrazioneSocial=(EditText) findViewById(R.id.editTextUsernameRegistrazioneSocial);


        Spinner spinnerRegistrazioneSocial=(Spinner) findViewById(R.id.spinner_social_registrazione);
        ArrayAdapter<CharSequence> adapterSpinnerRegistrazioneSocial=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(PopUpRegistrazioneSocial.this,R.array.elencoSocialRegistrazione, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterSpinnerRegistrazioneSocial.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerRegistrazioneSocial.setAdapter(adapterSpinnerRegistrazioneSocial);


        spinnerRegistrazioneSocial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //fa qualcosa se si è selezionato qualcosa
                opzioneSelezionata=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //non fa nulla
            }
        });


        bottoneChiudiRegistrazioneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottoneConfermaRegistrazioneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameSocialRegistrazione=editTextUsernameRegistrazioneSocial.getText().toString();
                String profiloSocialRegistrazione="https://www."+ opzioneSelezionata+".com/"+usernameSocialRegistrazione;
                //ElencoSocialRegistrazione.add(profiloSocialRegistrazione);Aggiungere social al database o qualcosa di back end
                Intent intent = new Intent(PopUpRegistrazioneSocial.this, CampiFacoltativiRegistrazione.class);
                Toast.makeText(getApplicationContext(),"Social Aggiunto correttamente!" + profiloSocialRegistrazione,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });




    }
















}
