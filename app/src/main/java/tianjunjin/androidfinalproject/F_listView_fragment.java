package tianjunjin.androidfinalproject;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.R;

import static java.sql.DriverManager.println;

/**
 * Created by Admin on 2017-12-24.
 */

public class F_listView_fragment extends Fragment {
    Cursor f_c;
    Database_nutrition DB;
    ListView listView;
    private ArrayList<Map> foodList = new ArrayList();
    FoodAdapter foodAdapter;


    public F_listView_fragment() {
    }
    public void init(ArrayList<Map> foodList) {
        this.foodList = foodList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.f_listview_fragment, container, false);
        listView = fragView.findViewById(R.id.f_listView_history);
        foodAdapter = new FoodAdapter(getActivity());
     //   Collections.reverse(listView);
        listView.setAdapter(foodAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, String> message = foodAdapter.getItem(position);

                    long idInDb =  foodAdapter.getItemId(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",idInDb);
                    bundle.putString("type", message.get(Database_nutrition.key_food_TYPE));
                    bundle.putString("time", message.get(Database_nutrition.key_TIME ));
                    bundle.putString("calories", message.get(Database_nutrition.key_Calories));
                    bundle.putString("total_Fat", message.get(Database_nutrition.key_Total_Fat ));
                    bundle.putString("carbohydrate", message.get(Database_nutrition. key_Carbohydrate));
                    Intent intent = new Intent(getActivity(), F_DetailActivity .class);
                    intent.putExtra("bundle", bundle);
                    getActivity().finish();
                    startActivity(intent);
                }
           });
        return fragView;
    }
    private class FoodAdapter extends ArrayAdapter<Map<String, String>> {

        public FoodAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return foodList.size();
        }

        public Map<String, String> getItem(int position) {

        //    return foodList.get(position);
            return foodList.get(getCount() - position - 1);
        }
        public long getItemId(int position) {

            String id = foodList.get(position).get("id").toString();
            return Long.parseLong(id);
//            f_c.moveToPosition(position);
//            return Long.parseLong(f_c.getString(f_c.getColumnIndex(DB.key_food_RowID)));
        }
//        public long getItemId(int position){
//            Map<String, String> content = getItem(position);
//            return Long.parseLong(content.get("id"));}
//
//        public long getItemId(int position){
//            cursor.moveToPosition(position);
//            return cursor.getLong(cursor.getColumnIndex(aHelper.KEY_ID));
//        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.food_item, null);
            if (!foodList.isEmpty()) {
            TextView message = result.findViewById(R.id.food_item_text);
            Map<String, String> food_view = getItem(position);

            String detail = "type: " + food_view.get("type") + " time: " + food_view.get("time") + " calories: " + food_view.get("calories") + " total_Fat: " + food_view.get("total_Fat") + " carbohydrate: " + food_view.get("carbohydrate");
            message.setText(detail);
            }
            return result;
        }
    }
}

