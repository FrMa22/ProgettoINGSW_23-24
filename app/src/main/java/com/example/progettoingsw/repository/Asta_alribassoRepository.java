package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_alribasso_DTO;
import com.example.progettoingsw.backendAPI.Asta_alribassoService;
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
        new GetAste_alribassoNuoveTask(listener).execute();
    }
    public void getAste_alribassoCategoriaNomeBackend(ArrayList<String> nomiCategorie, Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener) {
        new GetAste_alribassoCategoriaNomeTask(listener).execute(nomiCategorie);
    }
    public void acquistaAsta_alribasso(Long idAsta, String emailAcquirente,String prezzoAttuale, Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener) {
        new AcquistaAsta_aliribassoTask(listener).execute(String.valueOf(idAsta),emailAcquirente,prezzoAttuale);
    }
    public void trovaAsta_alribasso(Long idAsta, Asta_alribassoRepository.OnTrovaAstaRibassoListener listener) {
        new TrovaAsta_alribassoTask(listener).execute(String.valueOf(idAsta));
    }
    public void verificaAstaRibassoInPreferiti(String indirizzo_email, Long idAsta, Asta_alribassoRepository.OnVerificaAstaRibassoInPreferitiListener listener) {
        new VerificaAsta_alribassoInPreferitiTask(listener).execute(indirizzo_email, String.valueOf(idAsta));
    }
    public void inserimentoAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_alribassoRepository.OnInserimentoAstaRibassoInPreferitiListener listener) {
        new InserimentoAsta_alribassoInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    public void eliminazioneAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_alribassoRepository.OnEliminazioneAstaRibassoInPreferitiListener listener) {
        new EliminazioneAsta_alribassoInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    public void getAsteRibassoPreferite(String indirizzo_email, Asta_alribassoRepository.OnGetAsteRibassoPreferiteListener listener) {
        new GetAste_alribassoPreferiteTask(listener).execute(indirizzo_email);
    }
    public void saveAsta_ribasso(Asta_alribassoModel astaRibassoModel, ArrayList<String> listaCategorie, Asta_alribassoRepository.OnInserimentoAstaRibassoListener listener) {
        new InserimentoAsta_ribassoTask(listener).execute(astaRibassoModel,listaCategorie);
    }
    public void getAstePerRicerca(String nome,ArrayList<String> nomiCategorie,String ordinamento, Asta_alribassoRepository.OnGetAstePerRicercaListener listener) {
        new Asta_alribassoRepository.GetAstePerRicercaTask(listener,nome, nomiCategorie,ordinamento).execute();
    }
    private static class GetAste_alribassoNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAsteRibassoNuoveListener listener;

        public GetAste_alribassoNuoveTask(Asta_alribassoRepository.OnGetAsteRibassoNuoveListener listener) {
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
                    ArrayList<Asta_alribasso_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        ArrayList<Asta_alribassoModel> listAsta_alribassoModel = new ArrayList<>();
                        for (Asta_alribasso_DTO astaAlribassoDto : list){
                            byte[] immagine = null;
                            if(astaAlribassoDto.getPath_immagine()!=null){immagine = base64ToByteArray(astaAlribassoDto.getPath_immagine());}
                            Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                    astaAlribassoDto.getId(),
                                    astaAlribassoDto.getNome(),
                                    astaAlribassoDto.getDescrizione(),
                                    immagine,
                                    astaAlribassoDto.getPrezzoBase(),
                                    astaAlribassoDto.getIntervalloDecrementale(),
                                    astaAlribassoDto.getIntervalloBase(),
                                    astaAlribassoDto.getDecrementoAutomaticoCifra(),
                                    astaAlribassoDto.getPrezzoMin(),
                                    astaAlribassoDto.getPrezzoAttuale(),
                                    astaAlribassoDto.getCondizione(),
                                    astaAlribassoDto.getId_venditore());
                            listAsta_alribassoModel.add(astaAlribassoModel);
                        }
                        return listAsta_alribassoModel;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_alribassoModel> result) {

            if (listener != null) {
                listener.OnGetAsteRibassoNuove(result);
            }
        }
    }
    public interface OnGetAsteRibassoNuoveListener {
        void OnGetAsteRibassoNuove(ArrayList<Asta_alribassoModel> list);
    }
    private static class GetAste_alribassoCategoriaNomeTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener;

        public GetAste_alribassoCategoriaNomeTask(Asta_alribassoRepository.OnGetAsteRibassoCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_alribassoModel> doInBackground(ArrayList<String>... params) {
            ArrayList<String> nomiCategorie = params[0];
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
            Call<ArrayList<Asta_alribasso_DTO>> call = service.getAste_alribassoCategoriaNome(nomiCategorie);

            try {
                Response<ArrayList<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {

                    ArrayList<Asta_alribasso_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {

                        ArrayList<Asta_alribassoModel> listAsta_alribassoModel = new ArrayList<>();
                        for (Asta_alribasso_DTO astaAlribassoDto : list){
                            byte[] immagine = null;
                            if(astaAlribassoDto.getPath_immagine()!=null){immagine = base64ToByteArray(astaAlribassoDto.getPath_immagine());}
                            Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                    astaAlribassoDto.getId(),
                                    astaAlribassoDto.getNome(),
                                    astaAlribassoDto.getDescrizione(),
                                    immagine,
                                    astaAlribassoDto.getPrezzoBase(),
                                    astaAlribassoDto.getIntervalloDecrementale(),
                                    astaAlribassoDto.getIntervalloBase(),
                                    astaAlribassoDto.getDecrementoAutomaticoCifra(),
                                    astaAlribassoDto.getPrezzoMin(),
                                    astaAlribassoDto.getPrezzoAttuale(),
                                    astaAlribassoDto.getCondizione(),
                                    astaAlribassoDto.getId_venditore());
                            listAsta_alribassoModel.add(astaAlribassoModel);
                        }
                        return listAsta_alribassoModel;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_alribassoModel> result) {

            if (listener != null) {
                listener.OnGetAsteRibassoCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteRibassoCategoriaNomeListener {
        void OnGetAsteRibassoCategoriaNome(ArrayList<Asta_alribassoModel> list);
    }
    private static class AcquistaAsta_aliribassoTask extends AsyncTask<String, Void, Integer> {
        private Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener;

        public AcquistaAsta_aliribassoTask(Asta_alribassoRepository.OnAcquistaAstaRibassoListener listener) {
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

                    Integer risposta = response.body();
                    if (risposta != null && risposta==1) {
                        return risposta;
                    }

                }

            } catch (IOException e) {

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
    private static class TrovaAsta_alribassoTask extends AsyncTask<String, Void, Asta_alribassoModel> {
        private Asta_alribassoRepository.OnTrovaAstaRibassoListener listener;

        public TrovaAsta_alribassoTask(Asta_alribassoRepository.OnTrovaAstaRibassoListener listener) {
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

                    Asta_alribasso_DTO astaRecuperata = response.body();
                    if (astaRecuperata != null) {
                        byte[] immagine = null;
                        if(astaRecuperata.getPath_immagine()!=null){immagine = base64ToByteArray(astaRecuperata.getPath_immagine());}
                        Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                astaRecuperata.getId(),
                                astaRecuperata.getNome(),
                                astaRecuperata.getDescrizione(),
                                immagine,
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

                        return null;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Asta_alribassoModel result) {

            if (listener != null) {
                listener.OnTrovaAstaRibasso(result);
            }
        }
    }
    public interface OnTrovaAstaRibassoListener {
        void OnTrovaAstaRibasso(Asta_alribassoModel list);
    }
    private static class VerificaAsta_alribassoInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_alribassoRepository.OnVerificaAstaRibassoInPreferitiListener listener;

        public VerificaAsta_alribassoInPreferitiTask(Asta_alribassoRepository.OnVerificaAstaRibassoInPreferitiListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String indirizzo_email = params[0];
            Long idAsta = Long.valueOf(params[1]);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<Integer> call = service.verificaAstaAlRibassoInPreferiti(indirizzo_email,idAsta);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {

                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
                    }else{

                        return null;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.OnVerificaAstaRibassoInPreferiti(result);
            }
        }
    }
    public interface OnVerificaAstaRibassoInPreferitiListener {
        void OnVerificaAstaRibassoInPreferiti(Integer numeroRecuperato);
    }
    private static class InserimentoAsta_alribassoInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_alribassoRepository.OnInserimentoAstaRibassoInPreferitiListener listener;

        public InserimentoAsta_alribassoInPreferitiTask(Asta_alribassoRepository.OnInserimentoAstaRibassoInPreferitiListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            Long idAsta = Long.valueOf(params[0]);
            String indirizzo_email = params[1];
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
            Call<Integer> call = service.inserimentoAstaInPreferiti(idAsta,indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {

                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
                    }else{

                        return null;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.OnInserimentoAstaRibassoInPreferiti(result);
            }
        }
    }
    public interface OnInserimentoAstaRibassoInPreferitiListener {
        void OnInserimentoAstaRibassoInPreferiti(Integer numeroRecuperato);
    }
    private static class EliminazioneAsta_alribassoInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_alribassoRepository.OnEliminazioneAstaRibassoInPreferitiListener listener;

        public EliminazioneAsta_alribassoInPreferitiTask(Asta_alribassoRepository.OnEliminazioneAstaRibassoInPreferitiListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            Long idAsta = Long.valueOf(params[0]);
            String indirizzo_email = params[1];
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
            Call<Integer> call = service.eliminazioneAstaInPreferiti(idAsta,indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {

                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
                    }else{

                        return null;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (listener != null) {
                listener.OnEliminazioneAstaRibassoInPreferiti(result);
            }
        }
    }
    public interface OnEliminazioneAstaRibassoInPreferitiListener {
        void OnEliminazioneAstaRibassoInPreferiti(Integer numeroRecuperato);
    }
    private static class GetAste_alribassoPreferiteTask extends AsyncTask<String, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAsteRibassoPreferiteListener listener;

        public GetAste_alribassoPreferiteTask(Asta_alribassoRepository.OnGetAsteRibassoPreferiteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_alribassoModel> doInBackground(String... params) {
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

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<ArrayList<Asta_alribasso_DTO>> call = service.getAsteRibassoPreferite(indirizzo_email);

            try {
                Response<ArrayList<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {

                    ArrayList<Asta_alribasso_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {

                        ArrayList<Asta_alribassoModel> listAsta_alribassoModel = new ArrayList<>();
                        for (Asta_alribasso_DTO astaAlribassoDto : list){
                            byte[] immagine = null;
                            if(astaAlribassoDto.getPath_immagine()!=null){immagine = base64ToByteArray(astaAlribassoDto.getPath_immagine());}
                            Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                    astaAlribassoDto.getId(),
                                    astaAlribassoDto.getNome(),
                                    astaAlribassoDto.getDescrizione(),
                                    immagine,
                                    astaAlribassoDto.getPrezzoBase(),
                                    astaAlribassoDto.getIntervalloDecrementale(),
                                    astaAlribassoDto.getIntervalloBase(),
                                    astaAlribassoDto.getDecrementoAutomaticoCifra(),
                                    astaAlribassoDto.getPrezzoMin(),
                                    astaAlribassoDto.getPrezzoAttuale(),
                                    astaAlribassoDto.getCondizione(),
                                    astaAlribassoDto.getId_venditore());
                           listAsta_alribassoModel.add(astaAlribassoModel);
                        }
                        return listAsta_alribassoModel;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_alribassoModel> result) {

            if (listener != null) {
                listener.OnGetAsteRibassoPreferite(result);
            }
        }
    }
    public interface OnGetAsteRibassoPreferiteListener {
        void OnGetAsteRibassoPreferite(ArrayList<Asta_alribassoModel> list);
    }
    private static class InserimentoAsta_ribassoTask extends AsyncTask<Object, Void, Long> {
        private Asta_alribassoRepository.OnInserimentoAstaRibassoListener listener;

        public InserimentoAsta_ribassoTask(Asta_alribassoRepository.OnInserimentoAstaRibassoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            Asta_alribassoModel astaRibassoModel = (Asta_alribassoModel) params[0];
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

            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            String immagine = null;
            if(astaRibassoModel.getPath_immagine()!=null){
                immagine = byteArrayToBase64(astaRibassoModel.getPath_immagine());
            }

            Asta_alribasso_DTO asta_alribasso_dto = new Asta_alribasso_DTO(astaRibassoModel.getNome(),astaRibassoModel.getDescrizione(),immagine,astaRibassoModel.getPrezzoBase()
                    ,astaRibassoModel.getIntervalloDecrementale(),astaRibassoModel.getIntervalloBase(),astaRibassoModel.getDecrementoAutomaticoCifra(),
                    astaRibassoModel.getPrezzoMin(),astaRibassoModel.getPrezzoBase(),
                    astaRibassoModel.getCondizione(),astaRibassoModel.getId_venditore());

           Call<Long> call = service.saveAsta_ribasso(asta_alribasso_dto, lista_categorie);

            try {
                Response<Long> response = call.execute();
                if (response.isSuccessful()) {

                    Long numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return 0L;
                    }else{

                        return 0L;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {

            if (listener != null) {
                listener.OnInserimentoAstaRibasso(result);
            }
        }
    }
    public interface OnInserimentoAstaRibassoListener {
        void OnInserimentoAstaRibasso(Long numeroRecuperato);
    }
    private static class GetAstePerRicercaTask extends AsyncTask<Void, Void, ArrayList<Asta_alribassoModel>> {
        private Asta_alribassoRepository.OnGetAstePerRicercaListener listener;
        private String nome;
        private ArrayList<String> nomiCategorie;
        private String ordinamento;

        public GetAstePerRicercaTask(Asta_alribassoRepository.OnGetAstePerRicercaListener listener, String nome, ArrayList<String> nomiCategorie, String ordinamento) {
            this.listener = listener;
            this.nome = nome;
            this.nomiCategorie = nomiCategorie;
            this.ordinamento = ordinamento;
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

            ArrayList<String> nomiCategorieParam;
            if (nomiCategorie == null || nomiCategorie.isEmpty()) {
                nomiCategorieParam = null; // Imposta a null se vuoto
            } else {
                nomiCategorieParam = nomiCategorie;
            }
            Asta_alribassoService service = retrofit.create(Asta_alribassoService.class);
            Call<ArrayList<Asta_alribasso_DTO>> call = service.getAstePerRicerca(nome,ordinamento,nomiCategorieParam);

            try {
                Response<ArrayList<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {

                    ArrayList<Asta_alribasso_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {

                        ArrayList<Asta_alribassoModel> listAsta_alribassoModel = new ArrayList<>();
                        for (Asta_alribasso_DTO astaAlribassoDto : list){
                            byte[] immagine = null;
                            if(astaAlribassoDto.getPath_immagine()!=null){immagine = base64ToByteArray(astaAlribassoDto.getPath_immagine());}
                            Asta_alribassoModel astaAlribassoModel = new Asta_alribassoModel(
                                    astaAlribassoDto.getId(),
                                    astaAlribassoDto.getNome(),
                                    astaAlribassoDto.getDescrizione(),
                                    immagine,
                                    astaAlribassoDto.getPrezzoBase(),
                                    astaAlribassoDto.getIntervalloDecrementale(),
                                    astaAlribassoDto.getIntervalloBase(),
                                    astaAlribassoDto.getDecrementoAutomaticoCifra(),
                                    astaAlribassoDto.getPrezzoMin(),
                                    astaAlribassoDto.getPrezzoAttuale(),
                                    astaAlribassoDto.getCondizione(),
                                    astaAlribassoDto.getId_venditore());
                            listAsta_alribassoModel.add(astaAlribassoModel);
                        }
                        return listAsta_alribassoModel;
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_alribassoModel> result) {

            if (listener != null) {
                listener.OnGetAstePerRicerca(result);
            }
        }
    }
    public interface OnGetAstePerRicercaListener {
        void OnGetAstePerRicerca(ArrayList<Asta_alribassoModel> list);
    }

    public static byte[] base64ToByteArray(String base64String) {
        // Rimuovi il prefisso "data:image/jpeg;base64," se presente
        String base64WithoutPrefix = base64String.replaceFirst("^data:image/[a-zA-Z]*;base64,", "");

        // Decodifica la stringa Base64 in un array di byte
        return Base64.decode(base64WithoutPrefix, Base64.DEFAULT);
    }
    // Funzione per convertire un array di byte in una stringa Base64
    public static String byteArrayToBase64(byte[] byteArray) {
        // Codifica l'array di byte in una stringa Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
