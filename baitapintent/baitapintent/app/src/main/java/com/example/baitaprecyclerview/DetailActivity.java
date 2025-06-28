package com.example.baitaprecyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImageView;
    private TextView detailTextView;
    private Button orderButton, callButton, mapButton, websiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImageView = findViewById(R.id.detailImageView);
        detailTextView = findViewById(R.id.detailTextView);
        orderButton = findViewById(R.id.orderButton);
        callButton = findViewById(R.id.callButton);
        mapButton = findViewById(R.id.mapButton);
        websiteButton = findViewById(R.id.websiteButton);

        Food food = getIntent().getParcelableExtra("foodItem");

        if (food != null) {
            detailImageView.setImageResource(food.getImageResId());
            detailTextView.setText("Tên: " + food.getName()
                    + "\nMô tả: " + food.getDescription()
                    + "\nGiá: " + food.getPrice() + " VND");

            // Ghi nhớ món đã xem
            SharedPreferences prefs = getSharedPreferences("LastViewedFood", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastFoodName", food.getName());
            editor.apply();

            // Gọi món (lưu)
            orderButton.setOnClickListener(v -> {
                Toast.makeText(this, "Bạn đã gọi món: " + food.getName(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor orderEditor = prefs.edit();
                orderEditor.putString("orderedFoodName", food.getName());
                orderEditor.apply();
            });

            // GỌI ĐIỆN đặt món
            callButton.setOnClickListener(v -> {
                String phoneNumber = "0123456789"; // Thay bằng số điện thoại quán
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            });

            // XEM BẢN ĐỒ địa điểm quán
            mapButton.setOnClickListener(v -> {
                String geoUri = "geo:0,0?q=Quán+ăn+Cơm+tấm+Kiều+Giang"; // đổi địa điểm nếu cần
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            });

            // TRUY CẬP WEBSITE của quán
            websiteButton.setOnClickListener(v -> {
                String url = "https://www.example.com/mon-an"; // thay bằng URL thật nếu có
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webIntent);
            });

        } else {
            detailTextView.setText("Không có thông tin món ăn");
        }
    }
}
