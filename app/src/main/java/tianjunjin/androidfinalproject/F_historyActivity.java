package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;

public class F_historyActivity extends Activity {

    ProgressBar progressBar;
    FrameLayout frameLayout;

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    private static final String ACTIVITY_NAME = "F_historyActivity";
    Database_nutrition f_db;
    private SQLiteDatabase f_sqldb;
    private ArrayList<Map> foodList = new ArrayList();
    private Cursor f_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_history);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        frameLayout = findViewById(R.id.f_listview_Frame);

        FoodQuery init = new  FoodQuery ();
        init.execute();
    }

    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager =getFragmentManager();
        //remove previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.f_listview_Frame, fragment).addToBackStack(null).commit();
    }
    class FoodQuery extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            SystemClock.sleep(100);
            progressBar.setProgress(10);
            f_db = new Database_nutrition(F_historyActivity.this);
            f_sqldb = f_db.getWritableDatabase();
            f_c = f_sqldb.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
            f_c.moveToFirst();

            while (!f_c.isAfterLast()) {
                Map<String, String> f_infor = new HashMap<>();
                f_infor.put("id", Database_nutrition.key_food_RowID);
                f_infor.put("type", Database_nutrition.key_food_TYPE);
                f_infor.put("time", Database_nutrition.key_TIME);
                f_infor.put("calories", Database_nutrition.key_Calories);
                f_infor.put("total_Fat", Database_nutrition.key_Total_Fat);
                f_infor.put("carbohydrate", Database_nutrition.key_Carbohydrate);
                foodList.add(f_infor);
                f_c.moveToNext();
            }
            SystemClock.sleep(500);
            progressBar.setProgress(100);
            return null;
        }
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            F_listView_fragment listViewFragment = new F_listView_fragment();
            listViewFragment.init(foodList);
            addFragment(listViewFragment);
        }
    }



}

