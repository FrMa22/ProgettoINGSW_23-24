package com.example.progettoingsw.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.model.AstaIngleseItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private byte[] foto;
    private SchermataAstaInglese schermataAstaInglese;
    public AstaIngleseDAO(){

    }
    public AstaIngleseDAO(SchermataAstaInglese schermataAstaInglese){
        this.schermataAstaInglese = schermataAstaInglese;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void creaAstaInglese(String base, String intervallo, String rialzo,String nomeProdotto,String descrizioneProdotto,String email,byte[] datiFoto) {
        if (base.isEmpty() || intervallo.isEmpty() || rialzo.isEmpty() || nomeProdotto.isEmpty()  || email.isEmpty()) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        foto=datiFoto;
        new DatabaseTask().execute("insert", base, intervallo, rialzo,nomeProdotto,descrizioneProdotto,email);
    }

    public void getAstaIngleseByID(int idAsta) {
        new SelectAstaTask().execute(String.valueOf(idAsta));
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

                            //LocalDateTime dataScadenza = LocalDateTime.of(2024, 3, 10, 12, 0, 0);
                            LocalDateTime dataScadenza = LocalDateTime.now();
                            String condizione = "aperta";
                            String id_venditore = strings[6];
                            double baseAsta = Double.parseDouble(strings[1]);
                            int intervallo = Integer.parseInt(strings[2]);
                            double rialzoMin = Double.parseDouble(strings[3]);
                            double prezzoAttuale = baseAsta;
                            nomeP=strings[4];
                            descrizioneP=strings[5];

                            // Aggiungere l'intervallo in ore alla data di scadenza
                            dataScadenza = dataScadenza.plusHours(intervallo);

                            String intervalloString=strings[2];

                            // Creazione del formatter per la data
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                            // Formattazione della data
                            String formattedDataScadenza = dataScadenza.format(formatter);



                            String query = "INSERT INTO asta_allinglese " +
                                    "(baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione, nome, descrizione, id_venditore, path_immagine) " +
                                    "VALUES (?, ?::interval, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setDouble(1, baseAsta); // Imposta il primo parametro (baseAsta)
                            preparedStatement.setString(2, intervallo + " hours"); // Imposta il secondo parametro (intervalloTempoOfferte)
                            preparedStatement.setDouble(3, rialzoMin); // Imposta il terzo parametro (rialzoMin)
                            preparedStatement.setDouble(4, prezzoAttuale); // Imposta il quarto parametro (prezzoAttuale)
                            preparedStatement.setTimestamp(5, Timestamp.valueOf(formattedDataScadenza)); // Imposta il quinto parametro (dataDiScadenza)
                            preparedStatement.setString(6, condizione); // Imposta il sesto parametro (condizione)
                            preparedStatement.setString(7, nomeP); // Imposta il settimo parametro (nome)
                            preparedStatement.setString(8, descrizioneP); // Imposta l'ottavo parametro (descrizione)
                            preparedStatement.setString(9, id_venditore); // Imposta il nono parametro (id_venditore)
                            preparedStatement.setBytes(10, foto); // Imposta il decimo parametro (path_immagine)
                            preparedStatement.executeUpdate();
                            preparedStatement.close();


                            //statement.executeUpdate("INSERT INTO asta_allinglese" +
                              //      " (baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione, nome, descrizione, id_venditore, path_immagine) " +
                                //    "VALUES (" + baseAsta + ", INTERVAL '" + intervallo + " hours', " + rialzoMin + ", " + prezzoAttuale + ", '" + formattedDataScadenza + "', '" + condizione + "', '" + nomeP + "', '" + descrizioneP + "', '" + id_venditore + "', '" + foto + "')");

                            //statement.close();

                            return "Asta inglese inserita con successo!";
                        } else {
                            return "Impossibile inserire l'asta: connessione non aperta.";
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
    private class SelectAstaTask extends AsyncTask<String, Void, AstaIngleseItem> {

        @Override
        protected AstaIngleseItem doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[0]);
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT * FROM asta_allinglese WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, idAsta);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String nome = resultSet.getString("nome");
                            String descrizione = resultSet.getString("descrizione");
                            byte[] fotoBytes = resultSet.getBytes("path_immagine");
                            Bitmap foto = null;
                            if (fotoBytes != null) {
                                foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                            } else {
                                // Imposta un'immagine predefinita se l'immagine non è disponibile
                                foto = BitmapFactory.decodeResource(schermataAstaInglese.getResources(), R.drawable.img_default);
                            }
                            String baseAsta = resultSet.getString("baseAsta");
                            String intervalloTempoOfferte = resultSet.getString("intervalloTempoOfferte");
                            String rialzoMin = resultSet.getString("rialzoMin");
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String dataDiScadenza = resultSet.getString("dataDiScadenza");
                            String condizione = resultSet.getString("condizione");

                            AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione);
                            return astaIngleseItem;
                        } else {
                            return null; // Nessuna asta trovata con l'ID specificato
                        }
                    } else {
                        return null; // Connessione non aperta
                    }
                }
                return null; // Nessun ID specificato
            } catch (SQLException e) {
                e.printStackTrace();
                return null; // Errore durante l'operazione
            }
        }

        @Override
        protected void onPostExecute(AstaIngleseItem astaIngleseItem) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (astaIngleseItem != null) {
                // Operazione completata con successo, puoi fare qualcosa con l'oggetto AstaIngleseItem
                Log.d("Asta recuperata", astaIngleseItem.toString());
                schermataAstaInglese.setAstaData(astaIngleseItem);
            } else {
                // Operazione fallita o nessun risultato trovato
                Log.d("Errore", "Impossibile recuperare l'asta");
            }
        }
    }
    }

