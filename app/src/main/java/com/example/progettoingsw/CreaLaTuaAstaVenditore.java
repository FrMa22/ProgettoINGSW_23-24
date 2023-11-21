package com.example.progettoingsw;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;

public class CreaLaTuaAstaVenditore extends AppCompatActivity {

    String opzioneSelezionata;
    ImageView immagineProdotto;
    ImageButton back;
    Controller controller;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crea_la_tua_asta_venditore);

        controller = new Controller();

        immagineProdotto=(ImageView) findViewById(R.id.imageViewCreaAstaVenditore);
        ImageButton bottoneInserisciImmagine=(ImageButton) findViewById(R.id.imageButtonInserisciImmagineCreaAstaVenditore);


        back = findViewById(R.id.bottoneTornaIndietroCreaLaTuaAstaVenditore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.redirectActivity(CreaLaTuaAstaVenditore.this, HomeUtente.class);
            }
        });

        registraRisultati();

       bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine


        Spinner spinnerTipoAsta=(Spinner) findViewById(R.id.spinnerTipologiaAstaVenditore);
        ArrayAdapter<CharSequence> adapterSpinnerTipoAsta=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(CreaLaTuaAstaVenditore.this, R.array.elencoTipiAstaVenditore, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterSpinnerTipoAsta.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTipoAsta.setAdapter(adapterSpinnerTipoAsta);

        spinnerTipoAsta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    }



    private void prelevaImmagine(){
        Intent intent= new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri uriImmagine = result.getData().getData();
                            immagineProdotto.setImageURI(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(CreaLaTuaAstaVenditore.this, "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
