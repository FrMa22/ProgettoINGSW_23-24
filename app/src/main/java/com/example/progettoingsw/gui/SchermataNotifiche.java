package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.progettoingsw.DAO.NotificheDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.NotificheAdapter;
import com.example.progettoingsw.model.NotificaItem;

import java.util.ArrayList;


public class SchermataNotifiche extends AppCompatActivity {
    private String tipoUtente;
    private String email;
    private NotificheAdapter AdapterNotifiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_notifiche);

        AdapterNotifiche = new NotificheAdapter(this, null);

        email = getIntent().getStringExtra("email");
        tipoUtente=getIntent().getStringExtra("tipoUtente");


        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewNotifiche = findViewById(R.id.recycler_view_notifiche);
        // Utilizza GridLayoutManager con due colonne
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewNotifiche.setLayoutManager(gridLayoutManager);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, gridLayoutManager.getOrientation());
        recyclerViewNotifiche.addItemDecoration(dividerItemDecoration);

        AdapterNotifiche.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewNotifiche.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object notifica = AdapterNotifiche.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (notifica instanceof NotificaItem) {
                    int id = ((NotificaItem) notifica).getId();
                    String titolo=((NotificaItem) notifica).getTitolo();
                    String commento=((NotificaItem) notifica).getCommento();
                    PopUpNotifiche popUpNotifiche=new PopUpNotifiche(SchermataNotifiche.this,titolo,commento,id,tipoUtente);
                    popUpNotifiche.show();
                }
            }
        });

        recyclerViewNotifiche.setAdapter(AdapterNotifiche);

        NotificheDAO notificheDAO=new NotificheDAO(this);
        notificheDAO.openConnection();
        notificheDAO.getNotificheForEmail(email,tipoUtente);




    }



    public void updateNotifiche(ArrayList<Object> notifiche) {

            if (notifiche != null) {
                AdapterNotifiche.setNotifiche(notifiche);

            } else {
                // Nessun nome asta trovato per l'email specificata
            }

    }

}
