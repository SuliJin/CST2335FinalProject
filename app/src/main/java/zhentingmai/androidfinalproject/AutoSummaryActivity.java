package zhentingmai.androidfinalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import sulijin.androidfinalproject.R;

import static zhentingmai.androidfinalproject.AutoDatabaseHelper.KEY_LITERS;
import static zhentingmai.androidfinalproject.AutoDatabaseHelper.KEY_MONTH;
import static zhentingmai.androidfinalproject.AutoDatabaseHelper.KEY_PRICE;
import static zhentingmai.androidfinalproject.AutoDatabaseHelper.KEY_YEAR;
import static zhentingmai.androidfinalproject.AutoDatabaseHelper.TABLE_NAME;


public class AutoSummaryActivity extends Activity {
    protected static final String ACTIVITY_NAME = "AutoSummaryActivity";
    TextView year;
    TextView jan,janSum,feb,febSum,mar,marSum,apr,aprSum,may,maySum,jun,junSum,jul,julSum,aug,augSum,
            sep,sepSum,oct,octSum,nov,novSum,dec,decSum;
    EditText yearSelect;
    Button yearSubmit;

    ArrayList<String> list=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    //SQLiteDatabase db;
    //Cursor cursor;

    Calendar calendar = Calendar.getInstance();
    int thisYear = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_summary);

        year=(TextView) findViewById(R.id.autoSum_year);
        jan=(TextView) findViewById(R.id.autoSum_Jan);
        janSum=(TextView) findViewById(R.id.autoSum_JanValue);
        feb=(TextView) findViewById(R.id.autoSum_Feb);
        febSum=(TextView) findViewById(R.id.autoSum_FebValue);
        mar=(TextView) findViewById(R.id.autoSum_Mar);
        marSum=(TextView) findViewById(R.id.autoSum_MarValue);
        apr=(TextView) findViewById(R.id.autoSum_Apr);
        aprSum=(TextView) findViewById(R.id.autoSum_AprValue);
        may=(TextView) findViewById(R.id.autoSum_May);
        maySum=(TextView) findViewById(R.id.autoSum_MayValue);
        jun=(TextView) findViewById(R.id.autoSum_Jun);
        junSum=(TextView) findViewById(R.id.autoSum_JunValue);
        jul=(TextView) findViewById(R.id.autoSum_Jul);
        julSum=(TextView) findViewById(R.id.autoSum_JulValue);
        aug=(TextView) findViewById(R.id.autoSum_Aug);
        augSum=(TextView) findViewById(R.id.autoSum_AugValue);
        sep=(TextView) findViewById(R.id.autoSum_Sep);
        sepSum=(TextView) findViewById(R.id.autoSum_SepValue);
        oct=(TextView) findViewById(R.id.autoSum_Oct);
        octSum=(TextView) findViewById(R.id.autoSum_OctValue);
        nov=(TextView) findViewById(R.id.autoSum_Nov);
        novSum=(TextView) findViewById(R.id.autoSum_NovValue);
        dec=(TextView) findViewById(R.id.autoSum_Dec);
        decSum=(TextView) findViewById(R.id.autoSum_DecValue);

        yearSubmit = (Button) findViewById(R.id.autoSum_submit);
        yearSelect = (EditText) findViewById(R.id.autoSum_yearSelect);

        aHelper = new AutoDatabaseHelper(this);
        aHelper.setWritable();

        /*SumQuery query=new SumQuery();
        query.execute();*/
        display(thisYear);

        yearSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisYear = Integer.parseInt(yearSelect.getText().toString());
                display(thisYear);
                /*SumQuery query2=new SumQuery();
                 query2.execute();*/
                yearSelect.setText("");
            }
        });
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

    public void refreshActivity(){
        finish();
        Intent intentRef = getIntent();
        startActivity(intentRef);
    }


    public void display(int thisYear){

        year.setText(thisYear+"");

        janSum.setText(aHelper.getSum(thisYear).get(0));
        febSum.setText(aHelper.getSum(thisYear).get(1));
        marSum.setText(aHelper.getSum(thisYear).get(2));
        aprSum.setText(aHelper.getSum(thisYear).get(3));
        maySum.setText(aHelper.getSum(thisYear).get(4));
        junSum.setText(aHelper.getSum(thisYear).get(5));
        julSum.setText(aHelper.getSum(thisYear).get(6));
        augSum.setText(aHelper.getSum(thisYear).get(7));
        sepSum.setText(aHelper.getSum(thisYear).get(8));
        octSum.setText(aHelper.getSum(thisYear).get(9));
        novSum.setText(aHelper.getSum(thisYear).get(10));
        decSum.setText(aHelper.getSum(thisYear).get(11));
    }

   /* public class SumQuery extends AsyncTask<Void,Integer,Void> {
        @Override
        protected Void doInBackground(Void...params) {
            try {
                 //list=null;
                for(int i=1; i<=12; i++) {
                    String monthSum =aHelper.getMonthSum(thisYear,i);
                    list.add(monthSum);}

                year.setText(thisYear+"");

                janSum.setText(list.get(0));
                febSum.setText(list.get(1));
                marSum.setText(list.get(2));
                aprSum.setText(list.get(3));
                maySum.setText(list.get(4));
                junSum.setText(list.get(5));
                julSum.setText(list.get(6));
                augSum.setText(list.get(7));
                sepSum.setText(list.get(8));
                octSum.setText(list.get(9));
                novSum.setText(list.get(10));
                decSum.setText(list.get(11));

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer ...value){
            Log.i(ACTIVITY_NAME, "in onProgressUpdate");
        }

        @Override
        protected void onPostExecute(Void param){
            Log.i(ACTIVITY_NAME, "In onPostExecute");
        }
    }
*/

}
