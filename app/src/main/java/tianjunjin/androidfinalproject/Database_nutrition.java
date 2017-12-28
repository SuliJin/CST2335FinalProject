package tianjunjin.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sulijin.androidfinalproject.R;

public class Database_nutrition extends SQLiteOpenHelper {

    public static String DB_food_Name = "f_db";
    public static int DB_version =1;
    public static final String DB_food_table = "f_Table";
    public static final String key_food_RowID = "_id";
    public final static String key_food_TYPE = "type";
    public final static String key_TIME = "time";
    public final static String key_Calories= "Calories";
    public final static String key_Total_Fat = "Total_Fat";
    public final static String key_Carbohydrate= "Carbohydrate";

    public SQLiteDatabase f_database;

    private Context context;

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
        //  super(Context ctx ,String "Messages.db" , SQLiteDatabase.CursorFactory factory,int version);
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
        f_database = getWritableDatabase();
        f_database.execSQL("DELETE FROM " +DB_food_table + " WHERE " + key_food_RowID + " = " + id);
    }


    public void update(Long id,String type, String time, String calories, String total_Fat, String carbohydrate) {
        f_database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(key_food_TYPE, type);
        values.put(key_TIME, time);
        values.put(key_Calories, calories);
        values.put(key_Total_Fat, total_Fat);
        values.put( key_Carbohydrate, carbohydrate);

        f_database.update(DB_food_table, values, key_food_RowID  + " = " + id, null);
    }

    Cursor c;

        public String getAvg(){
         c= f_database.rawQuery("SELECT AVG(" + key_Calories +") FROM "+ DB_food_table,null);
        c.moveToFirst();
        String avg = c.getString(0);
        if(avg==null)
            return context.getString( R.string.f_noReord);
        else
            return avg;
    }

//    public String getAvg(){
//       /* Calendar calendar = Calendar.getInstance();
//        int iYear = calendar.get(Calendar.YEAR);
//        int iMonth = calendar.get(Calendar.MONTH);*/
//        //Cursor c;
//        if(iMonth==1)
//            c=database.rawQuery("SELECT AVG(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
//                    KEY_YEAR + " = " + (iYear-1) + " AND " + KEY_MONTH + " = " + 12,null);
//        else
//            c=database.rawQuery("SELECT AVG(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
//                    KEY_YEAR + " = " + iYear + " AND " + KEY_MONTH + " = " + (iMonth-1),null);
//
//        // c=database.rawQuery("SELECT AVG(" + KEY_PRICE +") FROM "+TABLE_NAME,null);
//        c.moveToFirst();
//        String avg = c.getString(0);
//        if(avg==null)
//            return context.getString( R.string.auto_noRecord);
//        else
//            return avg;
//
//    }
//
//    public String getTotal(){
//
//        if(iMonth==1)
//            c=database.rawQuery("SELECT SUM(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
//                    KEY_YEAR + " = " + (iYear-1) + " AND " + KEY_MONTH + " = " + 12,null);
//        else
//            c=database.rawQuery("SELECT SUM(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
//                    KEY_YEAR + " = " + iYear + " AND " + KEY_MONTH + " = " + (iMonth-1),null);
//        c.moveToFirst();
//        String strCost = c.getString(0);
//        String strAvgPrice = getAvg();
//        if(strCost==null||strAvgPrice==null){
//            return context.getString( R.string.auto_noRecord);
//        }
//        else{
//            double dCost = Double.parseDouble(strCost);
//            double dAvgPrice = Double.parseDouble(getAvg());
//            String total = ""+dCost*dAvgPrice;
//            return total;
//        }
//    }

//    public Cursor getSumCursor(){
//        c=f_database.rawQuery("SELECT " + KEY_YEAR+"||','||"+KEY_MONTH+", SUM("+KEY_LITERS+") FROM" +
//                " "+TABLE_NAME + " GROUP BY "+KEY_YEAR+","+KEY_MONTH,null);
//        return null;
//    }


    public Cursor getCursor() {
        return f_database.query(DB_food_table, null, null, null, null, null, null);
    }

}

