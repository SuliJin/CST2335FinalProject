package tianjunjin.androidfinalproject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;
import zhentingmai.androidfinalproject.AutoInfo;

/**
 * Created by Admin on 2017-12-24.
 */

public class F_detail_fragement extends Fragment {
    View view;
    Button cancel, update, delete;
    EditText Edit_type, Edit_Time, Edit_Calories, Edit_Total_Fat, Edit_Total_Carbohydrate;
    SQLiteDatabase sql;
    Database_nutrition f_db;

    public F_detail_fragement() {
    }

    @SuppressLint("ValidFragment")
    public F_detail_fragement(FoodActivity foodActivity) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.f_detail_fragment, container, false);
        final Bundle bundle_back = this.getArguments();
        Edit_type = view.findViewById(R.id.type_value);
        Edit_Time = view.findViewById(R.id.time_vlaue);
        Edit_Calories = view.findViewById(R.id.calories_value);
        Edit_Total_Fat = view.findViewById(R.id.total_fat_value);
        Edit_Total_Carbohydrate = view.findViewById(R.id.f_Carbohydrate_value);
        delete = view.findViewById(R.id.f_Delete);
        update = view.findViewById(R.id.f_Update);
        cancel = view.findViewById(R.id.f_Cancel);

        final Long single_ID = bundle_back.getLong("DB_ID");
        final int single_position = bundle_back.getInt("position");

        Edit_type.setText(bundle_back.getString("type"));
        Edit_Time.setText(bundle_back.getString("time"));
        Edit_Calories.setText(bundle_back.getString("calories"));
        Edit_Total_Fat.setText(bundle_back.getString("total_Fat"));
        Edit_Total_Carbohydrate.setText(bundle_back.getString("carbohydrate"));

        sql = new Database_nutrition(getActivity()).getWritableDatabase();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = Edit_Time.getText().toString();
                String type = Edit_type.getText().toString();
                String calories = Edit_Calories.getText().toString();
                String total_Fat = Edit_Total_Fat.getText().toString();
                String carbohydrate = Edit_Total_Carbohydrate.getText().toString();

//                    Map<String,String> food_map = new HashMap();
//                    food_map.put("type",type);
//                    food_map.put("time",time);
//                    food_map.put("calories",calories);
//                    food_map.put("total_Fat",total_Fat);
//                    food_map.put("carbohydrate",carbohydrate);
//                    ((FoodActivity) getActivity()).save(food_map);

                f_db.insert(type, time, calories, total_Fat, carbohydrate);

                //              }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.sure_to_delete);
                builder.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String time = Edit_Time.getText().toString();
                        String type = Edit_type.getText().toString();
                        String calories = Edit_Calories.getText().toString();
                        String total_Fat = Edit_Total_Fat.getText().toString();
                        String carbohydrate = Edit_Total_Carbohydrate.getText().toString();

                        f_db.insert(type, time, calories, total_Fat, carbohydrate);

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.sure_to_cancel);
                // Add the buttons
                builder.setPositiveButton(R.string.f_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton(R.string.f_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return view;
    }



}
