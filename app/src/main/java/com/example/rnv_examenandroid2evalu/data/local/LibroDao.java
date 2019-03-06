package com.example.rnv_examenandroid2evalu.data.local;

import com.example.rnv_examenandroid2evalu.data.local.model.Libro;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LibroDao {

    @Query("Select * from Libro")
    LiveData<List<Libro>> queryLibros();

    @Insert
    void insertLibro(Libro libro);

    @Delete
    void deleteLibro(Libro libro);
}
