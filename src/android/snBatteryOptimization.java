package cordova.softniels.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* * */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.view.View;

import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import java.util.Arrays;
import java.util.List;

import static android.R.string.cancel;
import static android.R.string.ok;
import static android.R.style.Theme_DeviceDefault_Light_Dialog;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.content.pm.PackageManager.MATCH_DEFAULT_ONLY;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.M;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

/**
 * This class echoes a string called from JavaScript.
 */
public class snBatteryOptimization extends CordovaPlugin {

    // To keep the device awake
    private PowerManager.WakeLock wakeLock;

    /**
     * Executes the request.
     *
     * @param action   The action to execute.
     * @param args     The exec() arguments.
     * @param callback The callback context used when
     *                 calling back into JavaScript.
     *
     * @return Returning false results in a "MethodNotFound" error.
     */
    @Override
    public boolean execute (String action, JSONArray args,
                            CallbackContext callback)
    {
        boolean validAction = true;

        switch (action)
        {
            case "battery":
                disableBatteryOptimizations();
                break;
            case "teste":
                disableBatteryOptimizationsteste();
                break;
            default:
                validAction = false;
        }

        if (validAction) {
            callback.success("teste ok retorno: " + action);
        } else {
            callback.error("Invalid action: " + action);
        }

        return validAction;
    }

    // @Override
    // public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    //     if (action.equals("battery")) {
    //         String message = args.getString(0);
    //         this.disableBatteryOptimizations();
    //         return true;
    //     }
    //     return false;
    // }








    // private void disableBatteryOptimizations(String message, CallbackContext callbackContext) {
        // if (message != null && message.length() > 0) {
            // callbackContext.success(message);
        // } else {
            // callbackContext.error("Expected one non-empty string argument.");
        // }
    // }

    /**
     * Disables battery optimizations for the app.
     * Requires permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS to function.
     */
    @SuppressLint("BatteryLife")
    private void disableBatteryOptimizations()
    {
        Activity activity = cordova.getActivity();
        Intent intent     = new Intent();
        String pkgName    = activity.getPackageName();
        PowerManager pm   = (PowerManager)getService(POWER_SERVICE);

        if (SDK_INT < M)
            return;

        if (pm.isIgnoringBatteryOptimizations(pkgName))
            return;

        intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + pkgName));

        cordova.getActivity().startActivity(intent);
    }

    private void disableBatteryOptimizationsteste()
    {
        
    }

    /**
     * Returns the activity referenced by cordova.
     */
    Activity getApp() {
        return cordova.getActivity();
    }


    /**
     * Get the requested system service by name.
     *
     * @param name The name of the service.
     */
    private Object getService(String name)
    {
        return getApp().getSystemService(name);
    }

}