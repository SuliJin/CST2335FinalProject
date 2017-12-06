package zhentingmai.androidfinalproject;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;

public class AutomobileActivity extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobileActivity";
    EditText liters;
    EditText price;
    EditText kilo;
    Button save;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> listLiters=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    //SQLiteDatabase db;
    Cursor c;
    //SaveAdapter saveAdapter;
    int requestCode=1;
    int messageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);


        liters=(EditText) findViewById(R.id.editText_litresValue);
        price =(EditText) findViewById(R.id.editText_priceValue);
        kilo = (EditText) findViewById(R.id.editText_kilosValue);

        save = (Button) findViewById(R.id.button_save);

        //carAdapter=new SaveAdapter(this);


        aHelper=new AutoDatabaseHelper(this);
        aHelper.openDatabase();


        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //ContentValues cValues=new ContentValues();
                String strPrice=price.getText().toString();
                String strLiters=liters.getText().toString();
                String strKilo=kilo.getText().toString();

              aHelper.insert("19", strPrice, strLiters, strKilo);
                refreshActivity();
            }
        });

        //c=db.rawQuery("select * from " + AutoDatabaseHelper.TABLE_NAME,null);
        //final Intent intent = new Intent(this, AutoHistoryActivity.class);
       // int colIndexLiter=c.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
        //int colIndexPrice=c.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
        //int colIndexKilo=c.getColumnIndex(AutoDatabaseHelper.KEY_KILO);



        Button historyButton = findViewById(R.id.button_history);
        final Intent historyIntent = new Intent(this, AutoHistoryActivity.class);
        historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(historyIntent);
            }
        });

        Button summaryButton = findViewById(R.id.button_summary);
        final Intent summaryIntent = new Intent(this, AutoSummaryActivity.class);
        summaryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(summaryIntent);
            }
        });
    }
   /* private class SaveAdapter extends ArrayAdapter<String>{
        public SaveAdapter(Context ctx){super(ctx,0);}
        public int getCount(){return list.size();}
        public String getItem(int position){return list.get(position);}

        public long getItemId(int position){
            c.moveToPosition(position);
            return c.getLong(c.getColumnIndex(aHelper.KEY_ID));
        }

        public View getView(int position, View convertView, ViewGroup parent){
            AutoHistoryActivity history=new AutoHistoryActivity();
            LayoutInflater inflater=history.getLayoutInflater();
            View result = null;
            result=inflater.inflate(R.layout.auto_record, null);
            TextView liters=(TextView)result.findViewById(R.id.textView_litersRecord);
            liters.setText(getItem(position));


            return result;
        }


    }*/
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
