package com.example.rnv_examenandroid2evalu.ui.agregarFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rnv_examenandroid2evalu.R;
import com.example.rnv_examenandroid2evalu.base.YesNoDialogFragment;
import com.example.rnv_examenandroid2evalu.data.RepositoryImpl;
import com.example.rnv_examenandroid2evalu.data.local.AppDatabase;
import com.example.rnv_examenandroid2evalu.data.local.model.Libro;
import com.example.rnv_examenandroid2evalu.databinding.AgregarFragmentBinding;
import com.example.rnv_examenandroid2evalu.ui.ListaFragmentViewModel;
import com.example.rnv_examenandroid2evalu.ui.ListaFragmentViewModelFactory;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AgregarFragment extends Fragment implements YesNoDialogFragment.Listener {

    private AgregarFragmentBinding b;
    private ListaFragmentViewModel viewModel;

    private static final String TAG_DIALOG_FRAGMENT = "TAG_DIALOG_FRAGMENT";
    private static final int RC_DIALOG_FRAGMENT = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = AgregarFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new ListaFragmentViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).libroDao()
                        )
                )).get(ListaFragmentViewModel.class);

        setupViews();
    }

    private void setupViews() {
        imgDefault();
        b.txtAgregarUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus && !b.txtAgregarUrl.getText().toString().isEmpty()){
                Picasso.with(getContext()).load(b.txtAgregarUrl.getText().toString()).into(b.imgAgregarPortada);
            }else{
                imgDefault();
            }
        });
        b.fabSave.setOnClickListener(v -> saveLibro());
    }

    private void imgDefault() {
        Picasso.with(getContext()).load("https://via.placeholder.com/350x150").into(b.imgAgregarPortada);
    }

    private boolean checkString(String s, TextInputLayout textInputLayout) {
        if(TextUtils.isEmpty(s)){
            textInputLayout.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError("");
            return true;
        }
    }

    private void saveLibro() {
        if (validForm()) {
            YesNoDialogFragment dialogFragment = YesNoDialogFragment.newInstance(
                    getString(R.string.main_fragment_confirm_deletion),
                    getString(R.string.main_fragment_sure), getString(R.string.main_fragment_yes),
                    getString(R.string.main_fragment_no), this, RC_DIALOG_FRAGMENT);
            dialogFragment.show(getFragmentManager(), TAG_DIALOG_FRAGMENT);
        }else{
            Toast.makeText(getContext(), getString(R.string.msg_error_agregar), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validForm() {
        if(checkString(b.txtAgregarTitulo.getText().toString(), b.tilAgregarTitulo) &&
                checkString(b.txtAgregarAutor.getText().toString(), b.tilAgregarAutor) &&
                checkString(b.txtAgregarAnio.getText().toString(), b.tilAgregarAnio)){
            return true;
        }else{
            checkString(b.txtAgregarTitulo.getText().toString(), b.tilAgregarTitulo);
            checkString(b.txtAgregarAutor.getText().toString(), b.tilAgregarAutor);
            checkString(b.txtAgregarAnio.getText().toString(), b.tilAgregarAnio);
            return  false;
        }
    }


    @Override
    public void onPositiveButtonClick(DialogInterface dialog) {
        viewModel.insertLibro(new Libro(b.txtAgregarTitulo.getText().toString(), b.txtAgregarAutor.getText().toString(), b.txtAgregarAnio.getText().toString(), b.txtAgregarUrl.getText().toString(), b.txtAgregarSinopsis.getText().toString()));
        Toast.makeText(getContext(), getString(R.string.msg_success_agregar), Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onNegativeButtonClick(DialogInterface dialog) {

    }
}
