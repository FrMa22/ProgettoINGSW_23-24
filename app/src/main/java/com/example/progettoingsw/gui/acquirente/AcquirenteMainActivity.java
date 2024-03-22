package com.example.progettoingsw.gui.acquirente;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gui.venditore.VenditorePopUpCreaAsta;
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
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        Log.d("AcquirenteMainActivity" , "La mail è " + email + ", il tipoUtente è: " + tipoUtente);

        // Impostazione del Fragment iniziale
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AcquirenteFragmentHome(email, tipoUtente))
                    .commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;

            // Imposta il fragment di default (potrebbe essere il fragment corrente)
            Fragment currentFragment =(Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            selectedFragment = (currentFragment != null) ? currentFragment : new AcquirenteFragmentHome(email, tipoUtente);

            if (item.getItemId() == R.id.action_home) {
                Log.d("BottomNav", "Selected Home");
                item.setIcon(R.drawable.ic_home);
                resetOtherIcons(bottomNavigationView, item);
                selectedFragment = new AcquirenteFragmentHome(email, tipoUtente);
            } else if (item.getItemId() == R.id.action_categories) {
                item.setIcon(R.drawable.ic_categorie);
                resetOtherIcons(bottomNavigationView, item);
                Log.d("BottomNav", "Selected Categories");
                selectedFragment = new AcquirenteFragmentSelezioneCategorie(email, tipoUtente);
            } else if (item.getItemId() == R.id.action_crea_asta) {
                Log.d("BottomNav", "Selected Crea Asta");
                item.setIcon(R.drawable.ic_plus);
                resetOtherIcons(bottomNavigationView, item);
                if(tipoUtente.equals("acquirente")){
                    resetOtherIcons(bottomNavigationView, item);
                    selectedFragment = new AcquirenteAstaInversa(email);
                }else{
                    VenditorePopUpCreaAsta popAsta  = new VenditorePopUpCreaAsta(AcquirenteMainActivity.this,email,tipoUtente);
                    popAsta.show();
                }
            } else if (item.getItemId() == R.id.action_search) {
                Log.d("BottomNav", "Selected Search");
                item.setIcon(R.drawable.ic_search);
                resetOtherIcons(bottomNavigationView, item);
                selectedFragment = new AcquirenteFragmentRicercaAsta(email ,tipoUtente);
            } else if (item.getItemId() == R.id.action_profile) {
                Log.d("BottomNav", "Selected Profile");
                item.setIcon(R.drawable.ic_profilo);
                resetOtherIcons(bottomNavigationView, item);
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
    // Metodo per resettare le icone degli elementi della BottomNavigationView
    private void resetOtherIcons(BottomNavigationView bottomNavigationView, MenuItem selectedItem) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item != selectedItem) {
                item.setIcon(getIconResource(item.getItemId()));
            }
        }
    }

    // Metodo per ottenere l'icona predefinita in base all'ID dell'elemento del menu
    private int getIconResource(int itemId) {
        if (itemId == R.id.action_home) {
            return R.drawable.ic_home_vuota;
        } else if (itemId == R.id.action_categories) {
            return R.drawable.ic_categorie_vuota;
        } else if (itemId == R.id.action_crea_asta) {
            return R.drawable.ic_plus_vuota;
        } else if (itemId == R.id.action_search) {
            return R.drawable.ic_search_vuota;
        } else if (itemId == R.id.action_profile) {
            return R.drawable.ic_profilo_vuoto;
        } else {
            return 0;
        }
    }


}
