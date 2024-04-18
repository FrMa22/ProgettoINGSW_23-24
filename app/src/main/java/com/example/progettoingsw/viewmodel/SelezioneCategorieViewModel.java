package com.example.progettoingsw.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progettoingsw.R;
import com.example.progettoingsw.repository.Asta_allingleseRepository;
import com.example.progettoingsw.repository.Asta_alribassoRepository;
import com.example.progettoingsw.repository.Asta_inversaRepository;
import com.example.progettoingsw.repository.Repository;

public class SelezioneCategorieViewModel extends ViewModel {

    private Repository repository;
    public MutableLiveData<Boolean> categoriaSelezionata = new MutableLiveData<>(false);



    public SelezioneCategorieViewModel(){
        repository = Repository.getInstance();
    }
    public void setCategoriaSelezionata(Boolean b){
        this.categoriaSelezionata.setValue(b);
    }
    public Boolean getCategoriaSelezionata(){
        return categoriaSelezionata.getValue();
    }

    public void categoriaCliccata(String categoria){
        repository.setCategoriaSelezionata(categoria);
        setCategoriaSelezionata(true);
    }

}
