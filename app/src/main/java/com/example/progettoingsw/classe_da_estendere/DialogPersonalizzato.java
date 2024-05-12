package com.example.progettoingsw.classe_da_estendere;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogPersonalizzato extends Dialog {
    public DialogPersonalizzato(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //serve per far nascondere la navigation bar di android (quella con back, home ecc):
        enableImmersiveMode();

    }

    protected void enableImmersiveMode() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

}


