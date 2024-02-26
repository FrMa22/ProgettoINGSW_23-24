package com.example.progettoingsw.gui.venditore;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.google.android.material.button.MaterialButton;


public class VenditoreAstaRibasso extends GestoreComuniImplementazioni {
    MaterialButton bottoneConferma;
    ImageButton bottoneBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_ribasso);

        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(VenditoreAstaRibasso.this, AcquirenteFragmentHome.class);
            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

