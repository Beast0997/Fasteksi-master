package android.deroid.com.fasteksi.Fragment;

import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gulshank on 02-03-2016.
 */
public class WhenCabTime extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.when_cabfragment , container ,false);
            return view;
        }else
            return view;
    }
}
