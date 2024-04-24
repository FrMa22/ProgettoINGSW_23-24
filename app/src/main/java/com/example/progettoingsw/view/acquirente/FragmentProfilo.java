package com.example.progettoingsw.view.acquirente;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.view.LeMieAste;
import com.example.progettoingsw.view.PopUpAggiungiSocialProfilo;
import com.example.progettoingsw.view.PopUpControlloPassword;
import com.example.progettoingsw.view.PopUpModificaCampiProfilo;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.view.LoginActivity;
import com.example.progettoingsw.view.PopUpModificaSocial;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
public class FragmentProfilo extends Fragment implements PopUpModificaCampiProfilo.PopupDismissListener, PopUpAggiungiSocialProfilo.PopupAggiungiSocialDismissListener, PopUpModificaSocial.PopupModificaSocialDismissListener {



    private TextView text_view_nessun_social;
    private MaterialButton button_partecipazione_aste;
    ImageButton button_log_out;
    MaterialButton button_le_mie_aste;
    ImageButton button_modifica;
    ImageButton bottone_info;
    Button button_cambia_password_profilo;
    ImageButton button_aggiungi_social;

    private GridView gridView;
    private CustomAdapter_gridview_profilo_social adapterSocial;

    private TextView textview_nome;
    private TextView textview_cognome;
    private TextView textview_email;
    private TextView textview_sitoweb;
    private TextView textview_paese;

    private TextView text_view_bio_profilo;
    private ProgressBar progressBarAcquirenteFragmentProfilo;
    private View view;
    private String email;
    String tipoUtente;
    private RelativeLayout relative_layout_fragment_profilo;
    public FragmentProfiloViewModel fragmentProfiloViewModel;

    public FragmentProfilo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Conserva il fragment durante i cambiamenti di configurazione
        fragmentProfiloViewModel = new ViewModelProvider(this).get(FragmentProfiloViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Assegna la variabile view nel metodo onCreateView
        view = inflater.inflate(R.layout.fragment_profilo, container, false);
        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       // fragmentProfiloViewModel = new ViewModelProvider(this).get(FragmentProfiloViewModel.class);

        relative_layout_fragment_profilo = view.findViewById(R.id.relative_layout_fragment_profilo);

        text_view_nessun_social = view.findViewById(R.id.text_view_nessun_social);
        textview_nome = view.findViewById(R.id.textview_nome);
        textview_cognome = view.findViewById(R.id.textview_cognome);
        textview_email = view.findViewById(R.id.textview_email);
        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
        textview_paese = view.findViewById(R.id.textview_paese);
        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);


        osservaAcquirenteModelPresente();
        osservaVenditoreModelPresente();
        osservaSocialAssenti();
        fragmentProfiloViewModel.checkTipoUtente();








         //Inizializza la GridView
        gridView = view.findViewById(R.id.gridview_social_activity_profilo);

// Imposta un'altezza minima per la GridView
        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

// Chiama il metodo per impostare l'altezza in base agli elementi
        setGridViewHeightBasedOnChildren(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomAdapter_gridview_profilo_social adapter = (CustomAdapter_gridview_profilo_social) gridView.getAdapter();
                List<String> socialNames = adapter.getSocialNames();
                List<String> socialLinks = adapter.getSocialLinks();

                String nome = socialNames.get(position);
                String link = socialLinks.get(position);
                //fragmentProfiloViewModel.gestisciModificaSocial(nome,link);
                PopUpModificaSocial popUpModificaSocial = new PopUpModificaSocial(getContext(), FragmentProfilo.this, fragmentProfiloViewModel, nome, link,FragmentProfilo.this);
                popUpModificaSocial.show();
            }
        });








        bottone_info = view.findViewById(R.id.bottone_info);
        bottone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cliccare un social per modificarlo. ", Toast.LENGTH_SHORT).show();
            }
        });


        button_log_out = view.findViewById(R.id.button_log_out_profilo);
        button_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                osservaEsci();
                fragmentProfiloViewModel.logout(requireContext());
                //Controller.redirectActivity(getContext(), LoginActivity.class);
            }
        });

        button_le_mie_aste = view.findViewById(R.id.button_le_mie_aste);
        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("le aste oooh");
                //passaggio alle mie aste
                fragmentProfiloViewModel.setApriLeMieAste(true);
            }
        });

        button_modifica = view.findViewById(R.id.button_modifica);
        button_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpModificaCampiProfilo popUpModificaCampiProfilo = new PopUpModificaCampiProfilo(getContext(), FragmentProfilo.this,fragmentProfiloViewModel,FragmentProfilo.this);
                popUpModificaCampiProfilo.show();
            }
        });

        button_cambia_password_profilo = view.findViewById(R.id.button_cambia_password_profilo);
        button_cambia_password_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpControlloPassword popUpControlloPassword = new PopUpControlloPassword(getContext(),FragmentProfilo.this ,fragmentProfiloViewModel);
                popUpControlloPassword.show();
            }
        });

        button_aggiungi_social = view.findViewById(R.id.button_aggiungi_social);
        button_aggiungi_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //email=Repository.getInstance().getAcquirenteModel().getIndirizzoEmail();
                //PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo = new PopUpAggiungiSocialProfilo(FragmentProfilo.this, email,tipoUtente);
                //System.out.println("popupAggiungiSocial  nel fragment ha email:"+email);
               // popUpAggiungiSocialProfilo.show();
                //chiamata di un metodo del viewmodel
                fragmentProfiloViewModel.setApriPopUpAggiungiSocial(true);//cliccando si dice al sistema che si vuole aprire il popup

            }
        });
