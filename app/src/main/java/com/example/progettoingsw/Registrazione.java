package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Spinner;
import android.widget.TextView;
public class Registrazione extends AppCompatActivity {
    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);

        controller = new Controller();
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(MainActivity.class));
                controller.redirectActivity(Registrazione.this, MainActivity.class);

            }
        });

        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(Registrazione.this, CampiFacoltativiRegistrazione.class);

            }
        });
    }
}