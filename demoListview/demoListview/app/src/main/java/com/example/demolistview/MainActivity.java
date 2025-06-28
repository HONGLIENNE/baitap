package com.example.demolistview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
// khởi tạo 1 arraylist để chứa contact
    private ArrayList<contactModel> arraycontact = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        khoiTaoData();
    }
    //khởi tảo data
    private void khoiTaoData() {
        arraycontact.add(new contactModel("Aang", "09000001", R.drawable.user1));
        arraycontact.add(new contactModel("Katara", "09000002", R.drawable.user2));
        arraycontact.add(new contactModel("Kyoshi", "09000003", R.drawable.user3));
        arraycontact.add(new contactModel("Sokka", "09000004", R.drawable.user4));
        arraycontact.add(new contactModel("Suki", "09000005", R.drawable.user5));
        arraycontact.add(new contactModel("Toph", "09000006", R.drawable.user6));
        arraycontact.add(new contactModel("TyLee", "09000007", R.drawable.user7));
        arraycontact.add(new contactModel("Zuko", "09000008", R.drawable.user8));


    }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}