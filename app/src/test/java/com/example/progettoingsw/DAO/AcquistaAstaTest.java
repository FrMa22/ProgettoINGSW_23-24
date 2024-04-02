package com.example.progettoingsw.DAO;

import static org.junit.Assert.*;

import android.os.Looper;
import android.util.Log;

import junit.framework.TestCase;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class AcquistaAstaTest extends TestCase {
    private AstaRibassoDAO astaRibassoDAO;
    private CountDownLatch latch;

    @Before
    public void setUp(){
        astaRibassoDAO = new AstaRibassoDAO();
        latch = new CountDownLatch(1);
    }

    @Test
    public void testAcquistaAstaIdNegativo(){
        int id = -1;
        String email = "o";
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaId0(){
        int id = 0;
        String email = "o";
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaIdNullo(){
        String email = "o";
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(null,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);

            // Assicurati che il thread principale sia stato eseguito fino al completamento di tutte le operazioni asincrone
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            // Verifica se viene visualizzato il Toast di acquisto non riuscito
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            // Gestisci l'eccezione se il thread viene interrotto durante l'attesa
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaEmailOfferenteVuota(){
        int id = 1;
        String email = "";
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaEmailOfferenteNull(){
        int id = 1;
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(id,null,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaOffertaNegativa(){
        int id = 1;
        String email = "o";
        Float offerta = -300f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaOfferta0(){
        int id = 1;
        String email = "o";
        Float offerta = 0f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaOffertaNull(){
        int id = 1;
        String email = "o";
        astaRibassoDAO.acquistaAsta(id,email,null);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Errore nell'acquisto", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }
    @Test
    public void testAcquistaAstaConSuccesso(){
        int id = 1;
        String email = "o";
        Float offerta = 300f;
        astaRibassoDAO.acquistaAsta(id,email,offerta);
        try {
            // Attendi il completamento dell'operazione
            latch.await(5, TimeUnit.SECONDS);
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            assertEquals("Acquisto effettuato con successo", ShadowToast.getTextOfLatestToast());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test interrotto");
        }
    }

}