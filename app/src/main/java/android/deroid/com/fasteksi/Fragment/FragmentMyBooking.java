package android.deroid.com.fasteksi.Fragment;



import android.deroid.com.fasteksi.Adapter.ViewPagerAdapter;
import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gulshank on 21-01-2016.
 */
public class FragmentMyBooking extends Fragment {

    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mybooking, container ,false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return  view;
    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        pagerAdapter.addFrag(new pendingBookingFragment(), "Pending Booking");
        pagerAdapter.addFrag(new PostBookingFragment() ,"Post Booking");
        viewPager.setAdapter(pagerAdapter);

    }

}
