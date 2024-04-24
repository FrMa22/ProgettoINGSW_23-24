package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.RegistrazioneDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;

public class Registrazione extends GestoreComuniImplementazioni {
    private EditText edittext_nome;
    private EditText edittext_cognome;
    private EditText edittext_email;
    private EditText edittext_password;
    private EditText edittext_conferma_password;
    private Spinner spinner_tipo_utente;
    private SwitchCompat switch_mostra_password_registrazione;
    private RegistrazioneViewModel registrazioneViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        setContentView(R.layout.registrazione);
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        switch_mostra_password_registrazione = findViewById(R.id.switch_mostra_password_registrazione);
        switch_mostra_password_registrazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Se lo switch è selezionato, mostra la password
                if (isChecked) {
                    edittext_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Altrimenti, nascondi la password
                    edittext_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        edittext_nome = findViewById(R.id.editTextNomeRegistrazione);
        edittext_cognome = findViewById(R.id.editTextCongnomeRegistrazione);
        edittext_email = findViewById(R.id.editTextEmailRegistrazione);
        edittext_password = findViewById(R.id.editTextPasswordRegistrazione);
        edittext_conferma_password = findViewById(R.id.editTextConfermaPasswordRegistrazione);
        spinner_tipo_utente = findViewById(R.id.spinnerRegistrazione);

        osservaMessaggioErroreEmail();
        osservaMessaggioErrorePassword();
        osservaMessaggioErroreNome();
        osservaMessaggioErroreCognome();
        osservaMessaggioErroreConfermaPassword();
        osservaProseguiRegistrazione();

        osservaValoriPresentiAcquirente();
        osservaValoriPresentiVenditore();

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(LoginActivity.class));
                Controller.redirectActivity(Registrazione.this, LoginActivity.class);

            }
        });


        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Ottieni i valori dai campi di input
                String email = edittext_email.getText().toString().trim();
                String tipoUtente = spinner_tipo_utente.getSelectedItem().toString().trim();
                Log.d("onClickbottoneprosegui", "valore spinner : " + tipoUtente);
                String nome = edittext_nome.getText().toString().trim();
                String cognome = edittext_cognome.getText().toString().trim();
                String password = edittext_password.getText().toString().trim();
                String conferma_password = edittext_conferma_password.getText().toString().trim();


                System.out.println("premuto bottone");



                if(tipoUtente.equals("acquirente")){

                    registrazioneViewModel.registrazioneAcquirente(email,password,conferma_password,nome,cognome);
                } else if (tipoUtente.equals("venditore")) {

                    registrazioneViewModel.registrazioneVenditore(email,password,conferma_password,nome,cognome);
                }


              /*
                // Verifica se email e tipoUtente non sono vuoti
                if (!email.isEmpty() && !tipoUtente.isEmpty()) {
                    // Effettua ulteriori controlli sulla validità dell'email
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() <= 100) {
                        // Tutti i controlli sull'email sono passati, procedi con gli altri controlli
                        // Controlla la lunghezza massima del nome (50 caratteri)
                        String nome = edittext_nome.getText().toString().trim();
                        if (nome.length() > 50) {
                            edittext_nome.setError("Il nome non può superare i 50 caratteri");
                            return;
                        }

                        // Controlla la lunghezza massima del cognome (50 caratteri)
                        String cognome = edittext_cognome.getText().toString().trim();
                        if (cognome.length() > 50) {
                            edittext_cognome.setError("Il cognome non può superare i 50 caratteri");
                            return; // Esce dal metodo onClick se il cognome supera i 50 caratteri
                        }

                        // Controlla la lunghezza massima della password (100 caratteri)
                        String password = edittext_password.getText().toString().trim();
                        if (password.length() > 100) {
                            edittext_password.setError("La password non può superare i 100 caratteri");
                            return; // Esce dal metodo onClick se la password supera i 100 caratteri
                        }

                        // Controlla che la conferma della password corrisponda alla password
                        String conferma_password = edittext_conferma_password.getText().toString().trim();
                        if (!password.equals(conferma_password)) {
                            edittext_conferma_password.setError("Le password non corrispondono");
                            return; // Esce dal metodo onClick se le password non corrispondono
                        }

                        // Tutti i controlli passati, procedi con l'apertura dell'activity successiva
                        Log.d("bottone prosegui", "email: " + email + ", tipo: " + tipoUtente);
                        RegistrazioneDAO registrazioneDAO = new RegistrazioneDAO(Registrazione.this, email, tipoUtente);
                        registrazioneDAO.openConnection();
                        registrazioneDAO.checkEmail();
                    } else {
                        // Mostra un messaggio di errore se l'email non è valida
                        edittext_email.setError("Inserire un'email nel formato valido e di lunghezza massima 100 caratteri.");
                    }
                } else {
                    // Mostra un messaggio di errore se email o tipoUtente sono vuoti
                    if (email.isEmpty()) {
                        edittext_email.setError("Inserire un'email");
                    }
                    if (tipoUtente.isEmpty()) {
                        // Mostra un messaggio di errore se il tipoUtente non è stato selezionato
                        // (potresti voler mostrare un messaggio diverso a seconda del tuo layout)
                        // Spinner non ha la possibilità di impostare un messaggio di errore direttamente,
                        // quindi potresti dover gestire questo caso in modo diverso
                        Log.d("bottone prosegui", "Il tipo utente non è stato selezionato");
                    }
                }*/
            }
        });


    }

   /*public void handleCheckEmail(int result) {
        //caso in cui la mail è gia presente nel db
        Log.d("handleCheckEmail", "result : " + result);
        if (result == 1) {
            Toast.makeText(getApplicationContext(), "L'indirizzo email inserito è gia stato utilizzato. Si prega di utilizzarne un altro.", Toast.LENGTH_LONG).show();
        } else if(result == 0){
            String nome = edittext_nome.getText().toString().trim();
            String cognome = edittext_cognome.getText().toString().trim();
            String email = edittext_email.getText().toString().trim();
            String password = edittext_password.getText().toString().trim();
            String conferma_password = edittext_conferma_password.getText().toString().trim();
            String tipoUtente = spinner_tipo_utente.getSelectedItem().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || cognome.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Inserire tutti i valori", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(conferma_password)) {
                    Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("nome", nome);
                    intent.putExtra("cognome", cognome);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        }
    }*/

    @Override
    public void onResume(){
        super.onResume();
        registrazioneViewModel.checkValoriPresenti();
    }
    public void messaggioErroreMail(String messaggio){edittext_email.setError(messaggio);}

    public void messaggioErrorePassword(String messaggio){
        edittext_password.setError(messaggio);
    }

    public void messaggioErroreConfermaPassword(String messaggio){edittext_conferma_password.setError(messaggio);}
    public void messaggioErroreNome(String messaggio){edittext_nome.setError(messaggio);}
    public void messaggioErroreCognome(String messaggio){edittext_cognome.setError(messaggio);}



    public void osservaMessaggioErroreEmail() {
        registrazioneViewModel.messaggioErroreEmail.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreEmail()) {
                messaggioErroreMail(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }
    public void osservaMessaggioErrorePassword() {
        registrazioneViewModel.messaggioErrorePassword.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorePassword()) {
                messaggioErrorePassword(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreConfermaPassword() {
        registrazioneViewModel.messaggioErroreConfermaPassword.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreConfermaPassword()) {
                messaggioErroreConfermaPassword(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreNome() {
        registrazioneViewModel.messaggioErroreNome.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreNome()) {
                messaggioErroreNome(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreCognome() {
        registrazioneViewModel.messaggioErroreCognome.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreCognome()) {
                messaggioErroreCognome(messaggio);
            }
        });
    }

    public void osservaProseguiRegistrazione(){
        registrazioneViewModel.proseguiRegistrazione.observe(this, (messaggio) ->{
            if (registrazioneViewModel.isProseguiRegistrazione("nuovo acquirente")) {
                Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                startActivity(intent);
                Toast.makeText(this, "Passaggio ai campi facoltativi", Toast.LENGTH_SHORT).show();
            } else if (registrazioneViewModel.isProseguiRegistrazione("nuovo venditore")) {
                Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                startActivity(intent);
                Toast.makeText(this, "Passaggio ai campi facoltativi", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void osservaValoriPresentiAcquirente(){
        registrazioneViewModel.valoriPresentiAcquirente.observe(this, (utente)->{
            if(registrazioneViewModel.isValoriPresentiAcquirente()){
                edittext_nome.setText(utente.getNome());
                edittext_cognome.setText(utente.getCognome());
                edittext_email.setText(utente.getIndirizzo_email());
                edittext_password.setText(utente.getPassword());
                edittext_conferma_password.setText(utente.getPassword());
                spinner_tipo_utente.setSelection(0);
            }
        });
    }
    public void osservaValoriPresentiVenditore(){
        registrazioneViewModel.valoriPresentiVenditore.observe(this, (utente) ->{
            if(registrazioneViewModel.isValoriPresentiVenditore()){
                edittext_nome.setText(utente.getNome());
                edittext_cognome.setText(utente.getCognome());
                edittext_email.setText(utente.getIndirizzo_email());
                edittext_password.setText(utente.getPassword());
                edittext_conferma_password.setText(utente.getPassword());
                spinner_tipo_utente.setSelection(1);
            }
        });
    }
}