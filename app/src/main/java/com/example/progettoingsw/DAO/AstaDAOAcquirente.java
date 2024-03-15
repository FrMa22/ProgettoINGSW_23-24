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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AstaDAOAcquirente {

    private Connection connection;
    private AcquirenteFragmentHome acquirenteFragmentHome;
    private String email;
    private String tipoUtente;

    public AstaDAOAcquirente(AcquirenteFragmentHome acquirenteFragmentHome , String email, String tipoUtente) {
        this.acquirenteFragmentHome = acquirenteFragmentHome;
        this.email = email;
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new AstaDAOAcquirente.OpenConnectionTask().execute();
    }

    public void getAste() {
        new AstaDAOAcquirente.GetAsteTask().execute();
    }

    public void getAsteCategorieAcquirente() {
        if(tipoUtente.equals("acquirente")){
            new GetAsteCategorieAcquirenteTask().execute();
        }else{
            new GetAsteCategorieVenditoreTask().execute();
        }
    }
    public void getCategorie(){
        new GetCategorieTask().execute();
    }

    public void getAsteScadenzaRecente(){
        if(tipoUtente.equals("acquirente")) {
            new GetAsteProssimaScadenzaIngleseTask().execute();
        }else{
            new GetAsteProssimaScadenzaInverseTask().execute();
        }
    }
    public void getAsteNuove(){
        if(tipoUtente.equals("acquirente")) {
            new GetAsteNuoveIngleseTask().execute();
        }else{
            new GetAsteNuoveInverseTask().execute();
        }
    }
    public void closeConnection() {
        new AstaDAOAcquirente.CloseConnectionTask().execute();
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
                Log.d("AstaDAOAcquirente", "asta all inglese:");
                String queryAsteInglese = "SELECT * FROM asta_allinglese";
                ResultSet resultSetAsteInglese = statement.executeQuery(queryAsteInglese);
                while (resultSetAsteInglese.next()) {
                    int id = resultSetAsteInglese.getInt("id");
                    Log.d("AstaDAOAcquirente", "asta all inglese: id = " + id);
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
                    String emailVenditore = resultSetAsteInglese.getString("id_venditore");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione, emailVenditore);
                    astaItems.add(astaIngleseItem);
                }
                resultSetAsteInglese.close();
                Log.d("AstaDAOAcquirente", "asta al ribasso:");
                String queryAsteRibasso = "SELECT * FROM asta_alribasso";
                ResultSet resultSetAsteRibasso = statement.executeQuery(queryAsteRibasso);
                while (resultSetAsteRibasso.next()) {
                    int id = resultSetAsteRibasso.getInt("id");
                    Log.d("AstaDAOAcquirente", "asta al ribasso: id = " + id);
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
                    String emailVenditore = resultSetAsteRibasso.getString("id_venditore");

                    AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione,emailVenditore);
                    astaItems.add(astaRibassoItem);
                }
                resultSetAsteRibasso.close();

                Log.d("AstaDAOAcquirente", "asta inversa:");
                String queryAsteInversa = "SELECT * FROM asta_inversa";
                ResultSet resultSetAsteInversa = statement.executeQuery(queryAsteInversa);
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
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }

                    String prezzoMax = resultSetAsteInversa.getString("prezzoMax");
                    String prezzoAttuale = resultSetAsteInversa.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInversa.getString("dataDiScadenza");
                    String condizione = resultSetAsteInversa.getString("condizione");
                    String emailAcquirente = resultSetAsteInversa.getString("id_acquirente");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
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
                Log.d("AstaDAOAcquirente", "onPostExecute:");
                acquirenteFragmentHome.handleAsteConsigliateResult(astaItems);
            }
        }
    }

    private class GetAsteCategorieAcquirenteTask extends AsyncTask<String, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(String... emails) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try {
                // Query per recuperare le categorie dell'acquirente
                String queryCategorieAcquirente = "SELECT nome  FROM categorieAcquirente WHERE indirizzo_email = ?";
                PreparedStatement stmtCategorieAcquirente = connection.prepareStatement(queryCategorieAcquirente);
                stmtCategorieAcquirente.setString(1, email);
                ResultSet resultSetCategorieAcquirente = stmtCategorieAcquirente.executeQuery();

                // ArrayList per memorizzare le categorie dell'acquirente
                ArrayList<String> categorieAcquirente = new ArrayList<>();
                while (resultSetCategorieAcquirente.next()) {
                    categorieAcquirente.add(resultSetCategorieAcquirente.getString("nome"));
                }
                resultSetCategorieAcquirente.close();
                stmtCategorieAcquirente.close();

                // Query per recuperare le aste inglesi associate alle categorie dell'acquirente
                String queryAsteInglese = "SELECT * FROM asta_allinglese AS a INNER JOIN AsteCategorieAllInglese AS c ON a.id = c.id_asta_allinglese WHERE c.nomeCategoria IN (SELECT nomeCategoria FROM CategorieAcquirente WHERE indirizzo_email = ?)";
                PreparedStatement stmtAsteInglese = connection.prepareStatement(queryAsteInglese);
                stmtAsteInglese.setString(1, email);
                ResultSet resultSetAsteInglese = stmtAsteInglese.executeQuery();

                // Recupera le aste inglesi
                while (resultSetAsteInglese.next()) {
                    int id = resultSetAsteInglese.getInt("id");
                    String nome = resultSetAsteInglese.getString("nome");
                    String descrizione = resultSetAsteInglese.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        Log.d("immagine", "impostata immagine");
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }
                    String baseAsta = resultSetAsteInglese.getString("baseAsta");
                    String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                    String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                    String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInglese.getString("dataDiScadenza");
                    String condizione = resultSetAsteInglese.getString("condizione");
                    String emailVenditore = resultSetAsteInglese.getString("id_venditore");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione,emailVenditore);
                    astaItems.add(astaIngleseItem);
                }
                resultSetAsteInglese.close();
                stmtAsteInglese.close();

                // Query per recuperare le aste al ribasso associate alle categorie dell'acquirente
                String queryAsteRibasso = "SELECT * FROM asta_alribasso AS a INNER JOIN AsteCategorieAlRibasso AS c ON a.id = c.id_asta_alribasso WHERE c.nomeCategoria IN (SELECT nomeCategoria FROM CategorieAcquirente WHERE indirizzo_email = ?)";
                PreparedStatement stmtAsteRibasso = connection.prepareStatement(queryAsteRibasso);
                stmtAsteRibasso.setString(1, email);
                ResultSet resultSetAsteRibasso = stmtAsteRibasso.executeQuery();

                // Recupera le aste al ribasso
                while (resultSetAsteRibasso.next()) {
                    int id = resultSetAsteRibasso.getInt("id");
                    String nome = resultSetAsteRibasso.getString("nome");
                    String descrizione = resultSetAsteRibasso.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteRibasso.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }
                    String prezzoBase = resultSetAsteRibasso.getString("prezzoBase");
                    String intervalloDecrementale = resultSetAsteRibasso.getString("intervalloDecrementale");
                    String decrementoAutomatico = resultSetAsteRibasso.getString("decrementoAutomaticoCifra");
                    String prezzoMin = resultSetAsteRibasso.getString("prezzoMin");
                    String prezzoAttuale = resultSetAsteRibasso.getString("prezzoAttuale");
                    String condizione = resultSetAsteRibasso.getString("condizione");
                    String emailVenditore = resultSetAsteRibasso.getString("id_venditore");

                    AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione,emailVenditore);
                    astaItems.add(astaRibassoItem);
                }
                resultSetAsteRibasso.close();
                stmtAsteRibasso.close();

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            super.onPostExecute(astaItems);
            if (acquirenteFragmentHome != null) {
                Log.d("AstaDAOAcquirente", "onPostExecute: GetAsteCategorie");
                acquirenteFragmentHome.handleAsteConsigliateResult(astaItems);
            }
        }
    }
    private class GetAsteCategorieVenditoreTask extends AsyncTask<String, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(String... emails) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try {
                // Query per recuperare le categorie del venditore
                String queryCategorieVenditore = "SELECT nome  FROM categorieVenditore WHERE indirizzo_email = ?";
                PreparedStatement stmtCategorieVenditore = connection.prepareStatement(queryCategorieVenditore);
                stmtCategorieVenditore.setString(1, email);
                ResultSet resultSetCategorieVenditore = stmtCategorieVenditore.executeQuery();

                // ArrayList per memorizzare le categorie dell'acquirente
                ArrayList<String> categorieVenditore = new ArrayList<>();
                while (resultSetCategorieVenditore.next()) {
                    categorieVenditore.add(resultSetCategorieVenditore.getString("nome"));
                }
                resultSetCategorieVenditore.close();
                stmtCategorieVenditore.close();

                // Query per recuperare le aste inglesi associate alle categorie dell'acquirente
                String queryAsteInverse = "SELECT * FROM asta_inversa AS a INNER JOIN AsteCategorieInversa AS c ON a.id = c.id_asta_inversa WHERE c.nomeCategoria IN (SELECT nomeCategoria FROM CategorieVenditore WHERE indirizzo_email = ?)";
                PreparedStatement stmtAsteInverse = connection.prepareStatement(queryAsteInverse);
                stmtAsteInverse.setString(1, email);
                ResultSet resultSetAsteInverse = stmtAsteInverse.executeQuery();

                // Recupera le aste inverse
                while (resultSetAsteInverse.next()) {
                    int id = resultSetAsteInverse.getInt("id");
                    String nome = resultSetAsteInverse.getString("nome");
                    String descrizione = resultSetAsteInverse.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInverse.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getResources(), R.drawable.img_default);
                    }

                    String prezzoMax = resultSetAsteInverse.getString("prezzoMax");
                    String prezzoAttuale = resultSetAsteInverse.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInverse.getString("dataDiScadenza");
                    String condizione = resultSetAsteInverse.getString("condizione");
                    String emailAcquirente = resultSetAsteInverse.getString("id_acquirente");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                    astaItems.add(astaInversaItem);
                }
                resultSetAsteInverse.close();
                stmtAsteInverse.close();

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            super.onPostExecute(astaItems);
            if (acquirenteFragmentHome != null) {
                Log.d("AstaDAOAcquirente", "onPostExecute: GetAsteCategorie venditore");
                acquirenteFragmentHome.handleAsteConsigliateResult(astaItems);
            }
        }
    }
    private class GetCategorieTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> categorie = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();
                String queryCategorie = "SELECT nome FROM categorieAcquirente WHERE indirizzo_email = '" + email + "' ";
                ResultSet resultSetCategorie = statement.executeQuery(queryCategorie);
                while (resultSetCategorie.next()) {
                    String nome = resultSetCategorie.getString("nome");
                    categorie.add(nome);
                }
                Log.d("numero di categorie" , "numero : " + categorie.size());
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return categorie;
        }

        @Override
        protected void onPostExecute(ArrayList<String> categorie) {
            super.onPostExecute(categorie);
            if (acquirenteFragmentHome != null) {
                Log.d("numero di categorie" , "numero : " + categorie.size());
                acquirenteFragmentHome.setCategorie(categorie);
            }
        }
    }

    private class GetAsteProssimaScadenzaIngleseTask extends AsyncTask<Void, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(Void... voids) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();

                // Query per recuperare le 5 aste inglesi con la data di scadenza più vicina
                String queryAsteInglese = "SELECT * FROM asta_allinglese ORDER BY dataDiScadenza ASC LIMIT 5";
                ResultSet resultSetAsteInglese = statement.executeQuery(queryAsteInglese);
                while (resultSetAsteInglese.next()) {
                    int id = resultSetAsteInglese.getInt("id");
                    String nome = resultSetAsteInglese.getString("nome");
                    String descrizione = resultSetAsteInglese.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }
                    String baseAsta = resultSetAsteInglese.getString("baseAsta");
                    String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                    String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                    String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInglese.getString("dataDiScadenza");
                    String condizione = resultSetAsteInglese.getString("condizione");
                    String emailVenditore = resultSetAsteInglese.getString("id_venditore");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione, emailVenditore);
                    astaItems.add(astaIngleseItem);
                }
                resultSetAsteInglese.close();
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
                // Gestisci il risultato come desiderato
                Log.d("AstaDAOAcquirente", "onPostExecute: GetAsteProssimaScadenza");
                acquirenteFragmentHome.handleAsteInScadenzaResult(astaItems);
            }
        }
    }
    private class GetAsteProssimaScadenzaInverseTask extends AsyncTask<Void, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(Void... voids) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();

                // Query per recuperare le 5 aste inglesi con la data di scadenza più vicina
                String queryAsteInverse = "SELECT * FROM asta_inversa ORDER BY dataDiScadenza ASC LIMIT 5";
                ResultSet resultSetAsteInverse = statement.executeQuery(queryAsteInverse);
                while (resultSetAsteInverse.next()) {
                    int id = resultSetAsteInverse.getInt("id");
                    String nome = resultSetAsteInverse.getString("nome");
                    String descrizione = resultSetAsteInverse.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInverse.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getResources(), R.drawable.img_default);
                    }

                    String prezzoMax = resultSetAsteInverse.getString("prezzoMax");
                    String prezzoAttuale = resultSetAsteInverse.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInverse.getString("dataDiScadenza");
                    String condizione = resultSetAsteInverse.getString("condizione");
                    String emailAcquirente = resultSetAsteInverse.getString("id_acquirente");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                    astaItems.add(astaInversaItem);
                }
                resultSetAsteInverse.close();
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
                // Gestisci il risultato come desiderato
                Log.d("AstaDAOAcquirente", "onPostExecute: GetAsteProssimaScadenza");
                acquirenteFragmentHome.handleAsteInScadenzaResult(astaItems);
            }
        }
    }

    private class GetAsteNuoveIngleseTask extends AsyncTask<String, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(String... emails) {
            ArrayList<Object> astaItems = new ArrayList<>();
                try{
                // Query per recuperare le aste inglesi associate alle categorie dell'acquirente
                String queryAsteInglese = "SELECT * FROM asta_allinglese ORDER BY id DESC LIMIT 5";
                Log.d("GetAsteRecenti" , " inglesi");
                PreparedStatement stmtAsteInglese = connection.prepareStatement(queryAsteInglese);
                ResultSet resultSetAsteInglese = stmtAsteInglese.executeQuery();

                // Recupera le aste inglesi
                while (resultSetAsteInglese.next()) {
                    int id = resultSetAsteInglese.getInt("id");
                    String nome = resultSetAsteInglese.getString("nome");
                    String descrizione = resultSetAsteInglese.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInglese.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        Log.d("immagine", "impostata immagine");
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                        Log.d("foto ok ", " foto : " + foto);
                        Log.d("immagine", "dopo impostata immagine");
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        Log.d("immagine", "impostata default");
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }
                    Log.d("foto ", " foto : " + foto);
                    String baseAsta = resultSetAsteInglese.getString("baseAsta");
                    String intervalloTempoOfferte = resultSetAsteInglese.getString("intervalloTempoOfferte");
                    String rialzoMin = resultSetAsteInglese.getString("rialzoMin");
                    String prezzoAttuale = resultSetAsteInglese.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInglese.getString("dataDiScadenza");
                    String condizione = resultSetAsteInglese.getString("condizione");
                    String emailVenditore = resultSetAsteInglese.getString("id_venditore");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, dataDiScadenza, condizione,emailVenditore);
                    astaItems.add(astaIngleseItem);
                    Log.d("asta inglese", "aggiunta");
                }
                resultSetAsteInglese.close();
                stmtAsteInglese.close();

                // Query per recuperare le aste al ribasso associate alle categorie dell'acquirente
                String queryAsteRibasso = "SELECT * FROM asta_alribasso ORDER BY id DESC LIMIT 5";
                    Log.d("GetAsteRecenti" , " ribasso");
                PreparedStatement stmtAsteRibasso = connection.prepareStatement(queryAsteRibasso);
                ResultSet resultSetAsteRibasso = stmtAsteRibasso.executeQuery();

                // Recupera le aste al ribasso
                while (resultSetAsteRibasso.next()) {
                    int id = resultSetAsteRibasso.getInt("id");
                    String nome = resultSetAsteRibasso.getString("nome");
                    String descrizione = resultSetAsteRibasso.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteRibasso.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        Log.d("immagine", "impostata immagine");
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                        Log.d("foto ok", " foto : " + foto);
                        Log.d("immagine", "dopo impostata immagine");
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        Log.d("immagine", "impostata default");
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getContext().getResources(), R.drawable.img_default);
                    }
                    Log.d("foto ", " foto : " + foto);
                    String prezzoBase = resultSetAsteRibasso.getString("prezzoBase");
                    String intervalloDecrementale = resultSetAsteRibasso.getString("intervalloDecrementale");
                    String decrementoAutomatico = resultSetAsteRibasso.getString("decrementoAutomaticoCifra");
                    String prezzoMin = resultSetAsteRibasso.getString("prezzoMin");
                    String prezzoAttuale = resultSetAsteRibasso.getString("prezzoAttuale");
                    String condizione = resultSetAsteRibasso.getString("condizione");
                    String emailVenditore = resultSetAsteRibasso.getString("id_venditore");

                    AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione,emailVenditore);
                    astaItems.add(astaRibassoItem);
                    Log.d("asta ribasso", "aggiunta");
                }
                resultSetAsteRibasso.close();
                stmtAsteRibasso.close();

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            super.onPostExecute(astaItems);
            if (acquirenteFragmentHome != null) {
                Log.d("GetAsteNuoveIngleseTask", "onPostExecute:");
                acquirenteFragmentHome.handleAsteNuoveResult(astaItems);
            }
        }
    }
    private class GetAsteNuoveInverseTask extends AsyncTask<String, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(String... emails) {
            ArrayList<Object> astaItems = new ArrayList<>();
            try{
                // Query per recuperare le aste inglesi associate alle categorie dell'acquirente
                String queryAsteInverse = "SELECT * FROM asta_inversa ORDER BY id DESC LIMIT 5";
                PreparedStatement stmtAsteInverse = connection.prepareStatement(queryAsteInverse);
                ResultSet resultSetAsteInverse = stmtAsteInverse.executeQuery();

                // Recupera le aste inglesi
                while (resultSetAsteInverse.next()) {
                    int id = resultSetAsteInverse.getInt("id");
                    String nome = resultSetAsteInverse.getString("nome");
                    String descrizione = resultSetAsteInverse.getString("descrizione");
                    byte[] fotoBytes = resultSetAsteInverse.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        foto = BitmapFactory.decodeResource(acquirenteFragmentHome.getResources(), R.drawable.img_default);
                    }

                    String prezzoMax = resultSetAsteInverse.getString("prezzoMax");
                    String prezzoAttuale = resultSetAsteInverse.getString("prezzoAttuale");
                    String dataDiScadenza = resultSetAsteInverse.getString("dataDiScadenza");
                    String condizione = resultSetAsteInverse.getString("condizione");
                    String emailAcquirente = resultSetAsteInverse.getString("id_acquirente");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale,emailAcquirente);
                    astaItems.add(astaInversaItem);
                }
                resultSetAsteInverse.close();
                stmtAsteInverse.close();



                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            super.onPostExecute(astaItems);
            if (acquirenteFragmentHome != null) {
                Log.d("GetAsteNuoveIngleseTask", "onPostExecute:");
                acquirenteFragmentHome.handleAsteNuoveResult(astaItems);
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
