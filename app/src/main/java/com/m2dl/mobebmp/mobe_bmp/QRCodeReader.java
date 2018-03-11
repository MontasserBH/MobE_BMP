package com.m2dl.mobebmp.mobe_bmp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by mazab on 10/03/2018.
 */

public class QRCodeReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private TextView scannerResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);
        scannerResult = (TextView) findViewById(R.id.scan_result_text);



    }

    @Override
    public void handleResult(Result rawResult) {
// Do something with the result here
        setContentView(R.layout.activity_qrcode_reader);
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        String contents = rawResult.getText();
        scannerResult = (TextView) findViewById(R.id.scan_result_text);
        scannerResult.setText(contents);
        Toast.makeText(this, contents,Toast.LENGTH_LONG).show();


// show the scanner result into dialog box.
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setMessage(rawResult.getText());
        //AlertDialog alert1 = builder.create();
        //alert1.show();


// If you would like to resume scanning, call this method below:
// mScannerView.resumeCameraPreview(this);
    }

    public void QrScanner(View view){

        mScannerView = new ZXingScannerView(this); // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera(); // Start camera
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera(); // Stop camera on pause
    }
}