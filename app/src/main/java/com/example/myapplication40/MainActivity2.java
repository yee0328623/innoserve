package com.example.myapplication40;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Button btn2;
    EditText password, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getAssets();

        //連結物件變數與UI的元件
        password = findViewById(R.id.password2);
        Email = findViewById(R.id.Email);
        btn2 = findViewById(R.id.btn2);

        //安裝傾聽者Listener
        btn2.setOnClickListener(btn2Listener);
        Email.setOnClickListener(EmailListener);
        password.setOnClickListener(passwordListener);

        // Find the txt4 TextView and add a click listener
        TextView forgetBtn = findViewById(R.id.txt4);
        forgetBtn.setOnClickListener(txt4Listener);
    }

    View.OnClickListener txt4Listener = view -> {
        // Start MainActivity13 when txt4 is clicked
        Intent intent = new Intent(MainActivity2.this, MainActivity13.class);
        startActivity(intent);
    };

    View.OnClickListener EmailListener = view -> {
        String enteredEmail = Email.getText().toString();
        Log.d("MainActivity3", "請輸入電子郵件: " + enteredEmail);
        Intent it3 = new Intent(MainActivity2.this, MainActivity2.class);
        startActivity(it3);
    };

    View.OnClickListener passwordListener = view -> {
        String enteredPassword = password.getText().toString();
        Log.d("MainActivity3", "Entered password: " + enteredPassword);
        Intent it2 = new Intent(MainActivity2.this, MapsActivity.class);
        startActivity(it2);
    };

    View.OnClickListener btn2Listener = view -> {
        String enteredEmail = Email.getText().toString(); // 獲取輸入的電子郵件地址
        String enteredPassword = password.getText().toString();
        String passwordFromDatabase = getPasswordFromDatabase(enteredEmail); // 呼叫函式查詢密碼
        if (enteredPassword.equals(passwordFromDatabase)) {
            Toast.makeText(getApplicationContext(), "登入成功！", Toast.LENGTH_SHORT).show();
            Intent it2 = new Intent(MainActivity2.this, MapsActivity.class);
            startActivity(it2);
        } else {
            // 密碼不相等，可以顯示一個錯誤消息或採取其他操作
            // 例如，顯示 Toast 消息
            Toast.makeText(getApplicationContext(), "密碼不正確", Toast.LENGTH_SHORT).show();
            Intent it2 = new Intent(MainActivity2.this, MainActivity2.class);
            startActivity(it2);
        }

    };

    // 函式用來查詢密碼
    private String getPasswordFromDatabase(String email) {
        String password = "";

        // 在這裡進行資料庫查詢，使用 email 作為查詢條件，獲取相應的 password
        // 這裡假設你已經設定了 DatabaseHelper 並有相應的資料庫連接程式碼

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.query("acc", new String[]{"password"}, "mail=?", new String[]{email}, null, null, null);

            if (cursor != null) {
                // 檢查是否找到對應的列名
                if (cursor.moveToFirst()) {
                    int passwordIndex = cursor.getColumnIndex("password");
                    if (passwordIndex >= 0) {
                        // 找到對應的列名，獲取密碼
                        password = cursor.getString(passwordIndex);
                    }
                }

                cursor.close();
            }

            db.close();
        }

        return password;
    }
}