//
//        text_view_nessun_social = view.findViewById(R.id.text_view_nessun_social);
//        textview_nome = view.findViewById(R.id.textview_nome);
//        textview_cognome = view.findViewById(R.id.textview_cognome);
//        textview_email = view.findViewById(R.id.textview_email);
//        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
//        textview_paese = view.findViewById(R.id.textview_paese);
//        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);
//        Log.d("Profilo" , "mail " + email +", tipoUtente " + tipoUtente + ".");
//
//        // Inizializza il DAO e recupera i dati dell'acquirente
//        FragmentProfiloDAO acquirente_fragment_profilo_DAO = new FragmentProfiloDAO(this, email, tipoUtente);
//        acquirente_fragment_profilo_DAO.openConnection();
//        acquirente_fragment_profilo_DAO.findUser();
//        acquirente_fragment_profilo_DAO.getSocialNamesForEmail();
//
//        button_partecipazione_aste = view.findViewById(R.id.button_partecipazione_aste);
//        button_partecipazione_aste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), SchermataPartecipazioneAste.class);
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente",tipoUtente);
//                startActivity(intent);
//            }
//        });
    }

    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
//        progressBarAcquirenteFragmentProfilo.setVisibility(View.VISIBLE);
//        setAllClickable(relative_layout_fragment_profilo,false);
//        setNavigationView(false);
//
//        Toast.makeText(getContext(), "la mail in ingresso è: " + email, Toast.LENGTH_SHORT).show();
//
//        // Inizializza il DAO e recupera i dati dell'acquirente
//        FragmentProfiloDAO acquirente_fragment_profilo_DAO = new FragmentProfiloDAO(this,email, tipoUtente);
//        acquirente_fragment_profilo_DAO.openConnection();
//        acquirente_fragment_profilo_DAO.findUser();
//        acquirente_fragment_profilo_DAO.getSocialNamesForEmail();
    }



