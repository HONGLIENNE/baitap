package com.example.sqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlite.adapter.EmployeeAdapter;
import com.example.sqlite.model.Employee;
import com.example.sqlite.sqlite.DBHelper;
import com.example.sqlite.sqlite.EmployeeDao;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private EmployeeAdapter employeeAdapter;
    private ListView liEmployees;
    private String emloyeeId;
    private List<Employee> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.close();
        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);

        lvEmloyees = findViewById(R.id.lvEmployees);
        EmployeeDao = new EmployeeDao(this);
         list = dao.getAll();

        employeeAdapter = new EmployeeAdapter(this, list);
        lvEmployees.setAdapter(employeeAdapter);
        lvEmloyees.setOnItenClickListner(new View.OnClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Employee emp = list.get(i);
                emloyeeId = emp.getId();
            }
                                         }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
@Override
protected void onResume() {
    super.onResume();
    EmployeeDao dao =  new EmployeeDao(this);
    List<Employee> updateList = dao.getAll();
    list.clear();
    updateList.forEach(item->list.add(item));

    employeeAdapter.notifyDataSetChanged();
}
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEdit) {
            Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnDelete) {
            // Add your delete logic here
            // For example, you might want to delete a selected employee

        }
    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);
        switch (view.getId()){
            case R.id.btnInsert:
                startActivity(intent);
                break;
            case R.id.btnEdit:
                if (emloyeeId == null){
                    Toast.makeText(this, "Employee id must be selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", emloyeeId);
                intent.putExtra("data",bundle);

                startActivity(intent);
                break;
            case R.id.btnDelete:
                if (emloyeeId == null) {
                    Toast.makeText(this, "Employee id must be selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                EmployeeDao dao = new EmployeeDao(this);
                dao.delete(emloyeeId);
                emloyeeId = null;

                onResume();

                Toast.makeText(this, "Emloyee was deleted",Toast.LENGTH_SHORT).show();
                break;
                }
        }
    }
}