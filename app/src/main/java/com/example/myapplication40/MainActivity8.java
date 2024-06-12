package com.example.myapplication40;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity8 extends AppCompatActivity {

    private Button sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);


        sendbtn = findViewById(R.id.btn7);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity8.this)
                        .setIcon(R.drawable._161308)
                        .setTitle("已成功通報")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent ent = new Intent(MainActivity8.this, MainActivity8.class);
                                startActivity(ent);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}