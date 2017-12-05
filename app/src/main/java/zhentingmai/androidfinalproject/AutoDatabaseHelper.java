package zhentingmai.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gdyjm on 2017-12-02.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase";

    public static final int VERSION_NUM =3;
    public static final String TABLE_NAME = "AUTO";
    public static final String KEY_ID = "_ID";
    public static final String KEY_TIME = "TIME";
    public static final String KEY_PRICE = "PRICE";
    public static final String KEY_LITERS = "LITERS";
    public static final String KEY_KILO = "KILO";

    public static final String ACTIVITY_NAME = "AutoDatabaseHelper";

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TIME+ KEY_PRICE + KEY_LITERS + KEY_KILO + " TEXT )";

        db.execSQL(CREATE_TABLE);

        Log.i("AutoDatabaseHelper", "Calling onCreate");

    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

        Log.i("AutoDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer +" "+ "newVersion=" + newVer);
    }

    @Override

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

        Log.i("ChatDatabaseHelper", "Calling onDowngrade, newVersion=" + newVer +" "+ "oldVersion=" +oldVer );
    }
}