package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.PopUpNotificheViewModel;

public class PopUpNotifiche extends DialogPersonalizzato implements View.OnClickListener{
    private PopUpNotifiche.PopupDismissListener popupDismissListener;
    private Button buttonCancella;
    private Button buttonChiudi;
    private TextView textViewTitolo;
    private TextView textViewCommento;
    private FragmentActivity fragmentActivity;
    private PopUpNotificheViewModel popUpNotificheViewModel;
    public PopUpNotifiche(Context context, FragmentActivity activity, PopUpNotifiche.PopupDismissListener dismissListener) {
        super(context);
        this.fragmentActivity=activity;
        this.popupDismissListener = dismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pop_up_notifiche);

            textViewTitolo = findViewById(R.id.textview_titolo_notifica_popup);
            textViewCommento = findViewById(R.id.textview_commento_notifica_popup);
            buttonCancella = findViewById(R.id.button_cancella_notifica_popup_notifica);
            buttonChiudi = findViewById(R.id.button_chiudi_popup_notifica);

            popUpNotificheViewModel = new ViewModelProvider(fragmentActivity).get(PopUpNotificheViewModel.class);
            osservaErroreEliminazione();
            osservaNotificaEliminata();
            osservaImpostaDatiNotificaAcquirente();
            osservaImpostaDatiNotificaVenditore();
            popUpNotificheViewModel.getNotificaData();


            buttonCancella.setOnClickListener(this);
            buttonChiudi.setOnClickListener(this);




    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_cancella_notifica_popup_notifica){

            popUpNotificheViewModel.eliminaNotifica();
        }else
            if(v.getId() == R.id.button_chiudi_popup_notifica){
                dismiss();
            }
        }
    public void dismissPopup() {
        dismiss();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupDismissed();
        }
    }
    public interface PopupDismissListener {
        void onPopupDismissed();
    }
    public void osservaErroreEliminazione(){
        popUpNotificheViewModel.erroreEliminazione.observe(fragmentActivity, (messaggio) -> {
            if(popUpNotificheViewModel.isErroreEliminazione()){
                Toast.makeText(fragmentActivity, messaggio, Toast.LENGTH_SHORT).show();
            }
        });
    }

        public void osservaImpostaDatiNotificaAcquirente(){
            popUpNotificheViewModel.impostaDatiNotificaAcquirente.observe(fragmentActivity, (notifica) -> {
                if(notifica!=null){
                    textViewTitolo.setText(notifica.getTitolo());
                    textViewCommento.setText(notifica.getCommento());
                }
            });
        }

        public void osservaImpostaDatiNotificaVenditore(){
            popUpNotificheViewModel.impostaDatiNotificaVenditore.observe(fragmentActivity, (notifica) -> {
                if(notifica!=null){
                    textViewTitolo.setText(notifica.getTitolo());
                    textViewCommento.setText(notifica.getCommento());
                }
            });
        }
        public void osservaNotificaEliminata(){
        popUpNotificheViewModel.notificaEliminata.observe(fragmentActivity, (result) -> {
            if(result){
                dismissPopup();
            }
            });
        }
}