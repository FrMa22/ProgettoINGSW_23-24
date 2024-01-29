package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class Registrazione extends AppCompatActivity {
    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        controller = new Controller();
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(LoginActivity.class));
                controller.redirectActivity(Registrazione.this, LoginActivity.class);

            }
        });

        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(Registrazione.this, CampiFacoltativiRegistrazione.class);

            }
        });
    }
}