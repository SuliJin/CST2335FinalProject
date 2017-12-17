package tianjunjin.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_nutrition extends SQLiteOpenHelper {

    public static String DB_food_Name = "Nutrition1.db";
    public static int DB_version =2;
    static final String DB_food_table = "Nutrition_Table";
    static final String key_food_RowID = "_id";
    static final String key_food_message = "Nutrition_detail";

    public Database_nutrition(Context ctx) {
        super(ctx, DB_food_Name, null,DB_version);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + DB_food_table +"( _id INTEGER PRIMARY KEY AUTOINCREMENT, Nutrition_detail text);");
}
    public void onUpgrade(SQLiteDatabase db,int newVersion, int oldVersion ){
        //  super(Context ctx ,String "Messages.db" , SQLiteDatabase.CursorFactory factory,int version);
        db.execSQL("DROP TABLE IF EXISTS "+ DB_food_table);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db)
    {
    }

}




