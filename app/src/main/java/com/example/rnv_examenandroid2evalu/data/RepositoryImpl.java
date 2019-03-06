package com.example.rnv_examenandroid2evalu.data;

import android.os.AsyncTask;

import com.example.rnv_examenandroid2evalu.data.local.LibroDao;
import com.example.rnv_examenandroid2evalu.data.local.model.Libro;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RepositoryImpl implements Repository {

    private LibroDao libroDao;

    public RepositoryImpl(LibroDao libroDao) {
        this.libroDao = libroDao;
    }


    @Override
    public LiveData<List<Libro>> queryLibros() {
        return libroDao.queryLibros();
    }

    @Override
    public void insertLibro(Libro libro) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> libroDao.insertLibro(libro));
    }

    @Override
    public void deleteLibro(Libro libro) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> libroDao.deleteLibro(libro));
    }
}
