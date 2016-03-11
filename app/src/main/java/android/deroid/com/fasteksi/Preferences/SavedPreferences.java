package android.deroid.com.fasteksi.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gulshank on 17-02-2016.
 */
public class SavedPreferences {

    SharedPreferences preferences;
    Context context;
    SharedPreferences.Editor editor;
    static final String ISLOGGEDIN = "ISLOGGEDIN";

    int PRIVATE_MODE = 0;
    static final String Prefere_Name = "SavedPreferences";
    static final String Is_Login = "IsLoggedIn";
    static final String Customer_mobileNumber = "phone";
    static final String Customer_password = "password";


    public static void setUserLoggedIn(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(ISLOGGEDIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ISLOGGEDIN, true);
        editor.commit();
    }

    public static void setUserLoggedOut(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(ISLOGGEDIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ISLOGGEDIN, false);
        editor.commit();
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(ISLOGGEDIN, Context.MODE_PRIVATE);
        return pref.getBoolean(ISLOGGEDIN, false);
    }
}
