package sulijin.androidfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import tianjunjin.androidfinalproject.f_activity;
import xiasheng.androidfinalproject.trackingHouseActivity;
import zhentingmai.androidfinalproject.AutomobileActivity;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button activityTrackingButton = findViewById(R.id.activity_tracking);
        final Intent activityTrackingIntent = new Intent(this, ActivityTrackingActivity.class);
        activityTrackingButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(activityTrackingIntent);
            }
        });

        Button nutritionButton = findViewById(R.id.nutrition);
        final Intent nutritionIntent = new Intent(this,f_activity.class);
        nutritionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(nutritionIntent);
            }
        });

        Button houseButton = findViewById(R.id.house);
        final Intent houseIntent = new Intent(this, trackingHouseActivity.class);
        houseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(houseIntent);
            }
        });

        Button autoButton = findViewById(R.id.automobile);
        final Intent autoIntent = new Intent(this, AutomobileActivity.class);
        autoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(autoIntent);
            }
        });

    }
    }

