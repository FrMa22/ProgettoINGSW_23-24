package com.example.progettoingsw.DAO;
import android.os.AsyncTask;
import android.util.Log;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentProfilo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public void getSocialNamesForEmail(String email) {
        if (email.isEmpty()) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new DatabaseTask().execute("get_social_names", email);
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
                                return new Acquirente(nome, cognome, email, sitoWeb, paese, bio);
                            }
                        }
                    } else if (action.equals("get_social_names")) {
                        if (connection != null && !connection.isClosed()) {
                            List<String> socialNames = new ArrayList<>();
                            List<String> socialLinks = new ArrayList<>();
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT s.nome, s.link FROM social s INNER JOIN socialAcquirente sa ON s.nome = sa.nome WHERE sa.indirizzo_email = '" + strings[1] + "'");
                            while (resultSet.next()) {
                                socialNames.add(resultSet.getString("nome"));
                                socialLinks.add(resultSet.getString("link"));
                                Log.d("AcquirenteFragmentProfiloDAO", "Nome Social: " + resultSet.getString("nome") + ", Link Social: " + resultSet.getString("link")); // Aggiunto
                            }
                            return new Object[]{socialNames, socialLinks};
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
        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof Acquirente) {
                // Esempio: restituisci l'acquirente al Fragment
                acquirenteFragmentProfilo.updateEditTexts((Acquirente) result);
            } else if (result instanceof Object[]) {
                Object[] socialData = (Object[]) result;
                List<String> socialNames = (List<String>) socialData[0];
                List<String> socialLinks = (List<String>) socialData[1];
                acquirenteFragmentProfilo.updateSocialNames(socialNames, socialLinks);
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
