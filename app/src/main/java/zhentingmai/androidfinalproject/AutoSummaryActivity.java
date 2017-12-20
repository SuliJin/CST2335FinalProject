package zhentingmai.androidfinalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;



public class AutoSummaryActivity extends Activity {
    protected static final String ACTIVITY_NAME = "AutoSummaryActivity";
    TextView time;
    TextView liters;
    TextView price;
    TextView kilo;
    ListView listView;

    ArrayList<AutoInfo> list=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    Cursor cursor;
    CarAdapter carAdapter;
    private ProgressBar progressBar;





    private class CarAdapter extends ArrayAdapter<AutoInfo> {
        public CarAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return list.size();
        }

        public AutoInfo getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(aHelper.KEY_ID));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.auto_record, parent, false);
            //View view = inflater.inflate(R.layout.auto_summary, parent, false);
            liters=(TextView) view.findViewById(R.id.textView_litersRecord);
            price =(TextView) view.findViewById(R.id.textView_priceRecord);
            kilo = (TextView) view.findViewById(R.id.textView_kiloRecord);
            time = (TextView) view.findViewById(R.id.textView_timeRecord);
            liters.setText("Liters: "+getItem(position).getLiters());
            price.setText("Price($/L): "+getItem(position).getPrice());
            kilo.setText("Kilometers: "+getItem(position).getKilo());
            time.setText(getItem(position).getYear()+"-"+getItem(position).getMonth()+ " - "+getItem(position).getDay());

            return view;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_summary);

        listView=(ListView)findViewById(R.id.auto_summary);

        carAdapter =new CarAdapter(this);
        listView.setAdapter(carAdapter);

        aHelper = new AutoDatabaseHelper(this);
        aHelper.setWritable();


        DatabaseQuery query=new DatabaseQuery();
        query.execute();


    }


    public class DatabaseQuery extends AsyncTask<AutoInfo,Integer,String> {


        protected void onProgressUpdate(Integer ...value){
            Log.i(ACTIVITY_NAME, "in onProgressUpdate");
        }

        @Override
        protected String doInBackground(AutoInfo... carInfos) {
            Log.i(ACTIVITY_NAME, "In DOINBACKGROUND");
            try {

                cursor = aHelper.getCursor();

                cursor.moveToFirst();

                int colIndexId = cursor.getColumnIndex(AutoDatabaseHelper.KEY_ID);
                int colIndexPrice = cursor.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
                int colIndexLiters = cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
                int colIndexKilo = cursor.getColumnIndex(AutoDatabaseHelper.KEY_KILO);
                int colIndexYear = cursor.getColumnIndex(AutoDatabaseHelper.KEY_YEAR);
                int colIndexMonth = cursor.getColumnIndex(AutoDatabaseHelper.KEY_MONTH);
                int colIndexDay = cursor.getColumnIndex(AutoDatabaseHelper.KEY_DAY);

                while (!cursor.isAfterLast()) {
                    list.add(new AutoInfo(cursor.getString(colIndexId), cursor.getString(colIndexYear),
                            cursor.getString(colIndexMonth), cursor.getString(colIndexDay),cursor.getString(colIndexPrice),
                            cursor.getString(colIndexLiters), cursor.getString(colIndexKilo)));
                    cursor.moveToNext();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Log.i(ACTIVITY_NAME, "In onPostExecute");
            carAdapter.notifyDataSetChanged();
        }
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
        //aHelper.closeDatabase();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void refreshActivity(){
        finish();
        Intent intentRef = getIntent();
        startActivity(intentRef);
    }
}
