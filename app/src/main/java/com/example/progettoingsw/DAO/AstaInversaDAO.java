package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AstaInversaDAO {

    private Connection connection;

    public void openConnection() {
        new AstaInversaDAO.DatabaseTask().execute("open");
    }

    public void creaAstaInversa(String nome, String prezzo,String data,String ora) {
        if (nome.isEmpty() || prezzo.isEmpty() || data.isEmpty() || ora.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new AstaInversaDAO.DatabaseTask().execute("insert", nome, prezzo,data,ora);
    }

    public void closeConnection() {
        new AstaInversaDAO.DatabaseTask().execute("close");
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
                            double prezzoMax = Double.parseDouble(strings[2]);
                            String dataDiScadenza = strings[3] +" " +strings[4]+ ":00";
                            String condizione = strings[1];//IN TEORIA DOVREBBE ESSERE "aperta" solo che non so come fare la ricerca per prodotto dal database
                            String id_venditore = "esempio@email.com";

// Definisci il pattern per il formato della stringa con data e orario
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

// Effettua la conversione della stringa in LocalDateTime
                            LocalDateTime localDateTime = LocalDateTime.parse(dataDiScadenza, formatter);

// Prepara l'istruzione SQL con un segnaposto per il LocalDateTime
                            String query = "INSERT INTO asta_inversa (prezzoMax, dataDiScadenza, condizione, id_acquirente) VALUES (?, ?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);

// Imposta i valori dei parametri
                            statement.setDouble(1, prezzoMax);
                            statement.setObject(2, localDateTime);
                            statement.setString(3, condizione);
                            statement.setString(4, id_venditore);

// Esegui l'aggiornamento
                            statement.executeUpdate();
                            statement.close();

                            return "Asta inversa inserita con successo!";
                        } else {
                            return "Impossibile inserire l'asta: connessione non aperta.";
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
