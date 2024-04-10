package com.example.progettoingsw.view.acquirente;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.FragmentProfiloDAO;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.PopUpAggiungiSocialProfilo;
import com.example.progettoingsw.view.PopUpControlloPassword;
import com.example.progettoingsw.view.PopUpModificaCampiProfilo;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.view.LeMieAste;
import com.example.progettoingsw.view.LoginActivity;
import com.example.progettoingsw.view.PopUpModificaSocial;
import com.example.progettoingsw.view.SchermataPartecipazioneAste;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;
import com.example.progettoingsw.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
public class FragmentProfilo extends Fragment{


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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Assegna la variabile view nel metodo onCreateView
        view = inflater.inflate(R.layout.fragment_profilo, container, false);
        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fragmentProfiloViewModel = new ViewModelProvider(this).get(FragmentProfiloViewModel.class);

        relative_layout_fragment_profilo = view.findViewById(R.id.relative_layout_fragment_profilo);





        text_view_nessun_social = view.findViewById(R.id.text_view_nessun_social);
        textview_nome = view.findViewById(R.id.textview_nome);
        textview_cognome = view.findViewById(R.id.textview_cognome);
        textview_email = view.findViewById(R.id.textview_email);
        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
        textview_paese = view.findViewById(R.id.textview_paese);
        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);

        //osserva cose







        //gestione gridview
        Repository repository = Repository.getInstance();
        AcquirenteModel acquirenteModel=repository.getAcquirenteModel();

         //Inizializza la GridView
        GridView gridView = view.findViewById(R.id.gridview_social_activity_profilo);

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
                email=acquirenteModel.getIndirizzoEmail();
                PopUpModificaSocial popUpModificaSocial = new PopUpModificaSocial(getContext(), FragmentProfilo.this, email, "acquirente", nome, link);
                popUpModificaSocial.show();
            }
        });




        //recupero dati+separato per tipo di utente
       // VenditoreModel venditoreModel=repository.getVenditoreModel();
        if(acquirenteModel!=null){
            List<SocialAcquirenteModel> socialAcquirenteModelList=repository.getSocialAcquirenteModelList();
            String email=acquirenteModel.getIndirizzoEmail();
            fragmentProfiloViewModel.fragmentProfiloAcquirente(email);
            textview_nome.setText(acquirenteModel.getNome());
            textview_cognome.setText(acquirenteModel.getCognome());
            textview_email.setText(acquirenteModel.getIndirizzoEmail());
            textview_sitoweb.setText(acquirenteModel.getLink());
            textview_paese.setText(acquirenteModel.getAreaGeografica());
            text_view_bio_profilo.setText(acquirenteModel.getBio());
            //gestione recupero dei social
//            List<String> nomiSocial=repository.getNomiSocialAcquirenteModelList(socialAcquirenteModelList);
//            List<String> linkSocial=repository.getLinksSocialAcquirenteModelList(socialAcquirenteModelList);
//            // Stampa i nomi social
//            System.out.println("Nomi Social:");
//            for (String nome : nomiSocial) {
//                System.out.println(nome);
//            }
//
//// Stampa i link social
//            System.out.println("Link Social:");
//            for (String link : linkSocial) {
//                System.out.println(link);
//            }

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<String> nomiSocial=repository.getNomiSocialAcquirenteModelList(socialAcquirenteModelList);
                    List<String> linkSocial=repository.getLinksSocialAcquirenteModelList(socialAcquirenteModelList);
                    // Stampa i nomi social
                    System.out.println("Nomi Social:");
                    for (String nome : nomiSocial) {
                        System.out.println(nome);
                    }

// Stampa i link social
                    System.out.println("Link Social:");
                    for (String link : linkSocial) {
                        System.out.println(link);
                    }
                    updateSocialNames(repository.getNomiSocialAcquirenteModelList(socialAcquirenteModelList),repository.getLinksSocialAcquirenteModelList(socialAcquirenteModelList));
                }
            }, 8000); // 5000 millisecondi = 5 secondi

            //se si lascia eseguire il metodo sotto non si recuperano dal db i social,lasciandolo commentato non esce a schermo l'elenco ma sono effettivamente presenti
           // updateSocialNames(repository.getNomiSocialAcquirenteModelList(socialAcquirenteModelList),repository.getLinksSocialAcquirenteModelList(socialAcquirenteModelList));

        }






