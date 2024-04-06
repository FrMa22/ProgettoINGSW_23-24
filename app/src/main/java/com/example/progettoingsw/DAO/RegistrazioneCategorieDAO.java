package com.example.progettoingsw.DAO;

import com.example.progettoingsw.view.RegistrazioneCategorie;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class RegistrazioneCategorieDAO {
    private Connection connection;
    private RegistrazioneCategorie registrazioneCategorie;
    private String email;
    private String tipoUtente;


    public RegistrazioneCategorieDAO(RegistrazioneCategorie registrazioneCategorie, String email, String tipoUtente) {
        this.registrazioneCategorie = registrazioneCategorie;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new RegistrazioneCategorieDAO.DatabaseTask().execute("open");
    }

    public void insertCategorie(ArrayList<String> arrayCategorie) {
        if (arrayCategorie.isEmpty()) {
            // Se l'arrayCategorie è vuoto, non fare nulla
            return;
        }

        new InsertCategorieTask().execute(arrayCategorie);
    }

    public void closeConnection() {
        new CloseConnectionTask().execute();
    }

    private class DatabaseTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!isCancelled()) {
                if (!result) {
                    Toast.makeText(registrazioneCategorie.getApplicationContext(), "Errore durante l'apertura della connessione", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class InsertCategorieTask extends AsyncTask<ArrayList<String>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(ArrayList<String>... arrayCategorie) {
            try {
                if (connection != null && !connection.isClosed()) {
                    // Per ogni categoria nell'arrayCategorie, esegui l'inserimento nella tabella socialAcquirente
                    for (String categoria : arrayCategorie[0]) {
                        String query = "INSERT INTO categorie" + tipoUtente +" (nome, indirizzo_email) VALUES ('" + categoria + "', '" + email + "')";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                        Log.d("insert" , "inseriti " + categoria + " per " + email + " .");
                    }
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!isCancelled()) {
                if (!result) {
                    Toast.makeText(registrazioneCategorie.getApplicationContext(), "Errore durante l'inserimento delle categorie", Toast.LENGTH_SHORT).show();
                }else{
                    registrazioneCategorie.handleInsert(result);
                }
            }
        }
    }
    private class CloseConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

}
