package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.view.PreferitiActivity;
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AstePreferiteDAO {


    private Connection connection;
    private PreferitiActivity preferitiActivity;
    private String flagCondizione;

    public AstePreferiteDAO(PreferitiActivity preferitiActivity) {
        this.preferitiActivity = preferitiActivity;
    }

    public void openConnection() {
        new AstePreferiteDAO.DatabaseTask().execute("open");
    }



    public void getAsteForEmailUtente(String email,String tipoUtente) {
        if (email == null || tipoUtente == null ) {
            // Se l'email è vuota, non fare nulla
            return;
        }
        Log.d("getAsteForEmailUtente", "chiamo");
        new AstePreferiteDAO.DatabaseTask().execute("get_aste", email,tipoUtente);
    }

    public void closeConnection() {
        new AstePreferiteDAO.DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected ArrayList<Object> doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("AstePreferiteDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("get_aste")) {
                        if (connection != null && !connection.isClosed()) {
                            Log.d("getAsteForEmailUtente", "entrato");
                            ArrayList<Object> aste = new ArrayList<>();
                            String tipoUtente = strings[2];
                            if (tipoUtente.equals("venditore")){
                                Log.d("getAsteForEmailUtente", "entrato in caso venditore");
                                String queryIngleseVenditore= "SELECT aa.* FROM preferitiVenditore pv JOIN asta_allinglese aa ON pv.id_asta = aa.id WHERE pv.indirizzo_email= ? AND pv.tipo_asta= 'inglese' " ;
                                PreparedStatement statementInglesiVenditore = connection.prepareStatement(queryIngleseVenditore);
                                statementInglesiVenditore.setString(1, strings[1]);
                                ResultSet resultSetAsteIngleseVenditore = statementInglesiVenditore.executeQuery();
                                while (resultSetAsteIngleseVenditore.next()) {
                                    int id = resultSetAsteIngleseVenditore.getInt("id");
                                    Log.d("AstePreferiteDAO", "asta all inglese: id = " + id);
                                    String nome = resultSetAsteIngleseVenditore.getString("nome");
                                    String descrizione = resultSetAsteIngleseVenditore.getString("descrizione");
                                    byte[] fotoBytes = resultSetAsteIngleseVenditore.getBytes("path_immagine");
                                    Bitmap foto = null;
                                    if (fotoBytes != null) {
                                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                                    } else {
                                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                        // Ecco un esempio di impostazione di un'immagine predefinita
                                        foto = BitmapFactory.decodeResource(preferitiActivity.getResources(), R.drawable.no_image_available);
                                    }

                                    String baseAsta = resultSetAsteIngleseVenditore.getString("baseAsta");
                                    String intervalloTempoOfferte = resultSetAsteIngleseVenditore.getString("intervalloTempoOfferte");
                                    String rialzoMin = resultSetAsteIngleseVenditore.getString("rialzoMin");
                                    String prezzoAttuale = resultSetAsteIngleseVenditore.getString("prezzoAttuale");
                                    String condizione = resultSetAsteIngleseVenditore.getString("condizione");
                                    String emailVenditore = resultSetAsteIngleseVenditore.getString("id_venditore");
                                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione,emailVenditore);
                                    aste.add(astaIngleseItem);
                                }
                                resultSetAsteIngleseVenditore.close();
                                statementInglesiVenditore.close();

                                Log.d("AstePreferiteDAO", "asta al ribasso:");
                                String queryAsteRibassoVenditore = "SELECT aa.* FROM preferitiVenditore pv JOIN asta_alribasso aa ON pv.id_asta = aa.id WHERE pv.indirizzo_email= ? AND pv.tipo_asta= 'ribasso' ";
                                PreparedStatement statementRibasso = connection.prepareStatement(queryAsteRibassoVenditore);
                                statementRibasso.setString(1, strings[1]);
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
                                        foto = BitmapFactory.decodeResource(preferitiActivity.getResources(), R.drawable.no_image_available);
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
                                String queryAsteInverse ="SELECT aa.* FROM preferitiVenditore pv JOIN asta_inversa aa ON pv.id_asta = aa.id WHERE pv.indirizzo_email= ? AND pv.tipo_asta= 'inversa' " ;
                                PreparedStatement statementInversa = connection.prepareStatement(queryAsteInverse);
                                statementInversa.setString(1, strings[1]);
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
                                        foto = BitmapFactory.decodeResource(preferitiActivity.getResources(), R.drawable.no_image_available);
                                    }

                                    String prezzoMax = resultSetAsteInversa.getString("prezzoMax");
                                    String prezzoAttuale = prezzoMax;
                                    String dataDiScadenza = resultSetAsteInversa.getString("dataDiScadenza");
                                    String condizione = resultSetAsteInversa.getString("condizione");
                                    String emailAcquirente = resultSetAsteInversa.getString("id_acquirente");
                                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                                    aste.add(astaInversaItem);
                                }
                                resultSetAsteInversa.close();

                                statementInversa.close();

                                return aste;

                            }
                            else if(tipoUtente.equals("acquirente")){
                                {
                                    String queryIngleseAcquirente= "SELECT aa.* FROM preferitiAcquirente pv JOIN asta_allinglese aa ON pv.id_asta = aa.id WHERE pv.indirizzo_email= ? AND pv.tipo_asta= 'inglese' " ;
                                    PreparedStatement statementInglesi = connection.prepareStatement(queryIngleseAcquirente);
                                    statementInglesi.setString(1, strings[1]);
                                    ResultSet resultSetAsteInglese = statementInglesi.executeQuery();
                                    while (resultSetAsteInglese.next()) {
                                        int id = resultSetAsteInglese.getInt("id");
                                        Log.d("AstePreferiteDAO", "asta all inglese: id = " + id);
                                        String nome = resultSetAsteInglese.getString("nome");
                                        String descrizione = resultSetAsteInglese.getString("descrizione");
                                        byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                                        Bitmap foto = null;
                                        if (fotoBytes != null) {
                                            foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                                        } else {
                                            // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                                            // Ecco un esempio di impostazione di un'immagine predefinita
                                            foto = BitmapFactory.decodeResource(preferitiActivity.getResources(), R.drawable.no_image_available);
                                        }

                                        String baseAsta = resultSetAsteInglese.getString("baseAsta");
                                        String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                                        String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                                        String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                                        String condizione = resultSetAsteInglese.getString("condizione");
                                        String emailVenditore = resultSetAsteInglese.getString("id_venditore");
                                        AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione,emailVenditore);
                                        aste.add(astaIngleseItem);
                                    }
                                    resultSetAsteInglese.close();
                                    statementInglesi.close();

                                    Log.d("AstePreferiteDAO", "asta al ribasso:");
                                    String queryAsteRibassoAcquirente = "SELECT aa.* FROM preferitiAcquirente pv JOIN asta_alribasso aa ON pv.id_asta = aa.id WHERE pv.indirizzo_email= ? AND pv.tipo_asta= 'ribasso' ";
                                    PreparedStatement statementRibasso = connection.prepareStatement(queryAsteRibassoAcquirente);
                                    statementRibasso.setString(1, strings[1]);
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
                                            foto = BitmapFactory.decodeResource(preferitiActivity.getResources(), R.drawable.no_image_available);
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


                                    return aste;

                                }
                            }

                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("AstePreferiteDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AstePreferiteDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        protected void onPostExecute(Object result) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            if (result instanceof ArrayList) {
                ArrayList<Object> aste = (ArrayList<Object>) result;
//                if (aste != null) {
//                    preferitiActivity.astePreferite(aste);
//                } else {
//                    // Nessun risultato trovato per l'utente
//                    preferitiActivity.astePreferite(new ArrayList<>());
//                }
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
