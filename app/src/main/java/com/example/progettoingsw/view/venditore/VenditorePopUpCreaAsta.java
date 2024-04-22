package com.example.progettoingsw.view.venditore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.view.SchermataAstaInglese;
import com.example.progettoingsw.view.acquirente.MainActivity;
import com.example.progettoingsw.viewmodel.MainActivityViewModel;

public class VenditorePopUpCreaAsta extends Dialog implements View.OnClickListener{

    private String email;
    private AppCompatButton bottoneAstaInglese;
    private AppCompatButton bottoneAstaRibasso;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivity mainActivity;
    public  VenditorePopUpCreaAsta(Context context, MainActivity mainActivity, MainActivityViewModel mainActivityViewModel) {
        super(context);
        this.mainActivity = mainActivity;
        this.mainActivityViewModel = mainActivityViewModel;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.venditore_pop_up_crea_asta);

        osservaApriSchermataAstaInglese();
        osservaApriSchermataAstaRibasso();


        bottoneAstaInglese = findViewById(R.id.bottoneAstaAllInglese);
        bottoneAstaInglese.setOnClickListener(this);
        bottoneAstaRibasso = findViewById(R.id.bottoneAstaAlRibasso);
        bottoneAstaRibasso.setOnClickListener(this);

    }
@Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAstaAllInglese) {
            mainActivityViewModel.setApriSchermataAstaInglese(true);
        } else if(v.getId() == R.id.bottoneAstaAlRibasso){
            mainActivityViewModel.setApriSchermataAstaRibasso(true);
        }
        dismiss();
    }

    public void osservaApriSchermataAstaInglese(){
        mainActivityViewModel.apriSchermataAstaInglese.observe(mainActivity, (valore)->{
            if(valore){
                Intent intent = new Intent(getContext(), VenditoreAstaInglese.class);
                getContext().startActivity(intent);
            }
        } );
    }
    public void osservaApriSchermataAstaRibasso(){
        mainActivityViewModel.apriSchermataAstaRibasso.observe(mainActivity, (valore)->{
            if(valore){
                Intent intent = new Intent(getContext(), VenditoreAstaRibasso.class);
                getContext().startActivity(intent);
            }
        } );
    }

    public void dismissPopup() {
        dismiss();
    }
}
