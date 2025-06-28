package com.example.myfood_tttlien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    private Context context;
    private List<Restaurant_TTHLien> restaurantList;

    public RestaurantAdapter(Context context, List<Restaurant_TTHLien> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return restaurantList.get(position).getResID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvAddress = convertView.findViewById(R.id.tvAddress);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        ImageView img = convertView.findViewById(R.id.imgRestaurant);

        Restaurant_TTHLien restaurant = restaurantList.get(position);
        tvName.setText(restaurant.getName());
        tvAddress.setText(restaurant.getAddress());
        tvPhone.setText("SĐT: " + restaurant.getPhone());

        // Lấy tên ảnh từ DB và loại bỏ .jpg
        String imageName = restaurant.getImage().replace(".jpg", "").trim();

        // Lấy ID ảnh từ thư mục drawable
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        // Set ảnh hoặc ảnh fallback nếu không tìm thấy
        if (imageResId != 0) {
            img.setImageResource(imageResId);
        } else {
            img.setImageResource(R.drawable.ic_launcher_background); // ảnh mặc định
        }

        return convertView;
    }
}
