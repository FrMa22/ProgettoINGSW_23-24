package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.viewmodel.RicercaAstaViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FragmentRicercaAsta extends Fragment {
    private EditText edittext_ricerca;
    private MaterialButton button_cerca_asta;
    private ImageButton button_filtro;
    private String parolaRicercata;
    private AstaAdapter asteRecuperate;
    private ProgressBar progress_bar_schermata_ricerca_asta;
    private RelativeLayout relative_layout_fragment_ricerca;
    private TextView text_view_nessuna_asta_ricercata;
    private RicercaAstaViewModel ricercaAstaViewModel;

    public FragmentRicercaAsta() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ricerca_asta, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ricercaAstaViewModel = new ViewModelProvider(this).get(RicercaAstaViewModel.class);



        text_view_nessuna_asta_ricercata = view.findViewById(R.id.text_view_nessuna_asta_ricercata);
        relative_layout_fragment_ricerca = view.findViewById(R.id.relative_layout_fragment_ricerca);
        progress_bar_schermata_ricerca_asta = view.findViewById(R.id.progress_bar_schermata_ricerca_asta);

        button_cerca_asta = view.findViewById(R.id.button_cerca_asta);
        asteRecuperate = new AstaAdapter(getContext(),null) ;
        RecyclerView recyclerViewAsteRecuperate = view.findViewById(R.id.recycler_view_aste_per_ricerca);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteRecuperate.setLayoutManager(gridLayoutManager);


        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), gridLayoutManager.getOrientation());
        //recyclerViewAsteRecuperate.addItemDecoration(dividerItemDecoration);

        //bisogna aggiungere il setOnItemClickListener per ogni setAdapter di ogni recycler view specificando cosa fare per ogni tipo di asta clickato
        asteRecuperate.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteRecuperate.getChildAdapterPosition(v);
                Object asta = asteRecuperate.getItem(position);
                ricercaAstaViewModel.gestisciClickRecyclerView(asta);

            }
        });
        recyclerViewAsteRecuperate.setAdapter(asteRecuperate);

        button_cerca_asta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parolaRicercata = edittext_ricerca.getText().toString();
                asteRecuperate.clearItems();
                ricercaAstaViewModel.getAsteRicerca(edittext_ricerca.getText().toString());
            }
        });

        edittext_ricerca = view.findViewById(R.id.edittext_ricerca);
        edittext_ricerca.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                button_cerca_asta.performClick();
                return true;
            }
            return false;
        });

        button_filtro = view.findViewById(R.id.button_filtro);
        button_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ricercaAstaViewModel.apriFiltro();

            }
        });

        osservaIsAcquirente();
        osservaApriFiltro();

        ricercaAstaViewModel.checkTipoUtente();

    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Ricerca Asta");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "FragmentRicercaAsta");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }
    public void osservaApriFiltro(){
        ricercaAstaViewModel.apriFiltro.observe(getViewLifecycleOwner(), (valore) ->{
            if(valore){
                PopUpFiltroRicerca popUpFiltroRicerca = new PopUpFiltroRicerca(getContext(),FragmentRicercaAsta.this,ricercaAstaViewModel);
                popUpFiltroRicerca.show();
            }
        });
    }
    public void osservaIsAcquirente(){
        ricercaAstaViewModel.isAcquirente.observe(getViewLifecycleOwner(), (valore) -> {
            if(valore){
                Log.d("acquirente", "" );
                osservaEntraInSchermataAstaInglese();
                osservaListaAstaIngleseRicerca();
                osservaListaAstaIngleseRicercaConvertite();
                osservaListaAstaRibassoRicerca();
                osservaListaAstaRibassoRicercaConvertite();
                osservaEntraInSchermataAstaRibasso();
                //osservaListaAstaRibassoRicerca();
            }else{
                Log.d("venditore", "" );
                osservaEntraInSchermataAstaInversa();
                osservaListaAstaInversaRicerca();
                osservaListaAstaInversaRicercaConvertite();
                //osservaListaAstaInversaRicerca();
            }

        });
    }

public void osservaEntraInSchermataAstaInglese(){
    ricercaAstaViewModel.entraInSchermataAstaInglese.observe(getViewLifecycleOwner(), (messaggio) -> {
        if (ricercaAstaViewModel.getEntraInSchermataAstaInglese()){
            Intent intent = new Intent(getContext(), SchermataAstaInglese.class);
            startActivity(intent);
        }
    });
}
    public void osservaEntraInSchermataAstaRibasso(){
        ricercaAstaViewModel.entraInSchermataAstaRibasso.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (ricercaAstaViewModel.getEntraInSchermataAstaRibasso()){
                Intent intent = new Intent(getContext(), SchermataAstaRibasso.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaInversa(){
        ricercaAstaViewModel.entraInSchermataAstaInversa.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (ricercaAstaViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(getContext(), SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }
    public void osservaListaAstaIngleseRicerca(){
        ricercaAstaViewModel.listaAstaIngleseRicerca.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                ricercaAstaViewModel.convertiAsteInglese();
            }
        });
    }
    public void osservaListaAstaIngleseRicercaConvertite(){
        ricercaAstaViewModel.listaAstaIngleseRicercaConvertite.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                if(listaAste.isEmpty()){
                    text_view_nessuna_asta_ricercata.setVisibility(View.VISIBLE);
                }else{
                    text_view_nessuna_asta_ricercata.setVisibility(View.INVISIBLE);
                }
                //fai qualcosa
                Log.d("inglesi", "" + listaAste.size());
                asteRecuperate.setAste(listaAste);
            }
        });
    }
    public void osservaListaAstaRibassoRicerca(){
        ricercaAstaViewModel.listaAstaRibassoRicerca.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                ricercaAstaViewModel.convertiAsteRibasso();
            }
        });
    }
    public void osservaListaAstaRibassoRicercaConvertite(){
        ricercaAstaViewModel.listaAstaRibassoRicercaConvertite.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                if(listaAste.isEmpty()){
                    text_view_nessuna_asta_ricercata.setVisibility(View.VISIBLE);
                }else{
                    text_view_nessuna_asta_ricercata.setVisibility(View.INVISIBLE);
                }
                //fai qualcosa
                Log.d("Ribasso", "" + listaAste.size());
                asteRecuperate.setAste(listaAste);
            }
        });
    }
    public void osservaListaAstaInversaRicerca(){
        ricercaAstaViewModel.listaAstaInversaRicerca.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                ricercaAstaViewModel.convertiAsteInversa();
            }
        });
    }
    public void osservaListaAstaInversaRicercaConvertite(){
        ricercaAstaViewModel.listaAstaInversaRicercaConvertite.observe(getViewLifecycleOwner(), (listaAste) -> {
            if(listaAste!=null){
                if(listaAste.isEmpty()){
                    text_view_nessuna_asta_ricercata.setVisibility(View.VISIBLE);
                }else{
                    text_view_nessuna_asta_ricercata.setVisibility(View.INVISIBLE);
                }
                //fai qualcosa
                Log.d("Inversa", "" + listaAste.size());
                asteRecuperate.setAste(listaAste);
            }
        });
    }
}