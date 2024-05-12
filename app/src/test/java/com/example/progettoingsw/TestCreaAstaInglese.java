package com.example.progettoingsw;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.viewmodel.CreaAstaIngleseViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class TestCreaAstaInglese {
    CreaAstaIngleseViewModel creaAstaIngleseViewModel = new CreaAstaIngleseViewModel();
    Repository repository = Repository.getInstance();
    VenditoreModel venditoreModel = new VenditoreModel("mario", "rossi", "mario@gmail.com","password", "","", "");

    @Test
    public void testRegistrazioneAcquirente_Positive() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).creaAstaBackend(any());
    }
    @Test
    public void testRegistrazioneAcquirente_NomeTroppoLungo() {
        String nome = "nome asta nome asta nome asta nome asta nome asta nome asta nome asta nome asta nome asta nome asta nome asta nome asta ";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreNome("Il nome non può essere più lungo di 100 caratteri.");
    }
    @Test
    public void testRegistrazioneAcquirente_NomeVuoto() {
        String nome = "";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreNome("Si prega di inserire un nome.");
    }
    @Test
    public void testRegistrazioneAcquirente_NomeNull() {
        String nome = null;
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreNome("Si prega di inserire un nome.");
    }
    @Test
    public void testValoriAstaIngleseValidi_DescrizioneTroppoLunga() {
        String nome = "nome asta";
        String descrizione = "descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta descrizione asta ";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreDescrizione("La descrizione non può essere più lunga di 250 caratteri.");
    }

    @Test
    public void testValoriAstaIngleseValidi_BaseAstaNonNumerico() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "nonNumerico";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreBaseAsta("Il prezzo deve essere un numero valido.");
    }

    @Test
    public void testValoriAstaIngleseValidi_BaseAstaMinoreZero() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "-10.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreBaseAsta("Il prezzo deve essere maggiore di 0.");
    }
    @Test
    public void testValoriAstaIngleseValidi_BaseAstaUgualeZero() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "0.00";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreBaseAsta("Il prezzo deve essere maggiore di 0.");
    }
    @Test
    public void testValoriAstaIngleseValidi_BaseAstaVuoto() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "";
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreBaseAsta("Si prega di inserire un prezzo.");
    }

    @Test
    public void testValoriAstaIngleseValidi_BaseAstaNull() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = null;
        String intervallo = "30";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreBaseAsta("Si prega di inserire un prezzo.");
    }

    @Test
    public void testValoriAstaIngleseValidi_IntervalloNonNumerico() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "nonNumerico";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreIntervallo("L'intervallo deve contenere solo numeri e non può superare i 5 caratteri.");
    }

    @Test
    public void testValoriAstaIngleseValidi_IntervalloPiùLungoDi5() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "123456";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreIntervallo("L'intervallo deve contenere solo numeri e non può superare i 5 caratteri.");
    }
    @Test
    public void testValoriAstaIngleseValidi_IntervalloMinore1() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "0";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreIntervallo("L'intervallo deve essere di almeno 1 minuto.");
    }

    @Test
    public void testValoriAstaIngleseValidi_IntervalloVuoto() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "";
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreIntervallo("Si prega di inserire un intervallo per le offerte.");
    }

    @Test
    public void testValoriAstaIngleseValidi_IntervalloNull() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = null;
        String rialzoMinimo = "5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreIntervallo("Si prega di inserire un intervallo per le offerte.");
    }

    @Test
    public void testValoriAstaIngleseValidi_RialzoNonNumerico() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "nonNumerico";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreSogliaRialzoMinimo("Si prega di inserire solo numeri per il valore minimo di rialzo.");
    }

    @Test
    public void testValoriAstaIngleseValidi_RialzoMinoreZero() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "-5.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreSogliaRialzoMinimo("Inserire un prezzo maggiore di 0");
    }
    @Test
    public void testValoriAstaIngleseValidi_RialzoUgualeZero() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "0.00";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreSogliaRialzoMinimo("Inserire un prezzo maggiore di 0");
    }

    @Test
    public void testValoriAstaIngleseValidi_RialzoVuoto() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = "";
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreSogliaRialzoMinimo("Si prega di inserire un valore minimo di rialzo.");
    }

    @Test
    public void testValoriAstaIngleseValidi_RialzoNull() {
        String nome = "nome asta";
        String descrizione = "descrizione asta";
        String baseAsta = "10.00";
        String intervallo = "30";
        String rialzoMinimo = null;
        repository.setVenditoreModel(venditoreModel);

        CreaAstaIngleseViewModel spyViewModel = spy(creaAstaIngleseViewModel);
        doNothing().when(spyViewModel).creaAstaBackend(any());
        spyViewModel.creaAsta(nome,descrizione,baseAsta,intervallo,rialzoMinimo,null);
        verify(spyViewModel).setErroreSogliaRialzoMinimo("Si prega di inserire un valore minimo di rialzo.");
    }
}
