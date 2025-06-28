package com.example.api;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo imageView đúng cú pháp
        ImageView imageView = findViewById(R.id.imageView);

        // URL ảnh từ Internet
        String imageUrl = "https://tse1.mm.bing.net/th?id=OIP.BeCxYmC74amByqaUU3hthgHaFr&pid=Api&P=0&h=220";

        // Dùng Glide để Load ảnh
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading)    // ảnh hiển thị khi đang tải (nếu có)
                .error(R.drawable.error_image)      // ảnh nếu tải thất bại (nếu có)
                .into(imageView);                   // load ảnh vào imageView
    }
}
