package tianjunjin.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sulijin.androidfinalproject.R;

public class Database_nutrition extends SQLiteOpenHelper {

    public static String DB_food_Name = "f_db_1";
    public static int DB_version =1;
    public static final String DB_food_table = "f_Table";
    public static final String key_food_RowID = "_id";
    public final static String key_food_TYPE = "type";
    public final static String key_TIME = "time";
    public final static String key_Calories= "calories";
    public final static String key_Total_Fat = "total_Fat";
    public final static String key_Carbohydrate= "carbohydrate";
    public SQLiteDatabase f_database;
    private Cursor c;

    public static final String ACTIVITY_NAME = "f_DatabaseHelper";

    public Database_nutrition(Context ctx) {
        super(ctx, DB_food_Name, null,DB_version);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_MSG = "CREATE TABLE " + DB_food_table + "("
                + key_food_RowID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + key_food_TYPE  + " TEXT, "
                + key_TIME + " TEXT, "
                + key_Calories + " TEXT, "
                + key_Total_Fat + " TEXT, "
                + key_Carbohydrate + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_MSG);
    }
    @Override
    public void onOpen(SQLiteDatabase db){}

    public void onUpgrade(SQLiteDatabase db,int newVersion, int oldVersion ){
        db.execSQL("DROP TABLE IF EXISTS "+ DB_food_table);
        onCreate(db);
    }

    public void setWritable() {
        f_database = getWritableDatabase();
    }

    public void closeDatabase() {
        if (f_database != null && f_database.isOpen()) {
            f_database.close();
        }
    }

    public void insert(String type, String time, String calories, String total_Fat, String carbohydrate) {
        f_database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(key_food_TYPE, type);
        values.put(key_TIME, time);
        values.put(key_Calories, calories);
        values.put(key_Total_Fat, total_Fat);
        values.put( key_Carbohydrate, carbohydrate);

        f_database.insert(DB_food_table, null, values);
    }
    public void delete(Long id) {
        f_database = this.getWritableDatabase();

        f_database.delete(DB_food_table,  key_food_RowID + " = " + id, null);
    }
    public void update(Long id,String type, String time, String calories, String total_Fat, String carbohydrate) {
        f_database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(key_food_TYPE, type);
        values.put(key_TIME, time);
        values.put(key_Calories, calories);
        values.put(key_Total_Fat, total_Fat);
        values.put( key_Carbohydrate, carbohydrate);

        f_database.update(DB_food_table, values, key_food_RowID  + " = " + id, null);
    }
    public int getTotal(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd" +
                "");
        String today = dateFormat.format(new Date()); //date of today

        Date myDate = null;
        try {
            myDate = dateFormat.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate = new Date(myDate.getTime() - 1);
        String yesterday = dateFormat.format(newDate);

        System.out.println(today);
        System.out.println(yesterday);

        c = f_database.rawQuery("SELECT * FROM " + DB_food_table + " Where "+ key_TIME + " like '" + yesterday + "%'",null);
        c.moveToFirst();

        int caloriesLastDay = 0;
        while(!c.isAfterLast()){
            caloriesLastDay += Integer.parseInt(c.getString(c.getColumnIndex(key_Calories)));
            c.moveToNext();
        }
        c.close();
        return caloriesLastDay;
    }
    public double getAvg(){
        f_database = getWritableDatabase();
        double total = 0.00;
        double avg = 0.00;
        c = f_database.rawQuery("select * from " + DB_food_table ,null);
            int num = c.getCount();
        c = f_database.rawQuery("select * from " + DB_food_table,null);
            for(int i = 0; i< num; i++){
                c.moveToPosition(i);
                total += Double.parseDouble(c.getString(c.getColumnIndex(key_Calories)));
            }
            avg = total/num;
        c.close();
        return avg;
    }

}

