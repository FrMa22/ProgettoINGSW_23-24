package com.example.progettoingsw;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class TestAggiornaSocialViewModel {
    FragmentProfiloViewModel fragmentProfiloViewModel = new FragmentProfiloViewModel();
    Repository repository = Repository.getInstance();
    AcquirenteModel acquirenteModel = new AcquirenteModel("mario", "rossi", "mario@gmail.com","password", "","", "");
    String oldNome = "linkedin";
    String oldLink ="linkedin.com";
    @Test
    public void testAggiornaSocialValido(){
        String nome = "facebook";
        String link = "facebook.com";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
    }
    @Test
    public void testAggiornaSocialNomeVuoto() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = "";
        String link = "facebook.com";

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreNomeNuovo("Il nome nuovo non può essere vuoto");
    }
    @Test
    public void testAggiornaSocialNomeTroppoLungo() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = "Nome lungo Nome lungo Nome lungo Nome lungo Nome lungo";
        String link = "facebook.com";

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreNomeNuovo("Il nome nuovo non può essere più lungo di 50 caratteri");
    }
    @Test
    public void testAggiornaSocialNomeNull() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = null;
        String link = "facebook.com";

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreNomeNuovo("Il nome nuovo non può essere vuoto");
    }
    @Test
    public void testAggiornaSocialLinkTroppoLungo() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = "Facebook";
        String link = "facebook.com facebook.com facebook.com facebook.com facebook.com ";

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreLinkNuovo("Il link nuovo non può essere più lungo di 50 caratteri");
    }
    @Test
    public void testAggiornaSocialLinkVuoto() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = "Facebook";
        String link = "";

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreLinkNuovo("Il link nuovo non può essere vuoto");
    }
    @Test
    public void testAggiornaSocialLinkNull() {
        repository.setAcquirenteModel(acquirenteModel);
        String nome = "Facebook";
        String link = null;

        FragmentProfiloViewModel spyViewModel = spy(fragmentProfiloViewModel);
        doNothing().when(spyViewModel).aggiornaSocialAcquirenteViewModel(oldNome, oldLink, nome, link);
        spyViewModel.aggiornaSocialViewModel(oldNome, oldLink, nome, link);
        verify(spyViewModel).setMessaggioErroreLinkNuovo("Il link nuovo non può essere vuoto");
    }
}
