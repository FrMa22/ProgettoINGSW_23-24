package com.example.progettoingsw.DAO;
import android.os.AsyncTask;
import android.util.Log;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentProfilo;
import com.example.progettoingsw.gui.venditore.VenditoreFragmentProfilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeMieAsteDAO {

    private Connection connection;
    private LeMieAste leMieAste;

    public LeMieAsteDAO(LeMieAste leMieAste) {
        this.leMieAste = leMieAste;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }



    public void getAsteForEmail(String email) {
        if (email.isEmpty()) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new DatabaseTask().execute("get_aste", email);
    }

    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("LeMieAsteDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("get_aste")) {
                        if (connection != null && !connection.isClosed()) {
                            List<String> asteNames = new ArrayList<>();

                           // Crea la query SQL
                            String sql = "SELECT nome FROM asta_allinglese WHERE id_venditore = ? " +
                                    "UNION " +
                                    "SELECT nome FROM asta_alribasso WHERE id_venditore = ? " +
                                    "UNION " +
                                    "SELECT nome FROM asta_inversa WHERE id_acquirente = ?";

                            // Prepara la dichiarazione
                            PreparedStatement statement = connection.prepareStatement(sql);

                            // Imposta i parametri per la query
                            statement.setString(1, strings[1]);
                            statement.setString(2, strings[1]);
                            statement.setString(3, strings[1]);

                            // Esegui la query
                            ResultSet resultSet = statement.executeQuery();

                            while (resultSet.next()) {
                                asteNames.add(resultSet.getString("nome"));
                            }
                            return new Object[]{asteNames};
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("LeMieAsteDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LeMieAsteDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof Object[]) {
                Object[] asteData = (Object[]) result;
                List<String> asteNames = (List<String>) asteData[0];

                leMieAste.updateAsteNames(asteNames);
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

