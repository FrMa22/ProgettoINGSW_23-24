package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.PopUpControlloPassword;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class PopUpControlloPasswordDAO {
    private Connection connection;
    private PopUpControlloPassword popUpControlloPassword;
    private String email;
    private String tipoUtente;


    public PopUpControlloPasswordDAO(PopUpControlloPassword popUpControlloPassword, String email, String tipoUtente) {
        this.popUpControlloPassword = popUpControlloPassword;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void checkPassword( String password) {
        if (email.isEmpty() || password.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new CheckPasswordTask().execute(password);
    }
    public void updatePassword(String newPassword) {
        if (email.isEmpty() || newPassword.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new UpdatePasswordTask().execute(newPassword);
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
                    Toast.makeText(popUpControlloPassword.getContext(), "Errore durante l'apertura della connessione", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class CheckPasswordTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tipoUtente + " WHERE indirizzo_email = '" + email + "' AND password = '" + strings[0] + "'");
                    return resultSet.next();
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
                Log.d("controllo", "result: " + result + " . ");
                popUpControlloPassword.handleResultPassword(result);
            }
        }

    }
    private class UpdatePasswordTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    int rowsAffected = statement.executeUpdate("UPDATE " + tipoUtente + " SET password = '" + strings[0] + "' WHERE indirizzo_email = '" + email + "'");
                    Log.d("Update", "il valore di rows è: " + rowsAffected + " .");
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
                    Toast.makeText(popUpControlloPassword.getContext(), "Password aggiornata con successo", Toast.LENGTH_SHORT).show();
                    popUpControlloPassword.dismissPopup();
                } else {
                    Toast.makeText(popUpControlloPassword.getContext(), "Errore durante l'aggiornamento della password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
