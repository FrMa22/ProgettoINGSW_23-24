package com.example.progettoingsw.controllers_package;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.progettoingsw.*;

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





}
