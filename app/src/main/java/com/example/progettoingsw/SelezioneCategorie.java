package com.example.progettoingsw;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SelezioneCategorie extends AppCompatActivity {
    Controller controller;
    NavigationView navigationView;
    List<String> selectedRadioButtonItems = new ArrayList<>();
    CheckBox button_arte;
    CheckBox button_elettrodomestici;
    CheckBox button_automobile;

    Button button_cerca;
    TextView elementi_selezionati;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton openDrawerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selezione_categorie);

        controller = new Controller();

        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerText = headerView.findViewById(R.id.textViewHeader);
        headerText.setText("Categorie");
        drawerLayout = findViewById(R.id.drawer_layout);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        openDrawerButton = findViewById(R.id.openDrawerButton);

        elementi_selezionati = findViewById(R.id.textview_lista_elementi);

        button_arte = findViewById(R.id.radiobutton_arte);
        button_arte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                       if (isChecked) {
                                                           selectedRadioButtonItems.add("Arte");
                                                       } else {
                                                           selectedRadioButtonItems.remove("Arte");
                                                       }
                                                       StringBuilder stringBuilder = new StringBuilder();
                                                       for (String item : selectedRadioButtonItems) {
                                                           stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                       }
                                                       elementi_selezionati.setText(stringBuilder.toString());
                                                   }
                                               }
        );

        button_elettrodomestici = findViewById(R.id.radiobutton_elettrodomestici);
        button_elettrodomestici.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                       if (isChecked) {
                                                           selectedRadioButtonItems.add("Elettrodomestici");
                                                       } else {
                                                           selectedRadioButtonItems.remove("Elettrodomestici");
                                                       }
                                                       StringBuilder stringBuilder = new StringBuilder();
                                                       for (String item : selectedRadioButtonItems) {
                                                           stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                       }
                                                       elementi_selezionati.setText(stringBuilder.toString());
                                                   }
                                               }
        );
        button_automobile = findViewById(R.id.radiobutton_automobili);
        button_automobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                       if (isChecked) {
                                                           selectedRadioButtonItems.add("Automobile");
                                                       } else {
                                                           selectedRadioButtonItems.remove("Automobile");
                                                       }
                                                       StringBuilder stringBuilder = new StringBuilder();
                                                       for (String item : selectedRadioButtonItems) {
                                                           stringBuilder.append(item).append("\n"); // Aggiungi ogni elemento della lista con un newline "\n"
                                                       }
                                                       elementi_selezionati.setText(stringBuilder.toString());
                                                   }
                                               }
        );


        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    controller.redirectActivity(SelezioneCategorie.this, HomeUtente.class);
                } else if (id == R.id.nav_cliccacategorie) {
                    if (drawerLayout.isOpen()) {
                        drawerLayout.closeDrawer(navigationView);
                    }
                } else if (id == R.id.nav_profilo) {
                    controller.redirectActivity(SelezioneCategorie.this, ProfiloActivity.class);
                } else if (id == R.id.nav_about_us) {
                    controller.redirectActivity(SelezioneCategorie.this, AboutUs.class);
                } else if (id == R.id.nav_esci) {
                    controller.redirectActivity(SelezioneCategorie.this, MainActivity.class);
                }

                return true;
            }
        });

        button_cerca = findViewById(R.id.buttonCerca);
        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Questo serve a passare informazioni ad un altra activity, mettendo quella in esecuzione
                Intent intent = new Intent(SelezioneCategorie.this, PopUpRicercaCategorie.class);
                intent.putStringArrayListExtra("listaStringhe", (ArrayList<String>) selectedRadioButtonItems);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}