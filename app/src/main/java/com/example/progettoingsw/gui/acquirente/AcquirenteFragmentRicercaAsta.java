package com.example.progettoingsw.gui.acquirente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.RicercaAsteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.gui.PopUpFiltroRicerca;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.gui.SchermataAstaInversa;
import com.example.progettoingsw.gui.SchermataAstaRibasso;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AcquirenteFragmentRicercaAsta extends Fragment {
    private EditText edittext_ricerca;
    private MaterialButton button_cerca_asta;
    private ImageButton button_filtro;
    private String parolaRicercata;
    private ArrayList<String> listaCategorieScelte;
    private String ordinamentoPrezzo;
    private AstaAdapter asteRecuperate;
    private String tipoUtente;
    private String email;
    private ProgressBar progress_bar_schermata_ricerca_asta;
    private RelativeLayout relative_layout_fragment_ricerca;
    private TextView text_view_nessuna_asta_ricercata;
    private RicercaAsteDAO ricercaAsteDAO;
    //da eliminare:
    private Button bottone_prova;

    public AcquirenteFragmentRicercaAsta(String email, String tipoUtente) {
        this.tipoUtente = tipoUtente;
        this.email = email;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_ricerca_asta, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         ricercaAsteDAO = new RicercaAsteDAO(this, tipoUtente);

        //l'ordinamento rispetto al prezzo di default è crescente
        ordinamentoPrezzo = "ASC";
        //la lista è vuota di default
        listaCategorieScelte = new ArrayList<>();

        text_view_nessuna_asta_ricercata = view.findViewById(R.id.text_view_nessuna_asta_ricercata);
        relative_layout_fragment_ricerca = view.findViewById(R.id.relative_layout_fragment_ricerca);
        progress_bar_schermata_ricerca_asta = view.findViewById(R.id.progress_bar_schermata_ricerca_asta);
        bottone_prova = view.findViewById(R.id.bottone_prova);
        bottone_prova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("prova bottone", "premuto ");
                AcquirenteMainActivity mainActivity = (AcquirenteMainActivity) getActivity();
                if (mainActivity != null) {
                    Log.d("prova bottone", "entrato ");
                    // Chiama il metodo selectFragment con il numero del fragment desiderato
                    mainActivity.selectFragment(2); // Cambia il numero del fragment secondo le tue esigenze
                }

            }
        });
        button_cerca_asta = view.findViewById(R.id.button_cerca_asta);
        asteRecuperate = new AstaAdapter(getContext(),null) ;
        RecyclerView recyclerViewAsteRecuperate = view.findViewById(R.id.recycler_view_aste_per_ricerca);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteRecuperate.setLayoutManager(gridLayoutManager);


        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), gridLayoutManager.getOrientation());
        recyclerViewAsteRecuperate.addItemDecoration(dividerItemDecoration);

        //bisogna aggiungere il setOnItemClickListener per ogni setAdapter di ogni recycler view specificando cosa fare per ogni tipo di asta clickato
        asteRecuperate.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteRecuperate.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = asteRecuperate.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(getContext(), SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(getContext(), SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(getContext(), SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
        recyclerViewAsteRecuperate.setAdapter(asteRecuperate);

        button_cerca_asta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parolaRicercata = edittext_ricerca.getText().toString();
                eseguiRicercaAste(parolaRicercata,listaCategorieScelte,ordinamentoPrezzo);
            }
        });

        edittext_ricerca = view.findViewById(R.id.edittext_ricerca);


        button_filtro = view.findViewById(R.id.button_filtro);
        button_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ricerca" , "premuto popup");
                PopUpFiltroRicerca popUpFiltroRicerca = new PopUpFiltroRicerca(getContext(),AcquirenteFragmentRicercaAsta.this,listaCategorieScelte, ordinamentoPrezzo);
                popUpFiltroRicerca.show();
            }
        });


    }
    public void handlePopUp(ArrayList<String> switchTexts, String ordinamentoPrezzo){
        this.listaCategorieScelte = switchTexts;
        this.ordinamentoPrezzo = ordinamentoPrezzo;
// Stampare l'ordinamento nel log
        Log.d("PopUpHandler", "Ordinamento Prezzo: " + ordinamentoPrezzo);

        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
        for (String categoria : switchTexts) {
            Log.d("PopUpHandler", "Categoria: " + categoria);
        }
    }

    public void handleAsteRecuperate(ArrayList<Object> asteItems){
        boolean asteVuote = asteItems == null || asteItems.isEmpty();
        if (!asteVuote)  {
            asteRecuperate.setAste(asteItems);
        } else {
            asteRecuperate.setAste(null);
            // Nessun nome asta trovato per l'email specificata
        }
        if(asteVuote){
            text_view_nessuna_asta_ricercata.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_asta_ricercata.setVisibility(View.INVISIBLE);
        }
            progress_bar_schermata_ricerca_asta.setVisibility(View.INVISIBLE);
            setAllClickable(relative_layout_fragment_ricerca,true);
            setNavigationView(true);
    }
    private void setNavigationView(Boolean valore) {
        AcquirenteMainActivity activity = (AcquirenteMainActivity) getActivity();
        if (activity != null) {
            activity.enableBottomNavigationView(valore);
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


    private void eseguiRicercaAste(String parolaRicercata, ArrayList<String> listaCategorieScelte, String ordinamentoPrezzo) {
        if(parolaRicercata.length()>100){
            edittext_ricerca.setError("Attenzione! La parola ricercata non può essere più lunga di 100 caratteri.");
        }else{
            progress_bar_schermata_ricerca_asta.setVisibility(View.VISIBLE);
            setAllClickable(relative_layout_fragment_ricerca,false);
            setNavigationView(false);
            ricercaAsteDAO.openConnection();
            ricercaAsteDAO.ricercaAste(parolaRicercata, listaCategorieScelte, ordinamentoPrezzo);
            ricercaAsteDAO.closeConnection();
        }
    }


}