package sulijin.androidfinalproject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Suli Jin on 10/11/2017.
 */

public class ActivityTrackingDatabaseHelper extends SQLiteOpenHelper {
    public final static String LOGGER_NAME = "ChatDatabaseHelper";
    public final static String DATABASE_NAME = "T_Activity_DB";
    public final static String TABLE_NAME = "T_Activity_Table";
    public final static int VERSION_NUM = 3;
    public final static String ID = "_id";
    public final static String TYPE = "type";
    public final static String TIME = "time";
    public final static String DURATION = "duration";
    public final static String COMMENT = "comment";

    public ActivityTrackingDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MSG = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TYPE + " TEXT, "
                + TIME + " TEXT, "
                + DURATION + " INTEGER, "
                + COMMENT + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_MSG);
        Log.i(LOGGER_NAME, "Table " + TABLE_NAME + " is created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(LOGGER_NAME, "Table " + TABLE_NAME + " is upgraded, oldVersion= " + oldVer
                + " newVersion=" + newVer);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);
        //  Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i(LOGGER_NAME, "Table " + TABLE_NAME + " is downgraded, newVersion=" + newVersion + "oldVersion=" + oldVersion);

    }
}