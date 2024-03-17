package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.SchermataAstaInglese;
import com.example.progettoingsw.gui.SchermataAstaRibasso;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaRibassoItem;

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
    private SchermataAstaRibasso schermataAstaRibasso;
    public AstaRibassoDAO(){

    }
    public AstaRibassoDAO(SchermataAstaRibasso schermataAstaRibasso){
        this.schermataAstaRibasso = schermataAstaRibasso;
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
    public void getAstaRibassoByID(int idAsta) {
        new AstaRibassoDAO.SelectAstaTask().execute(String.valueOf(idAsta));
    }
    public void acquistaAsta(int idAsta, String emailOfferente, Float offerta){
        new AcquistaAstaTask().execute(String.valueOf(idAsta),emailOfferente, String.valueOf(offerta));
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
    private class AcquistaAstaTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    connection = DatabaseHelper.getConnection();
                    int idAsta = Integer.parseInt(strings[0]);
                    if (connection != null && !connection.isClosed()) {
                        // Aggiorna la condizione dell'asta a "chiusa"
                        String queryUpdate = "UPDATE asta_alribasso SET condizione = 'chiusa' WHERE id = ?";
                        PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                        preparedStatementUpdate.setInt(1, idAsta);
                        preparedStatementUpdate.executeUpdate();
                        preparedStatementUpdate.close();
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
            if (result != null) {
                Toast.makeText(schermataAstaRibasso, "Acquisto effettuato con successo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(schermataAstaRibasso, "Errore nell'acquisto", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
