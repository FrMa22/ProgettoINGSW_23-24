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
        System.out.println("entrato in loginAcquirente backend");
        new LoginAcquirenteTask(listener).execute(email, password);
    }
    public void recuperaCategorieAcquirenteBackend(String email, OnRecuperaCategorieAcquirenteListener listener) {
        System.out.println("entrato in loginAcquirente backend");
        new RecuperaCategorieAcquirenteTask(listener).execute(email);
    }
    public void loginVenditoreBackend(String email, String password, OnLoginVenditoreListener listener) {
        System.out.println("entrato in loginVenditore backend");
        new LoginVenditoreTask(listener).execute(email, password);
    }
    public void recuperaCategorieVenditoreBackend(String email, OnRecuperaCategorieVenditoreListener listener) {
        System.out.println("entrato in loginAcquirente backend");
        new RecuperaCategorieVenditoreTask(listener).execute(email);
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
                if (response.body() != null && response.isSuccessful()) {
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {
                        System.out.println("acquirente dto non null");
                        System.out.println("valori del acquirente: " +" nome:" + acquirenteDTO.getNome() + " cognome:"+acquirenteDTO.getCognome() + " link:"+acquirenteDTO.getLink() +" areageografica:" +acquirenteDTO.getAreageografica() + " email:"+ acquirenteDTO.getIndirizzo_email() + " password:" +acquirenteDTO.getPassword());
                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(),email, password, acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
                    }
                    System.out.println("acquirente dto null");
                }
                System.out.println("response non successful");
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
            System.out.println("on post execute loginAcquirente" + result);
            if (listener != null) {
//               System.out.println("valori del acquirente: " +" nome:" + result.getNome() + " cognome:"+result.getCognome() + " link:"+result.getLink() +" areageografica:" +result.getAreageografica() + " email:" + result.getIndirizzo_email() + result.getPassword());
                listener.onLogin(result);
            }
        }
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
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    ArrayList<String> listaCategorie = response.body();
                    return listaCategorie;
                }
                System.out.println("response non successful");
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
            System.out.println("on post execute RecuperaCategorieAcquirenteTask" + result);
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
                if (response.body() != null && response.isSuccessful()) {
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {
                        System.out.println("venditore dto non null");
                        System.out.println("valori del venditore: " +" nome:" + venditoreDTO.getNome() + " cognome:"+venditoreDTO.getCognome() + " link:"+venditoreDTO.getLink() +" areageografica:" +venditoreDTO.getAreageografica() + " email:"+ venditoreDTO.getIndirizzo_email() + " password:" +venditoreDTO.getPassword());
                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(),email, password, venditoreDTO.getBio(), venditoreDTO.getAreageografica(), venditoreDTO.getLink());
                    }
                    System.out.println("venditore dto null");
                }
                System.out.println("response non successful");
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
            System.out.println("on post execute loginVenditore" + result);
            if (listener != null) {
//                System.out.println("valori del venditore: " +" nome:" + result.getNome() + " cognome:"+result.getCognome() + " link:"+result.getLink() +" areageografica:" +result.getAreageografica() + " email:" + result.getIndirizzo_email() + result.getPassword());
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
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    ArrayList<String> listaCategorie = response.body();
                    return listaCategorie;
                }
                System.out.println("response non successful");
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
            System.out.println("on post execute RecuperaCategorieVenditoreTask" + result);
            if (listener != null) {
                listener.onRecuperaCategorieVenditore(result);
            }
        }
    }
    public interface OnRecuperaCategorieVenditoreListener {
        void onRecuperaCategorieVenditore(ArrayList<String> listaCategorie);
    }
}

