package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.PopUpModificaSocial;

import java.sql.Connection;
import java.sql.Statement;

public class PopUpModificaSocialDAO {
    private Connection connection;
    private PopUpModificaSocial popUpModificaSocial;
    private String email;
    private String tipoUtente;

    public PopUpModificaSocialDAO(PopUpModificaSocial popUpModificaSocial, String email, String tipoUtente) {
        this.popUpModificaSocial = popUpModificaSocial;
        this.email=email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new PopUpModificaSocialDAO.DatabaseTask().execute("open");
    }

    public void updateSocial( String nome_vecchio, String link_vecchio, String nome, String link) {
        if (email.isEmpty() || nome.isEmpty() || link.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new UpdateSocialTask().execute( nome_vecchio,link_vecchio, nome,link);

    }public void deleteSocial( String nome_vecchio, String link_vecchio) {
        if (email.isEmpty() ) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new DeleteSocialTask().execute( nome_vecchio,link_vecchio);
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
                    Toast.makeText(popUpModificaSocial.getContext(), "Errore durante l'apertura della connessione", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class UpdateSocialTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            Log.d("UpdateSocialTask", "I valori di nome e link sono : " );
            try {
                if (connection != null && !connection.isClosed()) {
                    String nome_vecchio = strings[0];
                    String link_vecchio = strings[1];
                    String nome = strings[2];
                    String link = strings[3];
                    Statement statement = connection.createStatement();
                    Log.d("PopUp", "I valori di nome e link vecchi sono : " + nome_vecchio + link_vecchio + " e i nuovi sono: " + nome + link);
                    int rowsAffected = statement.executeUpdate("UPDATE social" + tipoUtente + " SET nome = '" + nome + "' , link = '" + link + "' where indirizzo_email = '" + email + "' AND nome = '" + nome_vecchio + "' AND link = '"  + link_vecchio + "' ;");
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
                    Toast.makeText(popUpModificaSocial.getContext(), "Valori aggiornati con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(popUpModificaSocial.getContext(), "Errore durante l'aggiornamento dei valori", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class DeleteSocialTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    String nome_vecchio = strings[0];
                    String link_vecchio = strings[1];
                    Statement statement = connection.createStatement();
                    Log.d("PopUp", "I valori di nome e link vecchi sono : " + nome_vecchio + link_vecchio );
                    int rowsAffected = statement.executeUpdate("DELETE FROM social" + tipoUtente + " WHERE nome = '" + nome_vecchio + "' AND link = '" + link_vecchio + "' AND indirizzo_email = '" + email + "' ;");
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
                    Toast.makeText(popUpModificaSocial.getContext(), "Valori eliminati con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(popUpModificaSocial.getContext(), "Errore durante l'aggiornamento dei valori", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}