package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();


        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        MaterialButton bottoneLogin = (MaterialButton) findViewById(R.id.bottonelogin);


        registrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre schermata registrazione
                controller.redirectActivity(MainActivity.this, Registrazione.class);
            }
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(MainActivity.this, PopUpLogin.class);

            }
        });

    }

}