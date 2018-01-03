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
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityTrackingActivity extends AppCompatActivity {
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
        final Intent startIntent = new Intent(this, ActivityTrackingActivity.class);
        switch (item.getItemId()) {
            case R.id.t_help:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(getResources().getString(R.string.t_help_title));
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.fragment_activity_tracking_help, null);
                ((TextView)dialogView.findViewById(R.id.t_help)).setMovementMethod(new ScrollingMovementMethod());
                builder2.setView(dialogView);
                // Add the buttons
                builder2.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(startIntent);
                    }
                });
                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                // Create the AlertDialog
                AlertDialog dialog2 = builder2.create();

                dialog2.show();
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
           try {
               SystemClock.sleep(100);
               progressBar.setProgress(10);
               ActivityTrackingDatabaseHelper dbHelper = new ActivityTrackingDatabaseHelper(ActivityTrackingActivity.this);
               writeableDB = dbHelper.getWritableDatabase();
               SystemClock.sleep(200);
               progressBar.setProgress(30);
               //populate activity list
               cursor = writeableDB.rawQuery("select * from " + ActivityTrackingDatabaseHelper.TABLE_NAME, null);
               cursor.moveToFirst();
               while (!cursor.isAfterLast()) {
                   Map<String, Object> row = new HashMap<>();
                   row.put(ID, cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.ID)));
                   String type = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.TYPE));
                   type = getActivityString(ActivityTrackingUtil.getPosition(type));
                   String time = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.TIME));
                   try {
                       Date date = ActivityTrackingUtil.getDateFormatter().parse(time);
                       time =  ActivityTrackingUtil.getDateFormatter().format(date);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   String duration = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.DURATION));
                   String comment = cursor.getString(cursor.getColumnIndex(ActivityTrackingDatabaseHelper.COMMENT));
                   row.put(ActivityTrackingDatabaseHelper.TYPE, type);
                   row.put(ActivityTrackingDatabaseHelper.TIME, time);
                   row.put(ActivityTrackingDatabaseHelper.DURATION, duration);
                   row.put(ActivityTrackingDatabaseHelper.COMMENT, comment);
                   row.put(DESCRIPTION, getResources().getString(R.string.t_start_at) + " " + time + ", " + type +
                           " " + getResources().getString(R.string.t_for) + " " + duration +
                           " " + getResources().getString(R.string.t_min_note) + " " + comment);

                   activityList.add(row);
                   cursor.moveToNext();
               }
               SystemClock.sleep(500);
               progressBar.setProgress(80);
               final Intent intent = new Intent(ActivityTrackingActivity.this, ActivityTrackingAddActivity.class);
               findViewById(R.id.t_newActivity).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       finish();
                       startActivity(intent);
                   }
               });
               SystemClock.sleep(200);
               progressBar.setProgress(100);
               return null;
           } finally {
               if (cursor != null)
                   cursor.close();
           }
        }

        protected void onProgressUpdate(Integer ...values){
            super.onProgressUpdate(values);
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
    public String getActivityString(int position) {
        return getResources().getStringArray(R.array.t_activities)[position];
    }

}
