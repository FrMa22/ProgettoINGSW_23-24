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
public class TestAggiornaPasswordViewModel {
    FragmentProfiloViewModel viewModel = new FragmentProfiloViewModel();
    Repository repository = Repository.getInstance();
    AcquirenteModel acquirenteModel = new AcquirenteModel("mario", "rossi", "mario@gmail.com","oldPassword", "","", "");

    @Test
    public void testPasswordValide_Positivo() {
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
    }
    @Test
    public void testPasswordValide_OldPasswordDiversaDallaAttuale() {
        String oldPassword = "password";
        String newPassword = "newPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordVecchia("La password vecchia non coincide con quella dell'utente");
    }
    @Test
    public void testPasswordValide_OldPasswordVuota() {
        String oldPassword = "";
        String newPassword = "newPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordVecchia("La password vecchia non può essere vuota");
    }
    @Test
    public void testPasswordValide_NewPasswordVuota() {
        String oldPassword = "oldPassword";
        String newPassword = "";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordNuova("La password nuova non può essere vuota");
    }
    @Test
    public void testPasswordValide_OldPasswordNull() {
        String oldPassword = null;
        String newPassword = "newPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordVecchia("La password vecchia non può essere vuota");
    }
    @Test
    public void testPasswordValide_NewPasswordNull() {
        String oldPassword = "oldPassword";
        String newPassword = null;
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordNuova("La password nuova non può essere vuota");
    }
    @Test
    public void testPasswordValide_OldPasswordTroppoLunga() {
        String oldPassword = "oldPasswordoldPasswordoldPasswordoldPasswordoldPasswordoldPasswordoldPasswordoldPasswordoldPasswordoldPassword";
        String newPassword = "newPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordVecchia("La password vecchia non può essere più lunga di 100 caratteri");
    }
    @Test
    public void testPasswordValide_NewPasswordTroppoLunga() {
        String oldPassword = "oldPassword";
        String newPassword = "newPasswordnewPasswordnewPasswordnewPasswordnewPasswordnewPasswordnewPasswordnewPasswordnewPasswordnewPassword";
        repository.setAcquirenteModel(acquirenteModel);

        FragmentProfiloViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).aggiornaPasswordAcquirenteViewModel(newPassword);
        spyViewModel.aggiornaPasswordViewModel(oldPassword,newPassword);
        verify(spyViewModel).setMessaggioErrorePasswordNuova("La password nuova non può essere più lunga di 100 caratteri");
    }
}
