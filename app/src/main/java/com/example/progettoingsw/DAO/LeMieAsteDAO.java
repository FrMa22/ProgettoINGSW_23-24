package com.example.progettoingsw.DAO;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeMieAsteDAO {

    private Connection connection;
    private LeMieAste leMieAste;
    private String flagCondizione;

    public LeMieAsteDAO(LeMieAste leMieAste) {
        this.leMieAste = leMieAste;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }



    public void getAsteForEmail(String email,String condizione) {
        if (email == null || condizione == null ) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        new DatabaseTask().execute("get_aste", email,condizione);
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
                        Log.d("LeMieAsteDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("get_aste")) {
                        if (connection != null && !connection.isClosed()) {
                            ArrayList<Object> aste = new ArrayList<>();


                            //
                            Log.d("LeMieAsteDAO", "asta all inglese:");
                            String queryAsteInglese = "SELECT * FROM asta_allinglese WHERE id_venditore = ? AND condizione=?";
                            PreparedStatement statementInglesi = connection.prepareStatement(queryAsteInglese);
                            statementInglesi.setString(1, strings[1]);
                            statementInglesi.setString(2, strings[2]);
                            ResultSet resultSetAsteInglese = statementInglesi.executeQuery();
                            while (resultSetAsteInglese.next()) {
                                int id = resultSetAsteInglese.getInt("id");
                                Log.d("LeMieAsteDAO", "asta all inglese: id = " + id);
                                String nome = resultSetAsteInglese.getString("nome");
                                String descrizione = resultSetAsteInglese.getString("descrizione");
                                byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                                Bitmap foto = null;
                                if (fotoBytes != null) {
                                    foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                                } else {
                                    // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                    // Ecco un esempio di impostazione di un'immagine predefinita
                                    foto = BitmapFactory.decodeResource(leMieAste.getResources(), R.drawable.img_default);
                                }

                                String baseAsta = resultSetAsteInglese.getString("baseAsta");
                                String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                                String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                                String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                                String dataDiScadenza = resultSetAsteInglese.getString("dataDiScadenza");
                                String condizione = resultSetAsteInglese.getString("condizione");
                                String emailVenditore = resultSetAsteInglese.getString("id_venditore");
                                AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione,emailVenditore);
                                aste.add(astaIngleseItem);
                            }
                            resultSetAsteInglese.close();
                            statementInglesi.close();
                            Log.d("LeMieAsteDAO", "asta al ribasso:");
                            String queryAsteRibasso = "SELECT * FROM asta_alribasso WHERE id_venditore = ? AND condizione=?";
                            PreparedStatement statementRibasso = connection.prepareStatement(queryAsteRibasso);
                            statementRibasso.setString(1, strings[1]);
                            statementRibasso.setString(2, strings[2]);
                            ResultSet resultSetAsteRibasso = statementRibasso.executeQuery();
                            while (resultSetAsteRibasso.next()) {
                                int id = resultSetAsteRibasso.getInt("id");
                                Log.d("AstaDAOAcquirente", "asta al ribasso: id = " + id);
                                String nome = resultSetAsteRibasso.getString("nome");
                                String descrizione = resultSetAsteRibasso.getString("descrizione");
                                byte[] fotoBytes = resultSetAsteRibasso.getBytes("path_immagine");
                                Bitmap foto = null;
                                if (fotoBytes != null) {
                                    foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                                } else {
                                    // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                    // Ecco un esempio di impostazione di un'immagine predefinita
                                    foto = BitmapFactory.decodeResource(leMieAste.getResources(), R.drawable.img_default);
                                }
                                String prezzoBase = resultSetAsteRibasso.getString("prezzoBase");
                                String intervalloDecrementale = resultSetAsteRibasso.getString("intervalloDecrementale");
                                String decrementoAutomatico = resultSetAsteRibasso.getString("decrementoAutomaticoCifra");
                                String prezzoMin = resultSetAsteRibasso.getString("prezzoMin");
                                String prezzoAttuale = resultSetAsteRibasso.getString("prezzoAttuale");
                                String condizione = resultSetAsteRibasso.getString("condizione");
                                String emailVenditore = resultSetAsteRibasso.getString("id_venditore");
                                AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione,emailVenditore);
                                aste.add(astaRibassoItem);
                            }
                            resultSetAsteRibasso.close();
                            statementRibasso.close();

                            Log.d("LeMieAsteDAO", "asta inversa:");
                            String queryAsteInverse = "SELECT * FROM asta_inversa WHERE id_acquirente = ? AND condizione=?";
                            PreparedStatement statementInversa = connection.prepareStatement(queryAsteInverse);
                            statementInversa.setString(1, strings[1]);
                            statementInversa.setString(2, strings[2]);
                            ResultSet resultSetAsteInversa = statementInversa.executeQuery();
                            while (resultSetAsteInversa.next()) {
                                int id = resultSetAsteInversa.getInt("id");
                                Log.d("AstaDAOAcquirente", "asta inversa: id = " + id);
                                String nome = resultSetAsteInversa.getString("nome");
                                String descrizione = resultSetAsteInversa.getString("descrizione");
                                byte[] fotoBytes = resultSetAsteInversa.getBytes("path_immagine");
                                Bitmap foto = null;
                                if (fotoBytes != null) {
                                    foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                                } else {
                                    // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                    // Ecco un esempio di impostazione di un'immagine predefinita
                                    foto = BitmapFactory.decodeResource(leMieAste.getResources(), R.drawable.img_default);
                                }

                                String prezzoMax = resultSetAsteInversa.getString("prezzoMax");
                                String dataDiScadenza = resultSetAsteInversa.getString("dataDiScadenza");
                                String condizione = resultSetAsteInversa.getString("condizione");
                                String prezzoAttuale = prezzoMax;
                                String emailAcquirente = resultSetAsteInversa.getString("id_acquirente");
                                AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                                aste.add(astaInversaItem);
                            }
                            resultSetAsteInversa.close();

                            statementInversa.close();


                            flagCondizione=strings[2];//da passare in una chiamate a le mie aste

                            //
                            return aste;
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
            if (result instanceof ArrayList) {
                ArrayList<Object> aste = (ArrayList<Object>) result;
                if (aste != null) {
                    leMieAste.updateAste(aste, flagCondizione);
                } else {
                    // Nessun risultato trovato per l'utente
                    leMieAste.updateAste(new ArrayList<>(), flagCondizione);
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
}

