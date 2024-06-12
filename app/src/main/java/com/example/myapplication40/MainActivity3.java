package com.example.myapplication40;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException; // 导入 SQLException 类
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast; // 导入 Toast 类
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication40.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    Button create;
    RadioButton man, women, secret;
    EditText password, Email, name,password2,phone;
    private String selectedGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        create = findViewById(R.id.btn3);
        man = findViewById(R.id.radioButton2);
        women = findViewById(R.id.radioButton3);
        password = findViewById(R.id.Password3);
        password2 = findViewById(R.id.editTextTextPassword3);
        Email = findViewById(R.id.Email2);
        name = findViewById(R.id.editText1);
        phone = findViewById(R.id.editTextPhone);

        create.setOnClickListener(createListener);
        man.setOnClickListener(radioClickListener);
        women.setOnClickListener(radioClickListener);
        password2.setOnClickListener(password2Listener);
        Email.setOnClickListener(EmailListener);
        name.setOnClickListener(nameListener);
        password.setOnClickListener(passwordListener);
        phone.setOnClickListener(phoneListener);
    }

    View.OnClickListener nameListener = view -> {
        String enteredName = name.getText().toString();
        Log.d("MainActivity3", "請輸入大名: " + enteredName);
        Intent it4 = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(it4);
    };
    View.OnClickListener phoneListener = view -> {
        String enteredphonenumber = phone.getText().toString();
        Log.d("MainActivity3", "請輸入電話: " + enteredphonenumber);
        Intent it4 = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(it4);
    };
    View.OnClickListener password2Listener = view -> {
        String enteredpassword2 = password2.getText().toString();
        Log.d("MainActivity3", "請輸入大名: " + enteredpassword2);
        Intent it4 = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(it4);
    };

    View.OnClickListener EmailListener = view -> {
        String enteredEmail = Email.getText().toString();
        Log.d("MainActivity3", "請輸入電子郵件: " + enteredEmail);
        Intent it3 = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(it3);
    };

    View.OnClickListener passwordListener = view -> {
        String enteredPassword = password.getText().toString();
        Log.d("MainActivity3", "Entered password: " + enteredPassword);
        Intent it2 = new Intent(MainActivity3.this, MainActivity3.class);
        startActivity(it2);
    };

    View.OnClickListener radioClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radioButton2:
                    selectedGender = "男";
                    break;
                case R.id.radioButton3:
                    selectedGender = "女";
                    break;

            }
            Log.d("MainActivity3", "Selected gender: " + selectedGender);
        }
    };

    View.OnClickListener createListener = view -> {
        String enteredName = name.getText().toString();
        String enteredEmail = Email.getText().toString();
        String enteredPassword = password.getText().toString();
        //String enteredpassword2 = password2.getText().toString();
        String enteredphonenumber = phone.getText().toString();
        // 获取 SQLite 数据库连接
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {
            db.beginTransaction(); // 开始事务

            try {
                ContentValues values = new ContentValues();
                values.put("name", enteredName);
                values.put("phonenumber", enteredphonenumber);
                values.put("mail", enteredEmail);
                values.put("password", enteredPassword);
                values.put("sex", selectedGender);

                long newRowId = db.insert("acc", null, values);
                if (newRowId != -1) {
                    Log.d("MainActivity3", "Successfully inserted data with ID: " + newRowId);

                    // 标记事务成功
                    db.setTransactionSuccessful();

                    // 显示注册成功的消息
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();

                    // 清空输入框等操作（如果需要的话）
                    name.setText("");
                    Email.setText("");
                    // 其他清空操作...

                    // 可以选择跳转到其他界面或执行其他操作
                } else {
                    Log.d("MainActivity3", "No data inserted.");

                    // 显示注册失败的消息（如果需要的话）
                    Toast.makeText(getApplicationContext(), "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();

                // 显示异常消息
                Toast.makeText(getApplicationContext(), "注册失败，数据库异常！", Toast.LENGTH_SHORT).show();
            } finally {
                // 结束事务
                db.endTransaction();
                // 关闭数据库连接
                db.close();
            }
        } else {
            Log.d("MainActivity3", "Database connection failed.");
        }

        Intent it8 = new Intent(MainActivity3.this,MainActivity6.class);
        startActivity(it8);
    };


}