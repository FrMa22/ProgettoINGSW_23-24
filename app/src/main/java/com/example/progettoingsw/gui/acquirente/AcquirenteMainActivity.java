package com.example.progettoingsw.gui.acquirente;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class AcquirenteMainActivity extends GestoreComuniImplementazioni {

    private BottomNavigationView bottomNavigationView;
    private String email;
    private String tipoUtente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquirente_activity_main);
        bottomNavigationView = findViewById(R.id.acquirente_nav_view);

        email = getIntent().getStringExtra("email");
        tipoUtente = "acquirente";

        Toast.makeText(this, "La mail è "+email + ", il tipoUtente è: " + tipoUtente , Toast.LENGTH_SHORT).show();

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        decorView.setSystemUiVisibility(uiOptions);


        // Impostazione del Fragment iniziale
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AcquirenteFragmentHome(email))
                    .commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;

            // Imposta il fragment di default (potrebbe essere il fragment corrente)
            Fragment currentFragment =(Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            selectedFragment = (currentFragment != null) ? currentFragment : new AcquirenteFragmentHome(email);

            Log.d("Home" , "main e tipoutente è : " + tipoUtente);
            if (item.getItemId() == R.id.action_home) {
                Log.d("BottomNav", "Selected Home");
                selectedFragment = new AcquirenteFragmentHome(email);
            } else if (item.getItemId() == R.id.action_categories) {
                Log.d("BottomNav", "Selected Categories");
                selectedFragment = new AcquirenteFragmentSelezioneCategorie();
            } else if (item.getItemId() == R.id.action_crea_asta) {
                Log.d("BottomNav", "Selected Crea Asta");
                    selectedFragment = new AcquirenteAstaInversa(email);
            } else if (item.getItemId() == R.id.action_search) {
                Log.d("BottomNav", "Selected Search");
                selectedFragment = new AcquirenteFragmentRicercaAsta();
            } else if (item.getItemId() == R.id.action_profile) {
                Log.d("BottomNav", "Selected Profile");
                selectedFragment = new FragmentProfilo(email,tipoUtente);
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

    public void enableBottomNavigationView(boolean enabled) {
        Log.d("enable" , "preso comando : " + enabled);
        bottomNavigationView.getMenu().getItem(0).setEnabled(enabled);
        bottomNavigationView.getMenu().getItem(1).setEnabled(enabled);
        bottomNavigationView.getMenu().getItem(2).setEnabled(enabled);
        bottomNavigationView.getMenu().getItem(3).setEnabled(enabled);
        bottomNavigationView.getMenu().getItem(4).setEnabled(enabled);

    }

}
