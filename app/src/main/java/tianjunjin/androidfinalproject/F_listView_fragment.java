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
import java.util.HashMap;
import java.util.Map;

import sulijin.androidfinalproject.ActivityTrackingActivity;
import sulijin.androidfinalproject.ActivityTrackingDatabaseHelper;
import sulijin.androidfinalproject.ActivityTrackingEditActivity;
import sulijin.androidfinalproject.ActivityTrackingListViewFragment;
import sulijin.androidfinalproject.R;
import zhentingmai.androidfinalproject.AutoDatabaseHelper;
import zhentingmai.androidfinalproject.AutoInfo;

/**
 * Created by Admin on 2017-12-24.
 */

public class F_listView_fragment extends Fragment {
    Database_nutrition f_db;
    Cursor f_c;
    ListView listView;
    SQLiteDatabase f_database;
    private ArrayList<Map> foodList = new ArrayList();
    //   FoodAdapter foodAdapter;

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
        listView = fragView.findViewById(R.id.listView_history);
        return  fragView;
    }

}

//        foodAdapter = new FoodAdapter(getActivity());
//        listView.setAdapter(foodAdapter );
//
//        FoodQuery foodQuery =new FoodQuery();
//        foodQuery.execute();
//
////        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////                Map message = chatAdapter.getItem(position);
////                long idInDb =  chatAdapter.getItemId(position);
////                Bundle bundle = new Bundle();
////              //  bundle.putLong(ActivityTrackingActivity.ID,idInDb);
////                bundle.putString(Database_nutrition.key_food_TYPE, message.get(Database_nutrition.key_food_TYPE).toString());
////                bundle.putString(Database_nutrition.key_TIME , message.get(Database_nutrition.key_TIME ).toString());
////                bundle.putString(Database_nutrition.key_Calories, message.get(Database_nutrition.key_Calories).toString());
////                bundle.putString(Database_nutrition.key_Total_Fat , message.get(Database_nutrition.key_Total_Fat ).toString());
////                bundle.putString(Database_nutrition.key_Carbohydrate, message.get(Database_nutrition. key_Carbohydrate).toString());
////
//////                Intent intent = new Intent(getActivity(), F_detail.class);
//////                intent.putExtra("bundle", bundle);
//////                getActivity().finish();
//////                startActivity(intent);
////                F_detail_fragement  detail_fragment= new F_detail_fragement();
////                detail_fragment.setArguments(bundle);
////                FragmentTransaction ft = getFragmentManager().beginTransaction();
////                ft.replace(R.id.f_FrameLayout, detail_fragment);
////                ft.commit();
////            }
////        });
//        return fragView;
//    }
//    class FoodAdapter extends ArrayAdapter<Map<String, String>> {
//        public FoodAdapter(Context ctx) {
//            super(ctx, 0);
//        }
//        public int getCount(){return foodList .size();}
//        public Map<String,String> getItem(int position){
//            return foodList .get(position);
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent){
//
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            View result = inflater.inflate(R.layout.food_item, null);
//            if (!foodList.isEmpty()) {
//            TextView message = result.findViewById(R.id.food_item_text);
//            Map<String, String> food_view = getItem(position);
//
//            String detail = "type: " + food_view.get("type") + " time: " + food_view.get("time") + " calories: " + food_view.get("calories") + " total_Fat: " + food_view.get("total_Fat") + " carbohydrate: " + food_view.get("carbohydrate");
//            message.setText(detail);
//            }
//            return result;
//        }
//        public long getItemId(int position){
//            f_c.moveToPosition(position);
//            return f_c.getLong(f_c.getColumnIndex(f_db.key_food_RowID));
//        }
//    }
//
//    class FoodQuery extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//
//            f_database = f_db.getWritableDatabase();
//            f_c = f_database.rawQuery("select * from " + Database_nutrition.DB_food_table, null);
//            f_c.moveToFirst();
//
//            while (!f_c.isAfterLast()) {
//                Map<String, String> f_infor = new HashMap<>();
//                f_infor.put("id", Database_nutrition.key_food_RowID);
//                f_infor.put("type", Database_nutrition.key_food_TYPE);
//                f_infor.put("time", Database_nutrition.key_TIME);
//                f_infor.put("calories", Database_nutrition.key_Calories);
//                f_infor.put("total_Fat", Database_nutrition.key_Total_Fat);
//                f_infor.put("carbohydrate", Database_nutrition.key_Carbohydrate);
//                         foodList.add(f_infor);
//                f_c.moveToNext();
//            }
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            foodAdapter.notifyDataSetChanged();
//        }
//    }
//
//}
