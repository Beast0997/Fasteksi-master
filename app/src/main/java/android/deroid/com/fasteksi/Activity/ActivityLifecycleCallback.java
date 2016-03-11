package android.deroid.com.fasteksi.Activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gulshank on 05-02-2016.
 */
public class ActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    List<Activity> activities = new ArrayList<Activity>();
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }

    public  void clearAllActivities(){
        List<Activity> copies  = new ArrayList<Activity>();
        copies.addAll(activities);

        for (final  Activity activity :copies ){

            if (!activity.isFinishing()){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.finish();
                    }
                });
            }
        }
    }
}
