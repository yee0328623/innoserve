package com.example.myapplication40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity14 extends AppCompatActivity {

    private Button forgetBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);

        forgetBtn3 = findViewById(R.id.button9);
        forgetBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity14.this, MainActivity15.class);
                startActivity(intent);
            }
        });
    }
}