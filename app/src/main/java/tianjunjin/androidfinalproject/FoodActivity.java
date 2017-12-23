package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.ActivityTrackingActivity;
import sulijin.androidfinalproject.ActivityTrackingAddActivity;
import sulijin.androidfinalproject.ActivityTrackingDatabaseHelper;
import sulijin.androidfinalproject.ActivityTrackingListViewFragment;
import sulijin.androidfinalproject.R;

public class FoodActivity extends Activity {

    ListView listView_food;
    Button addButton_food;
    ArrayList<Map> food_list;
    FoodAdapter foodAdapter;
    protected static final String ACTIVITY_NAME = "FoodActivity ";
    Database_nutrition database_nutrition_object;
    SQLiteDatabase sqLiteDatabase_nutrition;
    Cursor cursor_food;
    View food_item_result;
    FrameLayout tabletFrame;
    Boolean isTablet;
    Boolean add_clicked;
    int position;
    Long DB_ID;
    private ProgressBar progressBar;

       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_food);
           progressBar = (ProgressBar)findViewById(R.id.progressBar_f);
           progressBar.setVisibility(View.VISIBLE);
           food_list = new ArrayList<>();
           listView_food = (ListView) findViewById(R.id.ListView_food);
           addButton_food = (Button) findViewById(R.id.addButton_food);
           foodAdapter = new FoodAdapter(this);
           tabletFrame = findViewById(R.id.Food_tablet_frameLayout);

           FoodQuery foodQuery=new FoodQuery();
           foodQuery.execute();
