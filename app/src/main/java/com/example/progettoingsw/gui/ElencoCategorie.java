package com.example.progettoingsw.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;

public class ElencoCategorie extends GestoreComuniImplementazioni {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_categorie);
        /*ImageButton imageButtonArte = findViewById(R.id.ImageButtonArte);
        ImageButton imageButtonAutomobili = findViewById(R.id.ImageButtonAutomobili);

        imageButtonArte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Azioni da eseguire quando viene cliccato ImageButtonArte
                Toast.makeText(ElencoCategorie.this, "Categoria Arte selezionata", Toast.LENGTH_SHORT).show();
                // Aggiungi qui la logica per gestire il clic sull'ImageButton dell'arte
            }
        });

        imageButtonAutomobili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Azioni da eseguire quando viene cliccato ImageButtonAutomobili
                Toast.makeText(ElencoCategorie.this, "Categoria Automobili selezionata", Toast.LENGTH_SHORT).show();
                // Aggiungi qui la logica per gestire il clic sull'ImageButton delle automobili
            }
        });*/
    }
}
