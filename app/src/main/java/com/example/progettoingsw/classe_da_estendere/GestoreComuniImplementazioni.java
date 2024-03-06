package com.example.progettoingsw.classe_da_estendere;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.R;

public class GestoreComuniImplementazioni extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //serve per far nascondere la navigation bar di android (quella con back, home ecc):
        enableImmersiveMode();
        //serve per avere la barra delle notifiche del cell di un determinato colore:
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
    }

    protected void enableImmersiveMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    protected void setAllClickable(ViewGroup viewGroup, boolean enabled) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setAllClickable((ViewGroup) child, enabled);
            }
        }
    }
}
