package xiasheng.androidfinalproject;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sulijin.androidfinalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseHelpFragment extends Fragment {


    public HouseHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_house_help, container, false);
    }

}