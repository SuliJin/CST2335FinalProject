package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import sulijin.androidfinalproject.R;

public class food_frameLayout extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_frame_layout);

        //        open bundle_phone
        Bundle bundle = getIntent().getBundleExtra("food_bundle");
//        load bundle into the new Messagefragment
        Food_fragment food_fragment = new Food_fragment();
        food_fragment.setArguments(bundle);
//        load the new MessageFragment into the emptyFrame with ft
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.food_empty_frameLayout, food_fragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}
