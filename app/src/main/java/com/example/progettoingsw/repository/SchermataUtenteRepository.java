package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.VenditoreDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.backendAPI.VenditoreService;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.io.EOFException;
import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchermataUtenteRepository {

    public void getAcquirenteByIndirizzo_email(String email, SchermataUtenteRepository.OnGetAcquirenteByIndirizzo_emailListener listener) {
        new SchermataUtenteRepository.GetAcquirenteByIndirizzo_emailTask(listener).execute(email);
    }
    public void getVenditoreByIndirizzo_email(String email, SchermataUtenteRepository.OnGetVenditoreByIndirizzo_emailListener listener) {
        new SchermataUtenteRepository.GetVenditoreByIndirizzo_emailTask(listener).execute(email);
    }

    private static class GetAcquirenteByIndirizzo_emailTask extends AsyncTask<String, Void, AcquirenteModel> {
        private SchermataUtenteRepository.OnGetAcquirenteByIndirizzo_emailListener listener;

        public GetAcquirenteByIndirizzo_emailTask(SchermataUtenteRepository.OnGetAcquirenteByIndirizzo_emailListener listener) {
            this.listener = listener;
        }

        @Override
        protected AcquirenteModel doInBackground(String... params) {
            String email = params[0];

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            AcquirenteService service = retrofit.create(AcquirenteService.class);
            Call<AcquirenteDTO> call = service.getAcquirenteByIndirizzo_email(email);

            try {
                Response<AcquirenteDTO> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {
                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {
                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(), acquirenteDTO.getIndirizzo_email(), acquirenteDTO.getPassword(), acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
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
                listener.onGetAcquirenteByIndirizzo_emailListener(result);
            }
        }
    }
    public interface OnGetAcquirenteByIndirizzo_emailListener {
        void onGetAcquirenteByIndirizzo_emailListener(AcquirenteModel acquirenteModel);
    }

    private static class GetVenditoreByIndirizzo_emailTask extends AsyncTask<String, Void, VenditoreModel> {
        private SchermataUtenteRepository.OnGetVenditoreByIndirizzo_emailListener listener;

        public GetVenditoreByIndirizzo_emailTask(SchermataUtenteRepository.OnGetVenditoreByIndirizzo_emailListener listener) {
            this.listener = listener;
        }

        @Override
        protected VenditoreModel doInBackground(String... params) {
            String email = params[0];

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            VenditoreService service = retrofit.create(VenditoreService.class);
            Call<VenditoreDTO> call = service.getVenditoreByIndirizzo_email(email);

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
                listener.onGetVenditoreByIndirizzo_emailListener(result);
            }
        }
    }

    public interface OnGetVenditoreByIndirizzo_emailListener {
        void onGetVenditoreByIndirizzo_emailListener(VenditoreModel venditoreModel);
    }

}
