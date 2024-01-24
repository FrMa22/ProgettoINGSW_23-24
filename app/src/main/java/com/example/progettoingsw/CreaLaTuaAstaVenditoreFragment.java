package com.example.progettoingsw;

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

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class CreaLaTuaAstaVenditoreFragment extends Fragment {
    public int selezione_asta=0;
    String opzioneSelezionata;
    ImageView immagineProdotto;
    ImageButton back;

    MaterialButton bottone_prosegui;
    Controller controller;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment = inflater.inflate(R.layout.fragment_crea_la_tua_asta_venditore, container, false);

        return view_fragment;
    }

    public void onViewCreated(@NonNull View view_fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view_fragment, savedInstanceState);

        controller = new Controller();
        bottone_prosegui=view_fragment.findViewById(R.id.bottoneProsegui);

        immagineProdotto=(ImageView) view_fragment.findViewById(R.id.imageViewCreaAstaVenditore);
        ImageButton bottoneInserisciImmagine=(ImageButton) view_fragment.findViewById(R.id.imageButtonInserisciImmagineCreaAstaVenditore);


        /*back = view.findViewById(R.id.bottoneTornaIndietroCreaLaTuaAstaVenditore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        registraRisultati();

        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine


        Spinner spinnerTipoAsta=(Spinner) view_fragment.findViewById(R.id.spinnerTipologiaAstaVenditore);
        ArrayAdapter<CharSequence> adapterSpinnerTipoAsta=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(getContext(), R.array.elencoTipiAstaVenditore, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterSpinnerTipoAsta.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerTipoAsta.setAdapter(adapterSpinnerTipoAsta);

        spinnerTipoAsta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //fa qualcosa se si è selezionato qualcosa
                opzioneSelezionata=parent.getItemAtPosition(position).toString();

                if(opzioneSelezionata.equals("Asta all\'inglese")){
                    selezione_asta=0;
//                    Toast.makeText(getApplicationContext(), "Asta inglese", Toast.LENGTH_SHORT).show();
                }if(opzioneSelezionata.equals("Asta al ribasso")){
                    selezione_asta=1;
//                    Toast.makeText(getApplicationContext(), "Asta al ribasso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //non fa nulla
            }
        });

        bottone_prosegui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selezione_asta==0){
                    Controller.redirectActivity(getActivity(), AstaInglese.class);
                }
                else if(selezione_asta==1){
                    Controller.redirectActivity(getActivity(), AstaRibasso.class);
                }
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
                            Toast.makeText(getContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
