package xiasheng.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Cynthia Sheng on 06/12/2017.
 */

public class House_DatabaseHelper extends SQLiteOpenHelper {


    protected final static String DATABASE_NAME = "HouseTemperature1";
    protected final static String TABLE_NAME = "Temperature_Table";
    protected final static int VERSION_NUM = 1;
    protected final static String ID = "_id";
    protected final static String DAY= "weekday";
    protected final static String HOUR = "hour";
    protected final static String MINUTE= "minutes";
    protected final static String Temperature = "HouseTemperature";

    protected SQLiteDatabase database;
    protected static final String ACTIVITY_NAME = "AutoDatabaseHelper";

    protected House_DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " +TABLE_NAME+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                 DAY + " text, " +
                                                 HOUR + " text, " +
                                                 MINUTE + " text, " +
                                                 Temperature + " text );");
        Log.i(ACTIVITY_NAME, "Table " + TABLE_NAME + " is created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Table " + TABLE_NAME + " is upgraded, oldVersion= " + oldVer
                + " newVersion=" + newVer);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);
        //  Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i(ACTIVITY_NAME, "Table " + TABLE_NAME + " is downgraded, newVersion=" + newVersion + "oldVersion=" + oldVersion);
    }
    public void openDatabase() {
        database = getWritableDatabase();
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void insert(String day, String hour, String minute, String temp) {
        ContentValues values = new ContentValues();
        values.put(DAY, day);
        values.put(HOUR, hour);
        values.put(MINUTE, minute);
        values.put(Temperature, temp);

        database.insert(TABLE_NAME, null, values);
    }

    public void delete(String id) {
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = " + id);
    }

    public void update(String id, String day, String hour, String minute, String temp) {
        ContentValues values = new ContentValues();
        values.put(DAY, day);
        values.put(HOUR, hour);
        values.put(MINUTE, minute);
        values.put(Temperature, temp);

        database.update(TABLE_NAME, values, ID + " = " + id, null);
    }

    protected Cursor read() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }
@Override
public void onOpen(SQLiteDatabase db)
{
}


}


