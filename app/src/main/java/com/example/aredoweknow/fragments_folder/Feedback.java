package com.example.aredoweknow.fragments_folder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aredoweknow.R;

public class Feedback extends Fragment {
     Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        button = (Button) view.findViewById(R.id.feedbackBTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Feedback US", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
