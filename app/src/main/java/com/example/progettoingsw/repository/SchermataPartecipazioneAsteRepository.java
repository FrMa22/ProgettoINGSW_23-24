package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_alribasso_DTO;
import com.example.progettoingsw.DTO.Asta_inversa_DTO;
import com.example.progettoingsw.backendAPI.Asta_allingleseService;
import com.example.progettoingsw.backendAPI.Asta_alribassoService;
import com.example.progettoingsw.backendAPI.Asta_inversaService;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchermataPartecipazioneAsteRepository {

    public void trovaAsteInglesiBackend(String email,OnTrovaAsteInglesiListener listener) {

        new TrovaAsteInglesiTask(listener).execute(email);
    }
    public void trovaAsteInverseBackend(String email,OnTrovaAsteInverseListener listener) {

        new TrovaAsteInverseTask(listener).execute(email);
    }


    private static class TrovaAsteInglesiTask extends AsyncTask<String, Void, List<Asta_allingleseModel>> {
        private OnTrovaAsteInglesiListener listener;

        public TrovaAsteInglesiTask(OnTrovaAsteInglesiListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_allingleseModel> doInBackground(String... params) {
            String email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<ArrayList<Asta_allinglese_DTO>> call=service.getAste_allinglesePartecipateByEmail(email);



            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {

                    ArrayList<Asta_allinglese_DTO> astaIngleseDTOList=response.body();
                    if (astaIngleseDTOList!= null && !astaIngleseDTOList.isEmpty()) {


                        ArrayList<Asta_allingleseModel> astaIngleseModelList=new ArrayList<>();
                        for (Asta_allinglese_DTO astaIngleseDTO : astaIngleseDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaIngleseDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaIngleseDTO.getPath_immagine());}

                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_allingleseModel astaIngleseModel=new Asta_allingleseModel(astaIngleseDTO.getId(),astaIngleseDTO.getNome(),astaIngleseDTO.getDescrizione(),pathImmagineByteArray,astaIngleseDTO.getBaseAsta(),astaIngleseDTO.getIntervalloTempoOfferte(),astaIngleseDTO.getIntervalloOfferteBase(),astaIngleseDTO.getRialzoMin(),astaIngleseDTO.getPrezzoAttuale(),astaIngleseDTO.getCondizione(),astaIngleseDTO.getId_venditore());
                            astaIngleseModelList.add(astaIngleseModel);

                        }

                        return astaIngleseModelList;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_allingleseModel> result) {

            if (listener != null) {
                listener.onTrovaAsteInglesi(result);
            }
        }
    }


    public interface OnTrovaAsteInglesiListener {
        void onTrovaAsteInglesi(List<Asta_allingleseModel> astaAllingleseModelList);
    }

//




    //


    //


    //

    private static class TrovaAsteInverseTask extends AsyncTask<String, Void, List<Asta_inversaModel>> {
        private OnTrovaAsteInverseListener listener;

        public TrovaAsteInverseTask(OnTrovaAsteInverseListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_inversaModel> doInBackground(String... params) {
            String email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call=service.getAste_inversaPartecipateByEmail(email);



            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {

                    ArrayList<Asta_inversa_DTO> astaInversaDTOList=response.body();
                    if (astaInversaDTOList!= null && !astaInversaDTOList.isEmpty()) {


                        ArrayList<Asta_inversaModel> astaInversaModelList=new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDTO : astaInversaDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDTO.getPath_immagine());}

                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_inversaModel astaInversaModel=new Asta_inversaModel(astaInversaDTO.getId(),astaInversaDTO.getNome(),astaInversaDTO.getDescrizione(),pathImmagineByteArray,astaInversaDTO.getPrezzoMax(),astaInversaDTO.getPrezzoAttuale(),astaInversaDTO.getDataDiScadenza(),astaInversaDTO.getCondizione(),astaInversaDTO.getId_acquirente());
                            astaInversaModelList.add(astaInversaModel);

                        }

                        return astaInversaModelList;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_inversaModel> result) {

            if (listener != null) {
                listener.onTrovaAsteInverse(result);
            }
        }
    }


    public interface OnTrovaAsteInverseListener {
        void onTrovaAsteInverse(List<Asta_inversaModel> astaInversaModelList);
    }

    //


    public static byte[] base64ToByteArray(String base64String) {
        // Rimuovi il prefisso "data:image/jpeg;base64," se presente
        String base64WithoutPrefix = base64String.replaceFirst("^data:image/[a-zA-Z]*;base64,", "");

        // Decodifica la stringa Base64 in un array di byte
        return Base64.decode(base64WithoutPrefix, Base64.DEFAULT);
    }
    // Funzione per convertire un array di byte in una stringa Base64
    public static String byteArrayToBase64(byte[] byteArray) {
        // Codifica l'array di byte in una stringa Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
