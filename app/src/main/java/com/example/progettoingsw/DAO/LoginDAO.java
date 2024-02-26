package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAO {

    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void findUser(String mail, String password) {
        if (mail.isEmpty()) {
            // Se l'ID è vuoto, non fare nulla
            return;
        }
        new DatabaseTask().execute("find", mail, password);
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
                    } else if (action.equals("find")) {
                        if (connection != null && !connection.isClosed()) {
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM utente WHERE mail = '" + strings[1] + "' AND password = '" + strings[2] + "'");
                            if (resultSet.next()) {
                                // L'utente è stato trovato
                                return "Utente trovato: " + resultSet.getString("mail") + " " + resultSet.getString("password");
                            } else {
                                // L'utente non è stato trovato
                                return "Utente non trovato.";
                            }
                        } else {
                            return "Impossibile cercare l'utente: connessione non aperta.";
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
