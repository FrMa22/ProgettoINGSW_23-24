package com.example.progettoingsw.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.model.Prodotto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    private AcquirenteFragmentHome acquirenteFragmentHome;
    private Connection connection;

    public ProdottoDAO(AcquirenteFragmentHome acquirenteFragmentHome) {
        this.acquirenteFragmentHome = acquirenteFragmentHome;
    }

    public void openConnection() {
        new DatabaseTask().execute("open");
    }

    public void closeConnection() {
        new DatabaseTask().execute("close");
    }

    public void getProdotti() {
        new DatabaseTask().execute("getProdotti");
    }

    private class DatabaseTask extends AsyncTask<String, Void, List<Prodotto>> {

        @Override
        protected List<Prodotto> doInBackground(String... strings) {
            List<Prodotto> prodotti = new ArrayList<>();
            try {
                if (strings.length > 0) {
                    String action = strings[0];
                    if (action.equals("open")) {
                        connection = DatabaseHelper.getConnection();
                    } else if (action.equals("getProdotti")) {
                        if (connection != null && !connection.isClosed()) {
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM Prodotto");
                            while (resultSet.next()) {
                                Prodotto prodotto = new Prodotto();
                                prodotto.setId(resultSet.getInt("id"));
                                prodotto.setNome(resultSet.getString("nome"));
                                prodotto.setDescrizione(resultSet.getString("descrizione"));
                                prodotto.setPathImmagine(resultSet.getString("path_immagine"));
                                prodotti.add(prodotto);
                            }
                            resultSet.close();
                            statement.close();
                        }
                    } else if (action.equals("close")) {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    }
                }
            } catch (SQLException e) {
                Log.e("ProdottoDAO", "Errore durante l'accesso al database: " + e.getMessage());
            }
            return prodotti;
        }

        @Override
        protected void onPostExecute(List<Prodotto> prodotti) {
            // Questo metodo viene chiamato dopo che doInBackground è completato
            // Puoi mostrare il risultato all'utente o gestirlo in modo appropriato
            acquirenteFragmentHome.handleProdottiResult(prodotti);
        }
    }
}
