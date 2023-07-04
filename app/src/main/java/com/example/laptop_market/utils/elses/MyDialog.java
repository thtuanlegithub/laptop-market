package com.example.laptop_market.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop_market.R;

public class MyDialog {
    private Dialog dialog;
    private Button btnYes, btnNo, btnOk;
    private DialogClickListener dialogClickListener;

    private MyDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Find views
        btnYes = dialog.findViewById(R.id.btnYes);
        btnNo = dialog.findViewById(R.id.btnNo);
        btnOk = dialog.findViewById(R.id.btnOk);

        // Set button click listeners
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    dialogClickListener.onYesClick();
                }
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    dialogClickListener.onNoClick();
                }
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    dialogClickListener.onOkClick();
                }
                dialog.dismiss();
            }
        });
    }

    public static void showDialog(Context context, String message, DialogType type, DialogClickListener listener) {
        MyDialog myDialog = new MyDialog(context);
        myDialog.dialogClickListener = listener;
        TextView txtDialogMessage = myDialog.dialog.findViewById(R.id.txtDialogMessage);
        txtDialogMessage.setText(message);

        switch (type) {
            case OK:
                myDialog.btnYes.setVisibility(View.GONE);
                myDialog.btnNo.setVisibility(View.GONE);
                myDialog.btnOk.setVisibility(View.VISIBLE);
                break;
            case YES_NO:
                myDialog.btnYes.setVisibility(View.VISIBLE);
                myDialog.btnNo.setVisibility(View.VISIBLE);
                myDialog.btnOk.setVisibility(View.GONE);
                break;
        }
        myDialog.dialog.show();
    }

    public interface DialogClickListener {
        void onYesClick();
        void onNoClick();
        void onOkClick();
    }

    public enum DialogType {
        OK,
        YES_NO
    }
}
