package com.example.myapplication40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity9 extends AppCompatActivity {

    private Button waterBtn;
    private Spinner year,area,type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        year=findViewById(R.id.spinner2);
        area=findViewById(R.id.spinner4);
        type=findViewById(R.id.spinner5);
        waterBtn = findViewById(R.id.btn8);
        waterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedYearold = year.getSelectedItem().toString();
                String selectedAreaold = area.getSelectedItem().toString();
                String selectedTypeold = type.getSelectedItem().toString();
                // 创建一个意图以打开WaterDataActivity
                Intent intent = new Intent(MainActivity9.this, WaterDataActivity.class);
                intent.putExtra("selectedYear", selectedYearold);
                intent.putExtra("selectedArea", selectedAreaold);
                intent.putExtra("selectedType", selectedTypeold);
                startActivity(intent);
            }
        });

    }
}