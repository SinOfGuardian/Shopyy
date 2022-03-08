package com.example.aredoweknow.other_class;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aredoweknow.R;

public class dialogClass {

    public void simpleDialog(Dialog dialog, String text) {
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView message = dialog.findViewById(R.id.messageLabel);
        Button okayBtn = dialog.findViewById(R.id.okayBtn);

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        message.setText(text);
        dialog.show();
    }
}
