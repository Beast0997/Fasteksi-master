package android.deroid.com.fasteksi.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.deroid.com.fasteksi.Adapter.CustomDrawerAdapter;
import android.deroid.com.fasteksi.Fragment.FragmentAirpot;
import android.deroid.com.fasteksi.Fragment.FragmentFares;
import android.deroid.com.fasteksi.Fragment.FragmentFavorites;
import android.deroid.com.fasteksi.Fragment.FragmentFeedback;
import android.deroid.com.fasteksi.Fragment.FragmentMyBooking;
import android.deroid.com.fasteksi.Fragment.FragmentMyWallets;
import android.deroid.com.fasteksi.Fragment.FragmentProfile;
import android.deroid.com.fasteksi.Fragment.FragmentTrackMyCab;
import android.deroid.com.fasteksi.Fragment.MapsFragment;
import android.deroid.com.fasteksi.Model.DrawerItem;
import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gulshank on 19-02-2016.
 */
public class BookMyCar extends AppCompatActivity {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private String[] navMenuTitles;
    private List<DrawerItem> _items;

    CustomDrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_book_my_car);

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame , new MapsFragment()).commit();

        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.navList);
        //mDrawerList.addFooterView();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        addDrawerItems();
        setUPDrawer();

    }

    private void setUPDrawer() {

        _items = new ArrayList<DrawerItem>();

        _items.add(new DrawerItem("My Wallets", R.drawable.wallet));
        _items.add(new DrawerItem("My Bookings", R.drawable.car));
        _items.add(new DrawerItem("Track My Cab" , R.drawable.track));
        _items.add(new DrawerItem("Airpot/Kerb Pickups" , R.drawable.airpot));
        _items.add(new DrawerItem("Feedback" , R.drawable.feedback));
        _items.add(new DrawerItem("Favorites", R.drawable.favorite));
        _items.add(new DrawerItem("Profile", R.drawable.profile));
        _items.add(new DrawerItem("Fares" , R.drawable.rupee));

        // mDrawerList.addFooterView((View) getResources().getLayout(R.layout.listview_footer));
        View footerView = ((LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer ,null);
        mDrawerList.addFooterView(footerView);



        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

      /*  mDrawerList.setAdapter(new CustomDrawerAdapter(this , R.layout.drawer_list_item ,_items));*/
        adapter = new CustomDrawerAdapter(this ,R.layout.drawer_list_item, _items);
        mDrawerList.setAdapter(adapter);

    }

    private void addDrawerItems() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_close, R.string.navigation_drawer_open) {


            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

        };

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }

        private void displayView(int position) {

            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new FragmentMyWallets();
                    break;

                case 1:
                    fragment = new FragmentMyBooking();
                    break;

                case 2:
                    fragment = new FragmentTrackMyCab();
                    break;

                case 3:
                    fragment = new FragmentAirpot();
                    break;

                case 4:
                    fragment = new FragmentFeedback();
                    break;
                case  5:
                    fragment = new FragmentFavorites();
                    break;

                case 6:
                    fragment = new FragmentProfile();
                    break;

                case 7:
                    fragment = new FragmentFares();
                    break;
            }
            String backStack = this.getClass().getName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(backStack).commit();
            mDrawerList.setItemChecked(position, true);
            setTitle(_items.get(position).getItemName());
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}
