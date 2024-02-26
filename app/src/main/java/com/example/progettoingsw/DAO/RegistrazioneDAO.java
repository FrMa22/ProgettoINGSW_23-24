package com.example.progettoingsw.DAO;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class RegistrazioneDAO {
    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void registraUser(String nome, String cognome, String email, String password, String tipoUtente) {
        if (nome.isEmpty() || email.isEmpty() || cognome.isEmpty() || password.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new DatabaseTask().execute("insert", nome, cognome, email, password, tipoUtente);
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
                            Statement statement = connection.createStatement();
                            String tipoUtente = strings[5];
                            statement.executeUpdate("INSERT INTO " + tipoUtente + " (nome, cognome, indirizzo_email, password) VALUES ('" + strings[1] + "', '" + strings[2] + "', '" + strings[3] + "', '" + strings[4] + "')");
                            statement.close();
                            return "Utente inserito con successo!";
                        } else {
                            return "Impossibile inserire l'utente: connessione non aperta.";
                        }
                    }
                }
                return "Azione non supportata.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'operazione: " + e.getMessage();
            }
        }
    }

}
