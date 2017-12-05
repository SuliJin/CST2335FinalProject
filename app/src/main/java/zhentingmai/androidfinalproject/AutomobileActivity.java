package zhentingmai.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
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
    ArrayList<String> list=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    SQLiteDatabase db;
    Cursor c;
    SaveAdapter saveAdapter;
    int requestCode=1;
    int messageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);

        liters=(EditText) findViewById(R.id.editText_litresValue);
        price =(EditText) findViewById(R.id.editText_priceValue);
        kilo = (EditText) findViewById(R.id.editText_kilosValue);

        saveAdapter=new SaveAdapter(this);



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
    private class SaveAdapter extends ArrayAdapter<String>{
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


    }
}
