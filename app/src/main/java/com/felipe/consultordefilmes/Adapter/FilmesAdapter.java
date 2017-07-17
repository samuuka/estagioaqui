package com.felipe.consultordefilmes.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipe.consultordefilmes.Common.Funcoes;
import com.felipe.consultordefilmes.Model.FilmeResumido;
import com.felipe.consultordefilmes.R;

import java.util.List;

/**
 * Created by felip on 19/06/2016.
 */
public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MyViewHolder> {

    private List<FilmeResumido> lista;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, ano, tipo;
        public ImageView ivPoster;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.movie_title);
            tipo = (TextView) view.findViewById(R.id.movie_type);
            ano = (TextView) view.findViewById(R.id.movie_year);
            ivPoster = (ImageView) view.findViewById(R.id.movie_poster);
        }
    }


    public FilmesAdapter(Context context, List<FilmeResumido> moviesList) {
        this.lista = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_filmes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drawable drawable;
        FilmeResumido filme = lista.get(position);
        holder.titulo.setText(filme.getTitulo());
        holder.tipo.setText(filme.getTipo());
        holder.ano.setText(filme.getAno());

        if (Funcoes.temConexao(context)) {
            drawable = Funcoes.CarregarImagem(context, filme.getPoster());
        } else {
            drawable = context.getResources().getDrawable(R.drawable.imagem_padrao);
        }
        holder.ivPoster.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}
