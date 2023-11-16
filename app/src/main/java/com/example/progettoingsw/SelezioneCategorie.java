package com.example.progettoingsw;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class SelezioneCategorie extends AppCompatActivity {
    NavigationView navigationView;
    ImageButton button_arte;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton openDrawerButton;
    private boolean isButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selezione_categorie);

        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerText = headerView.findViewById(R.id.textViewHeader);
        headerText.setText("Categorie");
        drawerLayout = findViewById(R.id.drawer_layout);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        openDrawerButton = findViewById(R.id.openDrawerButton);

        button_arte = findViewById(R.id.button_selection_arte);
        button_arte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_arte.setImageResource(R.drawable.ic_categorie);
                isButtonClicked = !isButtonClicked;

                if (isButtonClicked) {
                    button_arte.setImageResource(R.drawable.ic_radio_button_clicked);
                } else {
                    button_arte.setImageResource(R.drawable.ic_radio_button_unclicked);
                }
            }
        });


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
                    MainActivity.redirectActivity(SelezioneCategorie.this, HomeUtente.class);
                } else if (id == R.id.nav_cliccacategorie) {
                    if (drawerLayout.isOpen()) {
                        drawerLayout.closeDrawer(navigationView);
                    }
                }else if(id==R.id.nav_profilo){
                    MainActivity.redirectActivity(SelezioneCategorie.this, ProfiloActivity.class);
                }else if(id==R.id.nav_about_us){
                    MainActivity.redirectActivity(SelezioneCategorie.this, AboutUs.class);
                }else if(id==R.id.nav_esci){
                    MainActivity.redirectActivity(SelezioneCategorie.this, MainActivity.class);
                }

                return true;
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