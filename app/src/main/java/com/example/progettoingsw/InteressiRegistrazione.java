package com.example.progettoingsw;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class InteressiRegistrazione extends AppCompatActivity {

    Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interessi_registrazione);
        controller = new Controller();

        MaterialButton bottoneProseguiInteressiRegistrazione= (MaterialButton) findViewById(R.id.bottoneProseguiInteressiRegistrazione);
        ArrayList<String> ElencoInteressiRegistrazione=new ArrayList<String>();

        CheckBox bottoneArte= findViewById(R.id.bottoneArte);
        CheckBox bottoneElettrodomestici=findViewById(R.id.bottoneElettrodomestici);
        CheckBox bottoneAutomobili= findViewById(R.id.bottoneAutomobili);
        CheckBox bottoneMoto= findViewById(R.id.bottoneMoto);
        CheckBox bottoneAutoEpoca= findViewById(R.id.bottoneAutoEpoca);
        CheckBox bottoneMotoEpoca=findViewById(R.id.bottoneMotoEpoca);
        CheckBox bottoneTelefonia= findViewById(R.id.bottoneTelefonia);
        CheckBox bottoneComputer= findViewById(R.id.bottoneComputer);
        CheckBox bottoneMusica= findViewById(R.id.bottoneMusica);

        CheckBox bottoneLibri= findViewById(R.id.bottoneLibri);
        CheckBox bottoneArredamentoCasa= findViewById(R.id.bottoneArredamentoCasa);
        CheckBox bottoneFumetti= findViewById(R.id.bottoneFumetti);
        CheckBox bottoneEsterniCasa= findViewById(R.id.bottoneEsterniCasa);
        CheckBox bottoneSport= findViewById(R.id.bottoneSport);
        CheckBox bottoneVestiario= findViewById(R.id.bottoneVestiario);
        CheckBox bottoneGioielli= findViewById(R.id.bottoneGioielli);
        CheckBox bottoneGiocattoli= findViewById(R.id.bottoneGiocattoli);
        CheckBox bottoneFotografia= findViewById(R.id.bottoneFotografia);



        bottoneProseguiInteressiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(InteressiRegistrazione.this, HomeUtente.class);

            }
        });

        bottoneArte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Arte");
                Toast.makeText(getApplicationContext(),"Arte!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneElettrodomestici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Elettrodomestici");
                Toast.makeText(getApplicationContext(),"Elettrodomestici!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneAutomobili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Automobili");
                Toast.makeText(getApplicationContext(),"Automobili!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Moto");
                Toast.makeText(getApplicationContext(),"Moto!",Toast.LENGTH_SHORT).show();
            }
        });



        bottoneAutoEpoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("AutoEpoca");
                Toast.makeText(getApplicationContext(),"AutoEpoca!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneMotoEpoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("MotoEpoca");
                Toast.makeText(getApplicationContext(),"MotoEpoca!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneTelefonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Telefonia");
                Toast.makeText(getApplicationContext(),"Telefonia!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Computer");
                Toast.makeText(getApplicationContext(),"Computer!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Musica");
                Toast.makeText(getApplicationContext(),"Musica!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneLibri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Libri");
                Toast.makeText(getApplicationContext(),"Libri!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneArredamentoCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("ArredamentoCasa");
                Toast.makeText(getApplicationContext(),"ArredamentoCasa!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneFumetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Fumetti");
                Toast.makeText(getApplicationContext(),"Fumetti!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneEsterniCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("EsterniCasa");
                Toast.makeText(getApplicationContext(),"EsterniCasa!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Sport");
                Toast.makeText(getApplicationContext(),"Sport!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneVestiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Vestiario");
                Toast.makeText(getApplicationContext(),"Vestiario!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneGioielli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Gioielli");
                Toast.makeText(getApplicationContext(),"Gioielli!",Toast.LENGTH_SHORT).show();
            }
        });

        bottoneGiocattoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Giocattoli");
                Toast.makeText(getApplicationContext(),"Giocattoli!",Toast.LENGTH_SHORT).show();
            }
        });


        bottoneFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElencoInteressiRegistrazione.add("Fotografia");
                Toast.makeText(getApplicationContext(),"Fotografia!",Toast.LENGTH_SHORT).show();
            }
        });

        
    }
}
