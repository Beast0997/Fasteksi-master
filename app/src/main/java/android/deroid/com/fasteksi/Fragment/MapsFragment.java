package android.deroid.com.fasteksi.Fragment;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.deroid.com.fasteksi.Adapter.AutoCompleteTextForArea;
import android.deroid.com.fasteksi.R;
import android.deroid.com.fasteksi.Services.AddressResultReceiver;
import android.deroid.com.fasteksi.Services.FetchAddressIntentService;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements LocationListener, AddressResultReceiver.Receiver {


    private LocationManager locManager;
    private boolean gps_enable = false;
    private boolean network_enable;
    ConnectivityManager connectivityManager;
    TextView currentStatus;
    NetworkInfo networkInfo;
    Location location;
    LatLng latLng;
    Geocoder geocoder;
    double latitude;
    Handler handler;
    SupportMapFragment mSupportMapFragment;
    double longitude;
    MarkerOptions markerOptions;
    CameraUpdate cameraUpdate;
    int MINTIME = 5000;
    int MINDISTANCE = 6000;
    GoogleMap gMap;
    String addressText;
    View view;
    ImageView close;
    Spinner spinnerCity;
    AutoCompleteTextView autoCompletetextArea;
    public static  String results ="";
    public AddressResultReceiver addressResultReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.map_fragment, container, false);
            currentStatus = (TextView) view.findViewById(R.id.txt_CurrentStatus);
         /*   Fragment fragment = new bookingFragment();
            FragmentManager fm = getChildFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
            Bundle args = new Bundle();
            args.putString("addressText", results);
            fragment.setArguments(args);
            fragmentTransaction.add(R.id.booking_frame, fragment);
            fragmentTransaction.commit();*/
            getChildFragmentManager().beginTransaction().add(R.id.booking_frame, new bookingFragment()).commit();
            checkConnection();
            return view;
        } else
            return view;
    }

    protected void checkConnection() {
        // TODO Auto-generated method stub
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enable = locManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            network_enable = locManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            if (gps_enable) {
                currentStatus.setText(addressText);
                setUpMap();
                getLocation();

            } else {
                gpsDisableDialog();
            }
            if (network_enable) {
                Toast.makeText(getActivity(), "network_enable true", Toast.LENGTH_SHORT).show();
            }
        } else {
            currentStatus.setText("No Internet Connection.Please Check.");
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINDISTANCE, MINTIME, this);
        if (locManager != null) {
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                onLocationChanged(location);
            }
        }
    }


    private void setUpMap() {
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        gMap = mSupportMapFragment.getMap();
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    gMap = googleMap;
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    gMap.setMyLocationEnabled(true);
                    /*gMap.moveCamera(cameraUpdate);
                    gMap.animateCamera(cameraUpdate);*/
                }
            });
        }
    }

    private void gpsDisableDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Unable to fetch your location. Please enable location access.")
                .setTitle("Warning")
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                        /*startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));*/
                    }
                });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View view = inflater.inflate(R.layout.gpsalert_dialog, null);
                spinnerCity = (Spinner) view.findViewById(R.id.spinner_city);
                close = (ImageView) view.findViewById(R.id.img_close);
                autoCompletetextArea = (AutoCompleteTextView) view.findViewById(R.id.edt_area);
                autoCompletetextArea.setAdapter(new AutoCompleteTextForArea(getActivity(), android.R.layout.simple_list_item_1));
                autoCompletetextArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String str = (String) parent.getItemAtPosition(position);
                        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                builder.setCancelable(true);
                builder.show();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(final Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        markerOptions = new MarkerOptions().position(latLng).draggable(true);
        gMap.animateCamera(cameraUpdate);
        gMap.addMarker(markerOptions);
        if (latLng != null) {
            //addressText = getCompleteAddressString(latLng);
            //new getCompleteAddressString(latitude ,longitude).execute();
            addressResultReceiver = new AddressResultReceiver(new Handler());
            addressResultReceiver.setReceiver(this);
            addressResultReceiver.customMethod(MapsFragment.this);
            Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
            intent.putExtra("longitude", longitude);
            intent.putExtra("latitude", latitude);
            intent.putExtra("recevier", addressResultReceiver);
            //   intent.putExtra("requestID" , 0);
            getContext().startService(intent);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

       /* if (resultCode == 1) {
            String results = resultData.getString("addressText");
            currentStatus.setText(results);
        } else if (resultCode == 0) {
            Toast.makeText(getContext(), "Please Wait Process is Runing" ,Toast.LENGTH_SHORT).show();
        }else {
            String message = resultData.getString(Intent.EXTRA_TEXT);
            Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
        }*/

        if (resultCode == 1) {
            results = resultData.getString("addressText");
            currentStatus.setText(results);
        }
        if (resultCode == 0) {
            Toast.makeText(getContext(), "Please Wait Process is Runing", Toast.LENGTH_SHORT).show();
        }
        if (resultCode == 2) {
            String message = resultData.getString(Intent.EXTRA_TEXT);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
