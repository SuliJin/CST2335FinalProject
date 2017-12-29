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

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.autoDetailFrameLayout, autoHistFragment).addToBackStack(null).commit();
    }
}
