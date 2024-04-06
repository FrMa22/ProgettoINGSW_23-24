    package com.example.progettoingsw.view.acquirente;

    import androidx.fragment.app.Fragment;

    import android.os.Bundle;
    import android.os.CountDownTimer;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;

    import com.example.progettoingsw.DAO.NotificheDAO;
    import com.example.progettoingsw.R;
    import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
    import com.example.progettoingsw.model.AcquirenteModel;
    import com.example.progettoingsw.model.VenditoreModel;
    import com.example.progettoingsw.repository.Repository;
    import com.example.progettoingsw.view.PopUpNotificaRicevuta;
    import com.example.progettoingsw.view.venditore.VenditorePopUpCreaAsta;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    public class AcquirenteMainActivity extends GestoreComuniImplementazioni {
        private AcquirenteModel acquirenteModel;
        private VenditoreModel venditoreModel;
        private BottomNavigationView bottomNavigationView;
        private String email;
        private String tipoUtente;
        private CountDownTimer countDownTimer;
        private int numeroNotifiche;
        private int numeroNotificheChecked;
        private boolean controlloIniziale = true;
        private Repository repository;
private Fragment selectedFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acquirente_activity_main);
            bottomNavigationView = findViewById(R.id.acquirente_nav_view);

            repository = Repository.getInstance();
            acquirenteModel = repository.getAcquirenteModel();
            venditoreModel = repository.getVenditoreModel();
            if (acquirenteModel != null) {
                Log.d("Main acitivity", "entrato come acquirente");
                Log.d("Main activiy", "Valori di acquirente: " +
                        "Indirizzo email: " + acquirenteModel.getIndirizzoEmail() +
                        ", Nome: " + acquirenteModel.getNome() +
                        ", Cognome: " + acquirenteModel.getCognome() +
                        ", Password: " + acquirenteModel.getPassword() +
                        ", Bio: " + acquirenteModel.getBio() +
                        ", Link: " + acquirenteModel.getLink() +
                        ", Area geografica: " + acquirenteModel.getAreaGeografica());
            } else if (venditoreModel != null) {
                Log.d("Main acitivity", "entrato come venditore");
                Log.d("Main activiy", "Valori di venditore: " +
                        "Indirizzo email: " + venditoreModel.getIndirizzoEmail() +
                        ", Nome: " + venditoreModel.getNome() +
                        ", Cognome: " + venditoreModel.getCognome() +
                        ", Password: " + venditoreModel.getPassword() +
                        ", Bio: " + venditoreModel.getBio() +
                        ", Link: " + venditoreModel.getLink() +
                        ", Area geografica: " + venditoreModel.getAreaGeografica());
            }

//            email = getIntent().getStringExtra("email").trim();
//            tipoUtente = getIntent().getStringExtra("tipoUtente");
//            NotificheDAO notificheDAO = new NotificheDAO(this,email,tipoUtente);
//            notificheDAO.openConnection();
//            notificheDAO.checkNotifiche();

