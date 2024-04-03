package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.SchermataPartecipazioneAste;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SchermataPartecipazioneAsteDAO {
    private Connection connection;
    private SchermataPartecipazioneAste schermataPartecipazioneAste;
    private String email;
    private String tipoUtente;
    public SchermataPartecipazioneAsteDAO(SchermataPartecipazioneAste schermataPartecipazioneAste, String email, String tipoUtente){
        this.schermataPartecipazioneAste = schermataPartecipazioneAste;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new SchermataPartecipazioneAsteDAO.DatabaseTask().execute("open");
    }
    public void partecipazioneAste(){
        if(tipoUtente.equals("acquirente")) {
            new SchermataPartecipazioneAsteDAO.PartecipazioneAsteAcquirente().execute();
        }else{
            new SchermataPartecipazioneAsteDAO.PartecipazioneAsteVenditore().execute();
        }
    }
    public void closeConnection() {
        new SchermataPartecipazioneAsteDAO.DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("FragmentProfiloDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("FragmentProfiloDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FragmentProfiloDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(Object result) {
        }
    }

    private class PartecipazioneAsteAcquirente extends AsyncTask<String, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(String... Void) {
            try {
                connection = DatabaseHelper.getConnection();
                ArrayList<Object> astaItems = new ArrayList<>();
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    String queryAsteInglese = "SELECT a.* FROM asta_allinglese a JOIN partecipazioneAstaAllInglese p ON a.id = p.idAstaInglese JOIN acquirente" +
                            " ac ON p.indirizzo_email = ac.indirizzo_email WHERE ac.indirizzo_email = '" + email + "' AND condizione = 'aperta' ";
                    ResultSet resultSetAsteInglese = statement.executeQuery(queryAsteInglese);
                    while (resultSetAsteInglese.next()) {
                        int id = resultSetAsteInglese.getInt("id");
                        String nome = resultSetAsteInglese.getString("nome");
                        String descrizione = resultSetAsteInglese.getString("descrizione");
                        byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                        Bitmap foto = null;
                        if (fotoBytes != null) {
                            foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                        } else {
                            // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                            // Ecco un esempio di impostazione di un'immagine predefinita
                            foto = BitmapFactory.decodeResource(schermataPartecipazioneAste.getResources(), R.drawable.no_image_available);
                        }
                        String baseAsta = resultSetAsteInglese.getString("baseAsta");
                        String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                        String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                        String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                        String condizione = resultSetAsteInglese.getString("condizione");
                        String emailVenditore = resultSetAsteInglese.getString("id_venditore");

                        AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione, emailVenditore);
                        astaItems.add(astaIngleseItem);
                    }
                    resultSetAsteInglese.close();
                    statement.close();
                    connection.close();
                }
                return astaItems;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SchermataPartecipazioneAsteDAO acquirente", "Errore durante l'operazione su DB", e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Object> result) {
            schermataPartecipazioneAste.updatePartecipazioni(result);
        }
    }
    private class PartecipazioneAsteVenditore extends AsyncTask<String, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(String... Void) {
            try {
                connection = DatabaseHelper.getConnection();
                ArrayList<Object> astaItems = new ArrayList<>();
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    String queryAsteInverse = "SELECT a.* FROM asta_inversa a JOIN partecipazioneAstaInversa p ON a.id = p.idAstaInversa JOIN venditore" +
                            " ac ON p.indirizzo_email = ac.indirizzo_email WHERE ac.indirizzo_email = '" + email + "' AND condizione = 'aperta' ";
                    ResultSet resultSetAsteInverse = statement.executeQuery(queryAsteInverse);

                    while (resultSetAsteInverse.next()) {
                        int id = resultSetAsteInverse.getInt("id");
                        String nome = resultSetAsteInverse.getString("nome");
                        String descrizione = resultSetAsteInverse.getString("descrizione");
                        byte[] fotoBytes = resultSetAsteInverse.getBytes("path_immagine");
                        Bitmap foto = null;
                        if (fotoBytes != null) {
                            foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                        } else {
                            // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                            // Ecco un esempio di impostazione di un'immagine predefinita
                            foto = BitmapFactory.decodeResource(schermataPartecipazioneAste.getResources(), R.drawable.no_image_available);
                        }

                        String prezzoMax = resultSetAsteInverse.getString("prezzoMax");
                        String prezzoAttuale = resultSetAsteInverse.getString("prezzoAttuale");
                        String dataDiScadenza = resultSetAsteInverse.getString("dataDiScadenza");
                        String condizione = resultSetAsteInverse.getString("condizione");
                        String emailAcquirente = resultSetAsteInverse.getString("id_acquirente");

                        AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                        astaItems.add(astaInversaItem);
                    }
                    resultSetAsteInverse.close();
                    statement.close();
                    connection.close();
                }
                return astaItems;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SchermataPartecipazioneAsteDAO acquirente", "Errore durante l'operazione su DB", e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Object> result) {
            schermataPartecipazioneAste.updatePartecipazioni(result);
        }
    }
}
