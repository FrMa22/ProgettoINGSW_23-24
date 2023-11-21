package com.example.progettoingsw;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpLogin extends AppCompatActivity {
    Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_login);

        controller = new Controller();

        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        MaterialButton bottoneAcquirente = findViewById(R.id.bottoneAcquirente);
        bottoneAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre schermata registrazione
                controller.redirectActivity(PopUpLogin.this, HomeUtente.class);
            }
        });



    }

}