//    public void updateEditTexts(Acquirente acquirente) {
//        if (acquirente != null) {
//            // Esempio: aggiorna l'interfaccia utente con i dati dell'acquirente
//            textview_nome.setText(acquirente.getNome());
//            textview_cognome.setText(acquirente.getCognome());
//            textview_email.setText(acquirente.getEmail());
//            textview_sitoweb.setText(acquirente.getSitoWeb());
//            textview_paese.setText(acquirente.getPaese());
//            text_view_bio_profilo.setText(acquirente.getBio());
//        } else {
//            // L'acquirente non è stato trovato
//        }
//    }
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
                gridView = view.findViewById(R.id.gridview_social_activity_profilo);
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
                    adapterSocial = new CustomAdapter_gridview_profilo_social(getContext());
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
                    gridView = view.findViewById(R.id.gridview_social_activity_profilo);
                    adapterSocial = new CustomAdapter_gridview_profilo_social(getContext());
                    gridView.setAdapter(adapterSocial);

                    // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
                    Log.d("FragmentProfilo", "Nessun social disponibile");

                    // Imposta l'altezza della GridView a 50dp
                    gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

                }
                setAllClickable(relative_layout_fragment_profilo, true);
                //progressBarAcquirenteFragmentProfilo.setVisibility(View.INVISIBLE);
                Log.d("update social names", "sblocco navigation bar");
                setNavigationView(true);

        }catch (IllegalStateException e) {
            // Gestione dell'eccezione
            Log.e("FragmentProfilo", "Error updating social names: " + e.getMessage());
        } catch (NullPointerException e) {
            // Gestione dell'eccezione
            Log.e("FragmentProfilo", "Error updating social names: NUll pointer " + e.getMessage());
        }
    }


    public void updateDatiUtente(String nome, String cognome, String email, String link, String paese, String bio){
        textview_nome.setText(nome);
        textview_cognome.setText(cognome);
        textview_email.setText(email);
        textview_sitoweb.setText(link);
        textview_paese.setText(paese);
        text_view_bio_profilo.setText(bio);
    }

    protected void setAllClickable(ViewGroup viewGroup, boolean enabled) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setAllClickable((ViewGroup) child, enabled);
            }
        }
    }

    //metodo per rendere clickabile o non la bottom navigation view, accede alla bottom di main tramite un metodo di main
    private void setNavigationView(Boolean valore) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                // Abilita la BottomNavigationView
                // Log.d("acquirente", "disabilito");
                activity.enableBottomNavigationView(valore);
            }

    }

    public void osservaEsci(){
        fragmentProfiloViewModel.esci.observe(getViewLifecycleOwner(), (valore)->{
            if(valore){
                Controller.redirectActivity(getContext(), LoginActivity.class);
            }
        });
    }
    public void osservaAcquirenteModelPresente(){
        fragmentProfiloViewModel.acquirenteModelPresente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (fragmentProfiloViewModel.getAcquirenteModelPresente()) {
                Toast.makeText(getContext(), "Entrato come acquirente in fragment profilo.", Toast.LENGTH_SHORT).show();
                //chiamate ai vari observer, tra cui quelli di cambiare schermata quindi pure per la gestione dei popup
                osservaSocialAcquirenteRecuperati();
                osservaAcquirenteRecuperato();//nel suo corpo ha un observer di un oggetto Acquirente Model che poi si usa per fare i vari get per un metodo che setta nella gui i valori
                osservaApriPopUpAggiungiSocial();
                osservaApriLeMieAste();
                //in teoria deve fare qui la chiamata per trovare i social dal backend
                fragmentProfiloViewModel.trovaSocialAcquirenteViewModel();//questa chiamata trova i social dal backend

            }
        });
    }


    public void osservaApriPopUpAggiungiSocial(){
        fragmentProfiloViewModel.apriPopUpAggiungiSocial.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (fragmentProfiloViewModel.getApriPopUpAggiungiSocial()){
                 //fa le cose che si farebbero premendo il pulsante aggiungi social
                PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo = new PopUpAggiungiSocialProfilo(FragmentProfilo.this,fragmentProfiloViewModel,FragmentProfilo.this);
                popUpAggiungiSocialProfilo.show();
            }
        });
    }

    public void osservaApriLeMieAste(){
        fragmentProfiloViewModel.apriLeMieAste.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (fragmentProfiloViewModel.getApriLeMieAste()){
                //fa le cose che si farebbero premendo il pulsante apri le mie aste
                Intent intent = new Intent(getContext(), LeMieAste.class);
                startActivity(intent);
            }
        });
    }


    public void osservaSocialAcquirentePresenti() {
        fragmentProfiloViewModel.socialAcquirentePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {//vede se sono presenti,li recupera,e poi deve aggiornare la gui
                System.out.println("in osserva social acquirente presenti");
                osservaSocialAcquirenteRecuperati();
                fragmentProfiloViewModel.recuperaSocialAcquirente();
            }


        });
    }

    public void osservaSocialAcquirenteRecuperati() {
        fragmentProfiloViewModel.socialAcquirenteRecuperati.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null  && !lista.isEmpty()) {
                System.out.println("in osserva social acquirente recuperati");
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                List<String> links = new ArrayList<>();
                List<String> nomi=new ArrayList<>();
                for (SocialAcquirenteModel social : lista) {
                    links.add(social.getLink());
                    nomi.add(social.getNome());
                }
                gridView.setVisibility(View.VISIBLE);
                updateSocialNames(nomi,links);
            }
        });
    }


    public void osservaSocialVenditoreRecuperati() {
        fragmentProfiloViewModel.socialVenditoreRecuperati.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null  && !lista.isEmpty()) {
                System.out.println("in osserva social venditore recuperati");
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                List<String> links = new ArrayList<>();
                List<String> nomi=new ArrayList<>();
                for (SocialVenditoreModel social : lista) {
                    links.add(social.getLink());
                    nomi.add(social.getNome());
                }
                gridView.setVisibility(View.VISIBLE);
                updateSocialNames(nomi,links);
            }
        });
    }


    public void osservaAcquirenteRecuperato() {
        fragmentProfiloViewModel.acquirenteRecuperato.observe(getViewLifecycleOwner(), (acquirente) -> {
            if (acquirente != null) {
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                String nome=acquirente.getNome();
                String cognome=acquirente.getCognome();
                String email=acquirente.getIndirizzo_email();
                String sitoweb=acquirente.getLink();
                String paese=acquirente.getAreageografica();
                String bio=acquirente.getBio();
                updateDatiUtente(nome,cognome,email,sitoweb,paese,bio);
            }
        });
    }


    public void osservaSocialVenditorePresenti() {
        fragmentProfiloViewModel.socialVenditorePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {//vede se sono presenti,li recupera,e poi deve aggiornare la gui
                System.out.println("in osserva social venditore presenti");
                osservaSocialVenditoreRecuperati();
                fragmentProfiloViewModel.recuperaSocialVenditore();
            }


        });
    }



    public void osservaVenditoreRecuperato() {
        fragmentProfiloViewModel.venditoreRecuperato.observe(getViewLifecycleOwner(), (venditore) -> {
            if (venditore != null) {
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                String nome=venditore.getNome();
                String cognome=venditore.getCognome();
                String email=venditore.getIndirizzo_email();
                String sitoweb=venditore.getLink();
                String paese=venditore.getAreageografica();
                String bio=venditore.getBio();
                updateDatiUtente(nome,cognome,email,sitoweb,paese,bio);
            }
        });
    }
