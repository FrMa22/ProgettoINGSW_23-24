package com.example.progettoingsw.view;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.NotificheDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.NotificheAdapter;
import com.example.progettoingsw.viewmodel.SchermataNotificheViewModel;


public class SchermataNotifiche extends GestoreComuniImplementazioni implements PopUpNotifiche.PopupDismissListener {
    private SchermataNotificheViewModel schermataNotificheViewModel;
    private ImageButton bottoneBackNotifiche;
    private String tipoUtente;
    private String email;
    private NotificheAdapter adapterNotifiche;
    private NotificheDAO notificheDAO;
    private int numeroNotifiche;
    private ProgressBar progressBarSchermataNotifiche;
    private TextView text_view_nessuna_notifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_notifiche);
//        numeroNotifiche = 0;
        adapterNotifiche = new NotificheAdapter(this);

        schermataNotificheViewModel = new ViewModelProvider(this).get(SchermataNotificheViewModel.class);
        osservaIsAcquirente();
        osservaVaiInNotificaPopUp();
        schermataNotificheViewModel.getTipoUtente();

        progressBarSchermataNotifiche = findViewById(R.id.progressBarSchermataNotifiche);
        //progressBarSchermataNotifiche.setVisibility(View.VISIBLE);
        text_view_nessuna_notifica = findViewById(R.id.text_view_nessuna_notifica);

//        email = getIntent().getStringExtra("email");
//        tipoUtente=getIntent().getStringExtra("tipoUtente");

        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewNotifiche = findViewById(R.id.recycler_view_notifiche);
        // Utilizza GridLayoutManager con due colonne
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewNotifiche.setLayoutManager(gridLayoutManager);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, gridLayoutManager.getOrientation());
        recyclerViewNotifiche.addItemDecoration(dividerItemDecoration);

        adapterNotifiche.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato

                Log.d("onclick" ,"QUI");
                schermataNotificheViewModel.gestisciClickRecyclerView(recyclerViewNotifiche,adapterNotifiche,v);


                // Ottieni la posizione dell'elemento cliccato
//                int position = recyclerViewNotifiche.getChildAdapterPosition(v);
//                schermataNotificheViewModel.onItemClick(position);
                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                //Object notifica = adapterNotifiche.getItem(position);

//                // Esegui le azioni desiderate con l'oggetto Asta
//                if (notifica instanceof NotificaItem) {
//                    int id = ((NotificaItem) notifica).getId();
//                    String titolo=((NotificaItem) notifica).getTitolo();
//                    String commento=((NotificaItem) notifica).getCommento();
//                    PopUpNotifiche popUpNotifiche=new PopUpNotifiche(SchermataNotifiche.this,titolo,commento,id,tipoUtente,SchermataNotifiche.this);
//                    popUpNotifiche.show();
//                }
            }
        });

        recyclerViewNotifiche.setAdapter(adapterNotifiche);
        bottoneBackNotifiche = findViewById(R.id.bottoneBackNotifiche);
        bottoneBackNotifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("numeroNotifiche", numeroNotifiche); // Passa il numero di notifiche come extra
//                setResult(Activity.RESULT_OK, resultIntent);
                onBackPressed();
//                Intent intent = new Intent(SchermataNotifiche.this, AcquirenteMainActivity.class);//test del login
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente", tipoUtente);
//                startActivity(intent);
            }
        });

//        notificheDAO=new NotificheDAO(this);
//        notificheDAO.openConnection();
//        notificheDAO.getNotificheForEmail(email,tipoUtente);
//        notificheDAO.closeConnection();




    }


    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
//        progressBarSchermataNotifiche.setVisibility(View.VISIBLE);
//        notificheDAO.openConnection();
//        notificheDAO.getNotificheForEmail(email,tipoUtente);
//        notificheDAO.closeConnection();
    }



//    public void updateNotifiche(ArrayList<Object> notifiche) {
//        boolean notificheVuote = notifiche == null || notifiche.isEmpty();
//        Log.d("updateNotifiche", "notificheVuote: " + notificheVuote);
//            if (!notificheVuote) {
//                AdapterNotifiche.setNotifiche(notifiche);
//                numeroNotifiche = notifiche.size();
//            } else {
//                numeroNotifiche = 0;
//                // Nessun nome asta trovato per l'email specificata
//            }
//            if(notificheVuote){
//                Log.d("updateNotifiche", "caso vuoto: ");
//                text_view_nessuna_notifica.setVisibility(View.VISIBLE);
//            }else{
//            text_view_nessuna_notifica.setVisibility(View.INVISIBLE);
//        }
//        progressBarSchermataNotifiche.setVisibility(View.GONE);
//
//    }

    @Override
    public void onPopupDismissed() {
        Log.d("onPopupDismissed di schermata notifiche","getTipoUtente");
        schermataNotificheViewModel.getTipoUtente();
    }
    public void osservaIsAcquirente(){
        schermataNotificheViewModel.isAcquirente.observe(this, (valore) -> {
            if(valore){
                osservaNotificheAcquirente();
                schermataNotificheViewModel.getNotificheAcquirente();
            }else{
                osservaNotificheVenditore();
                schermataNotificheViewModel.getNotificheVenditore();
            }
        });
    }
    public void osservaNotificheAcquirente(){
        schermataNotificheViewModel.notificheAcquirenteRecuperate.observe(this, (listaNotifiche) -> {
            if(schermataNotificheViewModel.isNotificheAcquirenteRecuperate()){
                adapterNotifiche.setNotificheAcquirente(listaNotifiche);
            }else{

            }
        });
    }
    public void osservaNotificheVenditore(){
        schermataNotificheViewModel.notificheVenditoreRecuperate.observe(this, (listaNotifiche) -> {
            if(schermataNotificheViewModel.isNotificheVenditoreRecuperate()){
                adapterNotifiche.setNotificheVenditore(listaNotifiche);
            }else{

            }
        });
    }
    public void osservaNotificheAssenti(){
        schermataNotificheViewModel.notificheAssenti.observe(this, (valore) -> {
            if(valore){
                Toast.makeText(this, "non ci sono notifiche", Toast.LENGTH_SHORT).show();
            }else{

            }
        });
    }
    public void osservaVaiInNotificaPopUp(){
        schermataNotificheViewModel.vaiInNotificaPopUp.observe(this, (valore) -> {
            if(valore){
                Log.d("creo il popup" ,"qui");
                PopUpNotifiche popUpNotifiche=new PopUpNotifiche(SchermataNotifiche.this, SchermataNotifiche.this,SchermataNotifiche.this);
                popUpNotifiche.show();
            }
        });
    }


}
