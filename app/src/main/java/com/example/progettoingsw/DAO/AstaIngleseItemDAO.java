package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.model.AstaIngleseItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AstaIngleseItemDAO {

    private Connection connection;
    private String email;
    private AcquirenteFragmentHome acquirenteFragmentHome;
    public AstaIngleseItemDAO(AcquirenteFragmentHome acquirenteFragmentHome , String email){//per ora lascio fragmentAcquirente
        this.acquirenteFragmentHome = acquirenteFragmentHome;
        this.email = email;
    }
    public void openConnection() {
        new OpenConnectionTask().execute();
    }

    public void getAsteInglesi() {
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

    private class GetAsteTask extends AsyncTask<Void, Void, List<AstaIngleseItem>> {
        @Override
        protected List<AstaIngleseItem> doInBackground(Void... voids) {
            List<AstaIngleseItem> aste = new ArrayList<>();
            try {
                Connection connection = DatabaseHelper.getConnection(); // Assumi che DatabaseHelper fornisca la connessione al database
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM asta_allinglese WHERE id_venditore = '" + email + "' ");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nome = resultSet.getString("nome");
                        String descrizione = resultSet.getString("descrizione");
                        byte[] immagineBytes = resultSet.getBytes("path_immagine");
                        Bitmap immagine = BitmapFactory.decodeByteArray(immagineBytes, 0, immagineBytes.length);
                        String baseAsta = String.valueOf(resultSet.getFloat("baseAsta"));
                        String condizione = resultSet.getString("condizione");
                        String prezzoAttuale = String.valueOf(resultSet.getFloat("prezzoAttuale"));
                        String rialzoMin = String.valueOf(resultSet.getFloat("rialzoMin"));
                        String intervalloOfferte=String.valueOf(resultSet.getString("intervalloTempoOfferte"));
                        String emailVenditore = resultSet.getString("id_venditore");

                        AstaIngleseItem asta = new AstaIngleseItem( id, nome, descrizione, immagine,  baseAsta,  intervalloOfferte,  rialzoMin,  prezzoAttuale,  condizione,emailVenditore);
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
        protected void onPostExecute(List<AstaIngleseItem> result) {
            if (result != null) {
                // Gestisci il risultato come desiderato, ad esempio passando i dati all'adapter
//                acquirenteFragmentHome.handleProdottiResult(result);
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