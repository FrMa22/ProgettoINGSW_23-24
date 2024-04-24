package com.example.progettoingsw.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.view.acquirente.MainActivity;
import com.example.progettoingsw.viewmodel.LoginViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends GestoreComuniImplementazioni {
    private SwitchCompat switch_mostra_password;
    ProgressBar progress_bar_login;
    EditText editText_mail;
    EditText editText_password;
    private CheckBox checkbox_ricordami;
    private LoginViewModel loginViewModel;
    private RelativeLayout relative_layout_login_activity;
    private String token;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private static final String TOKEN_KEY = "token";
    private SharedPreferences sharedPreferences;
    private FirebaseAnalytics firebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        relative_layout_login_activity = findViewById(R.id.relative_layout_login_activity);
        relative_layout_login_activity.setVisibility(View.INVISIBLE);
        progress_bar_login = findViewById(R.id.progress_bar_login);
        progress_bar_login.setVisibility(View.VISIBLE);
        switch_mostra_password = findViewById(R.id.switch_mostra_password);
        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);
        editText_mail = findViewById(R.id.editTextEmail);
        editText_password = findViewById(R.id.editTextPassword);

        editText_password.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                bottoneLogin.performClick();
                return true;
            }
            return false;
        });
        switch_mostra_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Memorizza la posizione corrente del cursore
                int cursorPosition = editText_password.getSelectionStart();

                // Se lo switch è selezionato, mostra la password
                if (isChecked) {
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Altrimenti, nascondi la password
                    editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Ripristina la posizione del cursore dopo il cambio di tipo di input
                editText_password.setSelection(cursorPosition);
            }
        });


        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //commentare per avere app con connesione al back end:
                //Attenzione, la view non puo avere istanza di Repository, questo è solo per far funzionare senza db e connessione
//                Repository repository = Repository.getInstance();
//                AcquirenteModel acquirenteModel = new AcquirenteModel("emailprova", "nomeprova", "cognomeprova","passwordprova", "biorprova", "linkprova", "italia");
//                Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(1L,"nomeasta" , "descrizioneasta" , null, 1f, "00:05:00" , "00:05:00", 1f, 1f, "aperta" , "d" );
//                ArrayList<Asta_allingleseModel> lista = new ArrayList<>();
//                lista.add(astaAllingleseModel);
//                repository.setListaAsteAllInglese(lista);
//                repository.setAcquirenteModel(acquirenteModel);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

                //commentare per testare app senza connessione al back end:
                String mail = editText_mail.getText().toString().trim();
                String password = editText_password.getText().toString().trim();

                requestNotificationPermissions();
                Log.d("premuto accedi", "mando token : " + token);
                loginViewModel.loginAcquirente(mail,password,token);


            }
        });


        // Inizializza Firebase
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Login");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        osservaMessaggioErroreEmail();
        osservaMessaggioErrorePassword();
        osservaProseguiLogin();
        osservaMessaggioUtenteNonTrovato();
        osservaConnessioneSpenta();
        loginViewModel.checkConnessione(this);
        //osservaTokenSalvato();
        //loginViewModel.checkSavedToken(getApplicationContext());
        osservaTokenSalvato();
        loginViewModel.checkSavedToken(getApplicationContext());

    }
private void requestNotificationPermissions() {
    if (ContextCompat.checkSelfPermission(this, "com.example.progettoingsw.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{"com.example.progettoingsw.permission.POST_NOTIFICATIONS"}, PERMISSION_REQUEST_CODE);
    }else{
        Log.d("Permesso", "il permesso c'è");
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // I permessi sono stati concessi
            } else {
                // I permessi non sono stati concessi
                // Puoi gestire questo caso in modo appropriato, ad esempio informando l'utente sui motivi per cui i permessi sono necessari
            }
        }
    }
    public void messaggioErroreMail(String messaggio){
        editText_mail.setError(messaggio);
    }
    public void messaggioErrorePassword(String messaggio){
        editText_password.setError(messaggio);
    }
    public void osservaMessaggioErroreEmail() {
        loginViewModel.messaggioErroreEmail.observe(this, (messaggio) -> {
            if (loginViewModel.isNuovoMessaggioErroreEmail()) {
                messaggioErroreMail(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }
    public void osservaMessaggioErrorePassword() {
        loginViewModel.messaggioErrorePassword.observe(this, (messaggio) -> {
            if (loginViewModel.isNuovoMessaggioErrorePassword()) {
                messaggioErrorePassword(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }
    public void salvaTokenInLocale(){
        Log.d("salvaTokenInLocale","salvo in locale : " + token);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
    public void osservaProseguiLogin(){
        loginViewModel.proseguiLogin.observe(this, (messaggio) -> {
            if (loginViewModel.isProseguiLogin("acquirente")) {
                Log.d("osservaProseguiLogin","Login effettuato come acquirente!");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Login effettuato come acquirente!", Toast.LENGTH_SHORT).show();

            }else if(loginViewModel.isProseguiLogin("venditore")){
                Log.d("osservaProseguiLogin","Login effettuato come venditore!");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Login effettuato come venditore!", Toast.LENGTH_SHORT).show();

            }else if(loginViewModel.isProseguiLogin("entrambi")){
                showAccountSelectionPopup();
            }else{
                System.out.println("errore in osserva Prosegui Login");
            }
        });
    }
    private void showAccountSelectionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Doppio account rilevato");
        builder.setMessage("Seleziona con quale account accedere:");
        builder.setPositiveButton("Acquirente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginViewModel.sceltoTipoAccount("acquirente");
            }
        });
        builder.setNegativeButton("Venditore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginViewModel.sceltoTipoAccount("venditore");
            }
        });
        builder.show();
    }
    public void osservaMessaggioUtenteNonTrovato() {
        loginViewModel.messaggioUtenteNonTrovato.observe(this, (messaggio) -> {
            if (loginViewModel.isMessaggioUtenteNonTrovato()) {
                if(loginViewModel.isNessunUtenteTrovatoConToken()){
                    relative_layout_login_activity.setVisibility(View.VISIBLE);
                    progress_bar_login.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void osservaTokenSalvato(){
        loginViewModel.tokenSalvato.observe(this, (tokenNuovo) -> {
            Log.d("osservaTokenSalvato","fuori if " + loginViewModel.isTokenSalvato());
            if(loginViewModel.isTokenSalvato()){
                relative_layout_login_activity.setVisibility(View.VISIBLE);
                progress_bar_login.setVisibility(View.INVISIBLE);
                Log.d("osservaTokenSalvato","nel if");
                this.token = tokenNuovo;
                salvaTokenInLocale();
            }
        });
    }

    public void osservaConnessioneSpenta(){
        loginViewModel.connessioneSpenta.observe(this, (valore) ->{
            if(valore){
                showPopUpConnessioneSpenta();
            }
        });
    }

    private void showPopUpConnessioneSpenta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connessione di rete necessaria");
        builder.setMessage("Questa applicazione richiede una connessione di rete attiva. Controlla la tua connessione e riprova.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Chiudi l'applicazione o gestisci l'evento in base alle tue esigenze
                finish();
            }
        });
        builder.setCancelable(false); // Impedisci all'utente di chiudere il dialogo premendo al di fuori di esso
        builder.show();
    }
}