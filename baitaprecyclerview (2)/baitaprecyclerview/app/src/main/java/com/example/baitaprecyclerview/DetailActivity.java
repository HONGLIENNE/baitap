package com.example.baitaprecyclerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImageView;
    private TextView detailTextView;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImageView = findViewById(R.id.detailImageView);
        detailTextView = findViewById(R.id.detailTextView);
        orderButton = findViewById(R.id.orderButton); // đã sửa lại ID trong XML

        Food food = getIntent().getParcelableExtra("foodItem");

        if (food != null) {
            detailImageView.setImageResource(food.getImageResId());
            detailTextView.setText("Tên: " + food.getName()
                    + "\nMô tả: " + food.getDescription()
                    + "\nGiá: " + food.getPrice() + " VND");

            orderButton.setOnClickListener(v ->
                    Toast.makeText(this, "Bạn đã gọi món: " + food.getName(), Toast.LENGTH_SHORT).show()
            );

            SharedPreferences prefs = getSharedPreferences("LastViewedFood", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastFoodName", food.getName());
            editor.apply();
        } else {
            detailTextView.setText("Không có thông tin món ăn");
        }
    }
}
