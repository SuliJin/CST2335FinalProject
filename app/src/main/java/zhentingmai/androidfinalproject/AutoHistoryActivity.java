package zhentingmai.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;



public class AutoHistoryActivity extends Activity {
    protected static final String ACTIVITY_NAME = "AutoHistoryActivity";
    TextView time;
    TextView liters;
    TextView price;
    TextView kilo;
    ListView listView;

    ArrayList<carInfo> list=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    Cursor cursor;
    CarAdapter carAdapter;

    class carInfo{
        private String id;
        private String time;
        private String price;
        private String liters;
        private String kilo;

        public carInfo(String id, String time, String price, String liters, String kilo) {
            this.id = id;
            this.time = time;
            this.price = price;
            this.liters = liters;
            this.kilo = kilo;
        }

        public String getId() {
            return id;
        }

        public String getTime() {
            return time;
        }

        public String getPrice() {
            return price;
        }

        public String getLiters() {
            return liters;
        }

        public String getKilo() {
            return kilo;
        }
    }

    private class CarAdapter extends ArrayAdapter<carInfo> {
        public CarAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return list.size();
        }

        public carInfo getItem(int position) {
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.auto_record, parent, false);
            liters=(TextView) view.findViewById(R.id.textView_litersRecord);
            price =(TextView) view.findViewById(R.id.textView_priceRecord);
            kilo = (TextView) view.findViewById(R.id.textView_kiloRecord);
            time = (TextView) view.findViewById(R.id.textView_timeRecord);
            liters.setText(getItem(position).getLiters());
            price.setText(getItem(position).getPrice());
            kilo.setText(getItem(position).getKilo());
            time.setText(getItem(position).getTime());

            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_history);

        listView=(ListView)findViewById(R.id.listView_history);

        carAdapter =new CarAdapter(this);
        listView.setAdapter(carAdapter);

       list.add(new carInfo("1","20","200","10","2000"));
        list.add(new carInfo("2","30","150","15","1500"));
       list.add(new carInfo("3","12","122","8","666"));
        list.add(new carInfo("4","25","18","18","2222"));

        aHelper=new AutoDatabaseHelper(this);
        aHelper.openDatabase();

        cursor=aHelper.read();
        cursor.moveToFirst();

        int colIndexId=cursor.getColumnIndex(AutoDatabaseHelper.KEY_ID);
        int colIndexPrice=cursor.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
        int colIndexLiters=cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
        int colIndexKilo=cursor.getColumnIndex(AutoDatabaseHelper.KEY_KILO);

        while(!cursor.isAfterLast()){
            list.add(new carInfo("4","25","18","18","2222"));
            list.add(new carInfo(cursor.getString(colIndexId),"25",cursor.getString(colIndexPrice),cursor.getString(colIndexLiters),cursor.getString(colIndexKilo)));
            cursor.moveToNext();
        }

//        cursor =db.rawQuery("select "+AutoDatabaseHelper.KEY_LITERS+" from " + AutoDatabaseHelper.TABLE_NAME,null);

//        int colIndexLiter= cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
        //int colIndexPrice=c.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
        //int colIndexKilo=c.getColumnIndex(AutoDatabaseHelper.KEY_KILO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        aHelper.closeDatabase();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

//    public void refreshActivity(){
//        finish();
//        Intent intent = getIntent();
//        startActivity(intent);
//    }
}

