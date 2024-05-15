package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.VenditoreDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.backendAPI.VenditoreService;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;

import okhttp3.OkHttpClient;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class LoginRepository {


    public void loginAcquirenteBackend(String email, String password, OnLoginAcquirenteListener listener) {

        new LoginAcquirenteTask(listener).execute(email, password);
    }
    public void loginAcquirenteConTokenBackend(String token, OnLoginAcquirenteConTokenListener listener) {

        new LoginAcquirenteConTokenTask(listener).execute(token);
    }
    public void recuperaCategorieAcquirenteBackend(String email, OnRecuperaCategorieAcquirenteListener listener) {

        new RecuperaCategorieAcquirenteTask(listener).execute(email);
    }
    public void setTokenAcquirente(String email, String token, OnSetTokenAcquirenteListener listener) {

        new SetTokenAcquirenteTask(listener).execute(email, token);
    }
    public void loginVenditoreBackend(String email, String password, OnLoginVenditoreListener listener) {

        new LoginVenditoreTask(listener).execute(email, password);
    }
    public void loginVenditoreConTokenBackend(String token, OnLoginVenditoreConTokenListener listener) {

        new LoginVenditoreConTokenTask(listener).execute(token);
    }
    public void recuperaCategorieVenditoreBackend(String email, OnRecuperaCategorieVenditoreListener listener) {

        new RecuperaCategorieVenditoreTask(listener).execute(email);
    }
    public void setTokenVenditore(String email, String token, OnSetTokenVenditoreListener listener) {

        new SetTokenVenditoreTask(listener).execute(email, token);
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
                if (response.body() != null && response.isSuccessful()) {


                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {


                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(),email, password, acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
                    }

                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginAcquirenteTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(AcquirenteModel result) {

            if (listener != null) {
                //
                listener.onLogin(result);
            }
        }
    }
    public interface OnLoginAcquirenteListener {
        void onLogin(AcquirenteModel acquirenteModel);
    }
    private static class RecuperaCategorieAcquirenteTask extends AsyncTask<String, Void, ArrayList<String>> {
        private OnRecuperaCategorieAcquirenteListener listener;

        public RecuperaCategorieAcquirenteTask(OnRecuperaCategorieAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
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

            AcquirenteService service = retrofit.create(AcquirenteService.class);
            Call<ArrayList<String>> call = service.findCategorieByIndirizzoEmailAcquirente(email);

            try {
                Response<ArrayList<String>> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    ArrayList<String> listaCategorie = response.body();
                    return listaCategorie;
                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginAcquirenteTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            if (listener != null) {
                listener.onRecuperaCategorieAcquirente(result);
            }
        }
    }
    public interface OnRecuperaCategorieAcquirenteListener {
        void onRecuperaCategorieAcquirente(ArrayList<String> listaCategorie);
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
                if (response.body() != null && response.isSuccessful()) {


                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {


                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(),email, password, venditoreDTO.getBio(), venditoreDTO.getAreageografica(), venditoreDTO.getLink());
                    }

                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginVenditoreTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(VenditoreModel result) {

            if (listener != null) {
//
                listener.onLogin(result);
            }
        }
    }

    public interface OnLoginVenditoreListener {
        void onLogin(VenditoreModel venditoreModel);
    }

    private static class RecuperaCategorieVenditoreTask extends AsyncTask<String, Void, ArrayList<String>> {
        private OnRecuperaCategorieVenditoreListener listener;

        public RecuperaCategorieVenditoreTask(OnRecuperaCategorieVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
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

            VenditoreService service = retrofit.create(VenditoreService.class);
            Call<ArrayList<String>> call = service.findCategorieByIndirizzoEmailVenditore(email);

            try {
                Response<ArrayList<String>> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    ArrayList<String> listaCategorie = response.body();
                    return listaCategorie;
                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginAcquirenteTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            if (listener != null) {
                listener.onRecuperaCategorieVenditore(result);
            }
        }
    }
    public interface OnRecuperaCategorieVenditoreListener {
        void onRecuperaCategorieVenditore(ArrayList<String> listaCategorie);
    }

    private static class SetTokenAcquirenteTask extends AsyncTask<String, Void, Integer> {
        private OnSetTokenAcquirenteListener listener;

        public SetTokenAcquirenteTask(OnSetTokenAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String email = params[0];
            String token = params[1];

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
            Call<Integer> call = service.setTokenAcquirente(email, token);

            try {
                Response<Integer> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    Integer valoreDiRitorno = response.body();
                    if (valoreDiRitorno != null && valoreDiRitorno>0) {

                        return valoreDiRitorno;
                    }

                }

            } catch(EOFException e){
                Log.d("SetTokenAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("SetTokenAcquirenteTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.onSetTokenAcquirente(result);
            }
        }
    }
    public interface OnSetTokenAcquirenteListener {
        void onSetTokenAcquirente(Integer valore);
    }

    private static class SetTokenVenditoreTask extends AsyncTask<String, Void, Integer> {
        private OnSetTokenVenditoreListener listener;

        public SetTokenVenditoreTask(OnSetTokenVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String email = params[0];
            String token = params[1];

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
            Call<Integer> call = service.setTokenVenditore(email, token);

            try {
                Response<Integer> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    Integer valoreDiRitorno = response.body();
                    if (valoreDiRitorno != null && valoreDiRitorno>0) {

                        return valoreDiRitorno;
                    }

                }

            } catch(EOFException e){
                Log.d("SetTokenVenditoreTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("SetTokenVenditoreTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.onSetTokenVenditore(result);
            }
        }
    }
    public interface OnSetTokenVenditoreListener {
        void onSetTokenVenditore(Integer valore);
    }

    private static class LoginAcquirenteConTokenTask extends AsyncTask<String, Void, AcquirenteModel> {
        private OnLoginAcquirenteConTokenListener listener;

        public LoginAcquirenteConTokenTask(OnLoginAcquirenteConTokenListener listener) {
            this.listener = listener;
        }

        @Override
        protected AcquirenteModel doInBackground(String... params) {
            String token = params[0];
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
            Call<AcquirenteDTO> call = service.loginAcquirenteConToken(token);

            try {
                Response<AcquirenteDTO> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {


                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(),acquirenteDTO.getIndirizzo_email(), acquirenteDTO.getPassword(), acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
                    }

                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginAcquirenteTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(AcquirenteModel result) {

            if (listener != null) {
                //
                listener.onLoginConToken(result);
            }
        }
    }
    public interface OnLoginAcquirenteConTokenListener {
        void onLoginConToken(AcquirenteModel acquirenteModel);
    }
    private static class LoginVenditoreConTokenTask extends AsyncTask<String, Void, VenditoreModel> {
        private OnLoginVenditoreConTokenListener listener;

        public LoginVenditoreConTokenTask(OnLoginVenditoreConTokenListener listener) {
            this.listener = listener;
        }

        @Override
        protected VenditoreModel doInBackground(String... params) {
            String token = params[0];
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
            Call<VenditoreDTO> call = service.loginVenditoreConToken(token);

            try {
                Response<VenditoreDTO> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {


                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(), venditoreDTO.getIndirizzo_email(), venditoreDTO.getPassword(), venditoreDTO.getBio(), venditoreDTO.getAreageografica(), venditoreDTO.getLink());
                    }

                }

            } catch(EOFException e){
                Log.d("LoginAcquirenteTask" ,"catch di EOFException");
                //e.printStackTrace();
            }catch (IOException e) {
                //e.printStackTrace();
                Log.d("LoginVenditoreTask" ,"catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(VenditoreModel result) {

            if (listener != null) {
//
                listener.onLoginConToken(result);
            }
        }
    }

    public interface OnLoginVenditoreConTokenListener {
        void onLoginConToken(VenditoreModel venditoreModel);
    }


}

