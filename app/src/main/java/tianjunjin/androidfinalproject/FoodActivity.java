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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;

import static tianjunjin.androidfinalproject.Database_nutrition.DB_food_Name;
import static tianjunjin.androidfinalproject.Database_nutrition.key_food_message;
import static tianjunjin.androidfinalproject.Database_nutrition.DB_food_Name;
import static tianjunjin.androidfinalproject.Database_nutrition.key_food_message;

public class FoodActivity extends Activity {

    ListView listView_food;
    EditText editText_food;
    Button addButton_food;
    Button deleteButton_food;
    ArrayList<String> food_list;
    FoodAdapter foodAdapter;
    protected static final String ACTIVITY_NAME = "FoodActivity ";
    ContentValues cValues_food;
    Database_nutrition database_nutrition_object ;
    SQLiteDatabase sqLiteDatabase_nutrition;
    Cursor cursor_food;
    View food_item_result;
    long food_item_ID;
    private int requestCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        food_list = new ArrayList<>();
        listView_food = (ListView)findViewById(R.id.ListView_food);
        addButton_food = (Button) findViewById(R.id.addButton_food);
        deleteButton_food = (Button) findViewById(R.id.deleteButton_food);
        editText_food = findViewById(R.id.EditText_food);
        foodAdapter=new FoodAdapter( this );
        listView_food.setAdapter (foodAdapter);
        database_nutrition_object= new Database_nutrition(this);
        sqLiteDatabase_nutrition = database_nutrition_object.getWritableDatabase();
        cursor_food= sqLiteDatabase_nutrition .rawQuery("select * from " + database_nutrition_object.DB_food_table, null);
        cursor_food.moveToFirst();

        while (!cursor_food.isAfterLast()) {
            int colIndex = cursor_food.getColumnIndex( key_food_message);
            food_list.add(cursor_food.getString(colIndex));
            cursor_food.moveToNext();
        }
        addButton_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_list.add(editText_food.getText().toString());
                cValues_food =new ContentValues();
                cValues_food.put(key_food_message,editText_food.getText().toString());
                sqLiteDatabase_nutrition .insert(database_nutrition_object.DB_food_table, null,cValues_food);
                foodAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView();
                editText_food.setText("");
            }
        });
                listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent nutrition_detail_Intent = new Intent(FoodActivity.this, NutritionActivity.class);
                        startActivity(nutrition_detail_Intent );
                    }
                } );
    }
    private class FoodAdapter extends ArrayAdapter<String> {

        public FoodAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return food_list.size();
        }

        public String getItem(int position) {
            return food_list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = FoodActivity.this.getLayoutInflater();
            food_item_result = null ;
            food_item_result = inflater.inflate(R.layout.food_item, null);
            TextView message = (TextView)food_item_result .findViewById(R.id.food_item_text);
            message.setText(getItem(position)); // get the string at position
            return food_item_result ;
        }
//        public long getItemId(int position){
//            cursor_food.moveToPosition(position);
//            food_item_ID=cursor_food.getLong(cursor_food.getColumnIndex(Database_nutrition.key_food_RowID));
//            return food_item_ID;
//        }
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (this.requestCode == requestCode && data != null) {
//            Long id = data.getLongExtra("id", -1);
//            sqLiteDatabase_nutrition.delete(DB_food_Name , database_nutrition_object .key_food_RowID + "=" + id, null);
//            refreshActivity();
//        }}
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        database_nutrition_object.close();
        cursor_food.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    public void refreshActivity(){
        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }
}
