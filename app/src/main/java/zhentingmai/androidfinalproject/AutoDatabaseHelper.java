package zhentingmai.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gdyjm on 2017-12-02.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase.db";

    public static final int VERSION_NUM =3;
    public static final String TABLE_NAME = "AUTO";
    public static final String KEY_ID = "_ID";
    public static final String KEY_TIME = "TIME";
    public static final String KEY_PRICE = "PRICE";
    public static final String KEY_LITERS = "LITERS";
    public static final String KEY_KILO = "KILO";

    public SQLiteDatabase database;

    public static final String ACTIVITY_NAME = "AutoDatabaseHelper";

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TIME+ " TEXT, "+ KEY_PRICE + " TEXT, "+ KEY_LITERS+ " TEXT, " + KEY_KILO + " TEXT)";

        db.execSQL(CREATE_TABLE);

        Log.i(ACTIVITY_NAME, "Calling onCreate");

    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVer +" "+ "newVersion=" + newVer);
    }

    @Override

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onDowngrade, newVersion=" + newVer +" "+ "oldVersion=" +oldVer );
    }

    public void openDatabase() {
        database = getWritableDatabase();
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void insert(String time, String price, String liters, String kilo) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, time);
        values.put(KEY_PRICE, price);
        values.put(KEY_LITERS, liters);
        values.put(KEY_KILO, kilo);

        database.insert(TABLE_NAME, null, values);
    }

    public void delete(Long id) {
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id);
    }

    public void update(String id, String time, String price, String liters, String kilo) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, time);
        values.put(KEY_PRICE, price);
        values.put(KEY_LITERS, liters);
        values.put(KEY_KILO, kilo);

        database.update(TABLE_NAME, values, KEY_ID + " = " + id, null);
    }

    public Cursor read() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }
}