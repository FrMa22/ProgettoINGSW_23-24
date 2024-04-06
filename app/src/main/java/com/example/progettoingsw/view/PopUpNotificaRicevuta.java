package com.example.progettoingsw.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;

public class PopUpNotificaRicevuta extends DialogPersonalizzato implements View.OnClickListener {
    private TextView textview_titolo_notifica_arrivata_popup;
    private TextView textViewChiudi;
    private TextView textViewApri;
    private int numeroNotifiche;
    private String email;
    private String tipoUtente;
    public PopUpNotificaRicevuta(Context context, int numeroNotifiche, String email, String tipoUtente) {
        super(context);
        this.numeroNotifiche = numeroNotifiche;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_notifica_ricevuta);
        // Inizializzazione delle view
        textview_titolo_notifica_arrivata_popup = findViewById(R.id.textview_titolo_notifica_arrivata_popup);
        if(numeroNotifiche == 1){
            textview_titolo_notifica_arrivata_popup.setText("Hai ricevuto 1 nuova notifica!");
        }else if(numeroNotifiche > 1){
            textview_titolo_notifica_arrivata_popup.setText("Hai ricevuto " + numeroNotifiche + " notifiche!");
                    }
        textViewChiudi = findViewById(R.id.textViewChiudi);
        textViewApri = findViewById(R.id.textViewApri);

        // Impostazione degli OnClickListener
        textViewChiudi.setOnClickListener(this);
        textViewApri.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Gestione del click sugli elementi
        if(v.getId() == R.id.textViewChiudi){
            // Chiude il popup quando si clicca su "Chiudi"
            dismiss();
        }else if(v.getId() == R.id.textViewApri){
            // Apre la SchermataNotifiche quando si clicca su "Apri"
            Intent intent = new Intent(getContext(), SchermataNotifiche.class);
            intent.putExtra("email", email);
            intent.putExtra("tipoUtente",tipoUtente);
            getContext().startActivity(intent);
            // Chiude il popup dopo aver avviato la nuova attività
            dismiss();
        }
    }
}
