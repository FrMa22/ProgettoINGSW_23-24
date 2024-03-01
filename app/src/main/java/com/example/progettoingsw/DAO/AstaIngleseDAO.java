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

public class AstaIngleseDAO {

    private Connection connection;
    private String idAsta;
    private String nomeP;
    private String descrizioneP;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void creaAstaInglese(String base, String intervallo, String rialzo,String nomeProdotto,String descrizioneProdotto) {
        if (base.isEmpty() || intervallo.isEmpty() || rialzo.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new DatabaseTask().execute("insert", base, intervallo, rialzo,nomeProdotto,descrizioneProdotto);
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

                            //LocalDateTime dataScadenza = LocalDateTime.of(2024, 3, 10, 12, 0, 0);
                            LocalDateTime dataScadenza = LocalDateTime.now();
                            String condizione = "aperta";
                            String id_venditore = "venditore1@example.com";
                            double baseAsta = Double.parseDouble(strings[1]);
                            int intervallo = Integer.parseInt(strings[2]);
                            double rialzoMin = Double.parseDouble(strings[3]);
                            double prezzoAttuale = baseAsta;
                            nomeP=strings[4];
                            descrizioneP=strings[5];

                            // Aggiungere l'intervallo in ore alla data di scadenza
                            dataScadenza = dataScadenza.plusHours(intervallo);

                            // Creazione del formatter per la data
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                            // Formattazione della data
                            String formattedDataScadenza = dataScadenza.format(formatter);

                            statement.executeUpdate("INSERT INTO asta_allinglese" + " (baseAsta,intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione, id_venditore) " +
                                    "VALUES (" + baseAsta + ", INTERVAL '" + intervallo + " hours', " + rialzoMin + ", " + prezzoAttuale + ",'" + formattedDataScadenza + "', ' " + condizione + "', '" + id_venditore + "')");

                            //
                            // Ottenimento dell'ID dell'asta appena creata
                            ResultSet resultSet = statement.executeQuery("SELECT LASTVAL()");
                            if (resultSet.next()) {
                                idAsta = resultSet.getString(1); // ID dell'asta
                            }
                            //
                            statement.close();

                            return "Asta inglese inserita con successo!";
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
            if(result.equals("Asta inglese inserita con successo!")) {
                ProdottoDAO prodottoDao = new ProdottoDAO();
                prodottoDao.openConnection();
                prodottoDao.creaProdotto(nomeP, descrizioneP, null, idAsta, "inglese");
                prodottoDao.closeConnection();
            }
        }
    }
}
