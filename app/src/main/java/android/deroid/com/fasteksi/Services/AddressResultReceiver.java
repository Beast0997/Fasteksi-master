package android.deroid.com.fasteksi.Services;

import android.deroid.com.fasteksi.Fragment.LaterFragment;
import android.deroid.com.fasteksi.Fragment.MapsFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by gulshan on 20/02/16.
 */
public class AddressResultReceiver  extends ResultReceiver{
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    private  Receiver mreceiver;
    MapsFragment mapFragment;

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    public void customMethod(MapsFragment mapFragment) {
        this.mapFragment=mapFragment;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }


    public void setReceiver(Receiver receiver){
    mreceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mreceiver != null){
           /* if(mapFragment.isAdded()) {

            }*/
            mreceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
