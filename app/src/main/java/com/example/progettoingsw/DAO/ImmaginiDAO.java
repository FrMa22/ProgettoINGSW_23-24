package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LeMieAste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImmaginiDAO {

    private Connection connection;

    private byte[] immagine;

    public void openConnection() {
        new ImmaginiDAO.DatabaseTask().execute("open");
    }

    public void aggiungiImmagine(byte[] imgData) {
        if (imgData==null || imgData.length<0 ) {
            // Se uno dei campi è vuoto, non fare nulla
            System.out.println("immagine dao vuoto");
            return;
        }
        immagine=imgData;
        new ImmaginiDAO.DatabaseTask().execute("insert");
    }

    public void closeConnection() {
        new ImmaginiDAO.DatabaseTask().execute("close");
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


// Prepara l'istruzione SQL con un segnaposto per il LocalDateTime
                            String query = "INSERT INTO immagini (dati) VALUES (?)";
                            PreparedStatement statement = connection.prepareStatement(query);

// Imposta i valori dei parametri
                            statement.setBytes(1, immagine);

// Esegui l'aggiornamento
                            statement.executeUpdate();
                            statement.close();
                            Log.d("immagine", "immagine inserita con successo");
                            return "Immagine inserita con successo!";
                        } else {
                            return "Impossibile inserire l'immagine: connessione non aperta.";
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
            Log.d("IMMAGINE", result);
            System.out.println(result);
        }
    }

}
