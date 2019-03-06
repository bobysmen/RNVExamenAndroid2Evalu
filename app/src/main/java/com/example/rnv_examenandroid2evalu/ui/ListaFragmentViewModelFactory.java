package com.example.rnv_examenandroid2evalu.ui;

import com.example.rnv_examenandroid2evalu.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ListaFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public ListaFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListaFragmentViewModel(repository);
    }
}
