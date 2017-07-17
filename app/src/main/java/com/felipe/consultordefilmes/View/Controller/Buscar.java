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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.felipe.consultordefilmes.Adapter.FilmesAdapter;
import com.felipe.consultordefilmes.Common.Funcoes;
import com.felipe.consultordefilmes.Controller.ConexaoOmdb;
import com.felipe.consultordefilmes.Model.FilmeResumido;
import com.felipe.consultordefilmes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 18/06/2016.
 */
public class Buscar extends Fragment {

    View view;

    private Button btnBuscar;
    private EditText txtTitulo;
    private RecyclerView recyclerView;
    private FilmesAdapter adapter;
    List<FilmeResumido> lista = new ArrayList<FilmeResumido>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.buscar, container, false);

        txtTitulo = (EditText) view.findViewById(R.id.txt_titulo);
        txtTitulo.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    BuscarFime();
                    handled = true;
                }
                return handled;
            }
        });
        btnBuscar = (Button) view.findViewById(R.id.btn_buscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarFime();
            }
        });
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
                intent.putExtra("imdbID",filme.getImdbID());
                intent.putExtra("ano",filme.getAno());
                intent.putExtra("tipo",filme.getTipo());
                intent.putExtra("historico", false);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    private void BuscarFime() {
        try{
            if (Funcoes.temConexao(getActivity())) {

                ConexaoOmdb conexao = new ConexaoOmdb(getActivity());
                JSONObject response = conexao.omdbBusca(txtTitulo.getText().toString().trim());

                if (response == null) {
                    Toast.makeText(getActivity(), "Não encontrado", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    JSONArray array = null;
                    lista.clear();

                    array = response.getJSONArray("Search");


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        FilmeResumido filmeResumido = new FilmeResumido();

                        filmeResumido.setTitulo(obj.getString("Title"));
                        filmeResumido.setAno(obj.getString("Year"));
                        filmeResumido.setImdbID(obj.getString("imdbID"));
                        filmeResumido.setTipo(obj.getString("Type"));
                        filmeResumido.setPoster(obj.getString("Poster"));

                        lista.add(filmeResumido);
                    }
                    adapter.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } else {
                Toast.makeText(getActivity(), "Sem Conexão", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Não encontrado", Toast.LENGTH_LONG).show();
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

