package com.example.openweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

public class MainActivity extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
    }

    public void getData(View view) {
        EditText cidade  = findViewById(R.id.editTextCidade);
        TextView pais = findViewById(R.id.editTextPais);

        if ((cidade.length() == 0) || (pais.length() == 0))
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        else {
            String city = cidade.getText().toString().toLowerCase();
            city = removerAcentos(city);
            String country = pais.getText().toString().toLowerCase();
            String appID = "2307b981ce8f3c64b8478e0cf913e118";

            String urlWeather = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&units=metric&appid=" + appID;
            MyTask task = new MyTask(output);
            task.execute(urlWeather);
        }
    }


    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

}