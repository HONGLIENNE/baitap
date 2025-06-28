package com.example.myfood_tttlien;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_TTHLien extends SQLiteOpenHelper {

    public static final String DB_NAME = "Food_TTHLien.db";
    public static final int DB_VERSION = 1;

    public DBHelper_TTHLien(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User_TTHLien (" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT, Gender TEXT, DateOfBirth TEXT," +
                "Phone TEXT, Username TEXT, Password TEXT)");

        db.execSQL("CREATE TABLE Restaurant_TTHLien (" +
                "ResID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT, Address TEXT, Phone TEXT, Image TEXT)");

        db.execSQL("CREATE TABLE Food_TTHLien (" +
                "FoodID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT, Type TEXT, Description TEXT," +
                "Image TEXT, ResID INTEGER," +
                "FOREIGN KEY(ResID) REFERENCES Restaurant_TTHLien(ResID))");

        db.execSQL("CREATE TABLE Order_TTHLien (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Address TEXT, Date_order TEXT, Total_value REAL," +
                "Status TEXT, UserID INTEGER," +
                "FOREIGN KEY(UserID) REFERENCES User_TTHLien(UserID))");

        db.execSQL("CREATE TABLE OrderDetail_TTHLien (" +
                "OrderID INTEGER," +
                "FoodID INTEGER," +
                "Size TEXT, Quantity INTEGER, Food TEXT," +
                "PRIMARY KEY(OrderID, FoodID)," +
                "FOREIGN KEY(OrderID) REFERENCES Order_TTHLien(OrderID)," +
                "FOREIGN KEY(FoodID) REFERENCES Food_TTHLien(FoodID))");

        db.execSQL("INSERT INTO User_TTHLien (Name, Gender, DateOfBirth, Phone, Username, Password) VALUES" +
                "('Nguyen Van A', 'Male', '1990-01-01', '0909123456', 'user1', '123')," +
                "('Tran Thi B', 'Female', '1992-02-02', '0909234567', 'user2', '123')," +
                "('Le Van C', 'Male', '1993-03-03', '0909345678', 'user3', '123')," +
                "('Pham Thi D', 'Female', '1994-04-04', '0909456789', 'user4', '123')," +
                "('Do Van E', 'Male', '1995-05-05', '0909567890', 'user5', '123');");

        for (int i = 1; i <= 5; i++) {
            db.execSQL("INSERT INTO Restaurant_TTHLien(Name, Address, Phone, Image) VALUES(" +
                    "'Quán Ăn " + i + "', 'Địa chỉ " + i + "', '012345678" + i + "', 'h" + i + ".jpg');");
        }

        for (int i = 1; i <= 10; i++) {
            int resId = (i % 5) + 1;
            db.execSQL("INSERT INTO Food_TTHLien(Name, Type, Description, Image, ResID) VALUES(" +
                    "'Món ăn " + i + "', 'Loại " + i + "', 'Mô tả cho món " + i + "', 'food" + i + ".jpg', " + resId + ");");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OrderDetail_TTHLien");
        db.execSQL("DROP TABLE IF EXISTS Order_TTHLien");
        db.execSQL("DROP TABLE IF EXISTS Food_TTHLien");
        db.execSQL("DROP TABLE IF EXISTS Restaurant_TTHLien");
        db.execSQL("DROP TABLE IF EXISTS User_TTHLien");
        onCreate(db);
    }
}
