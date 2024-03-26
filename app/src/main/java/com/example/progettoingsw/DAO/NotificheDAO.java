package com.example.progettoingsw.DAO;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.controllers_package.NotificheAdapter;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.SchermataNotifiche;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.example.progettoingsw.model.NotificaItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificheDAO {
    private Connection connection;
    private SchermataNotifiche schermataNotifiche;
    private String flagCondizione;
    private String tipoUtenteNotifica;
    private String indirizzo_email;
    private String tipoUtente;
    private AcquirenteMainActivity acquirenteMainActivity;

    public NotificheDAO(SchermataNotifiche schermataNotifiche) {
        this.schermataNotifiche = schermataNotifiche;
    }
    public NotificheDAO(AcquirenteMainActivity acquirenteMainActivity, String indirizzo_email, String tipoUtente){
        this.acquirenteMainActivity = acquirenteMainActivity;
        this.indirizzo_email = indirizzo_email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }
    public void getNotificheForEmail(String email,String tipoUtente) {
        if (email == null || tipoUtente == null ) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        tipoUtenteNotifica=tipoUtente;
        new DatabaseTask().execute("get_notifiche", email);
    }
    public void checkNotifiche(){
        new CheckNotificheTask().execute();
    }
    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected ArrayList<Object> doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("NotificheDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("get_notifiche")) {
                        if (connection != null && !connection.isClosed()) {
                            ArrayList<Object> notifiche = new ArrayList<>();
                            String queryNotifiche="";
                            String emailAcquirente="";
                            String emailVenditore="";

                            //
                             if(tipoUtenteNotifica.equals("acquirente")){ queryNotifiche = "SELECT * FROM notificheAcquirente WHERE id_acquirente = ? ";}
                            if(tipoUtenteNotifica.equals("venditore")){ queryNotifiche = "SELECT * FROM notificheVenditore WHERE id_venditore = ? ";}
                            PreparedStatement statementNotifiche = connection.prepareStatement(queryNotifiche);
                            statementNotifiche.setString(1, strings[1]);//prende l'email utente
                            ResultSet resultSetNotifiche = statementNotifiche.executeQuery();
                            while (resultSetNotifiche.next()) {
                                int id = resultSetNotifiche.getInt("id");
                                Log.d("NotificheDAO", "id  della notifica e' = " + id);
                                String titolo = resultSetNotifiche.getString("titolo");
                                String commento = resultSetNotifiche.getString("commento");
                                if(tipoUtenteNotifica.equals("acquirente")){emailAcquirente = resultSetNotifiche.getString("id_acquirente");emailVenditore=null;}
                                if(tipoUtenteNotifica.equals("venditore")){emailVenditore = resultSetNotifiche.getString("id_venditore"); emailAcquirente=null;}
                                NotificaItem notificaItem = new NotificaItem(id,titolo,commento,emailAcquirente ,emailVenditore ,tipoUtenteNotifica);
                                notifiche.add(notificaItem);
                            }
                            resultSetNotifiche.close();
                            statementNotifiche.close();


                            //
                            return notifiche;
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("NotificheDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("NotificheDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof ArrayList) {
                ArrayList<Object> notifiche = (ArrayList<Object>) result;
                if (notifiche != null) {
                    schermataNotifiche.updateNotifiche(notifiche);
                } else {
                    // Nessun risultato trovato per l'utente
                    schermataNotifiche.updateNotifiche(new ArrayList<>());
                }
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
    private class CheckNotificheTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    String query = "SELECT COUNT(*) AS count FROM notifiche" + tipoUtente + " WHERE id_" + tipoUtente + " = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, indirizzo_email);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        resultSet.close();
                        statement.close();
                        return count;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("CheckNotificheTask", "Errore durante il recupero del numero di notifiche", e);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result != null) {
                acquirenteMainActivity.handleGetNumeroNotifiche(result.intValue());
            } else {
                // Gestisci il caso in cui non è stato possibile recuperare il numero di notifiche
            }
        }
    }

}
