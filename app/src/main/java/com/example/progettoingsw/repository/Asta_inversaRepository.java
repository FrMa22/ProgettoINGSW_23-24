package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_inversa_DTO;
import com.example.progettoingsw.backendAPI.Asta_inversaService;
import com.example.progettoingsw.model.Asta_inversaModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asta_inversaRepository {

    public void getAste_inversaScadenzaRecenteBackend( Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener) {
        System.out.println("entrato in getAste_inversaScadenzaRecenteBackend");
        new Asta_inversaRepository.GetAsteScadenzaRecenteTask(listener).execute();
    }
    public void getAste_inversaNuoveBackend( Asta_inversaRepository.OnGetAsteInversaNuoveListener listener) {
        System.out.println("entrato in getAste_inversaNuoveBackend");
        new Asta_inversaRepository.getAste_inversaNuoveTask(listener).execute();
    }
    public void getAste_inversaCategoriaNomeBackend(String nomeCategoria, Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener) {
        System.out.println("entrato in getAste_inversaCategoriaNomeBackend");
        new Asta_inversaRepository.getAste_inversaCategoriaNomeTask(listener).execute(nomeCategoria);
    }
    private static class GetAsteScadenzaRecenteTask extends AsyncTask<Void, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener;

        public GetAsteScadenzaRecenteTask(Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(Void... voids) {

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
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaScadenzaRecente();

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inverse dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    astaInversaDto.getPath_immagine(),
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute recupero aste inverse scadenza recente" + result);
            if (listener != null) {
                listener.OnGetAsteScadenzaRecente(result);
            }
        }
    }
    public interface OnGetAsteScadenzaRecenteListener {
        void OnGetAsteScadenzaRecente(ArrayList<Asta_inversaModel> list);
    }
    private static class getAste_inversaNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteInversaNuoveListener listener;

        public getAste_inversaNuoveTask(Asta_inversaRepository.OnGetAsteInversaNuoveListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(Void... voids) {

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
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaNuove();

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    astaInversaDto.getPath_immagine(),
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute getAste_inversaNuoveTask" + result);
            if (listener != null) {
                listener.OnGetAsteInversaNuove(result);
            }
        }
    }
    public interface OnGetAsteInversaNuoveListener {
        void OnGetAsteInversaNuove(ArrayList<Asta_inversaModel> list);
    }
    private static class getAste_inversaCategoriaNomeTask extends AsyncTask<String, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener;

        public getAste_inversaCategoriaNomeTask(Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(String... params) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaCategoriaNome(nomeCategoria);

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    astaInversaDto.getPath_immagine(),
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute aste inverse per categoria nome" + result);
            if (listener != null) {
                listener.OnGetAsteInversaCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteInversaCategoriaNomeListener {
        void OnGetAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> list);
    }
}
