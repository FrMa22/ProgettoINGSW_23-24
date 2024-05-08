package com.example.progettoingsw.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;

public class PopUpLogin extends DialogPersonalizzato implements View.OnClickListener {
    private TextView bottoneAcquirente;
    private TextView bottoneVenditore;
    private String email;

    public  PopUpLogin(Context context, String email) {
        super(context);
        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_login);

        /*DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        getWindow().setLayout((int) (width * 0.895), (int) (height * 0.37));
        getWindow().setGravity(Gravity.CENTER);*/

        bottoneAcquirente = findViewById(R.id.textViewAcquirente);
        bottoneAcquirente.setOnClickListener(this);
        bottoneVenditore = findViewById(R.id.textViewVenditore);
        bottoneVenditore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textViewAcquirente) {
            Toast.makeText(getContext(), "Accesso eseguito come acquirente: ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), MainActivity.class);//test del login
            intent.putExtra("email", email);
            intent.putExtra("tipoUtente", "acquirente");
            getContext().startActivity(intent);
        } else if(v.getId() == R.id.textViewVenditore){
            Toast.makeText(getContext(), "Accesso eseguito come venditore: ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), MainActivity.class);//test del login
            intent.putExtra("email", email);
            intent.putExtra("tipoUtente", "venditore");
            getContext().startActivity(intent);
        }
        dismiss();
    }

}
