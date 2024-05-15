package com.example.progettoingsw.view;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
import com.example.progettoingsw.viewmodel.LoginViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class LoginActivity extends GestoreComuniImplementazioni {
    private SwitchCompat switch_mostra_password;
    ProgressBar progress_bar_login;
    EditText editText_mail;
    EditText editText_password;
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
                int cursorPosition = editText_password.getSelectionStart();

                if (isChecked) {
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                editText_password.setSelection(cursorPosition);
            }
        });


        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mail = editText_mail.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                loginViewModel.loginAcquirente(mail,password,token);
            }
        });

        registrazione.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Registrazione.class);
            startActivity(intent);

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

        checkConnessione();

        osservaTokenSalvato();

        requestNotificationPermissions();

    }
    public void checkConnessione() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(!(activeNetwork != null && activeNetwork.isConnected())){
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
private void requestNotificationPermissions() {
    if (ContextCompat.checkSelfPermission(this, "com.example.progettoingsw.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{"com.example.progettoingsw.permission.POST_NOTIFICATIONS"}, PERMISSION_REQUEST_CODE);
    }else{
        checkNotificationPermissions();
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("notifiche","notifiche  attive");
            } else {
                Toast.makeText(this, "è necessario abilitare i permessi per le notifiche", Toast.LENGTH_SHORT).show();
                Log.d("notifiche","notifiche non attive");
            }
        }
    }
    public void checkNotificationPermissions() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (!notificationManager.areNotificationsEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
            builder.setTitle("Notifiche disabilitate");
            builder.setMessage("Le notifiche sono disabilitate. Per il funzionamento ottimale dell'app sono necessarie, si prega di attivarle.");
            builder.setPositiveButton("Le attivo subito!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Puoi guidare l'utente alle impostazioni dell'app per abilitare le notifiche
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else{
            loginViewModel.checkSavedToken(getApplicationContext());
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
            }
        });
    }
    public void osservaMessaggioErrorePassword() {
        loginViewModel.messaggioErrorePassword.observe(this, (messaggio) -> {
            if (loginViewModel.isNuovoMessaggioErrorePassword()) {
                messaggioErrorePassword(messaggio);
            }
        });
    }
    public void salvaTokenInLocale(){
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
    public void osservaProseguiLogin(){
        loginViewModel.proseguiLogin.observe(this, (messaggio) -> {
            if (loginViewModel.isProseguiLogin("acquirente")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else if(loginViewModel.isProseguiLogin("venditore")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }else if(loginViewModel.isProseguiLogin("entrambi")){
                showAccountSelectionPopup();
            }else{
                System.out.println("errore in osserva Prosegui Login");
            }
        });
    }
    private void showAccountSelectionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialogTheme);

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
            if(loginViewModel.isTokenSalvato()){
                relative_layout_login_activity.setVisibility(View.VISIBLE);
                progress_bar_login.setVisibility(View.INVISIBLE);
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