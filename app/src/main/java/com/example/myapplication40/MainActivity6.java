package com.example.myapplication40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity6 extends AppCompatActivity {

    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        //連結物件變數與UI的元件
        button5 = findViewById(R.id.button5);

        //安裝傾聽者Listener
        button5.setOnClickListener(button5Listener);

    }
    View.OnClickListener button5Listener = view -> {
        Intent it8 = new Intent(MainActivity6.this,MapsActivity.class);
        startActivity(it8);
    };
}