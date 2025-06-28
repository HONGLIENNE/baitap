package com.example.myfood_tttlien;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood_tttlien.Login_TTHLien;

public class Register_TTHLien extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtRePassword;
    Button btnRegister;
    TextView txtLogin;
    com.example.myfood_tttlien.DBHelper_TTHLien dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tthlien);

        edtUsername = findViewById(R.id.et_username);
        edtPassword = findViewById(R.id.et_password);
        edtRePassword = findViewById(R.id.et_repassword);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.tv_login_link);


        dbHelper = new com.example.myfood_tttlien.DBHelper_TTHLien(this);

        btnRegister.setOnClickListener(v -> registerUser());

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register_TTHLien.this, Login_TTHLien.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String repassword = edtRePassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", username); // Có thể thêm Name nếu cần
        values.put("Username", username);
        values.put("Password", password);
        values.put("Gender", ""); // Tạm bỏ trống
        values.put("DateOfBirth", "");
        values.put("Phone", "");

        long result = db.insert("User_TTHLien", null, values);

        if (result != -1) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login_TTHLien.class));
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
