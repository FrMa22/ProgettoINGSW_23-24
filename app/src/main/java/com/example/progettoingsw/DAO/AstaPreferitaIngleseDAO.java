    package com.example.progettoingsw.DAO;
    
    import android.os.AsyncTask;
    import android.util.Log;
    
    import com.example.progettoingsw.controllers_package.DatabaseHelper;
    import com.example.progettoingsw.gui.SchermataAstaInglese;
    
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    
    public class AstaPreferitaIngleseDAO {
        private Connection connection;
        private SchermataAstaInglese schermataAstaInglese;
    
        public AstaPreferitaIngleseDAO(SchermataAstaInglese schermataAstaInglese) {
            this.schermataAstaInglese = schermataAstaInglese;
        }
    
        public void openConnection() {
            new AstaPreferitaIngleseDAO.DatabaseTask().execute("open");
        }
    
        public void closeConnection() {
            new AstaPreferitaIngleseDAO.DatabaseTask().execute("close");
        }
    
        public void verificaByID(int id, String email) {
            if (email == null) {
                // Se l'email è vuota, non fare nulla
                return;
            }
            Log.d("VerificaByID" , "id : " + id + "email: " + email);
            new AstaPreferitaIngleseDAO.VerificaTask().execute(email, String.valueOf(id));
        }
    
        public void inserisciByID(int id, String email) {
            if (email == null) {
                // Se l'email è vuota, non fare nulla
                return;
            }
            new AstaPreferitaIngleseDAO.InserimentoTask().execute("inserisci", String.valueOf(id), email);
        }
    
        public void eliminaByID(int id, String email) {
            if (email == null) {
                // Se l'email è vuota, non fare nulla
                return;
            }
            new AstaPreferitaIngleseDAO.EliminazioneTask().execute("elimina", String.valueOf(id), email);
        }
    
        private class DatabaseTask extends AsyncTask<String, Void, Void> {
            protected Void doInBackground(String... strings) {
                try {
                    if (strings.length > 0) {
                        String action = strings[0];
                        if (action.equals("open")) {
                            connection = DatabaseHelper.getConnection();
                            Log.d("AstaPreferitaIngleseDAO", "Connessione aperta");
                            return null;
                        } else if (action.equals("close")) {
                            if (connection != null && !connection.isClosed()) {
                                connection.close();
                                Log.d("AstaPreferitaInglesDAO", "Connessione chiusa");
                                return null;
                            }
                        }
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AstaPreferitaIngleseDAO", "Errore durante l'operazione su DB", e);
                    return null;
                }
            }
    
            protected void onPostExecute() {
                // Questo metodo viene chiamato dopo che doInBackground è completato
            }
        }
        private class VerificaTask extends AsyncTask<String, Void, Boolean> {
            protected Boolean doInBackground(String... strings) {
                try {
                    if (strings.length > 1) {
                        String email = strings[0];
                        int idAsta = Integer.parseInt(strings[1]);
                        if (connection != null && !connection.isClosed()) {
                            String queryIngleseAcquirente = "SELECT pv.* FROM preferitiAcquirente pv  WHERE pv.indirizzo_email= ? AND pv.id_asta= ? AND pv.tipo_asta= 'inglese'";
                            PreparedStatement statementInglesi = connection.prepareStatement(queryIngleseAcquirente);
                            statementInglesi.setString(1, email);
                            statementInglesi.setInt(2, idAsta);
                            ResultSet resultSetAsteInglese = statementInglesi.executeQuery();
                            return resultSetAsteInglese.next(); // Ritorna true se ci sono risultati, false altrimenti
                        }
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AstaPreferitaIngleseDAO", "Errore durante la verifica su DB", e);
                    return false;
                }
            }
    
            protected void onPostExecute(Boolean result) {
                // Chiamato dopo che doInBackground è completato
                // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
                schermataAstaInglese.verificaPreferiti(result);
            }
        }

        private class InserimentoTask extends AsyncTask<String, Void, Boolean> {
            protected Boolean doInBackground(String... strings) {
                try {
                    if (strings.length > 0) {
                        int idAsta = Integer.parseInt(strings[1]);
                        String action = strings[0];
                        if (action.equals("inserisci")) {
                            if (connection != null && !connection.isClosed()) {
                                String query = "INSERT INTO preferitiAcquirente (id_asta,tipo_asta,indirizzo_email) VALUES (?,?,?)";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1, idAsta);
                                preparedStatement.setString(2, "inglese");
                                preparedStatement.setString(3, strings[2]);
                                int rowsInserted = preparedStatement.executeUpdate();
                                return rowsInserted > 0;
                            }
                        }
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AstaPreferitaIngleseDAO", "Errore durante l'inserimento su DB", e);
                    return false;
                }
            }
                protected void onPostExecute(Boolean result) {
                    // Chiamato dopo che doInBackground è completato
                    // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
                    schermataAstaInglese.handleInserimento(result);
                }
    
            }

        private class EliminazioneTask extends AsyncTask<String, Void, Boolean> {
            protected Boolean doInBackground(String... strings) {
                try {
                    if (strings.length > 0) {
                        int idAsta = Integer.parseInt(strings[1]);
                        String action = strings[0];
                        if (action.equals("elimina")) {
                            if (connection != null && !connection.isClosed()) {
                                String query = "DELETE FROM preferitiAcquirente WHERE id_asta=? AND indirizzo_email= ? AND tipo_asta= 'inglese'";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1, idAsta);
                                preparedStatement.setString(2, strings[2]);
                                int rowsDeleted = preparedStatement.executeUpdate();
                                return rowsDeleted > 0;
                            }
                        }
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AstaPreferitaIngleseDAO", "Errore durante l'eliminazione su DB", e);
                    return false;
                }
            }
            protected void onPostExecute(Boolean result) {
                // Chiamato dopo che doInBackground è completato
                // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
                schermataAstaInglese.handleEliminazione(result);
            }
    
        }
    }
    
    
