package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

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
                Intent intent = new Intent(MainActivity.this, Registrazione.class);
                startActivity(intent);
            }
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottoneLogin.setText("Porco");
                Intent intent = new Intent(MainActivity.this, PopUpLogin.class);
                startActivity(intent);

            }
        });






    }
}