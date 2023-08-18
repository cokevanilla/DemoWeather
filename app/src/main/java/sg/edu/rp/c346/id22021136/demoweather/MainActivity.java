package sg.edu.rp.c346.id22021136.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.*;

public class MainActivity extends AppCompatActivity {

    ListView lvWeather;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWeather = findViewById(R.id.ListViewWeather);
        client = new AsyncHttpClient();


    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Weather> alWeather = new ArrayList<>();
        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {
            String area;
            String forecast;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
                    for (int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area");
                        forecast = jsonObjForecast.getString("forecast");
                        Weather weather = new Weather(area, forecast);
                        alWeather.add(weather);
                    }

                    // Create a custom ArrayAdapter to display the weather data in the ListView
                    ArrayAdapter<Weather> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);

                    // Set the adapter to the ListView
                    lvWeather.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } // end onSuccess
        });
    } // end onResume
}





