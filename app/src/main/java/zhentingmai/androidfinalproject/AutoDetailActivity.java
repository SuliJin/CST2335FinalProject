package zhentingmai.androidfinalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import sulijin.androidfinalproject.R;

public class AutoDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detail);
        AutoHistFragment autoHistFragment = new AutoHistFragment();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        autoHistFragment.setArguments(bundle);
        FragmentManager fragmentManager =getFragmentManager();
        //remove previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.phoneFrameLayout, messageFragment).addToBackStack(null).commit();
        fragmentTransaction.add(R.id.histFrameLayout, autoHistFragment).addToBackStack(null).commit();
    }
}
