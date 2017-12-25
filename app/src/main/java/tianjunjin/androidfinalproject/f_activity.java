package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;

public class f_activity extends Activity {
    private ProgressBar progressBar;
    FrameLayout frameLayout;
    Database_nutrition f_db;
    SQLiteDatabase f_sqldb;
    Cursor f_c;
    protected static final String ACTIVITY_NAME = " f_activity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_activity);

        progressBar = (ProgressBar) findViewById(R.id.f_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        frameLayout = findViewById(R.id.f_FrameLayout);

     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.f_welcome, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        FoodQuery foodQuery=new FoodQuery();
//        foodQuery.execute();

        Button f_historyButton = (Button) findViewById(R.id.f_button_history);
        f_historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addFragment( new F_listView_fragment());
            }}
        );

        final Button f_newEntryButton = (Button) findViewById(R.id.f_button_new);
        final Intent f_newEntry = new Intent(this, F_NewEntryActivity.class);
        f_newEntryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(f_newEntry);
            }
        });
    }
//    class FoodQuery extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            SystemClock.sleep(100);
//            progressBar.setProgress(10);
//            f_db = new Database_nutrition(f_activity.this);
//            f_sqldb = f_db.getWritableDatabase();
//            f_c = f_sqldb.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
//            f_c.moveToFirst();
//
//            while (!f_c.isAfterLast()) {
//                Map<String, String> f_infor = new HashMap<>();
//                f_infor.put("id", Database_nutrition.key_food_RowID);
//                f_infor.put("type", Database_nutrition.key_food_TYPE);
//                f_infor.put("time", Database_nutrition.key_TIME);
//                f_infor.put("calories", Database_nutrition.key_Calories);
//                f_infor.put("total_Fat", Database_nutrition.key_Total_Fat);
//                f_infor.put("carbohydrate", Database_nutrition.key_Carbohydrate);
//                //          food_list.add(food_information);
//                f_c.moveToNext();
//            }
//            progressBar.setProgress(100);
//
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//    }

    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager =getFragmentManager();
        //remove previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.listView_history, fragment).addToBackStack(null).commit();
    }
    }
