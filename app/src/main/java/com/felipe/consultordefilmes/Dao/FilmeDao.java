package com.felipe.consultordefilmes.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.felipe.consultordefilmes.Common.Funcoes;
import com.felipe.consultordefilmes.Model.FilmeDetalhado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 19/06/2016.
 */
public class FilmeDao {
    private static FilmesDBHelper dbHelper = null;

    Context context;
    public FilmeDao(Context context) {
        this.context = context;
        if (dbHelper == null) {
            dbHelper = new FilmesDBHelper(context);
        }
    }

    public void Add(FilmeDetalhado filme) {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            String consulta = "SELECT * FROM " + FilmesDBHelper.Filme.NOME_TABELA + " WHERE " + FilmesDBHelper.Filme.IMDB_ID + " = '" + filme.getImdbID() + "'";
            cursor = db.rawQuery(consulta, null);
            if (cursor.moveToFirst()) {
                return;
            } else {
                ContentValues valores = new ContentValues();
                valores.put(FilmesDBHelper.Filme.IMDB_ID, filme.getImdbID());
                valores.put(FilmesDBHelper.Filme.TITULO, filme.getTitulo());
                valores.put(FilmesDBHelper.Filme.ANO, filme.getAno());
                valores.put(FilmesDBHelper.Filme.TIPO, filme.getTipo());
                valores.put(FilmesDBHelper.Filme.LANCAMENTO, filme.getLancamento());
                valores.put(FilmesDBHelper.Filme.DURACAO, filme.getDuracao());
                valores.put(FilmesDBHelper.Filme.DIRETOR, filme.getDiretor());
                valores.put(FilmesDBHelper.Filme.GENERO, filme.getGenero());
                valores.put(FilmesDBHelper.Filme.ESCRITORES, filme.getEscritores());
                valores.put(FilmesDBHelper.Filme.ATORES, filme.getAtores());
                valores.put(FilmesDBHelper.Filme.SINOPSE, filme.getSinopse());
                valores.put(FilmesDBHelper.Filme.POSTER, filme.getUrlPoster());
                db.insert(FilmesDBHelper.Filme.NOME_TABELA, null, valores);
                db.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public List<FilmeDetalhado> Listar() {
        List<FilmeDetalhado> lista = null;
        try {
            lista = new ArrayList<FilmeDetalhado>();
            FilmeDetalhado filme;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String consulta = "SELECT * FROM " + FilmesDBHelper.Filme.NOME_TABELA + "";

            String imdbID = null;
            String titulo = null;
            String ano = null;
            String tipo = null;
            String lancamento = null;
            String duracao = null;
            String diretor = null;
            String genero = null;
            String escritores = null;
            String atores = null;
            String sinopse = null;
            String poster = null;

            Cursor cursor = db.rawQuery(consulta, null);

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    filme = new FilmeDetalhado();

                    imdbID = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.IMDB_ID));
                    if (imdbID != null) filme.setImdbID(imdbID);
                    titulo = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.TITULO));
                    if (titulo != null) filme.setTitulo(titulo);
                    ano = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ANO));
                    if (ano != null) filme.setAno(ano);
                    tipo = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.TIPO));
                    if (tipo != null) filme.setTipo(tipo);
                    lancamento = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.LANCAMENTO));
                    if (lancamento != null) filme.setLancamento(lancamento);
                    duracao = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.DURACAO));
                    if (duracao != null) filme.setDuracao(duracao);
                    diretor = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.DIRETOR));
                    if (diretor != null) filme.setDiretor(diretor);
                    genero = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.GENERO));
                    if (genero != null) filme.setTipo(genero);
                    escritores = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ESCRITORES));
                    if (escritores != null) filme.setTipo(escritores);
                    atores = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ATORES));
                    if (atores != null) filme.setTipo(atores);
                    sinopse = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.SINOPSE));
                    if (sinopse != null) filme.setTipo(sinopse);
                    poster = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.POSTER));
                    if (poster != null) filme.setTipo(poster);

                    filme.setImdbID(imdbID);
                    filme.setTitulo(titulo);
                    filme.setAno(ano);
                    filme.setTipo(tipo);
                    filme.setLancamento(lancamento);
                    filme.setDuracao(duracao);
                    filme.setDiretor(diretor);
                    filme.setGenero(genero);
                    filme.setEscritores(escritores);
                    filme.setAtores(atores);
                    filme.setSinopse(sinopse);
                    filme.setUrlPoster(poster);

                    lista.add(filme);
                    cursor.moveToNext();
                }
                db.close();
            } else {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            }

        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public FilmeDetalhado ListarFilme(String imdb_id) {
        FilmeDetalhado filme = null;
        try {
            filme = new FilmeDetalhado();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String consulta = "SELECT * FROM " + FilmesDBHelper.Filme.NOME_TABELA + " WHERE " + FilmesDBHelper.Filme.IMDB_ID + " = '" + imdb_id + "'";

            String imdbID = null;
            String titulo = null;
            String ano = null;
            String tipo = null;
            String lancamento = null;
            String duracao = null;
            String diretor = null;
            String genero = null;
            String escritores = null;
            String atores = null;
            String sinopse = null;
            String poster = null;

            Cursor cursor = db.rawQuery(consulta, null);

            if (cursor.moveToFirst()) {

                imdbID = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.IMDB_ID));
                if (imdbID != null) filme.setImdbID(imdbID);
                titulo = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.TITULO));
                if (titulo != null) filme.setTitulo(titulo);
                ano = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ANO));
                if (ano != null) filme.setAno(ano);
                tipo = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.TIPO));
                if (tipo != null) filme.setTipo(tipo);
                lancamento = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.LANCAMENTO));
                if (lancamento != null) filme.setLancamento(lancamento);
                duracao = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.DURACAO));
                if (duracao != null) filme.setDuracao(duracao);
                diretor = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.DIRETOR));
                if (diretor != null) filme.setDiretor(diretor);
                genero = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.GENERO));
                if (genero != null) filme.setTipo(genero);
                escritores = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ESCRITORES));
                if (escritores != null) filme.setTipo(escritores);
                atores = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.ATORES));
                if (atores != null) filme.setTipo(atores);
                sinopse = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.SINOPSE));
                if (sinopse != null) filme.setTipo(sinopse);
                poster = cursor.getString(cursor.getColumnIndex(FilmesDBHelper.Filme.POSTER));
                if (poster != null) filme.setTipo(poster);

                filme.setImdbID(imdbID);
                filme.setTitulo(titulo);
                filme.setAno(ano);
                filme.setTipo(tipo);
                filme.setLancamento(lancamento);
                filme.setDuracao(duracao);
                filme.setDiretor(diretor);
                filme.setGenero(genero);
                filme.setEscritores(escritores);
                filme.setAtores(atores);
                filme.setSinopse(sinopse);
                filme.setUrlPoster(poster);


                db.close();
            } else {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            }

        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return filme;
    }
}
