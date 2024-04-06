package com.example.progettoingsw.repository;

import android.os.AsyncTask;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.model.AcquirenteModel;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;public class LoginRepository {

    Repository repository = Repository.getInstance();

    public void loginBackend(String email, String password, OnLoginListener listener) {
        System.out.println("entrato in login backend");
        new LoginTask(listener).execute(email, password);
    }

    private static class LoginTask extends AsyncTask<String, Void, AcquirenteModel> {
        private OnLoginListener listener;

        public LoginTask(OnLoginListener listener) {
            this.listener = listener;
        }

        @Override
        protected AcquirenteModel doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            System.out.println("in async, email e pass: " + email + password);
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
            Call<AcquirenteDTO> call = service.logIn(email, password);

            try {
                Response<AcquirenteDTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {
                        System.out.println("acquirente dto non null");
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
            System.out.println("on post execute" + result);
            if (listener != null) {
                listener.onLogin(result);
            }
        }
    }

    public interface OnLoginListener {
        void onLogin(AcquirenteModel acquirenteModel);
    }
}

