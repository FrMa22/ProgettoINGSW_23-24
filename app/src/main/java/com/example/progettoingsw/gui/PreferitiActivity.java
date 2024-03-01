package com.example.progettoingsw.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;

public class PreferitiActivity extends GestoreComuniImplementazioni {
    Controller controller;
    private ImageButton backBottone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);
        controller = new Controller();
        backBottone = findViewById(R.id.backButton);

        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.redirectActivity(PreferitiActivity.this, AcquirenteMainActivity.class);
            }
        });


    }
}