package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import sulijin.androidfinalproject.R;

public class AddHouseActivity extends Activity {

    private Button save;
    private Button cancel;
    private NumberPicker hour;
    private NumberPicker minutes;
    private Spinner day;
    EditText temp;
    private final double default_temp = 20.0;
    protected static final String ACTIVITY_NAME = "HouseActivity";

    SQLiteDatabase writeableDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_add);
        //this.deleteDatabase("HouseTemperature1");
        day = (Spinner) findViewById(R.id.spinner);
        hour = (NumberPicker) findViewById(R.id.numberPicker1);
        minutes = (NumberPicker) findViewById(R.id.numberPicker2);
        temp = (EditText) findViewById(R.id.editText1);
        House_DatabaseHelper dbHelper = new House_DatabaseHelper(this);
        writeableDB = dbHelper.getWritableDatabase();
        //dbHelper.openDatabase();

        hour.setMinValue(0);
        hour.setMaxValue(23);
        minutes.setMinValue(0);
        minutes.setMaxValue(59);


        save = (Button) findViewById(R.id.savebutton);
        cancel = (Button) findViewById(R.id.cancelbutton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       day.setAdapter(adapter);

        final Intent startIntent = new Intent(this, trackingHouseActivity.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseTemp = temp.getText().toString();
                String dayOfWeek = ((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString();
                int hourInput = hour.getValue();
                int minInput = minutes.getValue();

                ContentValues input = new ContentValues();
                input.put(House_DatabaseHelper.DAY, dayOfWeek);
                input.put(House_DatabaseHelper.HOUR, hourInput);
                input.put(House_DatabaseHelper.MINUTE,minInput);
                input.put(House_DatabaseHelper.Temperature, houseTemp);
                writeableDB.insert(House_DatabaseHelper.TABLE_NAME,"", input);
                Toast.makeText(getApplicationContext(),"You save the rule successfully",Toast.LENGTH_LONG).show();

                finish();
                startActivity(startIntent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                setResult(Activity.RESULT_CANCELED);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddHouseActivity.this);
                builder.setTitle("Do you want to return without saving?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(startIntent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Snackbar.make(view, "Please continue to build rule", Snackbar.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            });
    }
}

