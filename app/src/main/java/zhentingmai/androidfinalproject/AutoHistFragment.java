package zhentingmai.androidfinalproject;


import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sulijin.androidfinalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutoHistFragment extends Fragment {

    private View view;
    private EditText editTextTime;
    private EditText editTextPrice;
    private EditText editTextLiters;
    private EditText editTextKilo;
    private EditText editTextId;
    private Button btDel;
    private Button btUpdate;
    private AutoDatabaseHelper dpHelper;

    public AutoHistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_auto_hist, container, false);
        dpHelper=new AutoDatabaseHelper(getActivity());
        dpHelper.setWritable();
        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("id");
        String year = bundle.getString("year");
        String month = bundle.getString("month");
        String day = bundle.getString("day");

        String price = bundle.getString("price");
        String liters = bundle.getString("liters");
        String kilo = bundle.getString("kilo");
        editTextId=view.findViewById(R.id.fragmentId);
        editTextId.setText(""+id);
        editTextTime=view.findViewById(R.id.fragmentTime);
        editTextTime.setText(year+"-"+month+"-"+day);
        editTextPrice=view.findViewById(R.id.fragmentPrice);
        editTextPrice.setText(price);
        editTextLiters=view.findViewById(R.id.fragmentLiters);
        editTextLiters.setText(liters);
        editTextKilo=view.findViewById(R.id.fragmentKilo);
        editTextKilo.setText(kilo);

        btDel = view.findViewById(R.id.autoFragmentDel);
        btDel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                dpHelper.delete(id);
                getActivity().finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);


               /* Intent intent = new Intent();
                intent.putExtra("id",id);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();*/

            }
        });

        btUpdate =view.findViewById(R.id.autoFragmentUpdate);

        btUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String strYear = calendar.get(Calendar.YEAR)+"";
                String strMonth = calendar.get(Calendar.MONTH)+"";
                String strDay = calendar.get(Calendar.DAY_OF_MONTH)+"";
                String strPrice=editTextPrice.getText().toString();
                String strLiters=editTextLiters.getText().toString();
                String strKilo=editTextKilo.getText().toString();
                dpHelper.update(id,strYear,strMonth,strDay,strPrice,strLiters,strKilo);
                getActivity().finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);
            }
        });
        return view;
    }

}
