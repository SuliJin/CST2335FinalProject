package zhentingmai.androidfinalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import sulijin.androidfinalproject.R;



public class AutoHistoryActivity extends Activity {
    protected static final String ACTIVITY_NAME = "AutoHistoryActivity";
    TextView time;
    TextView liters;
    TextView price;
    TextView kilo;
    ListView listView;

    ArrayList<carInfo> list=new ArrayList<>();
    AutoDatabaseHelper aHelper;
    Cursor cursor;
    CarAdapter carAdapter;
    private ProgressBar progressBar;



    class carInfo{
        private String id;
        private String time;
        private String price;
        private String liters;
        private String kilo;

        public carInfo(String id, String time, String price, String liters, String kilo) {
            this.id = id;
            this.time = time;
            this.price = price;
            this.liters = liters;
            this.kilo = kilo;
        }

        public String getId() {
            return id;
        }

        public String getTime() {
            return time;
        }

        public String getPrice() {
            return price;
        }

        public String getLiters() {
            return liters;
        }

        public String getKilo() {
            return kilo;
        }
    }

    private class CarAdapter extends ArrayAdapter<carInfo> {
        public CarAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return list.size();
        }

        public carInfo getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(aHelper.KEY_ID));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.auto_record, parent, false);
            liters=(TextView) view.findViewById(R.id.textView_litersRecord);
            price =(TextView) view.findViewById(R.id.textView_priceRecord);
            kilo = (TextView) view.findViewById(R.id.textView_kiloRecord);
            time = (TextView) view.findViewById(R.id.textView_timeRecord);
            liters.setText("Liters: "+getItem(position).getLiters());
            price.setText("Price($/L): "+getItem(position).getPrice());
            kilo.setText("Kilometers: "+getItem(position).getKilo());
            time.setText(getItem(position).getTime());

            return view;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_history);

        listView=(ListView)findViewById(R.id.listView_history);
        progressBar=(ProgressBar) findViewById(R.id.auto_progressBar);

        carAdapter =new CarAdapter(this);
        listView.setAdapter(carAdapter);

        aHelper = new AutoDatabaseHelper(this);
        aHelper.openDatabase();

      /* list.add(new carInfo("1","20","200","10","2000"));
        list.add(new carInfo("2","30","150","15","1500"));
       list.add(new carInfo("3","12","122","8","666"));
        list.add(new carInfo("4","25","18","18","2222"));*/
       DatabaseQuery query=new DatabaseQuery();
        query.execute();

       /* aHelper=new AutoDatabaseHelper(this);
        aHelper.openDatabase();

        cursor=aHelper.read();
        cursor.moveToFirst();

        int colIndexId=cursor.getColumnIndex(AutoDatabaseHelper.KEY_ID);
        int colIndexPrice=cursor.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
        int colIndexLiters=cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
        int colIndexKilo=cursor.getColumnIndex(AutoDatabaseHelper.KEY_KILO);
        int colIndexTime=cursor.getColumnIndex(AutoDatabaseHelper.KEY_TIME);

        while(!cursor.isAfterLast()){
            list.add(new carInfo(cursor.getString(colIndexId),cursor.getString(colIndexTime),cursor.getString(colIndexPrice),cursor.getString(colIndexLiters),cursor.getString(colIndexKilo)));
            cursor.moveToNext();
        }
*/


//        cursor =db.rawQuery("select "+AutoDatabaseHelper.KEY_LITERS+" from " + AutoDatabaseHelper.TABLE_NAME,null);

//        int colIndexLiter= cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
        //int colIndexPrice=c.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
        //int colIndexKilo=c.getColumnIndex(AutoDatabaseHelper.KEY_KILO);
        final Intent intent = new Intent(this,AutoDetailActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carInfo carInfo=carAdapter.getItem(position);
                long idInDb= carAdapter.getItemId(position);
                Bundle bundle = new Bundle();
                bundle.putLong("id", idInDb);
                bundle.putString("time",carInfo.getTime());
                bundle.putString("price",carInfo.getPrice());
                bundle.putString("liters",carInfo.getLiters());
                bundle.putString("kilo",carInfo.getKilo());

                intent.putExtra("bundle",bundle);
                startActivityForResult(intent,5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==5 && data != null) {
            Long id = data.getLongExtra("id", -1);
            /*AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("doyou");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();*/

            aHelper.delete(id);
           refreshActivity();
        }
    }

     public class DatabaseQuery extends AsyncTask<carInfo,Integer,String> {


         protected void onProgressUpdate(Integer ...value){
             Log.i(ACTIVITY_NAME, "in onProgressUpdate");
             progressBar.setVisibility(View.VISIBLE);
             progressBar.setProgress(value[0]);
         }

         @Override
         protected String doInBackground(carInfo... carInfos) {
            try {


                cursor = aHelper.read();
                cursor.moveToFirst();

                int colIndexId = cursor.getColumnIndex(AutoDatabaseHelper.KEY_ID);
                int colIndexPrice = cursor.getColumnIndex(AutoDatabaseHelper.KEY_PRICE);
                int colIndexLiters = cursor.getColumnIndex(AutoDatabaseHelper.KEY_LITERS);
                int colIndexKilo = cursor.getColumnIndex(AutoDatabaseHelper.KEY_KILO);
                int colIndexTime = cursor.getColumnIndex(AutoDatabaseHelper.KEY_TIME);

                while (!cursor.isAfterLast()) {
                    list.add(new carInfo(cursor.getString(colIndexId), cursor.getString(colIndexTime), cursor.getString(colIndexPrice), cursor.getString(colIndexLiters), cursor.getString(colIndexKilo)));
                    cursor.moveToNext();
                }
                SystemClock.sleep(400);
                publishProgress(25);
                SystemClock.sleep(400);
                publishProgress(50);
                SystemClock.sleep(400);
                publishProgress(75);
                SystemClock.sleep(400);
                publishProgress(100);
            }catch(Exception e){
                 e.printStackTrace();
                }
             return null;
         }

         @Override
         protected void onPostExecute(String result){
             Log.i(ACTIVITY_NAME, "In onPostExecute");
             carAdapter.notifyDataSetChanged();
             progressBar.setVisibility(View.INVISIBLE);
         }
     }

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //aHelper.closeDatabase();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

   public void refreshActivity(){
       finish();
       Intent intentRef = getIntent();
       startActivity(intentRef);
   }
}


