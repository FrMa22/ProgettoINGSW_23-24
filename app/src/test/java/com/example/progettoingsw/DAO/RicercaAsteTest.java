package com.example.progettoingsw.DAO;

import static androidx.core.content.ContextCompat.startActivity;
import static com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Verify.verify;
import static org.junit.Assert.*;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentRicercaAsta;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.google.android.material.navigation.NavigationView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


public class RicercaAsteTest extends TestCase {


    private RicercaAsteDAO ricercaAsteDAO;



    private AcquirenteFragmentRicercaAsta  fragmentRicercaAsta;

    private AcquirenteMainActivity mainActivity;

    @Before
    public void setUp() {
        //costruisce il dao cosi poi servono solo i casi di test
    }


    @Test
    public void ricercaAsteCaso1() {
        String parola="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        ArrayList<String> listaCategorie=new ArrayList<>();
        String ordine="ASC";//anche DESC è un caso altretanto valido come input
        fragmentRicercaAsta.eseguiRicercaAste(parola,listaCategorie,ordine);

    }
}