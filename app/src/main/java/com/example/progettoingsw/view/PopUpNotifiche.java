package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.progettoingsw.DAO.PopUpNotificheDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.PopUpNotificheViewModel;
import com.example.progettoingsw.viewmodel.SchermataNotificheViewModel;

public class PopUpNotifiche extends DialogPersonalizzato implements View.OnClickListener{
    private PopUpNotifiche.PopupDismissListener popupDismissListener;
    private String titolo;
    private String commento;
    private int idNotifica;
    private String tipoUtente;
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


//        textViewTitolo.setText(titolo);
//        textViewCommento.setText(commento);


    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_cancella_notifica_popup_notifica){

            popUpNotificheViewModel.eliminaNotifica();
            //cancella dal db la notifica
        }else
            if(v.getId() == R.id.button_chiudi_popup_notifica){
                Toast.makeText(fragmentActivity, "premuto annulla", Toast.LENGTH_SHORT).show();
                dismiss();
            }
//fare un dao che cancella se cliccato cancella e se chiude fa solo il dismiss
        //a prescindere chiude il popup se si preme uno dei bottoni
        //schermataNotifiche.onResume();
        }
    public void dismissPopup() {
        dismiss();
        if (popupDismissListener != null) {
            Log.d("dismissPopup","entrato");
            popupDismissListener.onPopupDismissed();
        }
    }
    public interface PopupDismissListener {
        void onPopupDismissed();
    }
    public void osservaErroreEliminazione(){
        popUpNotificheViewModel.erroreEliminazione.observe(fragmentActivity, (messaggio) -> {
            Log.d("osservaErroreEliminazione","" + messaggio);
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
            Log.d("osservaNotificaEliminata","" + result);
            if(result){
                Log.d("osservaNotificaEliminata","faccio dismiss popup" );
                dismissPopup();
            }
            });
        }
}