package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.progettoingsw.R;

public class PopUpNotifiche extends Dialog implements View.OnClickListener{
    public PopUpNotifiche(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_notifiche);
    }
    @Override
    public void onClick(View v) {

        }
}