package com.example.progettoingsw.gui.venditore;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VenditoreMainActivity extends GestoreComuniImplementazioni {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_activity_main);
        bottomNavigationView = findViewById(R.id.venditore_nav_view);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        // Impostazione del Fragment iniziale
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new VenditoreFragmentHome())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;

            // Imposta il fragment di default (potrebbe essere il fragment corrente)
            Fragment currentFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            selectedFragment = (currentFragment != null) ? currentFragment : new VenditoreFragmentHome();

            if (item.getItemId() == R.id.action_home) {
                Log.d("BottomNav", "Selected Home");
                selectedFragment = new VenditoreFragmentHome();
            } else if (item.getItemId() == R.id.action_categories) {
                Log.d("BottomNav", "Selected Categories");
                selectedFragment = new VenditoreFragmentSelezioneCategorie();
            } else if (item.getItemId() == R.id.action_crea_asta) {
                Log.d("BottomNav", "Selected Crea Asta");
                selectedFragment = new VenditoreFragmentCreaLaTuaAstaVenditore();
            } else if (item.getItemId() == R.id.action_search) {
                Log.d("BottomNav", "Selected Search");
                selectedFragment = new VenditoreFragmentRicercaAsta();
            } else if (item.getItemId() == R.id.action_profile) {
                Log.d("BottomNav", "Selected Profile");
                selectedFragment = new VenditoreFragmentProfilo();
            }

            // Controlla se il fragment corrente è già quello selezionato
            if (currentFragment != null && currentFragment.getClass().equals(selectedFragment.getClass())) {
                // Non fare nulla se il fragment corrente è già quello selezionato
                Log.d("BottomNav", "Fragment already selected");
                return true;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            return true;
        });
    }
}