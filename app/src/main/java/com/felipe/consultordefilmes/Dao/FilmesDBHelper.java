package com.felipe.consultordefilmes.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.felipe.consultordefilmes.Common.Funcoes;

import java.io.File;

/**
 * Created by felip on 19/06/2016.
 */
public class FilmesDBHelper extends SQLiteOpenHelper {

    private static String BANCO = "Historico";
    private static int VERSAO = 1;
    Context context;

    public FilmesDBHelper(Context context) {
        super(context, BANCO, null, VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + Filme.NOME_TABELA + " ("
                    + Filme.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Filme.IMDB_ID + " TEXT NOT NULL, "
                    + Filme.TITULO + " TEXT NOT NULL, "
                    + Filme.ANO + " TEXT, "
                    + Filme.TIPO + " TEXT, "
                    + Filme.LANCAMENTO + " TEXT, "
                    + Filme.DURACAO + " TEXT, "
                    + Filme.DIRETOR + " TEXT, "
                    + Filme.GENERO + " TEXT, "
                    + Filme.ESCRITORES + " TEXT, "
                    + Filme.ATORES + " TEXT, "
                    + Filme.SINOPSE + " TEXT, "
                    + Filme.POSTER + " TEXT);";
            db.execSQL(query);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoAtual) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Filme.NOME_TABELA);
            onCreate(db);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }

    public static class Filme {
        public static String NOME_TABELA = "Filmes";
        public static String ID = "_id";
        public static String IMDB_ID = "imdb_id";
        public static String TITULO = "titulo";
        public static String ANO = "ano";
        public static String TIPO = "tipo";
        public static String LANCAMENTO = "lancamento";
        public static String DURACAO = "duracao";
        public static String DIRETOR = "diretor";
        public static String GENERO = "genero";
        public static String ESCRITORES = "escritores";
        public static String ATORES = "atores";
        public static String SINOPSE = "sinopse";
        public static String POSTER = "poster";

    }
}
