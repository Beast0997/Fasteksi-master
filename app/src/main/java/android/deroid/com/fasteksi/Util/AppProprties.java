package android.deroid.com.fasteksi.Util;

import android.content.Context;
import android.content.res.Resources;
import android.deroid.com.fasteksi.Activity.CoustomerApplication;
import android.deroid.com.fasteksi.R;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by gulshank on 18-02-2016.
 */
public class AppProprties  extends Properties{

    private static final long serialVersionUID = 1L;

    private static AppProprties instance;

    private static Context mContext;

    private AppProprties() {
        super();
    }

    public  static AppProprties getInstance(){
        if (instance  == null){
            instance = new AppProprties();
            instance.loadProperties();
        }
        return instance;
    }

    private void loadProperties() {
        loadProperties(R.raw.endpoint);
    }



    private void loadProperties(int resourceId) {
        InputStream rawResource = null;
        try {
            Resources resources = CoustomerApplication.getInstance().getResources();
            rawResource = resources.openRawResource(resourceId);
            this.load(rawResource);
        } catch (Resources.NotFoundException e) {
            Log.e("loadProperties ",
                    "Did not find raw resource: " + resourceId, e);
        } catch (IOException e) {
            Log.e("loadProperties ", "Failed to open sequent property file ", e);
        } finally {
            if (rawResource != null)

                try {
                    rawResource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    public String getRootUrl() {
        return getProperty("rootUrl");
    }

    public String getEndPoint(String endpointName) {
        return getProperty(endpointName);
    }
}
