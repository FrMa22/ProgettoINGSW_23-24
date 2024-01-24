package com.example.progettoingsw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelezioneCategorie extends Fragment {
    Controller controller;
    NavigationView navigationView;
    List<String> selectedRadioButtonItems = new ArrayList<>();
    CheckBox button_arte;
    CheckBox button_elettrodomestici;
    CheckBox button_automobile;

    Button button_cerca;
    TextView elementi_selezionati;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton openDrawerButton;
    private BottomNavigationView bottomNavigationView;

    public FragmentSelezioneCategorie() {
        // Costruttore vuoto richiesto dal framework
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selezione_categorie, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new Controller();


        elementi_selezionati = view.findViewById(R.id.textview_lista_elementi);

        button_arte = view.findViewById(R.id.bottoneArte);
        button_arte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                       if (isChecked) {
                                                           selectedRadioButtonItems.add("Arte");
                                                       } else {
                                                           selectedRadioButtonItems.remove("Arte");
                                                       }
                                                       StringBuilder stringBuilder = new StringBuilder();
                                                       for (String item : selectedRadioButtonItems) {
                                                           stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                       }
                                                       elementi_selezionati.setText(stringBuilder.toString());
                                                   }
                                               }
        );

        button_elettrodomestici = view.findViewById(R.id.bottoneElettrodomestici);
        button_elettrodomestici.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                               @Override
                                                               public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                                   if (isChecked) {
                                                                       selectedRadioButtonItems.add("Elettrodomestici");
                                                                   } else {
                                                                       selectedRadioButtonItems.remove("Elettrodomestici");
                                                                   }
                                                                   StringBuilder stringBuilder = new StringBuilder();
                                                                   for (String item : selectedRadioButtonItems) {
                                                                       stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                                   }
                                                                   elementi_selezionati.setText(stringBuilder.toString());
                                                               }
                                                           }
        );
        button_automobile = view.findViewById(R.id.bottoneAutomobili);
        button_automobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                             if (isChecked) {
                                                                 selectedRadioButtonItems.add("Automobile");
                                                             } else {
                                                                 selectedRadioButtonItems.remove("Automobile");
                                                             }
                                                             StringBuilder stringBuilder = new StringBuilder();
                                                             for (String item : selectedRadioButtonItems) {
                                                                 stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                             }
                                                             elementi_selezionati.setText(stringBuilder.toString());
                                                         }
                                                     }
        );




        button_cerca = view.findViewById(R.id.buttonCerca);
        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Questo serve a passare informazioni ad un altra activity, mettendo quella in esecuzione
                Intent intent = new Intent(getActivity(), PopUpRicercaCategorie.class);
                intent.putStringArrayListExtra("listaStringhe", (ArrayList<String>) selectedRadioButtonItems);
                startActivity(intent);
            }
        });

    }

}

