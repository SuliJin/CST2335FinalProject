package zhentingmai.androidfinalproject;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sulijin.androidfinalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutoHistFragment extends Fragment {

    private View view;
    private TextView textViewTime;
    private TextView textViewPrice;
    private TextView textViewLiters;
    private TextView textViewKilo;
    private TextView textViewId;
    private Button btDel;
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
        dpHelper.openDatabase();
        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("id");
        String time = bundle.getString("time");
        String price = bundle.getString("price");
        String liters = bundle.getString("liters");
        String kilo = bundle.getString("kilo");
        TextView textViewId=view.findViewById(R.id.fragmentId);
        textViewId.setText("ID="+id);
        TextView textViewTime=view.findViewById(R.id.fragmentTime);
        textViewTime.setText("Time="+time);
        TextView textViewPrice=view.findViewById(R.id.fragmentPrice);
        textViewPrice.setText("Price="+price);
        TextView textViewLiters=view.findViewById(R.id.fragmentLiters);
        textViewLiters.setText("Liters="+liters);
        TextView textViewKilo=view.findViewById(R.id.fragmentKilo);
        textViewKilo.setText("Kilo="+kilo);

        btDel = view.findViewById(R.id.deleteAutoButton);
        btDel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("id",id);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