//        if(venditoreModel!=null){
//            fragmentProfiloViewModel.fragmentProfiloVenditore(email);
//        }


        //icona del caricamento
        progressBarAcquirenteFragmentProfilo = view.findViewById(R.id.progressBarAcquirenteFragmentProfilo);
        //progressBarAcquirenteFragmentProfilo.setVisibility(View.VISIBLE);

        // Inizializza la GridView
//        GridView gridView = view.findViewById(R.id.gridview_social_activity_profilo);
//
//// Imposta un'altezza minima per la GridView
//        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
//
//// Chiama il metodo per impostare l'altezza in base agli elementi
//        setGridViewHeightBasedOnChildren(gridView);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CustomAdapter_gridview_profilo_social adapter = (CustomAdapter_gridview_profilo_social) gridView.getAdapter();
//                List<String> socialNames = adapter.getSocialNames();
//                List<String> socialLinks = adapter.getSocialLinks();
//
//                String nome = socialNames.get(position);
//                String link = socialLinks.get(position);
//                PopUpModificaSocial popUpModificaSocial = new PopUpModificaSocial(getContext(), FragmentProfilo.this, email, tipoUtente, nome, link);
//                popUpModificaSocial.show();
//            }
//        });



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
                Controller.redirectActivity(getContext(), LoginActivity.class);
            }
        });

//        button_le_mie_aste = view.findViewById(R.id.button_le_mie_aste);
//        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("le aste oooh");
//                Intent intent = new Intent(getContext(), LeMieAste.class);
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente",tipoUtente);
//                startActivity(intent);
//            }
//        });

        button_modifica = view.findViewById(R.id.button_modifica);
        button_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=acquirenteModel.getIndirizzoEmail();
                PopUpModificaCampiProfilo popUpModificaCampiProfilo = new PopUpModificaCampiProfilo(getContext(), FragmentProfilo.this, email, "acquirente");
                popUpModificaCampiProfilo.show();
            }
        });

        button_cambia_password_profilo = view.findViewById(R.id.button_cambia_password_profilo);
        button_cambia_password_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=acquirenteModel.getIndirizzoEmail();
                PopUpControlloPassword popUpControlloPassword = new PopUpControlloPassword(getContext(),FragmentProfilo.this ,email, "acquirente");
                popUpControlloPassword.show();
            }
        });

        button_aggiungi_social = view.findViewById(R.id.button_aggiungi_social);
        button_aggiungi_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=Repository.getInstance().getAcquirenteModel().getIndirizzoEmail();
                PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo = new PopUpAggiungiSocialProfilo(FragmentProfilo.this, email,tipoUtente);
                System.out.println("popupAggiungiSocial  nel fragment ha email:"+email);
                popUpAggiungiSocialProfilo.show();
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



    public void updateEditTexts(Acquirente acquirente) {
        if (acquirente != null) {
            // Esempio: aggiorna l'interfaccia utente con i dati dell'acquirente
            textview_nome.setText(acquirente.getNome());
            textview_cognome.setText(acquirente.getCognome());
            textview_email.setText(acquirente.getEmail());
            textview_sitoweb.setText(acquirente.getSitoWeb());
            textview_paese.setText(acquirente.getPaese());
            text_view_bio_profilo.setText(acquirente.getBio());
        } else {
            // L'acquirente non è stato trovato
        }
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
            gridView = view.findViewById(R.id.gridview_social_activity_profilo);
            if (socialNames != null && !socialNames.isEmpty()) {
                // Aggiorna l'interfaccia utente con i nomi dei social
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
            AcquirenteMainActivity activity = (AcquirenteMainActivity) getActivity();
            if (activity != null) {
                // Abilita la BottomNavigationView
                // Log.d("acquirente", "disabilito");
                activity.enableBottomNavigationView(valore);
            }

    }

//    public void osservaMessaggioErroreEmail() {
//        fragmentProfiloViewModel.messaggioSocialUtenteNonTrovato.observe(this, (messaggio) -> {
//            if (fragmentProfiloViewModel.isNuovoMessaggioErroreEmail()) {
//                messaggioErroreMail(messaggio);
//                //loginViewModel.cancellaMessaggioLogin();
//            }
//        });
//    }

}