//            countDownTimer = new CountDownTimer(5000, 1000) {
//                public void onTick(long millisUntilFinished) {
//                    // Stampa il numero di secondi rimanenti
//                    Log.d("Timer main activity", "Secondi mancanti: " + millisUntilFinished / 1000);
//                }
//                public void onFinish() {
//                    Log.d("Timer main activity", "Timer scaduto");
//                    notificheDAO.checkNotifiche();
//                    start();
//                }
//            };
//            // Avvia il timer
//            countDownTimer.start();


            // Impostazione del Fragment iniziale
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AcquirenteFragmentHome())
                        .commit();


            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {


                // Imposta il fragment di default (potrebbe essere il fragment corrente)
                Fragment currentFragment =(Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                selectedFragment = (currentFragment != null) ? currentFragment : new AcquirenteFragmentHome();

                if (item.getItemId() == R.id.action_home) {
                    Log.d("BottomNav", "Selected Home");
                    item.setIcon(R.drawable.ic_home);
                    resetOtherIcons(bottomNavigationView, item);
                    selectedFragment = new AcquirenteFragmentHome();
                } else if (item.getItemId() == R.id.action_categories) {
                    item.setIcon(R.drawable.ic_categorie);
                    resetOtherIcons(bottomNavigationView, item);
                    Log.d("BottomNav", "Selected Categories");
                    selectedFragment = new AcquirenteFragmentSelezioneCategorie();
                } else if (item.getItemId() == R.id.action_crea_asta) {
                    Log.d("BottomNav", "Selected Crea Asta");
                    item.setIcon(R.drawable.ic_plus);
                    resetOtherIcons(bottomNavigationView, item);
                    //if(tipoUtente.equals("acquirente")){
                    if(acquirenteModel!=null){
                        resetOtherIcons(bottomNavigationView, item);
                        selectedFragment = new AcquirenteFragmentAstaInversa();
                    }else{
                        VenditorePopUpCreaAsta popAsta  = new VenditorePopUpCreaAsta(AcquirenteMainActivity.this,email,tipoUtente);
                        popAsta.show();
                    }
                } else if (item.getItemId() == R.id.action_search) {
                    Log.d("BottomNav", "Selected Search");
                    item.setIcon(R.drawable.ic_search);
                    resetOtherIcons(bottomNavigationView, item);
                    selectedFragment = new AcquirenteFragmentRicercaAsta();
                } else if (item.getItemId() == R.id.action_profile) {
                    Log.d("BottomNav", "Selected Profile");
                    item.setIcon(R.drawable.ic_profilo);
                    resetOtherIcons(bottomNavigationView, item);
                    selectedFragment = new FragmentProfilo();
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
        public void navigateToFragmentAndSelectIcon(Fragment fragment) {
            // Controlla se il fragment corrente è già quello selezionato
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
                // Non fare nulla se il fragment corrente è già quello selezionato
                Log.d("BottomNav", "Fragment already selected");
                return;
            }

            // Imposta il nuovo fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            // Imposta l'icona corrispondente nella BottomNavigationView
            MenuItem menuItem = null;
            if (fragment instanceof AcquirenteFragmentHome) {
                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_home);
            } else if (fragment instanceof AcquirenteFragmentSelezioneCategorie) {
                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_categories);
            } else if (fragment instanceof AcquirenteFragmentAstaInversa) {
                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_crea_asta);
            } else if (fragment instanceof AcquirenteFragmentRicercaAsta) {
                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_search);
            } else if (fragment instanceof FragmentProfilo) {
                menuItem = bottomNavigationView.getMenu().findItem(R.id.action_profile);
            }

            // Se l'elemento di menu corrispondente è stato trovato, imposta l'elemento come selezionato
            if (menuItem != null) {
                menuItem.setChecked(true);
                resetOtherIcons(bottomNavigationView, menuItem);
            }
        }

    public void handleGetNumeroNotifiche(int numero){
            if(controlloIniziale){
                this.numeroNotifiche = numero;
                controlloIniziale = false;
                Log.d("handleGetNumeroNotifiche", "controllo inizale, numero = " + numero + ", numero notifiche= " + numeroNotifiche);
            }else{
                Log.d("handleGetNumeroNotifiche", "controllo, numero: "+ numero + ", num notifich: " + numeroNotifiche);
                if(numero>numeroNotifiche){
                    int notificheNuove = numero - numeroNotifiche;
                    PopUpNotificaRicevuta popUpNotificaRicevuta = new PopUpNotificaRicevuta(AcquirenteMainActivity.this,notificheNuove,email,tipoUtente);
                    popUpNotificaRicevuta.show();
                    numeroNotifiche = numero;
                    Log.d("Numero notifiche in handle" , " Notifiche: " + numeroNotifiche );
                }else{
                    this.numeroNotifiche = numero;
                    Log.d("Numero notifiche in handle" , " Notifiche resettate : " + numeroNotifiche );
                }
            }
    }
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

    }
