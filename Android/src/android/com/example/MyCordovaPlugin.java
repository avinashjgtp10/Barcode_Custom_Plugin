/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
public class MyCordovaPlugin extends CordovaPlugin {

  private static final int ZXING_CAMERA_PERMISSION = 1;
  private static final String TAG = "CordovaPluginScancode";
  private CallbackContext mCallbackContext;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing CordovaPluginScancode");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    mCallbackContext = callbackContext;
    if(action.equals("requestPermission")) {
      checkPermission();
    } else if(action.equals("scan")) {
      if (ContextCompat.checkSelfPermission(this.cordova.getActivity(), Manifest.permission.CAMERA)
              != PackageManager.PERMISSION_GRANTED) {
        cordova.requestPermission(this,ZXING_CAMERA_PERMISSION,Manifest.permission.CAMERA);
      }
      else {
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);
        mCallbackContext.sendPluginResult(result);
        // An example of returning data back to the web layer
        //or Context context=cordova.getActivity().getApplicationContext();
        Context context = this.cordova.getActivity().getApplicationContext();
        Intent intent = new Intent(context, FullScreenActivity.class);
        cordova.startActivityForResult(this, intent, 1);
      }
    }
    return true;
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent){
    Log.d(TAG, "activity result in plugin: requestCode(" + requestCode + "), resultCode(" + resultCode + ")");
    if(requestCode == 1) {
        Bundle res = intent.getExtras();
        String _result = res.getString("results");
        JSONObject resultObj = new JSONObject();

      Log.d("FIRST", "result:"+_result);
        final PluginResult result = new PluginResult(PluginResult.Status.OK, _result.toString());
        mCallbackContext.sendPluginResult(result);
    }
  }

  public void checkPermission() {
    if (ContextCompat.checkSelfPermission(this.cordova.getActivity(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
        cordova.requestPermission(this,ZXING_CAMERA_PERMISSION,Manifest.permission.CAMERA);
    } else {
      final PluginResult result = new PluginResult(PluginResult.Status.OK, true);
      mCallbackContext.sendPluginResult(result);
    }
  }

  public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case ZXING_CAMERA_PERMISSION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Context context = this.cordova.getActivity().getApplicationContext();
          Intent intent = new Intent(context, FullScreenActivity.class);
          cordova.startActivityForResult(this, intent, 1);
        } else {
          final PluginResult result = new PluginResult(PluginResult.Status.OK, false);
          mCallbackContext.sendPluginResult(result);
        }
        return;
    }
  }

}
