package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.NotificheAcquirente_DTO;
import com.example.progettoingsw.DTO.NotificheVenditore_DTO;
import com.example.progettoingsw.backendAPI.NotificheService;
import com.example.progettoingsw.model.NotificheAcquirenteModel;
import com.example.progettoingsw.model.NotificheVenditoreModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificheRepository {

    public void getNotificheAcquirente(String indirizzo_email, NotificheRepository.OnGetNotificheAcquirenteListener listener) {
        System.out.println("entrato in getNotificheAcquirente");
        new NotificheRepository.GetNotificheAcquirenteTask(listener).execute(indirizzo_email);
    }
    public void getNotificheVenditore(String indirizzo_email, NotificheRepository.OnGetNotificheVenditoreListener listener) {
        System.out.println("entrato in getNotificheVenditore");
        new NotificheRepository.GetNotificheVenditoreTask(listener).execute(indirizzo_email);
    }
    public void deleteNotificheAcquirente(Long id, NotificheRepository.OnDeleteNotificheAcquirenteListener listener) {
        System.out.println("entrato in deleteNotificheAcquirente");
        new NotificheRepository.DeleteNotificheAcquirenteTask(listener).execute(String.valueOf(id));
    }
    public void deleteNotificheVenditore(Long id, NotificheRepository.OnDeleteNotificheVenditoreListener listener) {
        System.out.println("entrato in deleteNotificheVenditore");
        new NotificheRepository.DeleteNotificheVenditoreTask(listener).execute(String.valueOf(id));
    }
    public void getNumeroNotificheAcquirente(String indirizzo_email, NotificheRepository.OnGetNumeroNotificheAcquirenteListener listener) {
        System.out.println("entrato in getNumeroNotificheAcquirente");
        new NotificheRepository.GetNumeroNotificheAcquirenteTask(listener).execute(indirizzo_email);
    }
    public void getNumeroNotificheVenditore(String indirizzo_email, NotificheRepository.OnGetNumeroNotificheVenditoreListener listener) {
        System.out.println("entrato in getNumeroNotificheVenditore");
        new NotificheRepository.GetNumeroNotificheVenditoreTask(listener).execute(indirizzo_email);
    }
    private static class GetNotificheAcquirenteTask extends AsyncTask<String, Void, ArrayList<NotificheAcquirenteModel>> {
        private NotificheRepository.OnGetNotificheAcquirenteListener listener;

        public GetNotificheAcquirenteTask(NotificheRepository.OnGetNotificheAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<NotificheAcquirenteModel> doInBackground(String... params) {

            String indirizzo_email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<ArrayList<NotificheAcquirente_DTO>> call = service.getNotificheAcquirente(indirizzo_email);

            try {
                Response<ArrayList<NotificheAcquirente_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<NotificheAcquirente_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di notifiche acquirente dto non null");
                        ArrayList<NotificheAcquirenteModel> listNotificheAcquirenteModel = new ArrayList<>();
                        for (NotificheAcquirente_DTO notificheAcquirenteDTO : list){
                            NotificheAcquirenteModel notificheAcquirenteModel = new NotificheAcquirenteModel(
                                    notificheAcquirenteDTO.getId(),
                                    notificheAcquirenteDTO.getTitolo(),
                                    notificheAcquirenteDTO.getCommento(),
                                    notificheAcquirenteDTO.getIdAcquirente());
                            //stampa dei valori dell asta
                            Log.d("notifiche acquirente" ," valori " + notificheAcquirenteDTO.getId() + notificheAcquirenteDTO.getTitolo() + notificheAcquirenteDTO.getCommento() + notificheAcquirenteDTO.getIdAcquirente());
                            listNotificheAcquirenteModel.add(notificheAcquirenteModel);
                        }
                        return listNotificheAcquirenteModel;
                    }
                    System.out.println("lista di notifiche acquirente dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NotificheAcquirenteModel> result) {
            System.out.println("on post execute GetNotificheAcquirenteTask" + result);
            if (listener != null) {
                listener.OnGetNotificheAcquirente(result);
            }
        }
    }
    public interface OnGetNotificheAcquirenteListener {
        void OnGetNotificheAcquirente(ArrayList<NotificheAcquirenteModel> list);
    }
    private static class GetNotificheVenditoreTask extends AsyncTask<String, Void, ArrayList<NotificheVenditoreModel>> {
        private NotificheRepository.OnGetNotificheVenditoreListener listener;

        public GetNotificheVenditoreTask(NotificheRepository.OnGetNotificheVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<NotificheVenditoreModel> doInBackground(String... params) {

            String indirizzo_email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<ArrayList<NotificheVenditore_DTO>> call = service.getNotificheVenditore(indirizzo_email);

            try {
                Response<ArrayList<NotificheVenditore_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<NotificheVenditore_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di notifiche Venditore dto non null");
                        ArrayList<NotificheVenditoreModel> listNotificheVenditoreModel = new ArrayList<>();
                        for (NotificheVenditore_DTO notificheVenditoreDTO : list){
                            NotificheVenditoreModel notificheVenditoreModel = new NotificheVenditoreModel(
                                    notificheVenditoreDTO.getId(),
                                    notificheVenditoreDTO.getTitolo(),
                                    notificheVenditoreDTO.getCommento(),
                                    notificheVenditoreDTO.getIdVenditore());
                            //stampa dei valori dell asta
                            Log.d("notifiche acquirente" ," valori " + notificheVenditoreDTO.getId() + notificheVenditoreDTO.getTitolo() + notificheVenditoreDTO.getCommento() + notificheVenditoreDTO.getIdVenditore());
                            listNotificheVenditoreModel.add(notificheVenditoreModel);
                        }
                        return listNotificheVenditoreModel;
                    }
                    System.out.println("lista di notifiche acquirente dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NotificheVenditoreModel> result) {
            System.out.println("on post execute GetNotificheVenditoreTask" + result);
            if (listener != null) {
                listener.OnGetNotificheVenditore(result);
            }
        }
    }
    public interface OnGetNotificheVenditoreListener {
        void OnGetNotificheVenditore(ArrayList<NotificheVenditoreModel> list);
    }
    private static class DeleteNotificheAcquirenteTask extends AsyncTask<String, Void, Integer> {
        private NotificheRepository.OnDeleteNotificheAcquirenteListener listener;

        public DeleteNotificheAcquirenteTask(NotificheRepository.OnDeleteNotificheAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            Long id = Long.parseLong(params[0]);

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<Integer> call = service.deleteNotificheAcquirente(id);

            try {
                Response<Integer> result = call.execute();
                return 1;
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute DeleteNotificheAcquirenteTask" + result);
            if (listener != null) {
                listener.OnDeleteNotificheAcquirente(result);
            }
        }
    }
    public interface OnDeleteNotificheAcquirenteListener {
        void OnDeleteNotificheAcquirente(Integer result);
    }
    private static class DeleteNotificheVenditoreTask extends AsyncTask<String, Void, Integer> {
        private NotificheRepository.OnDeleteNotificheVenditoreListener listener;

        public DeleteNotificheVenditoreTask(NotificheRepository.OnDeleteNotificheVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            Long id = Long.parseLong(params[0]);

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<Integer> call = service.deleteNotificheVenditore(id);

            try {
                Response<Integer> result = call.execute();
                return 1;
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute DeleteNotificheVenditoreTask" + result);
            if (listener != null) {
                listener.OnDeleteNotificheVenditore(result);
            }
        }
    }
    public interface OnDeleteNotificheVenditoreListener {
        void OnDeleteNotificheVenditore(Integer result);
    }
    private static class GetNumeroNotificheAcquirenteTask extends AsyncTask<String, Void, Integer> {
        private NotificheRepository.OnGetNumeroNotificheAcquirenteListener listener;

        public GetNumeroNotificheAcquirenteTask(NotificheRepository.OnGetNumeroNotificheAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            String indirizzo_email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<Integer> call = service.getNumeroNotificheAcquirente(indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer conteggio = response.body();
                    Log.d("GetNumeroNotificheAcquirenteTask","numero notifiche recuperato: " + conteggio);
                    return conteggio;
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetNotificheAcquirenteTask" + result);
            if (listener != null) {
                listener.OnGetNumeroNotificheAcquirente(result);
            }
        }
    }
    public interface OnGetNumeroNotificheAcquirenteListener {
        void OnGetNumeroNotificheAcquirente(Integer result);
    }
    private static class GetNumeroNotificheVenditoreTask extends AsyncTask<String, Void, Integer> {
        private NotificheRepository.OnGetNumeroNotificheVenditoreListener listener;

        public GetNumeroNotificheVenditoreTask(NotificheRepository.OnGetNumeroNotificheVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            String indirizzo_email = params[0];

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            NotificheService service = retrofit.create(NotificheService.class);
            Call<Integer> call = service.getNumeroNotificheVenditore(indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer conteggio = response.body();
                    Log.d("GetNumeroNotificheVenditoreTask","numero notifiche recuperato: " + conteggio);
                    return conteggio;
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetNotificheVenditoreTask" + result);
            if (listener != null) {
                listener.OnGetNumeroNotificheVenditore(result);
            }
        }
    }
    public interface OnGetNumeroNotificheVenditoreListener {
        void OnGetNumeroNotificheVenditore(Integer result);
    }
}
