package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.progettoingsw.DAO.FragmentProfiloDAO;
import com.example.progettoingsw.DAO.NotificheDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.NotificheAdapter;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.model.NotificaItem;

import java.util.ArrayList;


public class SchermataNotifiche extends AppCompatActivity {
    private ImageButton bottoneBackNotifiche;
    private String tipoUtente;
    private String email;
    private NotificheAdapter AdapterNotifiche;
    private NotificheDAO notificheDAO;
    private int numeroNotifiche;
    private ProgressBar progressBarSchermataNotifiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_notifiche);
        numeroNotifiche = 0;
        AdapterNotifiche = new NotificheAdapter(this, null);

        progressBarSchermataNotifiche = findViewById(R.id.progressBarSchermataNotifiche);
        progressBarSchermataNotifiche.setVisibility(View.VISIBLE);

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
                    PopUpNotifiche popUpNotifiche=new PopUpNotifiche(SchermataNotifiche.this,titolo,commento,id,tipoUtente,SchermataNotifiche.this);
                    popUpNotifiche.show();
                }
            }
        });

        recyclerViewNotifiche.setAdapter(AdapterNotifiche);
        bottoneBackNotifiche = findViewById(R.id.bottoneBackNotifiche);
        bottoneBackNotifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("numeroNotifiche", numeroNotifiche); // Passa il numero di notifiche come extra
                setResult(Activity.RESULT_OK, resultIntent);
                onBackPressed();
            }
        });

        notificheDAO=new NotificheDAO(this);
        notificheDAO.openConnection();
        notificheDAO.getNotificheForEmail(email,tipoUtente);
        notificheDAO.closeConnection();




    }


    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
        progressBarSchermataNotifiche.setVisibility(View.VISIBLE);
        notificheDAO.openConnection();
        notificheDAO.getNotificheForEmail(email,tipoUtente);
        notificheDAO.closeConnection();
    }



    public void updateNotifiche(ArrayList<Object> notifiche) {

            if (notifiche != null) {
                AdapterNotifiche.setNotifiche(notifiche);
                numeroNotifiche = notifiche.size();
            } else {
                numeroNotifiche = 0;
                // Nessun nome asta trovato per l'email specificata
            }
        progressBarSchermataNotifiche.setVisibility(View.GONE);

    }


}
