package tianjunjin.androidfinalproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.support.v7.widget.Toolbar;
import sulijin.androidfinalproject.R;

public class f_activity extends AppCompatActivity{
    TextView textView;
    Database_nutrition f_db;
    Toolbar toolbar;
    protected static final String ACTIVITY_NAME = " f_activity ";

    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent startIntent = new Intent(this, f_activity.class);
        switch (item.getItemId()) {
            case R.id.f_help:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(getResources().getString(R.string.t_help_title));
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.fragment_f_help, null);
                ((TextView)dialogView.findViewById(R.id.f_help)).setMovementMethod(new ScrollingMovementMethod());
                builder2.setView(dialogView);
                builder2.setPositiveButton(R.string.f_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_f, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_activity);
        final Intent startIntent = new Intent(this, f_activity.class);
        textView = (TextView) findViewById(R.id.textView_title);
        f_db = new Database_nutrition(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar_f);
       setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.f_welcome, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button f_historyButton = (Button) findViewById(R.id.f_button_history);
        final Intent f_history = new Intent(this, F_historyActivity.class);
        f_historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(f_history);
            }
        });

        final Button f_newEntryButton = (Button) findViewById(R.id.f_button_new);
        final Intent f_newEntry = new Intent(this, F_NewEntryActivity.class);
        f_newEntryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(f_newEntry);
            }
        });
        final Button f_static =(Button) findViewById(R.id.f_static);
        f_static.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_staticFragment static_fragment= new F_staticFragment();
                addFragment(static_fragment);
            }
        });
    }
    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager =getFragmentManager();
        //remove previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.f_static_framelayout, fragment).addToBackStack(null).commit();
    }


}