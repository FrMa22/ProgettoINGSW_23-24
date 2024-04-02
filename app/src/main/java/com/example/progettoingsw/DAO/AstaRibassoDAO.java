package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.controllers_package.InsertAsta;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.gui.SchermataAstaRibasso;
import com.example.progettoingsw.gui.venditore.VenditoreAstaRibasso;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AstaRibassoDAO {
    private Connection connection;
    private String idAsta;
    private String nomeP;
    private String descrizioneP;
    private byte[] foto;
    private SchermataAstaRibasso schermataAstaRibasso;
    private VenditoreAstaRibasso venditoreAstaRibasso;
    public AstaRibassoDAO(){

    }
    public AstaRibassoDAO(SchermataAstaRibasso schermataAstaRibasso){
        this.schermataAstaRibasso = schermataAstaRibasso;
    }
    public AstaRibassoDAO(VenditoreAstaRibasso venditoreAstaRibasso){
        this.venditoreAstaRibasso = venditoreAstaRibasso;
    }

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
    public void recuperaInfoAsta(int idAsta) {
        new RecuperaInfoAstaTask().execute(idAsta);
    }

    public void getAstaRibassoByID(int idAsta) {
        new AstaRibassoDAO.SelectAstaTask().execute(String.valueOf(idAsta));
    }
    public void inserisciCategorieAstaRibasso(InsertAsta asta) {
        new AstaRibassoDAO.InsertCategorieAstaRibassoTask().execute(asta);
    }
    public void acquistaAsta(Integer idAsta, String emailOfferente, Float offerta){
        new AcquistaAstaTask().execute(String.valueOf(idAsta),emailOfferente, String.valueOf(offerta));
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
                        return 0; // Connessione aperta con successo, non restituisce alcun ID
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
                            PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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
                            Log.d("CreaAstaRibasso" , "Entrato mo");
                            // Recupera l'ID generato
                            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1); // Restituisce l'ID generato
                            } else {
                                return -1; // Nessun ID generato
                            }
                        } else {
                            Log.d("DatabaseTask AstaRibasso", "Impossibile inserire l'asta: connessione non aperta");
                            return -1; // Impossibile inserire l'asta: connessione non aperta
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return 0; // Connessione chiusa con successo, non restituisce alcun ID
                        } else {
                            Log.d("DatabaseTask AstaRibasso", " Connessione già chiusa, non restituisce alcun ID");
                            return -1; // Connessione già chiusa, non restituisce alcun ID
                        }
                    }
                }
                Log.d("DatabaseTask AstaRibasso", " Azione non supportata o nessun parametro fornito");
                return -1; // Azione non supportata o nessun parametro fornito
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("DatabaseTask AstaRibasso", "Errore durante l'operazione, non restituisce alcun ID");
                return -1; // Errore durante l'operazione, non restituisce alcun ID
            }
        }

        @Override
        protected void onPostExecute(Integer generatedId) {
            if (generatedId != -1 && generatedId != 0) {
                Log.d("ID", "L id è : " + generatedId);
                venditoreAstaRibasso.handleID(generatedId);
            } else if(generatedId == 0){
                Log.d("ID", "tutto ok");
            }else{
                Log.d("ID", "errore");
            }

        }
    }
    private class SelectAstaTask extends AsyncTask<String, Void, AstaRibassoItem> {

        @Override
        protected AstaRibassoItem doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[0]);
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT * FROM asta_alribasso WHERE id = ?";
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
                                foto = BitmapFactory.decodeResource(schermataAstaRibasso.getResources(), R.drawable.img_default);
                            }
                            String prezzoBase = resultSet.getString("prezzoBase");
                            String intervalloDecrementale = resultSet.getString("intervalloDecrementale");
                            String decrementoAutomatico = resultSet.getString("decrementoAutomaticoCifra");
                            String prezzoMin = resultSet.getString("prezzoMin");
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String condizione = resultSet.getString("condizione");
                            String emailVenditore = resultSet.getString("id_venditore");

                            AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione,emailVenditore);
                            return astaRibassoItem;
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
        protected void onPostExecute(AstaRibassoItem astaRibassoItem) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (astaRibassoItem != null) {
                // Operazione completata con successo, puoi fare qualcosa con l'oggetto AstaIngleseItem
                Log.d("Asta recuperata", astaRibassoItem.toString());
                schermataAstaRibasso.setAstaData(astaRibassoItem);
            } else {
                // Operazione fallita o nessun risultato trovato
                Log.d("Errore", "Impossibile recuperare l'asta");
            }
        }
    }
    private class AcquistaAstaTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    connection = DatabaseHelper.getConnection();
                    int idAsta = Integer.parseInt(strings[0]);
                    String email_offerente = strings[1];
                    Float offerta = Float.parseFloat(strings[2]);
                    //forse aggiungere l'if per offerta e di null
                    if (connection != null && !connection.isClosed() && idAsta>0 && email_offerente!=null && !email_offerente.isEmpty() && offerta>0) {
                        // inserisce in vincitoriAstaAlRibasso -> un trigger chiuderà l'asta
                        String queryUpdate = "INSERT INTO vincitoriAstaAlRibasso (idAstaRibasso, indirizzo_email, prezzoAcquisto) VALUES (?,?,?) ";
                        PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                        preparedStatementUpdate.setInt(1, idAsta);
                        preparedStatementUpdate.setString(2, email_offerente);
                        preparedStatementUpdate.setFloat(3, offerta);
                        preparedStatementUpdate.executeUpdate();
                        preparedStatementUpdate.close();
                        return true; // Operazione completata con successo
                    } else {
                        return false; // Connessione non aperta
                    }
                }
                return false; // Parametri non validi
            }catch(PSQLException e){
                e.printStackTrace();
                return false;
            }
            catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                return false; // Errore durante l'operazione
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(schermataAstaRibasso, "Acquisto effettuato con successo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(schermataAstaRibasso, "Errore nell'acquisto", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class InsertCategorieAstaRibassoTask extends AsyncTask<InsertAsta, Void, Void> {
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
                            String query = "INSERT INTO AsteCategorieAlRibasso  (id_asta_alribasso  , nomeCategoria) VALUES (?, ?)";
                            PreparedStatement statement = connection.prepareStatement(query);
                            Log.d("id recuperato è : " , " id in ciclo: " + idAsta);
                            statement.setInt(1, idAsta);
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
    private class RecuperaInfoAstaTask extends AsyncTask<Integer, Void, String[]> {

        @Override
        protected String[] doInBackground(Integer... params) {
            try {
                if (params.length > 0) {
                    int idAsta = params[0];
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        String query = "SELECT intervalloDecrementale, prezzoAttuale, condizione FROM asta_alribasso WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, idAsta);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            String intervalloDecrementale = resultSet.getString("intervalloDecrementale");
                            String prezzoAttuale = resultSet.getString("prezzoAttuale");
                            String condizione = resultSet.getString("condizione");
                            return new String[]{intervalloDecrementale, prezzoAttuale, condizione};
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
        protected void onPostExecute(String[] result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result != null) {
                // Operazione completata con successo, puoi fare qualcosa con i risultati ottenuti
                String intervalloDecrementale = result[0];
                String prezzoAttuale = result[1];
                String condizione = result[2];

                schermataAstaRibasso.getPrezzoCondizioneIntervallo(prezzoAttuale,condizione,intervalloDecrementale);

                // Puoi fare qualcos'altro con i risultati, come aggiornare l'UI
            } else {
                // Operazione fallita o nessun risultato trovato
                Log.d("Errore", "Impossibile recuperare le informazioni dell'asta");
            }
        }
    }

}
