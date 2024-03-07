package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RegistrazioneSocialDAO {


    private Connection connection;
    private String email;
    private String tipoUtente;
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

        new DatabaseTask().execute("insertSingolo",nomeSocial,nomeUtenteSocial);
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
                        return "Connessione aperta con successo!";
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
                                return "Social inseriti con successo!";
                            } else {
                                return "Errore: Le liste dei nomi social e dei nomi utente social hanno lunghezze diverse.";
                            }
                        } else {
                            return "Impossibile inserire il social: connessione non aperta.";
                        }

                    }else if(action.equals("insertSingolo")){
                        Log.d("Prima" , "valori : " + params[1].toString() + params[2].toString());
                        if (connection != null && !connection.isClosed()) {
                            String nomeSocial = params[1].toString();
                            String nomeUtenteSocial = params[2].toString();
                            Log.d("Dopo" , "valori : " + params[1].toString() + params[2].toString());
                            String query = "INSERT INTO social" + tipoUtente + " (nome, link, indirizzo_email) VALUES (?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, nomeSocial);
                            statement.setString(2, nomeUtenteSocial);
                            statement.setString(3, email);
                            statement.executeUpdate();
                            return "Social inseriti con successo!";
                        }else {
                            return "Impossibile inserire il social: connessione non aperta.";
                        }
                    }else if (action.equals("close")) {
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
