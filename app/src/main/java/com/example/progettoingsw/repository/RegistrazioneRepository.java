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
    Repository repository = Repository.getInstance();

    public void registrazioneAcquirenteDoppioBackend(String email, OnRegistrazioneAcquirenteDoppioListener listener) {
        System.out.println("entrato in registrazioneAcquirente backend");
        new RegistrazioneAcquirenteDoppioTask(listener).execute(email);
    }

    public void registrazioneVenditoreDoppioBackend(String email, OnRegistrazioneVenditoreDoppioListener listener) {
        System.out.println("entrato in registrazioneVenditore backend");
        new RegistrazioneVenditoreDoppioTask(listener).execute(email);
    }

    public void inserimentoAcquirente(AcquirenteModel acquirente, OnInserisciAcquirenteListener listener) {
        System.out.println("entrato in registrazioneAcquirenteCompleta backend");
        new InserisciAcquirenteTask(listener).execute(acquirente);
    }

    public void inserimentoVenditore(VenditoreModel venditore, OnInserisciVenditoreListener listener) {
        System.out.println("entrato in registrazioneVenditoreCompleta backend");
        new InserisciVenditoreTask(listener).execute(venditore);
    }

    public void inserimentoSocialAcquirente(ArrayList<SocialAcquirenteModel> listaSocial, OnInserimentoSocialAcquirenteListener listener) {
        System.out.println("entrato in inserimento social acquirente");
        new inserimentoSocialAcquirenteTask(listener).execute(listaSocial);
    }

    public void inserimentoSocialVenditore(ArrayList<SocialVenditoreModel> listaSocial, OnInserimentoSocialVenditoreListener listener) {
        System.out.println("entrato in inserimento social Venditore");
        new inserimentoSocialVenditoreTask(listener).execute(listaSocial);
    }

    public void saveCategorieAcquirente(String email, ArrayList<String> listaCategorie, RegistrazioneRepository.OnInserimentoCategorieAcquirente listener) {
        System.out.println("entrato in saveCategorie acquirente");
        new inserimentoCategorieAcquirenteTask(listener).execute(email, listaCategorie);
    }

    public void saveCategorieVenditore(String email, ArrayList<String> listaCategorie, RegistrazioneRepository.OnInserimentoCategorieVenditore listener) {
        System.out.println("entrato in saveCategorie venditore");
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
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    AcquirenteDTO acquirenteDTO = response.body();
                    if (acquirenteDTO != null) {
                        System.out.println("acquirente dto non null");
                        System.out.println("valori del acquirente: " + acquirenteDTO.getNome() + acquirenteDTO.getCognome());
                        return new AcquirenteModel(acquirenteDTO.getNome(), acquirenteDTO.getCognome(), acquirenteDTO.getIndirizzo_email(), acquirenteDTO.getPassword(), acquirenteDTO.getBio(), acquirenteDTO.getAreageografica(), acquirenteDTO.getLink());
                    }
                    System.out.println("acquirente dto null");
                }
                System.out.println("response non successful");
            } catch (
                    EOFException e) {
                Log.d("RegistrazioneAcquirenteDoppioTask", "catch di EOFException");
                //e.printStackTrace();
            } catch (
                    IOException e) {
                //e.printStackTrace();
                Log.d("RegistrazioneAcquirenteDoppioTask", "catch di IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(AcquirenteModel result) {
            System.out.println("on post execute registrazioneAcquirenteDoppia" + result);
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
                    System.out.println("valore di response " + response.body());
                    System.out.println("response successful");
                    VenditoreDTO venditoreDTO = response.body();
                    if (venditoreDTO != null) {
                        System.out.println("venditore dto non null");
                        System.out.println("valori del venditore: " + venditoreDTO.getNome() + venditoreDTO.getCognome());
                        return new VenditoreModel(venditoreDTO.getNome(), venditoreDTO.getCognome(), venditoreDTO.getIndirizzo_email(), venditoreDTO.getPassword(), venditoreDTO.getBio(), venditoreDTO.getAreageografica(), venditoreDTO.getLink());
                    }
                    System.out.println("venditore dto null");
                }
                System.out.println("response non successful");
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
            System.out.println("on post execute registrazioneAcquirenteDoppia" + result);
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
                    System.out.println("response successful");
                    Long numeroRecuperato = response.body();
                    if (numeroRecuperato != null) {
                        return 0L;
                    } else {
                        System.out.println("venditore dto null");
                        return 1L;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return 1L;
        }

        @Override
        protected void onPostExecute(Long result) {
            System.out.println("on post execute inserimento venditore" + result);
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
                    System.out.println("response successful");
                    Long numeroRecuperato = response.body();
                    if (numeroRecuperato != null) {
                        return 0L;
                    } else {
                        System.out.println("acquirente dto null");
                        return 1L;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return 1L;
        }

        @Override
        protected void onPostExecute(Long result) {
            System.out.println("on post execute inserimento acquirente" + result);
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
                    System.out.println("response successful");
                }
            } catch (IOException e) {
                System.out.println("exception IOEXC");
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
                    System.out.println("response successful");
                }
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

    }
    private static class inserimentoCategorieAcquirenteTask extends AsyncTask<Object, Void, Void> {
        private RegistrazioneRepository.OnInserimentoCategorieAcquirente listener;

        public inserimentoCategorieAcquirenteTask(RegistrazioneRepository.OnInserimentoCategorieAcquirente listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Object... params) {
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
                    System.out.println("response successful");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

    }

    private static class inserimentoCategorieVenditoreTask extends AsyncTask<Object, Void, Void> {
        private RegistrazioneRepository.OnInserimentoCategorieVenditore listener;

        public inserimentoCategorieVenditoreTask(RegistrazioneRepository.OnInserimentoCategorieVenditore listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Object... params) {
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
                    System.out.println("response successful");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

    }

        public interface OnInserimentoCategorieAcquirente {
            void categorieInseriteAcquirente();
        }

        public interface OnInserimentoCategorieVenditore {
            void categorieInseriteVenditore();
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

