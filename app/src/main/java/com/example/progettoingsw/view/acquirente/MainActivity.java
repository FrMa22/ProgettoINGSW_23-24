    package com.example.progettoingsw.view.acquirente;

    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;

    import android.os.Bundle;
    import android.os.CountDownTimer;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;

    import com.example.progettoingsw.R;
    import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
    import com.example.progettoingsw.model.AcquirenteModel;
    import com.example.progettoingsw.model.VenditoreModel;
    import com.example.progettoingsw.repository.Repository;
    import com.example.progettoingsw.view.venditore.VenditorePopUpCreaAsta;
    import com.example.progettoingsw.viewmodel.MainActivityViewModel;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    public class MainActivity extends GestoreComuniImplementazioni {
        private AcquirenteModel acquirenteModel;
        private VenditoreModel venditoreModel;
        private BottomNavigationView bottomNavigationView;
        private String email;
        private String tipoUtente;
        private CountDownTimer countDownTimer;
        private int numeroNotifiche;
        private int numeroNotificheChecked;
        private boolean controlloIniziale = true;
        private MainActivityViewModel mainActivityViewModel;
        private Fragment selectedFragment;
        private MenuItem homeMenuItem;
        private MenuItem categoriesMenuItem;
        private MenuItem creaAstaMenuItem;
        private MenuItem searchMenuItem;
        private MenuItem profileMenuItem;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acquirente_activity_main);
            bottomNavigationView = findViewById(R.id.acquirente_nav_view);

            mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

            Menu menu = bottomNavigationView.getMenu();
            homeMenuItem = menu.findItem(R.id.action_home);
            categoriesMenuItem = menu.findItem(R.id.action_categories);
            creaAstaMenuItem = menu.findItem(R.id.action_crea_asta);
            searchMenuItem = menu.findItem(R.id.action_search);
            profileMenuItem = menu.findItem(R.id.action_profile);

            osservaSceltoHome();
            osservaSceltoCategorie();
            osservaSceltoCreaAstaAcquirente();
            osservaSceltoCreaAstaVenditore();
            osservaSceltoRicerca();
            osservaSceltoProfilo();


            // Impostazione del Fragment iniziale
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FragmentHome())
                        .commit();


            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

                Fragment currentFragment =(Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                selectedFragment = (currentFragment != null) ? currentFragment : new FragmentHome();

                int positionItem = 0;
                for(int i=0;i<menu.size();i++){
                    if(menu.getItem(i).getItemId() == item.getItemId()){
                        positionItem = i;
                        break;
                    }
                }
                Log.d("bottomNavigationView","id dell'item : " + (positionItem+1));
                mainActivityViewModel.gestisciFragment(positionItem + 1);

                // Controlla se il fragment corrente è già quello selezionato
                if (currentFragment != null && currentFragment.getClass().equals(selectedFragment.getClass())) {
                    // Non fare nulla se il fragment corrente è già quello selezionato
                    Log.d("BottomNav", "Fragment already selected");
                    return true;
                }





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
//        public void navigateToFragmentAndSelectIcon(Fragment fragment) {
//            // Controlla se il fragment corrente è già quello selezionato
//            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//            if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
//                // Non fare nulla se il fragment corrente è già quello selezionato
//                Log.d("BottomNav", "Fragment already selected");
//                return;
//            }
//
//            // Imposta il nuovo fragment
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//
//            // Imposta l'icona corrispondente nella BottomNavigationView
//            MenuItem menuItem = null;
//            if (fragment instanceof FragmentHome) {
//                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_home);
//            } else if (fragment instanceof FragmentSelezioneCategorie) {
//                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_categories);
//            } else if (fragment instanceof FragmentCreaAstaInversa) {
//                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_crea_asta);
//            } else if (fragment instanceof FragmentRicercaAsta) {
//                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_search);
//            } else if (fragment instanceof FragmentProfilo) {
//                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_profile);
//            }
//
//            // Se l'elemento di menu corrispondente è stato trovato, imposta l'elemento come selezionato
//            if (menuItem != null) {
//                menuItem.setChecked(true);
//                resetOtherIcons(bottomNavigationView, menuItem);
//            }
//        }

//    public void handleGetNumeroNotifiche(int numero){
//            if(controlloIniziale){
//                this.numeroNotifiche = numero;
//                controlloIniziale = false;
//                Log.d("handleGetNumeroNotifiche", "controllo inizale, numero = " + numero + ", numero notifiche= " + numeroNotifiche);
//            }else{
//                Log.d("handleGetNumeroNotifiche", "controllo, numero: "+ numero + ", num notifich: " + numeroNotifiche);
//                if(numero>numeroNotifiche){
//                    int notificheNuove = numero - numeroNotifiche;
//                    PopUpNotificaRicevuta popUpNotificaRicevuta = new PopUpNotificaRicevuta(MainActivity.this,notificheNuove,email,tipoUtente);
//                    popUpNotificaRicevuta.show();
//                    numeroNotifiche = numero;
//                    Log.d("Numero notifiche in handle" , " Notifiche: " + numeroNotifiche );
//                }else{
//                    this.numeroNotifiche = numero;
//                    Log.d("Numero notifiche in handle" , " Notifiche resettate : " + numeroNotifiche );
//                }
//            }
//    }
        // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
//        @Override
//        protected void onPause() {
//            super.onPause();
//            // Ferma il countDownTimer se è attivo
//            if (countDownTimer != null) {
//                countDownTimer.cancel();
//            }
//        }
//        @Override
//        protected void onStop() {
//            super.onStop();
//            // Ferma il countDownTimer se è attivo
//            if (countDownTimer != null) {
//                countDownTimer.cancel();
//            }
//        }
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            // Ferma il countDownTimer se è attivo
//            if (countDownTimer != null) {
//                countDownTimer.cancel();
//            }
//        }
//        @Override
//        public void onResume() {
//            super.onResume();
//            // Ferma il countDownTimer se è attivo
//            if (countDownTimer != null) {
//                countDownTimer.cancel();
//                countDownTimer.start();
//            }
//        }

        public void osservaSceltoHome(){
            mainActivityViewModel.sceltoHome.observe(this, (valore) ->{
                if(mainActivityViewModel.isSceltoHome()){
                    Log.d("osservaSceltoHome","entrato");
                    homeMenuItem.setIcon(R.drawable.ic_home);
                    resetOtherIcons(bottomNavigationView, homeMenuItem);
                    selectedFragment = new FragmentHome();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }
        public void osservaSceltoCategorie(){
            mainActivityViewModel.sceltoCategorie.observe(this, (valore) -> {
                if(mainActivityViewModel.isSceltoCategorie()){
                    Log.d("osservaSceltoCategorie","entrato");
                    categoriesMenuItem.setIcon(R.drawable.ic_categorie);
                    resetOtherIcons(bottomNavigationView, categoriesMenuItem);
                    selectedFragment = new FragmentSelezioneCategorie();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }
        public void osservaSceltoCreaAstaAcquirente(){
            mainActivityViewModel.sceltoCreaAstaAcquirente.observe(this, (valore) -> {
                if(mainActivityViewModel.isSceltoCreaAstaAcquirente()){
                    Log.d("osservaSceltoCreaAstaAcquirente","entrato");
                    creaAstaMenuItem.setIcon(R.drawable.ic_plus);
                    resetOtherIcons(bottomNavigationView, creaAstaMenuItem);
                    selectedFragment = new FragmentCreaAstaInversa();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }
        public void osservaSceltoCreaAstaVenditore(){
            mainActivityViewModel.sceltoCreaAstaVenditore.observe(this, (valore) -> {
                if(mainActivityViewModel.isSceltoCreaAstaVenditore()){
                    Log.d("osservaSceltoCreaAstaVenditore","entrato");
                    creaAstaMenuItem.setIcon(R.drawable.ic_plus);
                    resetOtherIcons(bottomNavigationView, creaAstaMenuItem);
                    VenditorePopUpCreaAsta popAsta  = new VenditorePopUpCreaAsta(MainActivity.this,MainActivity.this,mainActivityViewModel);
                    popAsta.show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }
        public void osservaSceltoRicerca(){
            mainActivityViewModel.sceltoRicerca.observe(this, (valore) -> {
                if(mainActivityViewModel.isSceltoRicerca()){
                    Log.d("osservaSceltoRicerca","entrato");
                    searchMenuItem.setIcon(R.drawable.ic_search);
                    resetOtherIcons(bottomNavigationView, searchMenuItem);
                    selectedFragment = new FragmentRicercaAsta();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }
        public void osservaSceltoProfilo(){
            mainActivityViewModel.sceltoProfilo.observe(this, (valore) -> {
                if(mainActivityViewModel.isSceltoProfilo()){
                    profileMenuItem.setIcon(R.drawable.ic_profilo);
                    resetOtherIcons(bottomNavigationView, profileMenuItem);
                    selectedFragment = new FragmentProfilo();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
            });
        }

    }
