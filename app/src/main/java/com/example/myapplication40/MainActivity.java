package com.example.myapplication40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //連結物件變數與UI的元件
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        //Step 3. 安裝傾聽者Listener
        button3.setOnClickListener(button3Listener);
        button4.setOnClickListener(button4Listener);

    }
    View.OnClickListener button3Listener = view -> {
        Intent it1 = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(it1);
    };
    View.OnClickListener button4Listener = view -> {
        Intent it2 = new Intent(MainActivity.this,MainActivity3.class);
        startActivity(it2);
    };


}