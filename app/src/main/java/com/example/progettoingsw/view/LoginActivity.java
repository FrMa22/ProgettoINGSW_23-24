package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.viewmodel.LoginViewModel;

import java.util.ArrayList;

public class LoginActivity extends GestoreComuniImplementazioni {
    private SwitchCompat switch_mostra_password;
    ProgressBar progress_bar_login;
    LinearLayout linear_layout_login;
    EditText editText_mail;
    EditText editText_password;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        switch_mostra_password = findViewById(R.id.switch_mostra_password);
        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);
        editText_mail = findViewById(R.id.editTextEmail);
        editText_password = findViewById(R.id.editTextPassword);
        linear_layout_login = findViewById(R.id.linear_layout_login);
        progress_bar_login = findViewById(R.id.progress_bar_login);
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
        osservaMessaggioErroreEmail();
        osservaMessaggioErrorePassword();
        osservaProseguiLogin();
        osservaMessaggioUtenteNonTrovato();
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

                loginViewModel.loginAcquirente(mail,password);
                loginViewModel.loginVenditore(mail,password);

            }
        });



    }
    public void handleLoginResult(String tipoUtente) {
        progress_bar_login.setVisibility(View.INVISIBLE);
        setAllClickable(linear_layout_login,true);
        if (tipoUtente != null) {
            if(tipoUtente.equals("doppioAccount")){
                PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this,editText_mail.getText().toString());
                popUpLogin.show();
            }else{
                Toast.makeText(this, "Accesso eseguito come: " + tipoUtente, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", editText_mail.getText().toString());
                intent.putExtra("tipoUtente", tipoUtente);
                startActivity(intent);
            }

        } else {
            // L'utente non è stato trovato
            // Mostra un messaggio di errore o esegui altre azioni necessarie
            Toast.makeText(this, "Non trovato", Toast.LENGTH_SHORT).show();

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
    public void osservaProseguiLogin(){
        loginViewModel.proseguiLogin.observe(this, (messaggio) -> {
            if (loginViewModel.isProseguiLogin("acquirente")) {
                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Login effettuato come acquirente!", Toast.LENGTH_SHORT).show();
            }else if(loginViewModel.isProseguiLogin("venditore")){
                Toast.makeText(this, "Login effettuato come venditore!", Toast.LENGTH_SHORT).show();
            }else if(loginViewModel.isProseguiLogin("entrambi")){
                Toast.makeText(this, "Login effettuato come entrambi->popup!", Toast.LENGTH_SHORT).show();
            }else{
                System.out.println("errore in osserva Prosegui Login");
            }
        });
    }
    public void osservaMessaggioUtenteNonTrovato() {
        loginViewModel.messaggioUtenteNonTrovato.observe(this, (messaggio) -> {
            if (loginViewModel.isMessaggioUtenteNonTrovato()) {
//                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
//                startActivity(intent);
                Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
            }
        });
    }




}