package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.FragmentHome;
import com.example.progettoingsw.item.AstaInversaItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AstaInversaItemDAO {

    private Connection connection;
    private String email;
    private FragmentHome fragmentHome;
    public AstaInversaItemDAO(FragmentHome fragmentHome, String email){
        this.fragmentHome = fragmentHome;
        this.email = email;
    }
    public void openConnection() {
        new OpenConnectionTask().execute();
    }

    public void getAsteInverse() {
        new GetAsteTask().execute();
    }

    public void closeConnection() {
        new CloseConnectionTask().execute();
    }

    private class OpenConnectionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = DatabaseHelper.getConnection();
                return "Connessione aperta con successo!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'apertura della connessione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Gestisci il risultato come desiderato
            System.out.println(result);
        }
    }

    private class GetAsteTask extends AsyncTask<Void, Void, List<AstaInversaItem>> {
        @Override
        protected List<AstaInversaItem> doInBackground(Void... voids) {
            List<AstaInversaItem> aste = new ArrayList<>();
            try {
                Connection connection = DatabaseHelper.getConnection(); // Assumi che DatabaseHelper fornisca la connessione al database
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM asta_inversa WHERE id_acquirente = '" + email + "' ");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nome = resultSet.getString("nome");
                        String descrizione = resultSet.getString("descrizione");
                        byte[] immagineBytes = resultSet.getBytes("path_immagine");
                        Bitmap immagine = BitmapFactory.decodeByteArray(immagineBytes, 0, immagineBytes.length);
                        String prezzoMax = String.valueOf(resultSet.getFloat("prezzoMax"));
                        String condizione = resultSet.getString("condizione");
                        String dataScadenza = resultSet.getString("dataDiScadenza");
                        String prezzoAttuale = prezzoMax;
                        String emailAcquirente = resultSet.getString("id_acquirente");

                        AstaInversaItem asta = new AstaInversaItem(id, nome, descrizione,immagine, prezzoMax, dataScadenza, condizione, prezzoAttuale,emailAcquirente);
                        aste.add(asta);
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return aste;
        }

        @Override
        protected void onPostExecute(List<AstaInversaItem> result) {
            if (result != null) {
                // Gestisci il risultato come desiderato, ad esempio passando i dati all'adapter
//                fragmentHome.handleProdottiResult(result);
            }
        }
    }

    private class CloseConnectionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    return "Connessione chiusa con successo!";
                } else {
                    return "Connessione già chiusa.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante la chiusura della connessione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Gestisci il risultato come desiderato
            System.out.println(result);
        }
    }
}