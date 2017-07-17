package com.felipe.consultordefilmes.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by felip on 19/06/2016.
 */
public class Funcoes {

    public static String URL_IMAGEM_PADRAO = "http://www.imdb.com/images/nopicture/medium/film.png";

    public static boolean temConexao(Context context) {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Drawable CarregarImagem(Context context, String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