public void osservaSocialAssenti(){
        fragmentProfiloViewModel.socialAssenti.observe(getViewLifecycleOwner(), (valore) ->{
            if(valore){
                Log.d("osservaSocialAssenti","entrato");
                gridView.setVisibility(View.GONE);
                text_view_nessun_social.setVisibility(View.VISIBLE);
            }
        });
}

    public void osservaVenditoreModelPresente(){
        fragmentProfiloViewModel.venditoreModelPresente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (fragmentProfiloViewModel.getVenditoreModelPresente()) {
                System.out.println("venditore esiste");
                Toast.makeText(getContext(), "Entrato come venditore in fragment profilo.", Toast.LENGTH_SHORT).show();
                //chiamate ai vari observer, tra cui quelli di cambiare schermata quindi pure per la gestione dei popup
                osservaSocialVenditoreRecuperati();
                osservaVenditoreRecuperato();//nel suo corpo ha un observer di un oggetto Acquirente Model che poi si usa per fare i vari get per un metodo che setta nella gui i valori
                osservaApriPopUpAggiungiSocial();
                osservaApriLeMieAste();
                //in teoria deve fare qui la chiamata per trovare i social dal backend
                fragmentProfiloViewModel.trovaSocialVenditoreViewModel();//questa chiamata trova i social dal backend

            } else{System.out.println("venditore NON ESISTE");}
        });
    }


//    public void osservaMessaggioErroreEmail() {
//        fragmentProfiloViewModel.messaggioSocialUtenteNonTrovato.observe(this, (messaggio) -> {
//            if (fragmentProfiloViewModel.isNuovoMessaggioErroreEmail()) {
//                messaggioErroreMail(messaggio);
//                //loginViewModel.cancellaMessaggioLogin();
//            }
//        });
//    }


    @Override
    public void onPopupDismissed() {
        System.out.println("In on popupDismissed");
        fragmentProfiloViewModel.checkTipoUtente();
       // schermataAstaIngleseViewModel.checkUltimaOfferta();
      //  schermataAstaIngleseViewModel.getAstaData();
    }



    @Override
    public void onPopupAggiungiSocialDismissed() {
        System.out.println("In on popupAggiungiSocialDismissed");
        fragmentProfiloViewModel.trovaSocialAcquirenteViewModel();
        fragmentProfiloViewModel.trovaSocialVenditoreViewModel();
    }

    @Override
    public void onPopupModificaSocialDismissed() {
        System.out.println("In on popupModificaSocialDismissed");
        fragmentProfiloViewModel.trovaSocialAcquirenteViewModel();
        fragmentProfiloViewModel.trovaSocialVenditoreViewModel();
    }
}

