package sulijin.androidfinalproject;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityTrackingActivity extends Activity {
    private static final String ACTIVITY_NAME = "ActivityTracking";
    private SQLiteDatabase writeableDB;
    private ArrayList<Map> activityList = new ArrayList();
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        T_DatabaseHelper dbHelper = new T_DatabaseHelper(this);
        writeableDB = dbHelper.getWritableDatabase();

        //populate activity list
        cursor = writeableDB.rawQuery("select * from " + T_DatabaseHelper.TABLE_NAME,null );
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", cursor.getString(cursor.getColumnIndex(T_DatabaseHelper.ID)));
            String type = cursor.getString(cursor.getColumnIndex(T_DatabaseHelper.TYPE));
            String time = cursor.getString(cursor.getColumnIndex(T_DatabaseHelper.TIME));
            String duration = cursor.getString(cursor.getColumnIndex(T_DatabaseHelper.DURATION));
            String comment = cursor.getString(cursor.getColumnIndex(T_DatabaseHelper.COMMENT));
            row.put("type", type);
            row.put("description", "start at " + time +", " + type + " for " + duration + " minutes. Note: " + comment);

            activityList.add(row);
            cursor.moveToNext();
        }

        ListView listview = findViewById(R.id.t_activitListView);
        listview.setAdapter(new ChatAdapter(this));

        final Intent intent = new Intent(this, T_AddActivity.class);
        findViewById(R.id.newActivity).setOnClickListener(new View.OnClickListener() {
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

        public int getCount(){

            return activityList.size();
        }
        public Map<String, Object> getItem(int position){

            return activityList.get(position);
        }

        private int getImageId(String type) {
            switch (type) {
                case "Running": return R.drawable.running;
            }
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ActivityTrackingActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.t_tracking_row, null);
            if (!activityList.isEmpty()) {
                Map<String, Object> content = getItem(position);

                ImageView img = result.findViewById(R.id.t_activity_row_icon);
                img.setImageResource(getImageId(content.get("type").toString()));
                TextView message = (TextView) result.findViewById(R.id.t_activity_row_description);
                message.setText(content.get("description").toString()); // get the string at position
            }
            return result;
        }

        public long getItemId(int position){
            Map<String, Object> content = getItem(position);
            return Long.parseLong(content.get("id").toString());
        }
    }
}
