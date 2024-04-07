package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_alribasso_DTO;
import com.example.progettoingsw.backendAPI.Asta_allingleseService;
import com.example.progettoingsw.backendAPI.Asta_alribassoService;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asta_alribassoRepository {

    public void getAste_alribassoNuoveBackend( Asta_alribassoRepository.OnGetAsteRibassoNuoveListener listener) {
        System.out.println("entrato in getAste_alribassoNuoveBackend");
        new Asta_alribassoRepository.getAste_alribassoNuoveTask(listener).execute();
    }
    private static class getAste_alribassoNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAsteRibassoNuoveListener listener;

        public getAste_alribassoNuoveTask(Asta_alribassoRepository.OnGetAsteRibassoNuoveListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_alribassoModel> doInBackground(Void... voids) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<ArrayList<Asta_alribasso_DTO>> call = service.getAste_alribassoNuove();

            try {
                Response<ArrayList<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_alribasso_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste ribasso dto non null");
                        ArrayList<Asta_alribassoModel> listAsta_alribassoModel = new ArrayList<>();
                        for (Asta_alribasso_DTO astaAlribassoDto : list){
                            Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                    astaAlribassoDto.getId(),
                                    astaAlribassoDto.getNome(),
                                    astaAlribassoDto.getDescrizione(),
                                    astaAlribassoDto.getPath_immagine(),
                                    astaAlribassoDto.getPrezzoBase(),
                                    astaAlribassoDto.getIntervalloDecrementale(),
                                    astaAlribassoDto.getIntervalloBase(),
                                    astaAlribassoDto.getDecrementoAutomaticoCifra(),
                                    astaAlribassoDto.getPrezzoMin(),
                                    astaAlribassoDto.getPrezzoAttuale(),
                                    astaAlribassoDto.getCondizione(),
                                    astaAlribassoDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta ribasso" ," valori " + astaAlribassoModel.getNome() + astaAlribassoModel.getDescrizione() + astaAlribassoModel.getId() + astaAlribassoModel.getIntervalloBase() + astaAlribassoModel.getPrezzoMin());
                            listAsta_alribassoModel.add(astaAlribassoModel);
                        }
                        return listAsta_alribassoModel;
                    }
                    System.out.println("lista di aste inglesi dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_alribassoModel> result) {
            System.out.println("on post execute loginAcquirente" + result);
            if (listener != null) {
                listener.OnGetAsteRibassoNuove(result);
            }
        }
    }
    public interface OnGetAsteRibassoNuoveListener {
        void OnGetAsteRibassoNuove(ArrayList<Asta_alribassoModel> list);
    }
}
