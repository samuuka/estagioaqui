package com.felipe.consultordefilmes.Controller;


import android.content.Context;
import android.os.StrictMode;


import com.felipe.consultordefilmes.Common.Funcoes;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
public class ConexaoOmdb {

	private String URL_SEARCH = "http://www.omdbapi.com/?s=";
	private String URL_ID = "http://www.omdbapi.com/?i=";
	private Context context;

	public ConexaoOmdb(Context context) {
		this.context = context;
	}

	public JSONObject omdbBusca(String filme) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(URL_SEARCH + filme);

			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

			HttpResponse response = httpClient.execute(request);

			HttpEntity responseEntity = response.getEntity();

			String jsonText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
			// Cria o Objeto
			JSONObject objeto = new JSONObject(jsonText);

			return objeto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject omdbTitulo(String imdbID) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(URL_ID + imdbID);

			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

			HttpResponse response = httpClient.execute(request);

			HttpEntity responseEntity = response.getEntity();

			String jsonText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
			// Cria o Objeto
			JSONObject objeto = new JSONObject(jsonText);

			return objeto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
