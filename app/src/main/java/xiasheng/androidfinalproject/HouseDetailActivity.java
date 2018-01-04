package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import sulijin.androidfinalproject.R;

public class HouseDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        FragmentThermo thermoFragment = new FragmentThermo();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        thermoFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.HouseFrameLayout, thermoFragment);
        ft.commit();
    }
}
