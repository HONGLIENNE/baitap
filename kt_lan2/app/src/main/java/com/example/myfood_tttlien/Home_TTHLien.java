package com.example.myfood_tttlien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home_TTHLien extends AppCompatActivity {

    ListView lvRestaurant;
    ArrayList<Restaurant_TTHLien> restaurantList;
    RestaurantAdapter adapter;
    DBHelper_TTHLien dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tthlien);

        lvRestaurant = findViewById(R.id.lvRestaurant);
        restaurantList = new ArrayList<>();
        adapter = new RestaurantAdapter(this, restaurantList);
        lvRestaurant.setAdapter(adapter);

        dbHelper = new DBHelper_TTHLien(this);
        loadRestaurantData();
    }

    private void loadRestaurantData() {
        restaurantList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Restaurant_TTHLien", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String phone = cursor.getString(3);
                String image = cursor.getString(4);

                restaurantList.add(new Restaurant_TTHLien(id, name, address, phone, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
