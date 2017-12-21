package tianjunjin.androidfinalproject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;

/**
 * Created by Admin on 2017-12-14.
 */

public class Food_fragment extends Fragment {

    View view;
    Button cancelButton_food, saveButton_food, deleteButton_food;
    EditText Edit_type, Edit_Calories, Edit_Total_Fat, Edit_Total_Carbohydrate;
    EditText Edit_Time;
    Boolean tablet_mode;
    SQLiteDatabase sqLiteDatabase_nutrition;

    public Food_fragment() {
        tablet_mode = false;
    }

    @SuppressLint("ValidFragment")
    public Food_fragment(FoodActivity foodActivity) {
        tablet_mode = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_food_fragment, container, false);
        sqLiteDatabase_nutrition = new Database_nutrition(getActivity()).getWritableDatabase();

        final Bundle bundle_back = this.getArguments();

//        String single_type = bundle_back.getString("type");
        Edit_type = view.findViewById(R.id.f_type_value);

        //     String single_time = bundle_back.getString("time");
        Edit_Time = view.findViewById(R.id.f_time_value);

        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd hh:mm");
        final String time = format.format(new Date());
        Edit_Time.setText(time);

        //     String single_Calories = bundle_back.getString("Calories ");
        Edit_Calories = view.findViewById(R.id.f_Calories_value);

        //     String single_Total_Fat = bundle_back.getString("Total_Fat");
        Edit_Total_Fat = view.findViewById(R.id.f_Total_Fat_value);

        //     String single_Total_Carbohydrate = bundle_back.getString("Total_Carbohydrate");
        Edit_Total_Carbohydrate = view.findViewById(R.id.f_Carbohydrate_value);

        final Long single_ID = bundle_back.getLong("DB_ID");
//        id_TextView = view.findViewById(R.id.id_TextView);
//        id_TextView.setText(String.valueOf(single_ID));

        final int single_position = bundle_back.getInt("position");

        if (bundle_back.getLong("forempty") == 1) {
            deleteButton_food.setEnabled(false);
        } else {
            Edit_type.setText(bundle_back.getString("type"));
            Edit_Time.setText(bundle_back.getString("time"));
            Edit_Calories.setText(bundle_back.getString("calories"));
            Edit_Total_Fat.setText(bundle_back.getString("total_Fat"));
            Edit_Total_Carbohydrate.setText(bundle_back.getString("carbohydrate"));
        }


//        try {
//            int num = Integer.parseInt(type);
//            Log.i("", num + " is a number");
//        } catch (NumberFormatException e) {
//            Log.i("", type + " is not a number");
//        }


        Button save = view.findViewById(R.id.f_save_button_food);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = Edit_type.getText().toString();
                String calories = Edit_Calories.getText().toString();
                String total_Fat = Edit_Total_Fat.getText().toString();
                String carbohydrate = Edit_Total_Carbohydrate.getText().toString();
//                if (getActivity().getLocalClassName().equals("Food_fragment")) {
                if (tablet_mode == false) {
                    Bundle newbundle = new Bundle();

                    newbundle.putString("type", type);
                    newbundle.putString("time", time);
                    newbundle.putString("calories", calories);
                    newbundle.putString("total_Fat", total_Fat);
                    newbundle.putString("carbohydrate", carbohydrate);

                    Intent intent = new Intent();
                    intent.putExtra("newbundle", newbundle);
                    getActivity().setResult(2, intent);
                    getActivity().finish();
                } else {
                    Map<String,Object> food_map = new HashMap();
                    food_map.put("type",type);
                    food_map.put("time",time);
                    food_map.put("calories",calories);
                    food_map.put("total_Fat",total_Fat);
                    food_map.put("carbohydrate",carbohydrate);
                    ((FoodActivity) getActivity()).save(food_map);
                }
            }
        });

        deleteButton_food =view.findViewById(R.id.f_delete_button_food);
        deleteButton_food.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.sure_to_delete);
                // Add the buttons
                builder.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (getActivity().getLocalClassName().equals("MessageDetails")) {
                            Intent intent = new Intent();
                            intent.putExtra("bundle_back", bundle_back);
                            getActivity().setResult(2, intent);
                            getActivity().finish();
                        } else {
                            sqLiteDatabase_nutrition .delete(Database_nutrition.DB_food_table , Database_nutrition.key_food_RowID + " = " + id, null);
                            Intent foodAct = new Intent(getActivity(), FoodActivity.class);
                            getActivity().finish();
                            getActivity().startActivity(foodAct);
                        }
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return view;
    }
//    public boolean checkType(){
//        if(Edit_type.getText()== null)
//            return true;
//    }
//    public boolean checkCalories(){
//
//
//    }
//    public boolean checkTotal_Fat(){
//
//    }
//    public boolean checkCarbohydrate(){
//
//    }
}