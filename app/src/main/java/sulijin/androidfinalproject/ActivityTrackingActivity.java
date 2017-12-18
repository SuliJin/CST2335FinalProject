package sulijin.androidfinalproject;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityTrackingActivity extends Activity {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    private static final String ACTIVITY_NAME = "ActivityTracking";
    private SQLiteDatabase writeableDB;
    private ArrayList<Map> activityList = new ArrayList();
    private Cursor cursor;
    private ProgressBar progressBar;
    private ObjectAnimator animation;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Toast toast = Toast.makeText(this, R.string.t_cancel, Toast.LENGTH_SHORT);
        switch (item.getItemId()) {
            case R.id.t_about:

                ActivityTrackingAboutFragment fragment = new ActivityTrackingAboutFragment();

                addFragment(fragment);
                return true;
            case R.id.t_help:
                ActivityTrackingHelpFragment helpFragment = new ActivityTrackingHelpFragment();

                addFragment(helpFragment);
                return true;
            case R.id.t_stats:
                ActivityTrackingStatisticsFragment statsFragment = new ActivityTrackingStatisticsFragment();
                statsFragment.init(this.activityList);
                addFragment(statsFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_tracking, menu);
        return true;
    }

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
            ActivityTrackingListViewFragment listViewFragment = new ActivityTrackingListViewFragment();
            listViewFragment.init(activityList);

            addFragment(listViewFragment);
        }
    }

    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager =getFragmentManager();
        //remove previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.t_listview_Frame, fragment).addToBackStack(null).commit();
    }


}
