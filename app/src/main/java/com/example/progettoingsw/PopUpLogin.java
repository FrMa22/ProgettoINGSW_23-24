package com.example.progettoingsw;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpLogin extends AppCompatActivity {
    AppCompatButton bottoneAcquirente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_login);


        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.385));

        bottoneAcquirente = findViewById(R.id.bottoneAcquirente);
        bottoneAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Controller.redirectActivity(PopUpLogin.this, HomeUtente.class);
            }
        });



    }

}
