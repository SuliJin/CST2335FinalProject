package xiasheng.androidfinalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;


public class trackingHouseActivity extends Activity {
    private static final String ACTIVITY_NAME = "TrackingHouseActivity";
    private SQLiteDatabase tempDB;
    private ArrayList<Map> userList = new ArrayList();
    private Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        House_DatabaseHelper dbHelper = new House_DatabaseHelper(this);
        tempDB = dbHelper.getWritableDatabase();
        ListView listview = findViewById(R.id.thermView);
        listview.setAdapter(new ChatAdapter(this));

        //populate activity list
        cursor = tempDB.rawQuery("select * from " + House_DatabaseHelper.TABLE_NAME,null );
        //cursor=dbHelper.read();
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", cursor.getString(cursor.getColumnIndex(House_DatabaseHelper.ID)));
            String day = cursor.getString(cursor.getColumnIndex(House_DatabaseHelper.DAY));
            String hour = cursor.getString(cursor.getColumnIndex(House_DatabaseHelper.HOUR));
            String minutes = cursor.getString(cursor.getColumnIndex(House_DatabaseHelper.MINUTE));
            String temperature = cursor.getString(cursor.getColumnIndex(House_DatabaseHelper.Temperature));
            row.put("Day of  Week", day);
            row.put("description", "you choose " + hour +": "  + minutes + " Temperature is " + temperature);
            userList.add(row);
            cursor.moveToNext();
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent edit=new Intent(trackingHouseActivity.this,AddHouseActivity.class);
           startActivity(edit);
            }
        });

        final Intent intent = new Intent(this, AddHouseActivity.class);
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(intent);
            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<Map<String, Object>> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {

            return userList.size();
        }

        public Map<String, Object> getItem(int position) {

            return userList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View result = inflater.inflate(R.layout.activity_track_temperature, parent, false);
            if (!userList.isEmpty()) {
                Map<String, Object> content = getItem(position);
                TextView message1 = (TextView) result.findViewById(R.id.dayrecord);
                TextView message2 = (TextView) result.findViewById(R.id.hourRecord);
                TextView message3 = (TextView) result.findViewById(R.id.minRecord);
                TextView message4 = (TextView) result.findViewById(R.id.tempRecord);
                message1.setText(content.get("description").toString());
                message2.setText(content.get("description").toString());
                message3.setText(content.get("description").toString());
                message4.setText(content.get("description").toString());
          }

            return result;
        }
      /*  public long getItemId(int position){
            Map<String, Object> content = getItem(position);
            return Long.parseLong(content.get("id").toString());
        }*/
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
            Log.i(ACTIVITY_NAME, "In onDestroy()");
        }

        public void refreshActivity(){
            finish();
            Intent intent = getIntent();
            startActivity(intent);
        }
    }


