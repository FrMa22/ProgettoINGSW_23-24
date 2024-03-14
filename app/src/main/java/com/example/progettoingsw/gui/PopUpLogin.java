package com.example.progettoingsw.gui;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;

public class PopUpLogin extends Dialog implements View.OnClickListener {
    private AppCompatButton bottoneAcquirente;
    private AppCompatButton bottoneVenditore;

    public  PopUpLogin(Context context) {
        super(context);
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

        bottoneAcquirente = findViewById(R.id.bottoneAcquirente);
        bottoneAcquirente.setOnClickListener(this);
        bottoneVenditore = findViewById(R.id.bottoneVenditore);
        bottoneVenditore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAcquirente) {
            Controller.redirectActivity(getContext(), AcquirenteMainActivity.class);
        } else if(v.getId() == R.id.bottoneVenditore){
            Toast.makeText(getContext(), "ciao", Toast.LENGTH_SHORT).show();
            Controller.redirectActivity(getContext(), AcquirenteMainActivity.class);
        }
        dismiss();
    }

}
