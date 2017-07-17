package com.felipe.consultordefilmes.View.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.felipe.consultordefilmes.Adapter.FilmesAdapter;
import com.felipe.consultordefilmes.Common.Funcoes;
import com.felipe.consultordefilmes.Dao.FilmeDao;
import com.felipe.consultordefilmes.Model.FilmeDetalhado;
import com.felipe.consultordefilmes.Model.FilmeResumido;
import com.felipe.consultordefilmes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 18/06/2016.
 */
public class HistoricoDeBuscas extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private FilmesAdapter adapter;
    List<FilmeResumido> lista = new ArrayList<FilmeResumido>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.historico_buscas, container, false);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new FilmesAdapter(getActivity(), lista);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FilmeResumido filme = lista.get(position);

                Intent intent = new Intent(getActivity(), DetalhesFilme.class);
                intent.putExtra("imdbID", filme.getImdbID());
                intent.putExtra("ano", filme.getAno());
                intent.putExtra("tipo", filme.getTipo());
                intent.putExtra("historico", true);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        CarregarDados();

        return view;
    }

    private void CarregarDados() {
        try {
            FilmeDao dao = new FilmeDao(getActivity());
            List<FilmeDetalhado> listaDetalhado = dao.Listar();
            FilmeResumido filmeResumido;
            for (FilmeDetalhado filmeDetalhado : listaDetalhado) {
                filmeResumido = new FilmeResumido();
                filmeResumido.setImdbID(filmeDetalhado.getImdbID());
                filmeResumido.setTitulo(filmeDetalhado.getTitulo());
                filmeResumido.setAno(filmeDetalhado.getAno());
                filmeResumido.setTipo(filmeDetalhado.getTipo());
                filmeResumido.setPoster(filmeDetalhado.getUrlPoster());

                lista.add(filmeResumido);
            }
            if (lista.size() > 0) {
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
