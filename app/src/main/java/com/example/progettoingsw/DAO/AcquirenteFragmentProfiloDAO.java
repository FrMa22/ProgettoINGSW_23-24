package com.example.progettoingsw.DAO;import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentProfilo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcquirenteFragmentProfiloDAO {

    private Connection connection;
    private AcquirenteFragmentProfilo acquirenteFragmentProfilo;

    public AcquirenteFragmentProfiloDAO(AcquirenteFragmentProfilo acquirenteFragmentProfilo) {
        this.acquirenteFragmentProfilo = acquirenteFragmentProfilo;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void findUser(String mail) {
        if (mail.isEmpty()) {
            // Se l'ID è vuoto, non fare nulla
            return;
        }
        new DatabaseTask().execute("find", mail);
    }

    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Acquirente> {

        @Override
        protected Acquirente doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("AcquirenteFragmentProfiloDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("find")) {
                        if (connection != null && !connection.isClosed()) {
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM acquirente WHERE indirizzo_email = '" + strings[1] + "'");
                            Log.d("AcquirenteFragmentProfiloDAO", "result set" + strings[1]);
                            if (resultSet.next()) {
                                Log.d("AcquirenteFragmentProfiloDAO", "result set ok");
                                String nome = resultSet.getString("nome");
                                String cognome = resultSet.getString("cognome");
                                String email = resultSet.getString("indirizzo_email");
                                String sitoWeb = resultSet.getString("link");
                                String paese = resultSet.getString("areageografica");
                                String bio = resultSet.getString("bio");
                                return new Acquirente(nome, cognome, email, sitoWeb, paese,bio);
                            }
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("AcquirenteFragmentProfiloDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AcquirenteFragmentProfiloDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Acquirente acquirente) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (acquirente != null) {
                // Esempio: restituisci l'acquirente al Fragment
                acquirenteFragmentProfilo.updateEditTexts(acquirente);
            } else {
                // L'acquirente non è stato trovato
            }
        }
    }
}
