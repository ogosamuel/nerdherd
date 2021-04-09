package com.example.nerdherd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nerdherd.Database.LocalUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

/**
 * Maintain consistency in the app
 * 4 types of trials have been broken down into their model classes and fragment classes to maintain consistency and so that the app can follow everything being clicked on the screen that affects it
 * Essentially to create and host dialog per trial
 * @author Ogooluwa S. osamuel
 */

public class NonnegativeTrialFragment extends DialogFragment {
    private EditText inputEt;

    private String experimentId;
    private Bitmap image;

    private String qdata = null;
    private Button launchRegisterQrButton;

    public NonnegativeTrialFragment(String experimentId){
        this.experimentId=experimentId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.non_negative_trial, null);

        //link to xml
        Button Recordtbn = view.findViewById(R.id.record_nonNegativeTrial);
        inputEt = view.findViewById(R.id.nonNegative_input);
        TextView qrcontainstv = view.findViewById(R.id.tv_binom_qr_data);
        Button saveQRBtn = view.findViewById(R.id.btn_save_qr_code);
        ImageView generateQRiv = view.findViewById(R.id.iv_binom_qr);
        image = null;
        qrcontainstv.setText("");

        launchRegisterQrButton = view.findViewById(R.id.btn_launch_register_qr);

        launchRegisterQrButton.setVisibility(View.GONE);

        launchRegisterQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterBarcodeActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(check_int(inputEt.getText().toString())) {
                    String value = inputEt.getText().toString();
                    image = QRHelper.generateQRCode(experimentId+":"+value);
                    qrcontainstv.setText(getQRActionDescription(value));
                    generateQRiv.setImageBitmap(image);
                    saveQRBtn.setVisibility(View.VISIBLE);
                    qdata = experimentId+":"+value;
                    launchRegisterQrButton.setVisibility(View.VISIBLE);
                    launchRegisterQrButton.setText("Register Result to Barcode");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Recordtbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = inputEt.getText().toString();
                image = QRHelper.generateQRCode(experimentId+":"+value);
                qrcontainstv.setText(getQRActionDescription(value));
                generateQRiv.setImageBitmap(image);
                saveQRBtn.setVisibility(View.VISIBLE);
                int outcome;
                if(check_int(value)) {
                    outcome = Integer.parseInt(value);
                    if(outcome < 0) {
                        inputError();
                    } else {
                        ((TrialActivity) getActivity()).addNonNegativeTrial(outcome);
                    }
                } else {
                    inputError();
                }
            }
        });

        saveQRBtn.setVisibility(View.GONE);

        saveQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image != null) {
                    QRHelper.saveQRCode(image);
                }
            }
        });


        // we create the acctual dialog here
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .setNegativeButton("Cancel", null)
                .create();
    }

    public void inputError() {
        Toast.makeText(this.getContext(), "Invalid entry!", Toast.LENGTH_LONG).show();
    }

    public boolean check_int(String val){
        try
        {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            int overwrite = data.getIntExtra("overwrite", -1);
            String qrData = data.getStringExtra("qrData");
            if(overwrite != -1 && qrData != null) {
                LocalUser.addRegisteredBarcode(qrData, qdata, overwrite, true);
                launchRegisterQrButton.setText("Result Registered!");
            }
        }
    }

    private String getQRActionDescription(String outcome) {
        return "QR to Add a Non-Negative trial of " + outcome + " to current experiment";
    }
}
