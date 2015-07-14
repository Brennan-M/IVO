package com.ivolabs.android.ivo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomescreenFragment extends Fragment {

    Button startIVOPostButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_homescreen, container, false);

        enableHomeScreenButtons(view);

        return view;
    }

    private void enableHomeScreenButtons(View view) {
        startIVOPostButton = (Button) view.findViewById(R.id.startIVOPostActivityButton);

        startIVOPostButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent startIVOPostActivity = new Intent(HomescreenFragment.this.getActivity(), IVOPost.class);
                 startActivity(startIVOPostActivity);
             }
        });
    }
}