package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
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

public class FragmentThermo extends Fragment {

    private House_DatabaseHelper dbHelper;
    private Button del;
    private Button save;
    private View view;
    private TextView textViewId;
    private TextView textViewDay;
    private EditText textViewHour;
    private EditText textViewMinute;
    private EditText textViewTemp;
    String day;
    String hour;
    String minute;
    String temp;
    private SQLiteDatabase writeableDB;

    public FragmentThermo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_thermo_edit, container, false);


       dbHelper = new House_DatabaseHelper(getActivity());
        writeableDB = dbHelper.getWritableDatabase();
//       dbHelper.openDatabase();
       final Bundle bundle = this.getArguments();
       final long id = bundle.getLong("id");final boolean isLandscape = bundle.getBoolean("isLandscape");
//         day = bundle.getString("day");
//         hour = bundle.getString("hour");
//        minute = bundle.getString("minute");
//        temp = bundle.getString("temperature");


        textViewId=view.findViewById(R.id.fragmentId);
        //textViewId.setText("ID="+id);
        textViewDay=view.findViewById(R.id.fragmentWeek);
       //textViewDay.setText("Day="+day);
        textViewHour=view.findViewById(R.id.fragmentHour);
       //textViewHour.setText("Hour="+hour);
        textViewMinute=view.findViewById(R.id.fragmentMinute);
      // textViewMinute.setText("Minute="+minute);
        textViewTemp=view.findViewById(R.id.fragmentTemp);
        //textViewTemp.setText("Temperature="+temp);
//


        del = view.findViewById(R.id.button_delete_fg_h);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View var1) {
                if (isLandscape) {
                    writeableDB.delete(House_DatabaseHelper.TABLE_NAME, House_DatabaseHelper.ID + "=" + id, null);
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
        save = view.findViewById(R.id.button_saveNewRule_fg_h);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var2) {

                if (isLandscape) {
                    temp = textViewTemp.getText().toString();
                    day = textViewDay.getText().toString();
                    hour= textViewHour.getText().toString();
                    minute = textViewMinute.getText().toString();

                    ContentValues input = new ContentValues();
                    input.put(House_DatabaseHelper.DAY, day);
                    input.put(House_DatabaseHelper.HOUR, hour);
                    input.put(House_DatabaseHelper.MINUTE,minute);
                    input.put(House_DatabaseHelper.Temperature, temp);
                    writeableDB.insert(House_DatabaseHelper.TABLE_NAME, House_DatabaseHelper.ID, input );
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


