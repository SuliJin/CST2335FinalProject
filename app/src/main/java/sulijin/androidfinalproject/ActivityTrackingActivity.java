package sulijin.androidfinalproject;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityTrackingActivity extends FragmentActivity {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    private static final String ACTIVITY_NAME = "ActivityTracking";
    private SQLiteDatabase writeableDB;
    private ArrayList<Map> activityList = new ArrayList();
    private Cursor cursor;
    private ProgressBar progressBar;
    private ObjectAnimator animation;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        progressBar = (ProgressBar) findViewById(R.id.t_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        InitActivityTracking init = new InitActivityTracking();
        init.execute();
    }

    class InitActivityTracking extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
//            SystemClock.sleep(100);
            progressBar.setProgress(10);
            ActivityTrackingDatabaseHelper dbHelper = new ActivityTrackingDatabaseHelper(ActivityTrackingActivity.this);
            writeableDB = dbHelper.getWritableDatabase();
//            SystemClock.sleep(200);
            progressBar.setProgress(30);
            //populate activity list
            cursor = writeableDB.rawQuery("select * from " + ActivityTrackingDatabaseHelper.TABLE_NAME,null );
            cursor.moveToFirst();
            while(!cursor.isAfterLast() ) {
                Map<String, Object> row = new HashMap<>();
                row.put(ID, cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.ID)));
                String type = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.TYPE));
                String time = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.TIME));
                String duration = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.DURATION));
                String comment = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.COMMENT));
                row.put(ActivityTrackingDatabaseHelper.TYPE, type);
                row.put(ActivityTrackingDatabaseHelper.TIME, time);
                row.put(ActivityTrackingDatabaseHelper.DURATION, duration);
                row.put(ActivityTrackingDatabaseHelper.COMMENT, comment);
                row.put(DESCRIPTION, "start at " + time +", " + type + " for " + duration + " minutes. Note: " + comment);

                activityList.add(row);
                cursor.moveToNext();
            }
//            SystemClock.sleep(500);
            progressBar.setProgress(80);
            final Intent intent = new Intent(ActivityTrackingActivity.this, ActivityTrackingAddActivity.class);
            findViewById(R.id.newActivity).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(intent);
                }
            });
//            SystemClock.sleep(200);
            progressBar.setProgress(100);
            return null;
        }

        protected void onProgressUpdate(Integer ...values){
            super.onProgressUpdate(values);
//            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE );
            ListView listView = findViewById(R.id.t_activitListView);
            chatAdapter = new ChatAdapter(ActivityTrackingActivity.this);
            listView.setAdapter(chatAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Map message = chatAdapter.getItem(position);
                    long idInDb =  chatAdapter.getItemId(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong(ID,idInDb);
                    bundle.putString(ActivityTrackingDatabaseHelper.TYPE, message.get(ActivityTrackingDatabaseHelper.TYPE).toString());
                    bundle.putString(ActivityTrackingDatabaseHelper.TIME, message.get(ActivityTrackingDatabaseHelper.TIME).toString());
                    bundle.putString(ActivityTrackingDatabaseHelper.DURATION, message.get(ActivityTrackingDatabaseHelper.DURATION).toString());
                    bundle.putString(ActivityTrackingDatabaseHelper.COMMENT, message.get(ActivityTrackingDatabaseHelper.COMMENT).toString());

                    Intent intent = new Intent(ActivityTrackingActivity.this, ActivityTrackingEditActivity.class);
                    intent.putExtra("bundle", bundle);
                    finish();
                    startActivity(intent);
                }
            });
        }
    }
    private void init() {

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
                case "Running":
                case "撒鸭子":  return R.drawable.running;
                case "Walking":
                case "走道": return R.drawable.hiking;
                case "Biking":
                case "骑车子": return R.drawable.biking;
                case "Swimming":
                case "游泳": return R.drawable.swimming;
                case "Skating":
                case "滑出溜": return R.drawable.skating;
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
