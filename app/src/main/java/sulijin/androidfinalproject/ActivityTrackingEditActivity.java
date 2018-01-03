package sulijin.androidfinalproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTrackingEditActivity extends AppCompatActivity {
    private SQLiteDatabase writeableDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_edit);

        final ActivityTrackingDatabaseHelper dbHelper = new ActivityTrackingDatabaseHelper(this);
        writeableDB = dbHelper.getWritableDatabase();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        final long rowId = bundle.getLong(ActivityTrackingActivity.ID);
        String type = bundle.getString(ActivityTrackingDatabaseHelper.TYPE);
        String time = bundle.getString(ActivityTrackingDatabaseHelper.TIME);
        String duration = bundle.getString(ActivityTrackingDatabaseHelper.DURATION);
        String comment = bundle.getString(ActivityTrackingDatabaseHelper.COMMENT);

        final Spinner spinner = (Spinner)findViewById(R.id.t_type_value);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.t_activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(ActivityTrackingUtil.getPosition(type));

        final EditText timeView = (EditText)findViewById(R.id.t_time_value);
        timeView.setText(time);
        final EditText durationView = (EditText)findViewById(R.id.t_duration_value);
        durationView.setText(duration);
        final EditText commentView = (EditText)findViewById(R.id.t_comment_value);
        commentView.setText(comment);


        final Intent startIntent = new Intent(this, ActivityTrackingActivity.class);
        Button update = findViewById(R.id.t_button_update_activity);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityTrackingEditActivity.this);
                builder1.setTitle(getResources().getString(R.string.t_wanna_update));
                // Add the buttons
                builder1.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //update value
                        String type = spinner.getSelectedItem().toString();
                        String time = timeView.getText().toString();
                        String duration = durationView.getText().toString();
                        String comment = commentView.getText().toString();

                        ContentValues newData = new ContentValues();
                        newData.put(ActivityTrackingDatabaseHelper.TYPE, type);
                        newData.put(ActivityTrackingDatabaseHelper.TIME, time);
                        newData.put(ActivityTrackingDatabaseHelper.DURATION, duration);
                        newData.put(ActivityTrackingDatabaseHelper.COMMENT, comment);
                        writeableDB.update(ActivityTrackingDatabaseHelper.TABLE_NAME, newData, ActivityTrackingDatabaseHelper.ID + "=" + rowId, null);

                        Toast toast = Toast.makeText(ActivityTrackingEditActivity.this,
                                                    getResources().getString(R.string.t_act_update_success), Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        startActivity(startIntent);
                    }
                });
                builder1.setNegativeButton(R.string.t_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog1 = builder1.create();

                dialog1.show();

            }
        });

        Button delete = findViewById(R.id.t_button_delete_activity);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityTrackingEditActivity.this);
                builder1.setTitle(getResources().getString(R.string.t_wanna_delete));
                // Add the buttons
                builder1.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete value
//                        this line delete all: writeableDB.delete(dbHelper.TABLE_NAME, null, null);
                        writeableDB.delete(dbHelper.TABLE_NAME, dbHelper.ID + "=" + rowId, null);
                        Toast toast = Toast.makeText(ActivityTrackingEditActivity.this,
                                                getResources().getString(R.string.t_act_delete_success), Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        startActivity(startIntent);
                    }
                });
                builder1.setNegativeButton(R.string.t_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog1 = builder1.create();

                dialog1.show();

            }
        });

        Button cancel = findViewById(R.id.t_buttone_cancel_edit_activity);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            startActivity(startIntent);
            }
        });
    }

}
