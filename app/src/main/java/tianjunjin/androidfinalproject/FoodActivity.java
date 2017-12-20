package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;

public class FoodActivity extends Activity {

    ListView listView_food;
    Button addButton_food;
    ArrayList<Food_information> food_list;
    FoodAdapter foodAdapter;
    protected static final String ACTIVITY_NAME = "FoodActivity ";
    ContentValues cValues_food;
    Database_nutrition database_nutrition_object;
    SQLiteDatabase sqLiteDatabase_nutrition;
    Cursor cursor_food;
    View food_item_result;
    FrameLayout tabletFrame;
    Boolean isTablet;
    Boolean add_clicked;
    int position;
    Long DB_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        food_list = new ArrayList<>();

        listView_food = (ListView) findViewById(R.id.ListView_food);
        addButton_food = (Button)findViewById(R.id.addButton_food);

        foodAdapter = new FoodAdapter(this);
        listView_food.setAdapter(foodAdapter);

        database_nutrition_object = new Database_nutrition(this);
        sqLiteDatabase_nutrition = database_nutrition_object.getWritableDatabase();
        cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
        cursor_food.moveToFirst();

        while (!cursor_food.isAfterLast()) {
            Food_information food_information = new Food_information();
            food_information.food = cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_food_TYPE));
            food_information.time = cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_TIME ));
            food_information.calories = cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Calories));
            food_information.total_Fat = cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Total_Fat));
            food_information.Carbohydrate = cursor_food.getString(cursor_food.getColumnIndex(Database_nutrition.key_Carbohydrate));
            food_list.add(food_information);
            cursor_food.moveToNext();;
        }

        if (tabletFrame == null) {
            isTablet = false;
            Log.i(ACTIVITY_NAME, "the frameLayout does not exist, it is on the phone");
        } else {
            isTablet = true;
            Log.i(ACTIVITY_NAME, "the frameLayout exists, it is on the tablet");
        }

        addButton_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_clicked=true;
                Bundle empty_bundle=new Bundle();
                empty_bundle.putInt("forempty",1);

                    Intent intent = new Intent(FoodActivity.this, FoodAddActivity.class);
                    intent.putExtra("food_bundle" ,empty_bundle);
                    startActivityForResult(intent, 10);
            }
        });

        listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Food_information food_information = food_list.get(position);
                Long DB_ID = foodAdapter.getItemId(position);

                Bundle bundle = new Bundle();
                bundle.putString("type", food_information.food);
                bundle.putString("time", food_information.time);
                bundle.putString("calories", food_information.calories);
                bundle.putString("total_Fat", food_information.total_Fat);
                bundle.putString("carbohydrate", food_information.Carbohydrate);

                bundle.putLong("DB_ID", DB_ID);
                bundle.putInt("position", position);
                bundle.putInt("forempty",2);

                    Intent i = new Intent(FoodActivity.this, FoodAddActivity.class);
                    i.putExtra("food_bundle", bundle);
                    startActivityForResult(i, 2);
            }
        });
    }
    private class FoodAdapter extends ArrayAdapter<Food_information> {

        public FoodAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return food_list.size();
        }

        public Food_information getItem(int position) {
            return food_list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = FoodActivity.this.getLayoutInflater();
            food_item_result = null;
            food_item_result = inflater.inflate(R.layout.food_item, null);

            TextView message = (TextView) food_item_result.findViewById(R.id.food_item_text);
            Food_information fi = food_list.get(position);
            String detail = "Food: "+fi.food + "Time:" +fi.time + "calory:"+fi.calories + "Fat:"+ fi.total_Fat + "carbo:"+fi.Carbohydrate;
            message.setText(detail); // get the string at position
            return food_item_result;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle bundle_return = data.getBundleExtra("newbundle");
            String type,time,calories,total_fat,carbohydrate;
            type = bundle_return.getString("type");
            time = bundle_return.getString("time");
            calories = bundle_return.getString("calories");
            total_fat= bundle_return.getString("total_Fat");
            carbohydrate = bundle_return.getString("carbohydrate");
            save(new Food_information(type,time,calories,total_fat,carbohydrate));
        }
    }

    public void delete(Long id,int position) {
        sqLiteDatabase_nutrition .delete(Database_nutrition.DB_food_table , Database_nutrition.key_food_RowID + " = " + id, null);
        food_list.remove(position);
        foodAdapter.notifyDataSetChanged();
    }

    public void save(Food_information food_information) {
        ContentValues values = new ContentValues();
        values.put(Database_nutrition.key_food_TYPE, food_information.food);
        values.put(Database_nutrition.key_TIME, food_information.time);
        values.put(Database_nutrition.key_Calories, food_information.calories);
        values.put(Database_nutrition.key_Total_Fat, food_information.total_Fat);
        values.put(Database_nutrition.key_Carbohydrate, food_information.Carbohydrate);

    //    if(add_clicked==true) {
            sqLiteDatabase_nutrition.insert(Database_nutrition.DB_food_table, null, values);
            cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + database_nutrition_object.DB_food_table, null);
            food_list.add(food_information);
            cursor_food.moveToFirst();
            foodAdapter.notifyDataSetChanged();
            refreshActivity();
 //       }
//        else{
//            sqLiteDatabase_nutrition.update(Database_nutrition.DB_food_table, values, Database_nutrition.key_food_RowID +" = " + DB_ID, null);
//            cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + database_nutrition_object.DB_food_table,null);
//            food_list.set(position,food_information);
//            foodAdapter.notifyDataSetChanged();
//        }
    }
    public void refreshActivity(){
            finish();
            Intent intent = getIntent();
            startActivity(intent);
    }
}