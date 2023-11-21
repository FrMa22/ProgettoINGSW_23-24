package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import android.graphics.drawable.shapes.Shape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        MaterialButton bottoneLogin = (MaterialButton) findViewById(R.id.bottonelogin);


        registrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //apre schermata registrazione
                redirectActivity(MainActivity.this, PopUpLogin.class);
            }
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AstaRibasso.class);
                startActivity(intent);

            }
        });


    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}