package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.gui.SchermataAstaInversa;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;

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
    private byte[] foto;
    private SchermataAstaInversa schermataAstaInversa;
    public AstaInversaDAO(){

    }
    public AstaInversaDAO(SchermataAstaInversa schermataAstaInversa){
        this.schermataAstaInversa = schermataAstaInversa;
    }

    public void openConnection() {
        new AstaInversaDAO.DatabaseTask().execute("open");
    }

    public void creaAstaInversa(String nome, String prezzo,String data,String ora,String descrizione,String email,byte[] datiFoto) {
        if (nome.isEmpty() || prezzo.isEmpty() || data.isEmpty() || ora.isEmpty() || email.isEmpty() ) {
            // Se uno dei campi è vuoto, non fare nulla
            return;
        }
        foto=datiFoto;
        new AstaInversaDAO.DatabaseTask().execute("insert", nome, prezzo,data,ora,descrizione,email);
    }
    public void getAstaInversaByID(int idAsta) {
        new AstaInversaDAO.SelectAstaTask().execute(String.valueOf(idAsta));
    }
    public void partecipaAstaInversa(int idAsta, String emailOfferente, Float offerta){
        new AstaInversaDAO.PartecipaAstaTask().execute(String.valueOf(idAsta),emailOfferente, String.valueOf(offerta));
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
                            String nomeProdotto = strings[1];
                            String id_venditore =strings[6];
                            String condizione="aperta";
                            String descrizioneP=strings[5];

// Definisci il pattern per il formato della stringa con data e orario
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

// Effettua la conversione della stringa in LocalDateTime
                            LocalDateTime localDateTime = LocalDateTime.parse(dataDiScadenza, formatter);

// Prepara l'istruzione SQL con un segnaposto per il LocalDateTime
                            String query = "INSERT INTO asta_inversa (prezzoMax, dataDiScadenza, condizione, id_acquirente,nome,descrizione,path_immagine, prezzoAttuale) VALUES (?, ?, ?, ?,?,?,?,?)";
                            PreparedStatement statement = connection.prepareStatement(query);

// Imposta i valori dei parametri
                            statement.setDouble(1, prezzoMax);
                            statement.setObject(2, localDateTime);
                            statement.setString(3, condizione);
                            statement.setString(4, id_venditore);
                            statement.setString(5, nomeProdotto);
                            statement.setString(6,descrizioneP);
                            statement.setBytes(7,foto);
                            statement.setDouble(8,prezzoMax);

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

    private class SelectAstaTask extends AsyncTask<String, Void, AstaInversaItem> {

        @Override
        protected AstaInversaItem doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[0]);
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT * FROM asta_inversa WHERE id = ?";
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
                                // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                // Ecco un esempio di impostazione di un'immagine predefinita
                                foto = BitmapFactory.decodeResource(schermataAstaInversa.getResources(), R.drawable.img_default);
                            }

                            String prezzoMax = resultSet.getString("prezzoMax");
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String dataDiScadenza = resultSet.getString("dataDiScadenza");
                            String condizione = resultSet.getString("condizione");
                            String email_acquirente = resultSet.getString("id_acquirente");

                            AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,email_acquirente);
                            return astaInversaItem;
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
        protected void onPostExecute(AstaInversaItem astaInversaItem) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (astaInversaItem != null) {
                // Operazione completata con successo, puoi fare qualcosa con l'oggetto AstaIngleseItem
                Log.d("Asta recuperata", astaInversaItem.toString());
                schermataAstaInversa.setAstaData(astaInversaItem);
            } else {
                // Operazione fallita o nessun risultato trovato
                Log.d("Errore", "Impossibile recuperare l'asta");
            }
        }
    }
    private class PartecipaAstaTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[0]);
                    String emailOfferente = strings[1];
                    float offerta = Float.parseFloat(strings[2]);
                    LocalDateTime tempoOfferta = LocalDateTime.now();
                    String stato = "attiva"; // Supponendo che lo stato di partecipazione sia "attiva" per impostazione predefinita

                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "INSERT INTO partecipazioneAstaInversa " +
                                "(idAstaInversa , indirizzo_email, offerta, tempo_offerta, stato) " +
                                "VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, idAsta);
                        preparedStatement.setString(2, emailOfferente);
                        preparedStatement.setFloat(3, offerta);
                        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Utilizzo la data e l'ora correnti come valore predefinito
                        preparedStatement.setString(5, stato);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        return null; // Operazione completata con successo
                    } else {
                        return null; // Connessione non aperta
                    }
                }
                return null; // Parametri non validi
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                return null; // Errore durante l'operazione
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
        }
    }

}
