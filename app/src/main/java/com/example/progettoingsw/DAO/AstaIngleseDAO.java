package com.example.progettoingsw.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.controllers_package.InsertAsta;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.gui.venditore.VenditoreAstaInglese;
import com.example.progettoingsw.model.AstaIngleseItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AstaIngleseDAO {

    private Connection connection;
    private String idAsta;
    private String nomeP;
    private String descrizioneP;
    private byte[] foto;
    private SchermataAstaInglese schermataAstaInglese;
    private VenditoreAstaInglese venditoreAstaInglese;
    public AstaIngleseDAO(){

    }
    public AstaIngleseDAO(SchermataAstaInglese schermataAstaInglese){
        this.schermataAstaInglese = schermataAstaInglese;
    }
    public AstaIngleseDAO(VenditoreAstaInglese venditoreAstaInglese){
        this.venditoreAstaInglese = venditoreAstaInglese;
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
    public void getPrezzoECondizioneAstaByID(int idAsta) {
        new GetPrezzoECondizioneAstaTask().execute(idAsta);
    }
    public void inserisciCategorieAstaInglese(InsertAsta asta) {
        new AstaIngleseDAO.InsertCategorieAstaIngleseTask().execute(asta);
    }
    public void verificaAttualeVincitore(String email_offerente, int idAsta){
        new VerificaOffertaVincenteTask().execute(email_offerente,String.valueOf(idAsta));
    }
    public void getAstaIngleseByID(int idAsta) {
        new SelectAstaTask().execute(String.valueOf(idAsta));
    }
    public void partecipaAstaInglese(int idAsta, String emailOfferente, Float offerta){
        new PartecipaAstaTask().execute(String.valueOf(idAsta),emailOfferente, String.valueOf(offerta));
    }
    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return -1; // Connessione aperta con successo, non restituisce alcun ID
                    } else if (action.equals("insert")) {
                        if (connection != null && !connection.isClosed()) {
                            // Statement statement = connection.createStatement();
                            String condizione = "aperta";
                            String id_venditore = strings[6];
                            double baseAsta = Double.parseDouble(strings[1]);
                            int intervallo = Integer.parseInt(strings[2]);
                            double rialzoMin = Double.parseDouble(strings[3]);
                            double prezzoAttuale = baseAsta;
                            nomeP = strings[4];
                            descrizioneP = strings[5];
                            String intervalloString = strings[2];
                            String query = "INSERT INTO asta_allinglese " +
                                    "(baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione, nome, descrizione, id_venditore, path_immagine) " +
                                    "VALUES (?, ?::interval, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                            preparedStatement.setDouble(1, baseAsta); // Imposta il primo parametro (baseAsta)
                            preparedStatement.setString(2, intervallo + " minutes"); // Imposta il secondo parametro (intervalloTempoOfferte)
                            preparedStatement.setDouble(3, rialzoMin); // Imposta il terzo parametro (rialzoMin)
                            preparedStatement.setDouble(4, prezzoAttuale); // Imposta il quarto parametro (prezzoAttuale)
                            preparedStatement.setString(5, condizione); // Imposta il sesto parametro (condizione)
                            preparedStatement.setString(6, nomeP); // Imposta il settimo parametro (nome)
                            preparedStatement.setString(7, descrizioneP); // Imposta l'ottavo parametro (descrizione)
                            preparedStatement.setString(8, id_venditore); // Imposta il nono parametro (id_venditore)
                            preparedStatement.setBytes(9, foto); // Imposta il decimo parametro (path_immagine)
                            preparedStatement.executeUpdate();

                            // Recupera l'ID generato
                            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1); // Restituisce l'ID generato
                            } else {
                                return -1; // Nessun ID generato
                            }
                        } else {
                            return -1; // Impossibile inserire l'asta: connessione non aperta
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return -1; // Connessione chiusa con successo, non restituisce alcun ID
                        } else {
                            return -1; // Connessione già chiusa, non restituisce alcun ID
                        }
                    }
                }
                return -1; // Azione non supportata o nessun parametro fornito
            } catch (Exception e) {
                e.printStackTrace();
                return -1; // Errore durante l'operazione, non restituisce alcun ID
            }
        }

        @Override
        protected void onPostExecute(Integer generatedId) {
            if (generatedId != -1) {
                Log.d("ID", "L id è : " + generatedId);
                venditoreAstaInglese.handleID(generatedId);
            } else {
                Log.d("ID", "Errore");
            }
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
                                foto = BitmapFactory.decodeResource(schermataAstaInglese.getResources(), R.drawable.no_image_available);
                            }
                            String baseAsta = resultSet.getString("baseAsta");
                            String intervalloTempoOfferte = resultSet.getString("intervalloTempoOfferte");
                            String rialzoMin = resultSet.getString("rialzoMin");
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String condizione = resultSet.getString("condizione");
                            String email_venditore = resultSet.getString("id_venditore");

                            AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione,email_venditore);
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
    private class PartecipaAstaTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[0]);
                    String emailOfferente = strings[1];
                    float offerta = Float.parseFloat(strings[2]);
                    LocalDateTime tempoOfferta = LocalDateTime.now();
                    String stato = "attiva";
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "INSERT INTO partecipazioneAstaAllInglese " +
                                "(idAstaInglese, indirizzo_email, offerta, tempo_offerta, stato) " +
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
    private class InsertCategorieAstaIngleseTask extends AsyncTask<InsertAsta, Void, Void> {
        @Override
        protected Void doInBackground(InsertAsta... insertAstaArray) {
            try {
                if (insertAstaArray.length > 0) {
                    InsertAsta insertAsta = insertAstaArray[0];
                    int idAsta = insertAsta.getIdAsta();
                    Log.d("id recuperato è DAO: " , " id: " + idAsta);
                    ArrayList<String> categorie = insertAsta.getCategorie();
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        // Iterare sulla lista di categorie e inserire ogni categoria per l'asta specificata
                        for (String categoria : categorie) {
                            String query = "INSERT INTO AsteCategorieAllInglese (id_asta_allinglese , nomeCategoria) VALUES (?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);
// Imposta il valore dell'id dell'asta come parametro
                            Log.d("id recuperato è : " , " id in ciclo: " + idAsta);
                            statement.setInt(1, idAsta);
// Imposta il valore della categoria come parametro
                            statement.setString(2, categoria);
                            statement.execute();
                            Log.d("insert" , "inseriti " + categoria + " per " + idAsta + " .");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private class GetPrezzoECondizioneAstaTask extends AsyncTask<Integer, Void, String[]> {

        @Override
        protected String[] doInBackground(Integer... ids) {
            try {
                if (ids.length > 0) {
                    int idAsta = ids[0];
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT prezzoAttuale, condizione FROM asta_allinglese WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, idAsta);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String condizione = resultSet.getString("condizione");
                            return new String[]{prezzoAttuale, condizione};
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                // Aggiorna l'UI con il prezzo attuale e la condizione recuperati dal database
                String prezzoAttuale = result[0];
                String condizione = result[1];
                schermataAstaInglese.getPrezzoeCondizione(prezzoAttuale,condizione);
            } else {
                Log.d("Errore", "Impossibile recuperare il prezzo attuale e la condizione dell'asta");
            }
        }
    }
    private class VerificaOffertaVincenteTask extends AsyncTask<String, Void, Boolean> {

        private String emailOfferente;

        @Override
        protected Boolean doInBackground(String... params) {
            if (params.length >= 2) {
                try {
                    int idAsta = Integer.parseInt(params[1]); // Correzione qui
                    String emailVincente = params[0];
                    connection = DatabaseHelper.getConnection();

                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT indirizzo_email FROM partecipazioneAstaAllInglese " +
                                "WHERE idAstaInglese = ? " +
                                "ORDER BY offerta DESC " +
                                "LIMIT 1"; // Ottieni solo il primo risultato, che corrisponde all'offerta più alta
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, idAsta);

                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String emailOffertaPiùAlta = resultSet.getString("indirizzo_email");
                            return emailVincente.equals(emailOffertaPiùAlta); // Correzione qui
                        } else {
                            return false; // Nessun risultato trovato
                        }
                    } else {
                        Log.e("VerificaOffertaVincente", "Connessione non aperta");
                        return false;
                    }
                } catch (SQLException e) {
                    Log.e("VerificaOffertaVincente", "Errore durante la verifica dell'offerta vincente", e);
                    return false;
                }
            } else {
                Log.e("VerificaOffertaVincente", "Parametri insufficienti");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result != null) {
                schermataAstaInglese.handleOffertaAttualeTua(result);
            } else {
                // Errore durante la verifica o nessun risultato trovato
                // Puoi gestire la situazione qui
            }
        }
    }





}

