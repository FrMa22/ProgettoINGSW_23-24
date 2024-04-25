package com.example.progettoingsw.view;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.RegistrazioneFacoltativaDAO;
import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.acquirente.FragmentCreaAstaInversa;
import com.example.progettoingsw.view.acquirente.FragmentProfilo;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class RegistrazioneCampiFacoltativi extends GestoreComuniImplementazioni implements PopUpModificaSocialRegistrazione.PopupModificaSocialRegistrazioneDismissListener, PopUpRegistrazioneSocial.PopupRegistrazioneSocialDismissListener  {

    Controller controller;
    Intent intent;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String tipoUtente;
    private AcquirenteModel acquirenteModel;
    private VenditoreModel venditoreModel;
    String nomeSocial;
    String nomeUtente;
    ArrayList<String> elencoNomeSocialRegistrazione = new ArrayList<String>();
    ArrayList<String> elencoNomeUtenteSocialRegistrazione = new ArrayList<String>();

    MaterialButton bottoneAnnulla;
    ImageButton bottoneSocial;
    MaterialButton bottoneProseguiRegistrazione;
    EditText testoBio;
    EditText testoProvenienza;
    EditText testoSitoWeb;
    private Repository repository;
    RegistrazioneViewModel registrazioneViewModel;
    private AcquirenteModel acquirente;
    private VenditoreModel venditore;
    private ImageButton bottone_info_social_registrazione_campi_facoltativi;
    private CustomAdapter_gridview_profilo_social adapterSocial;

    private GridView gridView;

    String bio;
    String paese;
    String sitoWeb;
    private TextView text_view_nessun_social;
    private FirebaseAnalytics mFirebaseAnalytics;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_campi_facoltativi);

      /*  controller = new Controller();
        intent = getIntent();
        email =intent.getStringExtra("email").trim();
        tipoUtente =intent.getStringExtra("tipoUtente");
        nome =intent.getStringExtra("nome");
        cognome =intent.getStringExtra("cognome");
        password =intent.getStringExtra("password");

        Log.d("i valori in entrata facoltativi", " " + nome + cognome + email + password + tipoUtente);*/
