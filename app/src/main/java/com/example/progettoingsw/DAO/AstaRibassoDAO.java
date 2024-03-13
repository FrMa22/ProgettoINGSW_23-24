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

public class AstaRibassoDAO {

    private Connection connection;
    private String idAsta;
    private String nomeP;
    private String descrizioneP;
    private byte[] foto;

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void creaAstaRibasso(String base, String intervallo,String soglia,String min,String nomeProdotto,String descrizioneProdotto,String email,byte[] datiFoto) {
        if (base.isEmpty() || intervallo.isEmpty() || soglia.isEmpty() || min.isEmpty() || email.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        foto=datiFoto;
        new DatabaseTask().execute("insert", base, intervallo,soglia,min,nomeProdotto,descrizioneProdotto,email);
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
                           // Statement statement = connection.createStatement();


                            String condizione = "aperta";
                            String id_venditore = strings[7];
                            double baseAsta=Double.parseDouble(strings[1]);
                            String intervallo=strings[2];
                            double soglia=Double.parseDouble(strings[3]);
                            double prezzoMin=Double.parseDouble(strings[4]);
                            double prezzoAttuale = baseAsta;
                            nomeP=strings[5];
                            descrizioneP=strings[6];


                            String query = "INSERT INTO asta_alribasso " +
                                    "(prezzoBase, intervalloDecrementale, decrementoAutomaticoCifra, prezzoMin, prezzoAttuale, condizione, nome, descrizione, id_venditore, path_immagine) " +
                                    "VALUES (?, ?::interval, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setDouble(1, baseAsta); // Imposta il primo parametro (prezzoBase)
                            preparedStatement.setString(2, intervallo + " minutes"); // Imposta il secondo parametro (intervalloDecrementale)
                            preparedStatement.setDouble(3, soglia); // Imposta il terzo parametro (decrementoAutomaticoCifra)
                            preparedStatement.setDouble(4, prezzoMin); // Imposta il quarto parametro (prezzoMin)
                            preparedStatement.setDouble(5, prezzoAttuale); // Imposta il quinto parametro (prezzoAttuale)
                            preparedStatement.setString(6, condizione); // Imposta il sesto parametro (condizione)
                            preparedStatement.setString(7, nomeP); // Imposta il settimo parametro (nome)
                            preparedStatement.setString(8, descrizioneP); // Imposta l'ottavo parametro (descrizione)
                            preparedStatement.setString(9, id_venditore); // Imposta il nono parametro (id_venditore)
                            preparedStatement.setBytes(10, foto); // Imposta il decimo parametro (path_immagine)
                            preparedStatement.executeUpdate();
                            preparedStatement.close();



                            //statement.executeUpdate("INSERT INTO asta_alribasso" + " (prezzoBase, intervalloDecrementale, decrementoAutomaticoCifra, prezzoMin, prezzoAttuale, condizione, nome, descrizione, id_venditore,path_immagine) " +
                              //      "VALUES (" + baseAsta + ", INTERVAL '" + intervallo + " minutes', " + soglia + ", " + prezzoMin + ", " + prezzoAttuale + ", '" + condizione + "', '" + nomeP + "', '" + descrizioneP + "', '" + id_venditore + "', '" + foto + "')");
                            //statement.close();


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

        }
    }
}
