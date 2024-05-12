package com.example.progettoingsw;

import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class TestRegistrazioneAcquirente {
RegistrazioneViewModel viewModel = new RegistrazioneViewModel();

@Test
public void testRegistrazioneAcquirente_Positive() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
}

@Test
public void testRegistrazioneAcquirente_NomeTroppoLungo() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome Nome Nome Nome Nome Nome Nome Nome Nome Nome Nome Nome Nome Nome ";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreNome("il nome non può essere più lungo di 50 caratteri");

}

@Test
public void testRegistrazioneAcquirente_NomeLettereNumeriCaratteriSpecial() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome 1 !";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreNome("Il nome può contenere solo lettere");
}

@Test
public void testRegistrazioneAcquirente_NomeVuoto() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreNome("il nome non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_NomeNull() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = null;
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreNome("il nome non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_CognomeTroppoLungo() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome Cognome Cognome Cognome Cognome Cognome Cognome Cognome ";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreCognome("il cognome non può essere più lungo di 50 caratteri");
}

@Test
public void testRegistrazioneAcquirente_CognomeLettereNumeriCaratteriSpecial() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome 1 !";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreCognome("Il cognome può contenere solo lettere");
}

@Test
public void testRegistrazioneAcquirente_CognomeVuoto() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreCognome("il cognome non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_CognomeNull() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = null;

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreCognome("il cognome non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_EmailTroppoLunga() {
    String email = "email@example.com email@example.com email@example.com email@example.com email@example.com email@example.com email@example.com ";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreEmail("L'indirizzo email non può essere più lungo di 100 caratteri");
}

@Test
public void testRegistrazioneAcquirente_EmailNonValidoFormato() {
    String email = "email.com";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreEmail("L'indirizzo email non è nel formato corretto");
}

@Test
public void testRegistrazioneAcquirente_EmailVuota() {
    String email = "";
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreEmail("L'indirizzo email non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_EmailNull() {
    String email = null;
    String password = "password";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreEmail("L'indirizzo email non può essere vuoto");
}

@Test
public void testRegistrazioneAcquirente_PasswordTroppoLunga() {
    String email = "email@example.com";
    String password = "password password password password password password password password password password password password ";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErrorePassword("La password non può essere più lunga di 100 caratteri");
}

@Test
public void testRegistrazioneAcquirente_PasswordVuota() {
    String email = "email@example.com";
    String password = "";
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErrorePassword("La password non può essere vuota");
}

@Test
public void testRegistrazioneAcquirente_PasswordNull() {
    String email = "email@example.com";
    String password = null;
    String confermaPassword = "password";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErrorePassword("La password non può essere vuota");
}

@Test
public void testRegistrazioneAcquirente_ConfermaPasswordDiversa() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password diversa";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreConfermaPassword("Le password sono diverse");
}

@Test
public void testRegistrazioneAcquirente_ConfermaPasswordTroppoLunga() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "password password password password password password password password password password password password ";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreConfermaPassword("Conferma password non può essere più lunga di 100 caratteri");
}

@Test
public void testRegistrazioneAcquirente_ConfermaPasswordVuota() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = "";
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreConfermaPassword("Conferma password non può essere vuota");
}

@Test
public void testRegistrazioneAcquirente_ConfermaPasswordNull() {
    String email = "email@example.com";
    String password = "password";
    String confermaPassword = null;
    String nome = "Nome";
    String cognome = "Cognome";

    RegistrazioneViewModel spyViewModel = spy(viewModel);
    doNothing().when(spyViewModel).trovaAcquirenteDoppio(email, password, nome, cognome);
    spyViewModel.registrazioneAcquirente(email, password, confermaPassword, nome, cognome);
    verify(spyViewModel).setMessaggioErroreConfermaPassword("Conferma password non può essere vuota");
}
}
