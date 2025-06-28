package com.example.kt_nhom4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button buttonAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  layout
        buttonAddToCart = findViewById(R.id.giohang);

     //"Thêm vào giỏ hàng"
        buttonAddToCart.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,
                    "SẢN PHẨM ĐÃ ĐƯỢC THÊM VÀO GIỎ HÀNG CỦA BẠN",
                    Toast.LENGTH_LONG).show();
        });
    }
}
