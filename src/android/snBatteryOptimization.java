
/*
 Copyright 2013 Sebastián Katzer
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

package cordova.softniels.plugin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.view.View;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import android.util.Log;

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
 * Implements extended functions around the main purpose
 * of infinite execution in the background.
 */
public class snBatteryOptimization extends CordovaPlugin {

    // // To keep the device awake
    // private PowerManager.WakeLock wakeLock;
 
    public final int MY_OP = 11;
    private CallbackContext callback = null;

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
                            CallbackContext callbackContext)
    {
        boolean validAction = true;
        callback = callbackContext;
        switch (action)
        {
            case "battery":
                disableBatteryOptimizations();
                break;
            case "foreground":
                moveToForeground();
                break;
            default:
                validAction = false;
        }

        if (validAction) {
            //callbackContext.success("Action: " + action);
        } else {
            callbackContext.error("Invalid action: " + action);
        }

        return validAction;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if( requestCode == MY_OP )
        {
            if( resultCode == Activity.RESULT_OK )
            {
                PluginResult result = new PluginResult(PluginResult.Status.OK, "battery optimization sucess");
                result.setKeepCallback(true);
                callback.sendPluginResult(result);
            }
            else
            {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "no params returned successfully" );
                result.setKeepCallback(true);
                callback.sendPluginResult(result);
            }
        }
    }
 
    public void moveToForeground2() {
        try {
            Activity activity = cordova.getActivity();
            Intent intent = new Intent(activity, activity.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void moveToForeground() {
        try {
            Activity activity = cordova.getActivity();
            Intent intent = new Intent(activity, activity.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
            callback.success("Sucess in action: moveToForeground");
        } catch (Exception e) {
            callback.error("Erro in action: moveToForeground: " + e);
        }
        // try {
        //     Activity activity = cordova.getActivity();
        //     Intent notificationIntent = new Intent(activity, activity.getClass());
        //     notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //     PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, notificationIntent, 0);
        //     try {
        //         pendingIntent.send();
        //     } catch (PendingIntent.CanceledException e) {
        //         e.printStackTrace();
        //     }
        //     callback.success("Sucess in action: moveToForeground");
        // } catch (Exception e) {   
        //     callback.error("Erro in action: moveToForeground: " + e);
        // }
    }

    // private void moveToForeground() {
    //     try {
    //         Activity activity = cordova.getActivity();
    //         Intent intent     = new Intent(activity);
    //         String pkgName    = activity.getPackageName();
         
    //         intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
    //         intent.setData(Uri.parse("package:" + pkgName));
    //         intent.addFlags(
    //             Intent.FLAG_ACTIVITY_SINGLE_TOP |
    //             Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

    //         activity.startActivity(intent);
    //         callback.success("Sucess in action: moveToForeground");
    //     } catch (Exception e) {
    //         callback.error("Erro in action: moveToForeground: " + e);
    //     }
    // }

    /**
     * Disables battery optimizations for the app.
     * Requires permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS to function.
     */
    @SuppressLint("BatteryLife")
    private void disableBatteryOptimizations()
    {   
        try {
            Activity activity = cordova.getActivity();
            Intent intent     = new Intent();
            String pkgName    = activity.getPackageName();
            PowerManager pm   = (PowerManager)getService(POWER_SERVICE);

            if (SDK_INT < M){
                callback.success("Action: Battery optimization sucess: MIN SDK");
                return;
            }

            if (pm.isIgnoringBatteryOptimizations(pkgName)){
                callback.success("Action: Battery optimization sucess: already has");
                return;
            }
         
            //cordova.setActivityResultCallback(this);
            intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + pkgName));
            //cordova.getActivity().startActivity(intent);
            cordova.startActivityForResult(this, intent, MY_OP);

            //callback.success("Action: Battery optimization sucess");
        } catch (Exception e) {
            callback.error("N/A");
        }
    }
    
    /**
     * Returns the activity referenced by cordova.
     */
    Activity getApp() {
        return cordova.getActivity();
    }

    /**
     * Gets the launch intent for the main activity.
     */
    private Intent getLaunchIntent()
    {
        Context app    = getApp().getApplicationContext();
        String pkgName = app.getPackageName();

        return app.getPackageManager().getLaunchIntentForPackage(pkgName);
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

    /**
     * Returns list of all possible intents to present the app start settings.
     */
    private List<Intent> getAppStartIntents()
    {
        return Arrays.asList(
            new Intent().setComponent(new ComponentName("com.miui.securitycenter","com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.autostart.AutoStartActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.entry.FunctionActivity")).setData(android.net.Uri.parse("mobilemanager://function/entry/AutoStart")),
            new Intent().setAction("com.letv.android.permissionautoboot"),
            new Intent().setComponent(new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity")),
            new Intent().setComponent(ComponentName.unflattenFromString("com.iqoo.secure/.MainActivity")),
            new Intent().setComponent(ComponentName.unflattenFromString("com.meizu.safe/.permission.SmartBGActivity")),
            new Intent().setComponent(new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity")),
            new Intent().setComponent(new ComponentName("cn.nubia.security2", "cn.nubia.security.appmanage.selfstart.ui.SelfStartActivity")),
            new Intent().setComponent(new ComponentName("com.zui.safecenter", "com.lenovo.safecenter.MainTab.LeSafeMainActivity"))
        );
    }
}
