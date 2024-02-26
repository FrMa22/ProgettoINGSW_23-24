package com.example.progettoingsw.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.google.android.material.button.MaterialButton;

public class ProfiloVenditore extends GestoreComuniImplementazioni {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilo_venditore);

        MaterialButton bottonestoricoAste=(MaterialButton)findViewById(R.id.bottoneStoricoAsteProfiloVenditore);

        bottonestoricoAste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfiloVenditore.this, "StoricoAste", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
