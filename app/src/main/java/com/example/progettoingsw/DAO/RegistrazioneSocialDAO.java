package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.PopUpAggiungiSocialProfilo;
import com.example.progettoingsw.gui.acquirente.FragmentProfilo;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RegistrazioneSocialDAO {


    private Connection connection;
    private String email;
    private String tipoUtente;
    private PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo;
    public RegistrazioneSocialDAO(PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo, String email, String tipoUtente){
        this.popUpAggiungiSocialProfilo = popUpAggiungiSocialProfilo;
        this.email=email;
        this.tipoUtente=tipoUtente;
    }
    public RegistrazioneSocialDAO(String email, String tipoUtente){
        this.email=email;
        this.tipoUtente=tipoUtente;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void inserimentoSocial(ArrayList<String> nomeSocial, ArrayList<String> nomeUtenteSocial) {

        new DatabaseTask().execute("insert",nomeSocial,nomeUtenteSocial);
    }
    public void inserimentoSingoloSocial(String nomeSocial,String nomeUtenteSocial) {

        new InsertSingoloAsyncTask().execute(nomeSocial,nomeUtenteSocial);
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



    private class DatabaseTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            try {
                if (params.length > 0) {
                    String action = (String) params[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return "Connessione aperta con successo";
                    } else if (action.equals("insert")) {
                        Log.d("insert", "valori email e tipoutente: " + email + tipoUtente);
                        if (connection != null && !connection.isClosed()) {
                            ArrayList<String> nomeSocial = (ArrayList<String>) params[1];
                            ArrayList<String> nomeUtenteSocial = (ArrayList<String>) params[2];

                            // Controllo della lunghezza degli array
                            if (nomeSocial.size() == nomeUtenteSocial.size()) {
                                for (int i = 0; i < nomeSocial.size(); i++) {
                                    String social = nomeSocial.get(i);
                                    String link = nomeUtenteSocial.get(i);
                                    // Inserimento nel database
                                    String query = "INSERT INTO social" + tipoUtente + " (nome, link, indirizzo_email) VALUES (?, ?, ?)";
                                    PreparedStatement statement = connection.prepareStatement(query);
                                    statement.setString(1, social);
                                    statement.setString(2, link);
                                    statement.setString(3, email);
                                    statement.executeUpdate();
                                }
                                return "ok";
                            } else {
                                return "ok";
                            }
                        } else {
                            return "Problema con la connessione rilevato.";
                        }

                    }else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return "ok";
                        } else {
                            return "ok";
                        }
                    }
                }
            } catch (PSQLException e) {
                e.printStackTrace();
                return "Errore durante l'operazione: Duplicate key value violates unique constraint" ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String resul) {

        }
    }
    private class InsertSingoloAsyncTask extends AsyncTask<Object, Void, Integer> {
            @Override
            protected Integer doInBackground(Object... params) {
                try {
                    if (params.length > 0) {
                        connection = DatabaseHelper.getConnection();
                        if (connection != null && !connection.isClosed()) {
                            String nomeSocial = params[0].toString();
                            String nomeUtenteSocial = params[1].toString();
                            String query = "INSERT INTO social" + tipoUtente + " (nome, link, indirizzo_email) VALUES (?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, nomeSocial);
                            statement.setString(2, nomeUtenteSocial);
                            statement.setString(3, email);
                            statement.executeUpdate();
                            return 1;
                        }else {
                            return 0;
                        }
                    }
            } catch (PSQLException e) {
                e.printStackTrace();
                return -1 ;
                //"Errore durante l'operazione: Duplicate key value violates unique constraint"
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(popUpAggiungiSocialProfilo != null){
                Log.d("RegistrazioneSocialDAO" , "onPostExecute");
                popUpAggiungiSocialProfilo.handleRegistrazioneSocial(result);
            }
        }
    }

}
