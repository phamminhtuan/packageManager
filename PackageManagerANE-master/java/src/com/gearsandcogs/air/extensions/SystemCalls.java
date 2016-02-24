package com.gearsandcogs.air.extensions;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.adobe.fre.FREContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import android.content.Context;
/**
 * Created by jhilton on 10/3/14.
 */
public class SystemCalls
{
    public static final String TAG = "RunApp";

    public static JSONArray getApplications(FREContext freContext, String type) {
        PackageManager pm = freContext.getActivity().getPackageManager();
        List<ApplicationInfo> installedApplications = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        JSONArray ja = new JSONArray();
        for (ApplicationInfo appInfo : installedApplications) {
            Boolean add_app = false;
            Boolean system_app = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) !=0;

            if (system_app && type.equals("system")) {
                // IS A SYSTEM APP
                add_app = true;
            }

            if (!system_app && type.equals("user")) {
                // IS A USER INSTALLED APP
                add_app = true;
            }

            if (add_app) {
                Log.d("OUTPUT", "Package name : " + appInfo.packageName);
                Log.d("OUTPUT", "Name: " + appInfo.loadLabel(pm));
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("label", appInfo.loadLabel(pm).toString());
                    jo.put("packageName", appInfo.packageName);
                    ja.put(jo);
                }
                catch (Exception e) {
                    //some error occurred with reading the package
                }
            }
        }

        return ja;
    }

    //TODO: Get app icons
    // Drawable d = getPackageManager().getApplicationIcon("com.somepackage.com");
    // need to convert Drawable to Bitmap data (byteArray) to pass to AS3

//    public static Bitmap drawableToBitmap (Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable)drawable).getBitmap();
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//
//        return bitmap;
//    }

//    Bitmap bmp = intent.getExtras().get("data");
//    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//    byte[] byteArray = stream.toByteArray();

    public static Boolean installApplication(FREContext freContext, String app_id) {
        try {
            Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=" + app_id));
            freContext.getActivity().startActivity(goToMarket);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Boolean runApplication(FREContext freContext, String app_id, String json_params) {
        try {
            Intent launchIntent = freContext.getActivity().getPackageManager().getLaunchIntentForPackage(app_id);

            JSONObject jsonObject = new JSONObject(json_params);
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (jsonObject.getString(key) instanceof String) {
                    launchIntent.putExtra(key, jsonObject.getString(key));
                }
            }

            freContext.getActivity().startActivity(launchIntent);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Boolean uninstallApplication(FREContext freContext, String app_id) {
        try {
            Uri packageURI = Uri.parse("package:" + app_id);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            freContext.getActivity().startActivity(uninstallIntent);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static Boolean forceStopApplication(FREContext freContext, String app_id) {
    	 try {  
             Process process = Runtime.getRuntime().exec("su");  
             OutputStream out = process.getOutputStream();  
             String cmd = "am force-stop " + app_id + " \n";  
             try {  
                 out.write(cmd.getBytes());  
                 out.flush();
             } catch (IOException e) {  
                 e.printStackTrace(); 
                 return false;
             }  
   
             process.getOutputStream().close();  
             process = null;  
             return true;
         } catch (Exception e) {  
             e.printStackTrace(); 
             return false;
         }  
    }
    
    public static Boolean clearDataAndCacheApplication(FREContext freContext, String app_id) {
   	 try {  
            Process process = Runtime.getRuntime().exec("su");  
            OutputStream out = process.getOutputStream();  
            String cmd = "pm clear " + app_id + " \n";  
            try {  
                out.write(cmd.getBytes());  
                out.flush();
            } catch (IOException e) {  
                e.printStackTrace(); 
                return false;
            }  
  
            process.getOutputStream().close();  
            process = null;  
            return true;
        } catch (Exception e) {  
            e.printStackTrace(); 
            return false;
        }  
   }
}
