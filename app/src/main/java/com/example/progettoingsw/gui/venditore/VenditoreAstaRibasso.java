package com.example.progettoingsw.gui.venditore;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.DAO.AstaRibassoDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.google.android.material.button.MaterialButton;


public class VenditoreAstaRibasso extends GestoreComuniImplementazioni {
    MaterialButton bottoneConferma;
    ImageButton bottoneBack;

    EditText baseAsta;
    EditText intervalloDecremento;
    EditText sogliaDecremento;
    EditText prezzominimoAsta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_ribasso);
        AstaRibassoDAO astaRibassoDao = new AstaRibassoDAO();


        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);

        baseAsta=findViewById(R.id.editTextBaseAstaAstaRibasso);
        intervalloDecremento=findViewById(R.id.editTextTimerDecrementoAstaRibasso);
        sogliaDecremento=findViewById(R.id.editTextSogliaDecrementoAstaRibasso);
        prezzominimoAsta=findViewById(R.id.editTextPrezzoSegretoAstaRibasso);

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Controller.redirectActivity(VenditoreAstaRibasso.this, AcquirenteFragmentHome.class);

                 String base = baseAsta.getText().toString();
                String intervallo = intervalloDecremento.getText().toString();
                String soglia=sogliaDecremento.getText().toString();
                String min=prezzominimoAsta.getText().toString();

                // Chiamata al metodo per creare l'asta nel database
                astaRibassoDao.openConnection();
                astaRibassoDao.creaAstaRibasso(base,intervallo,soglia,min);
                astaRibassoDao.closeConnection();



            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
