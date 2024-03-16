package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.ProfiloVenditore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfiloVenditoreDaAstaDAO {

    private Connection connection;
    private ProfiloVenditore profiloVenditore;
    private String mail;
    private String tipoUtente;

    public ProfiloVenditoreDaAstaDAO(ProfiloVenditore profiloV, String email, String tipoUtente) {
        this.profiloVenditore = profiloV;
        this.mail = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new ProfiloVenditoreDaAstaDAO.DatabaseTask().execute("open");
    }

    public void findUser() {
        if (mail.isEmpty()) {
            // Se l'ID è vuoto, non fare nulla
            return;
        }
        new ProfiloVenditoreDaAstaDAO.DatabaseTask().execute("find");
    }

    public void getSocialNamesForEmail() {
        if (mail.isEmpty()) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new ProfiloVenditoreDaAstaDAO.DatabaseTask().execute("get_social_names");
    }


    public void closeConnection() {
        new ProfiloVenditoreDaAstaDAO.DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("FragmentProfiloDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("find")) {
                        if (connection != null && !connection.isClosed()) {
                            Log.d("find" , "sto qui");
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tipoUtente + " WHERE indirizzo_email = '" + mail + "'");
                            Log.d("FragmentProfiloDAO", "query fatta");
                            if (resultSet.next()) {
                                Log.d("FragmentProfiloDAO", "result set ok");
                                String nome = resultSet.getString("nome");
                                String cognome = resultSet.getString("cognome");
                                String email = resultSet.getString("indirizzo_email");
                                String sitoWeb = resultSet.getString("link");
                                String paese = resultSet.getString("areageografica");
                                String bio = resultSet.getString("bio");
                                Log.d("Valori di editTexts" , " nome : " + nome + ", cognome : " + cognome + ", email: " + email + ", link : " + sitoWeb + ", paese : " + paese + " , bio: " + bio + " .");
                                return new Acquirente(nome, cognome, email, sitoWeb, paese, bio);
                            }
                        }
                    } else if (action.equals("get_social_names")) {
                        if (connection != null && !connection.isClosed()) {
                            List<String> socialNames = new ArrayList<>();
                            List<String> socialLinks = new ArrayList<>();
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT nome, link FROM social" + tipoUtente + " WHERE indirizzo_email = '" + mail + "'");
                            while (resultSet.next()) {
                                socialNames.add(resultSet.getString("nome"));
                                socialLinks.add(resultSet.getString("link"));
                                Log.d("FragmentProfiloDAO", "Nome Social: " + resultSet.getString("nome") + ", Link Social: " + resultSet.getString("link")); // Aggiunto
                            }
                            return new Object[]{socialNames, socialLinks};
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("FragmentProfiloDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FragmentProfiloDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof Acquirente) {
                // Esempio: restituisci l'acquirente al Fragment
                profiloVenditore.updateEditTexts((Acquirente) result);
            } else if (result instanceof Object[]) {
                Object[] socialData = (Object[]) result;
                List<String> socialNames = (List<String>) socialData[0];
                List<String> socialLinks = (List<String>) socialData[1];
                profiloVenditore.updateSocialNames(socialNames, socialLinks);
            } else {
                // Nessun risultato o errore
            }

            // Aggiungi un controllo per verificare se result è null
            if (result != null) {
                Log.d("DatabaseTask", "Result: " + result.toString());
            } else {
                Log.d("DatabaseTask", "Result is null");
            }
        }




    }

}
