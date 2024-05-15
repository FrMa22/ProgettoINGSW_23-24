package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.SocialAcquirenteDTO;
import com.example.progettoingsw.DTO.SocialVenditoreDTO;
import com.example.progettoingsw.DTO.VenditoreDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.backendAPI.SocialAcquirenteService;
import com.example.progettoingsw.backendAPI.SocialVenditoreService;
import com.example.progettoingsw.backendAPI.VenditoreService;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrazioneRepository {

    public void registrazioneAcquirenteDoppioBackend(String email, OnRegistrazioneAcquirenteDoppioListener listener) {

        new RegistrazioneAcquirenteDoppioTask(listener).execute(email);
    }

    public void registrazioneVenditoreDoppioBackend(String email, OnRegistrazioneVenditoreDoppioListener listener) {

        new RegistrazioneVenditoreDoppioTask(listener).execute(email);
    }

    public void inserimentoAcquirente(AcquirenteModel acquirente, OnInserisciAcquirenteListener listener) {

        new InserisciAcquirenteTask(listener).execute(acquirente);
    }

    public void inserimentoVenditore(VenditoreModel venditore, OnInserisciVenditoreListener listener) {

        new InserisciVenditoreTask(listener).execute(venditore);
    }

    public void inserimentoSocialAcquirente(ArrayList<SocialAcquirenteModel> listaSocial, OnInserimentoSocialAcquirenteListener listener) {

        new inserimentoSocialAcquirenteTask(listener).execute(listaSocial);
    }

    public void inserimentoSocialVenditore(ArrayList<SocialVenditoreModel> listaSocial, OnInserimentoSocialVenditoreListener listener) {

        new inserimentoSocialVenditoreTask(listener).execute(listaSocial);
    }

    public void saveCategorieAcquirente(String email, ArrayList<String> listaCategorie, RegistrazioneRepository.OnInserimentoCategorieAcquirente listener) {

        new inserimentoCategorieAcquirenteTask(listener).execute(email, listaCategorie);
    }

    public void saveCategorieVenditore(String email, ArrayList<String> listaCategorie, RegistrazioneRepository.OnInserimentoCategorieVenditore listener) {

        new inserimentoCategorieVenditoreTask(listener).execute(email, listaCategorie);
    }

    private static class RegistrazioneAcquirenteDoppioTask extends AsyncTask<String, Void, AcquirenteModel> {
        private RegistrazioneRepository.OnRegistrazioneAcquirenteDoppioListener listener;

        public RegistrazioneAcquirenteDoppioTask(RegistrazioneRepository.OnRegistrazioneAcquirenteDoppioListener listener) {
            this.listener = listener;
        }

        @Override
        protected AcquirenteModel doInBackground(String... params) {
            String email = params[0];
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            AcquirenteService service = retrofit.create(AcquirenteService.class);
            Call<AcquirenteDTO> call = service.registrazioneAcquirenteDoppio(email);

            try {
                Response<AcquirenteDTO> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {


                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(), acquirenteDTO.getIndirizzo_email(), acquirenteDTO.getPassword(), acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
                    }

                }

            } catch (
                    EOFException e) {
                Log.d("RegistrazioneAcquirenteDoppioTask", "catch di EOFException");
            } catch (
                    IOException e) {
                Log.d("RegistrazioneAcquirenteDoppioTask", "catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(AcquirenteModel result) {

            if (listener != null) {
                listener.ricercaDoppia(result);
            }
        }
    }

    private static class RegistrazioneVenditoreDoppioTask extends AsyncTask<String, Void, VenditoreModel> {
        private RegistrazioneRepository.OnRegistrazioneVenditoreDoppioListener listener;

        public RegistrazioneVenditoreDoppioTask(RegistrazioneRepository.OnRegistrazioneVenditoreDoppioListener listener) {
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
            Call<VenditoreDTO> call = service.registrazioneVenditoreDoppio(email);

            try {
                Response<VenditoreDTO> response = call.execute();
                if (response.body() != null && response.isSuccessful()) {


                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {


                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(), venditoreDTO.getIndirizzo_email(), venditoreDTO.getPassword(), venditoreDTO.getBio(), venditoreDTO.getAreageografica(), venditoreDTO.getLink());
                    }

                }

            } catch (
                    EOFException e) {
                Log.d("RegistrazioneVenditoreDoppioTask", "catch di EOFException");
                //e.printStackTrace();
            } catch (
                    IOException e) {
                //e.printStackTrace();
                Log.d("RegistrazioneVenditoreDoppioTask", "catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(VenditoreModel result) {

            if (listener != null) {
                listener.ricercaDoppia(result);
            }
        }
    }

    private static class InserisciVenditoreTask extends AsyncTask<Object, Void, Long> {
        private RegistrazioneRepository.OnInserisciVenditoreListener listener;

        public InserisciVenditoreTask(RegistrazioneRepository.OnInserisciVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            VenditoreModel venditoreModel = (VenditoreModel) params[0];

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            VenditoreService service = retrofit.create(VenditoreService.class);

            VenditoreDTO venditoreDTO = new VenditoreDTO(venditoreModel.getNome(), venditoreModel.getCognome(), venditoreModel.getIndirizzo_email(), venditoreModel.getPassword(), venditoreModel.getBio(), venditoreModel.getAreageografica(), venditoreModel.getLink());

            Call<Long> call = service.saveVenditore(venditoreDTO);

            try {
                Response<Long> response = call.execute();
                if (response.isSuccessful()) {

                    Long numeroRecuperato = response.body();
                    if (numeroRecuperato != null) {
                        return 0L;
                    } else {

                        return 1L;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return 1L;
        }

        @Override
        protected void onPostExecute(Long result) {

            if (listener != null) {
                listener.confermaVenditore(result);
            }
        }
    }

    private static class InserisciAcquirenteTask extends AsyncTask<Object, Void, Long> {
        private RegistrazioneRepository.OnInserisciAcquirenteListener listener;

        public InserisciAcquirenteTask(RegistrazioneRepository.OnInserisciAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            AcquirenteModel acquirenteModel = (AcquirenteModel) params[0];

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            AcquirenteService service = retrofit.create(AcquirenteService.class);
            AcquirenteDTO acquirenteDTO = new AcquirenteDTO(acquirenteModel.getNome(), acquirenteModel.getCognome(), acquirenteModel.getIndirizzo_email(), acquirenteModel.getPassword(), acquirenteModel.getBio(), acquirenteModel.getAreageografica(), acquirenteModel.getLink());

            Call<Long> call = service.saveAcquirente(acquirenteDTO);

            try {
                Response<Long> response = call.execute();
                if (response.isSuccessful()) {

                    Long numeroRecuperato = response.body();
                    if (numeroRecuperato != null) {
                        return 0L;
                    } else {

                        return 1L;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return 1L;
        }

        @Override
        protected void onPostExecute(Long result) {

            if (listener != null) {
                listener.confermaAcquirente(result);
            }
        }
    }

    private static class inserimentoSocialAcquirenteTask extends AsyncTask<Object, Void, Void> {
        private RegistrazioneRepository.OnInserimentoSocialAcquirenteListener listener;

        public inserimentoSocialAcquirenteTask(RegistrazioneRepository.OnInserimentoSocialAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Object... params) {
            ArrayList<SocialAcquirenteModel> listaSocial = (ArrayList<SocialAcquirenteModel>) params[0];
            // Effettua l'operazione di rete qui...
            // Restituisci il risultato
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            SocialAcquirenteService service = retrofit.create(SocialAcquirenteService.class);
            ArrayList<SocialAcquirenteDTO> listaSocialDTO = new ArrayList<SocialAcquirenteDTO>();
            for (SocialAcquirenteModel socialAcquirenteModel : listaSocial) {
                String nome = socialAcquirenteModel.getNome();
                String link = socialAcquirenteModel.getLink();
                String email = socialAcquirenteModel.getIndirizzo_email();
                SocialAcquirenteDTO socialAcquirenteDTO = new SocialAcquirenteDTO(nome, link, email);
                listaSocialDTO.add(socialAcquirenteDTO);
            }


            Call<Void> call = service.insertSocialAcquirenteRegistrazione(listaSocialDTO);

            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {

                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

    }
    private static class inserimentoSocialVenditoreTask extends AsyncTask<Object, Void, Void> {
        private RegistrazioneRepository.OnInserimentoSocialVenditoreListener listener;

        public inserimentoSocialVenditoreTask(RegistrazioneRepository.OnInserimentoSocialVenditoreListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Object... params) {
            ArrayList<SocialVenditoreModel> listaSocial = (ArrayList<SocialVenditoreModel>) params[0];
            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            SocialVenditoreService service = retrofit.create(SocialVenditoreService.class);
            ArrayList<SocialVenditoreDTO> listaSocialDTO = new ArrayList<SocialVenditoreDTO>();
            for (SocialVenditoreModel socialVenditoreModel : listaSocial) {
                String nome = socialVenditoreModel.getNome();
                String link = socialVenditoreModel.getLink();
                String email = socialVenditoreModel.getIndirizzo_email();
                SocialVenditoreDTO socialVenditoreDTO = new SocialVenditoreDTO(nome, link, email);
                listaSocialDTO.add(socialVenditoreDTO);
            }


            Call<Void> call = service.insertSocialVenditoreRegistrazione(listaSocialDTO);

            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {

                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

    }
    private static class inserimentoCategorieAcquirenteTask extends AsyncTask<Object, Void, Integer> {
        private RegistrazioneRepository.OnInserimentoCategorieAcquirente listener;

        public inserimentoCategorieAcquirenteTask(RegistrazioneRepository.OnInserimentoCategorieAcquirente listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(Object... params) {
            String email = (String) params[0];
            ArrayList<String> lista_categorie = (ArrayList<String>) params[1];
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


            Call<Void> call = service.saveCategorieAcquirente(email, lista_categorie);

            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {

                    return 1;
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.categorieInseriteAcquirente(result);
            }
        }
    }

    public interface OnInserimentoCategorieAcquirente {
        void categorieInseriteAcquirente(Integer valore);
    }
    private static class inserimentoCategorieVenditoreTask extends AsyncTask<Object, Void, Integer> {
        private RegistrazioneRepository.OnInserimentoCategorieVenditore listener;

        public inserimentoCategorieVenditoreTask(RegistrazioneRepository.OnInserimentoCategorieVenditore listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(Object... params) {
            String email = (String) params[0];
            ArrayList<String> lista_categorie = (ArrayList<String>) params[1];
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


            Call<Void> call = service.saveCategorieVenditore(email, lista_categorie);

            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {

                    return 1;
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.categorieInseriteVenditore(result);
            }
        }
    }



    public interface OnInserimentoCategorieVenditore {
        void categorieInseriteVenditore(Integer result);
    }

    public interface OnInserimentoSocialVenditoreListener {
        void socialInseritoVenditore();
    }

    public interface OnInserimentoSocialAcquirenteListener {
        void socialInseritoAcquirente();
    }

    public interface OnRegistrazioneAcquirenteDoppioListener {
        void ricercaDoppia(AcquirenteModel acquirenteModel);
    }

    public interface OnRegistrazioneVenditoreDoppioListener {
        void ricercaDoppia(VenditoreModel venditoreModel);
    }

    public interface OnInserisciAcquirenteListener {
        void confermaAcquirente(Long check);
    }

    public interface OnInserisciVenditoreListener {
        void confermaVenditore(Long check);
    }
}

