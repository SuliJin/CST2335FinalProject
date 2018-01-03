package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sulijin.androidfinalproject.R;

public class F_NewEntryActivity extends Activity {
    EditText Edittype,Edittime,Editcarlories,Edittotal_Fat,Editcarbohydrate;
    Button save,cancel;
    Database_nutrition f_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_new_entry);

        Edittype=(EditText) findViewById(R.id.type_value);
        Edittime= (EditText) findViewById(R.id.time_vlaue);
        Editcarlories=(EditText) findViewById(R.id.calories_value);
        Edittotal_Fat=(EditText) findViewById(R.id.total_fat_value);
        Editcarbohydrate=(EditText) findViewById(R.id.carbohydrate_value);
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd hh:mm");
        Edittime.setText(format.format(new Date()));

        save = (Button) findViewById(R.id.f_Save);
        cancel= (Button) findViewById(R.id.f_Cancel);

        f_db =new Database_nutrition(this);
        f_db.setWritable();

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String type= Edittype.getText().toString();
                String time =Edittime.getText().toString();
                String carlories=Editcarlories.getText().toString();
                String total_Fat=Edittotal_Fat.getText().toString();
                String carbohydrate = Editcarbohydrate.getText().toString();

                f_db.insert(type, time, carlories,total_Fat, carbohydrate);
                Toast t = Toast.makeText(getApplicationContext(), R.string.f_saveConfirm, Toast.LENGTH_LONG);
                t.show();
                refreshActivity();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(F_NewEntryActivity.this);
                builder.setTitle(R.string.sure_to_cancel);
                builder.setPositiveButton(R.string.f_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        refreshActivity();
                    }
                });
                builder.setNegativeButton(R.string.auto_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public void refreshActivity(){
        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }
}



