package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentProfilo;

public class PopUpModificaCampoProfilo extends AppCompatActivity {

    private Controller controller;

    String testo_textview_campo_titolo;
    String testo_textview_valore;
    private AcquirenteFragmentProfilo acquirenteFragmentProfilo;

    private TextView textview_campo_nome;
    private TextView textview_valore_attuale;
    private TextView textview_titolo;
    private Button button_annulla_modifica;
    private Button button_conferma_modifica;
    private EditText edittext_valore_modificato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_modifica_campo_profilo);

        controller = new Controller();

        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        int offsetY = (int) (height * 0.15);
        getWindow().setLayout((int) (width * .763), (int) (height * .4475));
        getWindow().setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        getWindow().getAttributes().y = offsetY;


        textview_campo_nome = findViewById(R.id.textview_campo_nome);
        textview_valore_attuale = findViewById(R.id.textview_valore_attuale);
        textview_titolo = findViewById(R.id.textview_titolo);
        edittext_valore_modificato = findViewById(R.id.edittext_valore_modificato);

        Intent intent = getIntent();
        if (intent != null) {
            testo_textview_campo_titolo = intent.getStringExtra("testoTextViewCampoTitolo");
            testo_textview_valore = intent.getStringExtra("testoTextViewNomeValore");

            textview_campo_nome.setText("Attuale valore del campo " + testo_textview_campo_titolo + ": ");
            textview_valore_attuale.setText(testo_textview_valore);
            textview_titolo.setText("Modifica " + testo_textview_campo_titolo);

        }

        button_annulla_modifica = findViewById(R.id.button_annulla_modifica);
        button_annulla_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*button_conferma_modifica = findViewById(R.id.button_conferma_modifica);
        button_conferma_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valoreModificato = edittext_valore_modificato.getText().toString();
                Intent intent = new Intent(PopUpModificaCampoProfilo.this, AcquirenteFragmentProfilo.class);
                intent.putExtra("valoreDaModificare", testo_textview_campo_titolo);
                intent.putExtra("valoreModificato", valoreModificato);
                startActivity(intent);
            }
        });*/


    }
}