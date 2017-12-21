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
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;

public class FoodActivity extends Activity {

    ListView listView_food;
    Button addButton_food;
    ArrayList<Map> food_list;
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
            Map<String,Object> food_information =new HashMap<>();
            food_information.put("id",cursor_food.getColumnIndex(Database_nutrition.key_food_RowID));
            food_information.put("food",cursor_food.getColumnIndex(Database_nutrition.key_food_TYPE));
            food_information.put("time",cursor_food.getColumnIndex(Database_nutrition.key_TIME ));
            food_information.put("calories",cursor_food.getColumnIndex(Database_nutrition.key_Calories));
            food_information.put("total_Fat",cursor_food.getColumnIndex(Database_nutrition.key_Total_Fat));
            food_information.put("Carbohydrate",cursor_food.getColumnIndex(Database_nutrition.key_Carbohydrate));
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
                //   Food_info rmation food_information = food_list.get(position);
                //   Long DB_ID = foodAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", foodAdapter.getItemId(position)+"");
                bundle.putInt("forempty",2);

                Intent i = new Intent(FoodActivity.this, FoodAddActivity.class);
                i.putExtra("food_bundle", bundle);
                startActivityForResult(i, 2);
            }
        });
    }
    private class FoodAdapter extends ArrayAdapter<Map<String, Object>> {

        public FoodAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return food_list.size();
        }

        public Map<String, Object> getItem(int position) {
            //   Map<String,Object> food_item = getItem(posision);
            return food_list.get(position);
        }
        public long getItemId(int position){
            Map<String,Object> food_item = getItem(position);
            return Long.parseLong(food_item.get("id").toString());
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = FoodActivity.this.getLayoutInflater();
            food_item_result = null;
            food_item_result = inflater.inflate(R.layout.food_item, null);

            TextView message = (TextView) food_item_result.findViewById(R.id.food_item_text);
            Map<String,Object> food_view= getItem(position);
            String detail = food_view.get("food").toString()+ food_view.get("time").toString()+ food_view.get("calories").toString()+food_view.get("total_Fat").toString()+food_view.get("Carbohydrate").toString();
            message.setText(detail);
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
            Map<String,Object>  food_map = new HashMap();
            food_map.put("type",type);
            food_map.put("time",time);
            food_map.put("calories",calories);
            food_map.put("total_Fat",total_fat);
            food_map.put("carbohydrate",carbohydrate);
            save(food_map);
        }
    }
    public void delete(Long id,int position) {
        sqLiteDatabase_nutrition .delete(Database_nutrition.DB_food_table , Database_nutrition.key_food_RowID + " = " + id, null);
        food_list.remove(position);
        foodAdapter.notifyDataSetChanged();
    }

    public void save(Map<String,Object> save_food) {
        ContentValues values = new ContentValues();
        values.put(Database_nutrition.key_food_TYPE, save_food.get("food").toString());
        values.put(Database_nutrition.key_TIME, save_food.get("time").toString());
        values.put(Database_nutrition.key_Calories, save_food.get("calories").toString());
        values.put(Database_nutrition.key_Total_Fat, save_food.get("total_Fat").toString());
        values.put(Database_nutrition.key_Carbohydrate, save_food.get("Carbohydrate").toString());

        //    if(add_clicked==true) {
        sqLiteDatabase_nutrition.insert(Database_nutrition.DB_food_table, null, values);
        cursor_food = sqLiteDatabase_nutrition.rawQuery("select * from " + database_nutrition_object.DB_food_table, null);
        food_list.add(save_food);
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