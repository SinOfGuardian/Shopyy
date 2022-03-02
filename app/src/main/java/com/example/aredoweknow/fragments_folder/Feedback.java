package com.example.aredoweknow.fragments_folder;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.aredoweknow.R;
import com.example.aredoweknow.dialogClass;

public class Feedback extends Fragment {
    private Button button;
    private EditText subject, body;
    protected String dev_emailAdd = "kienielvalenzuela05@gmail.com";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        button = (Button) view.findViewById(R.id.feedback_btn);
        subject = (EditText) view.findViewById(R.id.subject_val);
        body = (EditText) view.findViewById(R.id.feedback_val);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subj_text = isSubjectEmpty(subject.getText().toString().trim());
                String body_text = body.getText().toString().trim();

                if (!isFeedbackEmpty(body_text)) {
                    Intent mainIntent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:?subject=" + subj_text + "&body=" + body_text + "&to=" + dev_emailAdd);
                    mainIntent.setData(data);
                    startActivity(Intent.createChooser(mainIntent, "Send Feedback"));

                    subject.setText("");
                    body.setText("");
                }
            }
        });

        body.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (body.isFocused()) {
                    body.setSelected(false);
                }
            }
        });

        return view;
    }


    private String isSubjectEmpty(String subjText) {
        if (subjText.equals("")) {
            subjText = "My Feedback";
        }
        return subjText;
    }

    private boolean isFeedbackEmpty(String bodyText) {
        boolean isEmpty = false;

        if (bodyText.equals("")) {
            isEmpty = true;
            body.setSelected(true); //--> Highlight red
        }
        if (isEmpty) {
            display_messageDialog("Feedback message cannot be empty!");
        }

        return isEmpty;
    }

    //-----------------------------------------------------------------------------------------
    private void display_messageDialog(String message) {
        Dialog dialog1 = new Dialog(getActivity());
        dialogClass dialog = new dialogClass();

        dialog.simpleDialog(dialog1, message); //--> show simple dialog
    }
}
