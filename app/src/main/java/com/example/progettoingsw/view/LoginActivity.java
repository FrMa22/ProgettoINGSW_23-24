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
import android.widget.LinearLayout;
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
import com.example.progettoingsw.view.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.viewmodel.LoginViewModel;
import com.google.firebase.FirebaseApp;
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
                // Qui esegui l'azione corrispondente a un clic sul pulsante "Accedi"
                bottoneLogin.performClick();
                return true; // Indica che l'evento è stato gestito
            }
            return false; // Lascia che l'evento venga gestito normalmente
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


//        LoginDAO logindao = new LoginDAO(this);
//
//        registrazione.setOnClickListener(v -> {
//            //apre schermata registrazione
//            Controller.redirectActivity(LoginActivity.this, Registrazione.class);
//
//        });

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
//                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
//                startActivity(intent);

                //commentare per testare app senza connessione al back end:
                String mail = editText_mail.getText().toString().trim();  // Rimuovi eventuali spazi all'inizio e alla fine
                String password = editText_password.getText().toString().trim();
                System.out.println("premuto bottone");

                requestNotificationPermissions();
                Log.d("premuto accedi", "mando token : " + token);
                loginViewModel.loginAcquirente(mail,password,token);

                //loginViewModel.loginVenditore(mail,password);

            }
        });


        // Inizializza Firebase
        FirebaseApp.initializeApp(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        osservaMessaggioErroreEmail();
        osservaMessaggioErrorePassword();
        osservaProseguiLogin();
        osservaMessaggioUtenteNonTrovato();
        //osservaTokenSalvato();
        //loginViewModel.checkSavedToken(getApplicationContext());
        checkSavedToken();

    }
//    public void handleLoginResult(String tipoUtente) {
//        progress_bar_login.setVisibility(View.INVISIBLE);
//        setAllClickable(linear_layout_login,true);
//        if (tipoUtente != null) {
//            if(tipoUtente.equals("doppioAccount")){
//                PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this,editText_mail.getText().toString());
//                popUpLogin.show();
//            }else{
//                Toast.makeText(this, "Accesso eseguito come: " + tipoUtente, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);//test del login
//                intent.putExtra("email", editText_mail.getText().toString());
//                intent.putExtra("tipoUtente", tipoUtente);
//                startActivity(intent);
//            }
//
//        } else {
//            // L'utente non è stato trovato
//            // Mostra un messaggio di errore o esegui altre azioni necessarie
//            Toast.makeText(this, "Non trovato", Toast.LENGTH_SHORT).show();
//
//        }
//    }
public void checkSavedToken(){
        Log.d("checkSavedToken","controllo il token");
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN_KEY, null);
        if(token != null){
            Log.d("checkSavedToken","token recuperato : " + token);
            //setTokenSalvato(token);
            loginViewModel.loginAcquirenteConToken(token);
        }else{
            relative_layout_login_activity.setVisibility(View.VISIBLE);
            progress_bar_login.setVisibility(View.INVISIBLE);
            Log.d("checkSavedToken","token non trovato : ");
            Log.d("osservaTokenSalvato","entrato in token non trovato, token : "+ token);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            token = task.getResult();
                            Log.d("token creato", "token: " + token);
                            Log.d("Firebase", "Connessione a Firebase avvenuta con successo. Token: " + token);
                            // Memorizza il token nelle SharedPreferences


                        } else {
                            Log.e("Firebase", "Errore durante la connessione a Firebase", task.getException());
                        }
                    });

        }

        //setTokenSalvato("");
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
                // Puoi eseguire azioni aggiuntive qui se necessario
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
        Log.d("salvaTokenInLocale","salvo in locale");
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
    public void osservaProseguiLogin(){
        loginViewModel.proseguiLogin.observe(this, (messaggio) -> {
            if (loginViewModel.isProseguiLogin("acquirente")) {
                salvaTokenInLocale();
                Log.d("osservaProseguiLogin","Login effettuato come acquirente!");
                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Login effettuato come acquirente!", Toast.LENGTH_SHORT).show();

            }else if(loginViewModel.isProseguiLogin("venditore")){
                salvaTokenInLocale();
                Log.d("osservaProseguiLogin","Login effettuato come venditore!");
                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
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
//                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
//                startActivity(intent);
                if(loginViewModel.isNessunUtenteTrovatoConToken()){
                    relative_layout_login_activity.setVisibility(View.VISIBLE);
                    progress_bar_login.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void osservaTokenSalvato(){
//        loginViewModel.tokenSalvato.observe(this, (token) -> {
//            Log.d("osservaTokenSalvato","fuori if " + loginViewModel.isTokenSalvato());
//            if(loginViewModel.isTokenSalvato()){
//                Log.d("osservaTokenSalvato","nel if");
//                loginViewModel.loginAcquirenteConToken(token);
//            }
//        });
//    }


}