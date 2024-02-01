package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentProfilo extends Fragment {
    private Controller controller;
    MaterialButton button_log_out;
    boolean modificaCampi = false;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private GridView gridView;
    private CustomAdapter_gridview_profilo_campi adapterSocial;

    private ImageButton bottone_modifica_nome;
    private TextView textview_campo_nome;
    private TextView textview_nome;
    private String testo_textview_campo_nome;
    private String testo_textview_nome;

    private ImageButton bottone_modifica_cognome;
    private TextView textview_campo_cognome;
    private TextView textview_cognome;
    private String testo_textview_campo_cognome;
    private String testo_textview_cognome;

    private ImageButton bottone_modifica_email;
    private TextView textview_campo_email;
    private TextView textview_email;
    private String testo_textview_campo_email;
    private String testo_textview_email;

    private ImageButton bottone_modifica_sitoweb;
    private TextView textview_campo_sitoweb;
    private TextView textview_sitoweb;
    private String testo_textview_campo_sitoweb;
    private String testo_textview_sitoweb;

    private ImageButton bottone_modifica_paese;
    private TextView textview_campo_paese;
    private TextView textview_paese;
    private String testo_textview_campo_paese;
    private String testo_textview_paese;


    public FragmentProfilo() {
        // Costruttore vuoto richiesto dal framework
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilo, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new Controller();

        button_log_out = view.findViewById(R.id.button_log_out_profilo);
        button_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(getContext(), LoginActivity.class);
            }
        });


        /*gridView = view.findViewById(R.id.gridview_social_activity_profilo);
        adapterSocial = new CustomAdapter_gridview_profilo_campi(getContext());
        gridView.setAdapter(adapterSocial);
        String[] socialArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        String[] linksArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        ArrayList<String> socialArrayList = new ArrayList<>(Arrays.asList(socialArray));
        ArrayList<String> linksArrayList = new ArrayList<>(Arrays.asList(linksArray));
        adapterSocial.setData(socialArrayList, linksArrayList);

        textview_campo_nome = view.findViewById(R.id.textview_campo_nome);
        testo_textview_campo_nome = textview_campo_nome.getText().toString();
        textview_nome = view.findViewById(R.id.textview_nome);
        testo_textview_nome = textview_nome.getText().toString();*/





    }



}