package android.deroid.com.fasteksi.Fragment;


import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gulshank on 21-01-2016.
 */
public class FragmentMyWallets extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mywallet, container ,false);
        intiViews();
        return  view;
    }

    private void intiViews() {


    }
}
