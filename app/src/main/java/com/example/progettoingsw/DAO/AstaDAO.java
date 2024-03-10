package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.example.progettoingsw.model.AstaInversaItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AstaDAO {

    private Connection connection;
    private AcquirenteFragmentHome acquirenteFragmentHome;
    private String email;

    public AstaDAO(AcquirenteFragmentHome acquirenteFragmentHome , String email) {
        this.acquirenteFragmentHome = acquirenteFragmentHome;
        this.email = email;

    }

    public void openConnection() {
        new AstaDAO.OpenConnectionTask().execute();
    }

    public void getAste() {
        new AstaDAO.GetAsteTask().execute();
    }

    public void closeConnection() {
        new AstaDAO.CloseConnectionTask().execute();
    }

    private class OpenConnectionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = DatabaseHelper.getConnection();
                return "Connessione aperta con successo!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante l'apertura della connessione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Gestisci il risultato come desiderato
            System.out.println(result);
        }
    }



    //codice che recupera TUTTE le aste dal DB senza alcun criterio (nè email nè tipo di asta)
    //costruisce un arraylist di object con tutto cio che trova
    private class GetAsteTask extends AsyncTask<Void, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(Void... voids) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();
                Log.d("AstaDAO", "asta all inglese:");
                String queryAsteInglese = "SELECT * FROM asta_allinglese";
                ResultSet resultSetAsteInglese = statement.executeQuery(queryAsteInglese);
                while (resultSetAsteInglese.next()) {
                    int id = resultSetAsteInglese.getInt("id");
                    Log.d("AstaDAO", "asta all inglese: id = " + id);
                    String nome = resultSetAsteInglese.getString("nome");
                    String descrizione = resultSetAsteInglese.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                    Bitmap foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    String baseAsta = resultSetAsteInglese.getString("baseAsta");
                    String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                    String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                    String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInglese.getString("dataDiScadenza");
                    String condizione = resultSetAsteInglese.getString("condizione");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione);
                    astaItems.add(astaIngleseItem);
                }
                resultSetAsteInglese.close();
                Log.d("AstaDAO", "asta al ribasso:");
                String queryAsteRibasso = "SELECT * FROM asta_alribasso";
                ResultSet resultSetAsteRibasso = statement.executeQuery(queryAsteRibasso);
                while (resultSetAsteRibasso.next()) {
                    int id = resultSetAsteRibasso.getInt("id");
                    Log.d("AstaDAO", "asta al ribasso: id = " + id);
                    String nome = resultSetAsteRibasso.getString("nome");
                    String descrizione = resultSetAsteRibasso.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteRibasso.getBytes("path_immagine");
                    Bitmap foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    String prezzoBase = resultSetAsteRibasso.getString("prezzoBase");
                    String intervalloDecrementale = resultSetAsteRibasso.getString("intervalloDecrementale");
                    String decrementoAutomatico = resultSetAsteRibasso.getString("decrementoAutomaticoCifra");
                    String prezzoMin = resultSetAsteRibasso.getString("prezzoMin");
                    String prezzoAttuale = resultSetAsteRibasso.getString("prezzoAttuale");
                    String condizione = resultSetAsteRibasso.getString("condizione");

                    AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione);
                    astaItems.add(astaRibassoItem);
                }
                resultSetAsteRibasso.close();

                Log.d("AstaDAO", "asta inversa:");
                String queryAsteInversa = "SELECT * FROM asta_inversa";
                ResultSet resultSetAsteInversa = statement.executeQuery(queryAsteInversa);
                while (resultSetAsteInversa.next()) {
                    int id = resultSetAsteInversa.getInt("id");
                    Log.d("AstaDAO", "asta inversa: id = " + id);
                    String nome = resultSetAsteInversa.getString("nome");
                    String descrizione = resultSetAsteInversa.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInversa.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }

                    String prezzoMax = resultSetAsteInversa.getString("prezzoMax");
                    String dataDiScadenza = resultSetAsteInversa.getString("dataDiScadenza");
                    String condizione = resultSetAsteInversa.getString("condizione");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione);
                    astaItems.add(astaInversaItem);
                }
                resultSetAsteInversa.close();

                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            super.onPostExecute(astaItems);
            if (acquirenteFragmentHome != null) {
                Log.d("AstaDAO", "onPostExecute:");
                acquirenteFragmentHome.handleProdottiResult(astaItems);
            }
        }
    }

    private class CloseConnectionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    return "Connessione chiusa con successo!";
                } else {
                    return "Connessione già chiusa.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore durante la chiusura della connessione: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Gestisci il risultato come desiderato
            System.out.println(result);
        }
    }
}
