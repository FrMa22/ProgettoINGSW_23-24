package com.example.progettoingsw.DAO;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrazioneFacoltativaDAO {


    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void inserimentoDatiRegistrazione(String nome, String cognome, String email, String password, String tipoUtente, String bio, String sitoWeb, String paese) {

        new DatabaseTask().execute("insert", nome , cognome, email, password, tipoUtente, sitoWeb, bio, paese);
    }

    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return "Connessione aperta con successo!";
                    } else if (action.equals("insert")) {
                        if (connection != null && !connection.isClosed()) {
                            Log.d("insert","sto inserendo");
                            String nome = strings[1];
                            String cognome = strings[2];
                            String email = strings[3];
                            String password = strings[4];
                            String tipoUtente = strings[5];
                            String sitoWeb= strings[6];
                            String bio =strings[7];
                            String paese = strings[8];
                            String query = "INSERT INTO " + tipoUtente + " (indirizzo_email, nome, cognome, password, bio, link, areageografica) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, email);
                            statement.setString(2, nome);
                            statement.setString(3, cognome);
                            statement.setString(4, password);
                            statement.setString(5, bio);
                            statement.setString(6, sitoWeb);
                            statement.setString(7, paese);
                            statement.executeUpdate();
                            statement.close();
                            Log.d("insert","inseriti");
                            return "Dati inseriti con successo!";
                        } else {
                            return "Impossibile inserire i dati  : connessione non aperta.";
                        }

                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return "Connessione chiusa con successo!";
                        } else {
                            return "Connessione già chiusa.";
                        }
                    }
                }
                return "Azione non supportata.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'operazione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            Log.d("insert","on post");
            System.out.println(result);
        }
    }

}