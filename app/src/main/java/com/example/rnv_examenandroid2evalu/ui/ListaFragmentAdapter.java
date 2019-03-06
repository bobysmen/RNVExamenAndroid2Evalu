package com.example.rnv_examenandroid2evalu.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rnv_examenandroid2evalu.R;
import com.example.rnv_examenandroid2evalu.data.local.model.Libro;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ListaFragmentAdapter extends ListAdapter<Libro, ListaFragmentAdapter.ViewHolder> {

    private final OnListaClickListenerBS onListaClickListenerBS;

    public ListaFragmentAdapter(OnListaClickListenerBS onListaClickListenerBS) {
        super(new DiffUtil.ItemCallback<Libro>() {
            @Override
            public boolean areItemsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
                return TextUtils.equals(oldItem.getUrlPortada(), newItem.getUrlPortada()) &&
                        TextUtils.equals(oldItem.getTitulo(), newItem.getTitulo()) &&
                        TextUtils.equals(oldItem.getAutor(), newItem.getAutor()) &&
                        TextUtils.equals(oldItem.getAnioPublicacion(), newItem.getAnioPublicacion()) &&
                        TextUtils.equals(oldItem.getSinopsis(), newItem.getSinopsis());
            }
        });
        this.onListaClickListenerBS = onListaClickListenerBS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_list_item_fragment, parent, false), onListaClickListenerBS
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Libro getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgPortada;
        private final TextView lblTitulo;
        private final TextView lblAutor;
        private final TextView lblAnioPublicacion;

        public ViewHolder(@NonNull View itemView, OnListaClickListenerBS onListaClickListenerBS) {
            super(itemView);
            imgPortada = ViewCompat.requireViewById(itemView, R.id.imgPortada);
            lblTitulo = ViewCompat.requireViewById(itemView, R.id.lblTitulo);
            lblAutor = ViewCompat.requireViewById(itemView, R.id.lblAutor);
            lblAnioPublicacion = ViewCompat.requireViewById(itemView, R.id.lblAnioPublicacion);
            itemView.setOnClickListener(v -> onListaClickListenerBS.onItemClick(getAdapterPosition()));
        }

        void bind (Libro libro){
            if (!libro.getUrlPortada().isEmpty()) {
                Picasso.with(itemView.getContext()).load(libro.getUrlPortada()).into(imgPortada);
            }
            lblTitulo.setText(libro.getTitulo());
            lblAutor.setText(libro.getAutor());
            lblAnioPublicacion.setText(libro.getAnioPublicacion());
        }
    }
}
