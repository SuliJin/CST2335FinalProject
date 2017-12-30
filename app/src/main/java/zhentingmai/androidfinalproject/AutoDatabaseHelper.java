package zhentingmai.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.Calendar;

import sulijin.androidfinalproject.R;

/**
 * Created by gdyjm on 2017-12-02.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase";

    public static final int VERSION_NUM =1;
    public static final String TABLE_NAME = "AUTO";
    public static final String KEY_ID = "_ID";

    public static final String KEY_YEAR = "YEAR";
    public static final String KEY_MONTH = "MONTH";
    public static final String KEY_DAY = "DAY";

    public static final String KEY_PRICE = "PRICE";
    public static final String KEY_LITERS = "LITERS";
    public static final String KEY_KILO = "KILO";

    public SQLiteDatabase db;

    private Context context;

    public static final String ACTIVITY_NAME = "AutoDatabaseHelper";

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        this.context = ctx;
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_YEAR+ " TEXT, " + KEY_MONTH + " TEXT, " + KEY_DAY + " TEXT, "
                + KEY_PRICE + " NUMERIC, "+ KEY_LITERS+ " NUMERIC, " + KEY_KILO + " NUMERIC)";

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

    public void setWritable() {
        db = getWritableDatabase();
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void insert(String year, String month, String day, String price, String liters, String kilo) {
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, year);
        values.put(KEY_MONTH, month);
        values.put(KEY_DAY, day);
        values.put(KEY_PRICE, price);
        values.put(KEY_LITERS, liters);
        values.put(KEY_KILO, kilo);

        db.insert(TABLE_NAME, null, values);
    }

    public void delete(Long id) {
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id);
    }


    public void update(Long id, String year, String month, String day, String price, String liters, String kilo) {
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, year);
        values.put(KEY_MONTH, month);
        values.put(KEY_DAY, day);
        values.put(KEY_PRICE, price);
        values.put(KEY_LITERS, liters);
        values.put(KEY_KILO, kilo);

        db.update(TABLE_NAME, values, KEY_ID + " = " + id, null);
    }


    Cursor c;
    Calendar calendar = Calendar.getInstance();
    int iYear = calendar.get(Calendar.YEAR);
    int iMonth = calendar.get(Calendar.MONTH);

    public String getAvg(){

       try {
           if (iMonth == 1)
               c = db.rawQuery("SELECT AVG(" + KEY_PRICE + ") FROM " + TABLE_NAME + " WHERE " +
                       KEY_YEAR + " = " + (iYear -1) + " AND " + KEY_MONTH + " = " + 12, null);
           else
               c = db.rawQuery("SELECT AVG(" + KEY_PRICE + ") FROM " + TABLE_NAME + " WHERE " +
                       KEY_YEAR + " = " + iYear + " AND " + KEY_MONTH + " = " + (iMonth -1), null);

           c.moveToFirst();
           String avg = c.getString(0);
           if (avg == null)
               return context.getString(R.string.auto_noRecord);
           else
               return String.format("%.2f", avg);
       }finally {
           if(c != null)
               c.close();
       }
    }

    public String getTotal(){

        try{if(iMonth==1)
            c=db.rawQuery("SELECT SUM(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
                    KEY_YEAR + " = " + (iYear-1) + " AND " + KEY_MONTH + " = " + 12,null);
        else
            c=db.rawQuery("SELECT SUM(" + KEY_PRICE +") FROM "+TABLE_NAME + " WHERE " +
                    KEY_YEAR + " = " + iYear + " AND " + KEY_MONTH + " = " + (iMonth-1),null);
        c.moveToFirst();
        String strCost = c.getString(0);
        String strAvgPrice = getAvg();
        if(strCost==null||strAvgPrice==null){
            return context.getString( R.string.auto_noRecord);
        }
        else{
            double dCost = Double.parseDouble(strCost);
            double dAvgPrice = Double.parseDouble(getAvg());
            String total = String.format("%.2f",dCost*dAvgPrice);
            return total;
        }}finally {
            if(c != null)
                c.close();
        }
    }


    public ArrayList<String> getSum(int thisYear) {
        ArrayList<String> list = new ArrayList<>();
        String strLiterSum =null;
        String strPriceAvg =null;
           for (int i = 1; i <= 12; i++) {
               try {
                   c = db.rawQuery("SELECT SUM (" + KEY_LITERS + ")FROM " + TABLE_NAME + " WHERE " +
                       KEY_YEAR + " = " + thisYear + " AND " + KEY_MONTH + " = " + i, null);
               c.moveToFirst();
               strLiterSum = c.getString(0);
               } finally {
                   if(c != null)
                       c.close();
               }
              try { c = db.rawQuery("SELECT AVG (" + KEY_PRICE + ")FROM " + TABLE_NAME + " WHERE " +
                       KEY_YEAR + " = " + thisYear + " AND " + KEY_MONTH + " = " + i, null);
               c.moveToFirst();
               strPriceAvg = c.getString(0);
              } finally {
                  if(c != null)
                      c.close();
              }
               if (strLiterSum == null || strPriceAvg == null) {
                   list.add(context.getString(R.string.auto_noRecord));
               } else {
                   double sumLiter = Double.parseDouble(strLiterSum);
                   double avgPrice = Double.parseDouble(strPriceAvg);
                   list.add(String.format("$" + "%.2f", avgPrice * sumLiter));
               }
           }


        return list;
    }

    public String getMonthSum(int thisYear, int i){
            String strLiterSum =null;
            String strPriceAvg =null;
            try {
                c = db.rawQuery("SELECT SUM (" + KEY_LITERS + ")FROM " + TABLE_NAME + " WHERE " +
                    KEY_YEAR + " = " + thisYear + " AND " + KEY_MONTH + " = " + i, null);
                c.moveToFirst();
                strLiterSum = c.getString(0);
            } finally {
                if(c != null)
                    c.close();
            }

            try {
                c = db.rawQuery("SELECT AVG (" + KEY_PRICE + ")FROM " + TABLE_NAME + " WHERE " +
                    KEY_YEAR + " = " + thisYear + " AND " + KEY_MONTH + " = " + i, null);
                 c.moveToFirst();
                 strPriceAvg = c.getString(0);
            } finally {
                if(c != null)
                    c.close();
            }
            if (strLiterSum == null || strPriceAvg == null) {
                return context.getString(R.string.auto_noRecord);
            } else {
                double sumLiter = Double.parseDouble(strLiterSum);
                double avgPrice = Double.parseDouble(strPriceAvg);
                return String.format("$" + "%.2f", avgPrice * sumLiter);
            }

    }

   /* public Cursor getSumCursor(){
        c=database.rawQuery("SELECT " + KEY_YEAR+"||','||"+KEY_MONTH+", SUM("+KEY_LITERS+") FROM" +
                " "+TABLE_NAME + " GROUP BY "+KEY_YEAR+","+KEY_MONTH,null);
        return c;
    }*/


    public Cursor getCursor() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}