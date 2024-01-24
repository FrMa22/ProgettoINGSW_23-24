package com.example.progettoingsw.controllers_package;

import android.app.Activity;
import android.content.Intent;

import com.example.progettoingsw.*;

public class Controller {


    // Altri membri e metodi del controller


    static FragmentProfilo fragmentProfilo;
    public Controller() {


    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }




}
