package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrazioneSocialDAO {


    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void inserimentoSocial( String social, String profiloSocialRegistrazione,String email, String tipoUtente) {

        new DatabaseTask().execute("insert",social,profiloSocialRegistrazione, email, tipoUtente);
    }

    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    public boolean controlloSocial(String social, String link){
        try {
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM social WHERE nome = '" + social + "' AND link = '" + link + "'");
                return resultSet.next();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                            String tipoUtente = strings[4];
                            String link = strings[2];
                            String social = strings[1];
                            String email = strings[3];
                            boolean check;
                            check = controlloSocial(social, link);
                            if(check==false){
                            String query1 = "INSERT INTO social ( nome,link ) VALUES ( ?, ?)";
                            PreparedStatement statement1 = connection.prepareStatement(query1);
                            statement1.setString(1, social);
                            statement1.setString(2, link);}
                            if(tipoUtente.equals("aquirente")) {
                                String query = "INSERT INTO socialacquirente ( nome,indirizzo_email ) VALUES ( ?, ?)";
                                PreparedStatement statement = connection.prepareStatement(query);
                                statement.setString(1, social);
                                statement.setString(2, email);
                            } else if(tipoUtente.equals("venditore")) {
                                String query = "INSERT INTO socialvenditore ( nome, indirizzo_email) VALUES ( ?, ?)";
                                PreparedStatement statement = connection.prepareStatement(query);
                                statement.setString(1, social);
                                statement.setString(2, email);
                            }
                            return "Social con successo!";
                        } else {
                            return "Impossibile inserire il social  : connessione non aperta.";
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
