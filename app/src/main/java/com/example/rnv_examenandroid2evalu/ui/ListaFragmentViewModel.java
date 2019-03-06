package com.example.rnv_examenandroid2evalu.ui;

import com.example.rnv_examenandroid2evalu.data.Repository;
import com.example.rnv_examenandroid2evalu.data.local.model.Libro;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListaFragmentViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Libro>> libros;
    private Libro libro;
    private int bsbState;

    public ListaFragmentViewModel(Repository repository) {
        this.repository = repository;
        libros = repository.queryLibros();
        this.bsbState = BottomSheetBehavior.STATE_COLLAPSED;
    }

    public LiveData<List<Libro>> getLibros(){
        return libros;
    }

    public void insertLibro (Libro libro){
        repository.insertLibro(libro);
    }

    public void deleteLibro(Libro libro){
        repository.deleteLibro(libro);
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public int getBsbState() {
        return bsbState;
    }

    public void setBsbState(int bsbState) {
        this.bsbState = bsbState;
    }
}
