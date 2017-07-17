package com.felipe.consultordefilmes.View.Controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felipe.consultordefilmes.Common.Funcoes;
import com.felipe.consultordefilmes.Controller.ConexaoOmdb;
import com.felipe.consultordefilmes.Dao.FilmeDao;
import com.felipe.consultordefilmes.Model.FilmeDetalhado;
import com.felipe.consultordefilmes.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by felip on 18/06/2016.
 */
public class DetalhesFilme extends AppCompatActivity {

    ImageView ivImagem;
    TextView tvTitulo, tvLancamento, tvDuracao, tvDiretor, tvGenero, tvEscritores, tvAtores, tvSinopse;
    String imdbID = "", ano = "", tipo = "";
    Boolean historico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        imdbID = intent.getStringExtra("imdbID");
        ano = intent.getStringExtra("ano");
        tipo = intent.getStringExtra("tipo");
        historico = getIntent().getExtras().getBoolean("historico");

        ivImagem = (ImageView) findViewById(R.id.detalhes_imagem);
        tvTitulo = (TextView) findViewById(R.id.detalhes_titulo);
        tvLancamento = (TextView) findViewById(R.id.detalhes_lancamento);
        tvDuracao = (TextView) findViewById(R.id.detalhes_duracao);
        tvDiretor = (TextView) findViewById(R.id.detalhes_diretor);
        tvGenero = (TextView) findViewById(R.id.detalhes_genero);
        tvEscritores = (TextView) findViewById(R.id.detalhes_escritores);
        tvAtores = (TextView) findViewById(R.id.detalhes_atores);
        tvSinopse = (TextView) findViewById(R.id.detalhes_sinopse);

        CarregarDados();
    }

    private void CarregarDados() {
        try {
            FilmeDetalhado filmeDetalhado = new FilmeDetalhado();
            Drawable drawable = null;
            if (historico) {
                FilmeDao dao = new FilmeDao(this);
                filmeDetalhado = dao.ListarFilme(imdbID);
            } else {
                if (Funcoes.temConexao(this)) {
                    ConexaoOmdb conexao = new ConexaoOmdb(this);
                    JSONObject response = conexao.omdbTitulo(imdbID);
                    if (response == null) {
                        Toast.makeText(this, "Não encontrado", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        filmeDetalhado.setTitulo(response.getString("Title"));
                        filmeDetalhado.setAtores(response.getString("Actors"));
                        filmeDetalhado.setDiretor(response.getString("Director"));
                        filmeDetalhado.setDuracao(response.getString("Runtime"));
                        filmeDetalhado.setEscritores(response.getString("Writer"));
                        filmeDetalhado.setGenero(response.getString("Genre"));
                        filmeDetalhado.setLancamento(response.getString("Released"));
                        filmeDetalhado.setSinopse(response.getString("Plot"));
                        filmeDetalhado.setUrlPoster(response.getString("Poster"));
                        filmeDetalhado.setImdbID(imdbID);
                        filmeDetalhado.setAno(ano);
                        filmeDetalhado.setTipo(tipo);
                        // Grava no banco
                        FilmeDao dao = new FilmeDao(this);
                        dao.Add(filmeDetalhado);
                    }
                } else {
                }
            }


            if (Funcoes.temConexao(this)) {
                drawable = Funcoes.CarregarImagem(this, filmeDetalhado.getUrlPoster());
            } else {
                drawable = this.getResources().getDrawable(R.drawable.imagem_padrao);
            }
            ivImagem.setImageDrawable(drawable);
            tvTitulo.setText(filmeDetalhado.getTitulo());
            tvLancamento.setText(filmeDetalhado.getLancamento());
            tvDuracao.setText(filmeDetalhado.getDuracao());
            tvDiretor.setText(filmeDetalhado.getDiretor());
            tvEscritores.setText(filmeDetalhado.getEscritores());
            tvAtores.setText(filmeDetalhado.getAtores());
            tvGenero.setText(filmeDetalhado.getGenero());
            tvSinopse.setText(filmeDetalhado.getSinopse());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
