package com.example.rnv_examenandroid2evalu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.rnv_examenandroid2evalu.R;
import com.example.rnv_examenandroid2evalu.data.RepositoryImpl;
import com.example.rnv_examenandroid2evalu.data.local.AppDatabase;
import com.example.rnv_examenandroid2evalu.data.local.model.Libro;
import com.example.rnv_examenandroid2evalu.databinding.ListaListFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ListaFragment extends Fragment {

    private ListaListFragmentBinding b;
    private ListaFragmentViewModel viewModel;
    private ItemTouchHelper swipeItemTouchHelper;
    private ListaFragmentAdapter listAdapter;
    private BottomSheetBehavior<RelativeLayout> bsb;
    private NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = ListaListFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        viewModel = ViewModelProviders.of(requireActivity(),
                new ListaFragmentViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).libroDao()
                        )
                )).get(ListaFragmentViewModel.class);

        setupSwipeToDismiss();

        setupViews();
        observerLibros();
        swipeItemTouchHelper.attachToRecyclerView(b.lstLibros);
    }

    private void observerLibros() {
        viewModel.getLibros().observe(this, libros -> {
            listAdapter.submitList(libros);
            b.lblEmptyView.setVisibility(libros.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews() {
        bsb = BottomSheetBehavior.from(b.rlBs);
        setupBottomSheet();

        b.fabAddLibro.setOnClickListener(this::addNewLibro);
        b.lblEmptyView.setOnClickListener(this::addNewLibro);

        b.lstLibros.setHasFixedSize(true);
        listAdapter = new ListaFragmentAdapter(position -> expandOrCollapseBottomSheet(listAdapter.getItem(position)));
        b.lstLibros.setAdapter(listAdapter);
        b.lstLibros.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lst_columns)));
        b.lstLibros.setItemAnimator(new DefaultItemAnimator());
    }


    private void setupBottomSheet() {
        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                viewModel.setBsbState(newState);
                updateIcon(newState);
                b.lblBsTitulo.setText(viewModel.getLibro().getTitulo());
                if (!viewModel.getLibro().getSinopsis().isEmpty()) {
                    b.lblBsSinopsis.setText(viewModel.getLibro().getSinopsis());
                }else{
                    b.lblBsSinopsis.setText(getString(R.string.msg_sinopsis_bt));
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        bsb.setState(viewModel.getBsbState());
        updateIcon(bsb.getState());

    }

    private void updateIcon(int newState) {
        switch (newState) {
            case BottomSheetBehavior.STATE_EXPANDED:
                b.imgBs.setImageResource(R.drawable.ic_close_black_24dp);
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
            case BottomSheetBehavior.STATE_HIDDEN:
            default:
        }
    }

    private void expandOrCollapseBottomSheet(Libro libro) {
        viewModel.setLibro(libro);
        if (bsb.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bsb.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void addNewLibro(View view) {
        Navigation.findNavController(view).navigate(R.id.agregarFragment);
    }

    private void setupSwipeToDismiss() {
        swipeItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.setLibro(listAdapter.getItem(viewHolder.getAdapterPosition()));
                viewModel.deleteLibro(listAdapter.getItem(viewHolder.getAdapterPosition()));
                Snackbar.make(getView(), "Libro borrado", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.deshacer), v -> viewModel.insertLibro(viewModel.getLibro())).show();


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)||super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mnu_list_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
