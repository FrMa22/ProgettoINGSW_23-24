package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProdottoDAO {

    private Connection connection;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void creaProdotto(String nome, String descrizione,String path,String idAsta,String tipoAsta) {
        if (nome.isEmpty() || descrizione.isEmpty() || idAsta.isEmpty() || tipoAsta.isEmpty() ) {
            // Se uno dei campi è vuoto, non fare nulla

            return;
        }
        new DatabaseTask().execute("insert", nome, descrizione,path,idAsta,tipoAsta);
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
                    } else if (action.equals("insert")) {
                        if (connection != null && !connection.isClosed()) {
                            Statement statement = connection.createStatement();


                            String nomeP=strings[1];
                            String descrizioneP=strings[2];
                            int idA=Integer.parseInt(strings[4]);
                            String tipoA=strings[5];
                            if(tipoA.equals("inglese")) {
                                statement.executeUpdate("INSERT INTO prodotto" + " (nome,descrizione, id_asta_allinglese) " +
                                        "VALUES ('" + nomeP + "', '" + descrizioneP + "', " + idA + ")");
                                statement.close();
                                return "Prodotto inserito con successo!";
                            }

                            if(tipoA.equals("ribasso")) {
                                statement.executeUpdate("INSERT INTO prodotto" + " (nome,descrizione, id_asta_alribasso) " +
                                        "VALUES ('" + nomeP + "', '" + descrizioneP + "', " + idA + ")");
                                statement.close();
                                return "Prodotto inserito con successo!";
                            }


                        } else {
                            return "Impossibile inserire il prodotto: connessione non aperta.";
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