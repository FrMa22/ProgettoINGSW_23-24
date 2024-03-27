package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        new OpenConnectionTask().execute();
    }

    public void findUser(String mail, String password) {
        if (mail.isEmpty()) {
            // Se l'ID è vuoto, non fare nulla
            return;
        }
        new FindUserTask().execute(mail, password);
    }

    public void closeConnection() {
        new CloseConnectionTask().execute();
    }

    private class OpenConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                connection = DatabaseHelper.getConnection();
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

    private class FindUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    String email = strings[0];
                    String password = strings[1];
                    Statement statement = connection.createStatement();

                    // Query per controllare se l'email e la password corrispondono a un record in venditore
                    String queryVenditore = "SELECT * FROM venditore WHERE indirizzo_email = '" + email + "' AND password = '" + password + "'";
                    ResultSet resultSetVenditore = statement.executeQuery(queryVenditore);
                    boolean isVenditorePresent = resultSetVenditore.next();

                    // Query per controllare se l'email e la password corrispondono a un record in acquirente
                    String queryAcquirente = "SELECT * FROM acquirente WHERE indirizzo_email = '" + email + "' AND password = '" + password + "'";
                    ResultSet resultSetAcquirente = statement.executeQuery(queryAcquirente);
                    boolean isAcquirentePresent = resultSetAcquirente.next();

                    if (isVenditorePresent && isAcquirentePresent) {
                        return "doppioAccount";
                    } else if (isVenditorePresent) {
                        return "venditore";
                    } else if (isAcquirentePresent) {
                        return "acquirente";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            if (!isCancelled()) {
                Log.d("LoginDAO on post", "result : " + result);
                loginActivity.handleLoginResult(result);
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

