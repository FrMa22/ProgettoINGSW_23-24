
package com.example.progettoingsw.DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.DatabaseHelper;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AcquirenteHomeDAO {

    private Connection connection;
    private AcquirenteFragmentHome acquirenteFragmentHome;
    private String email;

    public AcquirenteHomeDAO(AcquirenteFragmentHome acquirenteFragmentHome, String email) {
        this.acquirenteFragmentHome = acquirenteFragmentHome;
        this.email = email;
    }

    public void openConnection() {
        new AcquirenteHomeDAO.OpenConnectionTask().execute();
    }


    public void closeConnection() {
        new AcquirenteHomeDAO.CloseConnectionTask().execute();
    }

    private class OpenConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                connection = DatabaseHelper.getConnection();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }




    private class CloseConnectionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }
}
