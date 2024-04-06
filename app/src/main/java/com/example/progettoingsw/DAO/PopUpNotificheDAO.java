package com.example.progettoingsw.DAO;

import android.os.AsyncTask;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.PopUpNotifiche;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PopUpNotificheDAO {

    private Connection connection;
    private int id;
    private String tipoUtente;

    private PopUpNotifiche popUpNotifiche;

    public PopUpNotificheDAO(PopUpNotifiche popUpNotifiche){
        this.popUpNotifiche =popUpNotifiche;
    }
    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void eliminaNotifica(int id,String tipoUtente ) {
         this.id=id;
         this.tipoUtente=tipoUtente;
        new DatabaseTask().execute("delete");
    }


    public void closeConnection() {
        new DatabaseTask().execute("close");
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
                    } else if (action.equals("delete")) {
                        if (connection != null && !connection.isClosed()) {
                            // Statement statement = connection.createStatement();
                            String query="";

                            if(tipoUtente.equals("acquirente")){query = "DELETE FROM  notificheAcquirente WHERE id = ? ";}
                            if(tipoUtente.equals("venditore")){query = "DELETE FROM  notificheVenditore WHERE id = ? ";}
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, id); // Imposta il secondo parametro (intervalloDecrementale)
                            preparedStatement.executeUpdate();
                            preparedStatement.close();


                            return "Notifica eliminata con successo!";
                        } else {
                            return "Impossibile eliminare la notifica: connessione non aperta.";
                        }

                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            return "Connessione chiusa con successo!";
                        } else {
                            return "Connessione già chiusa.";
                        }
                    }
                }
                return "Azione non supportata.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'operazione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            System.out.println(result);

        }
    }




}
