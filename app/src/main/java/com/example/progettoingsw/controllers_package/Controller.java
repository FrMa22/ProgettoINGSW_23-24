package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.content.Intent;

import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentProfilo;

public class Controller {


    // Altri membri e metodi del controller


    static AcquirenteFragmentProfilo acquirenteFragmentProfilo;
    public Controller() {


    }


    public static void redirectActivity(Context context, Class<?> targetClass) {
        Intent intent = new Intent(context, targetClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }





}
