package com.example.progettoingsw;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.controllers_package.Controller;

public class PopUpLogin extends AppCompatActivity {
    AppCompatButton bottoneAcquirente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_login);


        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int)(width*.100),(int)(height*.500));
        getWindow().setGravity(Gravity.CENTER);
        bottoneAcquirente = findViewById(R.id.bottoneAcquirente);
        bottoneAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Controller.redirectActivity(PopUpLogin.this, MainActivity.class);
            }
        });



    }

}
