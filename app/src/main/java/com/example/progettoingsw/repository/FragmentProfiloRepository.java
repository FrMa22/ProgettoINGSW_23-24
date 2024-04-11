package com.example.progettoingsw.repository;

import android.os.AsyncTask;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.SocialAcquirenteDTO;
import com.example.progettoingsw.backendAPI.AcquirenteService;
import com.example.progettoingsw.backendAPI.SocialAcquirenteService;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class FragmentProfiloRepository {

    Repository repository = Repository.getInstance();

    public void trovaSocialAcquirenteBackend(String email,FragmentProfiloRepository.OnFragmentProfiloAcquirenteListener listener) {
        System.out.println("entrato in  trova Social Acquirente Backend");
        new TrovaSocialAcquirenteTask(listener).execute(email);
    }


    public void aggiungiSocialAcquirenteBackend(String nome,String link,String email,FragmentProfiloRepository.OnAggiungiSocialAcquirenteListener listener){
        System.out.println("entrato in aggiungi Social Acquirente Backend");
        new AggiungiSocialAcquirenteTask(listener).execute(nome,link,email);
    }

    public void eliminaSocialAcquirenteBackend(String nome,String link,String email,FragmentProfiloRepository.OnEliminaSocialAcquirenteListener listener){
        System.out.println("entrato in elimina Social Acquirente Backend");
        new EliminaSocialAcquirenteTask(listener).execute(nome,link,email);
    }

    public void aggiornaSocialAcquirenteBackend(String oldNome,String oldLink,String newNome,String newLink,FragmentProfiloRepository.OnAggiornaSocialAcquirenteListener listener){
        System.out.println("entrato in aggiorna Social Acquirente Backend");
        new AggiornaSocialAcquirenteTask(listener).execute(oldNome,oldLink,newNome,newLink);
    }

    public void aggiornaAcquirenteBackend(String nome,String cognome,String bio,String link,String areageografica,String email,FragmentProfiloRepository.OnAggiornaAcquirenteListener listener){
        System.out.println("entrato in aggiorna  Acquirente Backend");
        new AggiornaAcquirenteTask(listener).execute(nome,cognome,bio,link,areageografica,email);
    }


    public void aggiornaPasswordAcquirenteBackend(String password,String email,FragmentProfiloRepository.OnAggiornaPasswordAcquirenteListener listener){
        System.out.println("entrato in aggiorna password Acquirente Backend");
        new AggiornaPasswordAcquirenteTask(listener).execute(password,email);
    }


    private static class TrovaSocialAcquirenteTask extends AsyncTask<String, Void, List<SocialAcquirenteModel>  > {
        private OnFragmentProfiloAcquirenteListener listener;

        public TrovaSocialAcquirenteTask(OnFragmentProfiloAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<SocialAcquirenteModel> doInBackground(String... params) {
            String email = params[0];
            System.out.println("in async trova social acquirente, email: " + email);
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
            Call<List<SocialAcquirenteDTO>> call=service.findSocialAcquirente(email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<List<SocialAcquirenteDTO>> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    List<SocialAcquirenteDTO> socialAcquirenteDTOList=response.body();
                    if (socialAcquirenteDTOList!= null) {
                        System.out.println("Social acquirente dto non null");

                        List<SocialAcquirenteModel> socialAcquirenteModelList=new ArrayList<>();
                        for (SocialAcquirenteDTO socialAcquirenteDTO : socialAcquirenteDTOList) {
                            System.out.println("Valori del socialacquirente DTO: " + "nome: " + socialAcquirenteDTO.getNome() + ", link: " + socialAcquirenteDTO.getLink() + ", email: " + socialAcquirenteDTO.getIndirizzo_email());
                            // Fai qualcosa con i dati di socialAcquirenteDTO, ad esempio creando un nuovo oggetto AcquirenteModel
                            SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                            socialAcquirenteModelList.add(socialAcquirenteModel);
                            System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                        }
                        System.out.println("ritornato qualcosa nel asynctask");
                        return socialAcquirenteModelList;
                    }
                    System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(List<SocialAcquirenteModel> result) {
            System.out.println("on post execute trova socialAcquirente");
            if (listener != null) {
                listener.onFragmentProfilo(result);
            }
        }
    }









    private static class AggiungiSocialAcquirenteTask extends AsyncTask<String, Void, SocialAcquirenteModel > {
        private OnAggiungiSocialAcquirenteListener listener;

        public AggiungiSocialAcquirenteTask(OnAggiungiSocialAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected SocialAcquirenteModel doInBackground(String... params) {
            String nome = params[0];
            String link=params[1];
            String email=params[2];
            System.out.println("in async aggiungi social acquirente, nome: " + nome + "link:"+ link + "email:"+ email);
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
            Call<SocialAcquirenteDTO> call=service.insertSocialAcquirente(nome,link,email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<SocialAcquirenteDTO> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    SocialAcquirenteDTO socialAcquirenteDTO=response.body();
                    if (socialAcquirenteDTO!= null) {
                        System.out.println("Social acquirente dto non null");
                            SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                            System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                        System.out.println("ritornato qualcosa nel asynctask");
                        return socialAcquirenteModel;
                    }
                    System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(SocialAcquirenteModel result) {
            System.out.println("on post execute aggiungi socialAcquirente");
            if (listener != null) {
                listener.onAggiungiSocialAcquirente(result);
            }
        }
    }









    private static class EliminaSocialAcquirenteTask extends AsyncTask<String, Void, SocialAcquirenteModel > {
        private OnEliminaSocialAcquirenteListener listener;

        public EliminaSocialAcquirenteTask(OnEliminaSocialAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected SocialAcquirenteModel doInBackground(String... params) {
            String nome = params[0];
            String link=params[1];
            String email=params[2];
            System.out.println("in async elimina social acquirente, nome: " + nome + "link:"+ link + "email:"+ email);
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
            Call<Void> call=service.deleteSocialAcquirente(nome,link,email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    //SocialAcquirenteDTO socialAcquirenteDTO=response.body();
                   // if (socialAcquirenteDTO!= null) {
                        System.out.println("Social acquirente dto non null");
                       // SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                        SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(nome,link,email);
                        System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                        System.out.println("ritornato qualcosa nel asynctask");
                        return socialAcquirenteModel;
                 //   }
                   // System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(SocialAcquirenteModel result) {
            System.out.println("on post execute elimina socialAcquirente");
            if (listener != null) {
                listener.onEliminaSocialAcquirente(result);
            }
        }
    }




// Aggiorna task

    private static class AggiornaSocialAcquirenteTask extends AsyncTask<String, Void, String[] > {
        private OnAggiornaSocialAcquirenteListener listener;

        public AggiornaSocialAcquirenteTask(OnAggiornaSocialAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String oldNome = params[0];
            String oldLink=params[1];
            String newNome=params[2];
            String newLink=params[3];
            System.out.println("in async aggiorna social acquirente, oldNome: " + oldNome + "oldlink:"+ oldLink + "newNome:"+ newNome +" newLink:"+newLink);
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
            Call<Void> call=service.updateSocialAcquirente(oldNome,oldLink,newNome,newLink);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    //SocialAcquirenteDTO socialAcquirenteDTO=response.body();
                    // if (socialAcquirenteDTO!= null) {
                    //System.out.println("Social acquirente dto non null");
                    // SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                    //SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(nome,link,email);
                    //System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                    System.out.println("ritornato qualcosa nel asynctask");
                    //return socialAcquirenteModel;
                    return new String[] { oldNome, oldLink, newNome, newLink };
                    //   }
                    // System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(String[] result) {
            System.out.println("on post execute elimina socialAcquirente");
            if (listener != null && result != null && result.length == 4) {
                String oldNome = result[0];
                String oldLink = result[1];
                String newNome = result[2];
                String newLink = result[3];

                listener.onAggiornaSocialAcquirente(oldNome,oldLink,newNome,newLink);
            }
        }
    }




    private static class AggiornaAcquirenteTask extends AsyncTask<String, Void, String[] > {
        private OnAggiornaAcquirenteListener listener;

        public AggiornaAcquirenteTask(OnAggiornaAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String nome = params[0];
            String cognome=params[1];
            String bio=params[2];
            String link=params[3];
            String areageografica=params[4];
            String email=params[5];
            System.out.println("in async aggiorna  acquirente , nome:"+nome + " cognome:"+cognome + " bio:"+bio + " link:"+link +" area:"+areageografica+ " email:"+email );
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
            Call<Void> call=service.updateAcquirente(nome,cognome,bio,link,areageografica,email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    //SocialAcquirenteDTO socialAcquirenteDTO=response.body();
                    // if (socialAcquirenteDTO!= null) {
                    //System.out.println("Social acquirente dto non null");
                    // SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                    //SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(nome,link,email);
                    //System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                    System.out.println("ritornato qualcosa nel asynctask");
                    //return socialAcquirenteModel;
                    return new String[] { nome, cognome, bio, link,areageografica };
                    //   }
                    // System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(String[] result) {
            System.out.println("on post execute aggiorna Acquirente");
            if (listener != null && result != null && result.length == 5) {
                String nome = result[0];
                String cognome=result[1];
                String bio=result[2];
                String link=result[3];
                String areageografica=result[4];

                listener.onAggiornaAcquirente(nome,cognome,bio,link,areageografica);
            }
        }
    }






    private static class AggiornaPasswordAcquirenteTask extends AsyncTask<String, Void, String[] > {
        private OnAggiornaPasswordAcquirenteListener listener;

        public AggiornaPasswordAcquirenteTask(OnAggiornaPasswordAcquirenteListener listener) {
            this.listener = listener;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String password = params[0];
            String email=params[1];
            System.out.println("in async aggiorna  password acquirente , password:"+password + " email:"+email );
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
            Call<Void> call=service.updatePasswordAcquirente(password,email);


            System.out.println("entrato nel collegamento al backend,prima del try");
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    System.out.println("response successful");
                    //SocialAcquirenteDTO socialAcquirenteDTO=response.body();
                    // if (socialAcquirenteDTO!= null) {
                    //System.out.println("Social acquirente dto non null");
                    // SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(socialAcquirenteDTO.getNome(),socialAcquirenteDTO.getLink(),socialAcquirenteDTO.getIndirizzo_email());
                    //SocialAcquirenteModel socialAcquirenteModel=new SocialAcquirenteModel(nome,link,email);
                    //System.out.println("Valori del socialacquirente MODEL: " + "nome: " + socialAcquirenteModel.getNome() + ", link: " + socialAcquirenteModel.getLink() + ", email: " + socialAcquirenteModel.getIndirizzo_email());
                    System.out.println("ritornato qualcosa nel asynctask");
                    //return socialAcquirenteModel;
                    return new String[] { password };
                    //   }
                    // System.out.println("Social acquirente dto null");
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
        protected void onPostExecute(String[] result) {
            System.out.println("on post execute aggiorna password Acquirente");
            if (listener != null && result != null && result.length == 1) {
                String password = result[0];

                listener.onAggiornaPasswordAcquirente(password);
            }
        }
    }





    public interface OnFragmentProfiloAcquirenteListener {
        void onFragmentProfilo(List<SocialAcquirenteModel> socialAcquirenteModelList);
    }

    public interface OnAggiungiSocialAcquirenteListener {
        void onAggiungiSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel);
    }


    public interface OnEliminaSocialAcquirenteListener {
        void onEliminaSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel);
    }
    public interface OnAggiornaSocialAcquirenteListener {
        void onAggiornaSocialAcquirente(String oldNome,String oldLink,String newNome,String newLink);
    }


    public interface OnAggiornaAcquirenteListener {
        void onAggiornaAcquirente(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo);
    }

    public interface OnAggiornaPasswordAcquirenteListener {
        void onAggiornaPasswordAcquirente(String password);
    }

    public interface OnFragmentProfiloVenditoreListener {
        void onFragmentProfilo(List<SocialVenditoreModel> socialVenditoreModelList);
    }


}