package com.example.baitaprecyclerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private TextView lastViewedTextView, orderedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        recyclerView = findViewById(R.id.recyclerView);
        lastViewedTextView = findViewById(R.id.lastViewedTextView);
        orderedTextView = findViewById(R.id.orderedTextView);

        // Tạo danh sách món ăn mẫu
        foodList = new ArrayList<>();
        foodList.add(new Food("Phở", R.drawable.pho, "Phở bò truyền thống với nước dùng đậm đà", 45000));
        foodList.add(new Food("Bún chả", R.drawable.buncha, "Bún chả Hà Nội thơm ngon, thịt nướng vàng ươm", 40000));
        foodList.add(new Food("Bánh mì", R.drawable.banhmi, "Bánh mì kẹp thịt, rau sống, nước sốt", 20000));
        foodList.add(new Food("Cơm tấm", R.drawable.comtam, "Cơm tấm sườn bì chả, trứng ốp la", 50000));
        foodList.add(new Food("Gỏi cuốn", R.drawable.goicuon, "Gỏi cuốn tôm thịt, nước chấm đậm đà", 30000));

        // Cài đặt RecyclerView
        foodAdapter = new FoodAdapter(foodList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodAdapter);

        // Đọc SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LastViewedFood", MODE_PRIVATE);

        // Hiển thị món đã xem
        String lastFood = prefs.getString("lastFoodName", null);
        if (lastFood != null) {
            lastViewedTextView.setText("Bạn vừa xem: " + lastFood);
        } else {
            lastViewedTextView.setText("Bạn chưa xem món ăn nào.");
        }

        // Hiển thị món đã gọi
        String orderedFood = prefs.getString("orderedFoodName", null);
        if (orderedFood != null) {
            orderedTextView.setText("Bạn đã gọi món: " + orderedFood);
        } else {
            orderedTextView.setText("Bạn chưa gọi món nào.");
        }

        // Swipe to delete món ăn trong danh sách
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false; // Không xử lý kéo thả
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        foodAdapter.removeItem(viewHolder.getAdapterPosition());
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
