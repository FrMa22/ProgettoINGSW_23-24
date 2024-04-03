package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.gui.venditore.VenditoreAstaRibasso;

public class LoginActivity extends GestoreComuniImplementazioni {
    private SwitchCompat switch_mostra_password;
    ProgressBar progress_bar_login;
    LinearLayout linear_layout_login;
    EditText editText_mail;
    EditText editText_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        switch_mostra_password = findViewById(R.id.switch_mostra_password);
        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);
        editText_mail = findViewById(R.id.editTextEmail);
        editText_password = findViewById(R.id.editTextPassword);
        linear_layout_login = findViewById(R.id.linear_layout_login);
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



        progress_bar_login = findViewById(R.id.progress_bar_login);

        LoginDAO logindao = new LoginDAO(this);

        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            Controller.redirectActivity(LoginActivity.this, Registrazione.class);

        });
        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
//                intent.putExtra("email", "eml");
//                intent.putExtra("tipoUtente", "Utente");
//                startActivity(intent);


                String mail = editText_mail.getText().toString().trim();  // Rimuovi eventuali spazi all'inizio e alla fine
                String password = editText_password.getText().toString().trim();

                if(mail.isEmpty()){
                    editText_mail.setError("Si prega di inserire un indirizzo email.");
                }else if(mail.length()>100){
                    editText_mail.setError("Attenzione! L'indirizzo email non può essere più lungo di 100 caratteri.");
                }else if(password.isEmpty()){
                    editText_password.setError("Si prega di inserire una password.");
                }else if(password.length()>100){
                    editText_password.setError("Attenzione! La password non può essere più lunga di 100 caratteri.");
                }else{
                    progress_bar_login.setVisibility(View.VISIBLE);
                    setAllClickable(linear_layout_login,false);
                    // Chiamata al metodo per cercare nel database
                    logindao.openConnection();
                    logindao.findUser(mail, password);
                    Log.d("result set" , "fatta finduser");
                }
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




}