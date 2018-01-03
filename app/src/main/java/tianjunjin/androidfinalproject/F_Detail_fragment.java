//package tianjunjin.androidfinalproject;
//
//import android.annotation.SuppressLint;
//import android.app.Fragment;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import sulijin.androidfinalproject.ActivityTrackingActivity;
//import sulijin.androidfinalproject.ActivityTrackingDatabaseHelper;
//import sulijin.androidfinalproject.ActivityTrackingEditActivity;
//import sulijin.androidfinalproject.R;
//
///**
// * Created by Admin on 2017-12-25.
// */
//
//public class F_Detail_fragment  extends Fragment {
//    View view;
//    Button cancel, update, delete;
//    EditText Edit_type, Edit_Time,Edit_Calories, Edit_Total_Fat, Edit_Total_Carbohydrate;
//    SQLiteDatabase writeableDB;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.f_detail_fragment, container, false);
//        final Bundle bundle_back = this.getArguments();
//
//        Edit_type = view.findViewById(R.id.type_value);
//        Edit_Time = view.findViewById(R.id.time_value);
//        delete = view.findViewById(R.id.f_Delete);
//        update=view.findViewById(R.id.f_Update);
//        cancel= view.findViewById(R.id.f_Cancel);
//        Edit_Calories = view.findViewById(R.id.calories_value);
//        Edit_Total_Fat = view.findViewById(R.id.total_fat_value);
//        Edit_Total_Carbohydrate = view.findViewById(R.id.carbohydrate_value);
//
//        final Long single_ID = bundle_back.getLong("DB_ID");
//        final int single_position = bundle_back.getInt("position");
//
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        final long rowId = bundle.getLong(F_historyActivity.ID);
//        String type = bundle.getString(Database_nutrition.key_food_TYPE);
//        String time = bundle.getString(Database_nutrition.key_TIME);
//        String calories = bundle.getString(Database_nutrition.key_Calories);
//        String total_Fat = bundle.getString(Database_nutrition.key_Total_Fat);
//        String carbohydrate = bundle.getString(Database_nutrition.key_Carbohydrate);
//
//
//            Edit_type.setText(type);
//            Edit_Time.setText(time);
//            Edit_Calories.setText(calories);
//            Edit_Total_Fat.setText(total_Fat);
//            Edit_Total_Carbohydrate.setText(carbohydrate);
//
//        writeableDB = new Database_nutrition(getActivity()).getWritableDatabase();
//        final Intent startIntent = new Intent(getActivity(),F_historyActivity.this);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getActivity());
//                builder1.setTitle(getResources().getString(R.string.t_wanna_update));
//                // Add the buttons
//                builder1.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //update value
//                        String type = Edit_type.getText().toString();
//                        String time =  Edit_Time .getText().toString();
//                        String calories  =  Edit_Calories .getText().toString();
//                        String total_Fat = Edit_Total_Fat.getText().toString();
//                        String carbohydrate = Edit_Total_Carbohydrate .getText().toString();
//
//
//                        ContentValues newData = new ContentValues();
//                        newData.put(Database_nutrition.key_food_TYPE , type);
//                        newData.put(Database_nutrition.key_TIME , time);
//                        newData.put(Database_nutrition.key_Calories, calories);
//                        newData.put(Database_nutrition.key_Total_Fat, total_Fat);
//                        newData.put(Database_nutrition.key_Carbohydrate,  carbohydrate);
//
//                        writeableDB.update(Database_nutrition.DB_food_table, newData, Database_nutrition.key_food_RowID + "=" + rowId, null);
//
//                        finish();
//                        startActivity(startIntent);
//                    }
//                });
//                builder1.setNegativeButton(R.string.t_cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//                // Create the AlertDialog
//                android.app.AlertDialog dialog1 = builder1.create();
//
//                dialog1.show();
//
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v){
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(R.string.sure_to_delete);
//                builder.setPositiveButton(R.string.t_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                            writeableDB.delete(Database_nutrition.DB_food_table , Database_nutrition.key_food_RowID + " = " +single_ID, null);
//                            Intent foodAct = new Intent(getActivity(), FoodActivity.class);
//                            getActivity().finish();
//                            getActivity().startActivity(foodAct);
//
//                    }
//                });
//                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(R.string.sure_to_cancel);
//                // Add the buttons
//                builder.setPositiveButton(R.string.f_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        getActivity().finish();
//                    }
//                });
//                builder.setNegativeButton(R.string.f_cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            }
//        });
//        return view;
//    }
//}
