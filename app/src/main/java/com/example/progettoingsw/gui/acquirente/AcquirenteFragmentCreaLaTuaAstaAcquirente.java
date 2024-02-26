package com.example.progettoingsw.gui.acquirente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.PopUpLogin;
import com.google.android.material.button.MaterialButton;

public class AcquirenteFragmentCreaLaTuaAstaAcquirente extends Fragment {

    String opzioneSelezionata;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    Spinner spinnerTipoAsta;
    MaterialButton bottoneProseguiCreaAstaAcquirente;
    ActivityResultLauncher<Intent> resultLauncher;

    public AcquirenteFragmentCreaLaTuaAstaAcquirente() {
        // Costruttore vuoto richiesto dal framework
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment = inflater.inflate(R.layout.acquirente_fragment_crea_la_tua_asta_acquirente, container, false);
        return view_fragment;
    }

    public void onViewCreated(@NonNull View view_fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view_fragment, savedInstanceState);
        immagineProdotto= view_fragment.findViewById(R.id.imageViewCreaAstaAcquirente);
        bottoneInserisciImmagine = view_fragment.findViewById(R.id.imageButtonInserisciImmagineCreaAstaAcquirente);

        registraRisultati();

        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine

        bottoneProseguiCreaAstaAcquirente = view_fragment.findViewById(R.id.bottoneProseguiCreaAstaAcquirente);
        bottoneProseguiCreaAstaAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(getActivity(), AcquirenteAstaInversa.class);
            }
        });

        spinnerTipoAsta = view_fragment.findViewById(R.id.spinnerTipologiaAstaAcquirente);
        ArrayAdapter<CharSequence> adapterSpinnerTipoAsta=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(requireContext(), R.array.elencoTipiAstaAcquirente, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterSpinnerTipoAsta.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTipoAsta.setAdapter(adapterSpinnerTipoAsta);

        spinnerTipoAsta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //fa qualcosa se si è selezionato qualcosa

                opzioneSelezionata=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //non fa nulla
            }
        });


    }



    private void prelevaImmagine(){
        Intent intent= new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri uriImmagine = result.getData().getData();
                            immagineProdotto.setImageURI(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    }
