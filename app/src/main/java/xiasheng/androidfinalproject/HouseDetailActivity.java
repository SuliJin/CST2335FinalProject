package xiasheng.androidfinalproject;

import android.app.Activity;
import android.app.FragmentManager;
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
        FragmentManager FM =getFragmentManager();
        thermoFragment.setArguments(bundle);
        if (FM.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = FM.getBackStackEntryAt(0);
            FM.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.HouseFrameLayout, thermoFragment).addToBackStack(null).commit();
    }
}
