package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.progettoingsw.DTO.Asta_inversa_DTO;
import com.example.progettoingsw.backendAPI.Asta_inversaService;
import com.example.progettoingsw.model.Asta_inversaModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asta_inversaRepository {

    public void getAste_inversaScadenzaRecenteBackend( Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener) {
        System.out.println("entrato in getAste_inversaScadenzaRecenteBackend");
        new Asta_inversaRepository.GetAsteScadenzaRecenteTask(listener).execute();
    }
    public void getAste_inversaNuoveBackend( Asta_inversaRepository.OnGetAsteInversaNuoveListener listener) {
        System.out.println("entrato in getAste_inversaNuoveBackend");
        new Asta_inversaRepository.getAste_inversaNuoveTask(listener).execute();
    }
    public void getAste_inversaCategoriaNomeBackend(ArrayList<String> nomiCategorie, Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener) {
        System.out.println("entrato in getAste_inversaCategoriaNomeBackend");
        new Asta_inversaRepository.getAste_inversaCategoriaNomeTask(listener).execute(nomiCategorie);
    }
    public void partecipaAsta_inversa(Long idAsta, String emailVenditore,String offerta,String tempoOfferta, String stato, Asta_inversaRepository.OnPartecipazioneAstaInversaListener listener) {
        System.out.println("entrato in partecipaAsta_inversa");
        new Asta_inversaRepository.partecipaAsta_inversaTask(listener).execute(String.valueOf(idAsta),emailVenditore,offerta, tempoOfferta, stato);
    }
    public void trovaAsta_inversa(Long idAsta, Asta_inversaRepository.OnTrovaAstaInversaListener listener) {
        System.out.println("entrato in trovaAsta_inversa");
        new Asta_inversaRepository.trovaAsta_inversaTask(listener).execute(String.valueOf(idAsta));
    }
    public void verificaAstaInversaInPreferiti(String indirizzo_email, Long idAsta, Asta_inversaRepository.OnVerificaAstaInversaInPreferitiListener listener) {
        System.out.println("entrato in verificaAstaInversaInPreferiti");
        new Asta_inversaRepository.verificaAsta_inversaInPreferitiTask(listener).execute(indirizzo_email, String.valueOf(idAsta));
    }
    public void inserimentoAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_inversaRepository.OnInserimentoAstaInversaInPreferitiListener listener) {
        System.out.println("entrato in inserimentoAstaInPreferiti");
        new Asta_inversaRepository.inserimentoAsta_inversaInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    public void eliminazioneAstaInPreferiti( Long idAsta,String indirizzo_email, Asta_inversaRepository.OnEliminazioneAstaInversaInPreferitiListener listener) {
        System.out.println("entrato in eliminazioneAstaInPreferiti");
        new Asta_inversaRepository.eliminazioneAsta_inversaInPreferitiTask(listener).execute(String.valueOf(idAsta), indirizzo_email);
    }
    public void getAsteInversaPreferite(String indirizzo_email, Asta_inversaRepository.OnGetAsteInversaPreferiteListener listener) {
        System.out.println("entrato in getAsteInversaPreferite");
        new Asta_inversaRepository.getAste_inversaPreferiteTask(listener).execute(indirizzo_email);
    }
    public void saveAsta_inversa(Asta_inversaModel astaInversaModel, ArrayList<String> listaCategorie, Asta_inversaRepository.OnInserimentoAstaInversaListener listener) {
        System.out.println("entrato in saveAsta_inversa");
        new Asta_inversaRepository.inserimentoAsta_inversaTask(listener).execute(astaInversaModel,listaCategorie);
    }
    public void getEmailVincente(String indirizzo_email, Long idAsta, Asta_inversaRepository.OnGetEmailVincenteListener listener) {
        System.out.println("entrato in getEmailVincente");
        new Asta_inversaRepository.GetEmailVincenteTask(listener).execute(indirizzo_email, String.valueOf(idAsta));
    }
    public void getAstePerRicerca(String nome,ArrayList<String> nomiCategorie,String ordinamento, Asta_inversaRepository.OnGetAstePerRicercaListener listener) {
        System.out.println("entrato in getAstePerRicerca");
        new Asta_inversaRepository.GetAstePerRicercaTask(listener,nome, nomiCategorie,ordinamento).execute();
    }
    private static class GetAsteScadenzaRecenteTask extends AsyncTask<Void, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener;

        public GetAsteScadenzaRecenteTask(Asta_inversaRepository.OnGetAsteScadenzaRecenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(Void... voids) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaScadenzaRecente();

            Log.d("astescadenzarecente" , "prima del try)");
            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                Log.d("astescadenzarecente" , "nel try");
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inverse dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDto.getPath_immagine()!=null){
                            pathImmagineByteArray = base64ToByteArray(astaInversaDto.getPath_immagine());}
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    pathImmagineByteArray,
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            } catch(RuntimeException e) {
                System.out.println("exception runtime");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute recupero aste inverse scadenza recente" + result);
            if (listener != null) {
                listener.OnGetAsteScadenzaRecente(result);
            }
        }
    }
    public interface OnGetAsteScadenzaRecenteListener {
        void OnGetAsteScadenzaRecente(ArrayList<Asta_inversaModel> list);
    }
    private static class getAste_inversaNuoveTask extends AsyncTask<Void, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteInversaNuoveListener listener;

        public getAste_inversaNuoveTask(Asta_inversaRepository.OnGetAsteInversaNuoveListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(Void... voids) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaNuove();

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDto.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDto.getPath_immagine());}
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    pathImmagineByteArray,
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute getAste_inversaNuoveTask" + result);
            if (listener != null) {
                listener.OnGetAsteInversaNuove(result);
            }
        }
    }
    public interface OnGetAsteInversaNuoveListener {
        void OnGetAsteInversaNuove(ArrayList<Asta_inversaModel> list);
    }
    private static class getAste_inversaCategoriaNomeTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener;

        public getAste_inversaCategoriaNomeTask(Asta_inversaRepository.OnGetAsteInversaCategoriaNomeListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(ArrayList<String>... params) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAste_inversaCategoriaNome(nomiCategorie);

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDto.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDto.getPath_immagine());}
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    pathImmagineByteArray,
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute aste inverse per categoria nome" + result);
            if (listener != null) {
                listener.OnGetAsteInversaCategoriaNome(result);
            }
        }
    }
    public interface OnGetAsteInversaCategoriaNomeListener {
        void OnGetAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> list);
    }


    private static class partecipaAsta_inversaTask extends AsyncTask<String, Void, Integer> {
        private Asta_inversaRepository.OnPartecipazioneAstaInversaListener listener;

        public partecipaAsta_inversaTask(Asta_inversaRepository.OnPartecipazioneAstaInversaListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {

            // Effettua l'operazione di rete qui...
            // Restituisci il risultato
            Long idAsta = Long.valueOf(params[0]);
            String emailVenditore = params[1];
            String offerta = params[2];
            String tempoOfferta = params[3];
            String stato = params[4];
            Log.d("AstainversaRepository partecipa asta", " valori per id, email, offerta, tempooffera e stato: " + idAsta + emailVenditore + offerta + tempoOfferta + stato);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Configura il client OkHttpClient...

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Repository.backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<Integer> call = service.partecipaAsta_inversa(idAsta,emailVenditore,offerta,tempoOfferta,stato);

            try {
                Response<Integer> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Integer risposta = response.body();
                    if (risposta != null && risposta==1) {
                        return risposta;
                    }
                    System.out.println("errore in partecipazione");
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
                listener.OnPartecipazioneAstaInversa(result);
            }
        }
    }
    public interface OnPartecipazioneAstaInversaListener {
        void OnPartecipazioneAstaInversa(Integer risposta);
    }
    private static class trovaAsta_inversaTask extends AsyncTask<String, Void, Asta_inversaModel> {
        private Asta_inversaRepository.OnTrovaAstaInversaListener listener;

        public trovaAsta_inversaTask(Asta_inversaRepository.OnTrovaAstaInversaListener listener) {
            this.listener = listener;
        }

        @Override
        protected Asta_inversaModel doInBackground(String... params) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<Asta_inversa_DTO> call = service.trovaAstaInversa(idAsta);

            try {
                Response<Asta_inversa_DTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Asta_inversa_DTO astaRecuperata = response.body();
                    if (astaRecuperata != null) {
                        byte[] pathImmagineByteArray = null;
                        if(astaRecuperata.getPath_immagine()!=null){
                            pathImmagineByteArray = base64ToByteArray(astaRecuperata.getPath_immagine());}
                        Asta_inversaModel astainversaModel = new Asta_inversaModel(
                                astaRecuperata.getId(),
                                astaRecuperata.getNome(),
                                astaRecuperata.getDescrizione(),
                                pathImmagineByteArray,
                                astaRecuperata.getPrezzoMax(),
                                astaRecuperata.getPrezzoAttuale(),
                                astaRecuperata.getDataDiScadenza(),
                                astaRecuperata.getCondizione(),
                                astaRecuperata.getId_acquirente());
                        //stampa dei valori dell asta
                        Log.d("Asta inversa" ," valori " + astainversaModel.getNome() + astainversaModel.getDescrizione() + astainversaModel.getId() + astainversaModel.getPrezzoAttuale() + astainversaModel.getDataDiScadenza());
                        return astainversaModel;
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
        protected void onPostExecute(Asta_inversaModel result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnTrovaAstaInversa(result);
            }
        }
    }
    public interface OnTrovaAstaInversaListener {
        void OnTrovaAstaInversa(Asta_inversaModel list);
    }
    private static class verificaAsta_inversaInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_inversaRepository.OnVerificaAstaInversaInPreferitiListener listener;

        public verificaAsta_inversaInPreferitiTask(Asta_inversaRepository.OnVerificaAstaInversaInPreferitiListener listener) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<Integer> call = service.verificaAstaInversaInPreferiti(indirizzo_email,idAsta);

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
                System.out.println("exception IOEXC verifica asta inverse nei preferiti");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnVerificaAstaInversaInPreferiti(result);
            }
        }
    }
    public interface OnVerificaAstaInversaInPreferitiListener {
        void OnVerificaAstaInversaInPreferiti(Integer numeroRecuperato);
    }
    private static class inserimentoAsta_inversaInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_inversaRepository.OnInserimentoAstaInversaInPreferitiListener listener;

        public inserimentoAsta_inversaInPreferitiTask(Asta_inversaRepository.OnInserimentoAstaInversaInPreferitiListener listener) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
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
                listener.OnInserimentoAstaInversaInPreferiti(result);
            }
        }
    }
    public interface OnInserimentoAstaInversaInPreferitiListener {
        void OnInserimentoAstaInversaInPreferiti(Integer numeroRecuperato);
    }
    private static class eliminazioneAsta_inversaInPreferitiTask extends AsyncTask<String, Void, Integer> {
        private Asta_inversaRepository.OnEliminazioneAstaInversaInPreferitiListener listener;

        public eliminazioneAsta_inversaInPreferitiTask(Asta_inversaRepository.OnEliminazioneAstaInversaInPreferitiListener listener) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
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
                listener.OnEliminazioneAstaInversaInPreferiti(result);
            }
        }
    }
    public interface OnEliminazioneAstaInversaInPreferitiListener {
        void OnEliminazioneAstaInversaInPreferiti(Integer numeroRecuperato);
    }
    private static class getAste_inversaPreferiteTask extends AsyncTask<String, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAsteInversaPreferiteListener listener;

        public getAste_inversaPreferiteTask(Asta_inversaRepository.OnGetAsteInversaPreferiteListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(String... params) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAsteInversaPreferite(indirizzo_email);

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDto.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDto.getPath_immagine());}
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    pathImmagineByteArray,
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inverse dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute aste inverse per categoria nome" + result);
            if (listener != null) {
                listener.OnGetAsteInversaPreferite(result);
            }
        }
    }
    public interface OnGetAsteInversaPreferiteListener {
        void OnGetAsteInversaPreferite(ArrayList<Asta_inversaModel> list);
    }
    private static class inserimentoAsta_inversaTask extends AsyncTask<Object, Void, Long> {
        private Asta_inversaRepository.OnInserimentoAstaInversaListener listener;

        public inserimentoAsta_inversaTask(Asta_inversaRepository.OnInserimentoAstaInversaListener listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            Asta_inversaModel astaInversaModel = (Asta_inversaModel) params[0];
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            String immagine = null;
            if(astaInversaModel.getPath_immagine()!=null){
                immagine = byteArrayToBase64(astaInversaModel.getPath_immagine());
            }

            Asta_inversa_DTO asta_inversa_dto = new Asta_inversa_DTO(astaInversaModel.getNome(),astaInversaModel.getDescrizione(),immagine,astaInversaModel.getPrezzoMax()
            ,astaInversaModel.getPrezzoAttuale(),astaInversaModel.getDataDiScadenza(),astaInversaModel.getCondizione(),astaInversaModel.getId_acquirente());

            Call<Long> call = service.saveAsta_inversa(asta_inversa_dto, lista_categorie);

            try {
                Response<Long> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Long numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        return 0L;
                    }else{
                        System.out.println("asta dto null");
                        return 0L;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
            System.out.println("on post execute inserimento asta inversa" + result);
            if (listener != null) {
                listener.OnInserimentoAstaInversa(result);
            }
        }
    }
    public interface OnInserimentoAstaInversaListener {
        void OnInserimentoAstaInversa(Long numeroRecuperato);
    }
    private static class GetEmailVincenteTask extends AsyncTask<String, Void, Boolean> {
        private Asta_inversaRepository.OnGetEmailVincenteListener listener;

        public GetEmailVincenteTask(Asta_inversaRepository.OnGetEmailVincenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... params) {
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

            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            System.out.println("entrato in getemail vincente con id e email: "+ idAsta + ", " + indirizzo_email);
            Call<Boolean> call = service.getEmailVincente(indirizzo_email,idAsta);

            try {
                Response<Boolean> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    Boolean numeroRecuperato = response.body();
                    if(numeroRecuperato != null){
                        System.out.println("valore recuperato: " + numeroRecuperato);
                        return numeroRecuperato;
                    }else{
                        System.out.println("asta dto null");
                        return false;
                    }
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("on post execute GetAsteScadenzaRecenteTask" + result);
            if (listener != null) {
                listener.OnGetEmailVincente(result);
            }
        }
    }
    public interface OnGetEmailVincenteListener {
        void OnGetEmailVincente(Boolean numeroRecuperato);
    }
    private static class GetAstePerRicercaTask extends AsyncTask<Void, Void, ArrayList<Asta_inversaModel>> {
        private Asta_inversaRepository.OnGetAstePerRicercaListener listener;
        private String nome;
        private ArrayList<String> nomiCategorie;
        private String ordinamento;

        public GetAstePerRicercaTask(Asta_inversaRepository.OnGetAstePerRicercaListener listener, String nome, ArrayList<String> nomiCategorie, String ordinamento) {
            this.listener = listener;
            this.nome = nome;
            this.nomiCategorie = nomiCategorie;
            this.ordinamento = ordinamento;
        }

        @Override
        protected ArrayList<Asta_inversaModel> doInBackground(Void... voids) {

            Log.d("entrato in asycn", "listacategorie: " + nomiCategorie);
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
            Asta_inversaService service = retrofit.create(Asta_inversaService.class);
            Call<ArrayList<Asta_inversa_DTO>> call = service.getAstePerRicerca(nome,ordinamento,nomiCategorieParam);

            try {
                Response<ArrayList<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    ArrayList<Asta_inversa_DTO> list = response.body();
                    if (list != null && !list.isEmpty()) {
                        System.out.println("lista di aste inversa dto non null");
                        ArrayList<Asta_inversaModel> listAsta_inversaModel = new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDto : list){
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDto.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDto.getPath_immagine());}
                            Asta_inversaModel astaInversaModel = new Asta_inversaModel(
                                    astaInversaDto.getId(),
                                    astaInversaDto.getNome(),
                                    astaInversaDto.getDescrizione(),
                                    pathImmagineByteArray,
                                    astaInversaDto.getPrezzoMax(),
                                    astaInversaDto.getPrezzoAttuale(),
                                    astaInversaDto.getDataDiScadenza(),
                                    astaInversaDto.getCondizione(),
                                    astaInversaDto.getId_acquirente());
                            //stampa dei valori dell asta
                            Log.d("Asta inversa" ," valori " + astaInversaModel.getNome() + astaInversaModel.getDescrizione() + astaInversaModel.getId() + astaInversaModel.getDataDiScadenza() + astaInversaModel.getPrezzoAttuale());
                            listAsta_inversaModel.add(astaInversaModel);
                        }
                        return listAsta_inversaModel;
                    }
                    System.out.println("lista di aste inversa dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Asta_inversaModel> result) {
            System.out.println("on post execute GetAstePerRicercaTask" + result);
            if (listener != null) {
                listener.OnGetAstePerRicerca(result);
            }
        }
    }
    public interface OnGetAstePerRicercaListener {
        void OnGetAstePerRicerca(ArrayList<Asta_inversaModel> list);
    }
    // Funzione per convertire una stringa Base64 in un array di byte
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
