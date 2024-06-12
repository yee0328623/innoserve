package com.example.myapplication40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity13 extends AppCompatActivity {

    private Button forgetBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        forgetBtn2 = findViewById(R.id.button8);
        forgetBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity13.this, MainActivity14.class);
                startActivity(intent);
            }
        });
    }
}