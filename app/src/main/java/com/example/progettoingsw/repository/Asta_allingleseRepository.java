package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.backendAPI.Asta_allingleseService;
import com.example.progettoingsw.backendAPI.Asta_alribassoService;
import com.example.progettoingsw.model.Asta_allingleseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asta_allingleseRepository {

    public void getAste_allingleseScadenzaRecenteBackend( Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener) {
        System.out.println("entrato in getAste_allingleseScadenzaRecenteBackend");
        new Asta_allingleseRepository.GetAsteScadenzaRecenteTask(listener).execute();
    }
    public void getAste_allingleseNuoveBackend( Asta_allingleseRepository.OnGetAsteNuoveListener listener) {
        System.out.println("entrato in getAste_allingleseNuoveBackend");
        new Asta_allingleseRepository.GetAsteNuoveTask(listener).execute();
    }
    public void getAste_allingleseCategoriaNomeBackend(String nomeCategoria, Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener) {
        System.out.println("entrato in getAste_allingleseCategoriaNomeBackend");
        new Asta_allingleseRepository.GetAsteCategoriaNomeTask(listener).execute(nomeCategoria);
    }
    public void partecipaAsta_allinglese(Long idAsta, String emailAcquirente,String offerta,String tempoOfferta, String stato, Asta_allingleseRepository.OnPartecipazioneAstaIngleseListener listener) {
        System.out.println("entrato in partecipaAsta_allinglese");
        new Asta_allingleseRepository.partecipaAsta_allingleseTask(listener).execute(String.valueOf(idAsta),emailAcquirente,offerta, tempoOfferta, stato);
    }
    public void trovaAsta_allinglese(Long idAsta, Asta_allingleseRepository.OnTrovaAstaIngleseListener listener) {
        System.out.println("entrato in trovaAsta_allinglese");
        new Asta_allingleseRepository.trovaAsta_allingleseTask(listener).execute(String.valueOf(idAsta));
    }
    public void verificaAstaIngleseInPreferiti(String indirizzo_email, Long idAsta, Asta_allingleseRepository.OnVerificaAstaIngleseInPreferitiListener listener) {
        System.out.println("entrato in verificaAstaIngleseInPreferiti");
        new Asta_allingleseRepository.verificaAsta_allingleseInPreferitiTask(listener).execute(indirizzo_email, String.valueOf(idAsta));
    }
    public void inserimentoAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_allingleseRepository.OnInserimentoAstaIngleseInPreferitiListener listener) {
        System.out.println("entrato in inserimentoAstaInPreferiti");
        new Asta_allingleseRepository.inserimentoAsta_allingleseInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    public void eliminazioneAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_allingleseRepository.OnEliminazioneAstaIngleseInPreferitiListener listener) {
        System.out.println("entrato in eliminazioneAstaInPreferiti");
        new Asta_allingleseRepository.eliminazioneAsta_allingleseInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    private static class GetAsteScadenzaRecenteTask extends AsyncTask<Void, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener;

        public GetAsteScadenzaRecenteTask(Asta_allingleseRepository.OnGetAsteScadenzaRecenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(Void... voids) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseScadenzaRecente();

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
                    }
                    System.out.println("lista di aste inglesi dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnGetAsteScadenzaRecente(result);
            }
        }
    }
    public interface OnGetAsteScadenzaRecenteListener {
        void OnGetAsteScadenzaRecente(ArrayList<Asta_allingleseModel> list);
    }
    private static class GetAsteNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteNuoveListener listener;

        public GetAsteNuoveTask(Asta_allingleseRepository.OnGetAsteNuoveListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(Void... voids) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseNuove();

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
                    }
                    System.out.println("lista di aste inglesi dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteNuoveTask" + result);
            if (listener != null) {
                listener.OnGetAsteNuove(result);
            }
        }
    }
    public interface OnGetAsteNuoveListener {
        void OnGetAsteNuove(ArrayList<Asta_allingleseModel> list);
    }
    private static class GetAsteCategoriaNomeTask extends AsyncTask<String, Void, ArrayList<Asta_allingleseModel>> {
        private Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener;

        public GetAsteCategoriaNomeTask(Asta_allingleseRepository.OnGetAsteCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_allingleseModel> doInBackground(String... params) {

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

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<ArrayList<Asta_allinglese_DTO>> call = service.getAste_allingleseCategoriaNome(nomeCategoria);

            try {
                Response<ArrayList<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_allinglese_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inglesi dto non null");
                        ArrayList<Asta_allingleseModel> listAsta_allingleseModel = new ArrayList<>();
                        for (Asta_allinglese_DTO astaAllingleseDto : list){
                            Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                    astaAllingleseDto.getId(),
                                    astaAllingleseDto.getNome(),
                                    astaAllingleseDto.getDescrizione(),
                                    astaAllingleseDto.getPath_immagine(),
                                    astaAllingleseDto.getBaseAsta(),
                                    astaAllingleseDto.getIntervalloTempoOfferte(),
                                    astaAllingleseDto.getIntervalloOfferteBase(),
                                    astaAllingleseDto.getRialzoMin(),
                                    astaAllingleseDto.getPrezzoAttuale(),
                                    astaAllingleseDto.getCondizione(),
                                    astaAllingleseDto.getId_venditore());
                            //stampa dei valori dell asta
                            Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                            listAsta_allingleseModel.add(astaAllingleseModel);
                        }
                        return listAsta_allingleseModel;
                    }
                    System.out.println("lista di aste inglesi dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_allingleseModel> result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnGetAsteCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteCategoriaNomeListener {
        void OnGetAsteCategoriaNome(ArrayList<Asta_allingleseModel> list);
    }
    private static class partecipaAsta_allingleseTask extends AsyncTask<String, Void, Integer> {
        private Asta_allingleseRepository.OnPartecipazioneAstaIngleseListener listener;

        public partecipaAsta_allingleseTask(Asta_allingleseRepository.OnPartecipazioneAstaIngleseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato
            Long idAsta = Long.valueOf(params[0]);
            String emailAcquirente = params[1];
            String offerta = params[2];
            String tempoOfferta = params[3];
            String stato = params[4];
            Log.d("AStaAllIngleseRepository partecipa asta", " valori per id, email, offerta, tempooffera e stato: " + idAsta + emailAcquirente + offerta + tempoOfferta + stato);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<Integer> call = service.partecipaAsta_allinglese(idAsta,emailAcquirente,offerta,tempoOfferta,stato);

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
                listener.OnPartecipazioneAstaInglese(result);
            }
        }
    }
    public interface OnPartecipazioneAstaIngleseListener {
        void OnPartecipazioneAstaInglese(Integer risposta);
    }
    private static class trovaAsta_allingleseTask extends AsyncTask<String, Void, Asta_allingleseModel> {
        private Asta_allingleseRepository.OnTrovaAstaIngleseListener listener;

        public trovaAsta_allingleseTask(Asta_allingleseRepository.OnTrovaAstaIngleseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Asta_allingleseModel doInBackground(String... params) {
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

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<Asta_allinglese_DTO> call = service.trovaAstaInglese(idAsta);

            try {
                Response<Asta_allinglese_DTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Asta_allinglese_DTO astaRecuperata = response.body();
                    if (astaRecuperata != null) {
                        Asta_allingleseModel astaAllingleseModel = new Asta_allingleseModel(
                                astaRecuperata.getId(),
                                astaRecuperata.getNome(),
                                astaRecuperata.getDescrizione(),
                                astaRecuperata.getPath_immagine(),
                                astaRecuperata.getBaseAsta(),
                                astaRecuperata.getIntervalloTempoOfferte(),
                                astaRecuperata.getIntervalloOfferteBase(),
                                astaRecuperata.getRialzoMin(),
                                astaRecuperata.getPrezzoAttuale(),
                                astaRecuperata.getCondizione(),
                                astaRecuperata.getId_venditore());
                        //stampa dei valori dell asta
                        Log.d("Asta inglese" ," valori " + astaAllingleseModel.getNome() + astaAllingleseModel.getDescrizione() + astaAllingleseModel.getId() + astaAllingleseModel.getIntervalloOfferteBase() + astaAllingleseModel.getBaseAsta());
                        return astaAllingleseModel;
                    }else{
                        System.out.println("asta dto null");
                        return null;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC trova asta inglese nei preferiti");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Asta_allingleseModel result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnTrovaAstaInglese(result);
            }
        }
    }
    public interface OnTrovaAstaIngleseListener {
        void OnTrovaAstaInglese(Asta_allingleseModel list);
    }
    private static class verificaAsta_allingleseInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_allingleseRepository.OnVerificaAstaIngleseInPreferitiListener listener;

        public verificaAsta_allingleseInPreferitiTask(Asta_allingleseRepository.OnVerificaAstaIngleseInPreferitiListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String indirizzo_email = params[0];
            Long idAsta = Long.valueOf(params[1]);
            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<Integer> call = service.verificaAstaIngleseInPreferiti(indirizzo_email,idAsta);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
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
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnVerificaAstaIngleseInPreferiti(result);
            }
        }
    }
    public interface OnVerificaAstaIngleseInPreferitiListener {
        void OnVerificaAstaIngleseInPreferiti(Integer numeroRecuperato);
    }
    private static class inserimentoAsta_allingleseInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_allingleseRepository.OnInserimentoAstaIngleseInPreferitiListener listener;

        public inserimentoAsta_allingleseInPreferitiTask(Asta_allingleseRepository.OnInserimentoAstaIngleseInPreferitiListener listener) {
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

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<Integer> call = service.inserimentoAstaInPreferiti(idAsta,indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
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
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnInserimentoAstaIngleseInPreferiti(result);
            }
        }
    }
    public interface OnInserimentoAstaIngleseInPreferitiListener {
        void OnInserimentoAstaIngleseInPreferiti(Integer numeroRecuperato);
    }
    private static class eliminazioneAsta_allingleseInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_allingleseRepository.OnEliminazioneAstaIngleseInPreferitiListener listener;

        public eliminazioneAsta_allingleseInPreferitiTask(Asta_allingleseRepository.OnEliminazioneAstaIngleseInPreferitiListener listener) {
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

            Asta_allingleseService service = retrofit.create(Asta_allingleseService.class);
            Call<Integer> call = service.eliminazioneAstaInPreferiti(idAsta,indirizzo_email);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return numeroRecuperato;
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
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnEliminazioneAstaIngleseInPreferiti(result);
            }
        }
    }
    public interface OnEliminazioneAstaIngleseInPreferitiListener {
        void OnEliminazioneAstaIngleseInPreferiti(Integer numeroRecuperato);
    }
}
