package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.content.Intent;

import com.example.progettoingsw.view.acquirente.FragmentProfilo;

public class Controller {


    // Altri membri e metodi del controller


    static FragmentProfilo fragmentProfilo;
    public Controller() {


    }


    public static void redirectActivity(Context context, Class<?> targetClass) {
        Intent intent = new Intent(context, targetClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void redirectActivityEmail(Context context, Class<?> targetClass, String email) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtra("email", email);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void redirectActivityEmailTipoUtente(Context context, Class<?> targetClass, String email, String tipoUtente) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtra("email", email);
        intent.putExtra("tipoUtente", tipoUtente);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }






}
