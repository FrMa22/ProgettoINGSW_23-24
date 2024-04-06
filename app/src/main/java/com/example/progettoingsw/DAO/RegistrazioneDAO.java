package com.example.progettoingsw.DAO;
import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.Registrazione;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class RegistrazioneDAO {
    private Connection connection;
    private Registrazione registrazione;
    private  String email;
    private String tipoUtente;

    public RegistrazioneDAO(Registrazione registrazione, String email, String tipoUtente){
        this.registrazione = registrazione;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void checkEmail( ) {
        new CheckEmailTask().execute();
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
                    }
                }
                return "Azione non supportata.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'operazione: " + e.getMessage();
            }
        }
    }
    public class CheckEmailTask extends AsyncTask<String, Void, Integer> {
        private Connection connection;
        @Override
        protected Integer doInBackground(String... strings) {
            try {
                    connection = DatabaseHelper.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tipoUtente + " WHERE indirizzo_email = '" + email + "'");
                        if (resultSet.next()) {
                            // L'email è già presente nella tabella
                            statement.close();
                            return 1; // Email già presente
                        } else {
                            // L'email non è presente nella tabella
                            statement.close();
                            return 0; // Email non presente
                        }
                    } else {
                        return -1; // Impossibile eseguire la verifica: connessione non aperta
                    }
            } catch (SQLException e) {
                e.printStackTrace();
                return -2; // Errore durante l'operazione
            }
        }
        @Override
        protected void onPostExecute(Integer result) {
            // Qui puoi gestire il risultato in base a ciò che desideri fare con esso
            // Ad esempio, puoi chiamare un metodo specifico per gestire l'esito del check email
            registrazione.handleCheckEmail(result);
        }
    }
}
