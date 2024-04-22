package com.example.progettoingsw.view.acquirente;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;


import com.example.progettoingsw.R;

import com.example.progettoingsw.view.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.viewmodel.CreaAstaInversaViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentCreaAstaInversa extends Fragment {
    private CreaAstaInversaViewModel creaAstaInversaViewModel;
    private int idAsta;
    private ImageButton imageButtonRimuoviImmagine;
    AppCompatButton bottoneConferma;
    AppCompatButton bottoneAnnullaAstaInversa;
    MaterialButton bottoneData;
    MaterialButton bottoneOra;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    ImageButton bottone_info;
    ActivityResultLauncher<Intent> resultLauncher;
    private ArrayList<String> listaCategorieScelte;
    private MaterialButton bottoneCategorieAstaInversa;
    EditText nomeAstaInversa;
    EditText prezzoAstaInversa;

    private String selectedDateString;
    private String selectedHourString;
    private byte [] img;//è presente nei metodi per fare select+aggiunta foto nella schermata quindi non serve a livello pratico qui
    Bitmap bitmap;
    Uri uriImmagine;
    byte[] imageBytes;
    String email;
    EditText descrizioneProdottoAstaAstaInversa;

    public FragmentCreaAstaInversa(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acquirente_fragment_asta_inversa, container, false);
    }

    @Override
    public void onViewCreated(View view2, Bundle savedInstanceState) {
        super.onViewCreated(view2, savedInstanceState);

        creaAstaInversaViewModel = new ViewModelProvider(this).get(CreaAstaInversaViewModel.class);
        osservaApriPopUpCategorie();
        osservaErroreNome();
        osservaErroreDescrizione();
        osservaErrorePrezzo();
        osservaErroreDataOra();
        osservaApriGalleria();
        osservaOpenCalendario();
        osservaOpenOrario();
        osservaApriPopUpInformazioni();
        osservaCreaPopUpInformazioni();
        osservaImmagineConvertita();
        osservaAstaCreata();


        bottoneAnnullaAstaInversa = view2.findViewById(R.id.bottoneAnnullaAstaInversa);
        bottoneConferma = view2.findViewById(R.id.bottoneConfermaAstaInversa);
        immagineProdotto= view2.findViewById(R.id.imageViewCreaAstaAcquirente);
        bottoneInserisciImmagine = view2.findViewById(R.id.imageButtonInserisciImmagineCreaAstaAcquirente);
        bottoneInserisciImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(Intent.ACTION_PICK).setType("image/*"));
                //creaAstaInversaViewModel.prelevaImmagine(requireActivity());
            }
        });
        registraRisultati();
        imageButtonRimuoviImmagine = view2.findViewById(R.id.imageButtonRimuoviImmagineCreaAstaInversa);
        imageButtonRimuoviImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OSSERVATOREEEEEE
                immagineProdotto.setImageResource(android.R.color.transparent); // Rimuove l'immagine
                bitmap = null;
//                imageBytes = null; // Reimposta il byte array a null
//                uriImmagine = null;
            }
        });

        bottoneCategorieAstaInversa =view2.findViewById(R.id.bottoneCategorieAstaInversa);
        bottoneCategorieAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaInversaViewModel.apriPopUp();
            }
        });


        bottoneData =  view2.findViewById(R.id.bottoneDataAstaInversa);
        bottoneOra =  view2.findViewById(R.id.bottoneOraAstaInversa);

        nomeAstaInversa =view2.findViewById(R.id.editTextNomeProdottoAstaAstaInversa);
        descrizioneProdottoAstaAstaInversa = view2.findViewById(R.id.editTextDescrizioneProdottoAstaAstaInversa);
        prezzoAstaInversa=view2.findViewById(R.id.editTextPrezzoAstaInversa);

//        imageBytes=null;

        bottone_info = view2.findViewById(R.id.bottone_info);
        bottone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaInversaViewModel.apriPopUpInformazioni();
            }
        });

