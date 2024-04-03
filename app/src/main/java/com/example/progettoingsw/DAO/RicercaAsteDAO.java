package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gestori_gui.ItemRicercaAste;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentRicercaAsta;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

public class RicercaAsteDAO {

    private Connection connection;
    private AcquirenteFragmentRicercaAsta acquirenteFragmentRicercaAsta;
    private String parolaRicercata;
    private String[] listaCategorieScelte;
    private String ordinamentoPrezzo;
    private String tipoUtente;
    private ItemRicercaAste item;
    private ArrayList<Object> astaItems;

    public RicercaAsteDAO(AcquirenteFragmentRicercaAsta acquirenteFragmentRicercaAsta, String tipoUtente) {
        this.acquirenteFragmentRicercaAsta = acquirenteFragmentRicercaAsta;
        this.tipoUtente = tipoUtente;
    }

    public RicercaAsteDAO(String tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

    public void openConnection() {
        new RicercaAsteDAO.DatabaseTask().execute("open");
    }

    public void ricercaAste(String parolaRicercata, ArrayList<String> listaCategorieScelte, String ordinamentoPrezzo) {
        Log.d("ricerca Aste", "" + parolaRicercata);
        ItemRicercaAste itemRicercaAste = new ItemRicercaAste(parolaRicercata, listaCategorieScelte, ordinamentoPrezzo);
        if (tipoUtente.equals("acquirente")) {
            Log.d("ricercaAste", "chiamo ricercaAstaAcquirente");
            new RicercaAstaAcquirenteTask().execute(itemRicercaAste);
        } else {
            Log.d("ricercaAste", "chiamo ricercaAstaVenditore");
            new RicercaAstaVenditoreTask().execute(itemRicercaAste);
        }
    }


    public void closeConnection() {
        new RicercaAsteDAO.DatabaseTask().execute("close");
    }

    private class DatabaseTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                        Log.d("FragmentProfiloDAO", "Connessione aperta");
                        return null;
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            Log.d("FragmentProfiloDAO", "Connessione chiusa");
                            return null;
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FragmentProfiloDAO", "Errore durante l'operazione su DB", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {

        }


    }

    private class RicercaAstaAcquirenteTask extends AsyncTask<ItemRicercaAste, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(ItemRicercaAste... items) {
            try {
                item = items[0];
                String parolaRicercata = item.getParolaRicercata();
                ArrayList<String> categorieScelte = item.getCategorieScelteList();
                String ordinamentoPrezzo = item.getOrdinamentoPrezzo();

                String query = "";

                if (item.getParolaRicercata() == null ||
                        item.getCategorieScelteList() == null ||
                        item.getOrdinamentoPrezzo() == null ||
                        item.getOrdinamentoPrezzo().isEmpty() ||
                        !(item.getOrdinamentoPrezzo().equals("ASC") || item.getOrdinamentoPrezzo().equals("DESC"))) {
                    return astaItems;
                }
                astaItems = new ArrayList<>();
                ArrayList<Object> asteInglesi = new ArrayList<>();
                ArrayList<Object> asteAlRibasso = new ArrayList<>();
                Log.d("ricercaAsteDAO", "entrato in ricerca acquirente");
                // Query per recuperare le aste inglesi associate alle categorie dell'acquirente
                if (item.getParolaRicercata().isEmpty() && item.getCategorieScelteList().isEmpty()    ) {
                    // Se entrambi sono nulli o vuoti, seleziona tutte le aste senza applicare filtri
                    Log.d("RicercaAstaDAO", "parolaricerca null e categorie null");
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.baseAsta, a.intervalloTempoOfferte, a.rialzoMin, a.prezzoAttuale, a.condizione, a.id_venditore FROM asta_allinglese AS a ";
                    query += " WHERE a.condizione = 'aperta'";
                } else {
                    // Altrimenti, costruisci la query con i filtri appropriati
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.baseAsta, a.intervalloTempoOfferte, a.rialzoMin, a.prezzoAttuale, a.condizione, a.id_venditore FROM asta_allinglese AS a LEFT JOIN AsteCategorieAllInglese AS c ON a.id = c.id_asta_allinglese";
                    query += " WHERE a.condizione = 'aperta'";
                    if (!item.getParolaRicercata().isEmpty()) {
                        Log.d("RicercaAstaDAO", "parolaricerca non null");
                        query += " AND UPPER(a.nome) LIKE UPPER(?)";
                    }
                    if (!item.getCategorieScelteList().isEmpty()) {
                        Log.d("RicercaAstaDAO", " categorie non null");
                        String categoriePlaceholders = String.join(",", Collections.nCopies(item.getCategorieScelteList().size(), "?"));


//                        if (item.getParolaRicercata().isEmpty()) {
//                            query += " WHERE";
//                        } else{
//                            query += " AND";
//                        }
                        query += " AND c.nomeCategoria IN (" + categoriePlaceholders + ")";

                    }
                }
                query += " ORDER BY prezzoAttuale " + ordinamentoPrezzo;
                Log.d("Query SQL", "Query: " + query);
                // Esegui la query SQL per le aste inglesi
                connection = DatabaseHelper.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                if (!parolaRicercata.isEmpty()) {
                    statement.setString(1, parolaRicercata + "%");
                }
                if (!categorieScelte.isEmpty()) {
                    int k;
                    if (parolaRicercata.isEmpty()) {
                        k = 1;
                    } else {
                        k = 2;
                    }
                    Log.d("SIZE ", "size: " + categorieScelte.size());
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        statement.setString(i + k, categorieScelte.get(i));
                    }
                }
                String queryWithValues = query;
                if (!parolaRicercata.isEmpty()) {
                    queryWithValues = queryWithValues.replaceFirst("\\?", "'" + parolaRicercata + "'");
                }
                if (!categorieScelte.isEmpty()) {
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        queryWithValues = queryWithValues.replaceFirst("\\?", "'" + categorieScelte.get(i) + "'");
                    }
                }
                // Stampa della query finale con i valori reali
                Log.d("Query SQL", "Query vera: " + queryWithValues);

                ResultSet resultSet = statement.executeQuery();

                // Processa i risultati della query per le aste inglesi
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    Log.d("ricercaAsteDAO", "nome asta inglese : " + nome);
                    String descrizione = resultSet.getString("descrizione");
                    byte[] fotoBytes = resultSet.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        Log.d("immagine", "impostata immagine");
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        if(acquirenteFragmentRicercaAsta!=null)
                            foto = BitmapFactory.decodeResource(acquirenteFragmentRicercaAsta.getContext().getResources(), R.drawable.no_image_available);
                    }
                    String baseAsta = resultSet.getString("baseAsta");
                    String intervalloTempoOfferte = resultSet.getString("intervalloTempoOfferte");
                    String rialzoMin = resultSet.getString("rialzoMin");
                    String prezzoAttuale = resultSet.getString("prezzoAttuale");
                    String condizione = resultSet.getString("condizione");
                    String emailVenditore = resultSet.getString("id_venditore");

                    AstaIngleseItem astaIngleseItem = new AstaIngleseItem(id, nome, descrizione, foto, baseAsta, intervalloTempoOfferte, rialzoMin, prezzoAttuale, condizione, emailVenditore);
                    astaItems.add(astaIngleseItem);
                }

                resultSet.close();
                statement.close();

                // Query per recuperare le aste al ribasso associate alle categorie dell'acquirente
                if (item.getParolaRicercata().isEmpty() && item.getCategorieScelteList().isEmpty()) {
                    // Se entrambi sono nulli o vuoti, seleziona tutte le aste senza applicare filtri
                    Log.d("RicercaAstaDAO Ribasso", "parolaricerca null e categorie null");
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.prezzoBase, a.intervalloDecrementale, a.decrementoAutomaticoCifra, a.prezzoMin, a.prezzoAttuale, a.condizione, a.id_venditore FROM asta_alribasso AS a ";
                    query += " WHERE a.condizione = 'aperta'";
                } else {
                    // Altrimenti, costruisci la query con i filtri appropriati
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.prezzoBase, a.intervalloDecrementale, a.decrementoAutomaticoCifra, a.prezzoMin, a.prezzoAttuale, a.condizione, a.id_venditore FROM asta_alribasso AS a LEFT JOIN AsteCategorieAlRibasso AS c ON a.id = c.id_asta_alribasso";
                    query += " WHERE a.condizione = 'aperta'";
                    if (!item.getParolaRicercata().isEmpty()) {
                        Log.d("RicercaAstaDAO Ribasso", "parolaricerca non null");
                        query += " AND UPPER(a.nome) LIKE UPPER(?)";
                    }
                    if (!item.getCategorieScelteList().isEmpty()) {
                        Log.d("RicercaAstaDAO Ribasso", " categorie non null");
                        String categoriePlaceholders = String.join(",", Collections.nCopies(item.getCategorieScelteList().size(), "?"));
//                        if (item.getParolaRicercata().isEmpty()) {
//                            query += " WHERE";
//                        } else{
//                            query += " AND";
//                        }
                        query += " AND c.nomeCategoria IN (" + categoriePlaceholders + ")";
                    }
                }
                query += " ORDER BY prezzoAttuale " + ordinamentoPrezzo;
                Log.d("Query SQL Ribasso", "Query: " + query);
                // Esegui la query SQL per le aste al ribasso
                statement = connection.prepareStatement(query);
                if (!parolaRicercata.isEmpty()) {
                    statement.setString(1, parolaRicercata + "%");
                }
                if (!categorieScelte.isEmpty()) {
                    int k;
                    if (parolaRicercata.isEmpty()) {
                        k = 1;
                    } else {
                        k = 2;
                    }
                    Log.d("SIZE ", "size: " + categorieScelte.size());
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        Log.d("categorie inserite ", "inserisco in " + (i + k) + "parola " + categorieScelte.get(i));
                        statement.setString(i + k, categorieScelte.get(i));
                    }
                }
                queryWithValues = query;
                if (!parolaRicercata.isEmpty()) {
                    queryWithValues = queryWithValues.replaceFirst("\\?", "'" + parolaRicercata + "'");
                }
                if (!categorieScelte.isEmpty()) {
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        queryWithValues = queryWithValues.replaceFirst("\\?", "'" + categorieScelte.get(i) + "'");
                    }
                }
                // Stampa della query finale con i valori reali
                Log.d("Query SQL", "Query vera: " + queryWithValues);
                resultSet = statement.executeQuery();

                // Processa i risultati della query per le aste al ribasso
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    Log.d("ricercaAsteDAO", "nome asta ribasso : " + nome);
                    String descrizione = resultSet.getString("descrizione");
                    byte[] fotoBytes = resultSet.getBytes("path_immagine");
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
                        if(acquirenteFragmentRicercaAsta!=null)
                            foto = BitmapFactory.decodeResource(acquirenteFragmentRicercaAsta.getContext().getResources(), R.drawable.no_image_available);
                    }
                    Log.d("foto ", " foto : " + foto);
                    String prezzoBase = resultSet.getString("prezzoBase");
                    String intervalloDecrementale = resultSet.getString("intervalloDecrementale");
                    String decrementoAutomatico = resultSet.getString("decrementoAutomaticoCifra");
                    String prezzoMin = resultSet.getString("prezzoMin");
                    String prezzoAttuale = resultSet.getString("prezzoAttuale");
                    String condizione = resultSet.getString("condizione");
                    String emailVenditore = resultSet.getString("id_venditore");

                    AstaRibassoItem astaRibassoItem = new AstaRibassoItem(id, nome, descrizione, foto, prezzoBase, intervalloDecrementale, decrementoAutomatico, prezzoMin, prezzoAttuale, condizione, emailVenditore);
                    astaItems.add(astaRibassoItem);
                }

                resultSet.close();
                statement.close();

                // Aggiungi tutti gli elementi delle due liste separate all'arraylist principale
                astaItems.addAll(asteInglesi);
                astaItems.addAll(asteAlRibasso);
                connection.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Puntatore con riferimento null");
            } catch (PSQLException e) {
                e.printStackTrace();
                System.out.println("Errore nella query");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            // Aggiorna l'interfaccia utente con i risultati della ricerca dell'utente venditore

            if (astaItems != null) {

                if (acquirenteFragmentRicercaAsta != null) {
                    acquirenteFragmentRicercaAsta.handleAsteRecuperate(astaItems);
                    Toast.makeText(acquirenteFragmentRicercaAsta.getActivity(), "Ricerca effettuata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Ricerca effettuata con successo");
                }

            } else {//astaItems vuota quindi errore
                if (acquirenteFragmentRicercaAsta != null) {
                    Toast.makeText(acquirenteFragmentRicercaAsta.getActivity(), "Errore nella ricerca", Toast.LENGTH_SHORT).show();
                }
                System.out.println("Errore ricerca");
            }


        }
    }

    private class RicercaAstaVenditoreTask extends AsyncTask<ItemRicercaAste, Void, ArrayList<Object>> {
        @Override
        protected ArrayList<Object> doInBackground(ItemRicercaAste... items) {

            try {
                item = items[0];
                String parolaRicercata = item.getParolaRicercata();
                ArrayList<String> categorieScelte = item.getCategorieScelteList();
                String ordinamentoPrezzo = item.getOrdinamentoPrezzo();
                // Costruisci la query SQL base in base al tipo di utente
                String query = "";

                if (item.getParolaRicercata() == null ||
                        item.getCategorieScelteList() == null ||
                        item.getOrdinamentoPrezzo() == null ||
                        item.getOrdinamentoPrezzo().isEmpty() ||
                        !(item.getOrdinamentoPrezzo().equals("ASC") || item.getOrdinamentoPrezzo().equals("DESC"))) {
                    return astaItems;
                }
                astaItems = new ArrayList<>();
                Log.d("ricercaAsteDAO", "entrato in ricerca venditore");
                if (item.getParolaRicercata().isEmpty() && item.getCategorieScelteList().isEmpty() ) {
                    // Se entrambi sono nulli o vuoti, seleziona tutte le aste senza applicare filtri
                    Log.d("RicercaAstaDAO Inversa", "parolaricerca null e categorie null");
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.prezzoMax, a.prezzoAttuale , a.dataDiScadenza , a.condizione, a.id_acquirente FROM asta_inversa AS a ";
                    query += " WHERE a.condizione = 'aperta'";
                } else {
                    // Altrimenti, costruisci la query con i filtri appropriati
                    query = "SELECT DISTINCT a.id, a.nome, a.descrizione, a.path_immagine, a.prezzoMax, a.prezzoAttuale , a.dataDiScadenza ,a.condizione, a.id_acquirente FROM asta_inversa AS a LEFT JOIN AsteCategorieInversa AS c ON a.id = c.id_asta_inversa";
                    query += " WHERE a.condizione = 'aperta'";
                    if (!item.getParolaRicercata().isEmpty()) {
                        Log.d("RicercaAstaDAO", "parolaricerca non null");
                        query += " AND UPPER(a.nome) LIKE UPPER(?)";
                    }
                    if (!item.getCategorieScelteList().isEmpty()) {
                        Log.d("RicercaAstaDAO ", " categorie non null");
                        String categoriePlaceholders = String.join(",", Collections.nCopies(item.getCategorieScelteList().size(), "?"));
                        if (item.getParolaRicercata().isEmpty()) {
                            query += " AND";
                        } else {
                            query += " AND";
                        }
                        query += " c.nomeCategoria IN (" + categoriePlaceholders + ")";
                    }
                }
                query += " ORDER BY prezzoAttuale " + ordinamentoPrezzo;
                Log.d("Query SQL Ribasso", "Query: " + query);
                // Esegui la query SQL per le aste inverse
                connection = DatabaseHelper.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                if (!parolaRicercata.isEmpty()) {
                    statement.setString(1, parolaRicercata + "%");
                }
                if (!categorieScelte.isEmpty()) {
                    int k;
                    if (parolaRicercata.isEmpty()) {
                        k = 1;
                    } else {
                        k = 2;
                    }
                    Log.d("SIZE ", "size: " + categorieScelte.size());
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        Log.d("categorie inserite ", "inserisco in " + (i + k) + "parola " + categorieScelte.get(i));
                        statement.setString(i + k, categorieScelte.get(i));
                    }
                }
                String queryWithValues = query;
                if (!parolaRicercata.isEmpty()) {
                    queryWithValues = queryWithValues.replaceFirst("\\?", "'" + parolaRicercata + "'");
                }
                if (!categorieScelte.isEmpty()) {
                    for (int i = 0; i < categorieScelte.size(); i++) {
                        queryWithValues = queryWithValues.replaceFirst("\\?", "'" + categorieScelte.get(i) + "'");
                    }
                }
                Log.d("Query SQL", "Query vera: " + queryWithValues);
                ResultSet resultSet = statement.executeQuery();

                // Processa i risultati della query per le aste inverse
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    Log.d("ricercaAsteDAO", "nome asta inversa : " + nome);
                    String descrizione = resultSet.getString("descrizione");
                    byte[] fotoBytes = resultSet.getBytes("path_immagine");
                    Bitmap foto = null;
                    if (fotoBytes != null) {
                        foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    } else {
                        // In caso di immagine non disponibile, puoi impostare un'immagine predefinita o lasciare foto come null
                        // Ecco un esempio di impostazione di un'immagine predefinita
                        if(acquirenteFragmentRicercaAsta != null) {
                            foto = BitmapFactory.decodeResource(acquirenteFragmentRicercaAsta.getResources(), R.drawable.no_image_available);
                        }
                    }

                    String prezzoMax = resultSet.getString("prezzoMax");
                    String prezzoAttuale = resultSet.getString("prezzoAttuale");
                    String dataDiScadenza = resultSet.getString("dataDiScadenza");
                    String condizione = resultSet.getString("condizione");
                    String emailAcquirente = resultSet.getString("id_acquirente");

                    AstaInversaItem astaInversaItem = new AstaInversaItem(id, nome, descrizione, foto, prezzoMax, dataDiScadenza, condizione, prezzoAttuale, emailAcquirente);
                    astaItems.add(astaInversaItem);
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Puntatore con riferimento null");
            } catch (PSQLException e) {
                e.printStackTrace();
                System.out.println("Errore nella query");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return astaItems;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> astaItems) {
            // Aggiorna l'interfaccia utente con i risultati della ricerca dell'utente venditore

            if (astaItems != null) {

                if (acquirenteFragmentRicercaAsta != null) {
                    acquirenteFragmentRicercaAsta.handleAsteRecuperate(astaItems);
                    Toast.makeText(acquirenteFragmentRicercaAsta.getActivity(), "Ricerca effettuata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Ricerca effettuata con successo");
                }

            } else {//astaItems vuota quindi errore
                if (acquirenteFragmentRicercaAsta != null) {
                    Toast.makeText(acquirenteFragmentRicercaAsta.getActivity(), "Errore nella ricerca", Toast.LENGTH_SHORT).show();
                }
                System.out.println("Errore ricerca");
            }


        }




    }
}
