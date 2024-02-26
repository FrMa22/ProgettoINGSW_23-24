package com.example.progettoingsw.gui;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.R;

public class PopUpFiltroRicerca extends Dialog implements View.OnClickListener {
    private EditText prezzoMin;
    private EditText prezzoMax;
    private TextView scrittaPrezzo;
    private Button buttonSalva;

    public PopUpFiltroRicerca(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_filtro_ricerca);



        prezzoMin = findViewById(R.id.editTextPrezzoMin);
        prezzoMax = findViewById(R.id.editTextPrezzoMax);
        scrittaPrezzo = findViewById(R.id.textViewPrezzo);
        buttonSalva = findViewById(R.id.buttonSalvaFiltro);

        buttonSalva.setOnClickListener(this);
    }

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
                Toast.makeText(getContext(), "Inseriti valori errati!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Inserire valori validi!", Toast.LENGTH_LONG).show();
        }
    }
}
