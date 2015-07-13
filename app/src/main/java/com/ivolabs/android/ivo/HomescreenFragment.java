package com.ivolabs.android.ivo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomescreenFragment extends Fragment {

    Button startIVOPostButton;
    private int contentView;

    public HomescreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homescreen, container, false);
        setContentView(R.layout.fragment_homescreen);

        // Locate the button in activity_main.xml
        startIVOPostButton = (Button) findViewById(R.id.IvoPostActivity);

        // Capture button clicks
        startIVOPostButton.setOnClickListener(new OnClickListener()) {
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntent = new Intent(HomescreenFragment.this.getActivity(), IVOPostActivity.class);
                startActivity(myIntent);
            }
        };
    }
}
