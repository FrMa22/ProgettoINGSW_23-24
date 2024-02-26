package com.example.progettoingsw.gui.venditore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.PreferitiActivity;

public class VenditoreFragmentHome extends Fragment {

    public VenditoreFragmentHome() {
        // Costruttore vuoto richiesto dal framework
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venditore_fragment_home, container, false);
        // Il codice di AcquirenteFragmentHome è stato spostato qui
        // Assicurati di adattare le referenze alle view
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton preferitiButton = view.findViewById(R.id.openPreferiti);
        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(getActivity(), PreferitiActivity.class);
            }
        });

    }
}