// Inizializzazione di FirebaseAnalytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        bottoneSocial =  findViewById(R.id.bottoneSocialRegistrazione);
        bottoneProseguiRegistrazione = (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        testoBio = (EditText) findViewById(R.id.editTextBio);
        testoProvenienza = (EditText) findViewById(R.id.editTextPaeseDiProvenienza);
        testoSitoWeb = (EditText) findViewById(R.id.editTextSitoWeb);
        text_view_nessun_social = findViewById(R.id.text_view_nessun_social_registrazione_campi_facoltativi);

        registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        bottone_info_social_registrazione_campi_facoltativi = findViewById(R.id.bottone_info_social_registrazione_campi_facoltativi);
        bottone_info_social_registrazione_campi_facoltativi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegistrazioneCampiFacoltativi.this, "Cliccare un social per modificarlo. ", Toast.LENGTH_SHORT).show();
            }
        });

        gridView = findViewById(R.id.gridview_social_registrazione_campi_facoltativi);

        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

        setGridViewHeightBasedOnChildren(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomAdapter_gridview_profilo_social adapter = (CustomAdapter_gridview_profilo_social) gridView.getAdapter();
                List<String> socialNames = adapter.getSocialNames();
                List<String> socialLinks = adapter.getSocialLinks();

                String nome = socialNames.get(position);
                String link = socialLinks.get(position);
                registrazioneViewModel.setNomeSocialSelezionato(nome);
                registrazioneViewModel.setLinkSocialSelezionato(link);
                //fragmentProfiloViewModel.gestisciModificaSocial(nome,link);
                PopUpModificaSocialRegistrazione popUpModificaSocialRegistrazione = new PopUpModificaSocialRegistrazione(RegistrazioneCampiFacoltativi.this, registrazioneViewModel,RegistrazioneCampiFacoltativi.this,RegistrazioneCampiFacoltativi.this );
                popUpModificaSocialRegistrazione.show();
            }
        });



        osservaAcquirenteModelPresente();
        osservaVenditoreModelPresente();
        osservaApriPopUpSocial();
        osservaProseguiInserimento();
        osservaListaSocialAcquirente();
        osservaListaSocialVenditore();
        osservaSocialVuoti();
        osservaValoriPresentiFacoltativiAcquirente();
        osservaValoriPresentiFacoltativiVenditore();
        osservaTornaInRegistrazione();

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrazioneViewModel.tornaInRegistrazione();
            }
        });


        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrazioneViewModel.apriPopUp();
            }
        });

        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                registrazioneViewModel.recuperaToken(RegistrazioneCampiFacoltativi.this);
                registrazioneViewModel.checkTipoUtente();

            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();
        registrazioneViewModel.inserisciSocialNellaLista();
        registrazioneViewModel.checkValoriPresentiFacoltativi();
    }
    private void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            // Se l'adapter è null o non ci sono elementi, imposta l'altezza a 20dp e esci dal metodo
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            gridView.setLayoutParams(params);
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int columns = gridView.getNumColumns();
        int rows = items / columns;
        if (items % columns != 0) {
            rows++;
        }

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        int itemHeight = listItem.getMeasuredHeight();

        for (int i = 0; i < rows; i++) {
            totalHeight += itemHeight;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
    public void updateSocialNames(List<String> socialNames, List<String> socialLinks) {
        try {
            System.out.println("in update Social names");
            //gridView = findViewById(R.id.gridview_social_activity_profilo);
            if (socialNames != null && !socialNames.isEmpty() && socialLinks != null && !socialLinks.isEmpty()) {
                // Aggiorna l'interfaccia utente con i nomi dei social
                System.out.println("in update social names con nomi e link non nulli e non vuoti ");

                System.out.println("Stampa a schermo dei nomi dei social");
                for (String nome : socialNames) {
                    System.out.println(nome);
                }
                System.out.println("Stampa a schermo dei link dei social");
                for (String link : socialLinks) {
                    System.out.println(link);
                }

                gridView.setVisibility(View.VISIBLE);
                text_view_nessun_social.setVisibility(View.GONE);
                adapterSocial = new CustomAdapter_gridview_profilo_social(RegistrazioneCampiFacoltativi.this);
                gridView.setAdapter(adapterSocial);

                // Aggiungi i nomi dei social alla tua adapter
                adapterSocial.setData(socialNames, socialLinks);
                setGridViewHeightBasedOnChildren(gridView);

                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
                for (int i = 0; i < socialNames.size(); i++) {
                    Log.d("FragmentProfilo", "Nome Social: " + socialNames.get(i) + ", Link Social: " + socialLinks.get(i));
                }
            } else {
                text_view_nessun_social.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                // Rimuovi tutti i dati dall'adattatore e aggiorna la GridView
                gridView = findViewById(R.id.gridview_social_activity_profilo);
                adapterSocial = new CustomAdapter_gridview_profilo_social(RegistrazioneCampiFacoltativi.this);
                gridView.setAdapter(adapterSocial);

                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
                Log.d("FragmentProfilo", "Nessun social disponibile");

                // Imposta l'altezza della GridView a 50dp
                gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

            }

        } catch (IllegalStateException e) {
            Log.e("FragmentProfilo", "Error updating social names: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e("FragmentProfilo", "Error updating social names: NUll pointer " + e.getMessage());
        }
    }
    public void messaggioErroreBio(String messaggio) {
        testoBio.setError(messaggio);
    }

    public void messaggioErrorePaese(String messaggio) {
        testoProvenienza.setError(messaggio);
    }

    public void messaggioErroreSitoWeb(String messaggio) {
        testoSitoWeb.setError(messaggio);
    }


    public void osservaMessaggioErroreBio() {
        registrazioneViewModel.messaggioErroreBio.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreBio()) {
                messaggioErroreBio(messaggio);
            }
        });
    }

    public void osservaMessaggioErrorePaese() {
        registrazioneViewModel.messaggioErrorePaese.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorePaese()) {
                messaggioErrorePaese(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreSitoWeb() {
        registrazioneViewModel.messaggioErroreSitoWeb.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreSitoWeb()) {
                messaggioErroreSitoWeb(messaggio);
            }
        });
    }

    public void osservaAcquirente() {
        registrazioneViewModel.getAcquirente().observe(this, new Observer<AcquirenteModel>() {
            @Override
            public void onChanged(AcquirenteModel acquirente) {
                // Aggiorna l'interfaccia utente con il modello recuperato
                osservaMessaggioErroreBio();
                osservaMessaggioErrorePaese();
                osservaMessaggioErroreSitoWeb();
                bio = testoBio.getText().toString().trim();
                paese = testoProvenienza.getText().toString().trim();
                sitoWeb = testoSitoWeb.getText().toString().trim();
                Log.d("osservaAcquirente", "valori : " + bio + paese + sitoWeb);
                registrazioneViewModel.registrazioneAcquirenteCompleta(bio, paese, sitoWeb, acquirente);
            }
        });
    }

    public void osservaVenditore() {
        registrazioneViewModel.getVenditore().observe(this, new Observer<VenditoreModel>() {
            @Override
            public void onChanged(VenditoreModel venditore) {
                // Aggiorna l'interfaccia utente con il modello recuperato
                osservaMessaggioErroreBio();
                osservaMessaggioErrorePaese();
                osservaMessaggioErroreSitoWeb();
                bio = testoBio.getText().toString().trim();
                paese = testoProvenienza.getText().toString().trim();
                sitoWeb = testoSitoWeb.getText().toString().trim();
                registrazioneViewModel.registrazioneVenditoreCompleta(bio, paese, sitoWeb, venditore);
            }
        });
    }

    public void osservaAcquirenteModelPresente() {
        registrazioneViewModel.acquirenteModelPresente.observe(this, (messaggio) -> {
            if (registrazioneViewModel.getAcquirenteModelPresente()) {
                registrazioneViewModel.recuperoAcquirente();
                osservaAcquirente();
            }
        });
    }

    public void osservaVenditoreModelPresente() {
        registrazioneViewModel.venditoreModelPresente.observe(this, (messaggio) -> {
            if (registrazioneViewModel.getVenditoreModelPresente()) {
                registrazioneViewModel.recuperoVenditore();
                osservaVenditore();
            }
        });
    }

    public void osservaProseguiInserimento() {
        registrazioneViewModel.proseguiInserimento.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isProseguiInserimento("inserito")) {
                registrazioneViewModel.controlloSocial();
                logEvent("Registrazione utente",null);
                Intent intent = new Intent(RegistrazioneCampiFacoltativi.this, RegistrazioneCategorie.class);
                startActivity(intent);
            } else if (registrazioneViewModel.isProseguiInserimento("inserimento fallito")) {

            }

        });
    }
    // Metodo per registrare un evento con Firebase Analytics
    private void logEvent(String eventName, Bundle bundle) {
        mFirebaseAnalytics.logEvent(eventName, bundle);
    }
    public void osservaApriPopUpSocial() {
        registrazioneViewModel.apriPopUpSocial.observe(this, (valore) -> {
            if (valore) {
                PopUpRegistrazioneSocial popUpRegistrazioneSocial = new PopUpRegistrazioneSocial(this, RegistrazioneCampiFacoltativi.this, registrazioneViewModel, RegistrazioneCampiFacoltativi.this);
                popUpRegistrazioneSocial.show();
            }
        });
    }

    @Override
    public void onPopupModificaSocialRegistrazioneDismissed() {
        //aggiorno lista social
        Log.d("onPopupModificaSocialRegistrazioneDismissed","entrato");
        registrazioneViewModel.inserisciSocialNellaLista();
    }
    public void osservaListaSocialAcquirente(){
        registrazioneViewModel.listaSocialAcquirente.observe(this, (lista) ->{
            if(registrazioneViewModel.isListaSocialAcquirente()){
                List<String> links = new ArrayList<>();
                List<String> nomi = new ArrayList<>();
                for (SocialAcquirenteModel social : lista) {
                    links.add(social.getLink());
                    nomi.add(social.getNome());
                }
                gridView.setVisibility(View.VISIBLE);
                updateSocialNames(nomi, links);
            }
        });
    }
    public void osservaListaSocialVenditore(){
        registrazioneViewModel.listaSocialVenditore.observe(this, (lista) ->{
            if(registrazioneViewModel.isListaSocialVenditore()){
                List<String> links = new ArrayList<>();
                List<String> nomi = new ArrayList<>();
                for (SocialVenditoreModel social : lista) {
                    links.add(social.getLink());
                    nomi.add(social.getNome());
                }
                gridView.setVisibility(View.VISIBLE);
                updateSocialNames(nomi, links);
            }
        });
    }
    public void osservaSocialVuoti(){
        registrazioneViewModel.socialVuoti.observe(this, (valore)->{
            if(valore){
                text_view_nessun_social.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPopupRegistrazioneSocialDismissed() {
        //aggiorno lista social
        Log.d("onPopupRegistrazioneSocialDismissed","entrato");
        registrazioneViewModel.inserisciSocialNellaLista();
    }
    public void osservaValoriPresentiFacoltativiAcquirente(){
        registrazioneViewModel.valoriPresentiFacoltativiAcquirente.observe(this, (utente)->{
            if(registrazioneViewModel.isValoriPresentiFacoltativiAcquirente()){
                Log.d("osservaValoriPresentiFacoltativiAcquirente","entrato " + utente.getBio());
                testoBio.setText(utente.getBio());
                testoProvenienza.setText(utente.getAreageografica());
                testoSitoWeb.setText(utente.getLink());
                registrazioneViewModel.stampaLista();
            }
        });
    }
    public void osservaValoriPresentiFacoltativiVenditore(){
        registrazioneViewModel.valoriPresentiFacoltativiVenditore.observe(this, (utente) ->{
            if(registrazioneViewModel.isValoriPresentiFacoltativiVenditore()){
                Log.d("osservaValoriPresentiFacoltativiVenditore","entrato");
                testoBio.setText(utente.getBio());
                testoProvenienza.setText(utente.getAreageografica());
                testoSitoWeb.setText(utente.getLink());
                registrazioneViewModel.stampaLista();
            }
        });
    }
    public void osservaTornaInRegistrazione(){
        registrazioneViewModel.tornaInRegistrazione.observe(this, (valore) ->{
            if(valore){
                Intent intent = new Intent(RegistrazioneCampiFacoltativi.this, Registrazione.class);
                startActivity(intent);
            }
        });
    }
}