package com.example.myapplication40;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherDataActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView weatherImageView;
    private Spinner locationSpinner;
    private FetchWeatherTask fetchWeatherTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);

        listView = findViewById(R.id.listView);
        weatherImageView = findViewById(R.id.weatherImageView);
        locationSpinner = findViewById(R.id.locationSpinner);

        // Create an ArrayAdapter for the spinner with the location options
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.locations_array,  // An array of location names
                android.R.layout.simple_spinner_item
        );
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        locationSpinner.setSelection(locationAdapter.getPosition("大安區"));

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected location from the spinner
                String selectedLocation = locationSpinner.getSelectedItem().toString();

                // Execute the AsyncTask with the selected location
                fetchWeatherTask = new FetchWeatherTask();
                fetchWeatherTask.execute(selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时取消AsyncTask
        if (fetchWeatherTask != null && !fetchWeatherTask.isCancelled()) {
            fetchWeatherTask.cancel(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchWeatherTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... locationName1) {
            return fetchWeatherData(locationName1[0]);
        }

        @Override
        protected void onPostExecute(List<String> weatherData) {
            if (weatherData != null && !weatherData.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        WeatherDataActivity.this,
                        android.R.layout.simple_list_item_1,
                        weatherData
                );
                listView.setAdapter(adapter);

                // 在数据加载完成后加载图片
                loadWeatherImage();
            } else {
                // 處理沒有數據的情況，顯示錯誤消息
                Toast.makeText(WeatherDataActivity.this, "無法獲取數據", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 添加方法来加载图片
    private void loadWeatherImage() {
        String imageUrl = "https://cwaopendata.s3.ap-northeast-1.amazonaws.com/Forecast/F-C0035-015.png"; // 图片的URL
        Picasso.get().load(imageUrl).into(weatherImageView);
    }

    private List<String> fetchWeatherData(String locationName1) {
        List<String> weatherData = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();

            // 使用 LocalDate 生成當天日期
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 格式化日期為字串
            String formattedDate = today.format(formatter);

            // Updated URL with the new API endpoint and API key as a query parameter
            String apiUrl = "https://opendata.cwa.gov.tw/api/v1/rest/datastore/F-D0047-061?Authorization=CWB-A25ACE1A-9902-44E1-9BBF-BBF4BDF5AADB&locationName=" + locationName1 + "&elementName=WeatherDescription&timeFrom=" + formattedDate + "T12:00:00&timeTo=" + formattedDate + "T14:00:00";

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray locationsArray = jsonObject.getJSONObject("records").getJSONArray("locations");

                    for (int i = 0; i < locationsArray.length(); i++) {
                        JSONObject locationInfo = locationsArray.getJSONObject(i);
                        String locationsName = locationInfo.optString("locationsName", "");

                        JSONArray locationArray = locationInfo.getJSONArray("location");

                        for (int j = 0; j < locationArray.length(); j++) {
                            JSONObject location = locationArray.getJSONObject(j);
                            String locationName = location.optString("locationName", "");

                            JSONArray weatherElements = location.getJSONArray("weatherElement");

                            for (int k = 0; k < weatherElements.length(); k++) {
                                JSONObject element = weatherElements.getJSONObject(k);

                                String elementName = element.optString("elementName", "");
                                String description = element.optString("description", "");

                                JSONArray times = element.getJSONArray("time");

                                for (int l = 0; l < times.length(); l++) {
                                    JSONObject time = times.getJSONObject(l);

                                    String startTime = time.optString("startTime", "");
                                    String endTime = time.optString("endTime", "");

                                    String value = time.getJSONArray("elementValue").getJSONObject(0).optString("value", "");
                                    String measures = time.getJSONArray("elementValue").getJSONObject(0).optString("measures", "");

                                    weatherData.add("地點: " + locationName +
                                            //        ", 元素: " + elementName +
                                            //        ", 描述: " + description +
                                            "。時間: " + startTime +
                                            "到" + endTime +
                                            "。天氣資訊: " + value );
                                    //         ", 單位: " + measures)
                                }
                            }
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(WeatherDataActivity.this, "請求失敗：" + response.code(), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                //Handle network connection exception
                Toast.makeText(WeatherDataActivity.this, "網路連接異常", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing exception
                Toast.makeText(WeatherDataActivity.this, "JSON解析異常", Toast.LENGTH_SHORT).show();
            } finally {
                if (response != null) {
                    response.close(); // Close the Response object
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherData;
    }
}