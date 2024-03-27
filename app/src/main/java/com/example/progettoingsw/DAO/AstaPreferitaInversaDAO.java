package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.SchermataAstaInversa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AstaPreferitaInversaDAO {
    private Connection connection;
    private SchermataAstaInversa schermataAstaInversa;

    public AstaPreferitaInversaDAO(SchermataAstaInversa schermataAstaInversa) {
        this.schermataAstaInversa = schermataAstaInversa;
    }

    public void openConnection() {
        new AstaPreferitaInversaDAO.DatabaseTask().execute("open");
    }

    public void closeConnection() {
        new AstaPreferitaInversaDAO.DatabaseTask().execute("close");
    }

    public void VerificaByID(int id, String email) {
        if (email == null) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new AstaPreferitaInversaDAO.DatabaseTask().execute("verifica", String.valueOf(id), email);
    }
    public void InserisciByID(int id, String email) {
        if (email == null) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new AstaPreferitaInversaDAO.InserimentoTask().execute("inserisci", String.valueOf(id), email);
    }

    public void EliminaByID(int id, String email) {
        if (email == null) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new AstaPreferitaInversaDAO.EliminazioneTask().execute("elimina", String.valueOf(id), email);
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {
        protected Boolean doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    int idAsta = Integer.parseInt(strings[1]);
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("AstaPreferitaInversaDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("verifica")) {
                        if (connection != null && !connection.isClosed()) {
                            String queryInversa = "SELECT pv.* FROM preferitiVenditore pv  WHERE pv.indirizzo_email= ? AND pv.id_asta= ? AND pv.tipo_asta= 'inversa'";
                            PreparedStatement statementInversa = connection.prepareStatement(queryInversa);
                            statementInversa.setString(1, strings[2]);
                            statementInversa.setInt(2, idAsta);
                            ResultSet resultSetAsteInversa = statementInversa.executeQuery();
                            if (resultSetAsteInversa.equals(null)) {
                                return false;

                            } else {
                                return true;
                            }
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("AstaPreferitaInversaDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AstaPreferitaInversaDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof Boolean) {
                Boolean check = (Boolean) result;
                schermataAstaInversa.Verifica(check);
            }
        }
    }

    private class InserimentoTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[1]);
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("AstaPreferitaInversaDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("inserisci")) {
                        if (connection != null && !connection.isClosed()) {
                            String query = "INSERT INTO preferitiVenditore (id_asta,tipo_asta,indirizzo_email) VALUES (?,?,?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, idAsta);
                            preparedStatement.setString(2, "inversa");
                            preparedStatement.setString(3, strings[2]);
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AstaPreferitaInversaDAO", "Errore durante l'operazione su DB", e);
                return null;
            }

        }

    }

    private class EliminazioneTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    int idAsta = Integer.parseInt(strings[1]);
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("AstaPreferitaInversaDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("elimina")) {
                        if (connection != null && !connection.isClosed()) {
                            String query = "DELETE FROM preferitiVenditore WHERE id_asta=? AND indirizzo_email= ? AND tipo_asta= 'inversa'";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, idAsta);
                            preparedStatement.setString(2, strings[2]);
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AstaPreferitaInversaDAO", "Errore durante l'operazione su DB", e);
                return null;
            }

        }

    }
}
