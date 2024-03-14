package com.example.progettoingsw.controllers_package;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:postgresql://database-1.cvgowm06egxb.eu-north-1.rds.amazonaws.com:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "FozzaNaboli";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Se il driver JDBC non viene trovato, registra un messaggio di log
            System.err.println("Driver JDBC non trovato: " + e.getMessage());
            e.printStackTrace(); // Stampa la traccia delle eccezioni
            throw new SQLException("Driver JDBC non trovato", e);
        } catch (SQLException e) {
            // Se si verifica un problema durante la connessione, registra un messaggio di log
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            e.printStackTrace(); // Stampa la traccia delle eccezioni
            throw e;
        }
    }
}
