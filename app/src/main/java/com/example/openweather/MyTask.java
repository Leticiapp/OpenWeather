package com.example.openweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyTask extends AsyncTask<String, Void, String> {

    TextView output;

    public MyTask(TextView output) { this.output = output;}

    @Override
    protected String doInBackground(String... strings) {
        String stringURL = strings[0];
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer buffer = null;

        try{
            URL url = new URL(stringURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }  catch (Exception e){
            output.setText("Dados fornecidos incorretos!");
        }
        return buffer.toString();
    }

    @Override
    protected void onPreExecute() { super.onPreExecute(); }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        String temperatura = null;
        String minima = null;
        String maxima = null;
        String umidade = null;
        String chuva = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject temp = jsonObject.getJSONObject("main");
            temperatura = temp.getString("temp");
            minima = temp.getString("temp_min");
            maxima = temp.getString("temp_max");
            umidade = temp.getString("humidity");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONObject temp2 = jsonObject.getJSONObject("rain");
            chuva = temp2.getString("1h") + " %";

        } catch (JSONException e) {
            chuva = "Não informado";
            e.printStackTrace();
        }

        output.setText("Temperatura: " + temperatura + " ºC" +
                       "\nMínima: " + minima + " ºC" +
                       "\nMáxima: " + maxima + " ºC" +
                       "\nUmidade: " + umidade + " %" +
                       "\nChuva: " + chuva
        );
    }

}
