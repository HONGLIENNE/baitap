package com.example.sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private SQLiteDatabase db;

    public EmployeeDao(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Employee> get(String sql, String... selectArgs) {
        List<Employee> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);

        while (cursor.moveToNext()) {
            // Lấy chỉ số cột và kiểm tra trước khi sử dụng
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int salaryIndex = cursor.getColumnIndex("salary");

            // Chỉ thêm employee nếu tất cả các cột tồn tại
            if (idIndex != -1 && nameIndex != -1 && salaryIndex != -1) {
                Employee emp = new Employee();
                emp.setId(cursor.getString(idIndex));
                emp.setName(cursor.getString(nameIndex));
                emp.setSalary(cursor.getFloat(salaryIndex));
                list.add(emp);
            }
        }

        cursor.close();
        return list;
    }

    public List<Employee> getAll() {
        String sql = "SELECT * FROM nhanvien";
        return get(sql);
    }

    public long insert(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("id", emp.getId());
        values.put("name", emp.getName());
        values.put("salary", emp.getSalary());
        return db.insert("nhanvien", null, values);
    }

    public long update(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("name", emp.getName());
        values.put("salary", emp.getSalary());
        return db.update("nhanvien", values, "id=?", new String[]{emp.getId()});
    }

    public int delete(String id) {
        return db.delete("nhanvien", "id=?", new String[]{id});
    }

    public Employee getById(String id) {
        List<Employee> list = get("SELECT * FROM nhanvien WHERE id = ?", id);
        if (list.isEmpty()) return null;
        return list.get(0);
    }
}