package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.PopUpControlloPassword;
import com.example.progettoingsw.gui.PopUpModificaCampiProfilo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class PopUpModificaCampiProfiloDAO {
    private Connection connection;
    private PopUpModificaCampiProfilo popUpModificaCampiProfilo;

    public PopUpModificaCampiProfiloDAO(PopUpModificaCampiProfilo popUpModificaCampiProfilo) {
        this.popUpModificaCampiProfilo = popUpModificaCampiProfilo;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void getFields(String email) {
        if (email.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new GetFieldsTask().execute(email);
    }
    public void updateFields(String email, String nome, String cognome, String link, String paese, String bio) {
        if (email.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new UpdateFieldsTask().execute(email, nome,cognome,link,paese,bio);
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
                    Toast.makeText(popUpModificaCampiProfilo.getContext(), "Errore durante l'apertura della connessione", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class GetFieldsTask extends AsyncTask<String, Void, Object[]> {

        @Override
        protected Object[] doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT nome, cognome, link, areageografica, bio FROM acquirente WHERE indirizzo_email =  '" + strings[0] +  "'    ");
                    if (resultSet.next()) {
                        String nome = resultSet.getString("nome");
                        String cognome = resultSet.getString("cognome");
                        String sitoWeb = resultSet.getString("link");
                        String paese = resultSet.getString("areageografica");
                        String bio = resultSet.getString("bio");
                        return new Object[]{nome,cognome,sitoWeb,paese, bio};
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object[] result) {
            if (!isCancelled()) {
                popUpModificaCampiProfilo.setFields(result);
            }
        }

    }
    private class UpdateFieldsTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    String email = strings[0];
                    String nome = strings[1];
                    String cognome = strings[2];
                    String link = strings[3];
                    String paese = strings[4];
                    String bio = strings[5];
                    Statement statement = connection.createStatement();
                    int rowsAffected = statement.executeUpdate("UPDATE acquirente SET nome = '" + nome + "', cognome = '" + cognome + "', link = '" + link + "', areageografica = '" + paese + "', bio = '" + bio + "' WHERE indirizzo_email = '" + email + "'");
                    return rowsAffected > 0;
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
                if (result) {
                    Toast.makeText(popUpModificaCampiProfilo.getContext(), "Valori aggiornati con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(popUpModificaCampiProfilo.getContext(), "Errore durante l'aggiornamento dei valori", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}