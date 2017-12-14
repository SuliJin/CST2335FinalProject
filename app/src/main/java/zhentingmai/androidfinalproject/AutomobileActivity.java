package zhentingmai.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

        save = (Button) findViewById(R.id.autoBt_save);

        //carAdapter=new SaveAdapter(this);

        aHelper=new AutoDatabaseHelper(this);
        aHelper.openDatabase();


        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //ContentValues cValues=new ContentValues();
                String strTime= new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                String strPrice=price.getText().toString();
                String strLiters=liters.getText().toString();
                String strKilo=kilo.getText().toString();

              aHelper.insert(strTime, strPrice, strLiters, strKilo);
              Toast t = Toast.makeText(getApplicationContext(), "Information saved successfully", Toast.LENGTH_LONG);
              t.show();
              refreshActivity();
            }
        });


        Button historyButton = findViewById(R.id.button_history);
        final Intent historyIntent = new Intent(this, AutoHistoryActivity.class);
        historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(historyIntent);
            }
        });


        final Button summaryButton = findViewById(R.id.button_summary);
        final Intent summaryIntent = new Intent(this, AutoSummaryActivity.class);
        summaryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(summaryIntent);
            }
        });

        final Button cancelButton = findViewById(R.id.autoBt_delete);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

            AlertDialog.Builder builder=new AlertDialog.Builder(AutomobileActivity.this);
                   builder.setTitle("Do you want to clear the entry");
                   builder.setPositiveButton("yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    refreshActivity();
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Snackbar.make(view, "Please continue to entry", Snackbar.LENGTH_LONG).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

                //Snackbar.make(view, "You have cancel the entry", Snackbar.LENGTH_LONG).show();

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
