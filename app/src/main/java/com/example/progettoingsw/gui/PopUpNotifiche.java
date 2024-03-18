package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.NotificheDAO;
import com.example.progettoingsw.DAO.PopUpNotificheDAO;
import com.example.progettoingsw.R;

public class PopUpNotifiche extends Dialog implements View.OnClickListener{

    private String titolo;
    private String commento;
    private int idNotifica;
    private String tipoUtente;
    private Button buttonCancella;
    private Button buttonChiudi;
    private TextView textViewTitolo;
    private TextView textViewCommento;
    private SchermataNotifiche schermataNotifiche;
    public PopUpNotifiche(Context context,String titolo,String commento,int id,String tipoutente,SchermataNotifiche schermataNotifiche)
    {
        super(context);
        this.titolo=titolo;
        this.commento=commento;
        this.idNotifica=id;
        this.tipoUtente=tipoutente;
        this.schermataNotifiche=schermataNotifiche;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_notifiche);

        buttonCancella=findViewById(R.id.button_cancella_notifica_popup_notifica);
        buttonChiudi=findViewById(R.id.button_chiudi_popup_notifica);
        buttonCancella.setOnClickListener(this);
        buttonChiudi.setOnClickListener(this);

        textViewTitolo=findViewById(R.id.textview_titolo_notifica_popup);
        textViewCommento=findViewById(R.id.textview_commento_notifica_popup);
        textViewTitolo.setText(titolo);
        textViewCommento.setText(commento);

    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_cancella_notifica_popup_notifica){
            PopUpNotificheDAO popUpNotificheDAO=new PopUpNotificheDAO(this);
            popUpNotificheDAO.openConnection();
            popUpNotificheDAO.eliminaNotifica(idNotifica,tipoUtente);
            popUpNotificheDAO.closeConnection();
            //cancella dal db la notifica
        }
//fare un dao che cancella se cliccato cancella e se chiude fa solo il dismiss
        //a prescindere chiude il popup se si preme uno dei bottoni
        schermataNotifiche.onResume();
        dismiss();
        }
}