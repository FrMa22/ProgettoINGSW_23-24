package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AcquirenteHomeDAO {

    private Connection connection;
    private AcquirenteFragmentHome acquirenteFragmentHome;
    private String email;

    public AcquirenteHomeDAO(AcquirenteFragmentHome acquirenteFragmentHome, String email) {
        this.acquirenteFragmentHome = acquirenteFragmentHome;
        this.email = email;
    }

    public void openConnection() {
        new AcquirenteHomeDAO.OpenConnectionTask().execute();
    }

    public void findAstaInversaProva() {
        new AcquirenteHomeDAO.FindAstaInversaProvaTask().execute();
    }

    public void closeConnection() {
        new AcquirenteHomeDAO.CloseConnectionTask().execute();
    }

    private class OpenConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                connection = DatabaseHelper.getConnection();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

    private class FindAstaInversaProvaTask extends AsyncTask<String, Void, Object[]> {

        @Override
        protected Object[] doInBackground(String... strings) {
            try {
                if (connection != null && !connection.isClosed()) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM asta_inversa WHERE id_acquirente = '" + email + "'");
                    if (resultSet.next()) {
                        // Estrai i valori dalla tupla
                        String nome = resultSet.getString("nome");
                        String descrizione = resultSet.getString("descrizione");
                        // Estrai l'immagine come array di byte e convertila in un'immagine
                        byte[] immagineBytes = resultSet.getBytes("path_immagine");
                        Bitmap immagine = BitmapFactory.decodeByteArray(immagineBytes, 0, immagineBytes.length);
                        float prezzoMax = resultSet.getFloat("prezzoMax");
                        String condizione = resultSet.getString("condizione");
                        String dataScadenza = resultSet.getString("dataDiScadenza");
                        // Crea un array di oggetti per contenere i valori estratti
                        Object[] result = { nome, descrizione, immagine, prezzoMax, condizione, dataScadenza };
                        return result;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object[] result) {
            if (!isCancelled()) {
                // Imposta i valori nei TextView e ImageView corrispondenti
                if (result != null) {
//                    textView_nome_prova.setText((String) result[0]);
//                    textView_descrizione_prova.setText((String) result[1]);
//                    image_view_prova.setImageBitmap((Bitmap) result[2]);
//                    textView_prezzo_prova.setText(String.valueOf(result[3]));
//                    textView_condizione_prova.setText((String) result[4]);
                    acquirenteFragmentHome.handleProva((String) result[0], (String) result[1], String.valueOf(result[3]), (String) result[5], (String) result[4], (Bitmap) result[2]);
                    // Se hai una data di scadenza, impostala anche se non è stata fornita nell'esempio del database
                    // textView_data_scadenza_prova.setText((String) result[5]);
                } else {
                    // Se non ci sono risultati, mostra un messaggio o fai altre azioni necessarie
                }
            }
        }
    }



    private class CloseConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }
}

