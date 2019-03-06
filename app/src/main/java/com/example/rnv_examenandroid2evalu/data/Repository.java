package com.example.rnv_examenandroid2evalu.data;

import com.example.rnv_examenandroid2evalu.data.local.model.Libro;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface Repository {

    LiveData<List<Libro>> queryLibros();
    void insertLibro(Libro libro);
    void deleteLibro(Libro libro);

}
