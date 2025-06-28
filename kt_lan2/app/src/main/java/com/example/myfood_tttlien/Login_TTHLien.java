package com.example.myfood_tttlien;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myfood_tttlien.DBHelper_TTHLien;
public class Login_TTHLien extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtRegister;
    DBHelper_TTHLien dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tthlien);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        dbHelper = new DBHelper_TTHLien(this);

        btnLogin.setOnClickListener(v -> login());

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Login_TTHLien.this, com.example.myfood_tttlien.Register_TTHLien.class);
            startActivity(intent);
        });
    }
    private void login() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User_TTHLien WHERE Username=? AND Password=?",
                new String[]{username, password});

        if (cursor.getCount() > 0) {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home_TTHLien.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
