package xiasheng.androidfinalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import sulijin.androidfinalproject.R;

public class Fragment_AddThermo extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_house_add, container, false);


        day = view.findViewById(R.id.spinner);
        hour = view.findViewById(R.id.numberPicker1);
        minutes = view.findViewById(R.id.numberPicker2);
        temp = view.findViewById(R.id.editText1);
        temp.setFilters(new InputFilter[]{new InputFilterMinMax("0", "50")});
        House_DatabaseHelper dbHelper = new House_DatabaseHelper(getActivity());
        writeableDB = dbHelper.getWritableDatabase();

        hour.setMinValue(00);
        hour.setMaxValue(23);
        minutes.setMinValue(00);
        minutes.setMaxValue(59);

        save = view.findViewById(R.id.savebutton);
        cancel = view.findViewById(R.id.cancelbutton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String houseTemp = temp.getText().toString();
                String dayOfWeek = day.getSelectedItem().toString();
                int hourInput = hour.getValue();
                int minInput = minutes.getValue();

                ContentValues input = new ContentValues();
                input.put(House_DatabaseHelper.DAY, dayOfWeek);
                input.put(House_DatabaseHelper.HOUR, hourInput);
                input.put(House_DatabaseHelper.MINUTE, minInput);
                input.put(House_DatabaseHelper.Temperature, houseTemp);
                writeableDB.insert(House_DatabaseHelper.TABLE_NAME, "", input);
                Toast.makeText(getActivity(), "You save the rule successfully", Toast.LENGTH_LONG).show();

                getActivity().finish();
                getActivity().getFragmentManager().beginTransaction().remove(Fragment_AddThermo.this).commit();
                Intent intent = getActivity().getIntent();
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Do you want to return without saving?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                        Intent intent = getActivity().getIntent();
                        startActivity(intent);
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
        return view;
    }
}
