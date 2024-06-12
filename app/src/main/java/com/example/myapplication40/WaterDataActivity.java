package com.example.myapplication40;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WaterDataActivity extends AppCompatActivity {

    private ListView listView;
    private Spinner yearSpinner,areaSpinner,typeSpinner;
    private String selectedYear,selectedArea,selectedType;
    private int rowCount;
    private TextView count;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        yearSpinner = findViewById(R.id.year_spinner);
        areaSpinner = findViewById(R.id.area_spinner);
        typeSpinner = findViewById(R.id.type_spinner);
        listView = findViewById(R.id.listView);
        count=findViewById(R.id.count);
        selectedYear = "2017"; // 设置默认年份
        selectedArea = "臺北市"; // 设置默认区域
        selectedType = "湖潭"; // 设置默认类型
        loadWaterData(selectedYear, selectedArea, selectedType);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.place_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();

                loadWaterData(selectedYear,selectedArea,selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        ArrayAdapter<CharSequence> areaAdapter = ArrayAdapter.createFromResource(this, R.array.water_county, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        areaSpinner .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = parent.getItemAtPosition(position).toString();
                loadWaterData(selectedYear,selectedArea,selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence>  typeAdapter = ArrayAdapter.createFromResource(this, R.array.water_type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getItemAtPosition(position).toString();
                loadWaterData(selectedYear,selectedArea,selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private AlertDialog currentAlertDialog; // 宣告全域的AlertDialog變數

    private void loadWaterData(String selectedYear, String selectedArea, String selectedType) {
        List<String> waterList = getWaterDataFromDatabase(selectedYear, selectedArea, selectedType);
        rowCount = waterList.size();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, waterList);
        listView.setAdapter(adapter);

        // 如果當前有AlertDialog正在顯示，先將其關閉
        if (currentAlertDialog != null && currentAlertDialog.isShowing()) {
            currentAlertDialog.dismiss();
        }

        if (rowCount == 0) {
            currentAlertDialog = new AlertDialog.Builder(WaterDataActivity.this)
                    .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                    .setTitle("查無資料")
                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle button click
                        }
                    })
                    .create();
            currentAlertDialog.show();
        } else {
            currentAlertDialog = new AlertDialog.Builder(WaterDataActivity.this)
                    .setIcon(android.R.drawable.ic_menu_search)
                    .setTitle("共查詢到" + rowCount + "筆資料")
                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle button click
                        }
                    })
                    .create();
            currentAlertDialog.show();
        }
    }


    private List<String> getWaterDataFromDatabase(String selectedYear,String selectedArea,String selectedType) {
        List<String> waterList = new ArrayList<>();
        SQLiteDatabase database = null;

        try {
            database = SQLiteDatabase.openDatabase(getDatabasePath("water.db").getPath(), null, SQLiteDatabase.OPEN_READONLY);

            String tableName = "消防機關水域救援統計";
            String query;

            if (selectedYear.equals("2017~2021")) {
                if (selectedArea.equals("所有區域") && selectedType.equals("所有種類")) {
                    query = "SELECT * FROM " + tableName;
                } else if (selectedArea.equals("所有區域")) {
                    query = "SELECT * FROM " + tableName + " WHERE 水域種類 = '" + selectedType + "'";
                } else if (selectedType.equals("所有種類")) {
                    query = "SELECT * FROM " + tableName + " WHERE 縣市別 = '" + selectedArea + "'";
                } else {
                    query = "SELECT * FROM " + tableName + " WHERE 縣市別 = '" + selectedArea + "' AND 水域種類 = '" + selectedType + "'";
                }
            } else {
                if (selectedArea.equals("所有區域") && selectedType.equals("所有種類")) {
                    query = "SELECT * FROM " + tableName + " WHERE 年份 = '" + selectedYear + "'";
                } else if (selectedArea.equals("所有區域")) {
                    query = "SELECT * FROM " + tableName + " WHERE 年份 = '" + selectedYear + "' AND 水域種類 = '" + selectedType + "'";
                } else if (selectedType.equals("所有種類")) {
                    query = "SELECT * FROM " + tableName + " WHERE 年份 = '" + selectedYear + "' AND 縣市別 = '" + selectedArea + "'";
                } else {
                    query = "SELECT * FROM " + tableName + " WHERE 年份 = '" + selectedYear + "' AND 縣市別 = '" + selectedArea + "' AND 水域種類 = '" + selectedType + "'";
                }
            }


            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    // int idIndex = cursor.getColumnIndex("編號");
                    int yearIndex = cursor.getColumnIndex("年份");
                    int cityIndex = cursor.getColumnIndex("縣市別");
                    int locationIndex = cursor.getColumnIndex("溺水地點或附近地標");
                    int waterTypeIndex = cursor.getColumnIndex("水域種類");
                    int reasonIndex = cursor.getColumnIndex("溺水原因");
                    int resultIndex = cursor.getColumnIndex("溺水結果");

                    // String id = cursor.getString(idIndex);
                    String year = cursor.getString(yearIndex);
                    String city = cursor.getString(cityIndex);
                    String location = cursor.getString(locationIndex);
                    String waterType = cursor.getString(waterTypeIndex);
                    String reason = cursor.getString(reasonIndex);
                    String result = cursor.getString(resultIndex);

                    waterList.add(/*"ID: " + id + */" Year: " + year + " City: " + city + ", Location: " + location +
                            ", Water Type: " + waterType + ", Reason: " + reason +
                            ", Result: " + result);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return waterList;
    }

    @Override
    public File getDatabasePath(String name) {
        File file = new File(getFilesDir(), "databases");
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            InputStream inputStream = getAssets().open("water.db");
            OutputStream outputStream = new FileOutputStream(new File(file, name));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(file, name);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(getDatabasePath(name).getPath(), mode, factory);
    }
}




