package com.example.progettoingsw.repository;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_alribasso_DTO;
import com.example.progettoingsw.DTO.Asta_inversa_DTO;
import com.example.progettoingsw.DTO.SocialAcquirenteDTO;
import com.example.progettoingsw.backendAPI.Asta_allingleseService;
import com.example.progettoingsw.backendAPI.Asta_alribassoService;
import com.example.progettoingsw.backendAPI.Asta_inversaService;
import com.example.progettoingsw.backendAPI.SocialAcquirenteService;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeMieAsteRepository {


    public void trovaAsteInglesiAperteBackend(String email,LeMieAsteRepository.OnTrovaAsteInglesiAperteListener listener) {
        System.out.println("entrato in  Trova Aste inglesi Aperte Backend");
        new LeMieAsteRepository.TrovaAsteInglesiAperteTask(listener).execute(email);
    }


    public void trovaAsteRibassoAperteBackend(String email,LeMieAsteRepository.OnTrovaAsteRibassoAperteListener listener) {
        System.out.println("entrato in  Trova Aste  ribasso Aperte Backend");
        new LeMieAsteRepository.TrovaAsteRibassoAperteTask(listener).execute(email);
    }


    public void trovaAsteInverseAperteBackend(String email,LeMieAsteRepository.OnTrovaAsteInverseAperteListener listener) {
        System.out.println("entrato in  Trova Aste  inverse Aperte Backend");
        new LeMieAsteRepository.TrovaAsteInverseAperteTask(listener).execute(email);
    }



    public void trovaAsteInglesiChiuseBackend(String email,LeMieAsteRepository.OnTrovaAsteInglesiChiuseListener listener) {
        System.out.println("entrato in  Trova Aste inglesi Chiuse Backend");
        new LeMieAsteRepository.TrovaAsteInglesiChiuseTask(listener).execute(email);
    }


    public void trovaAsteRibassoChiuseBackend(String email,LeMieAsteRepository.OnTrovaAsteRibassoChiuseListener listener) {
        System.out.println("entrato in  Trova Aste  ribasso Chiuse Backend");
        new LeMieAsteRepository.TrovaAsteRibassoChiuseTask(listener).execute(email);
    }


    public void trovaAsteInverseChiuseBackend(String email,LeMieAsteRepository.OnTrovaAsteInverseChiuseListener listener) {
        System.out.println("entrato in  Trova Aste  inverse Chiuse Backend");
        new LeMieAsteRepository.TrovaAsteInverseChiuseTask(listener).execute(email);
    }


    private static class TrovaAsteInglesiAperteTask extends AsyncTask<String, Void, List<Asta_allingleseModel>> {
        private LeMieAsteRepository.OnTrovaAsteInglesiAperteListener listener;

        public TrovaAsteInglesiAperteTask(LeMieAsteRepository.OnTrovaAsteInglesiAperteListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_allingleseModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova aste inglesi aperte, email: " + email);
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
            Call<List<Asta_allinglese_DTO>> call=service.getAste_allingleseApertaByEmail(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<Asta_allinglese_DTO> astaIngleseDTOList=response.body();
                    if (astaIngleseDTOList!= null && !astaIngleseDTOList.isEmpty()) {
                        System.out.println("Asta inglese dto non null");

                        List<Asta_allingleseModel> astaIngleseModelList=new ArrayList<>();
                        for (Asta_allinglese_DTO astaIngleseDTO : astaIngleseDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaIngleseDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaIngleseDTO.getPath_immagine());}
                            System.out.println("Valori del asta inglese DTO: " + "id: " + astaIngleseDTO.getId() + ", nome: " + astaIngleseDTO.getNome() + ", email: " + astaIngleseDTO.getId_venditore());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_allingleseModel astaIngleseModel=new Asta_allingleseModel(astaIngleseDTO.getId(),astaIngleseDTO.getNome(),astaIngleseDTO.getDescrizione(),pathImmagineByteArray,astaIngleseDTO.getBaseAsta(),astaIngleseDTO.getIntervalloTempoOfferte(),astaIngleseDTO.getIntervalloOfferteBase(),astaIngleseDTO.getRialzoMin(),astaIngleseDTO.getPrezzoAttuale(),astaIngleseDTO.getCondizione(),astaIngleseDTO.getId_venditore());
                            astaIngleseModelList.add(astaIngleseModel);
                            System.out.println("Valori del asta inglese MODEL: " + "id: " + astaIngleseModel.getId() + ", nome: " + astaIngleseModel.getNome() + ", email: " + astaIngleseModel.getId_venditore());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaIngleseModelList;
                    }
                    System.out.println("Asta inglese dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_allingleseModel> result) {
            System.out.println("on post execute trova Asta inglese aperte");
            if (listener != null) {
                listener.onTrovaAsteInglesiAperte(result);
            }
        }
    }


    public interface OnTrovaAsteInglesiAperteListener {
        void onTrovaAsteInglesiAperte(List<Asta_allingleseModel> astaAllingleseModelList);
    }

//

    private static class TrovaAsteInglesiChiuseTask extends AsyncTask<String, Void, List<Asta_allingleseModel>> {
        private LeMieAsteRepository.OnTrovaAsteInglesiChiuseListener listener;

        public TrovaAsteInglesiChiuseTask(LeMieAsteRepository.OnTrovaAsteInglesiChiuseListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_allingleseModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova aste inglesi chiuse, email: " + email);
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
            Call<List<Asta_allinglese_DTO>> call=service.getAste_allingleseChiusaByEmail(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<Asta_allinglese_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<Asta_allinglese_DTO> astaIngleseDTOList=response.body();
                    if (astaIngleseDTOList!= null && !astaIngleseDTOList.isEmpty()) {
                        System.out.println("Asta inglese dto non null");

                        List<Asta_allingleseModel> astaIngleseModelList=new ArrayList<>();
                        for (Asta_allinglese_DTO astaIngleseDTO : astaIngleseDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaIngleseDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaIngleseDTO.getPath_immagine());}
                            System.out.println("Valori del asta inglese DTO: " + "id: " + astaIngleseDTO.getId() + ", nome: " + astaIngleseDTO.getNome() + ", email: " + astaIngleseDTO.getId_venditore());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_allingleseModel astaIngleseModel=new Asta_allingleseModel(astaIngleseDTO.getId(),astaIngleseDTO.getNome(),astaIngleseDTO.getDescrizione(),pathImmagineByteArray,astaIngleseDTO.getBaseAsta(),astaIngleseDTO.getIntervalloTempoOfferte(),astaIngleseDTO.getIntervalloOfferteBase(),astaIngleseDTO.getRialzoMin(),astaIngleseDTO.getPrezzoAttuale(),astaIngleseDTO.getCondizione(),astaIngleseDTO.getId_venditore());
                            astaIngleseModelList.add(astaIngleseModel);
                            System.out.println("Valori del asta inglese MODEL: " + "id: " + astaIngleseModel.getId() + ", nome: " + astaIngleseModel.getNome() + ", email: " + astaIngleseModel.getId_venditore());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaIngleseModelList;
                    }
                    System.out.println("Asta inglese dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_allingleseModel> result) {
            System.out.println("on post execute trova Asta inglese chiuse");
            if (listener != null) {
                listener.onTrovaAsteInglesiChiuse(result);
            }
        }
    }


    public interface OnTrovaAsteInglesiChiuseListener {
        void onTrovaAsteInglesiChiuse(List<Asta_allingleseModel> astaAllingleseModelList);
    }


    //
    private static class TrovaAsteRibassoAperteTask extends AsyncTask<String, Void, List<Asta_alribassoModel>> {
        private LeMieAsteRepository.OnTrovaAsteRibassoAperteListener listener;

        public TrovaAsteRibassoAperteTask(LeMieAsteRepository.OnTrovaAsteRibassoAperteListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_alribassoModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova aste ribasso aperte, email: " + email);
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
            Call<List<Asta_alribasso_DTO>> call=service.getAste_alribassoApertaByEmail(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<Asta_alribasso_DTO> astaRibassoDTOList=response.body();
                    if (astaRibassoDTOList!= null && !astaRibassoDTOList.isEmpty()) {
                        System.out.println("Asta ribasso dto non null");

                        List<Asta_alribassoModel> astaRibassoModelList=new ArrayList<>();
                        for (Asta_alribasso_DTO astaRibassoDTO : astaRibassoDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaRibassoDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaRibassoDTO.getPath_immagine());}
                            System.out.println("Valori del asta ribasso DTO: " + "id: " + astaRibassoDTO.getId() + ", nome: " + astaRibassoDTO.getNome() + ", email: " + astaRibassoDTO.getId_venditore());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_alribassoModel astaRibassoModel=new Asta_alribassoModel(astaRibassoDTO.getId(),astaRibassoDTO.getNome(),astaRibassoDTO.getDescrizione(),pathImmagineByteArray,astaRibassoDTO.getPrezzoBase(),astaRibassoDTO.getIntervalloDecrementale(),astaRibassoDTO.getIntervalloBase(),astaRibassoDTO.getDecrementoAutomaticoCifra(),astaRibassoDTO.getPrezzoMin(),astaRibassoDTO.getPrezzoAttuale(),astaRibassoDTO.getCondizione(),astaRibassoDTO.getId_venditore());
                            astaRibassoModelList.add(astaRibassoModel);
                            System.out.println("Valori del asta ribasso MODEL: " + "id: " + astaRibassoModel.getId() + ", nome: " + astaRibassoModel.getNome() + ", email: " + astaRibassoModel.getId_venditore());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaRibassoModelList;
                    }
                    System.out.println("Asta ribasso dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_alribassoModel> result) {
            System.out.println("on post execute trova Asta ribasso aperte");
            if (listener != null) {
                listener.onTrovaAsteRibassoAperte(result);
            }
        }
    }


    public interface OnTrovaAsteRibassoAperteListener {
        void onTrovaAsteRibassoAperte(List<Asta_alribassoModel> astaAlribassoModelList);
    }

    //
    private static class TrovaAsteRibassoChiuseTask extends AsyncTask<String, Void, List<Asta_alribassoModel>> {
        private LeMieAsteRepository.OnTrovaAsteRibassoChiuseListener listener;

        public TrovaAsteRibassoChiuseTask(LeMieAsteRepository.OnTrovaAsteRibassoChiuseListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_alribassoModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova aste ribasso chiuse, email: " + email);
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
            Call<List<Asta_alribasso_DTO>> call=service.getAste_alribassoChiusaByEmail(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<Asta_alribasso_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<Asta_alribasso_DTO> astaRibassoDTOList=response.body();
                    if (astaRibassoDTOList!= null && !astaRibassoDTOList.isEmpty()) {
                        System.out.println("Asta ribasso dto non null");

                        List<Asta_alribassoModel> astaRibassoModelList=new ArrayList<>();
                        for (Asta_alribasso_DTO astaRibassoDTO : astaRibassoDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaRibassoDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaRibassoDTO.getPath_immagine());}
                            System.out.println("Valori del asta ribasso DTO: " + "id: " + astaRibassoDTO.getId() + ", nome: " + astaRibassoDTO.getNome() + ", email: " + astaRibassoDTO.getId_venditore());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_alribassoModel astaRibassoModel=new Asta_alribassoModel(astaRibassoDTO.getId(),astaRibassoDTO.getNome(),astaRibassoDTO.getDescrizione(),pathImmagineByteArray,astaRibassoDTO.getPrezzoBase(),astaRibassoDTO.getIntervalloDecrementale(),astaRibassoDTO.getIntervalloBase(),astaRibassoDTO.getDecrementoAutomaticoCifra(),astaRibassoDTO.getPrezzoMin(),astaRibassoDTO.getPrezzoAttuale(),astaRibassoDTO.getCondizione(),astaRibassoDTO.getId_venditore());
                            astaRibassoModelList.add(astaRibassoModel);
                            System.out.println("Valori del asta ribasso MODEL: " + "id: " + astaRibassoModel.getId() + ", nome: " + astaRibassoModel.getNome() + ", email: " + astaRibassoModel.getId_venditore());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaRibassoModelList;
                    }
                    System.out.println("Asta ribasso dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_alribassoModel> result) {
            System.out.println("on post execute trova Asta ribasso chiuse");
            if (listener != null) {
                listener.onTrovaAsteRibassoChiuse(result);
            }
        }
    }


    public interface OnTrovaAsteRibassoChiuseListener {
        void onTrovaAsteRibassoChiuse(List<Asta_alribassoModel> astaAlribassoModelList);
    }

    //

    private static class TrovaAsteInverseAperteTask extends AsyncTask<String, Void, List<Asta_inversaModel>> {
        private LeMieAsteRepository.OnTrovaAsteInverseAperteListener listener;

        public TrovaAsteInverseAperteTask(LeMieAsteRepository.OnTrovaAsteInverseAperteListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Asta_inversaModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova aste inverse aperte, email: " + email);
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
            Call<List<Asta_inversa_DTO>> call=service.getAste_inversaApertaByEmail(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<Asta_inversa_DTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<Asta_inversa_DTO> astaInversaDTOList=response.body();
                    if (astaInversaDTOList!= null && !astaInversaDTOList.isEmpty()) {
                        System.out.println("Asta inversa dto non null");

                        List<Asta_inversaModel> astaInversaModelList=new ArrayList<>();
                        for (Asta_inversa_DTO astaInversaDTO : astaInversaDTOList) {
                            byte[] pathImmagineByteArray = null;
                            if(astaInversaDTO.getPath_immagine()!=null){
                                pathImmagineByteArray = base64ToByteArray(astaInversaDTO.getPath_immagine());}
                            System.out.println("Valori del asta inversa DTO: " + "id: " + astaInversaDTO.getId() + ", nome: " + astaInversaDTO.getNome() + ", email: " + astaInversaDTO.getId_acquirente());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            Asta_inversaModel astaInversaModel=new Asta_inversaModel(astaInversaDTO.getId(),astaInversaDTO.getNome(),astaInversaDTO.getDescrizione(),pathImmagineByteArray,astaInversaDTO.getPrezzoMax(),astaInversaDTO.getPrezzoAttuale(),astaInversaDTO.getDataDiScadenza(),astaInversaDTO.getCondizione(),astaInversaDTO.getId_acquirente());
                            astaInversaModelList.add(astaInversaModel);
                            System.out.println("Valori del asta inversa MODEL: " + "id: " + astaInversaModel.getId() + ", nome: " + astaInversaModel.getNome() + ", email: " + astaInversaModel.getId_acquirente());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return astaInversaModelList;
                    }
                    System.out.println("Asta ribasso dto null");
                }
                System.out.println("response non successful");
            } catch (IOException e) {
                System.out.println("exception IOEXC");
                e.printStackTrace();
            }
            System.out.println("ritornato null nel asynctask");
            return null;
        }

        @Override
        protected void onPostExecute(List<Asta_inversaModel> result) {
            System.out.println("on post execute trova Asta inverse aperte");
            if (listener != null) {
                listener.onTrovaAsteInverseAperte(result);
            }
        }
    }


    public interface OnTrovaAsteInverseAperteListener {
        void onTrovaAsteInverseAperte(List<Asta_inversaModel> astaInversaModelList);
    }

//
private static class TrovaAsteInverseChiuseTask extends AsyncTask<String, Void, List<Asta_inversaModel>> {
    private LeMieAsteRepository.OnTrovaAsteInverseChiuseListener listener;

    public TrovaAsteInverseChiuseTask(LeMieAsteRepository.OnTrovaAsteInverseChiuseListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Asta_inversaModel> doInBackground(String... params) {
        String email = params[0];
        System.out.println("in async trova aste inverse chiuse, email: " + email);
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
        Call<List<Asta_inversa_DTO>> call=service.getAste_inversaChiusaByEmail(email);


        System.out.println("entrato nel collegamento al backend,prima del try");
        try {
            Response<List<Asta_inversa_DTO>> response = call.execute();
            if (response.isSuccessful()) {
                System.out.println("response successful");
                List<Asta_inversa_DTO> astaInversaDTOList=response.body();
                if (astaInversaDTOList!= null && !astaInversaDTOList.isEmpty()) {
                    System.out.println("Asta inversa dto non null");

                    List<Asta_inversaModel> astaInversaModelList=new ArrayList<>();
                    for (Asta_inversa_DTO astaInversaDTO : astaInversaDTOList) {
                        byte[] pathImmagineByteArray = null;
                        if(astaInversaDTO.getPath_immagine()!=null){
                            pathImmagineByteArray = base64ToByteArray(astaInversaDTO.getPath_immagine());}
                        System.out.println("Valori del asta inversa DTO: " + "id: " + astaInversaDTO.getId() + ", nome: " + astaInversaDTO.getNome() + ", email: " + astaInversaDTO.getId_acquirente());
                        // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                        Asta_inversaModel astaInversaModel=new Asta_inversaModel(astaInversaDTO.getId(),astaInversaDTO.getNome(),astaInversaDTO.getDescrizione(),pathImmagineByteArray,astaInversaDTO.getPrezzoMax(),astaInversaDTO.getPrezzoAttuale(),astaInversaDTO.getDataDiScadenza(),astaInversaDTO.getCondizione(),astaInversaDTO.getId_acquirente());
                        astaInversaModelList.add(astaInversaModel);
                        System.out.println("Valori del asta inversa MODEL: " + "id: " + astaInversaModel.getId() + ", nome: " + astaInversaModel.getNome() + ", email: " + astaInversaModel.getId_acquirente());
                    }
                    System.out.println("ritornato qualcosa nel asynctask");
                    return astaInversaModelList;
                }
                System.out.println("Asta ribasso dto null");
            }
            System.out.println("response non successful");
        } catch (IOException e) {
            System.out.println("exception IOEXC");
            e.printStackTrace();
        }
        System.out.println("ritornato null nel asynctask");
        return null;
    }

    @Override
    protected void onPostExecute(List<Asta_inversaModel> result) {
        System.out.println("on post execute trova Asta inverse chiuse");
        if (listener != null) {
            listener.onTrovaAsteInverseChiuse(result);
        }
    }
}


    public interface OnTrovaAsteInverseChiuseListener {
        void onTrovaAsteInverseChiuse(List<Asta_inversaModel> astaInversaModelList);
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
