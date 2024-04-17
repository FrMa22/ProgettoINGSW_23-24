package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.acquirente.FragmentCreaAstaInversa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ImmaginiDAO {

    private Connection connection;

    private byte[] immagine;

    private FragmentCreaAstaInversa astaInv;

    public ImmaginiDAO(FragmentCreaAstaInversa astaInversa) {
        this.astaInv = astaInversa;
    }

    public void openConnection() {
        new ImmaginiDAO.DatabaseTask().execute("open");
    }

    public void aggiungiImmagine(byte[] imgData) {
        if (imgData==null || imgData.length<0 ) {
            // Se uno dei campi è vuoto, non fare nulla
            System.out.println("immagine dao vuoto");
            return;
        }
        immagine=imgData;
        new ImmaginiDAO.DatabaseTask().execute("insert");
    }

    public void closeConnection() {
        new ImmaginiDAO.DatabaseTask().execute("close");
    }

    public void selectTest() {new ImmaginiDAO.DatabaseTask().execute("select");}

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        return null;
                    } else if (action.equals("insert")) {
                        if (connection != null && !connection.isClosed()) {

                        Log.d("Insert" , "entrato");
// Prepara l'istruzione SQL con un segnaposto per il LocalDateTime
                            String query = "INSERT INTO immagini (dati) VALUES (?)";
                            PreparedStatement statement = connection.prepareStatement(query);

// Imposta i valori dei parametri
                            statement.setBytes(1, immagine);

// Esegui l'aggiornamento
                            statement.executeUpdate();
                            statement.close();
                            Log.d("immagine", "immagine inserita con successo");
                            return null;
                        } else {
                            return null;
                        }

                    }

                    else if (action.equals("select")){



                        if (connection != null && !connection.isClosed()) {
                            byte[] dati=null ;
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT dati  FROM immagini WHERE id =3 ");
                            while (resultSet.next()) {
                            dati=resultSet.getBytes("dati");
                            }
                            return new Object[]{dati};
                        }




                    }

                    else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return null;
                        } else {
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof Object[]) {
                Object[] socialData = (Object[]) result;
                immagine = (byte[]) socialData[0];
                //astaInv.updateFoto(immagine);
            } else {
                // Nessun risultato o errore
            }

            // Aggiungi un controllo per verificare se result è null
            if (result != null) {
                Log.d("DatabaseTask", "Result: " + result.toString());
            } else {
                Log.d("DatabaseTask", "Result is null");
            }
        }
    }

}
