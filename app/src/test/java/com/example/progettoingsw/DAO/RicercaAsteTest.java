package com.example.progettoingsw.DAO;

import static org.junit.Assert.*;

import android.os.Looper;
import android.util.Log;

import junit.framework.TestCase;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class RicercaAsteTest extends TestCase {
    private RicercaAsteDAO ricercaAsteDAO;
    private CountDownLatch latch;

    // Variabili per catturare l'output di System.out
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp(){
        ricercaAsteDAO = new RicercaAsteDAO("acquirente");
        latch = new CountDownLatch(1);
        // Redirigi System.out verso ByteArrayOutputStream per catturare l'output
        System.setOut(new PrintStream(outContent));
    }

    //casi validi

    @Test
    public void testRicercaAsteParolaTra1e100caratteriAlmenoUnaCategoriaOrdineValido(){
        String parola="key";
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolaTra1e100caratteriNessunaCategoriaOrdineValido(){
        String parola="key";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }






    @Test
    public void testRicercaAsteParolaTra1e100alfanumericiiAlmenoUnaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri 123!";
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolaTra1e100alfanumericiNessunaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri 123!";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }



    @Test
    public void testRicercaAsteParolaOltre100caratteriAlmenoUnaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri che ha molti caratteri in modo tale da poter testare il caso che si abbiano tanti caratteri nella stringa";
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolaOltre100caratteriNessunaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri che ha molti caratteri in modo tale da poter testare il caso che si abbiano tanti caratteri nella stringa";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }


    @Test
    public void testRicercaAsteParolaOltre100alfanumericiAlmenoUnaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri che ha esattamente 101 caratteri! Facciamo in modo che sia proprio così!";
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolaOltre100alfanumericiNessunaCategoriaOrdineValido(){
        String parola="Questa è una stringa di caratteri che ha esattamente 101 caratteri! Facciamo in modo che sia proprio così!";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }




    @Test
    public void testRicercaAsteParolasenzacaratteriAlmenoUnaCategoriaOrdineValido(){
        String parola="";
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolasenzacaratteriNessunaCategoriaOrdineValido(){
        String parola="";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Ricerca effettuata con successo",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    //casi non validi

    @Test
    public void testRicercaAsteParolanullAlmenoUnaCategoriaOrdineValido(){
        String parola=null;
        ArrayList<String> listaCategorie=new ArrayList<>();
        listaCategorie.add("arte");
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore ricerca",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
        catch (NullPointerException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

    @Test
    public void testRicercaAsteParolaTra1e100caratteriCategorianullOrdineValido(){
        String parola="key";
        ArrayList<String> listaCategorie=null;
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore ricerca",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
        catch (NullPointerException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }

    }



    @Test
    public void testRicercaAsteParolaTra1e100caratteriCategoriavuotaOrdinevuoto(){
        String parola="key";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore ricerca",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
        catch (NullPointerException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }

    }

    @Test
    public void testRicercaAsteParolaTra1e100caratteriCategoriavuotaOrdinenullo(){
        String parola="key";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine=null;//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore ricerca",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
        catch (NullPointerException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }

    }

    @Test
    public void testRicercaAsteParolaTra1e100caratteriCategoriavuotaOrdineNonvalido(){
        String parola="key";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="pippo";//anche DESC è un caso altretanto valido come input
        ricercaAsteDAO.ricercaAste(parola,listaCategorie,ordine);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore ricerca",  outContent.toString().trim());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
        catch (NullPointerException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }

    }




    // Ripristina System.out originale dopo il test
    @After
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }



}