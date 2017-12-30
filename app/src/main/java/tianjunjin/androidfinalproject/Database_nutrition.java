package tianjunjin.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database_nutrition extends SQLiteOpenHelper {

    public static String DB_food_Name = "Nutrition5.db";
    public static int DB_version =2;
    public static final String DB_food_table = "Nutrition_Table";
    public static final String key_food_RowID = "_id";
    public final static String key_food_TYPE = "type";
    public final static String key_TIME = "time";
    public final static String key_Calories= "Calories";
    public final static String key_Total_Fat = "Total_Fat";
    public final static String key_Carbohydrate= "Carbohydrate";



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
        //  super(Context ctx ,String "Messages.db" , SQLiteDatabase.CursorFactory factory,int version);
        db.execSQL("DROP TABLE IF EXISTS "+ DB_food_table);
        onCreate(db);
    }
}

