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
        System.out.println("entrato in  Trova Aste inglesi  Backend");
        new TrovaAsteInglesiTask(listener).execute(email);
    }





    public void trovaAsteInverseBackend(String email,OnTrovaAsteInverseListener listener) {
        System.out.println("entrato in  Trova Aste  inverse  Backend");
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
            System.out.println("in async trova aste inglesi aperte, email: " + email);
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


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> astaIngleseDTOList=response.body();
                    if (astaIngleseDTOList!= null && !astaIngleseDTOList.isEmpty()) {
                        System.out.println("Asta inglese dto non null");

                        ArrayList<Asta_allingleseModel> astaIngleseModelList=new ArrayList<>();
                        for (Asta_allinglese_DTO astaIngleseDTO : astaIngleseDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaIngleseDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaIngleseDTO.getPath_immagine());}
                            System.out.println("Valori del asta inglese DTO: " + "id: " + astaIngleseDTO.getId() + ", nome: " + astaIngleseDTO.getNome() + ", email: " + astaIngleseDTO.getId_venditore());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_allingleseModel astaIngleseModel=new Asta_allingleseModel(astaIngleseDTO.getId(),astaIngleseDTO.getNome(),astaIngleseDTO.getDescrizione(),pathImmagineByteArray,astaIngleseDTO.getBaseAsta(),astaIngleseDTO.getIntervalloTempoOfferte(),astaIngleseDTO.getIntervalloOfferteBase(),astaIngleseDTO.getRialzoMin(),astaIngleseDTO.getPrezzoAttuale(),astaIngleseDTO.getCondizione(),astaIngleseDTO.getId_venditore());
                            astaIngleseModelList.add(astaIngleseModel);
                            System.out.println("Valori del asta inglese MODEL: " + "id: " + astaIngleseModel.getId() + ", nome: " + astaIngleseModel.getNome() + ", email: " + astaIngleseModel.getId_venditore());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaIngleseModelList;
                    }
                    System.out.println("Asta inglese dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_allingleseModel> result) {
            System.out.println("on post execute trova Asta inglese partecipate");
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
            System.out.println("in async trova aste inverse , email: " + email);
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


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> astaInversaDTOList=response.body();
                    if (astaInversaDTOList!= null && !astaInversaDTOList.isEmpty()) {
                        System.out.println("Asta inversa dto non null");

                        ArrayList<Asta_inversaModel> astaInversaModelList=new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDTO : astaInversaDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDTO.getPath_immagine());}
                            System.out.println("Valori del asta inversa DTO: " + "id: " + astaInversaDTO.getId() + ", nome: " + astaInversaDTO.getNome() + ", email: " + astaInversaDTO.getId_acquirente());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_inversaModel astaInversaModel=new Asta_inversaModel(astaInversaDTO.getId(),astaInversaDTO.getNome(),astaInversaDTO.getDescrizione(),pathImmagineByteArray,astaInversaDTO.getPrezzoMax(),astaInversaDTO.getPrezzoAttuale(),astaInversaDTO.getDataDiScadenza(),astaInversaDTO.getCondizione(),astaInversaDTO.getId_acquirente());
                            astaInversaModelList.add(astaInversaModel);
                            System.out.println("Valori del asta inversa MODEL: " + "id: " + astaInversaModel.getId() + ", nome: " + astaInversaModel.getNome() + ", email: " + astaInversaModel.getId_acquirente());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaInversaModelList;
                    }
                    System.out.println("Asta ribasso dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_inversaModel> result) {
            System.out.println("on post execute trova Asta inverse partecipate");
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
