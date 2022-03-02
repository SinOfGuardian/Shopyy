package com.example.aredoweknow.fragments_folder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aredoweknow.R;

public class Feedback extends Fragment {
     Button button;
     EditText feed;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        button = (Button) view.findViewById(R.id.feedbackBTN);
        feed = (EditText) view.findViewById(R.id.TextTEXT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FEND = feed.getText().toString();

                Intent mainIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "My Feedback" + "&body=" + FEND + "&to=" + "kienielvalenzuela05@gmail.com" );
                mainIntent.setData(data);
                startActivity(Intent.createChooser(mainIntent, "Send mail..."));

            }
        });

        return view;
    }

}
