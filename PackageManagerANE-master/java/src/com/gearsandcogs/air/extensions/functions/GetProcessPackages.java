package com.gearsandcogs.air.extensions.functions;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.gearsandcogs.air.extensions.SystemCalls;

/**
 * Created by Andy on 22/02/16.
 */
public class GetProcessPackages implements FREFunction
{
    public static final String TAG = "GetProcessPackages";

    @Override
    public FREObject call(FREContext freContext, FREObject[] freObjects) {
    	String app_name = "";
        try {
            app_name = freObjects[0].getAsString();
        }catch (Exception e) {
            //something bad happened
        }
    	
    	FREObject processPackage=null;
		try {
			String strPackages = SystemCalls.getProcessPackages(freContext, app_name);
			processPackage = FREObject.newObject(strPackages);
		} catch (FREWrongThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //FREObject processPackage = (FREObject)
        return processPackage;
    }
}