package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import android.widget.Button;

import com.example.progettoingsw.controllers_package.Controller;

import java.util.ArrayList;

public class PopUpRicercaCategorie extends AppCompatActivity {
    Controller controller;
    Button button_annulla;
    Button button_cerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._pop_up_ricerca_categorie);

        controller = new Controller();

        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        GridView gridView = findViewById(R.id.gridview);
        button_annulla = findViewById(R.id.button_annulla);
        button_cerca = findViewById(R.id.button_cerca);

        button_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.redirectActivity(PopUpRicercaCategorie.this, FragmentSelezioneCategorie.class);
            }
        });

        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Passaggio a futura schermata di ricerca
            }
        });

        //Questo serve a recuperare informazioni (in questo caso un arrayList) da un altra activity
        Intent intent = getIntent();
        ArrayList<String> listaStringhe = intent.getStringArrayListExtra("listaStringhe");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaStringhe);

        gridView.setAdapter(adapter);
    }
}