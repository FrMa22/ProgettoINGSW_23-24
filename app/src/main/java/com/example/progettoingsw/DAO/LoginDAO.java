package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAO {

    private Connection connection;
    private LoginActivity loginActivity;

    public LoginDAO(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

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

    private class DatabaseTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return true;
                    } else if (action.equals("find")) {
                        if (connection != null && !connection.isClosed()) {
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM acquirente WHERE indirizzo_email = '" + strings[1] + "'");
                            return resultSet.next();
                        } else {
                            return false;
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
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
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            loginActivity.handleLoginResult(result);
        }
    }
}
