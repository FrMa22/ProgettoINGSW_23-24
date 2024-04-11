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
    public void getAste_alribassoCategoriaNomeBackend(String nomeCategoria, Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener) {
        System.out.println("entrato in getAste_alribassoCategoriaNomeBackend");
        new Asta_alribassoRepository.getAste_alribassoCategoriaNomeTask(listener).execute(nomeCategoria);
    }
    public void acquistaAsta_alribasso(Long idAsta, String emailAcquirente,String prezzoAttuale, Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener) {
        System.out.println("entrato in getAste_alribassoNuoveBackend");
        new Asta_alribassoRepository.acquistaAsta_aliribassoTask(listener).execute(String.valueOf(idAsta),emailAcquirente,prezzoAttuale);
    }
    public void trovaAsta_alribasso(Long idAsta, Asta_alribassoRepository.OnTrovaAstaRibassoListener listener) {
        System.out.println("entrato in trovaAsta_alribasso");
        new Asta_alribassoRepository.trovaAsta_alribassoTask(listener).execute(String.valueOf(idAsta));
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
                    System.out.println("lista di aste al ribasso dto null");
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
    private static class getAste_alribassoCategoriaNomeTask extends AsyncTask<String, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener;

        public getAste_alribassoCategoriaNomeTask(Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_alribassoModel> doInBackground(String... params) {
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

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<ArrayList<Asta_alribasso_DTO>> call = service.getAste_alribassoCategoriaNome(nomeCategoria);

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
                    System.out.println("lista di aste al ribasso dto null");
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
            System.out.println("on post execute aste al ribasso per categoria nome" + result);
            if (listener != null) {
                listener.OnGetAsteRibassoCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteRibassoCategoriaNomeListener {
        void OnGetAsteRibassoCategoriaNome(ArrayList<Asta_alribassoModel> list);
    }
    private static class acquistaAsta_aliribassoTask extends AsyncTask<String, Void, Integer> {
        private Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener;

        public acquistaAsta_aliribassoTask(Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato
            Long idAsta = Long.valueOf(params[0]);
            String emailAcquirente = params[1];
            String offerta = params[2];

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<Integer> call = service.acquistaAstaAlRibasso(idAsta,emailAcquirente,offerta);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer risposta = response.body();
                    if (risposta != null && risposta==1) {
                        return risposta;
                    }
                    System.out.println("lista di aste al ribasso dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (listener != null) {
                listener.OnAcquistaAstaRibasso(result);
            }
        }
    }
    public interface OnAcquistaAstaRibassoListener {
        void OnAcquistaAstaRibasso(Integer risposta);
    }
    private static class trovaAsta_alribassoTask extends AsyncTask<String, Void, Asta_alribassoModel> {
        private Asta_alribassoRepository.OnTrovaAstaRibassoListener listener;

        public trovaAsta_alribassoTask(Asta_alribassoRepository.OnTrovaAstaRibassoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Asta_alribassoModel doInBackground(String... params) {
            Long idAsta = Long.valueOf(params[0]);
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
            Call<Asta_alribasso_DTO> call = service.trovaAstaRibasso(idAsta);

            try {
                Response<Asta_alribasso_DTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Asta_alribasso_DTO astaRecuperata = response.body();
                    if (astaRecuperata != null) {
                        Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                astaRecuperata.getId(),
                                astaRecuperata.getNome(),
                                astaRecuperata.getDescrizione(),
                                astaRecuperata.getPath_immagine(),
                                astaRecuperata.getPrezzoBase(),
                                astaRecuperata.getIntervalloDecrementale(),
                                astaRecuperata.getIntervalloBase(),
                                astaRecuperata.getDecrementoAutomaticoCifra(),
                                astaRecuperata.getPrezzoMin(),
                                astaRecuperata.getPrezzoAttuale(),
                                astaRecuperata.getCondizione(),
                                astaRecuperata.getId_venditore());
                        //stampa dei valori dell asta
                        return astaAlribassoModel;
                    }else{
                        System.out.println("asta dto null");
                        return null;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Asta_alribassoModel result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnTrovaAstaRibasso(result);
            }
        }
    }
    public interface OnTrovaAstaRibassoListener {
        void OnTrovaAstaRibasso(Asta_alribassoModel list);
    }
}
