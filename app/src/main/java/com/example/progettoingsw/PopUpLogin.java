package com.example.progettoingsw;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class PopUpLogin extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_login);

        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        MaterialButton bottoneAcquirente = findViewById(R.id.bottoneAcquirente);
        bottoneAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre schermata registrazione
                MainActivity.redirectActivity(PopUpLogin.this, HomeUtente.class);
            }
        });



    }

}
