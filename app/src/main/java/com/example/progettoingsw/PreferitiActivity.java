package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PreferitiActivity extends AppCompatActivity {

    private ImageButton backBottone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);

        backBottone = findViewById(R.id.backButton);
        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.redirectActivity(PreferitiActivity.this, HomeUtente.class);
            }
        });


    }
}