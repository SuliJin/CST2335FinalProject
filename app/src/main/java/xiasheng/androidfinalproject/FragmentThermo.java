package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sulijin.androidfinalproject.R;

public class FragmentThermo extends Fragment {

    private House_DatabaseHelper dbHelper;
    private Button del;
    private View view;
    private TextView textViewId;
    private TextView textViewDay;
    private TextView textViewHour;
    private TextView textViewMinute;
    private TextView textViewTemp;
    long id;
    String day;
    String hour;
    String minute;
    String temp;

    public FragmentThermo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_thermo_edit, container, false);

        dbHelper = new House_DatabaseHelper(getActivity());
        dbHelper.openDatabase();
        Bundle bundle = this.getArguments();
         id = bundle.getLong("id");
         day = bundle.getString("day");
         hour = bundle.getString("hour");
        minute = bundle.getString("minute");
        temp = bundle.getString("temperature");

        textViewId=view.findViewById(R.id.fragmentId);
        textViewId.setText("ID="+id);
        textViewDay=view.findViewById(R.id.fragmentWeek);
        textViewDay.setText("Day="+day);
        textViewHour=view.findViewById(R.id.fragmentHour);
        textViewHour.setText("Hour="+hour);
        textViewMinute=view.findViewById(R.id.fragmentMinute);
        textViewMinute.setText("Minute="+minute);
        textViewTemp=view.findViewById(R.id.fragmentTemp);
        textViewTemp.setText("Temperature="+temp);

        del = view.findViewById(R.id.button_delete_fg_h);
        del.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("id",id);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
        return view;
    }

    }


