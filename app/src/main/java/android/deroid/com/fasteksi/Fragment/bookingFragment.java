package android.deroid.com.fasteksi.Fragment;

import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by gulshank on 19-02-2016.
 */
public class bookingFragment extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout layoutlater ,layoutnow;
    String addressTxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.booking_frame ,container ,false);
        initViews();
        layoutlater.setOnClickListener(this);
        layoutnow.setOnClickListener(this);
        return view;
    }

    private void initViews() {
        layoutlater = (LinearLayout)view.findViewById(R.id.layout_later);
        layoutnow = (LinearLayout)view.findViewById(R.id.layout_now);
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;

        switch (v.getId()){
            case R.id.layout_later:
                Toast.makeText(getActivity(),"Later",Toast.LENGTH_SHORT).show();
                fragment = new LaterFragment();
                break;
            case R.id.layout_now:
                Toast.makeText(getActivity(),"Later",Toast.LENGTH_SHORT).show();
                fragment = new NowFragment();
                break;
        }
        String backStack = this.getClass().getName();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame ,fragment).addToBackStack(backStack).commit();
    }
}
