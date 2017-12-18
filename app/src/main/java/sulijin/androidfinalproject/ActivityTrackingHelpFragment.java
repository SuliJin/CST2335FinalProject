package sulijin.androidfinalproject;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityTrackingHelpFragment extends Fragment {


    public ActivityTrackingHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_tracking_help, container, false);
        ((TextView)view.findViewById(R.id.t_help)).setMovementMethod(new ScrollingMovementMethod());
        final Intent startIntent = new Intent(getActivity(), ActivityTrackingActivity.class);
        ((Button)view.findViewById(R.id.t_help_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(startIntent);
            }
        });
        return view;
    }

}
