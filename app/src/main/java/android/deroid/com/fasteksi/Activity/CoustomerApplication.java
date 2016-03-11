package android.deroid.com.fasteksi.Activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by gulshank on 05-02-2016.
 */
public class CoustomerApplication extends Application{

    static CoustomerApplication instance;
    private static Context mContext;
    public  static Resources mResources;

    private ActivityLifecycleCallback activityLifeCycleCallback = new ActivityLifecycleCallback();


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mResources = getResources();
        registerActivityLifecycleCallbacks(activityLifeCycleCallback);
    }

    public static Context getmContext() {
        return mContext;
    }

    public CoustomerApplication(){
        instance = this;
    }
    public static CoustomerApplication getInstance() {
        return instance;
    }


    public void clearAllActivities(){
        activityLifeCycleCallback.clearAllActivities();
    }

}
