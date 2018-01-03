package zhentingmai.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class AutomobileActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AutomobileActivity";

    TextView avgPrice;
    TextView totalCost;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.auto_welcome, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        liters=(EditText) findViewById(R.id.editText_litresValue);
        price =(EditText) findViewById(R.id.editText_priceValue);
        kilo = (EditText) findViewById(R.id.editText_kilosValue);

        save = (Button) findViewById(R.id.autoBt_save);

        //carAdapter=new SaveAdapter(this);

        aHelper=new AutoDatabaseHelper(this);
        aHelper.setWritable();

        avgPrice=(TextView) findViewById(R.id.autoAvgPriceValue);
        String avgPriceValue=aHelper.getAvg();
        avgPrice.setText(avgPriceValue);

        totalCost=(TextView) findViewById(R.id.autoTotalValue);
        String totalCostValue=aHelper.getTotal();
        totalCost.setText(totalCostValue);

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //ContentValues cValues=new ContentValues();
                //String strTime= new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                Calendar calendar = Calendar.getInstance();
                String strYear = calendar.get(Calendar.YEAR)+"";
                String strMonth = calendar.get(Calendar.MONTH)+1+"";
                String strDay = calendar.get(Calendar.DAY_OF_MONTH)+"";

                String strPrice=price.getText().toString();
                String strLiters=liters.getText().toString();
                String strKilo=kilo.getText().toString();

                aHelper.insert(strYear, strMonth, strDay, strPrice, strLiters, strKilo);
                Toast t = Toast.makeText(getApplicationContext(), R.string.auto_saveConfirm, Toast.LENGTH_LONG);
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
                builder.setTitle(R.string.auto_cancelValue);
                builder.setPositiveButton(R.string.auto_yes, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        refreshActivity();
                    }
                });
                builder.setNegativeButton(R.string.auto_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Snackbar.make(view, R.string.auto_noCancel, Snackbar.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //Snackbar.make(view, "You have cancel the entry", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){

            case R.id.help:
                Log.d("Toolbar","help is selected");
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle(R.string.auto_helpTitle);


                builder.setItems(new String[]{getResources().getString(R.string.auto_helpAuthor),
                        getResources().getString(R.string.auto_helpHist),getResources().getString(R.string.auto_helpSum),
                        getResources().getString(R.string.auto_helpSave),getResources().getString(R.string.auto_helpCancel),
                        getResources().getString(R.string.auto_helpUpdate),getResources().getString(R.string.auto_helpInquire)},null);
                //builder.setMessage(R.string.auto_helpValue);

                builder.setNegativeButton(R.string.auto_ok,null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return true;
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
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void refreshActivity(){
        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }
}
