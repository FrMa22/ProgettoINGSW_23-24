package com.example.progettoingsw.gui.venditore;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;

public class VenditorePopUpCreaAsta extends Dialog implements View.OnClickListener{

    private String email;
    private String tipoUtente;
    private AppCompatButton bottoneAstaInglese;
    private AppCompatButton bottoneAstaRibasso;

    public  VenditorePopUpCreaAsta(Context context, String email, String tipoUtente) {
        super(context);
        this.email = email;
        this.tipoUtente = tipoUtente;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.venditore_pop_up_crea_asta);



        bottoneAstaInglese = findViewById(R.id.bottoneAstaAllInglese);
        bottoneAstaInglese.setOnClickListener(this);
        bottoneAstaRibasso = findViewById(R.id.bottoneAstaAlRibasso);
        bottoneAstaRibasso.setOnClickListener(this);

    }
@Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAstaAllInglese) {
            Controller.redirectActivityEmail(getContext(), VenditoreAstaInglese.class,email);
        } else if(v.getId() == R.id.bottoneAstaAlRibasso){
            Controller.redirectActivityEmail(getContext(), VenditoreAstaRibasso.class,email);
        }
        dismiss();
    }


    public void dismissPopup() {
        dismiss();
    }
}
