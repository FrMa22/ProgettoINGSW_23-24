package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.backendAPI.Asta_allingleseService;
import com.example.progettoingsw.model.Asta_allingleseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asta_allingleseRepository {

    public void getAste_allingleseScadenzaRecenteBackend( Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener) {
        System.out.println("entrato in getAste_allingleseScadenzaRecenteBackend");
        new Asta_allingleseRepository.GetAsteScadenzaRecenteTask(listener).execute();
    }
    public void getAste_allingleseNuoveBackend( Asta_allingleseRepository.OnGetAsteNuoveListener listener) {
        System.out.println("entrato in getAste_allingleseNuoveBackend");
        new Asta_allingleseRepository.GetAsteNuoveTask(listener).execute();
    }
    public void getAste_allingleseCategoriaNomeBackend(String nomeCategoria, Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener) {
        System.out.println("entrato in getAste_allingleseCategoriaNomeBackend");
        new Asta_allingleseRepository.GetAsteCategoriaNomeTask(listener).execute(nomeCategoria);
    }


    private static class GetAsteScadenzaRecenteTask extends AsyncTask<Void, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener;

        public GetAsteScadenzaRecenteTask(Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(Void... voids) {

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
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseScadenzaRecente();

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
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
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnGetAsteScadenzaRecente(result);
            }
        }
    }
    public interface OnGetAsteScadenzaRecenteListener {
        void OnGetAsteScadenzaRecente(ArrayList<Asta_allingleseModel> list);
    }
    private static class GetAsteNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteNuoveListener listener;

        public GetAsteNuoveTask(Asta_allingleseRepository.OnGetAsteNuoveListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(Void... voids) {

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
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseNuove();

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
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
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteNuoveTask" + result);
            if (listener != null) {
                listener.OnGetAsteNuove(result);
            }
        }
    }
    public interface OnGetAsteNuoveListener {
        void OnGetAsteNuove(ArrayList<Asta_allingleseModel> list);
    }

    private static class GetAsteCategoriaNomeTask extends AsyncTask<String, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener;

        public GetAsteCategoriaNomeTask(Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(String... params) {

            String nomeCategoria = params[0];

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
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseCategoriaNome(nomeCategoria);

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
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
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnGetAsteCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteCategoriaNomeListener {
        void OnGetAsteCategoriaNome(ArrayList<Asta_allingleseModel> list);
    }
}