//        //bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
//
//        // Imposta l'immagine nel tuo ImageView
//        //immagineProdotto.setImageBitmap(bitmap);

        bottoneData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaAstaInversaViewModel.openCalendario();
            }
        });

        bottoneOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaAstaInversaViewModel.openOrario();
            }
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nome = nomeAstaInversa.getText().toString();
                String prezzo = prezzoAstaInversa.getText().toString();
                String descrizione = descrizioneProdottoAstaAstaInversa.getText().toString();
                String data = selectedDateString;
                String ora = selectedHourString;
                creaAstaInversaViewModel.creaAsta(nome,descrizione,prezzo,data, ora, bitmap);

            }
        });
        bottoneAnnullaAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void osservaApriPopUpInformazioni(){
        creaAstaInversaViewModel.apriPopUpInformazioni.observe(getViewLifecycleOwner(), (valore) ->{
            if(valore){
                creaAstaInversaViewModel.creaPopUpInformazioni(requireActivity());
            }
        });
    }
    public void osservaCreaPopUpInformazioni(){
        creaAstaInversaViewModel.popUpInformazioni.observe(getViewLifecycleOwner(), (valore) ->{
            if(creaAstaInversaViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
    public void osservaApriPopUpCategorie(){
        creaAstaInversaViewModel.apriPopUpCategoria.observe(getViewLifecycleOwner(), (valore) ->{
            if(valore){
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(getContext(), FragmentCreaAstaInversa.this,creaAstaInversaViewModel);
                popUpAggiungiCategorieAsta.show();
            }
        });
    }
    public void osservaAstaCreata(){
        creaAstaInversaViewModel.astaCreata.observe(getViewLifecycleOwner(), (result) -> {
            if(result){
                bottoneAnnullaAstaInversa.performClick();
            }
        });
    }
    public void osservaErroreNome(){
        creaAstaInversaViewModel.erroreNome.observe(getViewLifecycleOwner(), (errore) ->{
            if(creaAstaInversaViewModel.isErroreNome()){
                nomeAstaInversa.setError(errore);
            }
        });
    }
    public void osservaErroreDescrizione(){
        creaAstaInversaViewModel.erroreDescrizione.observe(getViewLifecycleOwner(), (errore) ->{
            if(creaAstaInversaViewModel.isErroreDescrizione()){
                descrizioneProdottoAstaAstaInversa.setError(errore);
            }
        });
    }
    public void osservaErrorePrezzo(){
        creaAstaInversaViewModel.errorePrezzo.observe(getViewLifecycleOwner(), (errore) ->{
            if(creaAstaInversaViewModel.isErrorePrezzo()){
                prezzoAstaInversa.setError(errore);
            }
        });
    }
    public void osservaErroreDataOra(){
        creaAstaInversaViewModel.erroreDataOra.observe(getViewLifecycleOwner(), (errore) ->{
            if(creaAstaInversaViewModel.isErroreDataOra()){
                Toast.makeText(getContext(), errore, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void osservaApriGalleria(){
        creaAstaInversaViewModel.apriGalleria.observe(getViewLifecycleOwner(), (intent) -> {
            if(creaAstaInversaViewModel.isApriGalleria()){
                resultLauncher.launch(intent);
                registraRisultati();
            }
        });
    }
    public void osservaOpenCalendario(){
        creaAstaInversaViewModel.openCalendario.observe(getViewLifecycleOwner(), (valore) -> {
            if(valore){
                apriCalendario();
            }
        });
    }
    public void osservaOpenOrario(){
        creaAstaInversaViewModel.openOrario.observe(getViewLifecycleOwner(), (valore) -> {
            if(valore){
                apriOrologio();
            }
        });
    }
    private void apriCalendario() {
        // Ottieni l'anno, il mese e il giorno odierni
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Crea il DatePickerDialog con la data odierna come minima data selezionabile
        DatePickerDialog calendario = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Imposta la data selezionata nel bottone e nella variabile di classe
                bottoneData.setText(String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(day));
                selectedDateString = String.format("%04d-%02d-%02d", year, month + 1, day);
                bottoneData.setText(selectedDateString);
            }
        }, year, month, day);

        // Imposta la data minima selezionabile come data odierna
        calendario.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Mostra il DatePickerDialog
        calendario.show();
    }
    private void apriOrologio(){
        TimePickerDialog orologio = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minute) {
                bottoneOra.setText(String.valueOf(hours)+":"+String.valueOf(minute));
                selectedHourString=String.format("%02d:%02d", hours, minute);
                bottoneOra.setText(selectedHourString);
            }
        }, 00, 00, true);
        orologio.show();
    }



    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            creaAstaInversaViewModel.setImmagine(result, requireActivity());

//                            uriImmagine = result.getData().getData();
//                            displayImage(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void osservaImmagineConvertita(){
        creaAstaInversaViewModel.immagineConvertita.observe(getViewLifecycleOwner(), (immagine) -> {
            if(creaAstaInversaViewModel.isImmagineConverita()){
                bitmap = immagine;
                immagineProdotto.setImageBitmap(immagine);
            }
        });
    }



//    public void handlePopUp(ArrayList<String> switchTexts){
//        this.listaCategorieScelte = switchTexts;
//        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
//        for (String categoria : switchTexts) {
//            Log.d("PopUpHandler", "Categoria: " + categoria);
//        }
//    }
//    public void handleID(int id){
//        this.idAsta = id;
//        AstaInversaDAO astaInversaDAO = new AstaInversaDAO(FragmentCreaAstaInversa.this);
//        if(!listaCategorieScelte.isEmpty()){
//            astaInversaDAO.openConnection();
//            Log.d("id recuperato è ACquirente : " , " id: " + idAsta);
//            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
//            astaInversaDAO.inserisciCategorieAstaInversa(asta);
//            astaInversaDAO.closeConnection();
//        }else{
//            astaInversaDAO.closeConnection();
//        }
//        Toast.makeText(getContext(), "Asta creata con successo!", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getContext(), MainActivity.class);//test del login
//        intent.putExtra("email", email);
//        intent.putExtra("tipoUtente", "acquirente");
//        startActivity(intent);
//    }
}