//         listView_food.setAdapter(foodAdapter);
//           database_nutrition_object = new Database_nutrition(this);
//           sqLiteDatabase_nutrition = database_nutrition_object.getWritableDatabase();
////            SystemClock.sleep(200);
////            progressBar.setProgress(30);
//           cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
//           cursor_food.moveToFirst();
//
//           while (!cursor_food.isAfterLast()) {
//                Map<String, String> food_information = new HashMap<>();
//                food_information.put("id", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_food_RowID)));
//                food_information.put("type", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_food_TYPE)));
//                food_information.put("time", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_TIME)));
//                food_information.put("calories", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Calories)));
//                food_information.put("total_Fat", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Total_Fat)));
//                food_information.put("carbohydrate", cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Carbohydrate)));
//                food_list.add(food_information);
//                cursor_food.moveToNext();
//            }
           if (tabletFrame == null) {
            isTablet = false;
            Log.i(ACTIVITY_NAME, "the frameLayout does not exist, it is on the phone");
           } else {
            isTablet = true;
            Log.i(ACTIVITY_NAME, "the frameLayout exists, it is on the tablet");
           }
           listView_food.setAdapter(foodAdapter);

           addButton_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_clicked = true;
                Bundle empty_bundle = new Bundle();
                empty_bundle.putInt("forempty", 1);
                if(isTablet == false){
               Intent intent = new Intent(FoodActivity.this, FoodAddActivity.class);
                intent.putExtra("food_bundle", empty_bundle);
                startActivityForResult(intent, 10);
              }else{
                    Food_fragment  tablet_fragment= new Food_fragment();
                    tablet_fragment.setArguments(empty_bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.Food_tablet_frameLayout, tablet_fragment);
                    ft.commit();}
            }
           });
          listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Map<String, String> list_food = food_list.get(position);
                //  Long DB_ID = foodAdapter.getItem(position);
                String type = list_food.get("type");
                String time = list_food.get("time");
                String calories = list_food.get("calories");
                String total_Fat = list_food.get("total_Fat");
                String carbohydrate = list_food.get("carbohydrate");
                Bundle bundle = new Bundle();
                bundle.putLong("DB_ID",id);
                bundle.putString("type", type);
                bundle.putString("time", time);
                bundle.putString("calories", calories);
                bundle.putString("total_Fat", total_Fat);
                bundle.putString("carbohydrate", carbohydrate);
                //          bundle.putString("id", foodAdapter.getItemId(position)+"");
                bundle.putInt("forempty", 2);
                if(isTablet == false){
                Intent i = new Intent(FoodActivity.this, FoodAddActivity.class);
                i.putExtra("food_bundle", bundle);
                startActivityForResult(i, 2);}
                else{
                    Food_fragment  tablet_fragment= new Food_fragment();
                    tablet_fragment.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.Food_tablet_frameLayout, tablet_fragment);
                    ft.commit();}
            }
        });

       }
    class FoodQuery extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            SystemClock.sleep(100);
            progressBar.setProgress(33);
            database_nutrition_object = new Database_nutrition(FoodActivity.this);
            sqLiteDatabase_nutrition = database_nutrition_object.getWritableDatabase();
            SystemClock.sleep(500);
            progressBar.setProgress(66);
            cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
            cursor_food.moveToFirst();

            while (!cursor_food.isAfterLast()) {
                Map<String, String> food_information = new HashMap<>();
                food_information.put("id", Database_nutrition.key_food_RowID);
                food_information.put("type", Database_nutrition.key_food_TYPE);
                food_information.put("time", Database_nutrition.key_TIME);
                food_information.put("calories", Database_nutrition.key_Calories);
                food_information.put("total_Fat", Database_nutrition.key_Total_Fat);
                food_information.put("carbohydrate", Database_nutrition.key_Carbohydrate);
                food_list.add(food_information);
                cursor_food.moveToNext();
            }
            SystemClock.sleep(1000);
            progressBar.setProgress(100);
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
          private class FoodAdapter extends ArrayAdapter<Map<String, String>> {

          public FoodAdapter(Context ctx) {
            super(ctx, 0);
        }

          public int getCount() {
            return food_list.size();
        }

          public Map<String, String> getItem(int position) {
            //   Map<String,Object> food_item = getItem(posision);
            return food_list.get(position);
          }
          public long getItemId(int position) {
            cursor_food.moveToPosition(position);
            return Long.parseLong(cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_food_RowID)));
          }
          public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = FoodActivity.this.getLayoutInflater();
           food_item_result = null;
            food_item_result = inflater.inflate(R.layout.food_item, null);


            TextView message =  food_item_result.findViewById(R.id.food_item_text);
            Map<String, String> food_view = getItem(position);

            String detail = "type: " + food_view.get("type") + " time: " + food_view.get("time") + " calories: " + food_view.get("calories") + " total_Fat: " + food_view.get("total_Fat") + " carbohydrate: " + food_view.get("carbohydrate");
            message.setText(detail);
            return food_item_result;
          }
          }
          protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (resultCode == 2) {
            Bundle bundle_return = data.getBundleExtra("newbundle");
            String type, time, calories, total_fat, carbohydrate;
            type = bundle_return.getString("type");
            time = bundle_return.getString("time");
            calories = bundle_return.getString("calories");
            total_fat = bundle_return.getString("total_Fat");
            carbohydrate = bundle_return.getString("carbohydrate");
            Map<String, String> food_map = new HashMap();
            food_map.put("type", type);
            food_map.put("time", time);
            food_map.put("calories", calories);
            food_map.put("total_Fat", total_fat);
            food_map.put("carbohydrate", carbohydrate);
            save(food_map);
          }
          if (resultCode == 20) {
            Bundle bundle_return = data.getBundleExtra("bundle_back");
            final Long single_ID = bundle_return.getLong("DB_ID");
            final int single_position = bundle_return.getInt("position");
            delete(single_ID, single_position);
          }
          }
         public void delete(Long id, int position) {
          sqLiteDatabase_nutrition.delete(Database_nutrition.DB_food_table, Database_nutrition.key_food_RowID + " = " + id, null);
          food_list.remove(position);
          foodAdapter.notifyDataSetChanged();
         }
         public void save(Map<String, String> save_food) {
          ContentValues values = new ContentValues();
          values.put(Database_nutrition.key_food_TYPE, save_food.get("type"));
          values.put(Database_nutrition.key_TIME, save_food.get("time"));
          values.put(Database_nutrition.key_Calories, save_food.get("calories"));
          values.put(Database_nutrition.key_Total_Fat, save_food.get("total_Fat"));
          values.put(Database_nutrition.key_Carbohydrate, save_food.get("carbohydrate"));
          System.out.println("i am a food " + save_food.get("type"));

          if(add_clicked==true) {
          sqLiteDatabase_nutrition.insert(Database_nutrition.DB_food_table, null, values);
          cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + database_nutrition_object.DB_food_table, null);
          food_list.add(save_food);
          cursor_food.moveToFirst();
          foodAdapter.notifyDataSetChanged();
        //refreshActivity();
          }
          else{
          sqLiteDatabase_nutrition.update(Database_nutrition.DB_food_table, values, Database_nutrition.key_food_RowID +" = " + DB_ID, null);
          cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + database_nutrition_object.DB_food_table,null);
          food_list.set(position,save_food);
          foodAdapter.notifyDataSetChanged();
          }
          }
          public void refreshActivity() {
          finish();
          Intent intent = getIntent();
          startActivity(intent);
          }
          }