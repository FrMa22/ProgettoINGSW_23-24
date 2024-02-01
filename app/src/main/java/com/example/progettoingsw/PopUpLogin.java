package com.example.progettoingsw;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.controllers_package.Controller;

public class PopUpLogin extends Dialog implements View.OnClickListener {
    private AppCompatButton bottoneAcquirente;

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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAcquirente) {
            Controller.redirectActivity(getContext(), MainActivity.class);
        } else {
            // Aggiungi altri controlli if/else if se necessario
        }
        dismiss();
    }

}
