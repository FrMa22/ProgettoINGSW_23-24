package com.example.progettoingsw.DAO;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrazioneFacoltativaDAO {


    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void inserimentoDatiOpzionali(String email, String tipoUtente, String bio,String sitoWeb, String paese) {

        new DatabaseTask().execute("update", email, tipoUtente, sitoWeb, bio, paese);
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
                    } else if (action.equals("update")) {
                        if (connection != null && !connection.isClosed()) {

                            String tipoUtente = strings[2];
                            String bio =strings[3];
                            String sitoWeb= strings[4];
                            String paese = strings[5];
                            String email = strings[1];
                            String query = "UPDATE " + tipoUtente + " SET bio = ?,link = ? , areageografica= ? WHERE indirizzo_email = ?";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, bio);
                            statement.setString(2, sitoWeb);
                            statement.setString(3, paese);
                            statement.setString(4, email);
                            statement.executeUpdate();
                            statement.close();
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
            System.out.println(result);
        }
    }

}