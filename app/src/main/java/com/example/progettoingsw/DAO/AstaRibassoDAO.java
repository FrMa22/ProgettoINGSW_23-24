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

public class AstaRibassoDAO {

    private Connection connection;
    private String idAsta;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void creaAstaRibasso(String base, String intervallo,String soglia,String min) {
        if (base.isEmpty() || intervallo.isEmpty() || soglia.isEmpty() || min.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        new DatabaseTask().execute("insert", base, intervallo,soglia,min);
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


                            String condizione = "aperta";
                            String id_venditore = "venditore1@example.com";
                            double baseAsta=Double.parseDouble(strings[1]);
                            int intervallo=Integer.parseInt(strings[2]);
                            double soglia=Double.parseDouble(strings[3]);
                            double prezzoMin=Double.parseDouble(strings[4]);
                            double prezzoAttuale = baseAsta;

                            statement.executeUpdate("INSERT INTO asta_alribasso"  + " (prezzoBase,intervalloDecrementale, decrementoAutomaticoCifra, prezzoMin, prezzoAttuale, condizione, id_venditore) " +
                                    "VALUES (" + baseAsta + ", INTERVAL '" + intervallo + " minutes', " + soglia + ", " + prezzoMin + ", "+ prezzoAttuale + ", ' " + condizione + "', '" +id_venditore + "')");
                            //
                            // Ottenimento dell'ID dell'asta appena creata
                            ResultSet resultSet = statement.executeQuery("SELECT LASTVAL()");
                            if (resultSet.next()) {
                                idAsta = resultSet.getString(1); // ID dell'asta
                            }
                            //
                            statement.close();
                            return "Asta al ribasso inserita con successo!";
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
            if(result.equals("Asta al ribasso inserita con successo!")) {
                ProdottoDAO prodottoDao = new ProdottoDAO();
                String nomeProdotto = "persona 2";
                String descrizione = "souls-like";
                prodottoDao.openConnection();
                prodottoDao.creaProdotto(nomeProdotto, descrizione, null, idAsta, "ribasso");
                prodottoDao.closeConnection();
            }
        }
    }
}
