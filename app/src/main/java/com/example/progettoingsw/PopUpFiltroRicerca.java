package com.example.progettoingsw;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpFiltroRicerca extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_filtro_ricerca);


        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        int offsetY = (int) (height * 0.15);
        getWindow().setLayout((int) (width * .965), (int) (height * .423));
        getWindow().setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        getWindow().getAttributes().y = offsetY;

    }

}
