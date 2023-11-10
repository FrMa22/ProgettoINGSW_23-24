package com.example.progettoingsw;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class InteressiRegistrazione extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interessi_registrazione);

        MaterialButton bottoneProseguiInteressiRegistrazione= (MaterialButton) findViewById(R.id.bottoneProseguiInteressiRegistrazione);
        ArrayList<String> ElencoInteressiRegistrazione=new ArrayList<String>();

        MaterialButton bottoneArte=(MaterialButton) findViewById(R.id.bottoneArte);
        MaterialButton bottoneElettrodomestici=(MaterialButton) findViewById(R.id.bottoneElettrodomestici);
        MaterialButton bottoneAutomobili=(MaterialButton) findViewById(R.id.bottoneAutomobili);
        MaterialButton bottoneMoto=(MaterialButton) findViewById(R.id.bottoneMoto);
        MaterialButton bottoneAutoEpoca=(MaterialButton) findViewById(R.id.bottoneAutoEpoca);
        MaterialButton bottoneMotoEpoca=(MaterialButton) findViewById(R.id.bottoneMotoEpoca);
        MaterialButton bottoneTelefonia=(MaterialButton) findViewById(R.id.bottoneTelefonia);
        MaterialButton bottoneComputer=(MaterialButton) findViewById(R.id.bottoneComputer);
        MaterialButton bottoneMusica=(MaterialButton) findViewById(R.id.bottoneMusica);
        MaterialButton bottoneLibri=(MaterialButton) findViewById(R.id.bottoneLibri);
        MaterialButton bottoneArredamentoCasa=(MaterialButton) findViewById(R.id.bottoneArredamentoCasa);
        MaterialButton bottoneFumetti=(MaterialButton) findViewById(R.id.bottoneFumetti);
        MaterialButton bottoneEsterniCasa=(MaterialButton) findViewById(R.id.bottoneEsterniCasa);
        MaterialButton bottoneSport=(MaterialButton) findViewById(R.id.bottoneSport);
        MaterialButton bottoneVestiario=(MaterialButton) findViewById(R.id.bottoneVestiario);
        MaterialButton bottoneGioielli=(MaterialButton) findViewById(R.id.bottoneGioielli);
        MaterialButton bottoneGiocattoli=(MaterialButton) findViewById(R.id.bottoneGiocattoli);
        MaterialButton bottoneFotografia=(MaterialButton) findViewById(R.id.bottoneFotografia);



        bottoneProseguiInteressiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Prosegui!",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(CampiFacoltativiRegistrazione.this, InteressiRegistrazione.class);
                //startActivity(intent);

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
