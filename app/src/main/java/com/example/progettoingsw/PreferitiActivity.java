package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.progettoingsw.controllers_package.Controller;

public class PreferitiActivity extends AppCompatActivity {
    Controller controller;
    private ImageButton backBottone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        controller = new Controller();
        backBottone = findViewById(R.id.backButton);

        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.redirectActivity(PreferitiActivity.this, FragmentHomeUtente.class);
            }
        });


    }
}