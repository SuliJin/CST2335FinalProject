package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import sulijin.androidfinalproject.R;

import static xiasheng.androidfinalproject.House_DatabaseHelper.DAY;
import static xiasheng.androidfinalproject.House_DatabaseHelper.HOUR;
import static xiasheng.androidfinalproject.House_DatabaseHelper.ID;
import static xiasheng.androidfinalproject.House_DatabaseHelper.MINUTE;
import static xiasheng.androidfinalproject.House_DatabaseHelper.TABLE_NAME;
import static xiasheng.androidfinalproject.House_DatabaseHelper.Temperature;

public class FragmentThermo extends Fragment {

    private House_DatabaseHelper dbHelper;
    private SQLiteDatabase tempDB;
    private Button del;
    private Button saveNew;
    private Button save;
    private View view;
    private Cursor cursor;
    private TextView textViewId;
    private EditText textDay;
    private EditText textHour;
    private EditText textMinute;
    private EditText textTemp;
    String day;
    String hour;
    String minute;
    String temp;
    Bundle bundle;
    String id;
    boolean isLandscape;

    public FragmentThermo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_thermo_edit, container, false);

        bundle = this.getArguments();
       id = bundle.getString("id");
       isLandscape = bundle.getBoolean("isLandscape");


        dbHelper = new House_DatabaseHelper(getActivity());
        tempDB = dbHelper.getWritableDatabase();
        cursor = tempDB.rawQuery("select * from " + TABLE_NAME +" WHERE "+ID+" = " +id,null);
        cursor.moveToFirst();

        textViewId=view.findViewById(R.id.fragmentId);
        textViewId.setText("ID="+id);

        day=cursor.getString(cursor.getColumnIndex(DAY));
        hour=cursor.getString(cursor.getColumnIndex(HOUR));
        minute=cursor.getString(cursor.getColumnIndex(MINUTE));
        temp=cursor.getString(cursor.getColumnIndex(Temperature));

        textDay=view.findViewById(R.id.fragmentWeek);
        textDay.setText(day,TextView.BufferType.EDITABLE);
        textHour=view.findViewById(R.id.fragmentHour);
       textHour.setText(hour,TextView.BufferType.EDITABLE);
        textMinute=view.findViewById(R.id.fragmentMinute);
        textMinute.setText(minute,TextView.BufferType.EDITABLE);
        textTemp=view.findViewById(R.id.fragmentTemp);
        textTemp.setText(temp,TextView.BufferType.EDITABLE);
        cursor.close();

        del = view.findViewById(R.id.button_delete_fg_h);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View var1) {
                if (isLandscape) {
                    tempDB.delete(House_DatabaseHelper.TABLE_NAME, ID + "=" + id, null);
                    getActivity().finish();
                    getActivity().getFragmentManager().beginTransaction().remove(FragmentThermo.this).commit();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                }
                else {
                    Intent ret = new Intent();
                    ret.putExtra("id", id);
                    getActivity().setResult(Activity.RESULT_OK, ret);
                    getActivity().finish();                }
            }
        });

        save=view.findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLandscape) {
                    temp = textTemp.getText().toString();
                    day = textDay.getText().toString();
                    hour= textHour.getText().toString();
                    minute = textMinute.getText().toString();

                    ContentValues input = new ContentValues();
                    input.put(House_DatabaseHelper.DAY, day);
                    input.put(HOUR, hour);
                    input.put(MINUTE,minute);
                    input.put(Temperature, temp);
                    tempDB.insert(House_DatabaseHelper.TABLE_NAME, ID, input );
                    getActivity().finish();
                    getActivity().getFragmentManager().beginTransaction().remove(FragmentThermo.this).commit();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                }
                else {
                    Intent ret = new Intent();
                    ret.putExtra("id", id);
                    getActivity().setResult(Activity.RESULT_OK, ret);
                    getActivity().finish();
                }
            }

        });


        saveNew = view.findViewById(R.id.button_saveNewRule_fg_h);
        saveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var2) {

                if (isLandscape) {
                    temp = textTemp.getText().toString();
                    day = textDay.getText().toString();
                    hour= textHour.getText().toString();
                    minute = textMinute.getText().toString();

                    ContentValues input = new ContentValues();
                    input.put(House_DatabaseHelper.DAY, day);
                    input.put(HOUR, hour);
                    input.put(MINUTE,minute);
                    input.put(Temperature, temp);
                    tempDB.insert(House_DatabaseHelper.TABLE_NAME, ID, input );
                    getActivity().finish();
                    getActivity().getFragmentManager().beginTransaction().remove(FragmentThermo.this).commit();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                }
                else {
                    Intent ret = new Intent();
                    ret.putExtra("id", id);
                    getActivity().setResult(Activity.RESULT_OK, ret);
                    getActivity().finish();
                }
            }

        });
        return view;
    }

    }
