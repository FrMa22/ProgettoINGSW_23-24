package com.example.progettoingsw;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpFiltroRicerca extends AppCompatActivity {
    EditText prezzoMin;
    EditText prezzoMax;
    TextView scrittaPrezzo;
    Button buttonSalva;
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


        prezzoMin = findViewById(R.id.editTextPrezzoMin);
        prezzoMax = findViewById(R.id.editTextPrezzoMax);
        scrittaPrezzo = findViewById(R.id.textViewPrezzo);
        buttonSalva = findViewById(R.id.buttonSalvaFiltro);

        buttonSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prezzoMinString = prezzoMin.getText().toString();
                String prezzoMaxString = prezzoMax.getText().toString();

                if (!prezzoMinString.isEmpty() && !prezzoMaxString.isEmpty()) {
                    int minimoPrezzoIntero = Integer.parseInt(prezzoMinString);
                    int massimoPrezzoIntero = Integer.parseInt(prezzoMaxString);

                    if (minimoPrezzoIntero >= massimoPrezzoIntero) {
                        prezzoMin.setBackgroundResource(R.drawable.bordo_rosso_curvo);
                        prezzoMax.setBackgroundResource(R.drawable.bordo_rosso_curvo);
                        scrittaPrezzo.setTextColor(Color.parseColor("#FF0000"));
                        Toast.makeText(getApplicationContext(), "Inseriti valori errati!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Inserire valori validi!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
