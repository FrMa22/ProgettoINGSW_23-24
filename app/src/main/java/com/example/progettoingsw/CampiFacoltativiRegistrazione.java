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

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CampiFacoltativiRegistrazione  extends AppCompatActivity {

    String opzioneSelezionata;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campi_facoltativi_registrazione);

        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        MaterialButton bottoneSocial = (MaterialButton) findViewById(R.id.bottoneSocialRegistrazione);
        MaterialButton bottoneSitoWeb = (MaterialButton) findViewById(R.id.bottoneSitoWebRegistrazione);
        MaterialButton bottoneProseguiRegistrazione= (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        ArrayList<String> ElencoSocialRegistrazione=new ArrayList<String>();


        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //torna alla schermata precedente
                Intent intent = new Intent(CampiFacoltativiRegistrazione.this, PopUpLogin.class);
                startActivity(intent);
            }
        });

        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre elenco social inseribili
               Dialog popupSocialRegistrazioneDialog=new Dialog(CampiFacoltativiRegistrazione.this);

               //imposta il layout del popup
               popupSocialRegistrazioneDialog.setContentView(R.layout.pop_up_registrazione_social);
                //mostra il popup
                popupSocialRegistrazioneDialog.show();
               //Riferimenti ai widget all'interno del pop up(quindi qui si opera come una normale activity)
                MaterialButton bottoneChiudiRegistrazioneSocial=(MaterialButton) popupSocialRegistrazioneDialog.findViewById(R.id.bottoneChiudiRegistrazioneSocial);
                MaterialButton bottoneConfermaRegistrazioneSocial=(MaterialButton) popupSocialRegistrazioneDialog.findViewById(R.id.bottoneConfermaRegistrazioneSocial);
                EditText editTextUsernameRegistrazioneSocial=(EditText) popupSocialRegistrazioneDialog.findViewById(R.id.editTextUsernameRegistrazioneSocial);


                Spinner spinnerRegistrazioneSocial=(Spinner) popupSocialRegistrazioneDialog.findViewById(R.id.spinner_social_registrazione);
                ArrayAdapter<CharSequence> adapterSpinnerRegistrazioneSocial=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(CampiFacoltativiRegistrazione.this,R.array.elencoSocialRegistrazione, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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

                //azione bottonechiudiregistrazionesocial
                bottoneChiudiRegistrazioneSocial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupSocialRegistrazioneDialog.dismiss();
                    }
                });//


                bottoneConfermaRegistrazioneSocial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String usernameSocialRegistrazione=editTextUsernameRegistrazioneSocial.getText().toString();
                         String profiloSocialRegistrazione="https://www."+ opzioneSelezionata+".com/"+usernameSocialRegistrazione;
                        ElencoSocialRegistrazione.add(profiloSocialRegistrazione);
                        Toast.makeText(getApplicationContext(),"Social Aggiunto correttamente!",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });//fine pulsante social


        bottoneSitoWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre pop up per inserire il sito web
                Toast.makeText(getApplicationContext(),"Sito web!",Toast.LENGTH_SHORT).show();//stampa un messaggio a schermo quando si clicca
            }
        });



        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //PopUpLogin schermata_richiesta_login = new PopUpLogin();
                //schermata_richiesta_login.setVisible(true);
                Intent intent = new Intent(CampiFacoltativiRegistrazione.this, InteressiRegistrazione.class);
                startActivity(intent);

            }
        });



    }





}
