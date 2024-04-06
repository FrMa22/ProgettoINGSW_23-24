package com.example.progettoingsw.repository;

import android.os.AsyncTask;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.VenditoreDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.backendAPI.VenditoreService;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;

import okhttp3.OkHttpClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;public class LoginRepository {

    Repository repository = Repository.getInstance();

    public void loginAcquirenteBackend(String email, String password, OnLoginAcquirenteListener listener) {
        System.out.println("entrato in loginAcquirente backend");
        new LoginAcquirenteTask(listener).execute(email, password);
    }
    public void loginVenditoreBackend(String email, String password, OnLoginVenditoreListener listener) {
        System.out.println("entrato in loginVenditore backend");
        new LoginVenditoreTask(listener).execute(email, password);
    }

    private static class LoginAcquirenteTask extends AsyncTask<String, Void, AcquirenteModel> {
        private OnLoginAcquirenteListener listener;

        public LoginAcquirenteTask(OnLoginAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected AcquirenteModel doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            System.out.println("in async loginAcquirente, email e pass: " + email + password);
            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            AcquirenteService service = retrofit.create(AcquirenteService.class);
            Call<AcquirenteDTO> call = service.logInAcquirente(email, password);

            try {
                Response<AcquirenteDTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {
                        System.out.println("acquirente dto non null");
                        System.out.println("valori del acquirente: " + acquirenteDTO.getNome() + acquirenteDTO.getCognome());
                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(), acquirenteDTO.getIndirizzoEmail(), acquirenteDTO.getPassword(), acquirenteDTO.getBio(), acquirenteDTO.getAreaGeografica(), acquirenteDTO.getLink());
                    }
                    System.out.println("acquirente dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AcquirenteModel result) {
            System.out.println("on post execute loginAcquirente" + result);
            if (listener != null) {
                listener.onLogin(result);
            }
        }
    }
    private static class LoginVenditoreTask extends AsyncTask<String, Void, VenditoreModel> {
        private OnLoginVenditoreListener listener;

        public LoginVenditoreTask(OnLoginVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected VenditoreModel doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            System.out.println("in async loginVenditore, email e pass: " + email + password);
            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            VenditoreService service = retrofit.create(VenditoreService.class);
            Call<VenditoreDTO> call = service.logInVenditore(email, password);

            try {
                Response<VenditoreDTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {
                        System.out.println("venditore dto non null");
                        System.out.println("valori del venditore: " + venditoreDTO.getNome() + venditoreDTO.getCognome());
                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(), venditoreDTO.getIndirizzoEmail(), venditoreDTO.getPassword(), venditoreDTO.getBio(), venditoreDTO.getAreaGeografica(), venditoreDTO.getLink());
                    }
                    System.out.println("venditore dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(VenditoreModel result) {
            System.out.println("on post execute loginVenditore" + result);
            if (listener != null) {
                listener.onLogin(result);
            }
        }
    }
    public interface OnLoginAcquirenteListener {
        void onLogin(AcquirenteModel acquirenteModel);
    }
    public interface OnLoginVenditoreListener {
        void onLogin(VenditoreModel venditoreModel);
    }
}
