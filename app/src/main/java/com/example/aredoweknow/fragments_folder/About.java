package com.example.aredoweknow.fragments_folder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aredoweknow.R;

public class About extends Fragment implements View.OnClickListener{
    TextView hyperlink;

    TextView dev1, dev2, dev3, dev4, dev5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        hyperlink = (TextView) view.findViewById(R.id.source_code);
        hyperlink.setMovementMethod(LinkMovementMethod.getInstance());

        dev1 = view.findViewById(R.id.contact1);
        dev2 = view.findViewById(R.id.contact2);
        dev3 = view.findViewById(R.id.contact3);
        dev4 = view.findViewById(R.id.contact4);

        dev1.setOnClickListener(this);
        dev2.setOnClickListener(this);
        dev3.setOnClickListener(this);
        dev4.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String dev_emailAdd = "shopyy@gmail.com";

        switch (v.getId()) {
            case R.id.contact1:
                dev_emailAdd = getResources().getString(R.string.baron_email);
                break;
            case R.id.contact2:
                dev_emailAdd = getResources().getString(R.string.carlo_email);
                break;
            case R.id.contact3:
                dev_emailAdd = getResources().getString(R.string.lariosa_email);
                break;
            case R.id.contact4:
                dev_emailAdd = getResources().getString(R.string.nath_email);
                break;

            default:
                break;
        }

        Intent mainIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "" + "&body=" + "" + "&to=" + dev_emailAdd);
        mainIntent.setData(data);
        startActivity(Intent.createChooser(mainIntent, "Contact Dev"));
    }
}
