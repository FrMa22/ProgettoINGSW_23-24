package com.example.progettoingsw.view.acquirente;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AcquirenteFragmentCreaLaTuaAstaAcquirente extends Fragment {

    String opzioneSelezionata;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    Spinner spinnerTipoAsta;
    MaterialButton bottoneProseguiCreaAstaAcquirente;
    ActivityResultLauncher<Intent> resultLauncher;
    EditText descrizioneProdotto;
    String descProd;
    String email;

    byte[] imageBytes;

    Uri uriImmagine;

    public AcquirenteFragmentCreaLaTuaAstaAcquirente() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment = inflater.inflate(R.layout.acquirente_fragment_crea_la_tua_asta_acquirente, container, false);
        return view_fragment;
    }

    public void onViewCreated(@NonNull View view_fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view_fragment, savedInstanceState);
//        immagineProdotto= view_fragment.findViewById(R.id.imageViewCreaAstaAcquirente);
//        bottoneInserisciImmagine = view_fragment.findViewById(R.id.imageButtonInserisciImmagineCreaAstaAcquirente);
//        descrizioneProdotto=view_fragment.findViewById(R.id.editTextDescrizioneCreaAstaAcquirente);
//        registraRisultati();
//
//
//        //ImmaginiDAO immaginiDAO=new ImmaginiDAO();
//        imageBytes=null;
//        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine
//
//        bottoneProseguiCreaAstaAcquirente = view_fragment.findViewById(R.id.bottoneProseguiCreaAstaAcquirente);
//        bottoneProseguiCreaAstaAcquirente.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                //Controller.redirectActivity(getActivity(), FragmentCreaAstaInversa.class);
//                descProd=descrizioneProdotto.getText().toString();
//                //qui sopra era giusto
//
//               // immaginiDAO.openConnection();
//                //immaginiDAO.aggiungiImmagine(imageBytes);
//                //immaginiDAO.closeConnection();
//
//                //qui era giusto
//                Intent intent = new Intent(getActivity(), FragmentCreaAstaInversa.class);
//                intent.putExtra("descProd", descProd);
//                intent.putExtra("email",email);
//                intent.putExtra("img",imageBytes);
//                startActivity(intent);
//            }
//        });
//
//        spinnerTipoAsta = view_fragment.findViewById(R.id.spinnerTipologiaAstaAcquirente);
//        ArrayAdapter<CharSequence> adapterSpinnerTipoAsta=(ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(requireContext(), R.array.elencoTipiAstaAcquirente, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        adapterSpinnerTipoAsta.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        spinnerTipoAsta.setAdapter(adapterSpinnerTipoAsta);
//
//        spinnerTipoAsta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //fa qualcosa se si è selezionato qualcosa
//                opzioneSelezionata=parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //non fa nulla
//            }
//        });


    }



    private void prelevaImmagine(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            resultLauncher.launch(intent);
        } else {
            Toast.makeText(requireContext(), "Nessuna app disponibile per gestire la selezione delle immagini", Toast.LENGTH_SHORT).show();
        }
    }

    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            uriImmagine = result.getData().getData();
                            displayImage(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Metodo per comprimere un'immagine Bitmap e convertirla in un array di byte
    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
        return outputStream.toByteArray();
    }

    private void displayImage(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Ridimensiona l'immagine per adattarla alla dimensione desiderata
            int targetWidth = 500; // Imposta la larghezza desiderata
            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth())); // Calcola l'altezza in base al rapporto
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

            // Comprimi l'immagine
            byte[] compressedImageBytes = compressAndConvertToByteArray(resizedBitmap);

            // Imposta l'immagine ridimensionata nella ImageView
            immagineProdotto.setImageBitmap(resizedBitmap);

            // Salva i byte compressi per l'invio
            imageBytes = compressedImageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
