package android.deroid.com.fasteksi.Services;

import android.app.IntentService;
import android.content.Intent;
import android.deroid.com.fasteksi.Fragment.MapsFragment;
import android.deroid.com.fasteksi.R;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gulshank on 19-02-2016.
 */
public class FetchAddressIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    String addressText;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        double doublelongitude;
        double doublelatitude;
        double longitude = 0;
        double latitude = 0;
        List<Address> addresses = null;
        Geocoder geocoder;

        doublelongitude = intent.getDoubleExtra("longitude", longitude);

        doublelatitude = intent.getDoubleExtra("latitude", latitude);

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(doublelatitude, doublelongitude, 1);

            Log.i("addresses", addresses.toString());
            if (addresses != null) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\t");
                }
                addressText = sb.toString();
            } else {
            }
        } catch (IOException e) {
            errorMessage = getString(R.string.servicenotavaibale);
            e.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = getString(R.string.latlng);
        }
        mReceiver = intent.getParcelableExtra("recevier");
        Bundle bundle = new Bundle();
        mReceiver.send(STATUS_RUNNING, Bundle.EMPTY);

        bundle.putString("addressText", addressText);
        mReceiver.send(STATUS_FINISHED, bundle);

        bundle.putString(Intent.EXTRA_TEXT, "ERROR");
        mReceiver.send(STATUS_ERROR, bundle);
   /*     if (new MapsFragment().isAdded()) {
            mReceiver = intent.getParcelableExtra("recevier");
            Bundle bundle = new Bundle();
            mReceiver.send(STATUS_RUNNING, Bundle.EMPTY);

            bundle.putString("addressText", addressText);
            mReceiver.send(STATUS_FINISHED, bundle);

            bundle.putString(Intent.EXTRA_TEXT, "ERROR");
            mReceiver.send(STATUS_ERROR, bundle);
        }*/
    }
}
