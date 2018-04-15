package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import android.content.Intent;

public class FullScreenActivity extends Activity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "FullScreenActivity";
    private ZXingScannerView mScannerView;

    public static String RAW_RESULT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        // Toogle autofocus:
        mScannerView.setAutoFocus(true);

        // this paramter will make your HUAWEI phone works great!
//        mScannerView.setAspectTolerance(0.5f);

        // Toggle flash:
//        mScannerView.setFlash(false);

        // Specify interested barcode formats:
//        void setFormats(List<BarcodeFormat> formats);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.d(TAG, rawResult.getText()); // Prints scan results
        Log.d(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        RAW_RESULT = rawResult.getText();

        //Toast.makeText(FullScreenActivity.this, rawResult.getText() + " " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        finishWithResult(rawResult.getText() ,1);
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    private void finishWithResult(String result,int status)
    {
        Bundle conData = new Bundle();
        conData.putString("results", result);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(status, intent);
        finish();
    }
}
